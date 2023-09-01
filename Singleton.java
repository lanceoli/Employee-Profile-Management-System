/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Form;

/**
 *
 * @author Lancelot
 */
public class Singleton {
    private static Singleton ms;
    
    public String apos = "";
    
    private Singleton() {
        
    }
    
    public static Singleton getObject() {
        if(ms == null) {
            ms = new Singleton();
        }
        return ms;
    }
}
