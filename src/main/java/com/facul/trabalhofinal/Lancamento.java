
package com.facul.trabalhofinal;

import java.time.LocalDate;

/**
 *
 * @author Admin
 */
public abstract class Lancamento {
    private String descricao;
    private double valor;
    private LocalDate date;
    

    public Lancamento(String descricao,double valor, LocalDate date) {
        this.setValor(valor);
        this.setDate(date);
        this.setDescricao(descricao);
    }
    
    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    
    public double getValor() {
        return valor;
    }
    
    public void setValor(double valor) {
        if(valor<=0){
            throw new IllegalArgumentException("Valor invalido");
        }
        this.valor = valor;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
    
    
    
}
