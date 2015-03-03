
package System.Helper;

import java.util.Scanner;

/**
 * Clase desarrollada para obtener cadenas de texto desde la consola
 * @author Nekszer
 * @version JConexionDB 1.3.0
 * @since JConexionDB 1.3.0
 */
public class Teclado {

    //<editor-fold desc=" Campos [Objectos , Variables o Constantes]">
   
    /**
     * Objeto usado para obtener el lector de texto
     */
    private Scanner sc;
 
    //</editor-fold>
   
    public Teclado() {
        sc = new Scanner(System.in);
    }
 
    //<editor-fold defaultstate="collapsed" desc=" Metodos para obtener datos tipo String ">
    /**
     * Obtiene una cadena de caracteres desde el telcado, unicamente de la [A a
     * la Z] teniendo en cuenta mayusculas y minusculas de acuerdo a lo que se
     * pida en el titulo (parametro de este metodo).
     *
     * @param titulo Titulo a mostrar en consola, como referencia a lo que se
     * desea obtener
     * @return String Cadena de caracteres con lo capturado en consola
     */
    public String getString(String titulo) {
        String s1 = "";
        while (s1.equals("")) {
            System.out.println(titulo);
            s1 = sc.nextLine(); // "Hola"
            if (!s1.matches("^[A-Za-z ]*$")) {
                s1 = "";
            }
        }
        return s1;
    }
    
    /**
     * Obtiene una cadena de caracteres desde el telcado, esta debe ser alfanumerica 
     * de acuerdo a lo que se pida en el titulo (parametro de este metodo).
     * @param titulo Titulo a mostrar en consola, como referencia a lo que se
     * desea obtener
     * @return String Cadena de caracteres de lo capturado en consola
     */
    public String getAlfaNumeric(String titulo) {
        String s1 = "";
        while (s1.equals("")) {
            System.out.println(titulo);
            s1 = sc.nextLine(); // "Hola"
            if (!s1.matches("^[A-Za-z0-9 ]*$")) {
                s1 = "";
            }
        }
        return s1;
    }
    
    //</editor-fold>
 
    //<editor-fold defaultstate="collapsed" desc=" Metodos para obtener datos tipo Int ">
   
    /**
     * Obtiene un entero desde el telcado a traves de la consola, unicamente
     * este numero debe ser entero [0-9], Ademas se valida si el usuario captura numeros o letras
     * evitando la insonsistencia de informacion.
     * @param titulo Referencia a mostrar en consola para obtener datos
     * @param caracteres Que numero de caracteres deseamos obtener
     * @return Int Numero capturado por el usuario
     */
    public int getInt(String titulo, int caracteres) {
        String s1 = "";
        while (s1.equals("")) {
            System.out.println(titulo);
            s1 = sc.nextLine(); // "Hola"
            if (!s1.matches("[0-9]{" + caracteres + "}")) { // 455
                s1 = "";
            }
        }
        return Integer.parseInt(s1);
    }
   
    /**
     * Obtiene un entero desde el telcado a traves de la consola, unicamente
     * este numero debe ser entero [0-9], Ademas se valida si el usuario captura numeros o letras
     * evitando la insonsistencia de informacion.
     * @param titulo Referencia a mostrar en consola para obtener datos
     * @return Int Numero capturado por el usuario
     */
    public int getInt(String titulo) {
        String s1 = "";
        while (s1.equals("")) {
            System.out.println(titulo);
            s1 = sc.nextLine(); 
            if (!s1.matches("[0-9]*$")) {
                s1 = "";
            }
        }
        return Integer.parseInt(s1);
    }
   
    //</editor-fold>
}
