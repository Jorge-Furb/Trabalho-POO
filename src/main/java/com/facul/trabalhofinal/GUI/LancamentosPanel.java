package com.facul.trabalhofinal;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.SpinnerDateModel;
import javax.swing.table.DefaultTableModel;

public class LancamentosPanel extends JPanel {

    private final ControleDeDados controle;
    private final TipoLancamento tipo;
    private final DefaultTableModel modelo;
    private final JPanel painelCategorias;
    private final JSpinner dataInicio = new JSpinner(new SpinnerDateModel());
    private final JSpinner dataFim = new JSpinner(new SpinnerDateModel());
    private final JCheckBox usarFiltroData = new JCheckBox("Usar filtro de data");

    public LancamentosPanel(ControleDeDados controle, TipoLancamento tipo) {
        this.controle = controle;
        this.tipo = tipo;
        this.modelo = new DefaultTableModel(new Object[]{"Descrição", "Categoria", "Valor", "Data"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        this.painelCategorias = new JPanel(new GridLayout(0, 1));

        setLayout(new BorderLayout());

        dataInicio.setEditor(new JSpinner.DateEditor(dataInicio, "dd/MM/yyyy"));
        dataFim.setEditor(new JSpinner.DateEditor(dataFim, "dd/MM/yyyy"));

        add(criarFiltros(), BorderLayout.WEST);
        add(new JScrollPane(new JTable(modelo)), BorderLayout.CENTER);

        atualizarTabela();
    }

    private JPanel criarFiltros() {
        JPanel painel = new JPanel(new BorderLayout());
        JPanel topo = new JPanel(new GridLayout(0, 1, 4, 4));

        topo.add(new JLabel(tituloLista()));
        topo.add(usarFiltroData);
        topo.add(new JLabel("Data inicial:"));
        topo.add(dataInicio);
        topo.add(new JLabel("Data final:"));
        topo.add(dataFim);
        topo.add(new JLabel("Categorias:"));

        carregarCheckboxCategorias();

        JButton btnAplicar = new JButton("Aplicar filtros");
        JButton btnLimpar = new JButton("Limpar filtros");

        btnAplicar.addActionListener(e -> atualizarTabela());
        btnLimpar.addActionListener(e -> limparFiltros());

        JPanel botoes = new JPanel(new GridLayout(0, 1, 4, 4));
        botoes.add(btnAplicar);
        botoes.add(btnLimpar);

        painel.add(topo, BorderLayout.NORTH);
        painel.add(new JScrollPane(painelCategorias), BorderLayout.CENTER);
        painel.add(botoes, BorderLayout.SOUTH);
        return painel;
    }

    private String tituloLista() {
        switch (tipo) {
            case RECEITA:
                return "Receitas";
            case DESPESA:
                return "Despesas";
            default:
                throw new IllegalArgumentException("Tipo de lançamento inválido.");
        }
    }

    private void carregarCheckboxCategorias() {
        painelCategorias.removeAll();

        ArrayList<String> categorias;
        switch (tipo) {
            case RECEITA:
                categorias = controle.getCategoriasReceitas();
                break;
            case DESPESA:
                categorias = controle.getCategoriasDespesas();
                break;
            default:
                throw new IllegalArgumentException("Tipo de lançamento inválido.");
        }

        for (String categoria : categorias) {
            JCheckBox check = new JCheckBox(categoria, true);
            painelCategorias.add(check);
        }
    }

    private void limparFiltros() {
        usarFiltroData.setSelected(false);
        for (int i = 0; i < painelCategorias.getComponentCount(); i++) {
            if (painelCategorias.getComponent(i) instanceof JCheckBox) {
                ((JCheckBox) painelCategorias.getComponent(i)).setSelected(true);
            }
        }
        atualizarTabela();
    }

    private void atualizarTabela() {
        try {
            modelo.setRowCount(0);

            switch (tipo) {
                case RECEITA:
                    atualizarReceitas();
                    break;
                case DESPESA:
                    atualizarDespesas();
                    break;
                default:
                    throw new IllegalArgumentException("Tipo de lançamento inválido.");
            }
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }

    private void atualizarReceitas() {
        ArrayList<Receita> lista = SortFilters.sortByDate(controle.getReceitas());
        lista = aplicarFiltros(lista);

        for (Receita r : lista) {
            modelo.addRow(new Object[]{
                r.getDescricao(),
                r.getCategoria(),
                String.format("%.2f", r.getValor()),
                r.getDate()
            });
        }

        adicionarLinhaTotal(controle.balanco(lista));
    }

    private void atualizarDespesas() {
        ArrayList<Despesa> lista = SortFilters.sortByDate(controle.getDespesas());
        lista = aplicarFiltros(lista);

        for (Despesa d : lista) {
            modelo.addRow(new Object[]{
                d.getDescricao(),
                d.getCategoria(),
                String.format("%.2f", d.getValor()),
                d.getDate()
            });
        }

        adicionarLinhaTotal(controle.balanco(lista));
    }

    private void adicionarLinhaTotal(double total) {
        modelo.addRow(new Object[]{
            "TOTAL",
            "",
            String.format("%.2f", total),
            ""
        });
    }

    private <T extends Lancamento> ArrayList<T> aplicarFiltros(ArrayList<T> lista) {
        ArrayList<String> categoriasSelecionadas = categoriasSelecionadas();

        if (!categoriasSelecionadas.isEmpty()) {
            lista = ExclusionFilters.filterByCategoria(lista, categoriasSelecionadas);
        }

        if (usarFiltroData.isSelected()) {
            LocalDate inicio = converterData((Date) dataInicio.getValue());
            LocalDate fim = converterData((Date) dataFim.getValue());
            lista = ExclusionFilters.filterByDate(lista, inicio, fim);
        }

        return lista;
    }

    private ArrayList<String> categoriasSelecionadas() {
        ArrayList<String> selecionadas = new ArrayList<>();
        for (int i = 0; i < painelCategorias.getComponentCount(); i++) {
            if (painelCategorias.getComponent(i) instanceof JCheckBox) {
                JCheckBox check = (JCheckBox) painelCategorias.getComponent(i);
                if (check.isSelected()) {
                    selecionadas.add(check.getText());
                }
            }
        }
        return selecionadas;
    }

    private LocalDate converterData(Date data) {
        return data.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }
}
