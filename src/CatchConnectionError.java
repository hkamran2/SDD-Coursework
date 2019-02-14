
import javax.swing.*;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Hamza Kamran
 */
//This class deals with the exceptions related to data base
public class CatchConnectionError extends Exception{
    public CatchConnectionError(){
         JOptionPane.showMessageDialog(null, "Can't connect to Database or Server Error");
    }
    public CatchConnectionError(String message){
        System.out.println(message);
    }
    public void catchError(){
       JOptionPane.showMessageDialog(null, "Can't connect to Database or Server Error");
    }
}
