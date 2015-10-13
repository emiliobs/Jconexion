package System.DataBase.Core;

//<editor-fold defaultstate="collapsed" desc="Imports">
import System.Settings.MessageOption;
import java.beans.PropertyVetoException;
import java.io.File;
import java.util.ArrayList;
import javax.swing.JDesktopPane;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import System.DevAzt.GUI.FramTable;
import System.DevAzt.GUI.InternalTable;
import System.Helper.IO;
import System.Helper.Multimap;
import System.Settings.JConexion;
import java.security.KeyException;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
//</editor-fold>

/**
 * Clase desarrollada para guardar los datos de una consulta en MySQL Dichos
 * datos, son obtenidos del metodo Consulta de la clase Conexion
 *
 * @author Nekszer
 * @version JConexionDB 1.3.0
 * @since JConexionDB 1.0
 */
public abstract class Datos {

    //<editor-fold defaultstate="collapsed" desc="Campos [Variables, Objetos]">
    /**
     * Objeto que almacena la matriz resultante de la consulta realizada en SQL
     */
    
    /**
     * Objeto utilizado para manejar la matriz de una manera más eficiente
     */
    protected Multimap Relacion = new Multimap();
    
    protected ArrayList matriz = new ArrayList();
    /**
     * Default Model para el componente del JXTable
     */
    private DefaultTableModel tabla;

    /**
     * Objeto del JDialog FramTable el cual es utilizado para generar la tabla
     * con la consulta de SQL
     */
    private FramTable dialog = null;

    /**
     * Objeto de InternalFrame InternalTable utilizado para generar la tabla de
     * una consulta de SQL
     */
    private final InternalTable internal = new InternalTable();

    private File file;
    private JTable jTable;
    private String nombreTabla;
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Default Code">
    /**
     * Este metodo fue desarrollado para devolver el tamaño actual de la matriz
     * , la cual es el resultado o contiene los datos de una consulta
     * previamente realizada.
     *
     * @return int - tamaño de la matriz [tuplas].
     */
    public int sizeTuplas() {
        return matriz.size();
    }

    /**
     * Regresa el numero de columas que existen en la matriz; la cual contiene
     * los datos de una consulta previamente realizada.
     *
     * @return int - numero de columnas
     */
    public int sizeColumnas() {
        int columnas = 0;
        if (!this.isEmpty()) {
            columnas = ((ArrayList) this.matriz.get(0)).size();
        }
        return columnas;
    }

    /**
     * Este metodo, regresa true si la matriz; la cualc ontiene los datos de una
     * consulta previamente realizada, no tiene datos.
     *
     * @return True - la matriz no tiene datos <br> False - en caso contrario
     */
    public boolean isEmpty() {
        return matriz.isEmpty();
    }

    /**
     * Este metodo obtiene un dato de la tabla resultante de una consulta hecha
     * con la clase Conexion
     *
     * @param tupla Numero de la tupla donde esta el valor [La primer tupla es
     * 0]
     * @param columna Numero de la columna donde se encuentra el dato [La primer
     * columna es 0]
     * @return String el cual es el resultado de la busqueda del dato en la
     * matriz[tupla][columna].
     * @since JConexionDB 1.0
     */
    public String getDato(int tupla, int columna) {
        if (tupla < this.sizeTuplas() && columna < this.sizeColumnas()) {
            String s1 = ((String) ((ArrayList) this.matriz.get(tupla)).get(columna) + "");
            return s1;
        } else {
            MessageOption.exit(5, false);
            return null;
        }
    }
    
    /**
     * Este metodo obtiene los datos de una tupla, y los regresa en un ArrayList
     *
     * @param tupla Numero de la tupla en la cual se buscan ontienen los datos
     * [La primer tupla es 0]
     * @return ArrayList de la tupla que se busco en caso de no existir la
     * tupla, se regresa null
     * @since JConexionDB 1.0
     */
    public ArrayList getTupla(int tupla) {
        Map<String,String> data = new LinkedHashMap();
        ArrayList al = new ArrayList();
        
        if (tupla < this.sizeTuplas()) {
            for (int j = 0; j < ((ArrayList) this.matriz.get(tupla)).size(); j++) {
                al.add(((ArrayList) this.matriz.get(tupla)).get(j));
            }
            return al;
        } else {
            MessageOption.exit(6, false);
            return null;
        }
    }
    
    /**
     * Método diseñado para devolver un map con los datos correspondientes a la fila [index]
     * @param index
     * @return Map string,string
     * @throws java.security.KeyException
     * @since 1.4.2.36
     */
    public Map<String,String> row(int index) throws IndexOutOfBoundsException{
        return this.Relacion.row(index);
    }
    
