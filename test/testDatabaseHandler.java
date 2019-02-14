/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
/**
 *
 * @author Hamza Kamran
 */
public class testDatabaseHandler extends junit.framework.TestCase{   
     @Test
     public void testhandleDbConnection() throws ClassNotFoundException, SQLException, CatchConnectionError{
     DatabaseHandler myConnection = new DatabaseHandler();
    Connection DatabaseHandlerconnect = myConnection.handleDbConnection();
    String actual = DatabaseHandlerconnect.getCatalog();
    String expected = DatabaseHandler.Credentianls.DbName;   
    assertEquals(expected,actual);
     }
}
