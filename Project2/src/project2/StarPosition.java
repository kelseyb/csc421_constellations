/*
 * Star Position Class: 
 * This class takes information about a star, and the location and date of the 
 * viewer, and calculates the appropriate placement of the star between 0 and 1 
 * for Y and -1 to 1 for X using an orthographic projection onto a sphere. 
 * 
 * It uses the following information from the stars: right ascension and
 * declination. It uses the following information about the viewer: latitude, 
 * longitude, gaze direction (azimuth), gaze altitude, and date and time of
 * viewing. 
 * 
 */

package project2;
import java.util.GregorianCalendar;

/**
 * @author John Weiss, modified by Caitlin Taggart
 * This class calculates the position of a star using an orthographic 
 * projection. 
 */
public class StarPosition {
    
    private static double alt, azi;
    //calendar that give the time the viewer is looking at the stars 
    private static GregorianCalendar now_cal; 
    //tells the user the x and y of where the star should be placed. 
    public double X, Y; 
    //tells the user if the last calculated star should be clipped. 
    public boolean Clipped; 

/**
 * @author John Weiss modified by Caitlin Taggart
 * Computes the number of days that have passed since June 10, 2005 6:45:14 GMT. 
 *
 * @return the number of days elapsed since June 10, 2005. 
 */
    public static double elapsed_days( )
    {
        GregorianCalendar then_cal = new GregorianCalendar();
        then_cal.set( 2005, 5, 10, 6, 45, 14 );

        // need current time in GMT (MST + 6 hours, or MST 7 hours if not daylight savings time)
        long now_msec = now_cal.getTimeInMillis() + 6 * 3600 * 1000;
        long then_msec = then_cal.getTimeInMillis();
        double diff_days = ( now_msec - then_msec ) / 1000.0 / ( 24.0 * 3600.0 );
        return diff_days;
    }

/**
 * @author John Weiss
 * Given observer position (lat,lon), converts star position in (ra,dec) 
 * to (azi, alt). 
 *
 * @param ra - right ascension of the star
 * @param dec - declination of the star
 * @param lat - the latitude of the observer
 * @param lon - the longitude of the observer
 */
    public static void alt_azi( double ra, double dec, double lat, double lon )
    {
        // # days since June 10, 2005 6:45:14 GMT = 1957.093588
        double t = elapsed_days();
        double tG = Math.IEEEremainder( 360.0 * 1.0027379093 * t, 360.0 );
        double psi = tG + Math.toDegrees( lon ) + 90;

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

        // finally compute alt/azi values
        alt = Math.atan( Z / Math.sqrt( X * X + Y * Y ) );
        azi = Math.acos( Y / Math.sqrt( X * X + Y * Y ) );
            if ( X < 0.0 ) azi = 2.0 * Math.PI - azi;
    }

    /**
 * @author John Weiss modified by Caitlin Taggart
 * Calculates the position of a star based on an orthographic projection onto a 
 * sphere. Uses the star's right ascension and declination and the viewers
 * latitude longitude, gaze direction and altitude, and the date. 
 *
 * @param ra - right ascension of the star
 * @param dec - declination of the star
 * @param lat - the latitude of the observer
 * @param lon - the longitude of the observer
 * @param azi0 - the gaze direction of the viewer (N = 0, E = 90, S = 180, W = 270)
 * @param alt0 - the angle the viewer is looking up in the sky
 * @param date - the date and time the viewer is looking at the sky 
 */
    public void GetPoint(double ra, double dec, double lat, double lon, double azi0, double alt0, GregorianCalendar date)
    {
        //set the date
        now_cal = date; 

        // compute alt/azi of star
        alt_azi( ra, dec, lat, lon );

        //change the gazing direction to radians
        azi0 = Math.toRadians( azi0 ); 
        alt0 = Math.toRadians( alt0 );

        // project star's (alt,azi) position on sphere to (x,y) coordinate on viewing window
        double R = 1.0;		// distance to star: assume all stars are located on sphere of radius 1
        
        //calculate x and y 
        X = R * Math.cos( alt ) * Math.sin( azi - azi0 );
        Y = R * ( Math.cos( alt0 ) * Math.sin( alt ) - Math.sin( alt0 ) * Math.cos( alt ) * Math.cos( azi - azi0 ) ); 
        double clip = Math.sin( alt0 ) * Math.sin ( alt ) + Math.cos( alt0 ) * Math.cos( alt ) * Math.cos( azi - azi0 );
        
        //set the clipping variable
        Clipped = clip < 0.0;
    }  
}
