/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package System.Helper;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JButton;
import javax.swing.JComponent;

/**
 *
 * @author nekszer
 */
public class Frame {
    
    public static void setEnterKey(JComponent jtfset, JComponent to){
        jtfset.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent ke) {
                
            }

            @Override
            public void keyPressed(KeyEvent ke) {
                
            }

            @Override
            public void keyReleased(KeyEvent ke) {
                int keycode = ke.getKeyCode();
                if(keycode == 10){
                    to.requestFocus();   
                }
            }
        });
    }
    
    public static void buttonEnterKey(JButton btn){
        btn.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent ke) {
                
            }

            @Override
            public void keyPressed(KeyEvent ke) {
                
            }

            @Override
            public void keyReleased(KeyEvent ke) {
                int keycode = ke.getKeyCode();
                if(keycode == 10){
                    btn.doClick();
                }
            }
        });
    }
}
