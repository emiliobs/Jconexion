/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package System.DataBase.Core;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Nekszer
 */
class Instance {

    static Object toDefaultType(Class<?> target, String s) {
        if (target == Object.class || target == String.class || s == null) {
            return s;
        }
        if (target == Character.class || target == char.class) {
            return s.charAt(0);
        }
        if (target == Byte.class || target == byte.class) {
            return Byte.parseByte(s);
        }
        if (target == Short.class || target == short.class) {
            return Short.parseShort(s);
        }
        if (target == Integer.class || target == int.class) {
            return Integer.parseInt(s);
        }
        if (target == Long.class || target == long.class) {
            return Long.parseLong(s);
        }
        if (target == Float.class || target == float.class) {
            return Float.parseFloat(s);
        }
        if (target == Double.class || target == double.class) {
            return Double.parseDouble(s);
        }
        if (target == Boolean.class || target == boolean.class) {
            return Boolean.parseBoolean(s);
        }
        throw new IllegalArgumentException("Don't know how to convert to " + target);
    }

    static <T> T instantiate(ArrayList<String> args, Class<T> myclass) throws Exception {
        // Load the class.
        Class<?> clazz = Class.forName(myclass.getName());
        // Search for an "appropriate" constructor.
        for (Constructor<?> ctor : clazz.getConstructors()) {
            Class<?>[] paramTypes = ctor.getParameterTypes();

            // If the arity matches, let's use it.
            if (args.size() == paramTypes.length) {

                // Convert the String arguments into the parameters' types.
                Object[] convertedArgs = new Object[args.size()];
                for (int i = 0; i < convertedArgs.length; i++) {
                    convertedArgs[i] = toDefaultType(paramTypes[i], args.get(i));
                }
                // Instantiate the object with the converted arguments.
                return (T) ctor.newInstance(convertedArgs);
            }
        }
        
        throw new IllegalArgumentException("Don't know how to instantiate " + myclass.getName());
    }
}
