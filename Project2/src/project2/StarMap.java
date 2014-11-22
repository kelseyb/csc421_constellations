package project2;
import java.awt.*; 
import java.awt.event.*; 
import java.util.*; 
import javax.swing.*;
import project2.xmlparser.*;
import java.text.Format;
import java.text.SimpleDateFormat;

public class StarMap extends JFrame {
    private Container contents; 
    private boolean _showConstellations = true; 
    private xmlparser _parser; 
    private StarPosition _position; 
    private double _minMagnitude = 10;
    private int _scrollAmount = 0;
    private double scrollScale = 1;
    private StarInfo info; 
    private double lat = 44.08, lon = -103.23, azi = 0, alt = 20; 
    private GregorianCalendar cal = new GregorianCalendar(2014, 11, 18, 9, 0, 0);
    
    public StarMap(){
        super("Star Map");  
        
        //parse the xml files
        _parser = new xmlparser(); 
        _position = new StarPosition(); 
        
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
        info = new StarInfo(); 
        contents.add(info, BorderLayout.WEST); 
        this.pack(); 
        setVisible(true); 
    }
    
    private void changeLocation(){
        _scrollAmount = 0; 
        scrollScale = 1; 
        
        //initialize frame
        JFrame myframe = new JFrame();
        myframe.setTitle("Change Location and Date");
        
        //declare layouts
        GridLayout grid = new GridLayout(6, 1, 5, 5); 
        FlowLayout f1 = new FlowLayout(FlowLayout.CENTER, 5, 5);
        FlowLayout f2 = new FlowLayout(FlowLayout.CENTER, 5, 5);
        FlowLayout f3 = new FlowLayout(FlowLayout.CENTER, 5, 5);
        FlowLayout f4 = new FlowLayout(FlowLayout.CENTER, 5, 5);
        FlowLayout f5 = new FlowLayout(FlowLayout.CENTER, 5, 5);
        FlowLayout f6 = new FlowLayout(FlowLayout.CENTER, 5, 5);
        FlowLayout f7 = new FlowLayout(FlowLayout.CENTER, 5, 5);

        //make panels for layouts to go into
        JPanel p1 = new JPanel();
        JPanel p2 = new JPanel();
        JPanel p3 = new JPanel();
        JPanel p4 = new JPanel();
        JPanel p5 = new JPanel();
        JPanel p6 = new JPanel();
        
        //set panel layouts
        p1.setLayout(f1);
        p2.setLayout(f2);
        p3.setLayout(f3);
        p4.setLayout(f4);
        p5.setLayout(f5);
        p6.setLayout(f6);
        
        //set frame layout
        myframe.getContentPane().setLayout(grid);
        
        //initialize date and time text fields
        JFormattedTextField dateTextField = new JFormattedTextField(GregorianCalendar.getInstance().getTime());

        Format timeFormat = new SimpleDateFormat("HH:mm:ss");
        JFormattedTextField timeTextField = new JFormattedTextField(timeFormat);
        timeTextField.setValue(cal.getTime());
                
        //initialize labels
        JLabel dateLabel = new JLabel("Set Date: ");
        dateLabel.setLabelFor(dateTextField);
        
        JLabel timeLabel = new JLabel("     Set Time: ");
        timeLabel.setLabelFor(timeTextField);
        
        //initialize Latitude slider
        if(lat>90) lat = 90;
        if(lat<0) lat = 0;
        JSlider latSlider = new JSlider(-90, 90, (int)lat);
        latSlider.setMajorTickSpacing(10);
        latSlider.setMinorTickSpacing(5);
        latSlider.setPaintTicks(true);
        latSlider.setPaintLabels(true);
        latSlider.setPreferredSize(new Dimension(500, 50));
        
        //initialize Longitude slider
        if(lon>180) lon = 180;
        if(lon<-180) lon = -180;
        JSlider lonSlider = new JSlider(-180, 180, (int)lon);
        lonSlider.setMajorTickSpacing(20);
        lonSlider.setMinorTickSpacing(10);
        lonSlider.setPaintTicks(true);
        lonSlider.setPaintLabels(true);
        lonSlider.setPreferredSize(new Dimension(500, 50));
        
        //initialize Altitude slider
        if(alt>90) alt = 90;
        if(alt<0) alt = 0;
        JSlider altSlider = new JSlider(0, 90, (int)alt);
        altSlider.setMajorTickSpacing(10);
        altSlider.setMinorTickSpacing(5);
        altSlider.setPaintTicks(true);
        altSlider.setPaintLabels(true);
        altSlider.setPreferredSize(new Dimension(500, 50));
        
        //initialize Azimuth slider
        //System.out.println("azi:"+azi);
        if(azi > 360) azi = 360;
        if(azi < 0) azi = 0;
        JSlider aziSlider = new JSlider(0, 360, (int)azi); 
        aziSlider.setMajorTickSpacing(20);
        aziSlider.setMinorTickSpacing(10);
        aziSlider.setPaintTicks(true);
        aziSlider.setPaintLabels(true);
        aziSlider.setPreferredSize(new Dimension(500, 50));
        
        //initialize labels
        JLabel latLabel = new JLabel("Latidute: ");
        latLabel.setLabelFor(latSlider);
        JLabel lonLabel = new JLabel("Longitude: ");
        lonLabel.setLabelFor(lonSlider);
        
        JLabel altLabel = new JLabel("Altitude: ");
        altLabel.setLabelFor(altSlider);
        JLabel aziLabel = new JLabel("Azimuth: ");
        aziLabel.setLabelFor(aziSlider);
        
        //initialize Apply button
        JButton btnApply = new JButton("Apply");
        
        //handle button click
        btnApply.addActionListener((ActionEvent e) -> {
            GregorianCalendar tempCal = new GregorianCalendar(((Date)dateTextField.getValue()).getYear(),
                    ((Date)dateTextField.getValue()).getMonth(),
                    ((Date)dateTextField.getValue()).getDate(),
                    ((Date)timeTextField.getValue()).getHours(),
                    ((Date)timeTextField.getValue()).getMinutes(),
                    ((Date)timeTextField.getValue()).getSeconds());
            
            cal.setTime(tempCal.getTime());
            //System.out.println("cal: " + cal);
            
            lat = latSlider.getValue();
            lon = lonSlider.getValue();
            alt = altSlider.getValue();
            azi = aziSlider.getValue();
            
            System.out.println(cal);
            repaint();
            myframe.dispose();
        });
        
        //add labels, sliders, and text fields to the correct panel
        p1.add(dateLabel);
        p1.add(dateTextField);
        
        p1.add(timeLabel);
        p1.add(timeTextField);
        
        p2.add(latLabel);
        p2.add(latSlider);
        p3.add(lonLabel);
        p3.add(lonSlider);
        
        p4.add(altLabel);
        p4.add(altSlider);
        p5.add(aziLabel);
        p5.add(aziSlider);
        
        p6.add(btnApply);
        
        //add panels to frame
        myframe.getContentPane().add(p1);
        myframe.getContentPane().add(p2);
        myframe.getContentPane().add(p3);
        myframe.getContentPane().add(p4);
        myframe.getContentPane().add(p5);
        myframe.getContentPane().add(p6);

        myframe.pack(); 
        myframe.setVisible(true);
        
    }
    
