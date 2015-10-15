/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package System.MVC.Core;

import javax.swing.JFrame;

/**
 *
 * @author Nekszer
 */
public class View extends JFrame {

    /**
     * Instancia de la clase IController, utilizada para lanzar el evento
     * start y onClickFromUI
     */
    private IController controller;

    /**
     * Este método asigna un controller a esta View
     *
     * @param controller el controlador
     */
    public void setController(IController controller) {
        this.controller = controller;
    }

    /**
     * Obtiene el current controller para esta view
     *
     * @return IController
     */
    public IController getController() {
        return controller;
    }

    /**
     * Método para ejecutar al iniciar un JFrame
     *
     * @param args parametros para enviar como navegacion
     */
    public void onNavigatedTo(Object args) {
        
    }

    /**
     * Este método se ejecuta cuando se regresa de algun JFrame
     * @param args datos para enviar de regreso
     */
    public void onNavigatedFrom(Object args) {
        
    }
}
