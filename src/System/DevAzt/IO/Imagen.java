/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package System.DevAzt.IO;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.JTableHeader;

/**
 * Esta clase es creada con el motivo de importar la tabla a diferentes formatos
 * como son [JPG, PNG, etc...]
 *
 * @author Nekszer
 * @version JConexionDB 1.2.1
 * @since JConexionDB 1.1.9
 */
public class Imagen {

    private String nombre;
    private String extencion;
    private JTable table;
    private JTableHeader header;

    public Imagen(JTable table, JTableHeader header, String nombre, String extencion) {
        this.table = table;
        this.header = header;
        this.nombre = nombre;
        this.extencion = extencion;
    }
    
    /**
     * Convierte la tabla en imagen
     * @return true en caso exitoso de export, false en caso contrario
     */
    public boolean export() {
        boolean b1 = false;
        int w = Math.max(table.getWidth(), header.getWidth());
        int h = table.getHeight() + header.getHeight();
        BufferedImage bi = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = bi.createGraphics();
        header.paint(g2);
        g2.translate(0, header.getHeight());
        table.paint(g2);
        g2.dispose();
        try {
            ImageIO.write(bi, "png", new File(this.nombre + this.extencion));
            b1 = true;
        } catch (IOException ioe) {
            b1 = false;
        }
        
        return b1;
    }

}
