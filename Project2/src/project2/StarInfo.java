/*
 *  Star Info Class: 
 *  This Contains the panel shown on the left side of the screen of StarMap. It 
 *  displays information about the star that the mouse is currently hovered
 *  over. This includes the stars HR Number, Name, Common Name, associated
 *  constellation, right ascension, declination, magnitude, and class. 
 */

//imports
package project2; 
import javax.swing.*;
import javax.swing.border.EmptyBorder;

/**
 * @author Caitlin Taggart
 * Displays information about the star that the mouse is currently hovered over, 
 * using a JPanel. Information includes HR Number, Name, Common Name, 
 * constellation, right ascension, Declination, magnitude, and class. 
 */
public class StarInfo extends JPanel {
    
    //public labels so the text can easily be set on the objects. 
    public JLabel HRNumber;         //the HR Number text field
    public JLabel Name;             //the name text field 
    public JLabel CommonName;       //the common name text field
    public JLabel Constellation;    //the constellation text field
    public JLabel RA;               //the right ascension text field
    public JLabel Dec;              //the declination text field
    public JLabel Mag;              //the magnitude text field
    public JLabel Class;            //the class text field
    
/**
 * @author Caitlin Taggart
 * Creates the JPanel using JLabels to display information about stars. 
 */ 
    StarInfo()
    {
        String unknown = "???";
        
        //create a box layout to place the labels in
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        
        //create a title label
        JLabel label = new JLabel("Current Star"); 
        add(label); 
        
        //create a HR title label 
        label = new JLabel("HR Number:"); 
        label.setBorder(new EmptyBorder(10, 0, 0, 0)); //add space to the top to make easier to read
        add(label); 
        
        //create the current stars HR field 
        HRNumber = new JLabel(unknown);
        add(HRNumber); 
        
        //create the name title label 
        label = new JLabel("Name:"); 
        //add space to the top to make easier to read
        label.setBorder(new EmptyBorder(10, 0, 0, 0));
        add(label); 
        
        //create the current stars name field 
        Name = new JLabel(unknown); 
        add(Name); 
        
        //create the common name title label
        label = new JLabel("Common Name:"); 
        //add space to the top to make easier to read
        label.setBorder(new EmptyBorder(10, 0, 0, 0));
        add(label);
        
        //create the current stars common name field 
        CommonName = new JLabel(unknown); 
        add(CommonName); 

        //create the constellation name title label
        label = new JLabel("Constellation:"); 
        //add space to the top to make easier to read
        label.setBorder(new EmptyBorder(10, 0, 0, 0));
        add(label); 
        
        //create the current stars constellation field 
        Constellation = new JLabel(unknown); 
        add(Constellation); 
        
        //create the right ascension title label
        label = new JLabel("Right Ascension:"); 
        //add space to the top to make easier to read
        label.setBorder(new EmptyBorder(10, 0, 0, 0));
        add(label); 
        
        //create the current stars right ascension field 
        RA = new JLabel(unknown); 
        add(RA); 
        
        //create the declination title label
        label = new JLabel("Declination:"); 
        //add space to the top to make easier to read
        label.setBorder(new EmptyBorder(10, 0, 0, 0)); 
        add(label); 
        
        //create the current stars declination field 
        Dec = new JLabel(unknown);
        add(Dec); 
        
        //create the magnitude title label
        label = new JLabel("Magnitude:"); 
        //add space to the top to make easier to read
        label.setBorder(new EmptyBorder(10, 0, 0, 0));
        add(label); 
        
        //create the current stars magnitude field 
        Mag = new JLabel(unknown); 
        add(Mag); 
        
        //create the current stars HR field 
        label = new JLabel("Class:"); 
        //add space to the top to make easier to read
        label.setBorder(new EmptyBorder(10, 0, 0, 0));
        add(label); 
        
        //create the current stars class field field 
        Class = new JLabel(unknown); 
        add(Class); 
        
        //add a border so the objects are not squished within. 
        setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        
    } 
}
