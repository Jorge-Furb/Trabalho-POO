/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.facul.trabalhofinal.GUI;

import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 *
 * @author Admin
 */
public class BannerPanel extends JPanel {
      private final Image imagem;

    public BannerPanel() {
        imagem = new ImageIcon(
            "src/main/java/com/facul/trabalhofinal/GUI/Imagens/Porcofinance.png"
        ).getImage();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawImage(
            imagem,
            0,
            0,
            getWidth(),
            getHeight(),
            this
        );
    }
}
