/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package project2;
import java.lang.Math;
import java.util.Date;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Scanner;

/**
 *
 * @author 7047960
 */
public class StarPosition {
    private static double alt, azi;
    private static GregorianCalendar now_cal; 
    public double X, Y; 
    public boolean Clipped; 

    // compute number of days elapsed since June 10, 2005 6:45:14 GMT
    public static double elapsed_days( )
    {
        GregorianCalendar then_cal = new GregorianCalendar();
        then_cal.set( 2005, 5, 10, 6, 45, 14 );

        // need current time in GMT (MST + 6 hours, or MST 7 hours if not daylight savings time) jy
        long now_msec = now_cal.getTimeInMillis() + 6 * 3600 * 1000;
        long then_msec = then_cal.getTimeInMillis();
        double diff_days = ( now_msec - then_msec ) / 1000.0 / ( 24.0 * 3600.0 );
        // System.out.println( "Diff in days = " + diff_days );
        return diff_days;
    }

    // given observer position (lat,lon), convert star position in (ra,dec) to (azi, alt)
    public static void alt_azi( double ra, double dec, double lat, double lon )
    {
        // # days since June 10, 2005 6:45:14 GMT = 1957.093588
        double t = elapsed_days();
        // System.out.printf( "t = %.3f days elapsed\n", t );
        double tG = Math.IEEEremainder( 360.0 * 1.0027379093 * t, 360.0 );
        double thetaG = Math.toRadians( tG );
        // System.out.printf( "thetaG = %.3f = %.3f\n", tG, thetaG );
        double psi = tG + Math.toDegrees( lon ) + 90;
        // System.out.printf( "psi = %.3f = %.3f\n", psi, Math.toRadians( psi ) );

        // rename ala formulas in Don's paper
        double alpha = ra;
        double beta  = lat;
        double delta = dec;
        psi = Math.toRadians( psi );

        double X =  Math.cos( psi ) * Math.cos( delta ) * Math.cos( alpha )
                  + Math.sin( psi ) * Math.cos( delta ) * Math.sin( alpha );
        double Y = -Math.sin( beta ) * Math.sin( psi ) * Math.cos( delta ) * Math.cos( alpha )
                  + Math.sin( beta ) * Math.cos( psi ) * Math.cos( delta ) * Math.sin( alpha )
                  + Math.cos( beta ) * Math.sin( delta );
        double Z =  Math.cos( beta ) * Math.sin( psi ) * Math.cos( delta ) * Math.cos( alpha )
                  - Math.cos( beta ) * Math.cos( psi ) * Math.cos( delta ) * Math.sin( alpha )
                  + Math.sin( beta ) * Math.sin( delta );
        // System.out.printf( "(X,Y,Z) = (%.3f,%.3f,%3f)\n\n", X, Y, Z );

        // finally compute alt/azi values
        alt = Math.atan( Z / Math.sqrt( X * X + Y * Y ) );
        azi = Math.acos( Y / Math.sqrt( X * X + Y * Y ) );
            if ( X < 0.0 ) azi = 2.0 * Math.PI - azi;
    }

    public void StarPosition()
    {
    }
    
    public void GetPoint(double ra, double dec, double lat, double lon, double azi0, double alt0, GregorianCalendar date)
    {
        now_cal = date; 

        // compute alt/azi of star
        alt_azi( ra, dec, lat, lon );

        azi0 = Math.toRadians( azi0 ); 
        alt0 = Math.toRadians( alt0 );

        // project star's (alt,azi) position on sphere to (x,y) coordinate on viewing window
        double R = 1.0;		// distance to star: assume all stars are located on sphere of radius 1
        X = R * Math.cos( alt ) * Math.sin( azi - azi0 );
        Y = R * ( Math.cos( alt0 ) * Math.sin( alt ) - Math.sin( alt0 ) * Math.cos( alt ) * Math.cos( azi - azi0 ) ); 
        double clip = Math.sin( alt0 ) * Math.sin ( alt ) + Math.cos( alt0 ) * Math.cos( alt ) * Math.cos( azi - azi0 );
        if (clip < 0.0)
        {
            Clipped = true; 
        }
        else 
        {
            Clipped = false; 
        }
    }
    
}
