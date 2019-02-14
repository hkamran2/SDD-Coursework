/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
public class testDataQualityCheck extends junit.framework.TestCase{
    DataQualityCheck object = new DataQualityCheck();
     @Test
     public void testdbWork() throws ClassNotFoundException, SQLException, CatchConnectionError {
    
     ResultSet rs = object.dbWork("select * from `crimedata`;");
     boolean actual = rs.first();
     boolean expected = true;
     assertEquals(expected,actual);
     }
     
     public void testtransferRecordsWithNoCrimeID() throws CatchConnectionError, SQLException{
       object.transferRecordsWithNoCrimeID();
       String fileName =  "nocrimeid.txt";
 File file = new File( fileName );
 boolean expected = false;
 boolean actual = true;
 if(file.exists()){
     expected = true;
 }
 assertEquals(actual,expected);
     }
public void testtransferRecordsWithDuplicateIO() throws CatchConnectionError, SQLException, FileNotFoundException, IOException{
 object.transferRecordsWithDuplicateIO();
 
 String fileName =  "duplicatecrimeid.txt";
 File file = new File( fileName );
 String expected = "--------------------------------------------------------------------";
 String actual = "";
 BufferedReader readFile = new BufferedReader(new FileReader(file));
  actual = readFile.readLine();
   assertEquals(expected,actual);
}    
     
}
