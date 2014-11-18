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

    // compute number of days elapsed since June 10, 2005 6:45:14 GMT
    public static double elapsed_days( )
    {
        // double date_now, time_now;
        // System.out.print( "Enter date and time: " );
        // cin >> date_now >> time_now;

        // e.g., suppose current time is Oct 29, 2012 11:00:00 MST
        GregorianCalendar now_cal = new GregorianCalendar();
        GregorianCalendar then_cal = new GregorianCalendar();
        now_cal.set( 2012, 10, 29, 11, 0, 0 );
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

    // main() function for converting star position from (ra,dec) to (azi,alt) to (x,y) screen coordinates
    public void StarPosition()
    {
        // scanner is easiest way to get console input in Java
        Scanner sc = new Scanner( System.in );

        // get right ascension and declination of star
        // e.g., Sirius: RA = 6,45,38, DEC = -16,43,44
        System.out.print( "Enter RA  of star [Sirius: 6 45 38]: " );
        double hr = sc.nextDouble();
        double min = sc.nextDouble();
        double sec = sc.nextDouble();
        double ra = Math.toRadians( ( hr + min / 60 + sec / 3600 ) * 15 );
        System.out.print( "Enter DEC of star [Sirius: -16 43 44]: " );
        double deg = sc.nextDouble();
        min = sc.nextDouble();
        sec = sc.nextDouble();
        double dec = Math.toRadians( Math.abs( deg ) + min / 60 + sec / 3600 );
        if ( deg < 0 ) dec = -dec;
        System.out.printf( "(ra,dec) = (%.3f,%.3f) radians\n\n", ra, dec );

        // get longitude and latitude
        // Rapid City: (LAT,LON) = (44.08,-103.23)
        System.out.print( "Enter observer latitude and longitude in degrees [Rapid City: 44.08 -103.23]: " );
        double lat = Math.toRadians( sc.nextDouble() );
        double lon = Math.toRadians( sc.nextDouble() );
        System.out.printf( "(lat,lon) = (%.3f,%.3f) radians\n\n", lat, lon );

        // compute alt/azi of star
        alt_azi( ra, dec, lat, lon );
        System.out.printf( "(azi,alt) = (%.3f,%.3f) radians = (%.3f,%.3f) degrees\n\n",
            azi, alt, Math.toDegrees( azi ), Math.toDegrees( alt ) );

        // get direction of viewer's gaze, also in (azi,alt)
        // azi: N=0, E=90, S=180, W=270
        // alt: 0=horizon,90=straight up)
        System.out.print( "Enter gaze direction in degrees [azi alt]: " );
        double azi0 = Math.toRadians( sc.nextDouble() );
        double alt0 = Math.toRadians( sc.nextDouble() );

        // project star's (alt,azi) position on sphere to (x,y) coordinate on viewing window
        double R = 1.0;		// distance to star: assume all stars are located on sphere of radius 1
        double x = R * Math.cos( alt ) * Math.sin( azi - azi0 );
        double y = R * ( Math.cos( alt0 ) * Math.sin( alt ) - Math.sin( alt0 ) * Math.cos( alt ) * Math.cos( azi - azi0 ) );
        double clip = Math.sin( alt0 ) * Math.sin ( alt ) + Math.cos( alt0 ) * Math.cos( alt ) * Math.cos( azi - azi0 );
        System.out.printf( "\nstar position: (x,y) = (%.3f,%.3f)", x, y );
        if ( clip < 0.0 )
            System.out.print( "    <= bad point, should be clipped!" );
        System.out.printf( "\n\n" );
    }
    
    public double GetPoint(double ra, double dec, double lat, double lon, double azi0, double alt0, GregorianCalendar date)
    {
        //double ra = Math.toRadians( ( ra_hr + ra_min / 60 + ra_sec / 3600 ) * 15 );
        //double dec = Math.toRadians( Math.abs( dec_deg ) + dec_min / 60 + dec_sec / 3600 );
        //if ( dec_deg < 0 ) dec = -dec;

        // compute alt/azi of star
        alt_azi( ra, dec, lat, lon );

        azi0 = Math.toRadians( azi0 ); 
        alt0 = Math.toRadians( alt0 );

        // project star's (alt,azi) position on sphere to (x,y) coordinate on viewing window
        double R = 1.0;		// distance to star: assume all stars are located on sphere of radius 1
        double x = R * Math.cos( alt ) * Math.sin( azi - azi0 );
        double y = R * ( Math.cos( alt0 ) * Math.sin( alt ) - Math.sin( alt0 ) * Math.cos( alt ) * Math.cos( azi - azi0 ) );
        return x; 
        
    }
    
}
