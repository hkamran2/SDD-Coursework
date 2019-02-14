/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

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
public class testGUIImplementation extends junit.framework.TestCase{   
    GUIImplementation gui = new GUIImplementation();
     @Test
     public void testCheckEmptyLSOANameWhenPressedYes() { 
    String expectedQuery = "SELECT * FROM `crimedata` WHERE `LSOA name` = '' LIMIT 10;";
    String expectedWhenPressedNo = "";
    String actualQuery = gui.checkEmpty("", "SELECT * FROM `crimedata`");
    boolean actual = expectedQuery == actualQuery || expectedWhenPressedNo == actualQuery;
    boolean expected = true;
    assertEquals(expected, actual);
     }    
}
