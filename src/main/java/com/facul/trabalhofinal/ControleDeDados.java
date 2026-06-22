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
public class ControleDeDados {
   private ArrayList<Receita> receitas;
   private ArrayList<Despesa> despesas;
   private ArrayList<String> categoriasReceitas;
   private ArrayList<String> categoriasDespesas;
   
   public ControleDeDados(CarregarDados d){
       this.receitas=d.getReceitas();
       this.despesas=d.getDespesas();
       this.categoriasReceitas=d.getCategoriasReceitas();
       this.categoriasDespesas=d.getCategoriasDespesas();
   }
   public ControleDeDados(){
       CarregarDados d = new CarregarDados(); // Composicao só pelo meme
       this.receitas=d.getReceitas();
       this.despesas=d.getDespesas();
       this.categoriasReceitas=d.getCategoriasReceitas();
       this.categoriasDespesas=d.getCategoriasDespesas();
   }
   
   
   public <T extends Lancamento> double balanco(ArrayList<T> lancamentos){
       double total = 0.0;
       for(T l:lancamentos){
           total+=l.getValor();
       }
       return total;
   }
   
   public void adicionarCategoriaReceita(String s){
       for(String str : categoriasReceitas){
           if(s.equals(str)){
               throw new IllegalArgumentException("Categoria ja existe");
           }
       }
       categoriasReceitas.add(s);
   }
   public void adicionarCategoriaDespesa(String s){
       for(String str : categoriasDespesas){
           if(s.equals(str)){
               throw new IllegalArgumentException("Categoria ja existe");
               
           }
       }
       categoriasDespesas.add(s);
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

    public ArrayList<Receita> getReceitas() {
        return receitas;
    }

    public ArrayList<Despesa> getDespesas() {
        return despesas;
    }

    public ArrayList<String> getCategoriasReceitas() {
        return categoriasReceitas;
    }

    public ArrayList<String> getCategoriasDespesas() {
        return categoriasDespesas;
    }
    
   
    
}
