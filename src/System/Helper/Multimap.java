/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package System.Helper;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

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
}
