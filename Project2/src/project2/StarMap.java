package project2;
import javax.swing.*; 
import java.awt.*; 
import java.awt.event.*; 

public class StarMap extends JFrame  {

    private Container contents; 
    private boolean _showConstellations; 
    private xmlparser _parser; 
    
    public StarMap(){
        super("Star Map"); 
        contents = getContentPane(); 
        
        //parse the xml files
        _parser = new xmlparser(); 
        
        //add a file menu
        JMenuBar menuBar = new JMenuBar(); 
        JMenu menu = new JMenu("File"); 
        menuBar.add(menu); 
        
        //add exit button to the file menu
        JMenuItem menuItem = new JMenuItem ("Exit"); 
        menuItem.addActionListener(new ActionListener(){
            public void actionPerformed (ActionEvent ae) {System.exit(0);}
        });
        menu.add(menuItem); 
        
        //add a view menu 
        menu = new JMenu ("View"); 
        menuBar.add(menu); 
        
        //add a change location and time to menu
        menuItem = new JMenuItem ("Select Location and Time");
        menuItem.addActionListener(new ActionListener(){
            public void actionPerformed (ActionEvent ae) {changeLocation();}
        });
        menu.add(menuItem); 
        
        //toggle constellations to the menu 
        JCheckBoxMenuItem checkMenuItem = new JCheckBoxMenuItem("Toggle Constelations");
        checkMenuItem.addActionListener(new ActionListener(){
            public void actionPerformed (ActionEvent ae) {toggleConstellations();}
        });
        checkMenuItem.setSelected(true); 
        menu.add(checkMenuItem); 
        
        //select magnitude to the menu. 
        menuItem = new JMenuItem ("Select minimum visual magnitude"); 
        menuItem.addActionListener(new ActionListener(){
            public void actionPerformed (ActionEvent ae) {changeVisualMagnitude();}
        });
        menu.add(menuItem); 
        
        //set the menubar to our menu bar 
        setJMenuBar(menuBar); 
        setSize(640, 480); 
        setVisible(true); 
    }
    
    private void changeLocation(){   
    }
    
    private void toggleConstellations(){
        _showConstellations = !_showConstellations; 
    }
    
    private void changeVisualMagnitude(){
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
       StarMap app = new StarMap(); 
       app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
       app.setVisible(true);
    }
    
}
