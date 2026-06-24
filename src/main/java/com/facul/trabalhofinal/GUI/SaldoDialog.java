package com.facul.trabalhofinal;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.SpinnerDateModel;

public class SaldoDialog extends JDialog {

    private final ControleDeDados controle;

    private final JRadioButton rbAtual = new JRadioButton("Saldo atual", true);
    private final JRadioButton rbFiltros = new JRadioButton("Usar filtros");
    private final JSpinner dataInicio = new JSpinner(new SpinnerDateModel());
    private final JSpinner dataFim = new JSpinner(new SpinnerDateModel());

    public SaldoDialog(JFrame parent, ControleDeDados controle) {
        super(parent, "Saldo", true);
        this.controle = controle;

        setSize(450, 280);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());

        dataInicio.setEditor(new JSpinner.DateEditor(dataInicio, "dd/MM/yyyy"));
        dataFim.setEditor(new JSpinner.DateEditor(dataFim, "dd/MM/yyyy"));

        add(criarTopo(), BorderLayout.NORTH);
        add(criarBotoes(), BorderLayout.SOUTH);
    }

    private JPanel criarTopo() {
        JPanel painel = new JPanel(new GridLayout(0, 1, 4, 4));
        ButtonGroup grupo = new ButtonGroup();
        grupo.add(rbAtual);
        grupo.add(rbFiltros);

        painel.add(rbAtual);
        painel.add(rbFiltros);
        painel.add(new JLabel("Data inicial para filtros:"));
        painel.add(dataInicio);
        painel.add(new JLabel("Data final para filtros:"));
        painel.add(dataFim);

        return painel;
    }

    private JPanel criarBotoes() {
        JPanel painel = new JPanel();
        JButton calcular = new JButton("Calcular");
        JButton fechar = new JButton("Fechar");

        calcular.addActionListener(e -> calcularSaldo());
        fechar.addActionListener(e -> dispose());

        painel.add(calcular);
        painel.add(fechar);
        return painel;
    }

    

    private void calcularSaldo() {
        try {
            ArrayList<Lancamento> lista = SortFilters.sortByDate(
                    controle.getDespesas(),
                    controle.getReceitas()
            );

            if (rbAtual.isSelected()) {
                lista = ExclusionFilters.filterByDate(
                        lista,
                        LocalDate.of(1900, 1, 1),
                        LocalDate.now()
                );
            } else {
                LocalDate inicio = converterData((Date) dataInicio.getValue());
                LocalDate fim = converterData((Date) dataFim.getValue());
                lista = ExclusionFilters.filterByDate(lista, inicio, fim);
            }

            double saldo = SanitizeFields.valor(controle.balanco(lista));
            JOptionPane.showMessageDialog(this, "Saldo: " + saldo);
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }

    

    private LocalDate converterData(Date data) {
        return data.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }
}
