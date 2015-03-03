package System.Settings;

import Aplicacion.Terminal.Index;
import System.DevAzt.IO.Archivo;
import System.DataBase.Core.ConfiguracionCMD;

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
            Index index = new Index();
            if (index.validacion()) {
                index.run();
                System.exit(0);
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

}