    private void toggleConstellations(){
        _showConstellations = !_showConstellations; 
        repaint(); 
    }
    
    private void changeVisualMagnitude(){
        
        //initizliae frame
        JFrame myframe = new JFrame();
        myframe.setTitle("Change Visual Magnitude");
        myframe.setSize(new Dimension(400, 150));
        myframe.getContentPane().setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
       
        //check that _minMagnitude is not outside the bounds of ths slider
        if(_minMagnitude == 10)
            _minMagnitude = 6;

        //initialize slider
        JSlider slider = new JSlider(-2, 6, (int)_minMagnitude);
        slider.setMajorTickSpacing(2);
        slider.setMinorTickSpacing(1);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        
        //initialize label
        JLabel minLabel = new JLabel("Minimum Magnitude: "); //is this correct?
        minLabel.setLabelFor(slider);
        
        //initialize button
        JButton btnApply = new JButton("Apply");
        
        //handle button click
        btnApply.addActionListener((ActionEvent e) -> {
            //set _minMagnitude to new value
            _minMagnitude = (int)slider.getValue();
            System.out.println(_minMagnitude);
            repaint();
            myframe.dispose();
        });
        
        //add label, slider, and button to frame
        myframe.getContentPane().add(minLabel);
        myframe.getContentPane().add(slider);
        myframe.getContentPane().add(btnApply);

        myframe.setVisible(true);
        repaint(); 
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
    private int currentStar; 
    
    
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
        });
                
        addMouseMotionListener( new MouseAdapter()
        {
            
            public void mouseMoved (MouseEvent e)
            {
                Dimension d = getSize(); 
                int currentGuess = -1; 
                double dist = 0;
                double min_dist = 100000; 
                int shift = (int)(d.width/ 2 - scrollScale * d.width / 2); 
                
                for (star s: _parser.starList)
                {
                    //calculate the point 
                    _position.GetPoint(s.ra, s.dec, lat, lon, azi, alt, cal); 
            
                    //draw the star if the star is not clipped. 
                    if (_position.Clipped == false && s.vmag <= _minMagnitude)
                    {
                        //find the position of the star
                        int px1 = (int)((_position.X + 1 ) * scrollScale * d.width/2) + shift; 
                        int py1 = d.height - (int)((_position.Y ) * scrollScale * d.height - 3) ;
                        dist = (px1 - e.getX()) * (px1 - e.getX()) + (py1 - e.getY()) * (py1 - e.getY()); 
                        if (dist < min_dist && dist < 36)
                        {
                            min_dist = dist; 
                            currentGuess = _parser.starList.indexOf(s); 
                        }
                    }
                }
                if (currentGuess != -1)
                {
                    currentStar = currentGuess; 
                    ChangeStarInfo(currentStar);
                    System.out.printf(_parser.starList.get(currentStar).name + " \n"); 
                }
            }
        });
        
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
        double temp1 = 0, temp2 = 0; 
        Dimension d = this.getSize();
        
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
        
        DrawStar(g);
        DrawConstellations(g); 
        
        if (isDragging)
        {
            //change it back if necessary. 
            azi = temp1; 
            alt = temp2; 
        }
    }
    
    private void DrawStar(Graphics g)
    {
        Dimension d = this.getSize();
        int shift =(int)(d.width/ 2 - scrollScale * d.width / 2); 
        
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
                g.setColor(Color.blue);
                if (s.commonName != null && s.vmag < 3)
                g.drawString(s.commonName, (int)( px1 + 2 * radius), (int)(py1 + 2 * radius));
            }
        }
    }
    
    private void ChangeStarInfo(int currentStar)
    {
        star s = _parser.starList.get(currentStar); 
        info.CommonName.setText(s.commonName);
        Integer t = s.hrnumber;
        info.HRNumber.setText(t.toString()); 
        info.Name.setText(s.name);
        Double d = s.ra; 
        d = Math.toDegrees(d); 
        info.RA.setText(String.format("%4.2f degrees", d));
        d = s.dec; 
        d = Math.toDegrees(d);
        info.Dec.setText(String.format("%4.2f degrees", d)); 
        d = s.vmag; 
        info.Mag.setText(d.toString()); 
        info.Constellation.setText(s.starConstellation); 
        info.Class.setText(s.starClass);
    }
    
    private void DrawConstellations(Graphics g)
    {
        Dimension d = this.getSize();
        int shift =(int)(d.width/ 2 - scrollScale * d.width / 2); 
        
        if (_showConstellations)
        {
            int j, k; 
            int px1, px2, py1, py2; 
            for(constellation c : _parser.constellationList)
            {
                //go through each line in the constellation draw it. 
                for (int i = 0; i < c.getLineCount(); i++)
                {
                    j = -1; 
                    k = -1; 
                    String[] names = c.getStarSet(i);
                    //prepare the map string (name of the star - constellations abbr.)
                    for (int n = 0; n < names.length; n++)
                    {
                        names[n] = names[n] + "-" + c.getAbbr(); 
                    }
                    //find the index of the star if it is in the list
                    if (_parser.starMap.containsKey(names[0]))
                        j = _parser.starMap.get(names[0]);
                    if (_parser.starMap.containsKey(names[1]))
                        k = _parser.starMap.get(names[1]); 

                    //if both were found draw it
                    if (j != -1 && k != -1)
                    {
                        //find the point of the first star
                        star s = _parser.starList.get(j); 
                        double radius = (8 - s.vmag)/2; 
                        _position.GetPoint(s.ra, s.dec, lat, lon, azi, alt, cal);
                        px1 = (int)((_position.X + 1 ) * scrollScale * d.width/2) + shift; 
                        py1 = d.height - (int)((_position.Y ) * scrollScale * d.height - radius - 1); 

                        //if it is on screen move to the next star 
                        if (_position.Clipped == false)
                        {
                            //find the point the second star
                            s = _parser.starList.get(k); 
                            radius = (8 - s.vmag)/2;
                            _position.GetPoint(s.ra, s.dec, lat, lon, azi, alt, cal);
                            px2 = (int)((_position.X + 1 ) * scrollScale * d.width/2) + shift; 
                            py2 = d.height - (int)((_position.Y ) * scrollScale * d.height - radius - 1);

                            //if it is on screen draw the line. 
                            if (_position.Clipped == false)
                            {
                                g.setColor(Color.white);
                                g.drawLine(px1, py1, px2, py2);
                                g.setColor(Color.GRAY);
                                if (i == 0)
                                {
                                    g.drawString(c.getName(), (int)(px1 + 2 * radius),(int)(py1 - 2 * radius)); 
                                }
                            }
                        }
                    }
                }
            }
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
