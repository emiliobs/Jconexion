package Aplicacion.Terminal;

import java.sql.Connection;
import System.DevAzt.IO.Archivo;
import System.Helper.Fichero;
import System.Helper.Multimap;
import System.DataBase.Core.Conexion;
import System.DataBase.Core.DataBase;
import System.Settings.MessageOption;
import System.Settings.Options;
import java.util.List;
import java.util.Map;

/**
 * Clase desarrollada para manejar el controlador / vista principal para la
 * consola, aqui es donde usted, escribira su aplicacion, empezando por el
 * metodo run y tulizando como apoyo la clase, MainMySQL del package
 * system.test.terminal
 *
 * @author nekszer
 * @version 1.3.0
 * @since 1.3.0
 */
public class Index {

    //<editor-fold defaultstate="collapsed" desc=" No elimine este codigo - You don't delete this code">
    public boolean validacion() {
        Archivo config = new Archivo();
        DataBase d = new DataBase();
        Connection conection = DataBase.getConnection();
        if (Conexion.error) {
            config.borrarArchivo();
            return false;
        }
        return true;
    }

    //</editor-fold>
    /**
     * Metodo a ejecutar cuando la aplicacion se inicia con la clase
     * Terminal
     */
    public void run() {
        //<editor-fold defaultstate="collapsed" desc="Su codigo inicia aqui - You'r code start to here">
        //</editor-fold>
        MessageOption.success(100);
    }
    
}
