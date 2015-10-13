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
 * @param <T> Type for ORM
 */
public class ORM<T> {

    private final DataBase db = new DataBase();

    private T clazz;

    public void setClass(T clazz){
        this.clazz = clazz;
    }
    
    private void messageSQL(String SQL) {
        if (Options.showSQL) {
            System.out.println("\n" + SQL);
        }
    }
    
    /**
     * Este método busca una fila en especifico para el tipo de clase T
     * @param <T> tipo de clase
     * @param idvalue valor del id
     * @return devuelve un T [Generics] tipo de clase
     */
    public <T> T find(int idvalue) {
        String className = clazz.getClass().getSimpleName().toLowerCase();
        Map<String, String> data = toMap(clazz);
        String SQL = "SELECT * FROM " + className + " WHERE " + idattribname + " = " + idvalue;
        db.excecuteQuery(SQL);
        ArrayList<T> objetos = new ArrayList<>();
        try{
            data = db.row();
        }catch(Exception ex){
            return null;
        }
        ArrayList<String> list = new ArrayList();
        for (Map.Entry<String, String> entrySet : data.entrySet()) {
            String key = entrySet.getKey();
            String value = entrySet.getValue();
            list.add(value);
        }
        try {
            return Instance.instantiate(list, (Class<T>) clazz.getClass());
        } catch (Exception ex) {
            if(Options.errors){
                System.err.println(ex.getMessage());
            }
        }
        return null;
    }

    /**
     * Este método realiza un insert generico para la clase que contiene al método
     * @return true si se hizo el insert | false si fallo
     */
    public boolean insert() {
        Map<String, String> data = toMap(clazz);
        String classname = clazz.getClass().getSimpleName().toLowerCase();
        return db.insertar(classname, data);
    }
    
    /**
     * Este método realiza un delete para la clase actual, y elimina sus valores
     * @return true si se elimino la fila | false si fallo
     */
    public boolean delete(){
        Map<String,String> data = toMap(clazz);
        String classname = clazz.getClass().getSimpleName().toLowerCase();
        data.clear();
        data.put(idattribname, idattribvalue);
        if(db.delete(classname, data)){
            Field[] fields = clazz.getClass().getDeclaredFields();
            for (Field field : fields) {
                try {
                    field.setAccessible(true);
                    field.set(clazz, null);
                } catch (SecurityException | IllegalArgumentException | IllegalAccessException ex) {
                    System.err.println(ex.getMessage());
                }
            }
            return true;
        }else{
            return false;
        }
    }
    
    /**
     * Este método realiza un update para la clase actual, realizando una actualizacion de todos los campos utilizando el atributo id
     * @return true si actualizo correctamente los datos | false si fallo
     */
    public boolean update() {
        Map<String, String> data = toMap(clazz);
        String classname = clazz.getClass().getSimpleName().toLowerCase();
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
                }
                if (value.equals("null")) {
                    value = null;
                }
                data.put(campo, value);
            } catch (SecurityException | IllegalArgumentException | IllegalAccessException ex) {
                if (Options.errors) {
                    System.err.println("No es posible crear el SQL para este objeto");
                }
                return null;
            }
        }
        return data;
    }

    @Override
    public String toString() {
        Field[] fields = clazz.getClass().getDeclaredFields();
        String s = "";
        for (Field field : fields) {
            s += "\n" + field.getName() + ": ";
            try {
                field.setAccessible(true);
                s += String.valueOf(field.get(clazz));
            } catch (SecurityException | IllegalArgumentException | IllegalAccessException ex) {
                System.err.println(ex.getMessage());
            }
        }
        return s;
    }

}
