/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package System.DataBase.Core;

import System.Settings.Options;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 *
 * @author Nekszer
 * @version 1.4.3
 * @since 1.4.2
 */
public class ActiveRecord {
    
    private DataBase db = new DataBase();
    
    /**
     * Este método realiza un Query y devuelve una lista con base al generic enlazado
     * @param <T> generic
     * @param SQL query
     * @param clazz T.class
     * @return List de T
     */
    public <T> ArrayList<T> excecuteQuery(String SQL, Class<T> clazz) {
        ArrayList list = db.excecuteQuery(SQL);
        ArrayList<T> objetos = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            ArrayList<String> elementos = new ArrayList<>();
            for (int j = 0; j < Conexion.nombreColumnas.size(); j++) {
                String element = db.getDato(i, j);
                elementos.add(element);
            }
            try {
                objetos.add(Instance.instantiate(elementos, clazz));
            } catch (Exception ex) {

            }
        }
        return objetos;
    }

    /**
     * Este método realiza un get de la tabla basada en el nombre de la clase T
     * @param <T> generic
     * @param clazz T.class
     * @return List T
     */
    public <T> ArrayList<T> get(Class<T> clazz) {
        String SQL = "SELECT * FROM " + clazz.getSimpleName().toLowerCase();
        return this.excecuteQuery(SQL, clazz);
    }

    /**
     * Este método realiza un get de la tabla basada en el nombre de la clase T usando un start, limit
     * @param <T> generic
     * @param clazz T.class
     * @param start idstart
     * @param limit numero de rows para obtener
     * @return List T
     */
    public <T> ArrayList<T> get(Class<T> clazz, int start, int limit) {
        String SQL = "SELECT * FROM " + clazz.getSimpleName().toLowerCase() + " LIMIT " + start + "," + limit;
        return this.excecuteQuery(SQL, clazz);
    }
    
    private String idattribvalue;
    private String idattribname;
    
    private <T> Map<String, String> toMap(T instance) {
        Field[] fields = instance.getClass().getDeclaredFields();
        Map<String, String> data = new LinkedHashMap();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                String campo = field.getName();
                String value = String.valueOf(field.get(instance));
                if (campo.toLowerCase().contains("id")) {
                    idattribvalue = value;
                    idattribname = campo;
                }
                if(value.equals("null")){
                    value = null;
                }
                data.put(campo, value);
            } catch (Throwable ex) {
                if (Options.errors) {
                    System.err.println("No es posible crear el SQL para este objeto");
                }
                return null;
            }
        }
        return data;
    }
}


