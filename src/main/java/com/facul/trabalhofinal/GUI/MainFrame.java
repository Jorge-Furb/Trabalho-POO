package com.facul.trabalhofinal;

import com.facul.trabalhofinal.GUI.BannerPanel;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class MainFrame extends JFrame {

    private final CarregarDados dados;
    private final ControleDeDados controle;
    private final JPanel painelCentral;

    public MainFrame(CarregarDados dados) {
        this.dados = dados;
        this.controle = new ControleDeDados(dados);
        this.painelCentral = new JPanel(new BorderLayout());

        setTitle("Ez Finance");
        setSize(1000, 650);
        setMinimumSize(new Dimension(800, 600));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLayout(new BorderLayout());

        add(criarMenu(), BorderLayout.WEST);
        add(painelCentral, BorderLayout.CENTER);
        mostrarBoasVindas();

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int resposta = JOptionPane.showConfirmDialog(
                        MainFrame.this,
                        "Deseja salvar os dados antes de sair?",
                        "Sair",
                        JOptionPane.YES_NO_CANCEL_OPTION
                );

                if (resposta == JOptionPane.CANCEL_OPTION || resposta == JOptionPane.CLOSED_OPTION) {
                    return;
                }

                if (resposta == JOptionPane.YES_OPTION) {
                    salvarDados();
                }

                dispose();
                System.exit(0);
            }
        });
    }

    private JPanel criarMenu() {
        JPanel menu = new JPanel(new GridLayout(8, 1, 5, 5));

        JButton btnAddReceita = new JButton("Adicionar Receita");
        JButton btnAddDespesa = new JButton("Adicionar Despesa");
        JButton btnListReceitas = new JButton("Listar Receitas");
        JButton btnListDespesas = new JButton("Listar Despesas");
        JButton btnExtrato = new JButton("Extrato");
        JButton btnSaldo = new JButton("Saldo");
        JButton btnSalvar = new JButton("Salvar");
        JButton btnInicio = new JButton("Início");

        btnAddReceita.addActionListener(e -> abrirLancamento(TipoLancamento.RECEITA));
        btnAddDespesa.addActionListener(e -> abrirLancamento(TipoLancamento.DESPESA));
        btnListReceitas.addActionListener(e -> trocarPainel(new LancamentosPanel(controle, TipoLancamento.RECEITA)));
        btnListDespesas.addActionListener(e -> trocarPainel(new LancamentosPanel(controle, TipoLancamento.DESPESA)));
        btnExtrato.addActionListener(e -> trocarPainel(new ExtratoPanel(controle)));
        btnSaldo.addActionListener(e -> new SaldoDialog(this, controle).setVisible(true));
        btnSalvar.addActionListener(e -> salvarDados());
        btnInicio.addActionListener(e -> mostrarBoasVindas());

        menu.add(btnAddReceita);
        menu.add(btnAddDespesa);
        menu.add(btnListReceitas);
        menu.add(btnListDespesas);
        menu.add(btnExtrato);
        menu.add(btnSaldo);
        menu.add(btnSalvar);
        menu.add(btnInicio);

        return menu;
    }

    private void abrirLancamento(TipoLancamento tipo) {
        LancamentoDialog dialog = new LancamentoDialog(this, controle, tipo);
        dialog.setVisible(true);
    }

    private void salvarDados() {
        new SalvarDados(dados);
        JOptionPane.showMessageDialog(this, "Dados salvos com sucesso.");
    }

    private void mostrarBoasVindas() {
        trocarPainel(new BannerPanel());
    }

    private void trocarPainel(JPanel novoPainel) {
        painelCentral.removeAll();
        painelCentral.add(novoPainel, BorderLayout.CENTER);
        painelCentral.revalidate();
        painelCentral.repaint();
    }
}
