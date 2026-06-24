package com.facul.trabalhofinal;

import java.awt.BorderLayout;
import java.time.YearMonth;
import java.util.ArrayList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class ExtratoPanel extends JPanel {

    private final ControleDeDados controle;
    private final DefaultTableModel modelo;

    public ExtratoPanel(ControleDeDados controle) {
        this.controle = controle;
        this.modelo = new DefaultTableModel(new Object[]{"Data", "Descrição", "Categoria", "Valor", "Saldo"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        setLayout(new BorderLayout());
        add(new JScrollPane(new JTable(modelo)), BorderLayout.CENTER);
        carregarExtrato();
    }

    private void carregarExtrato() {
        modelo.setRowCount(0);

        ArrayList<Lancamento> todos = SortFilters.sortByDate(
                controle.getDespesas(),
                controle.getReceitas()
        );

        double saldo = 0.0;
        YearMonth mesAtual = null;
        ArrayList<Lancamento> lancamentosDoMes = new ArrayList<>();

        for (Lancamento l : todos) {
            YearMonth mesLancamento = YearMonth.from(l.getDate());

            if (mesAtual != null && !mesAtual.equals(mesLancamento)) {
                adicionarLinhaFechamentoMes(mesAtual, lancamentosDoMes, saldo);
                lancamentosDoMes.clear();
            }

            mesAtual = mesLancamento;
            saldo += l.getValor();
            lancamentosDoMes.add(l);

            modelo.addRow(new Object[]{
                l.getDate(),
                l.getDescricao(),
                categoriaDe(l),
                String.format("%.2f", l.getValor()),
                String.format("%.2f", saldo)
            });
        }

        if (mesAtual != null) {
            adicionarLinhaFechamentoMes(mesAtual, lancamentosDoMes, saldo);
        }
    }

    private void adicionarLinhaFechamentoMes(YearMonth mes, ArrayList<Lancamento> lancamentosDoMes, double saldo) {
        double balancoMes = controle.balanco(lancamentosDoMes);
        modelo.addRow(new Object[]{
            mes.toString(),
            "BALANÇO DO MÊS",
            "-",
            SanitizeFields.valor(balancoMes),
            SanitizeFields.valor(saldo)
        });
    }

    private String categoriaDe(Lancamento l) {
        if (l instanceof Receita) {
            return ((Receita) l).getCategoria();
        }
        if (l instanceof Despesa) {
            return ((Despesa) l).getCategoria();
        }
        return "-";
    }
}
