/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.facul.trabalhofinal;

import java.util.ArrayList;
import java.util.Comparator;

/**
 *
 * @author Admin
 */
public class SortFilters {
    private SortFilters() {
        throw new UnsupportedOperationException("Impossivel criar objetos dessa classe");
    }
    
    public static <T extends Lancamento> ArrayList<T> sortByDate(ArrayList<T> original){
        ArrayList<T> sorted = new ArrayList<>(original);
        sorted.sort(Comparator.comparing(Lancamento::getDate));
        return sorted;
    }
    
    public static ArrayList<Lancamento> sortByDate(ArrayList<Despesa> d, ArrayList<Receita> r){
        ArrayList<Lancamento> tudo = new ArrayList<>();
        tudo.addAll(d);
        tudo.addAll(r);
        tudo.sort(Comparator.comparing(Lancamento::getDate));
        return tudo;
    }
    
}
