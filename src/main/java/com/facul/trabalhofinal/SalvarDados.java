/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.facul.trabalhofinal;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author Admin
 */
public class SalvarDados {
    
    public SalvarDados(CarregarDados update) {
       
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(update.getFileDespesas()));
            String linha;
            linha = CarregarDados.getCabecalho();
            bw.write(linha);
            bw.newLine();
            for (Despesa d : update.getDespesas()){
                linha = d.getDescricao()+";"+d.getCategoria()+";"+String.valueOf(Math.abs(d.getValor()))+";"+d.getDate().toString();
                bw.write(linha);
                bw.newLine();
            }
            bw.close();
            bw = new BufferedWriter(new FileWriter(update.getFileReceitas()));
            linha = CarregarDados.getCabecalho();
            bw.write(linha);
            bw.newLine();
            for (Receita r : update.getReceitas()){
                linha = r.getDescricao()+";"+r.getCategoria()+";"+String.valueOf(r.getValor())+";"+r.getDate().toString();
                bw.write(linha);
                bw.newLine();
            }
            bw.close();
            bw = new BufferedWriter(new FileWriter(update.getFileCategoriasDespesas()));
            
            for(String d : update.getCategoriasDespesas()){
                if(d.equals("Outras despesas")){
                    continue;
                }
                bw.write(d);
                bw.newLine();
            }
            bw.close();
            bw = new BufferedWriter(new FileWriter(update.getFileCategoriasReceitas()));
            for(String r : update.getCategoriasReceitas()){
                if(r.equals("Outras receitas")){
                    continue;
                }
                bw.write(r);
                bw.newLine();
            }
            bw.close();
            
        } catch (IOException ex) {
            System.getLogger(SalvarDados.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
            
            
        
    }
}
