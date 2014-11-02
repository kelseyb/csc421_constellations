package starmap;
import javax.swing.*; 
import java.awt.*; 
import java.awt.event.*; 

public class StarMap extends JFrame  {

    private Container contents; 
    
    public StarMap(){
        super("Star Map"); 
        contents = getContentPane(); 
        
        JMenuBar menuBar = new JMenuBar(); 
        JMenu menu = new JMenu("File"); 
        menuBar.add(menu); 
        
        JMenuItem menuItem = new JMenuItem ("Exit"); 
        menuItem.addActionListener(new ActionListener(){
            public void actionPerformed (ActionEvent ae) {System.exit(0);}
        });
        menu.add(menuItem); 
        
        menu = new JMenu ("View"); 
        menuBar.add(menu); 
        
        menuItem = new JMenuItem ("Select Location and Time");
        menuItem.addActionListener(new ActionListener(){
            public void actionPerformed (ActionEvent ae) {changeLocation();}
        });
        menu.add(menuItem); 
        
        JCheckBoxMenuItem checkMenuItem = new JCheckBoxMenuItem("Toggle Constelations");
        checkMenuItem.addActionListener(new ActionListener(){
            public void actionPerformed (ActionEvent ae) {toggleConstellations();}
        });
        checkMenuItem.setSelected(true); 
        menu.add(checkMenuItem); 
        
        menuItem = new JMenuItem ("Select minimum visual magnitude"); 
        menuItem.addActionListener(new ActionListener(){
            public void actionPerformed (ActionEvent ae) {changeVisualMagnitude();}
        });
        menu.add(menuItem); 
        
        setJMenuBar(menuBar); 
        setSize(640, 480); 
        setVisible(true); 
    }
    
    private void changeLocation(){   
    }
    
    private void toggleConstellations(){
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
