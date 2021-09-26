package ca.ubc.cs.cpsc210.translink.parsers;

import ca.ubc.cs.cpsc210.translink.model.Route;
import ca.ubc.cs.cpsc210.translink.model.RouteManager;
import ca.ubc.cs.cpsc210.translink.model.RoutePattern;
import ca.ubc.cs.cpsc210.translink.parsers.exception.RouteDataMissingException;
import ca.ubc.cs.cpsc210.translink.providers.DataProvider;
import ca.ubc.cs.cpsc210.translink.providers.FileDataProvider;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.StringReader;

/**
 * Parse route information in JSON format.
 */
public class RouteParser {
    private String filename;
    private String name;
    private String destination;
    private String direction;
    private String patternNo;
    private String href;
    private String oc;
    private JSONArray patterns;
    private String routeNo;


    public RouteParser(String filename) {
        this.filename = filename;
        this.name="";
        this.destination="";
        this.direction="";
        this.patternNo="";
        this.href="";
        this.oc="";
        this.patterns=null;

    }
    /**
     * Parse route data from the file and add all route to the route manager.
     *
     */
    public void parse() throws IOException, RouteDataMissingException, JSONException{
        DataProvider dataProvider = new FileDataProvider(filename);

        parseRoutes(dataProvider.dataSourceToString());
    }
    /**
     * Parse route information from JSON response produced by Translink.
     * Stores all routes and route patterns found in the RouteManager.
     *
     * @param  jsonResponse    string encoding JSON data to be parsed
     * @throws JSONException   when:
     * <ul>
     *     <li>JSON data does not have expected format (JSON syntax problem)
     *     <li>JSON data is not an array
     * </ul>
     * If a JSONException is thrown, no routes should be added to the route manager
     *
     * @throws RouteDataMissingException when
     * <ul>
     *  <li>JSON data is missing RouteNo, Name, or Patterns element for any route</li>
     *  <li>The value of the Patterns element is not an array for any route</li>
     *  <li>JSON data is missing PatternNo, Destination, or Direction element for any route pattern</li>
     * </ul>
     * If a RouteDataMissingException is thrown, all correct routes are first added to the route manager.
     */

    public void parseRoutes(String jsonResponse)
            throws JSONException, RouteDataMissingException {
        JSONArray routes=null;
        try {
            routes = new JSONArray(jsonResponse);
        }
        catch (JSONException e) {
            throw new JSONException("Wrong route format");
            }

        for (int i = 0; i < routes.length(); i++) {
            JSONObject route = routes.getJSONObject(i);
            if(!(route.has("Name"))
                    &&route.has("Patterns")
                    &&route.has("RouteNo"))
                throw new RouteDataMissingException();

            name = route.getString("Name");
            oc = route.getString("OperatingCompany");
            routeNo=route.getString("RouteNo");

            try {
                patterns = route.getJSONArray("Patterns");
            }
            catch (JSONException e)
            {
                throw new JSONException("Wrong pattern format");
            }

            for (int j = 0; j < patterns.length(); j++) {
                try{
                    parsePattern(patterns.getJSONObject(j));
                }
                catch (RouteDataMissingException e)
                {
                    parsePattern(patterns.getJSONObject(j++));
                }
                storeRoutePatterns(routeNo,destination,direction,patternNo,name);
            }
        }
}

    public void parsePattern(JSONObject pattern) throws JSONException, RouteDataMissingException{

        if(!(pattern.has("Destination")
                &&pattern.has("Direction")
                &&pattern.has("PatternNo")))
            throw new RouteDataMissingException();

        destination = pattern.getString("Destination");
        direction = pattern.getString("Direction");
             try {
                 patternNo = pattern.getString("PatternNo");
             }catch (JSONException e){
                 throw new JSONException("Wrong pattern format");
             }
            JSONObject routeMap = pattern.getJSONObject("RouteMap");
            href = routeMap.getString("Href");

    }


    private void storeRoutePatterns(String routeNo, String destination, String direction,String patternNo, String name)
    {
        Route r= RouteManager.getInstance().getRouteWithNumber(routeNo);
        r.setName(name);
        RoutePattern rp =r.getPattern(patternNo,destination,direction);



    }
}

