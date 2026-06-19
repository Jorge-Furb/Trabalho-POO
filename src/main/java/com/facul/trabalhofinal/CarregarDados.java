/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.facul.trabalhofinal;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 *
 * @author Admin
 */
public class CarregarDados {

    private static final String DIR = "src\\main\\java\\com\\facul\\Dados\\";
    private ArrayList<Receita> receitas = new ArrayList<>();
    private ArrayList<Despesa> despesas = new ArrayList<>();

    
    public CarregarDados(){
        
        InputStreamReader sr = null;
        File fileReceitas = new File(DIR+"receitas.csv");
        File fileDespesas = new File(DIR+ "despesas.csv");
        
        try {
            validateFiles(fileReceitas,fileDespesas);
            sr = new InputStreamReader(new FileInputStream(fileReceitas),StandardCharsets.UTF_8);
            carregaReceitas(sr);
            sr = new InputStreamReader (new FileInputStream(fileDespesas),StandardCharsets.UTF_8);
            carregaDespesas(sr);
        } catch (FileNotFoundException ex) {
            System.getLogger(CarregarDados.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        } finally {
            try {
                sr.close();
            } catch (IOException ex) {
                System.getLogger(CarregarDados.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
            }
        }
    }
    protected void validateFiles(File receita, File despesa){
        try{
            if (!(receita.exists()&&receita.isFile())){
                System.out.println("Arquivo receita nao encontrado");
                receita.getParentFile().mkdirs();
                receita.createNewFile();
                System.out.println("receitas.csv criado com sucesso");
                criarCabecalho(receita);
            }
            if(!(despesa.exists()||despesa.isFile())){
                System.out.println("Arquivo despesa nao encontrado");
                despesa.getParentFile().mkdirs();
                despesa.createNewFile();
                System.out.println("despesas.csv criado com sucesso");
                criarCabecalho(despesa);
            }
        }catch(IOException e){
                System.out.println("Erro ao criar o arquivo");
                
    }
        
    }
     private void criarCabecalho(File f){
         String cabecalho="Descrição;Categoria;Valor;Data";
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(f));
            bw.write(cabecalho);
            bw.newLine();
            bw.close();
        } catch (FileNotFoundException ex) {
            System.out.println("Arquivo nao encontrado ao tentar criar o cabecalho");
        }catch(IOException e){
            e.printStackTrace();
        }
         
     }
     protected void carregaDespesas(InputStreamReader sr){
        
        BufferedReader br = new BufferedReader(sr);
        try {
            if(br.readLine()==null){
               throw new RuntimeException("Erro, arquivo encontrado mas sem cabecallho");
               //TODO arrumar esse caso especifico
            }
            String line = br.readLine();
            while(line!=null){
              System.out.println(line);
              String descricao = ParseLine.parseDescricao(line);
              String categoria = ParseLine.parseCategoria(line);
              double valor = ParseLine.parseValor(line);
              LocalDate date = ParseLine.parseDate(line);
              despesas.add(new Despesa(descricao,categoria,valor,date));
              line = br.readLine();
            }
            
        } catch (IOException ex) {
            System.out.println("IOException em carregaReceitas");
            
        }finally{
            try {
                br.close();
            } catch (IOException ex) {
                System.getLogger(CarregarDados.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
            }
     }

    }
    protected void carregaReceitas(InputStreamReader sr){
        
        BufferedReader br = new BufferedReader(sr);
        try {
            if(br.readLine()==null){
               throw new RuntimeException("Erro, arquivo encontrado mas sem cabecallho");
               //TODO arrumar esse caso especifico
            }
            String line = br.readLine();
            while(line!=null){
              System.out.println(line);
              String descricao = ParseLine.parseDescricao(line);
              String categoria = ParseLine.parseCategoria(line);
              double valor = ParseLine.parseValor(line);
              LocalDate date = ParseLine.parseDate(line);
              receitas.add(new Receita(descricao,categoria,valor,date));
              line = br.readLine();
            }
            
        } catch (IOException ex) {
            System.out.println("IOException em carregaReceitas");
            
        }finally{
            try {
                br.close();
            } catch (IOException ex) {
                System.getLogger(CarregarDados.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
            }
     }

    }
    
    public ArrayList<Receita> getReceitas() {
        return receitas;
    }

    public ArrayList<Despesa> getDespesas() {
        return despesas;
    }

    

}