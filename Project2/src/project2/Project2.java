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
        String xml = "constellations.xml";
        try
        {
            Document doc = builder.build(xml);// args[0] );	// parse XML tags
            Element root = doc.getRootElement();	// get root of XML tree
            listChildren( root, 0 );			// print info in XML tree
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
        printSpaces( depth );
        System.out.print( current.getName() );
        if ( !iterator.hasNext() )
            System.out.print( " = " + current.getValue() );
        System.out.println();

        // recursively process each child node
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
    
}
