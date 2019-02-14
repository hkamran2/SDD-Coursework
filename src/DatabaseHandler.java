/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Hamza Kamran
 */
import java.sql.*;

public class DatabaseHandler {

    public Connection handleDbConnection() throws CatchConnectionError {
        Connection establishConnection = null;
        try {
            String username = Credentianls.UserName;
            String password = Credentianls.Password;
            String dbName = Credentianls.DbName;
            Class.forName("com.mysql.jdbc.Driver");
            establishConnection = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + dbName, username, password);
        } catch (ClassNotFoundException cnfe) {
            System.out.println("Class not found");
        } catch (SQLException sql) {
            System.out.println("Cant establish the connection or error in the Query");
        } finally {
            if (establishConnection == null) {
                throw new CatchConnectionError();
            } else {
                return establishConnection;
            }
        }
    }

    public class Credentianls {

        public final static String UserName = "root";
        public final static String Password = "password";
        public final static String DbName = "crimedata";
    }
}
