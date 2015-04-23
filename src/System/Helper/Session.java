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
    private static String session_name;
    private static String session_type;
    
    public static void setUserData(String[] data){
        try{
            session_id = data[0];
            state_login = data[1];
            session_name = data[2];
            session_type = data[3];
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
                
            case "session_type":
                return session_type;
                    
            case "session_name":
                return session_name;
            
            default:
                return "";
        }
    }
    
    public static void unsetUserData(){
        session_id = "";
        state_login = "";
        session_type = "";
        session_name = "";
    }
}
