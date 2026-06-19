/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.facul.trabalhofinal;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

/**
 *
 * @author Admin
 * 
 */
public abstract class ParseLine {
    private static String[] lineSplit;
    public static String parseDescricao(String g){
        lineSplit = g.split(";",4);
        if(lineSplit[0]==null||lineSplit[0].isBlank()){
            System.out.println("Parse error, retornando valor padrao");
            return "Sem descrição";
        }
        return lineSplit[0];
    }
    public static String parseCategoria(String g){
         lineSplit = g.split(";",4);
        if(lineSplit[0]==null||lineSplit[0].isBlank()){
            System.out.println("Parse error, retornando valor padrao");
            return "Sem categoria";
        }
        return lineSplit[1];
    }
    public static double parseValor(String g){
        lineSplit = g.split(";",4);
        try{
        return Double.parseDouble(lineSplit[2]);
        }catch(NumberFormatException e){
            System.out.println("Parse error, retornando -1.0");
            return -1.0;
        }
        
    }
    public static LocalDate parseDate(String g){
        lineSplit = g.split(";",4);
        try{
            return LocalDate.parse(lineSplit[3]);
        }catch(DateTimeParseException e){
            System.out.println("Parse error, retornando -1.0");
            return LocalDate.parse("0000-00-00");
        }
    }
}
