package ca.ubc.cs.cpsc210.translink.tests.parsers;

import ca.ubc.cs.cpsc210.translink.model.Route;
import ca.ubc.cs.cpsc210.translink.model.RouteManager;
import ca.ubc.cs.cpsc210.translink.model.RoutePattern;
import ca.ubc.cs.cpsc210.translink.parsers.RouteMapParser;
import ca.ubc.cs.cpsc210.translink.util.LatLon;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Test the parser for route pattern map information
 */

// TODO: Write more tests

public class RouteMapParserTest {
    @Before
    public void setup() {
        RouteManager.getInstance().clearRoutes();
    }

    private int countNumRoutePatterns() {
        int count = 0;
        for (Route r : RouteManager.getInstance()) {
            for (RoutePattern rp : r.getPatterns()) {
                count ++;
            }
        }
        return count;
    }

    @Test
    public void testRouteParserNormal() {
        RouteMapParser p = new RouteMapParser("allroutemaps.txt");
        p.parse();
        assertEquals(1232, countNumRoutePatterns());
    }

    @Test
    public void testParseFirst(){
        RouteMapParser p = new RouteMapParser("allroutemaps.txt");
        p.parse();
        Route r= RouteManager.getInstance().getRouteWithNumber("C43","EB2");
        RoutePattern rp = r.getPattern("EB2");
        assertEquals("C43",r.getNumber());
        assertEquals("EB2",rp.getName());
        assertEquals(new LatLon(49.21716,-122.667252),rp.getPath().get(0));
    }

    @Test
    public void testParseLast()
    {
        RouteMapParser p = new RouteMapParser("allroutemaps.txt");
        p.parse();
        Route r= RouteManager.getInstance().getRouteWithNumber("006","EB3");
        RoutePattern rp = r.getPattern("EB3");
        assertEquals("006",r.getNumber());
        assertEquals("EB3",rp.getName());
        assertTrue(rp.getPath().contains(new LatLon(49.284452,-123.137481)));
        assertTrue(rp.getPath().contains(new LatLon(49.277155,-123.126192)));
    }
}
