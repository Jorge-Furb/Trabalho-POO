/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.facul.trabalhofinal;

/**
 *
 * @author Admin
 */
public class SanitizeFields {
    
    private SanitizeFields(){
        throw new UnsupportedOperationException("Impossivel criar objetos dessa classe");
    }
    
    public String descricao(String s){
        s=s.replace("\n", ". ");
        s=s.replace("\r", ". ");
        s=s.replace(";", "B===D"); // Voce sabia muito bem o que estava fazendo quando colocou isso na descricao...
        if(s.length()>=60){
           s=s.substring(0, 60);
        }
        
        return s;
    }
    public String categoria(String s){
        s=s.replace("\n", ". ");
        s=s.replace("\r", ". ");
        s=s.replace(";", "B===D");
        if(s.length()>=25){
           s=s.substring(0, 25);
        }
       
        return s;
    }
    public double valor(double v){
        v = Math.round(v*100)/100;
        return v;
    }
    
    
}
