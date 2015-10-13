package System.DataBase.Core;

import System.Settings.MessageOption;
import System.DevAzt.IO.Archivo;
import System.Helper.Multimap;
import System.Settings.Options;
import com.mysql.jdbc.CallableStatement;
import java.awt.HeadlessException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 * Clase desarrollada para insertar, consultar y modificar los datos de una base
 * de datos en MySQL Dichos datos, son obtenidos del metodo Consulta de la clase
 * Conexion
 *
 * @author Nekszer
 * @version JConexionDB 1.3.0
 * @since JConexionDB 1.0
 */
public abstract class Conexion extends Datos {

    //<editor-fold defaultstate="collapsed" desc="Varaibles,Objetos - Fields, Object">
    /**
     * Objeto que contiene el estado de la conexion al a base de datos
     */
    protected Connection miConexion;

    /**
     * Objeto el cual nos ayudara a ejecutar procedimientos almacenados
     */
    protected java.sql.CallableStatement procedure;
    
    /**
     * Se encarga de ejecutar consultas SQL
     */
    protected PreparedStatement consulta;

    /**
     * Recibe o almacena los elementos de la consulta
     */
    protected ResultSet datos;

    /**
     * Almacena el nombre de la base de datos
     */
    protected static String BD;

    /**
     * Almacena el usuario de la base de datos
     */
    protected static String usuario;

    /**
     * Almacena la contraseña de la base de datos
     */
    protected static String password;

    /**
     * log sql [Ultimo registro SQLConsulta
     */
    public static String SQLConsulta;

    /**
     * configuraciones de la base de datos
     */
    public static boolean error = false;

    /**
     * Almacena el nombre de las columnas de cierta consulta...
     */
    public static ArrayList<String> nombreColumnas = new ArrayList();

    /**
     * Instanca de la clase Archivo para poder manejar informacion del archivo
     * dbconfiguracion.dll
     */
    Archivo config = new Archivo();
    
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Default Code - No eliminar">
    /**
     * El constructor asigna la base de datos, en caso de no existir
     */
    public Conexion() {
        this.asignarBasededatos();
    }

    /**
     * Este método asigna la base de datos a nuestro proyecto
     * @since JConexionDB 1.0
     */
    public void asignarBasededatos() {
        String s1 = config.leerArchivo();
        String separator = ";";
        if(Options.driver.toUpperCase().equals("SQLSRV")){ separator = "*"; }
        ArrayList<String> s2 = config.leerConfiguracion(s1,separator);
        try {
            BD = s2.get(0);
            usuario = s2.get(1);
            if(s2.size() > 2){
                password = s2.get(2);
            }else{
                password = "";
            }
        } catch (IndexOutOfBoundsException e) {
            config.borrarArchivo();
            MessageOption.exit(1, true);
        }
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Logica BD - Logic DB">
    /**
     * Este método obtiene una conección a la base de datos
     *
     * @return Objecto de tipo Connection
     * @since JConexionDB 1.0
     */
    public static Connection getConnection() {
        switch (Options.driver.toUpperCase()) {
            case "MYSQL":
                return connectionMySQL();
            
            case "POSTGRE":
                return connectionPostgre();
                
            case "SQLSRV":
                return connectionSQLSRV();
                
            default:
                return null;
        }
    }
    
    private static Connection connectionSQLSRV(){
        Connection cn;
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            cn = DriverManager.getConnection(BD,usuario,password);
        } catch (ClassNotFoundException c) {
            error = true;
            cn = null;
            MessageOption.exit(2, true);
        } catch(SQLException s){
            error = true;
            cn = null;
            Archivo a = new Archivo();
            a.borrarArchivo();
            MessageOption.exit(3, true);
        }
        return cn;
    }
    
