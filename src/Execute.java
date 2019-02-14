
import java.sql.SQLException;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Hamza Kamran
 */
public class Execute {
    public static void main(String[] args) {
       try{
        GUIImplementation GUI = new GUIImplementation();
        DataQualityCheck writeFiles = new DataQualityCheck();
        writeFiles.transferRecordsWithNoCrimeID();
        writeFiles.transferRecordsWithDuplicateIO();
    }catch(CatchConnectionError e){
           System.out.println("Error with data base connection");
    }catch(SQLException ex){
           System.out.println("Error with data base connection");
    }
}
}
