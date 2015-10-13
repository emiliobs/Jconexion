
package System.DataBase.Core;

import System.DevAzt.IO.Archivo;
import System.Helper.Teclado;
import System.Settings.MessageOption;
import System.Settings.Options;

/**
 * Clase desarrollada para obtener una configuracion para nuestro sistema
 * la cual se almacenara en archivo dbconfiguracion.dll
 * @author nekszer
 * @version JConexionDB 1.3.0
 * @since JConexionDB 1.0
 */
public class ConfiguracionCMD {

    /**
     * Objeto para administrar la configuracion de la base de datos
     */
    private Archivo config = new Archivo();
    
    /**
     * Este objeto maneja la ejecucion de la consola [read line]
     */
    private Teclado in = new Teclado();
    
    /**
     * Obtiene la informacion de la consola y la guarda en el fichero
     * de la configuracion de la base de datos dbconfiguracion.dll
     */
    public ConfiguracionCMD() {
        String configuracion = this.getData();
        this.setConfiguracion(configuracion);
    }
    
    /**
     * Obtiene toda la informacion pertinente para conectar a la base de datos
     * @return String [cadena para el archivo dbconfiguracion.dll]
     */
    private String getData() {
        switch (Options.driver.toUpperCase()) {
            case "MYSQL":
                return configMySQL();
            
            case "SQLSRV":
                return configSQLSRV();
                
            case "POSTGRE":
                return configPostgre();
                
            default:
                MessageOption.exit(101, true);
                return "";
        }
    }
    
    /**
     * Este metodo genera el codigo standar para MySQL y PostgreSQL
     * @param bd - Url de la Base de datos
     * @return String con la configuracion de la base de datos
     */
    private String generateConfig(String bd){
        bd += in.getString("Ingresa base de datos\nejemplo: mybasededatos");
        System.out.println("");
        String user = in.getString("Ingresa el usuario de la base de datos");
        System.out.println(""); 
        String password = in.getAlfaNumeric("Ingresa contraseña de la base de datos\nNota: La contraseña sera visible al escribirla");
        String configuracion = bd + ";" + user + ";" + password;
        System.out.println("");
        return configuracion;
    }
    
    /**
     * Este metodo ejecuta el codigo para guardar la configuracion de la base de datos
     * @return String con la configuracion de la base de datos
     */
    private String configMySQL(){
        return generateConfig("jdbc:mysql://localhost/");
    }
    
    /**
     * Este metodo ejecuta el codigo para guardar la configuracion de la base de datos
     * @return String con la configuracion de la base de datos
     */
    private String configPostgre() {
        return generateConfig("jdbc:postgresql://127.0.0.1:5432/");
    }
    
    /**
     * Este metodo ejecuta el codigo para guardar la configuracion de la base de datos
     * @return String con la configuracion de la base de datos
     */
    private String configSQLSRV() {
        String servidor = "jdbc:sqlserver://";
        servidor += in.getAlfaNumeric("Ingresa el nombre del servidor\nejemplo: ATIVBOOK2");
        System.out.println("");
        servidor += ":1433;DatabaseName=";
        servidor += in.getAlfaNumeric("Ingresa el nombre de la base de datos\nEjemplo: JConexion");
        System.out.println("");
        String user = in.getString("Ingresa el usuario de la base de datos");
        System.out.println(""); 
        String password = in.getAlfaNumeric("Ingresa contraseña de la base de datos\nNota: La contraseña sera visible al escribirla");
        String configuracion = servidor + "*" + user + "*" + password;
        System.out.println("");
        return configuracion;
    }

    /**
     * Almacena un string como configuracion en el archivo dbconfiguracion.dll
     * @param configuracion String con la informacion para configurar la base de datos
     */
    private void setConfiguracion(String configuracion){
        config.escribir(configuracion);
        System.out.println(configuracion);
    }
}
