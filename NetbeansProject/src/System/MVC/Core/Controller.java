/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package System.MVC.Core;

/**
 *
 * @author Nekszer
 */
public class Controller{
 
    /**
     * Instancia de un Frame
     */
    private Frame frame;
    
    /**
     * Asigna al frame que se utilizara para este controlador
     * @param frame 
     */
    public void setFrame(Frame frame) {
        this.frame = frame;
    }
    
    /**
     * Obtiene el frame con el cual se puede asignar una vista JFrame
     * @return
     */
    public Frame getFrame() {
        return frame;
    }
}
