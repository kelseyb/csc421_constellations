/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project2;

import org.jdom2.*;
import org.jdom2.input.SAXBuilder;
import java.io.IOException;
import java.util.*;

/**
 *
 * @author 7032956
 */
public class xmlparser {

    public static boolean isStar = false;
        
    public static List<star> starList = new ArrayList<star>();
    public static List<constellation> constellationList = new ArrayList<constellation>();
    
    private static constellation tempConstellation;
    private static star tempStar;

    //empty constructor just parses a star file and the constellations file 
    public xmlparser(){
        generateStar("stars_mag4.xml");
        generateStar("constellations.xml");
    }
    
    public xmlparser(String[] xmlFiles){
        for(String s : xmlFiles)
        {
            generateStar(s); 
        }
    }
    
    public static class star
    {
        public double hrnumber;
        public String name;
        public String starConstellation;
        
        public double ra;
        public double dec;
        
        public double vmag;
        public String starClass;
        public String commonName;
    
    };
    
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
    
    //possibly pass in star/constellation struct
    public static void generateStar(String xml)
    {
        //reset isStar
        isStar = false;
        
        // read and parse XML document
        SAXBuilder builder = new SAXBuilder();
        try
        {
            Document doc = builder.build(xml);// args[0] );	// parse XML tags
            Element root = doc.getRootElement();	// get root of XML tree
            parseChildren( root, 0 );			// print info in XML tree
            
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
    
    // print XML tags and leaf node values
    public static void parseChildren( Element current, int depth )
    {
	// get children of current node
        List children = current.getChildren();
        Iterator iterator = children.iterator();
        
        if(current.getName() == "star")
        {
            isStar = true;
//            System.out.print( "   printing star yaya \n");
            parseStar(current);
            
//            System.out.print("adding star to star list\n");
            starList.add(tempStar);
//            System.out.println(tempStar.name+":"+tempStar.commonName+":"+tempStar.dec+":"+tempStar.ra+":"+tempStar.starClass+":"+tempStar.starConstellation+":"+tempStar.vmag+";;\n");
            
        }
        else if(current.getName() == "constellation" && isStar == false)
        {
//            System.out.print( "   printing constellation yaya \n");
            parseConstellation(current);
            constellationList.add(tempConstellation);
//            System.out.println(tempConstellation.getName()+":"+tempConstellation.getAbbr()+":"+tempConstellation.getLineCount()+":"+tempConstellation.getStarSet(0)[0]+";;\n");
        }
              
        // recursively process each child node
        //hmmmm. this seems to do alot of extra work. hmmmmmmmmm.
        //not that much extra work? additionally, if we don't have /something/ here, we only get one branch of a thing.
        while ( iterator.hasNext() )
        {
            Element child = ( Element ) iterator.next();
            parseChildren( child, depth + 1 );
        }
    }
    
    //i dunno if this is gonna work so well.
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
//                System.out.print( "name: " + current.getName() +" = "+ current.getValue() +"\n");
            }
            else if(current.getName() == "abbr")
            {
                tempConstellation.setAbbr(current.getValue());
//                System.out.print( "abbr: " + current.getName() +" = "+ current.getValue() +"\n");
            }
            else if(current.getName() == "line")
            {
//                System.out.print( "line: " + current.getName() +" = "+ current.getValue() +"\n");
                String[] lines = current.getValue().split("\\s+");

//                System.out.print(lines[0] +":"+ lines[1]+":"+ lines[2]+":"+ lines[3] +"\n");
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
    
    public static void parseStar(Element current)
    {
        // get children of current node
        List children = current.getChildren();
        Iterator iterator = children.iterator();
        
        if ( !iterator.hasNext() )
        {
            if(current.getName() == "HRnumber")
            {
                System.out.print("new star?\n");
//                System.out.print( "hrnumber: " + current.getName() +" = "+ current.getValue() +"\n");
                tempStar = new star();
                double t = Double.parseDouble(current.getValue().toString());
                tempStar.hrnumber = t;
            }
            else if(current.getName() == "name")
            {
//                System.out.print( "name: " + current.getName() +" = "+ current.getValue() +"\n");
                tempStar.name = current.getValue();
            }
            else if(current.getName() == "constellation")
            {
//                System.out.print( "constellation: " + current.getName() +" = "+ current.getValue() +"\n");
                tempStar.name = current.getValue();
            }
            else if(current.getName() == "ra")
            {
//                System.out.print( "ra: " + current.getName() +" = "+ current.getValue() +"\n");
                String[] lines = current.getValue().split("\\s+");
//                System.out.println("ra:"+lines[0]+":"+lines[1]+":"+lines[2]+":"+lines[3]+";");
                
                double hr = Double.parseDouble(lines[1]);
                double min = Double.parseDouble(lines[2]);
                double sec = Double.parseDouble(lines[3]);
                
                tempStar.ra = Math.toRadians( ( hr + min / 60 + sec / 3600 ) * 15 );
            }
            else if(current.getName() == "dec")
            {
//                System.out.print( "dec: " + current.getName() +" = "+ current.getValue() +"\n");
                String[] lines = current.getValue().split("\\s+");
//                System.out.println("dec:"+lines[0]+":"+lines[1]+":"+lines[2]+":"+lines[3]+";");
                
                double deg = Double.parseDouble(lines[1]);
                double min = Double.parseDouble(lines[2]);
                double sec = Double.parseDouble(lines[3]);
                
                tempStar.dec = Math.toRadians( Math.abs( deg ) + min / 60 + sec / 3600 );
                if ( deg < 0 ) tempStar.dec = -tempStar.dec;
            }
            else if(current.getName() == "vmag")
            {
//                System.out.print( "vmag: " + current.getName() +" = "+ current.getValue() +"\n");
                tempStar.vmag = Double.parseDouble(current.getValue());
            }
            else if(current.getName() == "class")
            {
//                System.out.print( "class: " + current.getName() +" = "+ current.getValue() +"\n");
                tempStar.starClass = current.getValue();
            }
            else if(current.getName() == "common_name")
            {
//                System.out.print( "common_name: " + current.getName() +" = "+ current.getValue() +"\n");
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
