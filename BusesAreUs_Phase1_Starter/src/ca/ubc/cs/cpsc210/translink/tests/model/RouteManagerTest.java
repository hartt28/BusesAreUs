package ca.ubc.cs.cpsc210.translink.tests.model;

import ca.ubc.cs.cpsc210.translink.model.Route;
import ca.ubc.cs.cpsc210.translink.model.RouteManager;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Test the RouteManager
 */
public class RouteManagerTest {

    @Before
    public void setup() {
        RouteManager.getInstance().clearRoutes();
    }

    @Test
    public void testGetRouteWithNumber() {
        Route r43 = new Route("43");
        Route r = RouteManager.getInstance().getRouteWithNumber("43");
        assertEquals(r43, r);
        Route r41=new Route("41");
        r41.setName("Joyce");
        Route r2= RouteManager.getInstance().getRouteWithNumber("41","Joyce");
        assertEquals(r41, r2);

    }

    @Test
    public void testClearRoutes()
    {
        Route r43 = new Route("43");
        Route r41=new Route("41");
        Route r=new Route("43");
        RouteManager.getInstance().getRouteWithNumber("43");
        RouteManager.getInstance().getRouteWithNumber("41");
        assertEquals(2,RouteManager.getInstance().getNumRoutes());
        RouteManager.getInstance().clearRoutes();
        assertEquals(0,RouteManager.getInstance().getNumRoutes());

    }

    @Test
    public void testaddRoute()
    {

    }
}
