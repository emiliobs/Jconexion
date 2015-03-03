/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package System.DevAzt.IO;

import java.awt.print.PrinterException;
import java.text.MessageFormat;
import javax.swing.JOptionPane;
import javax.swing.JTable;

/**
 * Clase para imprimir datos directamente hacia la impresora
 * @author Nekszer
 * @version JConexionDB 1.2
 * @since JConexionDB 1.2
 */
public class Impresora {
    
    /**
     * Objeto que recibe la tabla a imprimir
     */
    private JTable jConexionTable;

    /**
     * Recibe la jtable a imprimir
     * @param jConexionTable tabla a imprimir
     */
    public Impresora(JTable jConexionTable) {
        this.jConexionTable = jConexionTable;
    }

    /**
     * Este metodo imprime la tabla directamente a nuestras impresoras instaladas
     * @param Cabecera Titulo de nuestro documento
     * @param Pie Ejemplo: Numero de pagina
     * @version JConexionDB 1.2
     */
    public void imprimir(String Cabecera, String Pie) {
        
        MessageFormat header = new MessageFormat(Cabecera);
        MessageFormat footer = new MessageFormat(Pie +" {0,number,integer}");
        
        try {
            this.jConexionTable.print(JTable.PrintMode.FIT_WIDTH, header, footer);
        } catch (PrinterException e) {
            JOptionPane.showMessageDialog(null, "No es posible imprimir");
        }
    }

}
