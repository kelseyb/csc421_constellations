/*
 *  XMLParser Class: 
 *  This contains code for parsing xmls, constellation class, and star class. 
 *  When it gets the information out of the xml, it puts that information into 
 *  an instance of a star or constellation, which is then added to a 
 *  comprehencive list.
 */
package project2;

import org.jdom2.*;
import org.jdom2.input.SAXBuilder;
import java.io.IOException;
import java.util.*;

/**
 * @author Kelsey Bellew
 * Parses the information found in xml files and stores them into star or 
 * constellation classes, accordingly.
 */
public class xmlparser {

    public static boolean isStar = false;
        
    public static List<star> starList = new ArrayList<star>();
    public static List<constellation> constellationList = new ArrayList<constellation>();
    public static Map<String, Integer> starMap = new HashMap<String, Integer>(); 
    
    private static constellation tempConstellation;
    private static star tempStar;

    /**
    * @author Caitlin Taggart and Kelsey Bellew
    * Constructor for the xml parser class. It calls the function to parse 
    * through the star file, and then the constellation file.
    */ 
    public xmlparser(){
        generateStar("stars.xml");
        generateStar("constellations.xml");
        for (int i = 0; i < starList.size(); i++)
        {
            starMap.put(starList.get(i).name + "-" + starList.get(i).starConstellation, i);
            if (starList.get(i).commonName != null)
            {
                starMap.put(starList.get(i).commonName + "-" + starList.get(i).starConstellation, i);
            }
        }
    }
    
    /**
    * @author Caitlin Taggart
    * ?
    * 
    * @param xmlFiles - A list of strings containing the xml files to be parsed.
    */ 
    public xmlparser(String[] xmlFiles){
        for(String s : xmlFiles)
        {
            generateStar(s); 
        }
    }
    
    /**
    * @author Caitlin Taggart and Kelsey Bellew
    * Star class to hold all the information associated with a given star.
    */ 
    public static class star implements Comparable
    {
        public int hrnumber;
        public String name;
        public String starConstellation;
        
        public double ra;
        public double dec;
        
        public double vmag;
        public String starClass;
        public String commonName;
        public boolean isVisible = false; 
        
        public int compareTo(star comp)
        {
            return name.compareTo(comp.name); 
        }
        
        public int compareTo(String s)
        {
            return name.compareTo(s); 
        }

        @Override
        public int compareTo(Object t) {
            if ( !( t instanceof star ) )
                throw new ClassCastException( "Incompatible data type: " + t + " (not a star object)" );
            
            star s = (star) t; 
            return this.compareTo(s.name);
        }
    
    };
    
    /**
    * @author Kelsey Bellew
    * Constellation class to hold all the information associated with a given 
    * constellation.
    */ 
    static class constellation
    {
        private String name;
        private String abbr;
        
        private Vector<String> fromStar;
        private Vector<String> toStar;
        
        //constructor
        public constellation(String name)
        {
            this.name = name;
            abbr = "";
            toStar = new Vector<String>();
            fromStar = new Vector<String>();
        }
        public String getName(){return name;}
        public String getAbbr(){return abbr;}
        public int getLineCount(){return fromStar.size();}
        public String[] getStarSet(int i)
        {
            String[] starSet = {fromStar.get(i), toStar.get(i)};
            return starSet;
        }
        
        public void setAbbr(String newAbbr){abbr = newAbbr;}
        
        public void addStarSet(String from, String to)
        {
            fromStar.add(from);
            toStar.add(to);
        }
    };
    
    /**
    * @author Kelsey Bellew (using code from csc421 website)
    * Takes in the name of an xml file and sets up the xml file to be read.
    * 
    * @param xml - The string containing the xml name.
    */
    public static void generateStar(String xml)
    {
        //reset isStar
        isStar = false;
        
        // read and parse XML document
        SAXBuilder builder = new SAXBuilder();
        try
        {
            Document doc = builder.build(xml);          // parse XML tags
            Element root = doc.getRootElement();	// get root of XML tree
            parseChildren( root);			// print info in XML tree
            
        }
        // JDOMException indicates a well-formedness error
        catch ( JDOMException e )
        {
            System.out.println( xml + " is not well-formed." );
            System.out.println( e.getMessage() );
        }
        catch ( IOException e )
        {
            System.out.println( e );
        }
    }
    
