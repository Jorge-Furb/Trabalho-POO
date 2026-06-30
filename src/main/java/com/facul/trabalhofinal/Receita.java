/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.facul.trabalhofinal;

import java.time.LocalDate;

/**
 *
 * @author Jorge
 * @author Lucas
 */
public class Receita extends Lancamento {
    private String categoria;
    
    public Receita(String descricao, String categoria,double valor, LocalDate date) {
        super(descricao,valor, date);
        this.setCategoria(categoria);
    }
    @Override
    public void setDescricao(String s){
        if(s==null||s.isBlank()){
            super.setDescricao("Lancamento Receita");
        }
        else{
            super.setDescricao(s);
        }
    }
    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        if(categoria==null||categoria.isBlank()){
            this.categoria="Outras receitas";
        }
        this.categoria = categoria;
    }
    @Override
    public String toString() {
        return "Receita{" + "categoria=" + categoria + '}'+super.toString();
    }
    
}
