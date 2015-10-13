/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package System.DevAzt.IO;

import java.awt.Desktop;
import java.io.*;
import javax.swing.*;
import jxl.*;
import jxl.write.*;
import System.DataBase.Core.Conexion;

/**
 * Clase desarollada para importar una jtable a un fichero de excel
 * 
 * @version 1.2
 * @since 1.1
 * @author nekszer
 */
public class ExportXLS {

    private File file;
    private JTable table;
    private String nombreTab;

    /**
     * constructor de la clase ExportXLS, con el cual se instancia un nuevo objeto
     * con los siguientes parametros:
     * @param table tabla a exportar a XLS
     * @param file  nombre del fichero con el que se guardara el xls
     * @param nombreTab nombre de la tabla resultante en excel
     */
    public ExportXLS(JTable table, File file, String nombreTab) {
        this.file = file;
        this.table = table;
        this.nombreTab = nombreTab;
    }

    /**
     * Este metodo exporta el JTable a Excel, para poder usarlo es necesario asignar
     * los datos correspondientes en el constructor de la clase
     * @return true si se ha realizado el export correctamente, false en caso contrario
     */
    public boolean export() {

        try {
            //Nuestro flujo de salida para apuntar a donde vamos a escribir
            DataOutputStream out = new DataOutputStream(new FileOutputStream(file));

            //Representa nuestro archivo en excel y necesita un OutputStream para saber donde va locoar los datos
            WritableWorkbook w = Workbook.createWorkbook(out);

            //Como excel tiene muchas hojas esta crea o toma la hoja
            //Coloca el nombre del "nombreTab" y el indice de la hoja
            WritableSheet s = w.createSheet(nombreTab, 0);
            
            //agregamos los datos de las columnas al archivo de excel
            for (int i = 0; i < Conexion.nombreColumnas.size(); i++) {
                s.addCell(new Label(i+1, 1, Conexion.nombreColumnas.get(i)));
            }
            
            //agregamos los datos a cada fila columna de la hoja en excel
            for (int i = 0; i < table.getColumnCount(); i++) {
                for (int j = 0; j < table.getRowCount(); j++) {
                    Object objeto = table.getValueAt(j,i);
                    s.addCell(new Label(i+1, j+2, String.valueOf(objeto)));
                }
            }
            
            //escribimos el archivo de xls
            w.write();

            //Cerramos el WritableWorkbook y DataOutputStream
            w.close();
            out.close();
            
            //si todo sale bien salimos de aqui con un true :D
            return true;
        } catch (IOException ex) {
            //exception en caso de crear el fichero .xls
            ex.printStackTrace();
        } catch (WriteException ex) {
            //exception en caso de no poder escribir en el fichero
            ex.printStackTrace();
        }

        //Si llegamos hasta aqui algo salio mal
        return false;
    }

}