    private static Connection connectionMySQL(){
        Connection conexion = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String usuarioDB = usuario;
            String passwordDB = password;
            String direccion = BD;
            conexion = DriverManager.getConnection(direccion, usuarioDB, passwordDB);
        } catch (ClassNotFoundException ex) {
            error = true;
            conexion = null;
            MessageOption.exit(2, false);
        } catch (SQLException ex) {
            error = true;
            conexion = null;
            MessageOption.exit(3, false);
        }
        return conexion;
    }
    
    private static Connection connectionPostgre(){
        Connection conexion;
        try {
            Class.forName("org.postgresql.Driver");
            conexion = DriverManager.getConnection(BD, usuario, password);
        } catch (ClassNotFoundException ex) {
            error = true;
            conexion = null;
            MessageOption.exit(2, false);
        } catch (SQLException ex) {
            error = true;
            conexion = null;
            MessageOption.exit(3, false);
        }
        return conexion;
    }

    /**
     * Este metodo realiza una consulta en la base de datos y devuelve un
     * arraylist el cual contiene una tabla (matriz) de la consulta [tabla]
     * resultante.
     *
     * @param SQL Codigo SQLConsulta para nuestra consulta
     * @return Tabla de la consulta
     * @since JConexionDB 1.0
     */
    protected ArrayList consulta(String SQL) {
        Conexion.SQLConsulta = SQL;
        matriz.clear();
        nombreColumnas.clear();
        try {
            StartDataBase(SQL);
            //obtengo numero de columnas
            int numColumnas = datos.getMetaData().getColumnCount();
            String var = "";
            this.initTamaño(numColumnas);
            /**
             * recorremos una fila de los datos, obteniendo el tipo de dato
             */
            for (int i = 1; i <= numColumnas; i++) {
                nombreColumnas.add(datos.getMetaData().getColumnName(i));
                //System.out.println(type.get(i-1)); //mostramos tipo de dato - descomentar para test
                //System.out.println(datos.getMetaData().getColumnName(i)); //mostramos el nombre de la columna
                this.setTamaño(nombreColumnas.get(i - 1), i - 1);
            }
            int fila = 0;
            //insertamos los datos en nuestra tabla, contenida en la clase Datos
            while (this.datos.next()) {
                for (int i = 1; i <= numColumnas; i++) {
                    Object obj = datos.getObject(i);
                    var = String.valueOf(obj);
                    Relacion.put(nombreColumnas.get(i-1), var);
                    this.crearTabla(fila, i - 1, obj);
                    this.setTamaño(var, i - 1);
                }
                fila++;
            }
        } catch (SQLException | NullPointerException ex) {
            /**     --      */
        } finally {
            this.desconectar();
        }
        return matriz;
    }

    /**
     * Este método realiza la conexion a la base de datos y ejecuta código SQL
     * @param SQL - Consulta SQL a ejecutar
     * @since JConexionDB 1.4.2
     */
    private void StartDataBase(String SQL) throws SQLException{
        this.miConexion = (Connection) Conexion.getConnection();
        this.consulta = this.miConexion.prepareStatement(SQL);
        this.datos = this.consulta.executeQuery();
    }

    /**
     * Este metodo puede ingresar datos en nuestra base de datos
     *
     * @param SQL Codigo SQLConsulta para ingresar datos
     * @return true si se agregaron datos correctamente. false en caso contrario
     * @since JConexionDB 1.2
     */
    protected boolean insertar(String SQL) {

        boolean b1 = false;

        try {
            this.miConexion = (Connection) Conexion.getConnection();
            Statement statement;
            statement = (Statement) miConexion.createStatement();
            statement.execute(SQL);
            statement.close();
            miConexion.close();
            b1 = true;
            MessageOption.success(0);
        } catch (SQLException | HeadlessException ex) {
            MessageOption.exit(0, false);
            b1 = false;
        }

        return b1;
    }

    /**
     * Este metodo puede actualizar datos en nuestra base de datos haciendo uso
     * de codigo SQLConsulta, el cual es enviado al metodo como parametro
     *
     * @param SQL Codigo SQLConsulta para ingresar datos
     * @return true si se actualizaron los datos correctamente, false en caso
     * contrario
     * @since JConexionDB 1.2
     */
    protected boolean actualizar(String SQL) {
        boolean b1 = false;
        try {
            this.miConexion = (Connection) Conexion.getConnection();
            Statement statement;
            statement = (Statement) miConexion.createStatement();
            statement.executeUpdate(SQL);
            statement.close();
            miConexion.close();
            b1 = true;
            MessageOption.success(1);
        } catch (SQLException | HeadlessException ex) {
            MessageOption.exit(0, false);
            b1 = false;
        }
        return b1;
    }

    public ArrayList CallProcedure(String call){
        try
        {
            this.miConexion = (Connection) Conexion.getConnection();
            procedure = miConexion.prepareCall(call);
            datos = procedure.executeQuery();
        }
        catch (SQLException e)
        {
            return null;
        }
        matriz.clear();
        nombreColumnas.clear();
        try {
            //obtengo numero de columnas
            int numColumnas = datos.getMetaData().getColumnCount();
            String var = "";
            this.initTamaño(numColumnas);
            /**
             * recorremos una fila de los datos, obteniendo el tipo de dato
             */
            for (int i = 1; i <= numColumnas; i++) {
                nombreColumnas.add(datos.getMetaData().getColumnName(i));
                //System.out.println(type.get(i-1)); //mostramos tipo de dato - descomentar para test
                //System.out.println(datos.getMetaData().getColumnName(i)); //mostramos el nombre de la columna
                this.setTamaño(nombreColumnas.get(i - 1), i - 1);
            }
            int fila = 0;
            //insertamos los datos en nuestra tabla, contenida en la clase Datos
            while (this.datos.next()) {
                for (int i = 1; i <= numColumnas; i++) {
                    Object obj = datos.getObject(i);
                    var = String.valueOf(obj);
                    Relacion.put(nombreColumnas.get(i-1), var);
                    this.crearTabla(fila, i - 1, obj);
                    this.setTamaño(var, i - 1);
                }
                fila++;
            }
        } catch (SQLException | NullPointerException ex) {
            /**     --      */
        } finally {
            this.desconectar();
        }
        return matriz;
    }
    
    /**
     * Metodo que cierra las conexiones
     *
     * @since JConexionDB 1.0
     */
    private void desconectar() {
        try {
            this.miConexion.close();
            this.consulta.close();
        } catch (Throwable ex) {
        }
    }

    /**
     *
     * Metodo que cierra las conexiones
     *
     * @since JConexionDB 1.0
     * @param rs datos a eliminar
     */
    private void desconectar(ResultSet rs) {
        this.desconectar();
        try {
            rs.close();
        } catch (SQLException ex) {
        }
    }

    /**
     * Este metodo sirve para obtener el texto del ultimo codigo SQLConsulta
     * echo
     *
     * @return SQLConsulta String con la ultima consulta, update o insercion
     * realizada.
     */
    public static String getSQL() {
        return SQLConsulta;
    }

    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Codigo para la Terminal">
    public static Integer[] tamaño = null;

    private void setTamaño(String var, int columna) {
        int tamañoVar = var.length();

        if (tamaño[columna] < tamañoVar) {
            tamaño[columna] = tamañoVar;
        }
    }

    private void initTamaño(int numColumnas) {
        tamaño = new Integer[numColumnas];
        for (int i = 0; i < numColumnas; i++) {
            tamaño[i] = 0;
        }
    }
    //</editor-fold>
}
