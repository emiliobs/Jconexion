package Aplicacion.Terminal;

import System.MVC.Core.Controller;
import System.MVC.Core.IController;
import System.MVC.Core.IView;
import System.Settings.MessageOption;
import javax.swing.JComponent;

/**
 * Clase desarrollada para manejar el controlador / vista principal para la
 * consola, aqui es donde usted, escribira su aplicacion, empezando por el
 * metodo run y tulizando como apoyo la clase, MainMySQL del package
 * system.test.terminal
 *
 * @author nekszer
 * @version 1.3.0
 * @since 1.3.0
 */
public class Index extends Controller implements IController{
    
    @Override
    public void start(Object o) {
        //<editor-fold defaultstate="collapsed" desc="Su codigo inicia aqui - You'r code start to here">
        //</editor-fold>
        MessageOption.success(100);
    }

    @Override
    public void onClickFromUI(int actionId, IView view, JComponent... componentes) {
        
    }
    
}
