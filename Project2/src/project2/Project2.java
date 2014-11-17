/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project2;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import project2.xmlparser;

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
        String xml = "stars_mag4.xml";
//        String xml = "constellations.xml";
        xmlparser.generateStar(xml);
        
        if(xmlparser.constellationList.size() != 0)
        {
            String a = xmlparser.constellationList.get(0).getStarSet(0)[0]; 
            String b = xmlparser.constellationList.get(0).getStarSet(0)[1]; 
            String c = xmlparser.constellationList.get(0).getName();
            System.out.println("test--"+a+":"+b+":"+c);
        }
        
        if(xmlparser.starList.size() != 0)
        {
            int size = xmlparser.starList.size();
            String d = xmlparser.starList.get(0).commonName;
//            Vector <Double> a = xmlparser.starList.get(0).dec;
//            Vector <Double> b = xmlparser.starList.get(0).ra;
//            System.out.println("dec:"+a.get(0)+":"+a.get(1)+":"+a.get(2)+";");//xmlparser.starList.get(0).dec[0]);
//            System.out.println("ra:"+b.get(0)+":"+b.get(1)+":"+b.get(2)+";");
            
            double f = xmlparser.starList.get(0).vmag;
            System.out.println("test--"+d+":"+f+":"+size);
        }
    }
}
