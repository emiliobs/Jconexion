/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package System.MVC.Core;

import java.util.EmptyStackException;
import java.util.Stack;
import javax.swing.JFrame;

/**
 *
 * @author Nekszer
 */
public class Frame {
    
    /**
     * Asignamos la view actual
     */
    private View currentView;
    
    /**
     * Este objeto almacena al JFrame actual
     */
    private JFrame currentFrame;
    
    /**
     * Este objeto almacena los JFrame a los que se ha navegado
     */
    private final Stack<JFrame> backFrameStack = new Stack<>();

    private Stack<JFrame> getBackFrameStack() {
        return backFrameStack;
    }
    
    private Object getObject() {
        return object;
    }

    private void setObject(Object object) {
        this.object = object;
    }

    private void setCurrentFrame(JFrame currentFrame) {
        this.currentFrame = currentFrame;
    }

    /**
     * Devuelve la vista actual
     * @return 
     */
    public IView getView(){
        return (IView) getCurrentView();
    }
    
    private JFrame getCurrentFrame() {
        return currentFrame;
    }

    private View getCurrentView() {
        return currentView;
    }

    private void setCurrentView(View currentView) {
        this.currentView = currentView;
    }

    private Controller getCurrentController() {
        return currentController;
    }

    private void setCurrentController(Controller currentController) {
        this.currentController = currentController;
    }
    
    /**
     * Este método carga inicia un jframe
     * @param frame instancia del nuevo JFrame
     */
    public void loadView(JFrame frame) {
        try {
            if(getCurrentFrame() != null){
                String title = getCurrentFrame().getTitle();
                getCurrentFrame().setVisible(false);
            }
            frame.setVisible(true);
            setCurrentView((View) frame);
            getCurrentView().setController((IController) getCurrentController());
            pushFrame(frame);
            ((View) frame).onNavigatedTo(getObject());
            setObject(null);
            setCurrentFrame(frame);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
    
    /**
     * Este objeto almacena al controlador actual asignado en el frame
     */
    private Controller currentController;
    
    /**
     * Este método inicia un controllador, y en automatico, realiza el método start
     * del controllador al que se llama
     * @param controller instancia del controllador
     * @param o objecto para enviar como parametro
     */
    public void startController(Controller controller, Object o){
        setCurrentController(controller);
        controller.setFrame(this);
        ((IController)getCurrentController()).start(o);
    }

    /**
     * Esté metodo realiza la comprobacion de, si es posible o no, regresar
     * @return 
     */
    public boolean canGoBack() {
        return !getBackFrameStack().isEmpty();
    }

    /**
     * Este método realiza la funcion de back de un frame
     * @throws EmptyStackException 
     */
    public void goBack() throws EmptyStackException {
        JFrame frame = popFrame(); // cachamos el frame actual
        frame.setVisible(false); // ocultamos el frame actual
        JFrame peek = getBackFrameStack().peek();// cachamos el frame actual
        peek.setVisible(true); // hacemos visible al frame actual
        setCurrentView((View) peek); // asignamos la nueva vista
        getCurrentView().onNavigatedFrom(getObject()); // lanzamos el evento onNavigatedFrom
        setCurrentFrame(peek); // asignamos el frame actual
        setObject(null); // seteamos al objeto a null
    }

    /**
     * Objecto para ser enviado como parametro a la vista
     */
    private Object object = null;

    /**
     * Con este método puedes mandar datos entre una vista y otra.
     * Antes de cargar la vista get_frame().load_view(new View());
     * puedes usar get_frame().put_object(new Object());
     * y cacharlo en el metodo onNavigatedTo(Object o); del JFrame al cual
     * navegas
     * @param object
     */
    public void putObject(Object object) {
        setObject(object);
    }

    /**
     * Este método realiza un insert al backStackFrame
     * @param back JFrame
     */
    private void pushFrame(JFrame back) {
        getBackFrameStack().push(back);
    }
    
    /**
     * Este método obtiene el ultimo JFrame insertado
     * @return JFrame
     */
    private JFrame popFrame(){
        return getBackFrameStack().pop();
    }
}
