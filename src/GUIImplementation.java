
/**
 *
 * @author Hamza Kamran
 */
import javax.swing.*;
import java.sql.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.table.DefaultTableModel;
//mport net.proteanit.sql.DbUtils;

public class GUIImplementation implements ActionListener {

    private Connection con = null;
    private Statement stat = null;
    private ResultSet rs = null;
    GUIHandler gui;

    public GUIImplementation() {
        gui = new GUIHandler();
        gui.createGUI();
        createActionListners();
    }

    private void createActionListners() {
        gui.LSOANameCheckBox.addActionListener(this);
        gui.lalCheckBox.addActionListener(this);
        gui.latitudeCheckBox.addActionListener(this);
        gui.longitudeCheckBox.addActionListener(this);
        gui.crimeTypeCheckBox.addActionListener(this);
        gui.searchButton.addActionListener(this);
    }

    public ResultSet ExecuteQuery(String query) throws SQLException, CatchConnectionError {
        DatabaseHandler connection = new DatabaseHandler();
        con = connection.handleDbConnection();
        stat = con.createStatement();
        rs = stat.executeQuery(query);
        return rs;
    }

    public void FillTable(JTable table, String Query) throws CatchOtherExceptions, CatchConnectionError {
        try {
            this.ExecuteQuery(Query);
            DefaultTableModel tableModel = new DefaultTableModel();

            //Retrieve meta data from ResultSet
            ResultSetMetaData metaData = rs.getMetaData();
            //Get number of columns from meta data
            int columnCount = metaData.getColumnCount();
            //Get all column names from meta data and add columns to table model
            for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
                tableModel.addColumn(metaData.getColumnLabel(columnIndex));
            }
            //Create array of Objects with size of column count from meta data
            Object[] row = new Object[columnCount];
            //Scroll through result set
            while (rs.next()) {
                //Get object from column with specific index of result set to array of objects
                for (int i = 0; i < columnCount; i++) {
                    row[i] = rs.getObject(i + 1);
                }
                //Now add row to table model with that array of objects as an argument
                tableModel.addRow(row);
            }
            //Now add that table model to your table and you are done :D
            table.setModel(tableModel);
        } catch (SQLException ex) {
            System.out.println("User selected no");
        } finally {
            try {
                if (con != null) {
                    rs.close();
                    stat.close();
                    con.close();
                }
                if (table.getRowCount() == 0) {
                    throw new CatchOtherExceptions("Your search brought no results OR Please enter correct input!");
                }
            } catch (SQLException ex) {
                System.out.println("Error with connection or query");
            }
        }
    }

    public String checkEmpty(String input, String query) {
        if (input.isEmpty()) {
            int optionChoosen = JOptionPane.showConfirmDialog(null, "You have entered nothing. Select yes if you want empty records to be shown", null, JOptionPane.YES_OPTION);
            if (optionChoosen == 0) {
                String emptyQuery = "SELECT * FROM `crimedata` WHERE `LSOA name` = '' LIMIT 10;";
                return emptyQuery;
            }
        } else {
            return query;
        }
        return "";
    }

    public void searchLatitudeOnly(String input) throws CatchOtherExceptions, CatchConnectionError {
        input = gui.latitudeText.getText();
        String query = "SELECT * FROM `crimedata` WHERE `Latitude` = '" + input + "' LIMIT 10;";
        this.FillTable(gui.table, checkEmpty(input, query));
    }

    public void searchLongitudeOnly(String input) throws CatchOtherExceptions, CatchConnectionError {
        input = gui.longitudeText.getText();
        String query = "SELECT * FROM `crimedata` WHERE `Longitude` = '" + input + "' LIMIT 10;";
        this.FillTable(gui.table, checkEmpty(input, query));
    }

    public String searchByLatitudeAndLongitude(String latInput, String longInput) {
        latInput = gui.latText.getText();
        longInput = gui.longText.getText();
        String query = "SELECT * FROM `crimedata` WHERE `Latitude` = '" + latInput + "' AND `Longitude` = '" + longInput + "' LIMIT 10;";
        if (latInput.isEmpty() && longInput.isEmpty()) {
            int optionChoosen = JOptionPane.showConfirmDialog(null, "You have entered nothing. Select yes if you want empty records to be shown", null, JOptionPane.YES_OPTION);
            if (optionChoosen == 0) {
                String emptyQuery = " SELECT * FROM `crimedata` WHERE `Latitude` = '' AND `Longitude` = '' LIMIT 10 ;";
                return emptyQuery;
            }
        } else {
            return query;
        }
        return "";
    }

    public void searchLSOAName(String input) throws CatchOtherExceptions, CatchConnectionError {
        input = gui.LSOANametext.getText();
        String query = "SELECT * FROM `crimedata` WHERE `LSOA name` LIKE '%" + input + "%' LIMIT 10;";

        this.FillTable(gui.table, checkEmpty(input, query));
    }

    public void searchCrime(String comboBoxInput) throws CatchOtherExceptions, CatchConnectionError {
        comboBoxInput = (String) gui.crimeBox.getSelectedItem();
        String query = "SELECT * FROM `crimedata` WHERE `Crime type` = '" + comboBoxInput + "'  LIMIT 10;";
        this.FillTable(gui.table, query);
    }

    public void searchItems() throws CatchOtherExceptions, CatchConnectionError {
        if (gui.lalCheckBox.isSelected()) {
            this.FillTable(gui.table, this.searchByLatitudeAndLongitude(this.gui.latText.getText(), this.gui.longText.getText()));
        }
        if (gui.LSOANameCheckBox.isSelected()) {
            this.searchLSOAName(gui.LSOANametext.getText());
        }
        if (gui.crimeTypeCheckBox.isSelected()) {
            this.searchCrime((String) gui.crimeBox.getSelectedItem());
        }
        if (gui.longitudeCheckBox.isSelected()) {
            this.searchLongitudeOnly(this.gui.longitudeText.getText());
        }
        if (gui.latitudeCheckBox.isSelected()) {
            this.searchLatitudeOnly(this.gui.latitudeText.getText());
        }
    }

    private JTextField SetEditAble(JCheckBox checkBox, JTextField textField) {
        if (checkBox.isSelected()) {
            textField.setEditable(true);
            textField.setText(null);
        } else {
            textField.setEditable(false);
            textField.setText(null);
        }
        return textField;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            if (e.getSource() == gui.searchButton) {
                this.searchItems();
            }
            JTextField latText = SetEditAble(gui.lalCheckBox, gui.latText);
            latText.setText("Enter lat value");
            JTextField lngText = SetEditAble(gui.lalCheckBox, gui.longText);
            lngText.setText("Enter lng value");
            SetEditAble(gui.LSOANameCheckBox, gui.LSOANametext);
            SetEditAble(gui.longitudeCheckBox, gui.longitudeText);
            SetEditAble(gui.latitudeCheckBox, gui.latitudeText);
        } catch (Exception er) {
        }
    }
}
