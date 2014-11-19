package project2;
import java.awt.*; 
import java.awt.event.*; 
import java.util.*; 
import javax.swing.*;
import project2.xmlparser.*;

public class StarMap extends JFrame {
    private Container contents; 
    private boolean _showConstellations = true; 
    private xmlparser _parser; 
    private StarPosition _position; 
    private int _minMagnitude = 10;
    private int _scrollAmount = 0;
    private double scrollScale = 1;
    private double lat = 44.08, lon = -103.23, azi = 0, alt = 20; 
    private GregorianCalendar cal = new GregorianCalendar(2014, 11, 18, 9, 0, 0);
    
    public StarMap(){
        super("Star Map");  
        
        //parse the xml files
        _parser = new xmlparser(); 
        _position = new StarPosition(); 
        
        for (int i = 0; i < _parser.starList.size(); i++)
        {
            star s = _parser.starList.get(i);
            if (s.starConstellation != null)
            {
                s.hasConstellation = true;
            }
            else 
            {
                s.hasConstellation = false; 
            }
        }
        
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
        
        menuItem = new JMenuItem ("Zoom In");
        menuItem.addActionListener(new ActionListener(){
            public void actionPerformed (ActionEvent ae) {zoomIn(1);}
        });
        menu.add(menuItem);
        
        menuItem = new JMenuItem ("Zoom Out");
        menuItem.addActionListener(new ActionListener(){
            public void actionPerformed (ActionEvent ae) {zoomIn(-1);}
        });
        menu.add(menuItem);
        
        //set the menubar to our menu bar 
        setJMenuBar(menuBar); 
        
        contents = getContentPane();        
        contents.add(new DrawingPane());
        this.pack(); 
        setVisible(true); 
    }
    
    private void changeLocation(){
        _scrollAmount = 0; 
        scrollScale = 1; 
    }
    
    private void toggleConstellations(){
        _showConstellations = !_showConstellations; 
    }
    
    private void changeVisualMagnitude(){
        _minMagnitude = 10; 
    }
    
    private void zoomIn(int i){
        _scrollAmount += i; 
        if (_scrollAmount == 0)
        {
            scrollScale = 1; 
        }
        else if (_scrollAmount < 0)
        {
            scrollScale =  (_scrollAmount + 8 ) * .125;
        }
        else
        {
            scrollScale =  8.0 / (- _scrollAmount + 8.0); 
        }
        repaint();
    }
        
        
   
// drawing panel class
class DrawingPane extends JPanel
{
    private int x1 = 0, x2 = 0;
    private int y1 = 0, y2 = 0; 
    private boolean isDragging;  
    
    
    // constructor
    public DrawingPane()
    {
	// can use any background color for "canvas" (default is white)
	setBackground( Color.black );

        // mouse listener/adapter methods

        // button presses initiate rectangle drawing
        addMouseListener( new MouseAdapter()
        {
            public void mousePressed( MouseEvent e )
            {
                x1 = e.getX();
                y1 = e.getY(); 
                isDragging = true; 
            }
        } );

        // button releases end rectangle drawing
        addMouseListener( new MouseAdapter()
        {
            public void mouseReleased( MouseEvent e )
            {
                x2 = e.getX();
                y2 = e.getY(); 
                isDragging = false; 
                azi = azi - (double)(x2 - x1) / (double)(getSize().width) * 180.0;
                alt = alt + (double) (y2 - y1) / (double)(getSize().height) * 90.0;
                if (alt > 90)
                {
                    alt = 90;
                }
                else if (alt < 0)
                {
                    alt = 0; 
                }
                repaint();
            }
        } );

        // click and drag causes rectangle to be rubberbanded
        addMouseMotionListener( new MouseAdapter()
        {
            public void mouseDragged( MouseEvent e )
            {
                x2 = e.getX();
                y2 = e.getY(); 
                repaint();
            }
        } );
        
        addMouseWheelListener((MouseWheelEvent e)-> 
        {
            //scrollx = e.getX(); 
            //scrolly = e.getY();
            _scrollAmount += e.getWheelRotation();
            if (_scrollAmount == 0)
            {
                scrollScale = 1; 
            }
            else if (_scrollAmount < 0)
            {
                scrollScale =  (_scrollAmount + 8 ) * .125;
            }
            else
            {
                scrollScale =  8.0 / (- _scrollAmount + 8.0); 
            }
            repaint(); 
        });
    }

