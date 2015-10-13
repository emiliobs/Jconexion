/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package System.Helper;

import java.security.KeyException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 *
 * @author Nekszer
 */
public class Multimap<Key,Value> extends LinkedHashMap<String, List<String>> {
    
    public void put(String key, String number) {
        List<String> current = get(key);
        if (current == null) {
            current = new ArrayList<>();
            super.put(key, current);
        }
        current.add(number);
    }
    
    public Map<String,String> row(int index) throws IndexOutOfBoundsException{
        boolean found = false;
        Map<String,String> data = new LinkedHashMap<>();
        for (Entry<String, List<String>> entrySet : this.entrySet()) {
            String key = entrySet.getKey();
            List<String> value = entrySet.getValue();
            for (int i = 0; i < value.size(); i++) {
                if(i == index){
                    found = true;
                    data.put(key,value.get(i));
                }
            }
        }
        if(found){
            return data;
        }else{
            throw new IndexOutOfBoundsException("El index no existe");
        }
    }
    
    /**
     * Este método realiza una proyeccion de datos para la matriz actual
     * @param columname nombre de la columna
     * @return List con los valores de la columna para la matriz actual
     * @throws KeyException el nombre de la columna no es correcto o no existe
     */
    public List<String> column(String columname) throws KeyException{
        for (Entry<String, List<String>> entrySet : this.entrySet()) {
            String key = entrySet.getKey();
            List<String> value = entrySet.getValue();
            if(key.toLowerCase().equals(columname.toLowerCase())){
                return value;
            }
        }
        throw new KeyException("El nombre de la column no es correcta");
    }
}
