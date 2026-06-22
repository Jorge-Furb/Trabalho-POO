/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.facul.trabalhofinal;

/**
 *
 * @author Admin
 */
public class TrabalhoFinal {
    public static void main(String[] args) {
        CarregarDados c = new CarregarDados(); // Codigo comeca nessa classe
        for(Despesa e : c.getDespesas()){ //Teste pra ver se os objetos estao sendo iniciados corretamente
            System.out.println(e.toString());
        }
        for(String e : c.getCategoriasDespesas()){ //Teste pra ver se os objetos estao sendo iniciados corretamente
            System.out.println(e.toString());
        }
    }
}
