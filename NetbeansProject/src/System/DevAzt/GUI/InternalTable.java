package System.DevAzt.GUI;

import System.DevAzt.IO.Imagen;
import System.DevAzt.IO.Impresora;
import System.DevAzt.IO.Archivo;
import System.Helper.IO;
import java.io.File;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import System.Settings.JConexion;
import System.DevAzt.IO.ExportXLS;
import System.DataBase.Core.Conexion;
import System.DataBase.Core.DataBase;

/**
 *
 * @author Daniel
 */
public class InternalTable extends javax.swing.JInternalFrame {

    private DefaultTableCellRenderer Alinear = new DefaultTableCellRenderer();
    private DefaultTableModel tableModel = new DefaultTableModel();

    private ArrayList matriz = new ArrayList();
    private Archivo file = new Archivo();
    private String SQL;
    private Conexion c;

    boolean tablaEditable = false;

    public InternalTable() {
        initComponents();
        this.tabla.setEnabled(tablaEditable);
        this.tabla.setAutoCreateRowSorter(true);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane2 = new javax.swing.JScrollPane();
        tabla = new org.jdesktop.swingx.JXTable();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        menuNew = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        menuPrint = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        jMenu3 = new javax.swing.JMenu();
        exportXLS = new javax.swing.JMenuItem();
        exportImg = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jCheckBoxMenuItem1 = new javax.swing.JCheckBoxMenuItem();
        jMenu4 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();

        setClosable(true);

        tabla.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "JConexion", "JConexion", "JConexion", "JConexion", "JConexion", "JConexion", "JConexion", "JConexion", "JConexion", "JConexion", "JConexion", "JConexion", "JConexion", "JConexion", "JConexion", "JConexion", "JConexion", "JConexion", "JConexion", "JConexion", "JConexion"
            }
        ));
        jScrollPane2.setViewportView(tabla);

        jMenu1.setText("Archivo");

        menuNew.setText("Nuevo");
        jMenu1.add(menuNew);
        jMenu1.add(jSeparator2);

        menuPrint.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_P, java.awt.event.InputEvent.CTRL_MASK));
        menuPrint.setText("Imprimir");
        menuPrint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuPrintActionPerformed(evt);
            }
        });
        jMenu1.add(menuPrint);
        jMenu1.add(jSeparator1);

        jMenu3.setText("Guardar como ...");

        exportXLS.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_X, java.awt.event.InputEvent.CTRL_MASK));
        exportXLS.setText("Excel [.xls]");
        exportXLS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exportXLSActionPerformed(evt);
            }
        });
        jMenu3.add(exportXLS);

        exportImg.setText("Imagen [.png]");
        exportImg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exportImgActionPerformed(evt);
            }
        });
        jMenu3.add(exportImg);

        jMenu1.add(jMenu3);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Edit");

        jCheckBoxMenuItem1.setText("Editar Tabla");
        jCheckBoxMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxMenuItem1ActionPerformed(evt);
            }
        });
        jMenu2.add(jCheckBoxMenuItem1);

        jMenu4.setText("Opciones");

        jMenuItem1.setText("Refrescar");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem1);

        jMenu2.add(jMenu4);

        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 1038, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void menuPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuPrintActionPerformed

        Impresora impresora = new Impresora(this.tabla);
        impresora.imprimir(JConexion.informacion, "Pagina");
        
    }//GEN-LAST:event_menuPrintActionPerformed

    private void exportXLSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exportXLSActionPerformed

        String nombre = JOptionPane.showInputDialog(null, "Ingresa nombre del archivo");
        String extencion = ".xls";
        ExportXLS xls = new ExportXLS(this.tabla, new File(nombre + extencion), JConexion.informacion);

        if (xls.export()) {
            file.openFichero("", nombre, extencion);
        }

    }//GEN-LAST:event_exportXLSActionPerformed

    private void exportImgActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exportImgActionPerformed

        String nombre = JOptionPane.showInputDialog(null, "Ingresa nombre del archivo");
        String extencion = ".png";
        Imagen img = new Imagen(tabla, tabla.getTableHeader(), nombre, extencion);

        if (img.export()) {
            file.openFichero("", nombre, extencion);
        }
    }//GEN-LAST:event_exportImgActionPerformed

    private void jCheckBoxMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxMenuItem1ActionPerformed
        if (this.jCheckBoxMenuItem1.isSelected()) {
            tablaEditable = true;
            this.tabla.setEnabled(tablaEditable);
        } else {
            tablaEditable = false;
            this.tabla.setEnabled(tablaEditable);
        }
    }//GEN-LAST:event_jCheckBoxMenuItem1ActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        this.actualizar();
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    public void actualizar() {
        this.SQL = Conexion.getSQL();
        DataBase db = new DataBase();
        this.matriz = db.excecuteQuery(SQL);
        IO.tabla_setDatos(matriz, tabla);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem exportImg;
    private javax.swing.JMenuItem exportXLS;
    private javax.swing.JCheckBoxMenuItem jCheckBoxMenuItem1;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    private javax.swing.JMenuItem menuNew;
    private javax.swing.JMenuItem menuPrint;
    public org.jdesktop.swingx.JXTable tabla;
    // End of variables declaration//GEN-END:variables
}
