/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Form;

import javax.swing.JOptionPane;

/**
 *
 * @author Lancelot
 */
public class DepartmentClass extends FuncAbstract{
    
    GUI gui = new GUI();
    
    @Override
    public String DoFunction(){
        gui.update = JOptionPane.showInputDialog(null, "Enter New Department: ", "UPDATE", JOptionPane.QUESTION_MESSAGE);
        return gui.update;
//        gui.ID.set(gui.num, gui.update);
//            
//        // change table value
//        gui.model.setValueAt(gui.update, gui.num, 0);
    }
    
}
