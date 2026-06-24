/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.facul.trabalhofinal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Ordena listas de Lancamentos por data (sempre do mais antigo pro mais recente)
 * 
 * @author Jorge
 * @author Lucas
 */
public class SortFilters {
    private SortFilters() {
        throw new UnsupportedOperationException("Impossivel criar objetos dessa classe");
    }
    /**
     * 
     * @param 
     * @param original unica lista
     * @return lista <Lancamento> ordenada
     */
    public static <T extends Lancamento> ArrayList<T> sortByDate(ArrayList<T> original){
        ArrayList<T> sorted = new ArrayList<>(original);
        Collections.sort(sorted);
        return sorted;
    }
    /**
     * 
     * @param d lista despesas
     * @param r lista receitas
     * @return lista <Lancamento> ordenada
     */
    public static ArrayList<Lancamento> sortByDate(ArrayList<Despesa> d, ArrayList<Receita> r){
        ArrayList<Lancamento> tudo = new ArrayList<>();
        tudo.addAll(d);
        tudo.addAll(r);
        Collections.sort(tudo);
        return tudo;
    }
    
}
