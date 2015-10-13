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
 * @since 1.4.2
 * @version 1.4.3
 */
public class ORM<T> {

    private String tableNameBelongsTo;
    private String tableNameHasOne;

    public void setTableNameBelongsTo(String tableNameBelongsTo) {
        this.tableNameBelongsTo = tableNameBelongsTo;
    }

    public String getTableNameBelongsTo() {
        return tableNameBelongsTo;
    }

    public void setTableNameHasOne(String tableNameHasOne) {
        this.tableNameHasOne = tableNameHasOne;
    }

    public String getTableNameHasOne() {
        return tableNameHasOne;
    }

    private String idColumnName;

    private Object idColumnValue;

    public <K> void setIdColumnValue(String idColumnName, K idColumnValue) {
        this.idColumnName = idColumnName;
        this.idColumnValue = idColumnValue;
    }

    public String getIdColumnName() {
        return idColumnName;
    }

    public <K> K getIdColumnValue() {
        return (K) idColumnValue;
    }

    private final DataBase db = new DataBase();

    private T clazz;

    public void setClass(T clazz) {
        this.clazz = clazz;
    }

    /**
     * Este método busca una fila en especifico para el tipo de clase T
     *
     * @param <T> tipo de clase
     * @param idvalue valor del id
     * @return devuelve un T [Generics] tipo de clase
     */
    public <T> T find(int idvalue) {
        String className;
        try {
            className = clazz.getClass().getSimpleName().toLowerCase();
        } catch (NullPointerException npe) {
            if (Options.errors) {
                System.err.println("El modelo no contiene un constructor\npublic ClassName(){\n\t super.setClass(this); \n}");
                System.err.println("Help url: https://github.com/nekszer/Jconexion/wiki/ORM-y-Active-Record-Java#orm-example-time");
            }
            return null;
        }
        Map<String, String> data = toMap(clazz);
        String SQL = "SELECT * FROM " + className + " WHERE " + idattribname + " = " + idvalue;
        db.excecuteQuery(SQL);
        try {
            data = db.row();
        } catch (Exception ex) {
            if (Options.success) {
                System.out.println("No hay datos en la consulta");
            }
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
            if (Options.errors) {
                System.err.println(ex.getMessage());
            }
        }
        return null;
    }

    /**
     * Este método realiza un insert generico para la clase que contiene al
     * método
     *
     * @return true si se hizo el insert | false si fallo
     */
    public boolean insert() {
        Map<String, String> data = toMap(clazz);
        String classname = clazz.getClass().getSimpleName().toLowerCase();
        return db.insertar(classname, data);
    }

    /**
     * Este método realiza un delete para la clase actual, y elimina sus valores
     *
     * @return true si se elimino la fila | false si fallo
     */
    public boolean delete() {
        Map<String, String> data = toMap(clazz);
        String classname = clazz.getClass().getSimpleName().toLowerCase();
        data.clear();
        data.put(idattribname, idattribvalue);
        if (db.delete(classname, data)) {
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
        } else {
            return false;
        }
    }

    /**
     * Este método realiza un update para la clase actual, realizando una
     * actualizacion de todos los campos utilizando el atributo id
     *
     * @return true si actualizo correctamente los datos | false si fallo
     */
    public boolean update() {
        Map<String, String> data = toMap(clazz);
        String classname = clazz.getClass().getSimpleName().toLowerCase();
        return db.update(classname, data, new String[]{idattribname, idattribvalue});
    }

    private String idattribvalue;
    private String idattribname;

