/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package System.Settings;

import javax.swing.JOptionPane;

/**
 * Esta clase gestiona todos los mensajes que son mostrados en este framework
 * esta optimizada de tal manera que si el desarrollo es tipo GUI se utilizara
 * un JOptionPane para los mensajes, en cambio si se usa la terminal se mostraran
 * los errores con un System.err.println(""); si son mensajes de confirmacion
 * se mostrara en un System.out.println("");
 * @author Nekszer
 * @since JConexion 1.3.0
 */
public class MessageOption extends Options{

    //<editor-fold defaultstate="collapsed" desc="Tipo de desarrollo - Mode of Develop">
    /**
     * Lgica para saber el tipo de desarrollo de la aplicacion y el tipo de mensajes a mostrar
     * @return numero correspondiente al tipo de desarrollo <br>
     * 1 = GUI <br>
     * 2 = Terminal
     */
    private static int type() {
        String tipo = modeDevelop.toUpperCase();
        switch (tipo) {
            case "GUI":
                return 1;

            case "TERMINAL":
                return 2;

            default:
                return 0;
        }
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Mensajes de error - Errors message">
    /**
     * Metodo que define el tipo de salida del programa y si este ha de ser terminado o no.
     * @param i Tipo de salida = Mensaje a mostrar de acuerdo al tipo de desarrollo [GUI,Terminal]
     * @param finishApp determina si la aplicacion ha de ser terminada [True, False]
     */
    public static void exit(int i, boolean finishApp) {
        if (errors) {
            if (type() == 1) {
                guiMessage(i);
            } else {
                terminalMessage(i);
            }
        }
        
        if(finishApp){
            if(errors){
                System.exit(i);
            }else{
                System.exit(0);
            }
        }
    }

    /**
     * Define los mensajes que se mostraran en la terminal de acuerdo al tipo de salida MessageOption.exit();
     * @param i entero = tipo de mensaje
     */
    private static void terminalMessage(int i) {
        switch (i) {
            //Clase Conexion
            case 0:
                errorMessage("Error");
                break;

            case 1:
                errorMessage("Error en la configuracion de la base de datos, por favor revise el fichero dbconfiguracion.dll");
                break;
                
            case 2:
                errorMessage("Error en la conexión con la BD\n"
                        + "\tLine       => 144 System.DataBase.Core.Conexion.java\n"
                        + "\tVar        => []\n"
                        + "\tException  => ClassNotFoundException");
                break;
                
            case 3:
                errorMessage("La configuracion de la base de datos no es correcta... "
                    + "\nRevise los datos, configure e intente nuevamente.");
                break;
                
            // Clase datos
            case 4:
                errorMessage("No hay datos en la consulta");
                break;
            
            case 5:
                errorMessage(" --- No es posible obtener el dato --- \n" + 
                    "Posibles errores:\n" + 
                    "1 = No se ha realizado consulta\n" + 
                    "2 = Es posible que el valor a obtener esta fuera del limite [tupla,columna]");
                break;
                
            case 6:
                errorMessage(" --- No es posible obtener la tupla --- \n" + 
                    "Posibles errores:\n" + 
                    "1 = No se ha realizado consulta\n" + 
                    "2 = Es posible que la tupla a obtener esta fuera del limite [tupla]");
                break;
                
            // clase Upload
            case 50:
                errorMessage("Información corrupta");
                break;
                
            case 51:
                errorMessage("no se ha seleccionado archivo");
                break;
                
            case 52:
                errorMessage("Error al subir imagen");
                break;
                
            // clase main
            case 100:
                errorMessage("El modo de desarrollo asignado es incorrecto, asigna algun valor valido {Terminal o GUI} \n"
                        + "\tLine => 23 System.Settings.Options.java\n"
                        + "\tVar  => modeDevelop\n"
                        + "\tException  => IllegalArgumentException");
                break;
                
            case 101:
                errorMessage("El driver para manejar la base de datos no esta configurado correctamente {MySQL,Postgre,SQLSRV}\n"
                        + "\tLine       => 60 System.Settings.Options.java\n"
                        + "\tVar        => driver\n"
                        + "\tException  => IllegalArgumentException");
                break;
        }
    }
    
    /**
     * Define los mensajes que se mostraran en la interfaz grafica de acuerdo al tipo de salida MessageOption.exit();
     * @param i entero = tipo de mensaje
     */
    private static void guiMessage(int i){
        switch (i) {
            //Clase Conexion
            case 0:
                JOptionPane("Error al guardar registro\n");
                break;

            case 1:
                JOptionPane("Error en la configuracion de la base de datos, por favor revise el fichero dbconfiguracion.dll");
                break;
                
            case 2:
                JOptionPane("Error en la conexión con la BD");
                break;
                
            case 3:
                JOptionPane("La configuracion de la base de datos no es correcta... "
                    + "\nRevise los datos, configure e intente nuevamente.");
                break;
                
            // Clase Datos
            case 4:
                JOptionPane("No hay datos en la consulta");
                break;
                
            case 5:
                JOptionPane(" --- No es posible obtener el dato --- \n" + 
                    "Posibles errores:\n" + 
                    "1 = No se ha realizado consulta\n" + 
                    "2 = Es posible que el valor a obtener esta fuera del limite [tupla,columna]");
                break;
                
            case 6:
                JOptionPane(" --- No es posible obtener la tupla --- \n" + 
                    "Posibles errores:\n" + 
                    "1 = No se ha realizado consulta\n" + 
                    "2 = Es posible que la tupla a obtener esta fuera del limite [tupla]");
                break;
                
            // clase Upload
            case 50:
                JOptionPane("Información corrupta");
                break;
                
            case 51:
                JOptionPane("no se ha seleccionado archivo");
                break;
                
            case 52:
                JOptionPane("Error al subir imagen");
                break;
                
            // clase main
            case 100:
                JOptionPane("El modo de desarrollo asignado es incorrecto, asigna algun valor valido {Terminal o GUI} \n"
                        + "\tLine => 23 System.Settings.Options\n"
                        + "\tVar  => modeDevelop");
                break;
                
            case 101:
                JOptionPane("El driver para manejar la base de datos no esta configurado correctamente."
                        + "\tLine => 60 System.Settings.Options\n"
                        + "tVar  => driver");
                break;
        }
    }
    
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Mensajes afirmativos - Success message">
    
    /**
     * Define los mensajes afirmativos que se mostrara de acuerdo al tipo de desarrollo [GUI, Terminal]
     * @param i entero = tipo de mensaje
     */
    public static void success(int i) {
        if (success) {
            if (type() == 1) {
                successGUI(i);
            } else {
                successTerminal(i);
            }
        }
    }

    /**
     * Define los mensajes que se mostraran en la interfaz grafica de acuerdo al MessageOption.success;
     * @param i entero = tipo de mensaje
     */
    private static void successGUI(int i) {
        switch (i) {
            case 0:
                JOptionPane("Una fila afectada");
                break;
                
            case 1:
                JOptionPane("Accion completada");
                break;
                
            case 2:
                JOptionPane("Accion completada");
                break;
                
                
            // Index GUI
            case 100:
                /** ocupado  **/
                break;
        }
    }

    /**
     * Define los mensajes que se mostraran en la terminal de acuerdo al MessageOption.success;
     * @param i entero = tipo de mensaje
     */
    private static void successTerminal(int i) {
        switch (i) {
            case 0:
                successMessage("Una fila afectada");
                
            case 1:
                successMessage("Accion completada");
                break;
                
            // Index Terminal
            case 100:
                successMessage("Bienvenido a este pequeño framework desarrollado para manejar la BD en Java. \n"
                + "Welcome to small framework for managment DB in Java.");
                break;
        }
    }
    
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Tipos de mensaje">
    private static void JOptionPane(String message){
        JOptionPane.showMessageDialog(null, message);
    }
    
    private static void errorMessage(String message){
        System.err.println(message);
    }
    
    private static void successMessage(String message){
        System.out.println(message);
    }
    //</editor-fold>
}
