/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package System.Helper;

import java.util.Map;

/**
 *
 * @author Nekszer
 */
public class Session {
    
    private static String session_id;
    private static String state_login;
    
    public static void setUserData(String[] data){
        try{
            session_id = data[0];
            state_login = data[1];
        }catch(Throwable t){
            //message of error
        }
    }
    
    public static void setUserData(Map<String,String> data){
        
    }
    
    public static String userData(String s1){
        
        switch (s1) {
            case "session_id":
                return session_id;
                
            case "state_login":
                return state_login;
        }
        
        return "";
    }
    
    public static void unsetUserData(){
        session_id = "";
        state_login = "";
    }
}
