
package Aplicacion.Vistas;

import java.sql.Connection;
import javax.swing.JOptionPane;
import System.DevAzt.IO.Archivo;
import System.DataBase.Core.Conexion;
import System.DataBase.Core.DataBase;

/**
 * Frame secundario del API, pero principal del sistema que se piensa desarrollar
 * @author Nekszer
 * @version 1.3.0
 * @since JConexionDB 1.0
 */
public class Index extends javax.swing.JFrame {

    /**
     * Este constructor contiene mucha informacion que no debe ser modficada
     * contiene las propiedades de la ventana [modificables]
     * pero apartir de errores se encuentran los elementos que hacen la funcion
     * de validar la configuracion
     * @since JConexionDB 1.0
     */
    public Index() {
        initComponents();
        setVisible(true);
        setTitle("Ventana principal del sistema");
        setLocationRelativeTo(null);
        this.configuracion();
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jSeparator2 = new javax.swing.JSeparator();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Bienvenido a este  pequeño framework desarrollado en Java para manejar base de datos");

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Welcome to small framework for managment DB in Java.");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 621, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addContainerGap(357, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JSeparator jSeparator2;
    // End of variables declaration//GEN-END:variables
    
    //<editor-fold defaultstate="collapsed" desc="Default Code For The Framework">
    private void configuracion(){
        Archivo config = new Archivo();
        DataBase d = new DataBase();
        Connection conection = DataBase.getConnection();
        if (Conexion.error) {
            config.borrarArchivo();
            JOptionPane.showMessageDialog(this, "Vuelva a ejecutar el programa");
            System.exit(0);
        }
    }
    //</editor-fold>
}