    // start with 800x600 canvas
    public Dimension getPreferredSize()
    {
        return new Dimension( 800, 600 );
    }

    // repaint: draw the rectangle
    protected void paintComponent( Graphics g )
    {
	// redraw filled oval and latest rubberbanded rectangle
        super.paintComponent( g );		// clear drawing canvas

        Dimension d = this.getSize();
        double temp1 = 0, temp2 = 0; 
        
        if (isDragging)
        {
            // if the user is currently dragging temporarily change the azi 
            temp1 = azi; 
            temp2 = alt; 
            azi = azi - (double)(x2 - x1) / (double)(d.width) * 180.0; 
            alt = alt + (double) (y2 - y1) / (double)(d.height) * 90.0; 
            if (alt > 90)
            {
                alt = 90;
            }
            else if (alt < 0)
            {
                alt = 0; 
            }
        }
        
        int shift =(int)(d.width/ 2 - scrollScale * d.width / 2); 
        
        //what i need to draw
        for (star s: _parser.starList)
        {
            //calculate the point 
            _position.GetPoint(s.ra, s.dec, lat, lon, azi, alt, cal);
            //calculate the approximate mag to show (linear instead of exponential 
            double radius = (8 - s.vmag)/2; 
            
            //draw the star if the star is not clipped. 
            if (_position.Clipped == false && s.vmag <= _minMagnitude)
            {
                //find the corder of the oval 
                int px1 = (int)((_position.X + 1 ) * scrollScale * d.width/2 - radius) + shift; 
                int py1 = d.height - (int)((_position.Y ) * scrollScale * d.height - radius) ; 
                g.setColor(Color.yellow);
                //draw the star (should be circle) 
                g.fillOval(px1, py1, (int)(2* radius), (int)(2* radius));
                s.isVisible = true; 
            }
            else 
            {
                s.isVisible = false; 
            }
        }
        if (_showConstellations)
        {
            int j = -1 , k = -1; 
            int px1, px2, py1, py2; 
            for(constellation c : _parser.constellationList)
            {
                for (int i = 0; i < c.getLineCount(); i++)
                {
                    j = -1; 
                    k = -1; 
                    String[] names = c.getStarSet(i);
                    if (_parser.starMap.containsKey(names[0]))
                        j = _parser.starMap.get(names[0]);
                    if (_parser.starMap.containsKey(names[1]))
                        k = _parser.starMap.get(names[1]); 

                    if (j != -1 && k != -1)
                    {
                        star s = _parser.starList.get(j); 
                        _position.GetPoint(s.ra, s.dec, lat, lon, azi, alt, cal);
                        px1 = (int)((_position.X + 1 ) * scrollScale * d.width/2) + shift; 
                        py1 = d.height - (int)((_position.Y ) * scrollScale * d.height); 

                        if (_position.Clipped == false && s.isVisible == true)
                        {
                            s = _parser.starList.get(k); 
                            _position.GetPoint(s.ra, s.dec, lat, lon, azi, alt, cal);
                            px2 = (int)((_position.X + 1 ) * scrollScale * d.width/2) + shift; 
                            py2 = d.height - (int)((_position.Y ) * scrollScale * d.height);

                            if (_position.Clipped == false && s.isVisible == true)
                            {
                                g.drawLine(px1, py1, px2, py2);
                            }
                        }
                    }
                }
            }
        }
        if (isDragging)
        {
            //change it back if necessary. 
            azi = temp1; 
            alt = temp2; 
        }
    }

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
