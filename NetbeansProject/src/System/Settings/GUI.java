package System.Settings;

import Aplicacion.Controller.IndexController;
import Aplicacion.Vistas.Index;
import System.DataBase.Core.Conexion;
import System.DevAzt.IO.Archivo;
import System.DataBase.Core.ConfiguracionGUI;
import System.DataBase.Core.DataBase;
import System.MVC.Core.Controller;
import System.MVC.Core.Frame;
import java.sql.Connection;
import javax.swing.JOptionPane;

/**
 * Clase principal de la Appi, es recomendable trabajar sobre el archivo de
 * Aplicacion.vista llamado Index.java y asignarla como ventana principal de su
 * proyecto
 *
 * @author Nekszer
 * @version JConexionDB 1.3.0
 * @since JConexionDB 1.0
 */
public class GUI {
    
    public void run() {
        Archivo config = new Archivo();
        config.crearDir();
        if (config.fileExist()) {
            if (validacion()) {
                Controller c = new Controller();
                c.setFrame(new Frame());
                c.getFrame().startController(new IndexController(), null);
            }else{
                System.exit(0);
            }
        } else {
            configuracion();
        }
    }

    public static void configuracion() {
        ConfiguracionGUI gui = new ConfiguracionGUI();
    }
    
    private boolean validacion(){
        Archivo config = new Archivo();
        DataBase d = new DataBase();
        Connection conection = DataBase.getConnection();
        if (Conexion.error) {
            config.borrarArchivo();
            JOptionPane.showMessageDialog(null, "Vuelva a ejecutar el programa");
            return false;
        }
        return true;
    }
}
