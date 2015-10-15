/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package System.MVC.Core;

import javax.swing.JComponent;

/**
 *
 * @author Nekszer
 */
public interface IController {
    
    /**
     * Este método realiza el inicio de un nuevo controllador, al mismo tiempo, envia como parametro
     * un objeto el cual puede ser de cualquier tipo
     * @param o objeto como parametro
     */
    public void start(Object o);

    /**
     * Este método sera utilizado para realizar peticiones desde la vie hacia el controllador
     * @param actionId numero que identifica al evento 
     * @param view vista que realiza el evento
     * @param componentes componentes GUI de la view como args
     */
    public void onClickFromUI(int actionId, IView view, JComponent... componentes);
    
}
