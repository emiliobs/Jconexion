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
public interface IView {
 
    /**
     * Response of controller to view [JFrame]
     * @param action constant for a get current action
     * @param object Object from controller
     */
    public void onControllerResponse(int action, Object object);
    
}
