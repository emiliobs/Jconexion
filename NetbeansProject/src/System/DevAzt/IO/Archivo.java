package System.DevAzt.IO;

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
 * Esta clase esta desarrollada para el manejo de archivos, estos archivos se
 * han creado para la configuracion de la base de datos, el archivo es guardado
 * en .dll... para que algun usuario semiexperto, no modifique la configuracion
 *
 * @author Nekszer
 * @version JConexionDB 1.2
 * @since JConexionDB 1.0
 */
public class Archivo {

    private File f;
    private File directorio;
    private String ruta = "configuraciones//";
    private String folder = "configuraciones";

    /**
     * Este metodo recibe el texto a grabar o escribir en un archivo llamado
     * bdconfiguracion
     *
     * @param texto Texto a escribir en el archivo
     * @since JConexionDB 1.2
     */
    public void escribir(String texto) {
        f = new File(ruta + "bdconfiguracion.dll");
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
     * Este metodo lee el archivo de configuraciones de la base de datos
     * "bdconfiguracion.ini"
     *
     * @return una linea con la configuracion [bd;usuario;contraseña]
     * @since JConexionDB 1.2
     */
    public String leerArchivo() {
        f = new File(ruta + "bdconfiguracion.dll");
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
     * Este metodo puede borra el archivo de bdconfiguracion.ini
     *
     * @version JConexionDB 1.2
     * @since JConexionDB 1.0
     */
    public void borrarArchivo() {
        f = new File(ruta + "bdconfiguracion.dll");
        f.delete();
    }

    /**
     * Este metodo revisa si el arrchivo bdconfiguracion.ini existe
     *
     * @return true o false
     * @version JConexionDB 1.2
     * @since JConexionDB 1.0
     */
    public boolean fileExist() {
        f = new File(ruta + "bdconfiguracion.dll");
        return f.exists();
    }

    /**
     * Este metodo lee la configuracion del bdconfiguracion.ini
     *
     * @param text Linea de la configuracion con la estructura
     * [bd;usuario;contraseña]
     * @param separator Caracter utilizado para separación de cadenas
     * @return ArrayList de tipo String con los datos
     * @version JConexionDB 1.1
     * @since JConexionDB 1.0
     */
    public ArrayList<String> leerConfiguracion(String text, String separator) {
        StringTokenizer st = new StringTokenizer(text, separator);

        ArrayList<String> config = new ArrayList();

        while (st.hasMoreTokens()) {
            config.add(st.nextToken());
        }

        return config;
    }

    /**
     * Genera el directorio que almacena las configuraciones, sus posibles
     * modificaciones en un futuro son amplias, tomar en cuenta su uso... <br>
     * Este metodo fue creado para que nuestro ejecutable [.jar], pueda crear la
     * carpeta de configuraciones, la cual almacena la informacion de la base de
     * datos.
     * <br>
     * Directorio Dist o Directorio de nuestra app: <br>
     * &nbsp;Dist <br>
     * &nbsp;| <br>
     * &nbsp;| - lib <br>
     * &nbsp;&nbsp;| - jxl.jar <br>
     * &nbsp;&nbsp;| - mysql-connector-java-bin <br>
     * &nbsp;| - NombreProyecto.jar <br>
     * &nbsp;| - README.TXT <br>
     *
     * @version JConexionDB 1.2
     * @since JConexionDB 1.2
     */
    public void crearDir() {
        directorio = new File(folder);
        directorio.mkdir();
    }

    /**
     * Abre un fichero, especificando :
     *
     * @param ruta Direccion completa o relativa
     * @param nombre Nombre del fichero a abrir
     * @param extencion Tipo de extencion
     * @version JConexionDB 1.2.1
     * @since JConexionDB 1.2.1
     */
    public void openFichero(String ruta, String nombre, String extencion) {
        try {
            File path = new File(ruta + nombre + extencion);
            Desktop.getDesktop().open(path);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}
