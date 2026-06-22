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
public class CarregarDados  {

    private static final String DIR = "src\\main\\java\\com\\facul\\Dados\\";
    private ArrayList<Receita> receitas = new ArrayList<>();
    private ArrayList<Despesa> despesas = new ArrayList<>();
    private ArrayList<String> categoriasDespesas = new ArrayList<>();
    private ArrayList<String> categoriasReceitas = new ArrayList<>();
    private File fileReceitas = new File(DIR+"receitas.csv");
    private File fileDespesas = new File(DIR+ "despesas.csv");
    private File fileCategoriasDespesas = new File(DIR+"categoriasDespesas.csv");
    private File fileCategoriasReceitas = new File(DIR+"categoriasReceitas.csv");
    
    // Carrega os dados do arquivo csv e da parse neles, poe tudo em objetos nessa arraylist receitas e despesas em arquivos separados
    // um monte desse codigo é guardrail, garantindo que o arquivo existe e segue a formatacao esperada ( mais ou menos, falta deixar mais robusto )
    //TODO deixar mais robusto
    public CarregarDados(){
        
        categoriasDespesas.add("Outras despesas"); //Carrega a categoria default pra memoria
        categoriasReceitas.add("Outras receitas"); 
        InputStreamReader sr = null;
        
        
        try {
            validateFiles(fileReceitas,fileDespesas,fileCategoriasDespesas,fileCategoriasReceitas);
            sr = new InputStreamReader(new FileInputStream(fileReceitas),StandardCharsets.UTF_8);
            carregaReceitas(sr);
            sr = new InputStreamReader (new FileInputStream(fileDespesas),StandardCharsets.UTF_8);
            carregaDespesas(sr);
            sr = new InputStreamReader (new FileInputStream(fileCategoriasDespesas),StandardCharsets.UTF_8);
            carregaCategoriasDespesas(sr);
            sr = new InputStreamReader (new FileInputStream(fileCategoriasReceitas),StandardCharsets.UTF_8);
            carregaCategoriasReceitas(sr);
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
    protected void validateFiles(File receita, File despesa, File categoriasDespesas, File categoriasReceitas){
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
            if(!(categoriasDespesas.exists()&&categoriasDespesas.isFile())){
                System.out.println("Arquivo categoriasDespesas nao encontrado");
                categoriasDespesas.getParentFile().mkdirs();
                categoriasDespesas.createNewFile();
                System.out.println("Arquivo categoriasDespesas.csv criado com sucesso ");
                
            }
            if(!(categoriasReceitas.exists()&&categoriasReceitas.isFile())){
                System.out.println("Arquivo categoriasDespesas nao encontrado");
                categoriasReceitas.getParentFile().mkdirs();
                categoriasReceitas.createNewFile();
                System.out.println("Arquivo categoriasReceitas.csv criado com sucesso ");
                
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
      protected void carregaCategoriasReceitas(InputStreamReader sr){
      
        try ( BufferedReader br = new BufferedReader(sr)){
     
            String line = br.readLine();
            while(line!=null){
              categoriasReceitas.add(line.trim().replace(";", ""));
            }
            
        } catch (IOException ex) {
            System.out.println("IOException em carregaCategorias");
            
        } 
     }
     protected void carregaCategoriasDespesas(InputStreamReader sr){
       
        try(BufferedReader br = new BufferedReader(sr)) {
        
            String line = br.readLine();
            while(line!=null){
              categoriasDespesas.add(line.trim().replace(";", ""));
            }
            
        } catch (IOException ex) {
            System.out.println("IOException em carregaCategorias");
            
        }  
     }
     
     protected void carregaDespesas(InputStreamReader sr){
        
       
        try( BufferedReader br = new BufferedReader(sr)) {
            if(br.readLine()==null){
               throw new RuntimeException("Erro, arquivo encontrado mas sem cabecallho");
               //TODO arrumar esse caso especifico
            }
            String line = br.readLine();
            while(line!=null){
              
              String descricao = ParseLine.parseDescricao(line);
              String categoria = ParseLine.parseCategoria(line);
              double valor = ParseLine.parseValor(line);
              LocalDate date = ParseLine.parseDate(line);
              despesas.add(new Despesa(descricao,categoria,valor,date));
              line = br.readLine();
            }
            
        } catch (IOException ex) {
            System.out.println("IOException em carregaReceitas");
            
        }
        
    }
    protected void carregaReceitas(InputStreamReader sr){
        
        
        try(BufferedReader br = new BufferedReader(sr)) {
            if(br.readLine()==null){
               throw new RuntimeException("Erro, arquivo encontrado mas sem cabecallho");
               //TODO arrumar esse caso especifico
            }
            String line = br.readLine();
            while(line!=null){
              
              String descricao = ParseLine.parseDescricao(line);
              String categoria = ParseLine.parseCategoria(line);
              double valor = ParseLine.parseValor(line);
              LocalDate date = ParseLine.parseDate(line);
              receitas.add(new Receita(descricao,categoria,valor,date));
              line = br.readLine();
            }
            
        } catch (IOException ex) {
            System.out.println("IOException em carregaReceitas");
            
        }

    }
    
    public ArrayList<String> getCategoriasDespesas() {
        return categoriasDespesas;
    }

    public ArrayList<String> getCategoriasReceitas() {
        return categoriasReceitas;
    }
    
    public ArrayList<Receita> getReceitas() {
        return receitas;
    }

    public ArrayList<Despesa> getDespesas() {
        return despesas;
    }

    public File getFileReceitas() {
        return fileReceitas;
    }

    public File getFileDespesas() {
        return fileDespesas;
    }

    public File getFileCategoriasDespesas() {
        return fileCategoriasDespesas;
    }

    public File getFileCategoriasReceitas() {
        return fileCategoriasReceitas;
    }

    

}