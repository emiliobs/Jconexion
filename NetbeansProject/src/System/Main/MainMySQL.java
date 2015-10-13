/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package System.Main;

import System.Settings.Terminal;
import System.Settings.GUI;
import System.Settings.MessageOption;
import System.Settings.Options;

/**
 *
 * @author Nekszer
 */
public class MainMySQL {

    public static void main(String[] args) {

        String modeDevelop = Options.modeDevelop.toUpperCase(); // gui GUI

        switch (modeDevelop) {
            case "TERMINAL":
                Terminal t = new Terminal();
                t.run();
                break;

            case "GUI":
                GUI gui = new GUI();
                gui.run();
                break;

            default:
                MessageOption.exit(100, true);
                break;
        }

    }

}
