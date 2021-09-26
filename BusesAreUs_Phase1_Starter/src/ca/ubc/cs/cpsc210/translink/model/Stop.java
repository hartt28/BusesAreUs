package ca.ubc.cs.cpsc210.translink.model;

import ca.ubc.cs.cpsc210.translink.util.LatLon;

import java.util.*;

/**
 * Represents a bus stop with an number, name, location (lat/lon)
 * set of routes which stop at this stop and a list of arrivals.
 */
// TODO: Task 2: Complete all the methods of this class

public class Stop implements Iterable<Arrival> {
    private List<Arrival> arrivals;
    private Set<Route> routes;
    private int num;
    private String name;
    private LatLon locn;

    /**
     * Constructs a stop with given number, name and location.
     * Set of routes and list of arrivals are empty.
     *
     * @param number    the number of this stop
     * @param name      name of this stop
     * @param locn      location of this stop
     */
    public Stop(int number, String name, LatLon locn) {
        routes = new HashSet <Route>();
        arrivals = new ArrayList <Arrival>();
        this.num=number;
        this.name=name;
        this.locn=locn;
    }

    /**
     * getter for name
     * @return      the name
     */
    public String getName() {
        return this.name;
    }

    /**
     * getter for locn
     * @return      the location
     */
    public LatLon getLocn() {
        return this.locn;
    }

    /**
     * getter for number
     * @return      the number
     */
    public int getNumber() {
        return this.num;
    }

    /**
     * getter for set of routes
     * @return      an unmodifiable set of routes using this stop
     */
    public Set<Route> getRoutes() {
        return Collections.unmodifiableSet(routes);
    }

    /**
     * Add route to set of routes with stops at this stop.
     *
     * @param route  the route to add
     */
    public void addRoute(Route route) {
        route.addStop(this);
        this.routes.add(route);
    }

    /**
     * Remove route from set of routes with stops at this stop
     *
     * @param route the route to remove
     */
    public void removeRoute(Route route) {
        routes.remove(route.hasStop(this));
    }

    /**
     * Determine if this stop is on a given route
     * @param route  the route
     * @return  true if this stop is on given route
     */
    public boolean onRoute(Route route) {
        if(route.hasStop(this))
            return true;
        return false;
    }

    /**
     * Add bus arrival travelling on a particular route at this stop.
     * Arrivals are to be sorted in order by arrival time
     *
     * @param arrival  the bus arrival to add to stop
     */
    public void addArrival(Arrival arrival) {
        if(arrivals.size()==0)
            arrivals.add(arrival);
        for(int i=0;i<arrivals.size();i++)
        {
            if(arrivals.get(arrivals.size()-1).compareTo(arrival)<0)
                arrivals.add(arrival);
            else if(arrivals.get(0).compareTo(arrival)>0)
                arrivals.set(0,arrival);
            else if(arrivals.get(i).compareTo(arrival)>0)
               arrivals.set(i,arrival);
        }
    }

    /**
     * Remove all arrivals from this stop
     */
    public void clearArrivals() {
        for(Arrival next:arrivals)
        {
            arrivals.remove(next);
        }
    }

    /**
     * Two stops are equal if their numbers are equal
     */
    @Override
    public boolean equals(Object o) {
        if( o instanceof Stop==false)
            return false;
        Stop s = (Stop)o;
        return this.getNumber()==s.getNumber();
    }

    /**
     * Two stops are equal if their numbers are equal.
     * Therefore hashCode only pays attention to number.
     */
    @Override
    public int hashCode() {
        return this.num;
    }

    @Override
    public Iterator<Arrival> iterator() {
        // Do not modify the implementation of this method!
        return arrivals.iterator();
    }

    /**
     * setter for name
     *
     * @param name      the new name
     */
    public void setName(String name) {
        this.name=name;
    }

    /**
     * setter for location
     * @param locn      the new location
     */
    public void setLocn(LatLon locn) {
        this.locn=locn;
    }
}
