/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Aplicacion.Controller;

import Aplicacion.Vistas.Index;
import System.MVC.Core.Controller;
import System.MVC.Core.IController;
import System.MVC.Core.IView;
import javax.swing.JComponent;

/**
 *
 * @author Nekszer
 */
public class IndexController extends Controller implements IController{
    
    public static final int btnExitApp = 0;
    
    @Override
    public void start(Object o) {
        getFrame().loadView(new Index());
    }

    @Override
    public void onClickFromUI(int actionId, IView view, JComponent... componentes) {
        switch (actionId) {
            // Hacemos uso del MVC y capturamos el evento desde la vista
            case IndexController.btnExitApp:
                // enviamos informacion a la vista
                getFrame().getView().onControllerResponse(actionId, "La aplicacion terminara");
                // terminamos la app
                System.exit(0);
                break;
        }
    }
}