    /**
     * Método diseñado para devolver la row por defecto [0] en consultas que devuelven una sola tupla
     * @return Map string,string
     * @since 1.4.2.36
     */
    public Map<String,String> row() throws IndexOutOfBoundsException{
        return this.Relacion.row(0);
    }
    
    /**
     * Este método devuelve todos los valores disponibles para una columna [columname param]
     * @param columname nombre de la columna a obtener
     * @return lista de valores
     * @throws KeyException el nombre de la columna no es correcto o no existe 
     */
    public List<String> column(String columname) throws KeyException{
        return this.Relacion.column(columname);
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="JDialog Table Code">
    /**
     * Metodo desarrollado para generar la tabla de la consulta
     *
     * @param tupla (int) Numero de la tupla file la cual se agregaran los datos
     * @param columna (int) Numero de la columna en la cual se agrega el dato
     * @param o (Object) file agregar el dato
     * @since JConexionDB 1.0
     */
    protected void crearTabla(int tupla, int columna, Object o) {
        if (columna == 0) {
            this.matriz.add(new ArrayList());
        }
        ((ArrayList) this.matriz.get(tupla)).add(o.toString());
    }

    /**
     * Muestra consulta en JDialog <br>
     * Este metodo fue agregado en la version 1.1 de JConexionDB con el fin de
     * mostrar la consulta en una tabla.
     *
     * @param titulo Nombre de la tabla resultante de la consulta [String]
     * @since JConexionDB 1.1
     */
    public void mostrarDialogTable(String titulo) {
        dialog = new FramTable(new javax.swing.JFrame(), true);
        dialog.setLocationRelativeTo(null);
        try {
            //obtenemos el numero de las filaz de la matriz
            int filas = this.matriz.size();
            //obtenemos el numero de las columnas de la matriz
            int columnas = ((ArrayList) this.matriz.get(0)).size();

            //modificamos tabla
            //seteamos el numero de columnas al numero de columnas correctas
            boolean b1 = IO.tabla_setFilas(this.dialog.tabla, filas);
            boolean b2 = IO.tabla_setColumnas(this.dialog.tabla, columnas);

            //verificamos que se hayan echo correctamente el set de las filas y 
            //las columnas para poder ingresar los datos
            if (b1 && b2) {
                //añadimos cada elemento de la matriz a la tabla
                IO.tabla_setDatos(this.matriz, this.dialog.tabla);
                //le asignamos el nombre de las columnas
                IO.tabla_setNombreColumna(Conexion.nombreColumnas, this.dialog.tabla);
                //asignamos los datos a la ventana y la hacemos visible
                this.propiedadesVentana(columnas, titulo);
            }
        } catch (IndexOutOfBoundsException i) {
            JOptionPane.showMessageDialog(null, "No hay datos en la consulta");
        }
    }

    /**
     * Muestra consulta en JDialog <br>
     * Este metodo se utiliza para asignar algunos valores que se han repetido
     * en dos metodos, por lo cual, se ha hecho uno especial para no tener
     * codigo extra.
     *
     * @param columnas Numero de columnas obtenidas de la matriz
     * @param titulo Titulo de la ventana
     */
    private void propiedadesVentana(int columnas, String titulo) {
        this.dialog.tabla.packAll();
        //alineamos al centro las columnas
        IO.tabla_setAlign(this.dialog.tabla, columnas, "center");
        //mandamos titulo de la ventana
        this.dialog.setTitle(titulo);
        //hacemos visible la ventana
        this.dialog.setVisible(true);
        IO.tabla_setHeightFilas(this.dialog.tabla);
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Internal Table Code">
    /**
     * Muestra una consulta en un InternalFrame, es necesario usar JDesktopPane
     * <br>
     * Este metodo fue agregado en la version 1.2.1 de JConexionDB con el fin de
     * mostrar la consulta en una tabla en la misma ventana, haciendo uso de
     * JDesktopPane
     *
     * @param dp Nombre del JDesktopPane contenedor del InternalFrame
     * @param titulo Nombre de la tabla resultante de la consulta [String]
     * @since JConexionDB 1.2.1
     */
    public void mostrarInternalTable(String titulo, JDesktopPane dp) {
        boolean b1 = this.mostrarInternalTable(titulo);
        if (b1) {
            this.internal.show();

            dp.add(this.internal);
            this.internal.setTitle(titulo);

            this.internal.tabla.packAll();

            try {
                this.internal.setMaximum(true);
            } catch (PropertyVetoException ex) {

            }
        }
    }

    private boolean mostrarInternalTable(String titulo) {
        boolean b = false;

        try {
            //obtenemos el numero de las filaz de la matriz
            int filas = this.matriz.size();
            //obtenemos el numero de las columnas de la matriz
            int columnas = ((ArrayList) this.matriz.get(0)).size();

            //modificamos tabla
            //seteamos el numero de columnas al numero de columnas correctas
            boolean b1 = IO.tabla_setFilas(this.internal.tabla, filas);
            boolean b2 = IO.tabla_setColumnas(this.internal.tabla, columnas);

            //verificamos que se hayan echo correctamente el set de las filas y 
            //las columnas para poder ingresar los datos
            if (b1 && b2) {
                //añadimos cada elemento de la matriz a la tabla
                IO.tabla_setDatos(this.matriz, this.internal.tabla);
                //le asignamos el nombre de las columnas
                IO.tabla_setNombreColumna(Conexion.nombreColumnas, this.internal.tabla);

                IO.tabla_setHeightFilas(this.internal.tabla);
                //alineamos al centro las columnas
                IO.tabla_setAlign(this.internal.tabla, columnas, "center");
                b = true;
            } else {
                b = false;
            }
        } catch (IndexOutOfBoundsException i) {
            JOptionPane.showMessageDialog(null, "No hay datos en la consulta");
            b = false;
        }

        return b;
    }

    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Terminal Table Code">
    /**
     * Este medoto fue desarrollado para mostrar una consulta en forma de tabla
     * , la consulta debe estar anteriormente realizada.
     *
     * @param titulo Titulo a poner en el centro de la tabla impresa
     */
    public void mostrarTerminalTable(String titulo) {
        if (!this.isEmpty()) {
            this.printTitulo(titulo);
            this.printCabecera();
            this.printTabla();
        } else {
            System.err.println("No hay datos en la consulta");
        }
    }

    private void printTitulo(String titulo) {
        int total = anchoTabla();
        int numeroColumnas = this.sizeColumnas();
        int espacios = (numeroColumnas * 2) + (numeroColumnas + 1);
        int mitad = (total + espacios) / 2;
        int mitadTitulo = titulo.length() / 2 ;
        mitad -= mitadTitulo;
        format("", mitad);
        System.out.println(titulo);
    }

    private void printCabecera() {
        for (int i = 0; i < Conexion.nombreColumnas.size(); i++) {
            String s1 = Conexion.nombreColumnas.get(i);
            int ancho = Conexion.tamaño[i];
            if (i == 0) {
                System.out.print("| ");
                format(s1, ancho);
            } else {
                if (i == Conexion.nombreColumnas.size() - 1) {
                    System.out.print(" | ");
                    this.format(s1, ancho);
                    System.out.print(" |\n");
                } else {
                    System.out.print(" | ");
                    this.format(s1, ancho);
                }
            }
        }
    }

    private void printTabla() {
        for (int i = 0; i < matriz.size(); i++) {
            for (int j = 0; j < ((ArrayList) matriz.get(i)).size(); j++) {
                String s1 = (((ArrayList) matriz.get(i)).get(j)).toString();
                int ancho = Conexion.tamaño[j];
                if (j == 0) {
                    System.out.print("| ");
                    this.format(s1, ancho);
                } else {
                    if (j == (((ArrayList) matriz.get(i)).size()) - 1) {
                        System.out.print(" | ");
                        this.format(s1, ancho);
                        System.out.print(" | \n");
                    } else {
                        System.out.print(" | ");
                        this.format(s1, ancho);
                    }
                }
            }
        }
    }

    private void format(String s1, int ancho) {
        int tamaño = s1.length();
        System.out.print(s1);
        if (tamaño < ancho) {
            tamaño = ancho - tamaño;
            for (int i = 0; i < tamaño; i++) {
                System.out.print(" ");
            }
        }
    }

    private int anchoTabla() {
        int caracters = 0;
        for (Integer tamaño : Conexion.tamaño) {
            caracters += tamaño;
        }
        return caracters;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Override">
    @Override
    public String toString() {
        String s1 = "";
        for (int i = 0; i < matriz.size(); i++) {
            s1 += "Tupla " + i + "\n";
            for (int j = 0; j < ((ArrayList) matriz.get(i)).size(); j++) {
                s1 += (((ArrayList) matriz.get(i)).get(j)).toString() + " ";
            }
            s1 += "\n";
        }
        return s1;
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Override">
    
    public List<String> Select(String key){
        ArrayList meta = (ArrayList) Relacion.keySet();
        if(meta.contains(key)){
            return (List<String>) Relacion.get(key);
        }else{
            return null;
        }
    }
    
    //</editor-fold>
}
