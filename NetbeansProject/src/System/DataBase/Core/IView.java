/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package System.DataBase.Core;

import javax.swing.JFrame;

/**
 *
 * @author Neksze
 */
public interface IView{
    
    /**
     * Response of controller to view [JFrame]
     * @param action constant for a get current action
     * @param object Object from controller
     */
    public void onResponseController(int action, Object object);
    
    /**
     * Método para ejecutar al iniciar un frame
     * @param back jframeanterior
     * @param args parametros para enviar como navegacion
     */
    public void onNavigated(JFrame back, Object args);
}
