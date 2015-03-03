package System.Settings;

import Aplicacion.Vistas.Index;
import System.DevAzt.IO.Archivo;
import System.DataBase.Core.MySQL;
import System.DataBase.Core.ConfiguracionGUI;

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
            Index index = new Index();
            index.setVisible(true);
        } else {
            configuracion();
        }
    }

    public static void configuracion() {
        ConfiguracionGUI gui = new ConfiguracionGUI();
    }
}
