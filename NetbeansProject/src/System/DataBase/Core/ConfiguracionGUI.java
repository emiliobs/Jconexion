/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package System.DataBase.Core;

import System.Settings.MessageOption;
import System.Settings.Options;

/**
 *
 * @author nekszer
 */
public class ConfiguracionGUI {

    public ConfiguracionGUI() {
        getData();
    }
    
    /**
     * Ejecuta la ventana GUI para obtener toda la informacion pertinente para 
     * conectar a la base de datos.
     */
    private void getData() {
        switch (Options.driver.toUpperCase()) {
            case "MYSQL":
                configMySQL();
                break;
            
            case "SQLSRV":
                configSQLSRV();
                break;
                
            case "POSTGRE":
                configPostgre();
                break;
                
            default:
                MessageOption.exit(101, true);
        }
    }

    /**
     * Este metodo inicia la GUI para la configuración de MySQL
     */
    private void configMySQL() {
        MySQL mysql = new MySQL();
        mysql.setVisible(true);
        mysql.setLocationRelativeTo(null);
    }

    /**
     * Este metodo inicia la GUI para la configuración de SQL Server
     */
    private void configSQLSRV() {
        SQLSRV sqlsrv = new SQLSRV();
        sqlsrv.setVisible(true);
        sqlsrv.setLocationRelativeTo(null);
    }

    /**
     * Este metodo inicia la GUI para la configuración de PostgreSQL
     */
    private void configPostgre() {
        PostgreSQL postgre = new PostgreSQL();
        postgre.setVisible(true);
        postgre.setLocationRelativeTo(null);
    }
    
}
