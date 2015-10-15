package System.Settings;

import Aplicacion.Terminal.Index;
import System.DataBase.Core.Conexion;
import System.DevAzt.IO.Archivo;
import System.DataBase.Core.ConfiguracionCMD;
import System.DataBase.Core.DataBase;
import System.MVC.Core.Controller;
import System.MVC.Core.Frame;
import java.sql.Connection;

/**
 * Clase principal de la Appi, es recomendable trabajar sobre el archivo de
 * Aplicacion.Terminal llamado Index.java y asignarla como clase principal de su
 * proyecto
 *
 * @author Nekszer
 * @version JConexionDB 1.3.0
 * @since JConexionDB 1.3.0
 */
public class Terminal {

    public void run() {
        Archivo config = new Archivo();
        boolean b1 = true;
        config.crearDir();

        if (config.fileExist()) {
            if (validacion()) {
                Controller c = new Controller();
                c.setFrame(new Frame());
                c.getFrame().startController(new Index(), null);
            }
        } else {
            configuracion();
            System.out.println("Ejecute de nuevo el programa");
            System.exit(0);
        }
    }

    public static void configuracion() {
        ConfiguracionCMD c = new ConfiguracionCMD();
    }

    public boolean validacion(){
        Archivo config = new Archivo();
        DataBase d = new DataBase();
        Connection conection = DataBase.getConnection();
        if (Conexion.error) {
            config.borrarArchivo();
            return false;
        }
        return true;
    } 
    
}
