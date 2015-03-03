
package System.Helper;

import System.Settings.MessageOption;
import System.Settings.Options;
import java.awt.Component;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import javax.swing.JFileChooser;

/**
 * Esta clase ayuda al programador a subir ficheros [.txt,.jpg, etc..] a la
 * localizacion del proyecto con el motivo de poder asi guardar el fichero y
 * poder hacer uso de el en el sistema en modo de ejecucion.
 *
 * Por ejemplo, al hacer un login de usuario, si es posible personalizar el
 * avatar, esta clase permitira personalizar el avatar de la persona y cuando
 * esta haga login en el sistema, se recuperara su imagen.
 * @version 1.4.2
 * @since 1.4.2
 * @author Nekszer
 */
public class Upload {

    private String extension;
    private String nombre;
    private String ruta;
    private String max_size;
    private boolean sobre_escribir;
    private boolean encriptar;
    private boolean addFecha;

    /**
     * El constructor asigna la extension del fichero que se desea subir y el
     * nombre con el cual se desea guardar, esta clase sube ficheros dinamica-
     * mente, es posible subir el mismo fichero con el mismo nombre sin perderlo
     * ya que al final del nombre, se agrega la fecha en la que se subio el fichero.
     * @param extension String - nombre de la extension
     * @param name String - nombre del fichero
     * @since JConexion 1.4.2
     */
    public Upload(String extension, String name) {
        this.extension = extension;
        this.nombre = name;
    }

    private boolean setConfig;

    /**
     * Este metodo asigna las propiedades del fichero a subir
     * @param map Map String,String con la informacion del fichero
     * @since JConexion 1.4.2
     */
    public void setConfig(Map<String, String> map) {
        this.setConfig = true;
        for (Map.Entry<String, String> entrySet : map.entrySet()) {
            String key = entrySet.getKey();
            String value = entrySet.getValue();

            switch (key.toUpperCase()) {
                case "RUTA":
                    if (value.endsWith("//")) {
                        this.ruta = value;
                    } else {
                        this.ruta = value + "//";
                    }
                    break;

                case "MAX_SIZE":
                    if (value == null || value.equals("")) {
                        this.max_size = "2097152";
                    } else {
                        this.max_size = value;
                    }
                    break;

                case "SOBREESCRIBIR":
                    try {
                        this.sobre_escribir = Boolean.valueOf(value);
                    } catch (Exception ex) {
                        System.err.println("Información corrupta");
                    }
                    break;

                case "ENCRIPTAR":
                    try {
                        this.encriptar = Boolean.valueOf(value);
                    } catch (Exception ex) {
                        MessageOption.exit(50, false);
                    }
                    break;

                case "ADDFECHA":
                    try {
                        this.addFecha = Boolean.valueOf(value);
                    } catch (Exception ex) {
                        MessageOption.exit(50, false);
                    }
                    break;
            }
        }

    }

    /**
     * Este metodo asigna por defecto una configuracion en la cual se especifican
     * valores genericos como, el nombre de la carpeta donde se subiran los
     * ficheros, el tamaño maximo del fichero, si se puede sobre escribir, si 
     * es posible encriptar el fichero [nombre] y si se desea agregar la fecha
     * al fichero creado.
     * @since JConexion 1.4.2
     */
    private void setConfig() {
        Fichero f = new Fichero(".txt");
        f.mkdir("MediaUpload");
        this.ruta = "MediaUpload//";
        this.max_size = "2097152"; // 2MB en Bits
        this.sobre_escribir = false;
        this.encriptar = false;
        this.addFecha = true;
    }

    private File open;
    private boolean canUpload;

    /**
     * Este metodo despliega el JFileChooser para poder seleccionar el fichero
     * a subir
     * @param c - Component : indica de quien es el padre del JFileChooser este
     * se puede quedar en [null] si se desea.
     * @since JConexion 1.4.2
     */
    public void selectFile(Component c) {
        JFileChooser fc = new JFileChooser();
        fc.showOpenDialog(c);
        try {
            open = fc.getSelectedFile();
            String name = open.getName();
            if (!setConfig) {
                this.setConfig();
            }

            if (open.length() < Long.valueOf(this.max_size)) {
                canUpload = true;
            }
        } catch (Exception ex) {
            System.err.println("no se ha seleccionado archivo");
        }
    }

    /**
     * Este metodo realiza el upload del fichero seleccionado por el 
     * JFileChooser
     * @return String - path del fichero + nombre del fichero
     * @since JConexion 1.4.2
     */
    public String upload() {
        String urlImagen = "";
        if (canUpload) {
            try {
                FileInputStream origen = new FileInputStream(open);
                if (this.encriptar) {
                    urlImagen = ruta;
                    for (int i = 0; i < 3; i++) {
                        Integer numero = (int) (Math.random() * (Integer.MIN_VALUE + 10) + (Integer.MAX_VALUE - 10));
                        urlImagen += numero + "-";
                    }
                    if (this.addFecha) {
                        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
                        Date date = new Date();
                        urlImagen += dateFormat.format(date);
                    }
                } else {
                    if (this.addFecha) {
                        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
                        Date date = new Date();
                        urlImagen = ruta + nombre + dateFormat.format(date);
                    } else {
                        urlImagen = ruta + nombre;
                    }
                }
                urlImagen += extension;

                FileOutputStream destino = new FileOutputStream(urlImagen, true);
                int b = origen.read();
                while (b != -1) {
                    destino.write(b);
                    b = origen.read();
                }

                destino.flush();
                destino.close();
                origen.close();
            } catch (Throwable e) {
                System.err.println("Error" + e.getMessage());
            }
        } else {
            System.err.println("Error al subir imagen");
        }
        this.unsetData();
        return urlImagen;
    }

    /**
     * Este metodo formatea la clase a su estado inicial, para asi poder volver
     * a utilizar el metodo upload sin problemas.
     * @since JConexion 1.4.2
     */
    private void unsetData() {
        this.addFecha = false;
        this.canUpload = false;
        this.encriptar = false;
        this.max_size = "2097152";
        this.ruta = "MediaUpload//";
        this.setConfig = false;
        this.sobre_escribir = false;
    }
}
