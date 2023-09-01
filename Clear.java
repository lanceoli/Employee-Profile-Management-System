/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Form;

/**
 *
 * @author Lancelot
 */
public class Clear implements Runnable{
    GUI test;
    int temp;
    
    public Clear(GUI test) {
        this.test = test;
    }
    
    @Override
    public void run() {
        
            synchronized(Singleton.getObject()) {

                test.getjTextField1().setText(Singleton.getObject().apos);
                test.getjTextField2().setText(Singleton.getObject().apos);
                test.getjTextField3().setText(Singleton.getObject().apos);
                test.getjTextField4().setText(Singleton.getObject().apos);
                test.getjTextField5().setText(Singleton.getObject().apos);
                test.getjTextField6().setText(Singleton.getObject().apos);
            }
        
        try {
            Thread.sleep(1000);
        } catch(Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
}
