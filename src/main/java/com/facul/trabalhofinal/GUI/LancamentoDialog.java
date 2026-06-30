package com.facul.trabalhofinal.GUI;


import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;

import com.facul.trabalhofinal.ControleDeDados;
import com.facul.trabalhofinal.Despesa;
import com.facul.trabalhofinal.Receita;
import com.facul.trabalhofinal.SanitizeFields;

public class LancamentoDialog extends JDialog {

    private final ControleDeDados controle;
    private final TipoLancamento tipo;

    private final JTextField txtDescricao = new JTextField(25);
    private final JTextField txtValor = new JTextField(10);
    private final JComboBox<String> comboCategoria = new JComboBox<>();
    private final JSpinner spinnerData = new JSpinner(new SpinnerDateModel());

    public LancamentoDialog(JFrame parent, ControleDeDados controle, TipoLancamento tipo) {
        super(parent, tituloJanela(tipo), true);
        this.controle = controle;
        this.tipo = tipo;

        setSize(500, 300);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());

        carregarCategorias();
        spinnerData.setEditor(new JSpinner.DateEditor(spinnerData, "dd/MM/yyyy"));

        add(criarFormulario(), BorderLayout.CENTER);
        add(criarBotoes(), BorderLayout.SOUTH);
    }

    private static String tituloJanela(TipoLancamento tipo) {
        switch (tipo) {
            case RECEITA:
                return "Adicionar Receita";
            case DESPESA:
                return "Adicionar Despesa";
            default:
                throw new IllegalArgumentException("Tipo de lançamento inválido.");
        }
    }

    private JPanel criarFormulario() {
        JPanel painel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(6, 6, 6, 6);
        c.fill = GridBagConstraints.HORIZONTAL;

        c.gridx = 0;
        c.gridy = 0;
        painel.add(new JLabel("Descrição (máx. 60):"), c);

        c.gridx = 1;
        c.gridy = 0;
        c.gridwidth = 2;
        painel.add(txtDescricao, c);

        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 1;
        painel.add(new JLabel("Categoria:"), c);

        c.gridx = 1;
        c.gridy = 1;
        painel.add(comboCategoria, c);

        JButton btnNovaCategoria = new JButton("Adicionar categoria");
        btnNovaCategoria.addActionListener(e -> adicionarCategoria());
        c.gridx = 2;
        c.gridy = 1;
        painel.add(btnNovaCategoria, c);

        c.gridx = 0;
        c.gridy = 2;
        painel.add(new JLabel("Valor:"), c);

        c.gridx = 1;
        c.gridy = 2;
        c.gridwidth = 2;
        painel.add(txtValor, c);

        c.gridx = 0;
        c.gridy = 3;
        c.gridwidth = 1;
        painel.add(new JLabel("Data:"), c);

        c.gridx = 1;
        c.gridy = 3;
        c.gridwidth = 2;
        painel.add(spinnerData, c);

        return painel;
    }

    private JPanel criarBotoes() {
        JPanel painel = new JPanel();
        JButton btnSalvar = new JButton("Salvar");
        JButton btnCancelar = new JButton("Cancelar");

        btnSalvar.addActionListener(e -> salvarLancamento());
        btnCancelar.addActionListener(e -> dispose());

        painel.add(btnSalvar);
        painel.add(btnCancelar);
        return painel;
    }

    private void carregarCategorias() {
        comboCategoria.removeAllItems();

        switch (tipo) {
            case RECEITA:
                for (String categoria : controle.getCategoriasReceitas()) {
                    comboCategoria.addItem(categoria);
                }
                break;
            case DESPESA:
                for (String categoria : controle.getCategoriasDespesas()) {
                    comboCategoria.addItem(categoria);
                }
                break;
            default:
                throw new IllegalArgumentException("Tipo de lançamento inválido.");
        }
    }

    private void adicionarCategoria() {
        String nova = JOptionPane.showInputDialog(
                this,
                "Nova categoria (máx. 25 caracteres):",
                "Adicionar categoria",
                JOptionPane.PLAIN_MESSAGE
        );

        if (nova == null) {
            return;
        }

        nova = SanitizeFields.categoria(nova);

        if (nova.isBlank()) {
            JOptionPane.showMessageDialog(this, "Categoria inválida.");
            return;
        }

        try {
            switch (tipo) {
                case RECEITA:
                    controle.adicionarCategoriaReceita(nova);
                    break;
                case DESPESA:
                    controle.adicionarCategoriaDespesa(nova);
                    break;
                default:
                    throw new IllegalArgumentException("Tipo de lançamento inválido.");
            }

            comboCategoria.addItem(nova);
            comboCategoria.setSelectedItem(nova);
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }

    private void salvarLancamento() {
        try {
            String descricao = SanitizeFields.descricao(txtDescricao.getText());
            String categoria = SanitizeFields.categoria((String) comboCategoria.getSelectedItem());
            double valor = SanitizeFields.valor(txtValor.getText());
            LocalDate data = converterData((Date) spinnerData.getValue());

            if (descricao.isBlank()) {
                descricao = descricaoPadrao();
            }

            if (valor <= 0) {
                throw new IllegalArgumentException("Valor inválido");
            }

            switch (tipo) {
                case RECEITA:
                    controle.adicionarLancamento(new Receita(descricao, categoria, valor, data));
                    break;
                case DESPESA:
                    controle.adicionarLancamento(new Despesa(descricao, categoria, valor, data));
                    break;
                default:
                    throw new IllegalArgumentException("Tipo de lançamento inválido.");
            }

            dispose();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Valor inválido. Digite um número válido.");
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }

    private String descricaoPadrao() {
        switch (tipo) {
            case RECEITA:
                return "Lancamento Receita";
            case DESPESA:
                return "Lancamento Despesa";
            default:
                throw new IllegalArgumentException("Tipo de lançamento inválido.");
        }
    }

    private LocalDate converterData(Date data) {
        return data.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }
}
