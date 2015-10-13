/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package System.DataBase.Core;

import javax.swing.JComponent;
import javax.swing.JFrame;

/**
 * Clase creada para poder manejar el patron MVC en Java
 * para usar este modelo, es necesario crear controlladores, los cuales
 * deberan ser instanciados en la vista, para que de esta manera la vista pueda
 * utiliar los metodos, onClickSentUI y navigate
 * @version 1.4.3
 * @author Nekszer
 */
public interface IController {
    
    /**
     * Receive action from view to controller
     * Recibimos una accion desde una vista hacia el controllador
     * @param action 
     * @param frame
     * @param components 
     */
    public void onClickSentUI(int action, IView frame, JComponent... components);
    
    /**
     * Request para hacer una navegacion desde un frame, al invocarse recive
     * un frame [from] y un frame [to] al igual que parametros para ser enviados y
     * procesados antes de que el form inicie.
     * @param from
     * @param to
     * @param args 
     */
    public void navigate(JFrame from, JFrame to, Object args);
}
