package com.facul.trabalhofinal;

public class TrabalhoFinal {

    public static void main(String[] args) {
        CarregarDados c = new CarregarDados();
        for (Receita r : c.getReceitas()) {
            System.out.println(r.toString());
        }
        for (Despesa r : c.getDespesas()) {
            System.out.println(r.toString());
        }
        /*javax.swing.SwingUtilities.invokeLater(() -> {
            CarregarDados dados = new CarregarDados();
            new MainFrame(dados).setVisible(true);
        });*/
    }
}