    private <K> Map<String, String> toMap(K instance) {
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

    /**
     * Obtiene la relacion de uno a uno en la relationTable asignada ejemplo:
     * Clase hereda de ORM: | idtableName | value1 | value2 | | 15 | xd | otro |
     *
     * Clase enviada como parametro: | id | column | tableName | | 1 | lol | 15
     * |
     *
     * La union se hace a travez de idtableName y tableName
     *
     * @param <K> ClassName Type
     * @param relationTable ClassName.class
     * @return Instance of a table one to one
     */
    public <K> K hasOne(Class<K> relationTable) {
        K k = null;
        String relationTableName = relationTable.getSimpleName().toLowerCase();
        toMap(clazz);
        String idname = idattribname.replace("id", "");
        String idvalue = idattribvalue;
        if (getTableNameHasOne() != null && !getTableNameHasOne().equals("")) {
            relationTableName = getTableNameHasOne();
        }
        if (getIdColumnName() != null) {
            idname = getIdColumnName();
            idvalue = getIdColumnValue().toString();
        }
        setIdColumnValue(null, null);
        String SQL = "SELECT * FROM " + relationTableName + " WHERE " + idname + " = " + idvalue;
        db.excecuteQuery(SQL);
        Map<String, String> data = null;
        try {
            data = db.row();
        } catch (Exception ex) {
            if (Options.success) {
                System.out.println("No hay datos para esta consulta.");
            }
            return null;
        }
        ArrayList<String> list = new ArrayList();
        for (Map.Entry<String, String> entrySet : data.entrySet()) {
            String key = entrySet.getKey();
            String value = entrySet.getValue();
            list.add(value);
        }
        try {
            k = Instance.instantiate(list, relationTable);
        } catch (Exception ex) {
            if (Options.errors) {
                System.err.println(ex.getMessage());
            }
        }

        if (k == null) {
            if (Options.success) {
                System.out.println("No hay datos para esta consulta");
            }
        }
        return k;
    }

    /**
     * Este método realiza una busqueda de todos los elementos que pertencen al
     * id de la tableName ejemplo: Clase que hereda de ORM: | id | column |
     * tableName | | 1 | lol | 15 |
     *
     * Clase enviada como parametro: | idtableName | value1 | value2 | | 15 | xd
     * | otro |
     *
     * La union se hace a travez de idtableName y tableName
     *
     * @param <K> Type Class
     * @param tableName Class.class
     * @return Lista con todos los elementos que pertenecen a...
     */
    public <K> ArrayList<K> belognsTo(Class<K> tableName) {
        ArrayList<K> list = null;
        String searchIn = clazz.getClass().getSimpleName().toLowerCase();
        String attribnameTo = "";
        try {
            attribnameTo = tableName.getSimpleName();
            Map<String, String> data = toMap(clazz);
            String id = null;
            for (Map.Entry<String, String> entrySet : data.entrySet()) {
                String key = entrySet.getKey();
                String value = entrySet.getValue();
                if (key.equals(attribnameTo.toLowerCase())) {
                    id = value;
                }
            }
            String idcolumn = "id" + attribnameTo;
            String SQL = "SELECT * FROM ";
            if (getTableNameBelongsTo() != null && !getTableNameBelongsTo().equals("")) {
                attribnameTo = getTableNameBelongsTo();
            }
            SQL += attribnameTo.toLowerCase() + " WHERE ";
            if (getIdColumnName() != null) {
                idcolumn = getIdColumnName();
                id = getIdColumnValue().toString();
            }
            setIdColumnValue(null, null);
            SQL += idcolumn.toLowerCase() + " = " + id;
            ActiveRecord ar = new ActiveRecord();
            list = ar.excecuteQuery(SQL, (Class<K>) tableName);
        } catch (Exception e) {
            if (Options.errors) {
                System.err.println("El constructor de " + attribnameTo + " no contiene\npublic " + attribnameTo + "() {\n\tsuper.setClass(this); \n}");
            }
        }
        if (list != null || list.isEmpty()) {
            if (Options.success) {
                System.out.println("No hay datos para esta consulta");
            }
        }
        return list;
    }

    private String pivotIdName;
    private String pivotFromForeanKeyName;
    private String pivotToForeanKeyName;
    private boolean setPivotData;

    private boolean isSetPivotData() {
        return setPivotData;
    }

    public void setPivotData(String pivotIdName, String pivotFromForeanKeyName, String pivotToForeanKeyName) {
        this.pivotIdName = pivotIdName;
        this.pivotFromForeanKeyName = pivotFromForeanKeyName;
        this.pivotToForeanKeyName = pivotToForeanKeyName;
        setPivotData = true;
    }

    /**
     * Este método realiza un get de todos los elementos K type class
     * [tableName] que tiene esta tabla 
     * Relacion N - M [Muchos a muchos]
     *
     * @param <K> Type generic
     * @param tableName Class.class
     * @param pivotTableName Nombre de la tabla pivot
     * @return Lista de elementos Type Class
     */
    public <K> ArrayList<K> hasManyToMany(Class<K> tableName, String pivotTableName) {
        ArrayList<K> list = null;
        String from = clazz.getClass().getSimpleName().toLowerCase();
        pivotTableName = pivotTableName.toLowerCase();
        String to = "";
        toMap(clazz);
        // id,value from
        String idFrom = from + "." + idattribname;
        String valueFrom = idattribvalue;
        // id,value pivot
        String idPivot = pivotTableName + ".id" + pivotTableName;
        String pivotFromForeanKey = pivotTableName + "." + from;
        String pivotToForeanKey = pivotTableName + ".";
        if (isSetPivotData()) {
            this.setPivotData = false;
            idPivot = this.pivotIdName;
            pivotFromForeanKey = this.pivotFromForeanKeyName;
            pivotToForeanKey = this.pivotToForeanKeyName;
        }
        // id,value to
        String idTo = "";
        String valueTo = "";
        try {
            to = tableName.getSimpleName();
            pivotToForeanKey += to.toLowerCase(); //obtenemos el pivotToforeankey
            K k = tableName.newInstance();
            Map<String, String> attribstablename = toMap(k);
            idTo = to + "." + idattribname;
            valueTo = idattribvalue;
            int count = 0;
            int size = attribstablename.size() - 1;
            String SQL = "SELECT ";
            for (Map.Entry<String, String> entrySet : attribstablename.entrySet()) {
                String key = entrySet.getKey();
                String value = entrySet.getValue();
                SQL += to.toLowerCase() + "." + key;
                if (count < size) {
                    SQL += ",";
                }
                count++;
            }
            SQL += " FROM " + from;
            SQL += " INNER JOIN " + pivotTableName;
            SQL += " ON " + idFrom + " = " + pivotFromForeanKey;
            SQL += " INNER JOIN " + to.toLowerCase();
            SQL += " ON " + pivotToForeanKey + " = " + idTo;
            SQL += " WHERE " + idFrom + " = " + valueFrom;
            ActiveRecord ar = new ActiveRecord();
            list = ar.excecuteQuery(SQL, (Class<K>) tableName);
        } catch (Exception ex) {
            if (Options.errors) {
                System.err.println("El constructor de " + to + " no contiene\npublic " + to + "() {\n\tsuper.setClass(this); \n}");
            }
        }

        if (list == null || list.isEmpty()) {
            if (Options.success) {
                System.out.println("No hay datos para esta consulta");
            }
        }
        return list;
    }

    @Override
    public String toString() {
        Field[] fields = clazz.getClass().getDeclaredFields();
        String s = "{";
        for (Field field : fields) {
            s += "\n  " + field.getName() + ": ";
            try {
                field.setAccessible(true);
                s += String.valueOf(field.get(clazz));
            } catch (SecurityException | IllegalArgumentException | IllegalAccessException ex) {
                System.err.println(ex.getMessage());
            }
        }
        s += "\n}";
        return s;
    }

    /**
     * Este método realiza un get de la tabla [T Class].Name clase generica
     * @return Lista de elementos T Generic
     */
    public ArrayList<T> get() {
        return get(0, 30);
    }

    /**
     * Este método realiza un get de la tabla [T Class].Name clase generica
     * @param start inicio de la fila
     * @param limit numero de [T] objects a devolver
     * @return Lista de elementos T generic
     */
    public ArrayList<T> get(int start, int limit) {
        ActiveRecord ar = new ActiveRecord();
        return ar.get((Class<T>) clazz.getClass(), start, limit);
    }
}
