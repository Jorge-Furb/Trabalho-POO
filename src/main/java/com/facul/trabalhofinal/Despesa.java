/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.facul.trabalhofinal;

import java.time.LocalDate;

/**
 *
 * @author Admin
 */
public class Despesa extends Lancamento {
    private String categoria;
    
    public Despesa(String descricao, String categoria, double valor, LocalDate date) {
        super(descricao,valor, date);
        this.setCategoria(categoria);
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        if(categoria==null||categoria.isBlank()){
            this.categoria="Outras despesas";
        }
        this.categoria = categoria;
    }
    
}
