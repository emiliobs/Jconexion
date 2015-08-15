/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package System.DataBase.Core;

import System.Settings.Options;
import java.lang.reflect.Field;
import java.util.ArrayList;

/**
 *
 * @author Nekszer
 */
public class ActiveRecord extends Conexion {

    public <T> ArrayList<T> excecuteQuery(String SQL, Class<T> clazz) {
        this.messageSQL(SQL);
        ArrayList list = super.consulta(SQL);
        ArrayList<T> objetos = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            ArrayList<String> elementos = new ArrayList<>();
            for (int j = 0; j < Conexion.nombreColumnas.size(); j++) {
                String element = getDato(i, j);
                elementos.add(element);
            }
            try {
                objetos.add(Instance.instantiate(elementos, clazz));
            } catch (Exception ex) {

            }
        }
        return objetos;
    }

    private void messageSQL(String SQL) {
        if (Options.showSQL) {
            System.out.println("\n" + SQL);
        }
    }

    public <T> ArrayList<T> Get(Class<T> clazz) {
        String SQL = "SELECT * FROM " + clazz.getSimpleName().toLowerCase();
        return this.excecuteQuery(SQL, clazz);
    }

    public <T> ArrayList<T> Get(Class<T> clazz, int start, int limit) {
        String SQL = "SELECT * FROM " + clazz.getSimpleName().toLowerCase() + " LIMIT " + start + "," + limit;
        return this.excecuteQuery(SQL, clazz);
    }
}


