/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package System.Helper;

import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * @version 1.4.2
 * @since 1.4.2
 * @author nekszer
 */
public class Fichero {
    
    private File f;
    private File directorio;
    private String ruta = "configuraciones//";
    private String folder = "configuraciones";
    private String extension;

    /**
     * Inicializa la clase y agrega la extension para manejar ficheros del mismo tipo
     * @param extension - Extension del fichero
     */
    public Fichero(String extension) {
        this.extension = extension;
    }
    
    /**
     * Este metodo crea un fichero y asigna el texto a grabar o escribir [texto]
     * en un archivo dado [nombre]
     * @param texto Texto a escribir en el archivo
     * @param nombre Nombre de fichero 
     * @version 1.4.2
     * @since 1.4.2
     */
    public void touch(String texto, String nombre) {
        f = new File(nombre + this.extension);
        try {
            FileWriter w = new FileWriter(f);
            BufferedWriter bw = new BufferedWriter(w);
            PrintWriter wr = new PrintWriter(bw);
            wr.write(texto);
            wr.close();
            bw.close();
        } catch (IOException e) {
            
        }
    }
    
    /**
     * Este metodo crea un fichero en la carpeta especificada [folder] y asigna 
     * el texto a grabar o escribir [texto] en un archivo dado [nombre]
     * @param folder Nombre de la carpeta
     * @param texto Texto a escribir en el archivo
     * @param nombre Nombre de fichero 
     * @version 1.4.2
     * @since 1.4.2
     */
    public void touch(String folder, String texto, String nombre){
        f = new File(folder + "//" + nombre + this.extension);
        try {
            FileWriter w = new FileWriter(f);
            BufferedWriter bw = new BufferedWriter(w);
            PrintWriter wr = new PrintWriter(bw);
            wr.write(texto);
            wr.close();
            bw.close();
        } catch (IOException e) {
            
        }
    }

    /**
     * Este metodo lee un archivo de una carpeta [folder] llamado [nombre]
     * @param nombre - Nombre del fichero
     * @return una linea de texto con el texto que contiene el fichero
     * @version 1.4.2
     * @since 1.4.2
     */
    public String cat(String nombre) {
        f = new File(nombre + this.extension);
        BufferedReader entrada;
        String linea = "";
        try {
            entrada = new BufferedReader(new FileReader(f));
            while (entrada.ready()) {
                linea = entrada.readLine();
            }
            entrada.close();
        } catch (IOException e) {
            
        }
        return linea;
    }
    
    /**
     * Este metodo lee un archivo de una carpeta [folder] llamado [nombre]
     * @param folder - Nombre de la carpeta
     * @param nombre - Nombre del fichero
     * @return una linea de texto con el texto que contiene el fichero
     * @version 1.4.2
     * @since 1.4.2
     */
    public String cat(String folder, String nombre) {
        f = new File(folder + "//" + nombre + this.extension);
        BufferedReader entrada;
        String linea = "";
        try {
            entrada = new BufferedReader(new FileReader(f));
            while (entrada.ready()) {
                linea = entrada.readLine();
            }
            entrada.close();
        } catch (IOException e) {
            
        }
        return linea;
    }

    /**
     * Este metodo puede borra un fichero [nombre]
     * @nombre Nombre del fichero a borrar
     * @version 1.4.2
     * @since 1.4.2
     */
    public void rm(String nombre) {
        f = new File(this.ruta + nombre + this.extension);
        f.delete();
    }
    
    /**
     * Este metodo puede borra un fichero [nombre] de una carpeta especifica [folder]
     * @param folder Nombre del folder en el que se buscara el fichero
     * @param nombre Nombre del fichero a borrar
     * @version 1.4.2
     * @since 1.4.2
     */
    public void rm(String folder, String nombre) {
        f = new File(folder + "//" + nombre + this.extension);
        f.delete();
    }

    /**
     * Este metodo revisa si un archivo existe [nombre]
     * @param nombre nombre del fichero
     * @return true o false
     * @version 1.4.2
     * @since 1.4.2
     */
    public boolean Exist(String nombre) {
        f = new File(this.ruta + nombre + this.extension);
        return f.exists();
    }

    /**
     * Este metodo lee un archivo y separa la cadena que contiene utilizando un
     * tipo de caracter especificado por el usuario
     * @param s1 Texto a separar
     * @param caracter caracter para dividir el texto
     * @return ArrayList de tipo String con los datos
     * @version JConexionDB 1.4.2
     * @since JConexionDB 1.4.2
     */
    public ArrayList<String> toKenizer(String s1, String caracter) {
        StringTokenizer st = new StringTokenizer(s1, caracter);
        ArrayList<String> config = new ArrayList();
        while (st.hasMoreTokens()) {
            config.add(st.nextToken());
        }
        return config;
    }

    /**
     * Este metodo crea un directorio en la raiz del proyecto
     * <br>
     * Directorio Dist o Directorio de nuestra app: <br>
     * &nbsp;Dist <br>
     * &nbsp;| <br>
     * &nbsp;| - lib <br>
     * &nbsp;&nbsp;| - jxl.jar <br>
     * &nbsp;&nbsp;| - mysql-connector-java-bin.jar <br>
     * &nbsp;&nbsp;| - swingx-all-1.6.4.jar <br>
     * &nbsp;| - NombreProyecto.jar <br>
     * &nbsp;| - README.TXT <br>
     *
     * @param nombre Nombre de la carpeta a crear
     * @version JConexionDB 1.2
     * @since JConexionDB 1.2
     */
    public void mkdir(String nombre) {
        directorio = new File(nombre);
        if(!directorio.mkdir()){
            System.err.println("Error al crear el fichero");
        }
    }
    
    /**
     * Este metodo crea un directorio en la raiz del proyecto
     * <br>
     * Directorio Dist o Directorio de nuestra app: <br>
     * &nbsp;Dist <br>
     * &nbsp;| <br>
     * &nbsp;| - lib <br>
     * &nbsp;&nbsp;| - jxl.jar <br>
     * &nbsp;&nbsp;| - mysql-connector-java-bin.jar <br>
     * &nbsp;&nbsp;| - swingx-all-1.6.4.jar <br>
     * &nbsp;| - NombreProyecto.jar <br>
     * &nbsp;| - README.TXT <br>
     * @param folder nombre del folder
     * @param nombre Nombre de la carpeta a crear
     * @version JConexionDB 1.2
     * @since JConexionDB 1.2
     */
    public void mkdir(String folder, String nombre) {
        directorio = new File(folder +"//"+nombre);
        directorio.mkdir();
    }

    /**
     * Inicia un fichero con base en los siguientes parametros
     * @param ruta Direccion completa o relativa
     * @param nombre Nombre del fichero a abrir
     * @param extencion Tipo de extencion
     * @version JConexionDB 1.2.1
     * @since JConexionDB 1.2.1
     */
    public void launchFile(String ruta, String nombre, String extencion) {
        try {
            java.io.File path = new java.io.File(ruta + nombre + extencion);
            Desktop.getDesktop().open(path);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
}
