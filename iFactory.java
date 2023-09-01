/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Form;

/**
 *
 * @author Lancelot
 */
public class iFactory {
    public FuncAbstract func;
    
    public FuncAbstract createObject(int ch){
         if(ch ==1){
            func = new IDclass();
        }
        else if(ch == 2){
            func = new NameClass();
        }
         
        else if(ch == 3){
            func = new DepartmentClass();
        }
         
         return func;
    }
}
