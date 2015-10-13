
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
public class Index extends javax.swing.JFrame{
    
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

        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Welcome to JConexion");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 798, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addComponent(jLabel2)
                .addContainerGap(454, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel2;
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


