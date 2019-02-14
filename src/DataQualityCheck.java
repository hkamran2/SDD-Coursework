
/**
 *
 * @author Hamza Kamran
 */
import java.io.*;
import java.sql.*;

public class DataQualityCheck {

    public ResultSet dbWork(String Query) throws CatchConnectionError, SQLException {
        Connection con = null;
        Statement stat = null;
        ResultSet rs = null;
        try {
            DatabaseHandler connection = new DatabaseHandler();
            con = connection.handleDbConnection();
            stat = con.createStatement();
            rs = stat.executeQuery(Query);
        } catch (SQLException ex) {
            System.out.println("Error with connection or query");
        } finally {
            if (rs == null) {
                throw new CatchConnectionError("Errow with the connection to data base");
            } else {
                return rs;
            }
        }
    }

    public void writeTextToFile(String fileName, String text, ResultSet rs) {
        PrintWriter outputStream = null;
        try {
            outputStream = new PrintWriter(new FileWriter(fileName));

            while (rs.next()) {

                String month = rs.getString("Month");
                String reportedBy = rs.getString("Reported by");
                String fallsWithin = rs.getString("Falls within");
                String longitude = rs.getString("Longitude");
                String latitude = rs.getString("Latitude");
                String location = rs.getString("Location");
                String LSOACode = rs.getString("LSOA code");
                String LSOAName = rs.getString("LSOA name");
                String crimeType = rs.getString("Crime type");
                String loc = rs.getString("Last outcome category");
                String context = rs.getString("Context");
                outputStream.println("--------------------------------------------------------------------");
                outputStream.println("Month: " + month);
                outputStream.println("Reported By: " + reportedBy);
                outputStream.println("Falls within: " + fallsWithin);
                outputStream.println("Longitude: " + longitude);
                outputStream.println("Latitude: " + latitude);
                outputStream.println("Location: " + location);
                outputStream.println("LSOA code: " + LSOACode);
                outputStream.println("LSOA name: " + LSOAName);
                outputStream.println("Crime type: " + crimeType);
                outputStream.println("Last outcome category: " + loc);
                outputStream.println("Context: " + context);
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
        } catch (IOException e) {
            System.out.println("IO exception");
        } catch (SQLException e) {
            System.out.println("Connection error or query error");
        } finally {
            if (outputStream != null) {
                outputStream.close();
            }
        }
    }

    public void transferRecordsWithNoCrimeID() throws CatchConnectionError, SQLException {
        String empty = "";
        String Query = "SELECT * FROM `crimedata` WHERE `Crime ID`=  '" + empty + "'";
        String fileName = "nocrimeid.txt";
        ResultSet result = this.dbWork(Query);
        if (result == null) {
            throw new CatchConnectionError();
        }
        this.writeTextToFile(fileName, Query, result);
    }

    public void transferRecordsWithDuplicateIO() throws CatchConnectionError, SQLException {
        String Query = "SELECT *\n"
                + "FROM crimedata\n"
                + "INNER JOIN(\n"
                + "SELECT `Crime ID`\n"
                + "FROM crimedata\n"
                + "GROUP BY `Crime ID`\n"
                + "HAVING COUNT(`Crime ID`) >1\n"
                + ")temp ON crimedata.`Crime ID`= temp.`Crime ID`;";
        String fileName = "duplicatecrimeid.txt";
        ResultSet result = this.dbWork(Query);
        this.writeTextToFile(fileName, Query, result);
    }
}
