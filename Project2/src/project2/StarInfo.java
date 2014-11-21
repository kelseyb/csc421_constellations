/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package project2;
import java.awt.*; 
import java.awt.event.*; 
import java.util.*; 
import javax.swing.*;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author 7047960
 */
public class StarInfo extends JPanel {
    
    public JLabel HRNumber; 
    public JLabel Name; 
    public JLabel CommonName; 
    public JLabel Constellation; 
    public JLabel RA; 
    public JLabel Dec; 
    public JLabel Mag; 
    public JLabel Class; 
    
    
    StarInfo()
    {
        String unknown = "???";
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        
        JLabel label = new JLabel("CurrentStar"); 
        Font f = label.getFont();
        Font g = new Font(f.getName(), Font.BOLD, f.getSize()); 
        label.setFont(g);
        add(label); 
        
        label = new JLabel("\nHR Number:"); 
        label.setFont(g);
        label.setBorder(new EmptyBorder(10, 0, 0, 0));
        add(label); 
        
        HRNumber = new JLabel(unknown);
        add(HRNumber); 
        
        label = new JLabel("Name:"); 
        label.setFont(g);
        label.setBorder(new EmptyBorder(10, 0, 0, 0));
        add(label); 
        
        Name = new JLabel(unknown); 
        add(Name); 
        
        label = new JLabel("Common Name:"); 
        label.setFont(g);
        label.setBorder(new EmptyBorder(10, 0, 0, 0));
        add(label);
        
        CommonName = new JLabel(unknown); 
        add(CommonName); 

        
        label = new JLabel("Constellation:"); 
        label.setFont(g);
        label.setBorder(new EmptyBorder(10, 0, 0, 0));
        add(label); 
        
        Constellation = new JLabel(unknown); 
        add(Constellation); 
        
        
        label = new JLabel("Right Ascension:"); 
        label.setFont(g);
        label.setBorder(new EmptyBorder(10, 0, 0, 0));
        add(label); 
        
        RA = new JLabel(unknown); 
        add(RA); 
        
        label = new JLabel("Declination:"); 
        label.setFont(g);
        label.setBorder(new EmptyBorder(10, 0, 0, 0));
        add(label); 
        
        Dec = new JLabel(unknown);
        add(Dec); 
        
        
        label = new JLabel("Magnitude:"); 
        label.setFont(g);
        label.setBorder(new EmptyBorder(10, 0, 0, 0));
        add(label); 
        
        Mag = new JLabel(unknown); 
        add(Mag); 
        
        
        label = new JLabel("Class:"); 
        label.setFont(g);
        label.setBorder(new EmptyBorder(10, 0, 0, 0));
        add(label); 
        
        Class = new JLabel(unknown); 
        add(Class); 
        
        setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        
    }
    
    
}
