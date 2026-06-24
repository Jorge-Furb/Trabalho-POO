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
    private final boolean receita;
    private final DefaultTableModel modelo;
    private final JPanel painelCategorias;
    private final JSpinner dataInicio = new JSpinner(new SpinnerDateModel());
    private final JSpinner dataFim = new JSpinner(new SpinnerDateModel());
    private final JCheckBox usarFiltroData = new JCheckBox("Usar filtro de data");

    public LancamentosPanel(ControleDeDados controle, boolean receita) {
        this.controle = controle;
        this.receita = receita;
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

        topo.add(new JLabel(receita ? "Receitas" : "Despesas"));
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

    private void carregarCheckboxCategorias() {
        painelCategorias.removeAll();
        ArrayList<String> categorias = receita ? controle.getCategoriasReceitas() : controle.getCategoriasDespesas();
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

            if (receita) {
                ArrayList<Receita> lista = SortFilters.sortByDate(controle.getReceitas());
                lista = aplicarFiltros(lista);
                for (Receita r : lista) {
                    modelo.addRow(new Object[]{r.getDescricao(), r.getCategoria(), String.format("%.2f", r.getValor()), r.getDate()});
                }
                modelo.addRow(new Object[]{
                    "TOTAL",
                    "",
                    String.format("%.2f", controle.balanco(lista)),
                    ""
                });
            } else {
                ArrayList<Despesa> lista = SortFilters.sortByDate(controle.getDespesas());
                lista = aplicarFiltros(lista);
                for (Despesa d : lista) {
                    modelo.addRow(new Object[]{d.getDescricao(), d.getCategoria(), String.format("%.2f", d.getValor()), d.getDate()});
                }
                modelo.addRow(new Object[]{
                    "TOTAL",
                    "",
                    String.format("%.2f", controle.balanco(lista)),
                    ""
                });
            }
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
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
