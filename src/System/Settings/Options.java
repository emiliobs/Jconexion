/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package System.Settings;

/**
 *
 * @author Nekszer
 */
public class Options {
    
    //<editor-fold desc="Opciones de esarrollo">
    
    /**
     * Define el modo de desarrollo
     * <br>
     * GUI = Utilizando Swing
     * <br>
     * TERMINAL = UTilizando la consola
     */
    public static final String modeDevelop = "GUI";

    /**
     * Define si los errores seran mostrados <br>
     * true = Programa en desarrollo <br>
     * false = Programa terminado
     */
    public static final boolean errors = true;

    /**
     * Define si se mostraran mensajes de exito en la insercion y eliminacion de registros
     * true = Se muestran mensajes <br>
     * false = No se muestran mensajes
     */
    public static final boolean success = false;
    
    //</editor-fold>
    
    //<editor-fold desc="Opciones de la base de datos">
    /**
     * Esta variable determina si se mostrara el codigo SQL generado por esta
     * clase en la terminal.
     * <br>
     * True = Mostrar en terminal el codigo SQL generado
     * <br>
     * False = No mostrar en terminal el codigo SQL generado
     */
    public static final boolean showSQL = true;
    
    /**
     * Este wrapper contiene el valor o el nombre del gestor de la base de datos
     * <br>
     * Gestores disponibles para JConexion
     * |- MySQL     => MySQL
     * |- SQLSRV    => SQL Server
     * |- Postgre   => PostgreSQL
     */
    public static final String driver = "MYSQL";
    
    //</editor-fold>
}
