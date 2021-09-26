package ca.ubc.cs.cpsc210.translink.tests.model;

import ca.ubc.cs.cpsc210.translink.model.Route;
import ca.ubc.cs.cpsc210.translink.model.Stop;
import ca.ubc.cs.cpsc210.translink.model.StopManager;
import ca.ubc.cs.cpsc210.translink.model.exception.StopException;
import ca.ubc.cs.cpsc210.translink.util.LatLon;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Test the StopManager
 */
public class StopManagerTest {

    @Before
    public void setup() {
        StopManager.getInstance().clearStops();
    }

    @Test
    public void testGetStopWithNumber() {
        Stop s9999 = new Stop(9999, "My house", new LatLon(-49.2, 123.2));
        Stop r = StopManager.getInstance().getStopWithNumber(9999);
        assertEquals(s9999, r);
        Stop r10 = StopManager.getInstance().getStopWithNumber(10,"School",new LatLon(49.1,121.7));
        assertEquals(2,StopManager.getInstance().getNumStops());

       //assertEquals(r,StopManager.getInstance().setSelected(s9999));
    }

    @Test
    public void testClearStop()
    {
        StopManager.getInstance().getStopWithNumber(9999);
        StopManager.getInstance().getStopWithNumber(10,"School",new LatLon(49.1,121.7));
        assertEquals(2,StopManager.getInstance().getNumStops());
        StopManager.getInstance().clearStops();
        assertEquals(0,StopManager.getInstance().getNumStops());
    }

    @Test
    public void testSelected() throws StopException
    {
        StopManager.getInstance().getStopWithNumber(9999);
        StopManager.getInstance().getStopWithNumber(10,"School",new LatLon(49.1,121.7));
        assertNull(StopManager.getInstance().getSelected());
        StopManager.getInstance().setSelected(StopManager.getInstance().getStopWithNumber(10));
        assertEquals(StopManager.getInstance().getStopWithNumber(10), StopManager.getInstance().getSelected());
        StopManager.getInstance().clearSelectedStop();
        assertNull(StopManager.getInstance().getSelected());
    }

    @Test (expected = StopException.class)
    public void testSelectedError() throws StopException
    {
        Stop s=new Stop(1,"My House",new LatLon(0,0));
        StopManager.getInstance().setSelected(new Stop(9999, "My house", new LatLon(-49.2, 123.2)));
    }

    @Test
    public void testFindNearestTo()
    {
        Stop r0 = StopManager.getInstance().getStopWithNumber(1000);
        Stop r1= StopManager.getInstance().getStopWithNumber(1,"Washroom",new LatLon(48,-123));
        Stop s=StopManager.getInstance().findNearestTo(new LatLon(49,122));
        assertEquals(r1,s);
        assertNull(StopManager.getInstance().findNearestTo(new LatLon(60,-150)));
    }

    @Test
    public void testAddAndRemoveRoute()
    {
        Stop r0 = StopManager.getInstance().getStopWithNumber(1000);
        r0.addRoute(new Route("99"));
        r0.addRoute(new Route("100"));
        assertEquals(2,r0.getRoutes().size());
        r0.removeRoute(new Route("100"));
        assertEquals(1,r0.getRoutes().size());
    }

    @Test
    public void testAddRoute()
    {
        Stop r0 = StopManager.getInstance().getStopWithNumber(1000);
        r0.addRoute(new Route("88"));
        r0.addRoute(new Route("88"));
        assertEquals(1,r0.getRoutes().size());

    }

    @Test
    public void testOnRoute()
    {
        Stop s= StopManager.getInstance().getStopWithNumber(1000);
        Route r=new Route("99");
        Route r1=new Route("100");
        r.addStop(s);
        assertTrue(s.onRoute(r));
        assertFalse(s.onRoute(r1));
    }

}
