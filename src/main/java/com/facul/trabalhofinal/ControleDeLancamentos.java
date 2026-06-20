/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.facul.trabalhofinal;

import java.util.ArrayList;

/**
 *
 * @author Admin
 */

// Por hora adiciona novos Lancamentos
// TODO - Salvar no arquivo, como vai ser feito eu vou descobrir quando tiver a interface grafica implementada
public class ControleDeLancamentos {
   private ArrayList<Receita> receitas;
   private ArrayList<Despesa> despesas;
   
   
   public ControleDeLancamentos(CarregarDados d){
       this.receitas=d.getReceitas();
       this.despesas=d.getDespesas();
   }
   public ControleDeLancamentos(){
       CarregarDados d = new CarregarDados(); // Composicao só pelo meme
       this.receitas=d.getReceitas();
       this.despesas=d.getDespesas();
   }
   
   public void adicionarLancamento(Receita r){
       if(r==null){
           throw new IllegalArgumentException("Erro, objeto vazio");
       }
       receitas.add(r);
   }
   public void adicionarLancamento(Despesa d){
       if(d==null){
           throw new IllegalArgumentException("Erro, objeto vazio");
       }
       despesas.add(d);
   }
   
   
   
}
