package ca.ubc.cs.cpsc210.translink.util;

import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

/**
 * Compute relationships between points, lines, and rectangles represented by LatLon objects
 */
public class Geometry {
    /**
     * Return true if the point is inside of, or on the boundary of, the rectangle formed by northWest and southeast
     *
     * @param northWest the coordinate of the north west corner of the rectangle
     * @param southEast the coordinate of the south east corner of the rectangle
     * @param point     the point in question
     * @return true if the point is on the boundary or inside the rectangle
     */
    public static boolean rectangleContainsPoint( LatLon northWest, LatLon southEast, LatLon point ) {
        if (southEast.getLatitude() <= point.getLatitude() && point.getLatitude() <= northWest.getLatitude()
                && northWest.getLongitude() <= point.getLongitude() && point.getLongitude() <= southEast.getLongitude())
            return true;
        return false;
    }

    /**
     * Return true if the rectangle intersects the line
     *
     * @param northWest the coordinate of the north west corner of the rectangle
     * @param southEast the coordinate of the south east corner of the rectangle
     * @param src       one end of the line in question
     * @param dst       the other end of the line in question
     * @return true if any point on the line is on the boundary or inside the rectangle
     */
    public static boolean rectangleIntersectsLine( LatLon northWest, LatLon southEast, LatLon src, LatLon dst ) {
        // TODO: Tasks 5: Implement this method
        if ((southEast.getLatitude() <= src.getLatitude() && src.getLatitude() <= northWest.getLatitude()
                && northWest.getLongitude() <= src.getLongitude() && src.getLongitude() <= southEast.getLongitude()
                && southEast.getLatitude() <= dst.getLatitude() && dst.getLatitude() <= northWest.getLatitude()
                && northWest.getLongitude() <= dst.getLongitude() && dst.getLongitude() <= southEast.getLongitude()))
            return true;
        else {
            Line2D line = new Line2D.Double(src.getLatitude(),src.getLongitude(),dst.getLatitude(),dst.getLongitude());
            Line2D topRectangular = new Line2D.Double(northWest.getLatitude(),northWest.getLongitude(),southEast.getLatitude(),northWest.getLongitude());
            Line2D bottomRectangular=new Line2D.Double(northWest.getLatitude(),southEast.getLongitude(),southEast.getLatitude(),southEast.getLongitude());
            Line2D leftRectangular=new Line2D.Double(northWest.getLatitude(),northWest.getLongitude(),northWest.getLatitude(),southEast.getLongitude());
            Line2D rightRectangular=new Line2D.Double(southEast.getLatitude(),southEast.getLongitude(),southEast.getLatitude(),southEast.getLongitude());
            if(line.intersectsLine(topRectangular)||line.intersectsLine(bottomRectangular)||line.intersectsLine(leftRectangular)||line.intersectsLine(rightRectangular))
                return true;
            else return false;
        }
    }

/*
                ||((southEast.getLatitude()<=src.getLatitude()&&src.getLatitude()<=northWest.getLatitude()
                &&northWest.getLongitude()<=src.getLongitude()&&src.getLongitude()<=southEast.getLongitude()
                &&southEast.getLatitude()>=dst.getLatitude()||dst.getLatitude()>=northWest.getLatitude()
                &&northWest.getLongitude()>=dst.getLongitude()||dst.getLongitude()>=southEast.getLongitude()))

                ||((southEast.getLatitude()<=dst.getLatitude()&&dst.getLatitude()<=northWest.getLatitude()
                &&northWest.getLongitude()<=dst.getLongitude()&&dst.getLongitude()<=southEast.getLongitude()
                &&southEast.getLatitude()>=src.getLatitude()||src.getLatitude()>=northWest.getLatitude()
                &&northWest.getLongitude()>=src.getLongitude()||src.getLongitude()>=southEast.getLongitude()))
                )
        {return true;}
        else if((southEast.getLatitude()<=src.getLatitude()&&src.getLatitude()<=northWest.getLatitude()
                &&northWest.getLongitude()>=src.getLongitude()&&src.getLongitude()>=southEast.getLongitude())
               ){
            /*
        }
    }

    /**
     * A utility method that you might find helpful in implementing the two previous methods
     * Return true if x is >= lwb and <= upb
     * @param lwb      the lower boundary
     * @param upb      the upper boundary
     * @param x         the value in question
     * @return          true if x is >= lwb and <= upb
     */
    private static boolean between(double lwb, double upb, double x) {
        return lwb <= x && x <= upb;
    }
}
