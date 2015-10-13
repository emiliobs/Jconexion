package System.DevAzt.GUI;

import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;


/**
 * Ventana para poder guardar ficheros
 * @version 1.0
 * @author www.codejava.net
 * @deprecated 
 */
public class SaveFile{

    JFileChooser fileChooser;

    public SaveFile(){
        this.dialog();
    }
    
    public final void dialog(){
        
        fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Especifica el nombre del fichero...");   

        //FileFilter ff = (FileFilter) new ExtensionFile("JPG and JPEG", new String[] { "JPG", "JPEG" });
        
        //fileChooser.setFileFilter((javax.swing.filechooser.FileFilter) ff);
        
        int userSelection = fileChooser.showSaveDialog(null);
        
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File Save = fileChooser.getSelectedFile();
            
            String s1 = Save.getAbsolutePath();
            File f = new File(s1);
            
            int sobreEscrivir;
            
            if(f.exists()){
                //show dialog sobre escrivir
                sobreEscrivir = JOptionPane.showConfirmDialog(
                        null, 
                        "¿Desea sobre escribir?", 
                        "El fichero ya existe", 
                        JOptionPane.YES_NO_OPTION
                );
                
                switch (sobreEscrivir) {
                    case JOptionPane.YES_OPTION:
                        
                        break;
                    case JOptionPane.NO_OPTION:
                        
                        break;
                }
                
            }else{
                //escribimos
            }
            
        }else{
            
        }
    }
    
}
