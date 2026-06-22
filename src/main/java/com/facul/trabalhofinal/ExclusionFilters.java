/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.facul.trabalhofinal;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 *
 * @author Admin
 */
public final class ExclusionFilters {

    private ExclusionFilters() {
        throw new UnsupportedOperationException("Impossivel criar objetos dessa classe");
    }

    public static <T extends Lancamento> ArrayList<T> filterByCategoria(ArrayList<T> original, ArrayList<String> categorias) {

        ArrayList<T> filtered = new ArrayList<>();
        if (categorias == null || categorias.isEmpty() || original == null || original.isEmpty()) {
            return filtered;
        }
        for (T l : original) {
            String categoriaLancamento;
            if (l instanceof Receita) {
                categoriaLancamento = ((Receita) l).getCategoria();
            } else if (l instanceof Despesa) {
                categoriaLancamento = ((Despesa) l).getCategoria();
            } else {
                continue;
            }
            for (String s : categorias) {
                if (categoriaLancamento.equals(s)) {
                    filtered.add(l);
                }
            }
        }
        return filtered;
    }
    public static <T extends Lancamento> ArrayList<T> filterByDate(ArrayList<T> original, LocalDate inic, LocalDate fin){
        ArrayList<T> filtered = new ArrayList<>();
        for(T l : original){
            if((l.getDate().isAfter(inic)||l.getDate().isEqual(inic))&&(l.getDate().isBefore(fin)||l.getDate().isEqual(fin))){
                filtered.add(l);
            }
        }
        
        return filtered;
    }
    

}
