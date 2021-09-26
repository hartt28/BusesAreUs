package ca.ubc.cs.cpsc210.translink.model;

import ca.ubc.cs.cpsc210.translink.model.exception.StopException;
import ca.ubc.cs.cpsc210.translink.util.LatLon;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Manages all bus stops.
 *
 * Singleton pattern applied to ensure only a single instance of this class that
 * is globally accessible throughout application.
 */
// TODO: Task 2: Complete all the methods of this class

public class StopManager implements Iterable<Stop> {
    public static final int RADIUS = 10000;
    public static final double LAT=-122.2144;
    public static final double LON=49.0524;
    private static final double DEGREE_TO_METER=111319.9;
    private static StopManager instance;

    // Use this field to hold all of the stops.
    // Do not change this field or its type, as the iterator method depends on it
    private Map<Integer, Stop> stopMap;
    private Stop selectedStop;

    /**
     * Constructs stop manager with empty collection of stops and null as the selected stop
     */
    private StopManager() {
        instance=null;
        stopMap = new HashMap <Integer,Stop>();
        selectedStop= null;
    }

    /**
     * Gets one and only instance of this class
     *
     * @return  instance of class
     */
    public static StopManager getInstance() {
        // Do not modify the implementation of this method!
        if(instance == null) {
            instance = new StopManager();
        }

        return instance;
    }

    /**
     * Returns the stop selected by StopManager.setSelected(Stop),
     * or null if no stop is selected.
     *
     * @return the selected stop.
     */
    public Stop getSelected() {
        return selectedStop;
    }

    /**
     * Get stop with given number, creating it and adding it to the collection of all stops if necessary.
     * If it is necessary to create a new stop, then provide it with an empty string as its name,
     * and a default location somewhere in the lower mainland as its location.
     *
     * In this case, the correct name and location of the stop will be provided later
     *
     * @param number  the number of this stop
     *
     * @return  stop with given number
     */
    public Stop getStopWithNumber(int number) {
        if(!stopMap.containsKey(number))
        {
            stopMap.put(number,new Stop(number,"",new LatLon(LON,LAT)));
        }
        return stopMap.get(number);
    }

    /**
     * Get stop with given number, creating it and adding it to the collection of all stops if necessary,
     * using the given name and location
     *
     * @param number  the number of this stop
     * @param name   the name of this stop
     * @param locn   the location of this stop
     *
     * @return  stop with given number
     */
    public Stop getStopWithNumber(int number, String name, LatLon locn) {
        if(!stopMap.containsKey(number))
        {
            stopMap.put(number, new Stop(number, name, locn));
        }
        return stopMap.get(number);
    }

    /**
     * Set the stop selected by user
     *
     * @param selected   stop selected by user
     * @throws StopException when stop manager doesn't contain selected stop
     */
        public void setSelected(Stop selected) throws StopException {
            if(!stopMap.containsKey(selected.getNumber()))
                throw new StopException("No such stop: " + selected.getNumber() + " " + selected.getName());
            selectedStop=selected;
    }

    /**
     * Clear selected stop (selected stop is null)
     */
    public void clearSelectedStop() {
        selectedStop=null;
    }

    /**
     * Get number of stops managed
     *
     * @return  number of stops added to manager
     */
    public int getNumStops() {
        return stopMap.size();
    }

    /**
     * Remove all stops from stop manager
     */
    public void clearStops() {
        stopMap.clear();
    }

    /**
     * Find nearest stop to given point.  Returns null if no stop is closer than RADIUS metres.
     *
     * @param pt  point to which nearest stop is sought
     * @return    stop closest to pt but less than RADIUS away; null if no stop is within RADIUS metres of pt
     */
    public Stop findNearestTo(LatLon pt) {
        boolean isFirst=true;
        Stop s = null;

        for (Stop next : stopMap.values()) {

            if (isFirst) {
                s = next;
                isFirst = false;
            } else {
                double nLat = next.getLocn().getLatitude() - pt.getLatitude();
                double nLon = next.getLocn().getLongitude() - pt.getLongitude();
                double sLat = s.getLocn().getLatitude() - pt.getLatitude();
                double sLon = s.getLocn().getLongitude() - pt.getLatitude();
                double ndistance = Math.sqrt(Math.pow(nLat, 2) + Math.pow(nLon, 2));
                double sdistance = Math.sqrt(Math.pow(sLat, 2) + Math.pow(sLon, 2));

                if (ndistance < sdistance)
                    s = next;
            }
        }

        if ((s.getLocn().getLatitude() - pt.getLatitude()) * DEGREE_TO_METER > RADIUS
                || (s.getLocn().getLongitude() - pt.getLongitude()) * DEGREE_TO_METER > RADIUS) {
            s = null;
        }
        return s;
    }

    @Override
    public Iterator<Stop> iterator() {
        // Do not modify the implementation of this method!
        return stopMap.values().iterator();
    }
}
