/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package System.DataBase.Core;

import System.Settings.Options;
import java.lang.reflect.Field;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 *
 * @author Nekszer
 */
public class ORM<T> {

    private DataBase db = new DataBase();
    
    public boolean insert(T instance) {
        Map<String, String> data = toMap(instance);
        String classname = instance.getClass().getSimpleName().toLowerCase();
        return db.insertar(classname, data);
    }
    
    public int lastInsertId(){
        db.excecuteQuery("SELECT last_insert_id() as lastid");
        String dato = db.getDato(0, 0);
        return Integer.parseInt(dato);
    }

    public boolean update(T instance) {
        Map<String, String> data = toMap(instance);
        String classname = instance.getClass().getSimpleName().toLowerCase();
        return db.update(classname, data, new String[]{idattribname, idattribvalue});
    }

    private String idattribvalue;
    private String idattribname;

    private Map<String, String> toMap(T instance) {
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
                } else {
                    data.put(campo, value);
                }
                if (value.equals("null")) {
                    value = null;
                }
                System.out.println(value);
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
