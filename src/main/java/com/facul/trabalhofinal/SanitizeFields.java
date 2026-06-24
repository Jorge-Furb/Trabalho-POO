/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.facul.trabalhofinal;

/**
 * Filtra os dados de entrada do usuario para adequalos ao programa.
 * 
 * @author Jorge
 * @author Lucas
 */
public class SanitizeFields {
    
    private SanitizeFields(){
        throw new UnsupportedOperationException("Impossivel criar objetos dessa classe");
    }
    
    public static String descricao(String s){
        s=s.replace("\n", ". ");
        s=s.replace("\r", ". ");
        s=s.replace(";", "B===D"); // Voce sabia muito bem o que estava fazendo quando colocou isso na descricao...
        if(s.length()>=60){
           s=s.substring(0, 60);
        }
        
        return s.trim();
    }
    public static String categoria(String s){
        s=s.replace("\n", ". ");
        s=s.replace("\r", ". ");
        s=s.replace(";", "B===D");
        if(s.length()>=25){
           s=s.substring(0, 25);
        }
       
        return s.trim();
    }
    public static double valor(double v){
        v = Math.round(v*100.0)/100.0;
        return v;
    }
    
    
}