    /**
    * @author Kelsey Bellew (using code from csc421 website)
    * Goes through the xml file, looking at the name of the current object to 
    * decide what kind of object it is. It then uses the correct parsing 
    * function.
    * 
    * @param current - the current line of the xml
    */
    public static void parseChildren( Element current)
    {
	// get children of current node
        List children = current.getChildren();
        Iterator iterator = children.iterator();
        
        if(current.getName() == "star")
        {
            isStar = true;
            parseStar(current);
            starList.add(tempStar);
        }
        else if(current.getName() == "constellation" && isStar == false)
        {
            parseConstellation(current);
            constellationList.add(tempConstellation);
        }
              
        while ( iterator.hasNext() )
        {
            Element child = ( Element ) iterator.next();
            parseChildren( child);
        }
    }
        
    /**
    * @author Kelsey Bellew (using code from csc421 website)
    * Goes through a constellation node and puts the information found there 
    * into an instance of a constellation class. It then adds that instance 
    * to the comprehensive list.
    * 
    * @param current - the current line of the xml
    */
    public static void parseConstellation(Element current)
    {
        // get children of current node
        List children = current.getChildren();
        Iterator iterator = children.iterator();

        //for constellations        
        if(!iterator.hasNext())
        {
            if(current.getName() == "name")
            {
                tempConstellation = new constellation(current.getValue());
            }
            else if(current.getName() == "abbr")
            {
                tempConstellation.setAbbr(current.getValue().replaceAll("\\s+",""));
            }
            else if(current.getName() == "line")
            {
                String[] lines = current.getValue().split("\\s+");

                tempConstellation.addStarSet(lines[1], lines[3]);
            }
        }
        
        // recursively process each child node
        while ( iterator.hasNext() )
        {
            Element child = (Element)iterator.next();
            parseConstellation(child);
        }
    }
    
    /**
    * @author Kelsey Bellew (using code from csc421 website)
    * Goes through a star node and puts the information found there into an 
    * instance of a star class. It then adds that instance to the 
    * comprehensive list.
    * 
    * @param current - the current line of the xml
    */
    public static void parseStar(Element current)
    {
        // get children of current node
        List children = current.getChildren();
        Iterator iterator = children.iterator();
        
        if ( !iterator.hasNext() )
        {
            if(current.getName() == "HRnumber")
            {
                tempStar = new star();
                int t = Integer.parseInt(current.getValue().toString());
                tempStar.hrnumber = t;
            }
            else if(current.getName() == "name")
            {
                tempStar.name = current.getValue().replaceAll("\\s+","");
            }
            else if(current.getName() == "constellation")
            {
                tempStar.starConstellation = current.getValue().replaceAll("\\s+","");
            }
            else if(current.getName() == "ra")
            {
                String[] lines = current.getValue().split("\\s+");
                double hr = 0, min = 0, sec = 0; 
                
                if (lines.length >= 1)
                {
                    hr = Double.parseDouble(lines[0]);
                }
                if (lines.length >= 2)
                {
                    min = Double.parseDouble(lines[1]);
                }
                if (lines.length >= 3)
                {
                    sec = Double.parseDouble(lines[2]);
                }
                
                tempStar.ra = Math.toRadians( ( hr + min / 60 + sec / 3600 ) * 15 );
            }
            else if(current.getName() == "dec")
            {
                String[] lines = current.getValue().split("\\s+");
                
                double deg = 0, min = 0, sec = 0; 
                if (lines.length >= 1)
                {
                    deg = Double.parseDouble(lines[0]);
                }
                if (lines.length >= 2)
                {
                    min = Double.parseDouble(lines[1]);
                }
                if (lines.length >= 3)
                {
                    sec = Double.parseDouble(lines[2]);
                }
                
                tempStar.dec = Math.toRadians( Math.abs( deg ) + min / 60 + sec / 3600 );
                if ( deg < 0 ) tempStar.dec = -tempStar.dec;
            }
            else if(current.getName() == "vmag")
            {
                tempStar.vmag = Double.parseDouble(current.getValue());
            }
            else if(current.getName() == "class")
            {
                tempStar.starClass = current.getValue();
            }
            else if(current.getName() == "common_name")
            {
                tempStar.commonName = current.getValue();
            }
        }
        
        // recursively process each child node
        while (iterator.hasNext())
        {
            Element child = (Element) iterator.next();
            if(child.getName() == "star")
                return;
            parseStar(child);
        }
    }
}
