/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project2;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

/**
 *
 * @author 7032956
 */
public class Project2 {

    /**
     * @param args the command line arguments
     */
    //public static void main(String[] args) {
        // TODO code application logic here
    //}
    
    public static boolean isStar = false;
    
    public static void main( String[] args )
    {
	// check usage
        /*if ( args.length == 0 )
        {
            System.out.println( "Usage: java ElementLister URL" );
            return;
        }*/

	// read and parse XML document
        SAXBuilder builder = new SAXBuilder();
        String xml = "stars_mag4.xml";//"constellations.xml";
        try
        {
            Document doc = builder.build(xml);// args[0] );	// parse XML tags
            Element root = doc.getRootElement();	// get root of XML tree
            listChildren( root, 0 );			// print info in XML tree
            
            //instead
            //should be, xml then star/constellation
//            if(root.getName() != "xml")
//            {
//                System.out.println( xml + " is not well-formed." );
//            }
//            else
//                
//            
//            if(root.getName() == "star")
//                parseStar(root);
//            else if(root.getName() == "constellation")
//                parseConstellation(root);
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
    public static void listChildren( Element current, int depth )
    {
	// get children of current node
        List children = current.getChildren();
        Iterator iterator = children.iterator();

        // print node name and leaf node value, indented one space per level in XML tree
        //printSpaces( depth );
        //System.out.print( current.getName() );
//        if(current.getName() == "xml")
//        {
//            System.out.print( "found xml ? recurring blah \n");
//            Element child = ( Element ) iterator.next();
//            listChildren( child, depth + 1 );
//        } //prob dont need this. //well, def dont need this, but might revert.
        
        if(current.getName() == "star")
        {
            isStar = true;
            System.out.print( "   printing star yaya \n");
            parseStar(current);
        }
        else if(current.getName() == "constellation" && isStar == false)
        {
            System.out.print( "   printing constellation yaya \n");
            parseConstellation(current);
        }
                
        //if ( !iterator.hasNext() )
            //System.out.print( " = " + current.getValue() );
        //System.out.println();

        // recursively process each child node
        //hmmmm. this seems to do alot of extra work. hmmmmmmmmm.
        //not that much extra work? additionally, if we don't have /something/ here, we only get one branch of a thing.
        while ( iterator.hasNext() )
        {
            Element child = ( Element ) iterator.next();
            listChildren( child, depth + 1 );
        }
    }

    // indent to show hierarchical structure of XML tree
    private static void printSpaces( int n )
    {
        for ( int i = 0; i < n; i++ )
        {
            System.out.print( "  " );
        }
    }
    
    //i dunno if this is gonna work so well.
    public static void parseConstellation(Element current)
    {
        // get children of current node
        List children = current.getChildren();
        Iterator iterator = children.iterator();

        //for constellations        
        if(!iterator.hasNext()) //does this do what you think it does?
        {
//            System.out.print( "whatami: " + current.getName() +" = "+ current.getValue() +"\n");
            if(current.getName() == "name")
            {

                System.out.print( "name: " + current.getName() +" = "+ current.getValue() +"\n");
            }
            else if(current.getName() == "abbr")
            {

                System.out.print( "abbr: " + current.getName() +" = "+ current.getValue() +"\n");
            }
            else if(current.getName() == "line")
            {
                System.out.print( "line: " + current.getName() +" = "+ current.getValue() +"\n");
                
                //send current.getValue() to drawLine function
                String[] lines = current.getValue().split("\\s+");

                //get xy of lines[0]

                //get xy of lines[2]

            }
        }

        // recursively process each child node
        while ( iterator.hasNext() )
        {
            
            Element child = (Element)iterator.next();
            if(child.getName() == "constellation")
            {
                //huh. we never reach this.
                //huuuuh.
                System.out.print("exiting constellations \n");
                return;
            }
            parseConstellation(child);
        }
    }
    
    public static void parseStar(Element current)//depth?
    {
        // get children of current node
        List children = current.getChildren();
        Iterator iterator = children.iterator();
        
        //else if stars:
        if ( !iterator.hasNext() )
        {
            if(current.getName() == "HRnumber")
            {
                System.out.print( "hrnumber: " + current.getName() +" = "+ current.getValue() +"\n");

            }
            else if(current.getName() == "name")
            {
                System.out.print( "name: " + current.getName() +" = "+ current.getValue() +"\n");

            }
            else if(current.getName() == "constellation")
            {
                System.out.print( "constellation: " + current.getName() +" = "+ current.getValue() +"\n");

            }
            else if(current.getName() == "ra")
            {
                System.out.print( "ra: " + current.getName() +" = "+ current.getValue() +"\n");
                
                String[] lines = current.getValue().split("\\s+");
                //lines.count ?= 3 //0,1 = int, 2 = float w/ 2 past dec
            }
            else if(current.getName() == "dec")
            {
                System.out.print( "dec: " + current.getName() +" = "+ current.getValue() +"\n");
                
                String[] lines = current.getValue().split("\\s+");
                //lines.count ?= 3
            }
            else if(current.getName() == "vmag")
            {
                System.out.print( "vmag: " + current.getName() +" = "+ current.getValue() +"\n");

            }
            else if(current.getName() == "class")
            {
                System.out.print( "class: " + current.getName() +" = "+ current.getValue() +"\n");

            }
            else if(current.getName() == "common_name")
            {
                System.out.print( "common_name: " + current.getName() +" = "+ current.getValue() +"\n");

            }
        }
        
        // recursively process each child node
        while (iterator.hasNext())
        {
            Element child = (Element) iterator.next();
            if(child.getName() == "star")
                return;
            parseStar( child);
        }
    }
    
}
