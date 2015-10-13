package System.DataBase.Core;

import System.Settings.Options;
import java.util.ArrayList;
import java.util.Map;

/**
 * Clase desarrollada para manejar las consultas de nuestra base de datos, el uso
 * de esta clase es opcional, el metodo que si es necesario para hacer consultas
 * se llama excecuteQuery();
 * @author nekszer
 * @version 1.4.3
 * @since 1.3.0
 */
public class DataBase extends Conexion {

    public DataBase() {
        super();
    }

    //<editor-fold desc="General" defaultstate="collapsed">
    /**
     * Ejecuta cualquier sentencia de SQL en nuestro codigo
     *
     * @param SQL Sentencia en lenguaje SQL
     * @return ArrayList Multidimencional [Matriz] con la informacion de la
     * consulta en caso de error el valor sera null
     */
    public ArrayList excecuteQuery(String SQL) {
        this.messageSQL(SQL);
        return super.consulta(SQL);
    }

    private void messageSQL(String SQL) {
        if (Options.showSQL) {
            System.out.println("\n" + SQL);
        }
    }

    //</editor-fold>
    
    //<editor-fold desc="GET Table" defaultstate="collapsed">
    /**
     * Este metodo consulta en una tabla mandada como parametro
     *
     * @param tabla String [Nombre de la tabla]
     * @return ArrayList [matriz] con los datos de la consulta
     * @since 1.3.0
     */
    public ArrayList get(String tabla) {
        String SQL = "SELECT * FROM " + tabla;
        messageSQL(SQL);
        return super.consulta(SQL); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Obtiene toda la informacion de una relacion, enviada como parametro al
     * String tabla, estrableciendo un limite de inicio y un limite de fin
     *
     * @param tabla - Nombre de la tabla
     * @param limitStart - Numero limite inicio
     * @param limitEnd Numero limite final
     * @return ArrayList con la informacion obtenida de la tabla
     * @since 1.3.0
     */
    public ArrayList get(String tabla, int limitStart, int limitEnd) {
        String SQL = "SELECT * FROM " + tabla + " LIMIT " + limitStart + "," + limitEnd;
        messageSQL(SQL);
        return super.consulta(SQL); //To change body of generated methods, choose Tools | Templates.
    }

    //</editor-fold>
    
    //<editor-fold desc="Select [campos]" defaultstate="collapsed">
    /**
     * Este metodo recupera sierto numero de elementos, de una tabla la tabla
     * donde se hara la busqueda sera el primer parametro enviado como String -
     * Ejemplo [ select("tabla",new
     * String[]{"atributo1","atributo2","atributo3"}); ] especificados en el
     * parametro dos, un arreglo de string para cada atributo de la tabla
     * @since 1.3.0
     * @param tabla - nombre de la tabla en la cual se buscara informacion
     * @param campo - atributos a buscar
     * @return ArrayList [matriz] con los datos de la consulta
     * @since 1.3.3
     */
    public ArrayList select(String tabla, String[] campo) {
        String SQL = "SELECT ";
        SQL = this.setAtributos(SQL, campo);
        SQL += " FROM " + tabla;
        messageSQL(SQL);
        return super.consulta(SQL);
    }

    /**
     * Estemetodo generaliza la creacion de codigo SQL para asignar los
     * atributos a seleccionar
     * @since 1.3.0
     * @param SQL Previo codigo SQL
     * @param campo String[] con los campos que se han de buscar
     * @return String - codigo complementado de SQL
     * @since 1.3.1
     */
    private String setAtributos(String SQL, String[] campo) {
        for (int i = 0; i < campo.length; i++) {
            if (i == 0) {
                SQL += campo[i];
            } else {
                SQL += "," + campo[i];
            }
        }
        return SQL;
    }
    
    /**
     * Codigo generico que resuelve la problematica de crear el codigo
     * dinamico para SELECT {campos} FROM {tabla} WHERE
     * @since 1.4.2
     * @param campo - String[] campos a buscar
     * @param tabla - String tabla en la cual se hara la busqueda
     * @return String codigo SQL dinamico
     * @since 1.3.1
     */
    private String selectWhere(String[] campo, String tabla){
        String SQL = "SELECT ";
        SQL = this.setAtributos(SQL, campo);
        SQL += " FROM " + tabla + " WHERE ";
        return SQL;
    }
    
    /**
     * Selecciona haciendo busqueda utilizando el caracter '=', como parametros
     * se enviara la tabla en la cual se buscara, un Map con key tipo String y
     * value tipo String, ademas de los campos en los cuales se buscara. 
     * @param campo - campos a seleccionar
     * @param tabla - Nombre de la tabla
     * @param map - Map con los campos y datos a buscar
     * @param operadorLogico - Tipo de busqueda logica recibe [AND, OR, NOT]
     * @return ArrayList[Matriz] con los datos de la consulta
     * @since 1.4.2
     */
    public ArrayList selectWhereEquals(String[] campo, String tabla, Map<String, String> map, String[] operadorLogico) {
        String SQL = selectWhere(campo, tabla);
        SQL = this.where(
                SQL, 
                map, 
                operadorLogico, 
                new String[]{"=", "=", "=", "=", "=", "=", "=", "=","=","=","=","=","=","=","=","="});
        messageSQL(SQL);
        return super.consulta(SQL);
    }

    /**
     * Selecciona haciendo busqueda utilizando LIKE; como parametros se enviara
     * la tabla en la cual se buscara, un Map con key tipo String y value tipo
     * String, ademas de los campos en los cuales se buscara.
     * @since 1.4.2
     * @param campo - campos a seleccionar
     * @param tabla - Nombre de la tabla
     * @param map - Map con los campos y datos a buscar
     * @param operadorLogico - Tipo logico de busqueda [AND, OR, NOT]
     * @return ArrayList[Matriz] con los datos de la consulta
     */
    public ArrayList selectWhereLike(String[] campo, String tabla, Map<String, String> map, String[] operadorLogico) {
        String SQL = selectWhere(campo, tabla);
        SQL = this.where(
                SQL, 
                map, 
                operadorLogico, 
                new String[]{"LIKE","LIKE","LIKE","LIKE","LIKE","LIKE","LIKE","LIKE","LIKE","LIKE","LIKE","LIKE","LIKE","LIKE","LIKE","LIKE"});
        messageSQL(SQL);
        return super.consulta(SQL);
    }
    
    /**
     * Selecciona haciendo busqueda utilizando [<] menor que; como parametros se enviara
     * la tabla en la cual se buscara, un Map con key tipo String y value tipo
     * String, ademas de los campos en los cuales se buscara.
     * @since 1.4.2
     * @param campo - campos a seleccionar
     * @param tabla - Nombre de la tabla
     * @param map - Map con los campos y datos a buscar
     * @param operadorLogico - Tipo logico de busqueda [AND, OR, NOT]
     * @return ArrayList[Matriz] con los datos de la consulta
     */
    public ArrayList selectWhereLess(String[] campo, String tabla, Map<String, String> map, String[] operadorLogico) {
        String SQL = selectWhere(campo, tabla);
        SQL = this.where(SQL, map, operadorLogico, new String[]{"<"});
        messageSQL(SQL);
        return super.consulta(SQL);
    }
    
    /**
     * Selecciona haciendo busqueda utilizando [>] mayor que; como parametros se enviara
     * la tabla en la cual se buscara, un Map con key tipo String y value tipo
     * String, ademas de los campos en los cuales se buscara.
     * @since 1.4.2
     * @param campo - campos a seleccionar
     * @param tabla - Nombre de la tabla
     * @param map - Map con los campos y datos a buscar
     * @param operadorLogico - Tipo logico de busqueda [AND, OR, NOT]
     * @return ArrayList[Matriz] con los datos de la consulta
     */
    public ArrayList selectWhereMore(String[] campo, String tabla, Map<String, String> map, String[] operadorLogico) {
        String SQL = selectWhere(campo, tabla);
        SQL = this.where(SQL, map, operadorLogico, new String[]{">"});
        messageSQL(SQL);
        return super.consulta(SQL);
    }
    
    /**
     * Selecciona haciendo busqueda utilizando [<>] distinto de; como parametros se enviara
     * la tabla en la cual se buscara, un Map con key tipo String y value tipo
     * String, ademas de los campos en los cuales se buscara.
     * @since 1.4.2
     * @param campo - campos a seleccionar
     * @param tabla - Nombre de la tabla
     * @param map - Map con los campos y datos a buscar
     * @param operadorLogico - Tipo logico de busqueda [AND, OR, NOT]
     * @return ArrayList[Matriz] con los datos de la consulta
     */
    public ArrayList selectWhereOther(String[] campo, String tabla, Map<String, String> map, String[] operadorLogico) {
        String SQL = selectWhere(campo, tabla);
        SQL = this.where(SQL, map, operadorLogico, new String[]{"<>"});
        messageSQL(SQL);
        return super.consulta(SQL);
    }
    
    /**
     * Selecciona haciendo busqueda utilizando [<=] menor o igual que; como parametros se enviara
     * la tabla en la cual se buscara, un Map con key tipo String y value tipo
     * String, ademas de los campos en los cuales se buscara.
     * @since 1.4.2
     * @param campo - campos a seleccionar
     * @param tabla - Nombre de la tabla
     * @param map - Map con los campos y datos a buscar
     * @param operadorLogico - Tipo logico de busqueda [AND, OR, NOT]
     * @return ArrayList[Matriz] con los datos de la consulta
     */
    public ArrayList selectWhereLessEquals(String[] campo, String tabla, Map<String, String> map, String[] operadorLogico) {
        String SQL = selectWhere(campo, tabla);
        SQL = this.where(SQL, map, operadorLogico, new String[]{"<="});
        messageSQL(SQL);
        return super.consulta(SQL);
    }
    
    /**
     * Selecciona haciendo busqueda utilizando [>=] mayor o igual que; como parametros se enviara
     * la tabla en la cual se buscara, un Map con key tipo String y value tipo
     * String, ademas de los campos en los cuales se buscara.
     * @since 1.4.2
     * @param campo - campos a seleccionar
     * @param tabla - Nombre de la tabla
     * @param map - Map con los campos y datos a buscar
     * @param operadorLogico - Tipo logico de busqueda [AND, OR, NOT]
     * @return ArrayList[Matriz] con los datos de la consulta
     */
    public ArrayList selectWhereMoreEquals(String[] campo, String tabla, Map<String, String> map, String[] operadorLogico) {
        String SQL = selectWhere(campo, tabla);
        SQL = this.where(SQL, map, operadorLogico, new String[]{">="});
        messageSQL(SQL);
        return super.consulta(SQL);
    }
    
    /**
     * Codigo generico para crear sentencias WHERE
     * @since 1.4.2
     * @param SQL
     * @param map
     * @param operadorLogico
     * @param comparador
     * @return 
     */
    private String where(String SQL, Map<String, String> map, String[] operadorLogico, String[] comparador) {
        int lenght = map.size() - 1;
        int posicion = 0;
        for (Map.Entry<String, String> entry : map.entrySet()) {
            String s1 = entry.getKey();
            String s2 = entry.getValue();
            SQL += s1;
            SQL = this.comparador(SQL, comparador[posicion]) + "'" + s2 + "'";
            if (lenght != 0) {
                SQL += " " + operadorLogico[posicion] + " ";
                posicion++;
                lenght--;
            }
        }
        return SQL;
    }
    
    /**
     * Este codigo resuelve el problema de la comparacion en los metodos creados
     * para generar codigo SQL dinamico
     * @since 1.4.2
     * @param SQL - String con codigo SQL
     * @param comparador - tipo de comparador a utilziar
     * @return Codigo pre compilado
     */
    private String comparador(String SQL, String comparador){
        switch (comparador) {
            case "<":
                return SQL + " < ";
                
            case ">":
                return SQL + " > ";
                
            case "<>":
                return SQL + " <> ";
                
            case "<=":
                return SQL + " <= ";
                
            case ">=":
                return SQL + " >= ";
                
            case "=":
                return SQL + " = ";
                
            case "BETWEEN":
                return SQL + " BETWEEN ";
                
            case "LIKE":
                return SQL + " LIKE ";
                
            case "IN":
                return SQL + " IN ";
        }
        return "";
    }

    //</editor-fold>
    
    //<editor-fold desc="Busquedas" defaultstate="collapsed">
    /**
     * Este metodo obtiene toda la informacion de una tabla, con base en los
     * valores a buscar utilizando el caracter '='.
     * <br>
     * Ejemplo:
     * <br>
     * Map String,String map = new HashMap();
     * <br>
     * map.put("atributo","valor a buscar");
     * <br>
     * map.put("atributo2","valor a buscar");
     * <br>
     * getWhereEquals("tabla",map, "AND");
     * <br>
     * @deprecated Sustituya este metodo por alguno de los selectWhere[....]
     * @param tabla - nombre de la tabla que se desea consultar
     * @param map - arreglo asociativo para los atributos a igualar con =
     * @param operadorLogico - Operador logico para unir las compraciones OR o AND
     * @return ArrayList - Matriz con los datos de la consulta
     */
    public ArrayList getWhereEquals(String tabla, Map<String, String> map, String operadorLogico) {
        String SQL = "SELECT * FROM " + tabla + " WHERE ";
        SQL = this.whereEquals(SQL, map,operadorLogico);
        messageSQL(SQL);
        return super.consulta(SQL);
    }

    /**
     * Codigo generico para obtener setencias dinamicas SQL WHERE
     * @param SQL
     * @param map
     * @param operadorLogico
     * @return 
     */
    private String whereEquals(String SQL, Map<String, String> map,String operadorLogico) {
        int lenght = map.size() - 1;
        for (Map.Entry<String, String> entry : map.entrySet()) {
            String s1 = entry.getKey();
            String s2 = entry.getValue();
            SQL += s1 + " = '" + s2 + "'";
            if (lenght != 0) {
                SQL += " "+ operadorLogico +" ";
                lenght--;
            }
        }
        return SQL;
    }

    /**
     * Este metodo obtiene toda la informacion de una tabla, con base en los
     * valores a buscar utilizando la palabra LIKE.
     * <br>
     * Ejemplo:
     * <br>
     * Map String,String map = new HashMap();
     * <br>
     * map.put("atributo","valor a buscar");
     * <br>
     * map.put("atributo2","valor a buscar");
     * <br>
     * getWhereLike("tabla",map, "OR");
     * <br>
     * @deprecated Sustituya este metodo por alguno de los selectWhere[....]
     * @param tabla - Nombre de la tabla en la cual se va a buscar
     * @param map - campos en los cuales vamos a buscar informacion
     * @param operadorLogico - Tipo de conexion para las comparaciones OR o AND
     * @return ArrayList[matriz] de la informacion obtenida en la consulta
     */
    public ArrayList getWhereLike(String tabla, Map<String, String> map, String operadorLogico) {
        String SQL = "SELECT * FROM " + tabla + " WHERE ";
        SQL = this.whereLike(SQL, map, operadorLogico);
        messageSQL(SQL);
        return super.consulta(SQL);
    }

    /**
     * Codigo generico para obtener sentencias SQL WHERE
     * @param SQL
     * @param map
     * @param operadorLogico
     * @return 
     */
    private String whereLike(String SQL, Map<String, String> map,String operadorLogico) {
        int lenght = map.size() - 1;
        for (Map.Entry<String, String> entry : map.entrySet()) {
            String s1 = entry.getKey();
            String s2 = entry.getValue();

            SQL += s1 + " LIKE '" + s2 + "'";
            if (lenght != 0) {
                SQL += " "+ operadorLogico +" ";
                lenght--;
            }
        }
        return SQL;
    }

    //</editor-fold>
    
    //<editor-fold desc="Insercion" defaultstate="collapsed">
   
    /**
     * Este método devuelve el ultimo id de insersion realizado
     * @return int
     */
    public int lastInsertId() {
        this.excecuteQuery("SELECT last_insert_id() as lastid");
        String dato = this.getDato(0, 0);
        return Integer.parseInt(dato);
    }
    
    /**
     * Este metodo sirve para ingresar valores en una tabla, para poder agregar
     * los valores, se utiliza un Map[key,value]
     * <br>
     * Es importante recordar que este metodo unicamente puede ingresar un unico
     * registro.
     * <br>
     * Ejemplo
     * <br>
     * Map String,String map = new LinkedHashMap<>();
     * <br>
     * map.put("atributo","valor ingresar");
     * <br>
     * map.put("atributo2","valor ingresar");
     * <br>
     * insertar("tabla",map);
     *
     * @param tabla - nombre de la tabla en la cual se buscara
     * @param map - map con los atributo en los cuales se buscara
     * @return TRUE si fue posible ingresar valores, FALSE en caso contrario
     */
    public boolean insertar(String tabla, Map<String, String> map) {
        String SQL = "INSERT INTO " + tabla + " (";
        SQL = setCampos(SQL, map);
        SQL += ") " + "VALUES (";
        SQL = setValores(SQL, map);
        SQL += ")";
        messageSQL(SQL);
        return super.insertar(SQL);
    }

    private String setCampos(String SQL, Map<String, String> map) {
        int lenght = map.size() - 1;
        for (Map.Entry<String, String> entry : map.entrySet()) {
            String key = entry.getKey();
            SQL += key;
            if (lenght != 0) {
                SQL += " , ";
                lenght--;
            }
        }
        return SQL;
    }

    private String setValores(String SQL, Map<String, String> map) {
        int lenght = map.size() - 1;
        for (Map.Entry<String, String> entry : map.entrySet()) {
            String value = entry.getValue();
            if (value == null) {
                SQL += null;
            } else {
                SQL += "'" + value + "'";
            }

            if (lenght != 0) {
                SQL += " , ";
                lenght--;
            }
        }
        return SQL;
    }

    //</editor-fold>
    
    //<editor-fold desc="Actualizacion" defaultstate="collapsed">
    /**
     * Este metodo puede realizar una actualizacion en un registro, unicamente
     * un registro. <br>
     * este metodo recibe como parametros
     *
     * @param tabla - nombre de la tabla
     * @param campos - campos a actualizar
     * @param id - id de la tupla a actualizar
     * @return true si fue posible actualizar, false en caso contrario
     */
    public boolean update(String tabla, Map<String, String> campos, String[] id) {
        String SQL = "UPDATE " + tabla + " SET ";
        int lenght = campos.size() - 1;
        for (Map.Entry<String, String> entry : campos.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            SQL += key + " = '" + value + "'";
            if (lenght != 0) {
                SQL += " , ";
                lenght--;
            }
        }
        SQL += " WHERE " + id[0] + " = '" + id[1] + "'";
        messageSQL(SQL);
        return super.actualizar(SQL);
    }

    //</editor-fold>
    
    //<editor-fold desc="Eliminacion" defaultstate="collapsed">
    /**
     * Este metodo elimina una tupla de una tabla donde especifique el id y el
     * valor del mismo.
     *
     * @param tabla - Nombre de la tabla
     * @param id - Map con el atributo [id,valor]
     * @return true si se puede eliminar, false en caso contrario.
     */
    public boolean delete(String tabla, Map<String, String> id) {
        String SQL = "DELETE FROM " + tabla + " WHERE ";
        SQL = where(SQL,id, new String[] {"AND"}, new String[]{"=","=","=","=","=","="});
        messageSQL(SQL);
        return super.actualizar(SQL);
    }

    /**
     * Este metodo limpia los datos de la tabla
     *
     * @param tabla - Nombre de la tabla
     * @return boolean - True si se borraron correctamente los datos, false en
     * caso contrario
     */
    public boolean truncate_table(String tabla) {
        String SQL = "TRUNCATE " + tabla;
        return super.actualizar(SQL);
    }
    //</editor-fold>

    //<editor-fold desc="Max" defaultstate="collapsed">
    /**
     * Devuelve un string con el valor maximo obtenido de la consulta
     *
     * @param tabla - Tabla en la cual se buscara el valor maximo
     * @param campo - Nombre del campo del que se desea saber el valor maximo
     * @return String - Valor maximo obtenido
     */
    public String maxValue(String tabla, String campo) {
        String SQL = "Select MAX(" + campo + ") as " + campo + " FROM " + tabla;
        messageSQL(SQL);
        super.consulta(SQL);
        String valor = "";
        if (!isEmpty()) {
            valor = getDato(0, 0);
        }
        return valor;
    }
    
    /**
     * Devuelve un string con el valor maximo obtenido de la consulta
     *
     * @param tabla - Tabla en la cual se buscara el valor maximo
     * @param campo - Nombre del campo del que se desea saber el valor maximo
     * @param asignacion - Nombre que remplazara al nombre de la columna resultante
     * @return String - Valor maximo obtenido
     */
    public String maxValue(String tabla, String campo, String asignacion) {
        String SQL = "Select MAX(" + campo + ") as [" + asignacion + "] FROM " + tabla;
        messageSQL(SQL);
        super.consulta(SQL);
        String valor = "";
        if (!isEmpty()) {
            valor = getDato(0, 0);
        }
        return valor;
    }

    //</editor-fold>

    //<editor-fold desc="Min" defaultstate="collapsed">
    /**
     * Devuelve un string con el valor minimo obtenido de la consulta
     *
     * @param tabla - Tabla en la cual se buscara el valor minimo
     * @param campo - Nombre del campo del que se desea saber el valor minimo
     * @return String - Valor minimo obtenido
     */
    public String minValue(String tabla, String campo) {
        String SQL = "Select MIN(" + campo + ") as " + campo + " FROM " + tabla;
        messageSQL(SQL);
        super.consulta(SQL);
        String valor = "";
        if (!isEmpty()) {
            valor = getDato(0, 0);
        }
        return valor;
    }

    /**
     * Devuelve un string con el valor minimo obtenido de la consulta
     *
     * @param tabla - Tabla en la cual se buscara el valor minimo
     * @param campo - Nombre del campo del que se desea saber el valor minimo
     * @param asignacion - Nombre de la columna resultante
     * @return String - Valor minimo obtenido
     */
    public String minValue(String tabla, String campo, String asignacion) {
        String SQL = "Select MAX(" + campo + ") as [" + asignacion + "] FROM " + tabla;
        messageSQL(SQL);
        super.consulta(SQL);
        String valor = "";
        if (!isEmpty()) {
            valor = getDato(0, 0);
        }
        return valor;
    }
    
    //</editor-fold>

    //<editor-fold desc="AVG" defaultstate="collapsed">
    /**
     * Devuelve un string con el valor promedio obtenido de la consulta
     *
     * @param tabla - Tabla en la cual se buscara el valor promedio
     * @param campo - Nombre del campo del que se desea saber el valor minimo
     * @return String - Valor minimo obtenido
     */
    public String avgValue(String tabla, String campo) {
        String SQL = "Select AVG(" + campo + ") as " + campo + " FROM " + tabla;
        messageSQL(SQL);
        super.consulta(SQL);
        String valor = "";
        if (!isEmpty()) {
            valor = getDato(0, 0);
        }
        return valor;
    }
    //</editor-fold>
    
    //<editor-fold desc="JOIN" defaultstate="collapsed">
    /**
     * Este codigo genera las sentencias SQL para hacer un [LEFT,RIGHT,INNER,FULL] JOIN
     * @since 1.4.2
     * @param campos
     * @param tablas
     * @param tipo
     * @param data
     * @return 
     */
    private String join(String[] campos, String[] tablas, String tipo, Map<String, String> data) {
        String SQL = "SELECT ";
        if (data.size() + 1 == tablas.length) {
            try {
                int i = 0;
                SQL = this.setAtributos(SQL, campos);
                SQL += " FROM " + tablas[i] + " ";

                SQL = this.setTypeJoin(SQL, tipo);

                for (Map.Entry<String, String> entrySet : data.entrySet()) {
                    String PK = entrySet.getKey();
                    String FK = entrySet.getValue();
                    i++;
                    if (i < tablas.length) {
                        SQL += tablas[i] + " ON " + PK + " = " + FK;
                        if (i + 1 < tablas.length) {
                            SQL = this.setTypeJoin(SQL, tipo);
                        }
                    }
                }
            } catch (Exception ex) {

            }
        }
        return SQL;
    }
    
    /**
     * Este codigo es genericopara poder crear la sentencia INNER, LEFT, RIGHT o FULL para el JOIN
     * @since 1.4.2
     * @param SQL
     * @param tipo
     * @return 
     */
    private String setTypeJoin(String SQL, String tipo) {
        switch (tipo.toUpperCase()) {
            case "INNER":
                return SQL += " INNER JOIN ";

            case "LEFT":
                return SQL += " LEFT JOIN ";
                
            case "RIGHT":
                return SQL += " RIGHT JOIN ";
                
            case "FULL":
                return SQL += "FULL OUTER JOIN";
        }
        return "";
    }
    //</editor-fold>
    
    //<editor-fold desc="LEFT JOIN" defaultstate="collapsed">
    /**
     * Este metodo crea codigo SQL de tipo INNER JOIN para realizar consultas, el resultado del metodo
     * es un ArrayList [Matriz] con todos los datos obtenidos de la consulta
     * @since 1.4.2
     * @param campos - String[] campos a buscar, si se desea buscar todos los capos es posible usar el *
     * @param tablas - String[] tablas en las cuales se buscara la informacion
     * @param data - Map[key,value] con las uniones de llaves primarias y llaves secundarias
     * @return ArrayList[matriz] con los datos de la consulta
     */
    public ArrayList leftJoin(String[] campos, String[] tablas, Map<String, String> data) {
        String SQL = this.join(campos, tablas, "left", data);
        SQL += " WHERE ";
        return super.consulta(SQL);
    }
    
    /**
     * Este metodo crea codigo SQL de tipo INNER JOIN para realizar consultas, el resultado del metodo
     * es un ArrayList [Matriz] con todos los datos obtenidos de la consulta
     * @since 1.4.2
     * @param campos - String[] campos a buscar, si se desea buscar todos los capos es posible usar el *
     * @param tablas - String[] tablas en las cuales se buscara la informacion
     * @param data - Map[key,value] con las uniones de llaves primarias y llaves secundarias
     * @param where - Map[key,value] para los posibles filtros o bien, mejor dicho para busqueda de valores en la consulta
     * @param operadorLogico - El tipo de union en el codigo para la condicoon WHERE
     * @param comparador - Comparador a usar para las expresiondes de WHERE [ < , > , <= , >= , =, <> , LIKE ]
     * @return ArrayList[matriz] con los datos de la consulta
     */
    public ArrayList leftJoin(String[] campos, String[] tablas, Map<String,String> data, Map<String,String> where, String[] operadorLogico, String[] comparador){
        String SQL = this.join(campos, tablas, "left", data);
        SQL = this.where(SQL, where, operadorLogico, comparador);
        return super.consulta(SQL);
    }

    //</editor-fold>
    
    //<editor-fold desc="RIGHT JOIN" defaultstate="collapsed">
    /**
     * Este metodo crea codigo SQL de tipo RIGHT JOIN para realizar consultas, el resultado del metodo
     * es un ArrayList [Matriz] con todos los datos obtenidos de la consulta
     * @since 1.4.2
     * @param campos - String[] campos a buscar, si se desea buscar todos los capos es posible usar el *
     * @param tablas - String[] tablas en las cuales se buscara la informacion
     * @param data - Map[key,value] con las uniones de llaves primarias y llaves secundarias
     * @return ArrayList[matriz] con los datos de la consulta
     */
    public ArrayList rightJoin(String[] campos, String[] tablas, Map<String, String> data) {
        String SQL = this.join(campos, tablas, "right", data);
        return super.consulta(SQL);
    }
    
    /**
     * Este metodo crea codigo SQL de tipo RIGHT JOIN para realizar consultas, el resultado del metodo
     * es un ArrayList [Matriz] con todos los datos obtenidos de la consulta
     * @since 1.4.2
     * @param campos - String[] campos a buscar, si se desea buscar todos los capos es posible usar el *
     * @param tablas - String[] tablas en las cuales se buscara la informacion
     * @param data - Map[key,value] con las uniones de llaves primarias y llaves secundarias
     * @param where - Map[key,value] para los posibles filtros o bien, mejor dicho para busqueda de valores en la consulta
     * @param operadorLogico - El tipo de union en el codigo para la condicoon WHERE
     * @param comparador - Comparador a usar para las expresiondes de WHERE [ < , > , <= , >= , =, <> , LIKE ]
     * @return ArrayList[matriz] con los datos de la consulta
     */
    public ArrayList rightJoin(String[] campos, String[] tablas, Map<String,String> data, Map<String,String> where, String[] operadorLogico, String[] comparador){
        String SQL = this.join(campos, tablas, "right", data);
        SQL += " WHERE ";
        SQL = this.where(SQL, where, operadorLogico, comparador);
        return super.consulta(SQL);
    }

    //</editor-fold>
    
    //<editor-fold desc="FULL JOIN" defaultstate="collapsed">
    /**
     * Este metodo crea codigo SQL de tipo RIGHT JOIN para realizar consultas, el resultado del metodo
     * es un ArrayList [Matriz] con todos los datos obtenidos de la consulta
     * @since 1.4.2
     * @param campos - String[] campos a buscar, si se desea buscar todos los capos es posible usar el *
     * @param tablas - String[] tablas en las cuales se buscara la informacion
     * @param data - Map[key,value] con las uniones de llaves primarias y llaves secundarias
     * @return ArrayList[matriz] con los datos de la consulta
     */
    public ArrayList fullJoin(String[] campos, String[] tablas, Map<String, String> data) {
        String SQL = this.join(campos, tablas, "full", data);
        return super.consulta(SQL);
    }
    
    /**
     * Este metodo crea codigo SQL de tipo RIGHT JOIN para realizar consultas, el resultado del metodo
     * es un ArrayList [Matriz] con todos los datos obtenidos de la consulta
     * @since 1.4.2
     * @param campos - String[] campos a buscar, si se desea buscar todos los capos es posible usar el *
     * @param tablas - String[] tablas en las cuales se buscara la informacion
     * @param data - Map[key,value] con las uniones de llaves primarias y llaves secundarias
     * @param where - Map[key,value] para los posibles filtros o bien, mejor dicho para busqueda de valores en la consulta
     * @param operadorLogico - El tipo de union en el codigo para la condicoon WHERE
     * @param comparador - Comparador a usar para las expresiondes de WHERE [ < , > , <= , >= , =, <> , LIKE ]
     * @return ArrayList[matriz] con los datos de la consulta
     */
    public ArrayList fullJoin(String[] campos, String[] tablas, Map<String,String> data, Map<String,String> where, String[] operadorLogico, String[] comparador){
        String SQL = this.join(campos, tablas, "full", data);
        SQL += " WHERE ";
        SQL = this.where(SQL, where, operadorLogico, comparador);
        return super.consulta(SQL);
    }

    //</editor-fold>
    
    //<editor-fold desc="INNER JOIN" defaultstate="collapsed">
    /**
     * Este metodo crea codigo SQL de tipo INNER JOIN para realizar consultas, el resultado del metodo
     * es un ArrayList [Matriz] con todos los datos obtenidos de la consulta
     * @since 1.4.2
     * @param campos - String[] campos a buscar, si se desea buscar todos los capos es posible usar el *
     * @param tablas - String[] tablas en las cuales se buscara la informacion
     * @param data - Map[key,value] con las uniones de llaves primarias y llaves secundarias
     * @return ArrayList[matriz] con los datos de la consulta
     */
    public ArrayList innerJoin(String[] campos, String[] tablas, Map<String, String> data) {
        String SQL = this.join(campos, tablas, "INNER", data);
        messageSQL(SQL);
        return super.consulta(SQL);
    }
    
    /**
     * Este metodo crea codigo SQL de tipo INNER JOIN para realizar consultas, el resultado del metodo
     * es un ArrayList [Matriz] con todos los datos obtenidos de la consulta
     * @since 1.4.2
     * @param campos - String[] campos a buscar, si se desea buscar todos los capos es posible usar el *
     * @param tablas - String[] tablas en las cuales se buscara la informacion
     * @param data - Map[key,value] con las uniones de llaves primarias y llaves secundarias
     * @param where - Map[key,value] para los posibles filtros o bien, mejor dicho para busqueda de valores en la consulta
     * @param operadorLogico - El tipo de union en el codigo para la condicoon WHERE
     * @param comparador - Comparador a usar para las expresiondes de WHERE [ < , > , <= , >= , =, <> , LIKE ]
     * @return ArrayList[matriz] con los datos de la consulta
     */
    public ArrayList innerJoin(String[] campos, String[] tablas, Map<String,String> data, Map<String,String> where, String[] operadorLogico, String[] comparador){
        String SQL = this.join(campos, tablas, "INNER", data);
        SQL += " WHERE ";
        SQL = this.where(SQL, where, operadorLogico, comparador);
        messageSQL(SQL);
        return super.consulta(SQL);
    }

    //</editor-fold>
    
    //<editor-fold desc="UNION" defaultstate="collapsed">
    /**
     * Este metodo une a dos tablas, las cuales debent ener los mismos atributos, para poder realizar la union
     * @param tablas - String[] arreglo con las tablas a usar
     * @param campos1 - String[] campos a utilizar para la tabla 1
     * @param campos2 - String[] campos a utilizar para la tabla 2
     * @return ArrayList[matriz] - con los datos resultantes de la consulta
     */
    public ArrayList union(String[] tablas, String[] campos1, String[] campos2){
        String SQL = "";
        if(tablas.length == 2){
            SQL = "SELECT ";
            SQL = this.setAtributos(SQL, campos1);
            SQL += " FROM " + tablas[0];
            SQL += " UNION ";
            SQL += " SELECT ";
            SQL = this.setAtributos(SQL, campos2);
            SQL += " FROM " + tablas[1];
        }else{
            System.err.println("");
        }
        messageSQL(SQL);
        return super.consulta(SQL);
    }
    //</editor-fold>
    
    @Override
    public ArrayList CallProcedure(String procedure){
        return super.CallProcedure(procedure);
    }
}
//oiiiiieeeeeeeee
