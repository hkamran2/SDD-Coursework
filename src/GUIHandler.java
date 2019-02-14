
import javax.swing.*;
import java.awt.*;
import java.sql.*;
import static javax.swing.JFrame.EXIT_ON_CLOSE;

public class GUIHandler extends JFrame {

    public JTable table;
    public JCheckBox LSOANameCheckBox;
    public JCheckBox crimeTypeCheckBox;
    public JButton searchButton;
    public JTextField LSOANametext;
    public JTextField longitudeText;
    public JTextField latitudeText;
    public JTextField longText;
    public JTextField latText;
    public JCheckBox latitudeCheckBox;
    public JCheckBox longitudeCheckBox;
    public JCheckBox lalCheckBox; // latitude & longitude checkbox.
    public JComboBox crimeBox;
    public static Dimension size;
    javax.swing.border.Border border = BorderFactory.createLineBorder(Color.BLUE, 1);

    /* 
  This method deals with the creation of GUI
     */
    public void createGUI() {
        this.setLabel();
        this.setLSOA();
        this.setLatitudeOnly();
        this.setLongitudeOnly();
        this.setLatAndLang();
        this.setCrimeTypes();
        this.setSearchButtons();
        this.groupButtons();
        this.setTable();
        // Sets the title of the JFrame
        setTitle("Search Crime Engine");
//sets the size of the JFrame
        setSize(1000, 590);
        // sets the location of the JFrame on the screen 
        setLocation(200, 100);
        //Absolute layout is being used for this GUI
        setLayout(null);
        // display GUI
        setVisible(true);
        //close on exit
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        //don't let user resize the window
        setResizable(false);
    }

    public void setLabel() {
        JLabel label = new JLabel("Crime Search Engine");
        //set position of the label on JFrame
        label.setBounds(400, 0, 400, 30);
        // sets the font for the label
        label.setFont(new Font("Serif", Font.PLAIN, 25));
        getContentPane().add(label);
        // text field settings
    }

    /*
     Settings for when the user check LSOA name 
     */
    public void setLSOA() {
        LSOANametext = new JTextField();
        LSOANametext.setEditable(false);
        size = LSOANametext.getPreferredSize();
        LSOANametext.setBounds(390, 50, 220, size.height);
        LSOANameCheckBox = new JCheckBox("LSOA name");
        Dimension lsoBoxSize = this.LSOANameCheckBox.getPreferredSize();
        LSOANameCheckBox.setBounds(620, 46, lsoBoxSize.width, lsoBoxSize.height);
        getContentPane().add(this.LSOANametext);
        getContentPane().add(LSOANameCheckBox);
        LSOANametext.setBorder(border);
    }

    /*
    Settings for when the user checks latitude(only)
     */
    public void setLatitudeOnly() {
        this.latitudeText = new JTextField();
        this.latitudeText.setEditable(false);
        latitudeText.setBounds(390, 80, 220, size.height);
        latitudeCheckBox = new JCheckBox("latitude");
        Dimension latitudeBoxSize = this.latitudeCheckBox.getPreferredSize();
        latitudeCheckBox.setBounds(620, 80, latitudeBoxSize.width, latitudeBoxSize.height);
        getContentPane().add(this.latitudeText);
        getContentPane().add(latitudeCheckBox);
        latitudeText.setBorder(border);
    }

    /*
    Settings for when the user checks longitude only
     */
    public void setLongitudeOnly() {
        this.longitudeText = new JTextField();
        this.longitudeText.setEditable(false);
        longitudeText.setBounds(390, 110, 220, size.height);
        longitudeCheckBox = new JCheckBox("longitude");
        Dimension longitudeBoxSize = this.longitudeCheckBox.getPreferredSize();
        longitudeCheckBox.setBounds(620, 110, longitudeBoxSize.width, longitudeBoxSize.height);
        getContentPane().add(this.longitudeText);
        getContentPane().add(longitudeCheckBox);
        longitudeText.setBorder(border);
    }

    /*
    Settings for when the user checks both latitude and longitude option;
     */
    public void setLatAndLang() {
        this.latText = new JTextField();
        this.latText.setText("Enter Latitude here");
        this.latText.setEditable(false);
        latText.setBounds(390, 140, 220, size.height);
        latText.setBorder(border);
        getContentPane().add(this.latText);
        this.longText = new JTextField();
        this.longText.setText("Enter longitude here");
        this.longText.setEditable(false);
        longText.setBounds(390, 170, 220, size.height);
        getContentPane().add(this.longText);
        longText.setBorder(border);
        lalCheckBox = new JCheckBox("longitude & latitude");
        Dimension lalBoxSize = lalCheckBox.getPreferredSize();
        lalCheckBox.setBounds(620, 150, lalBoxSize.width, lalBoxSize.height);
        getContentPane().add(lalCheckBox);
    }

    /*
   Settings for crime type options
     */
    public void setCrimeTypes() {
        String[] crimeTypes = {"Anti-social behaviour", "Drugs", "Violence and sexual offences", "Vehicle crime", "Bicycle theft", "Burglary", "Criminal damage and arson", "Public order", "Shoplifting", "Other theft", "Other crime", "Possession of weapons"};
        this.crimeBox = new JComboBox(crimeTypes);
        Dimension crimeBoxSize = crimeBox.getPreferredSize();
        this.crimeTypeCheckBox = new JCheckBox("Crime Type");
        Dimension priceSize = this.crimeTypeCheckBox.getPreferredSize();
        crimeBox.setBounds(390, 200, 220, crimeBoxSize.height);
        Dimension crimeCheckBoxSize = this.crimeTypeCheckBox.getPreferredSize();
        this.crimeTypeCheckBox.setBounds(620, 200, crimeCheckBoxSize.width, crimeCheckBoxSize.height);
        getContentPane().add(this.crimeBox);
        getContentPane().add(this.crimeTypeCheckBox);
    }

    /*
    Search button settings
     */
    public void setSearchButtons() {
        searchButton = new JButton("Search");
        Dimension searchSize = searchButton.getPreferredSize();
        searchButton.setBounds(640, 250, searchSize.width, searchSize.height);
        getContentPane().add(searchButton);
    }

    /*
     Settings for grouping buttons
     */
    public void groupButtons() {
        ButtonGroup group = new ButtonGroup();
        group.add(this.LSOANameCheckBox);
        group.add(this.crimeTypeCheckBox);
        group.add(this.lalCheckBox);
        group.add(this.latitudeCheckBox);
        group.add(this.longitudeCheckBox);
    }

    /*
Table configuration
     */
    public void setTable() {
        String[] columnNames = {"Crime ID",
            "Month",
            "Reported by",
            "Falls within",
            "Longitude",
            "Latitude",
            "Location",
            "LSOA code",
            "LSOA name",
            "Crime type",
            "Last outcome category",
            "Context"
        };
// view 10 empty values first.
        Object[][] data = new Object[10][12];
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[i].length; j++) {
                data[i][j] = "";
            }
        }
        this.table = new JTable(data, columnNames);
        JScrollPane addTable = new JScrollPane(table);
        Dimension tableSize = addTable.getPreferredSize();
        addTable.setBounds(50, 300, 900, 183);
        getContentPane().add(addTable);
    }
}
