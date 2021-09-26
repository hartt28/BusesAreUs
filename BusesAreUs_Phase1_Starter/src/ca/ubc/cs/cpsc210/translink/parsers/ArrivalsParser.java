package ca.ubc.cs.cpsc210.translink.parsers;

import ca.ubc.cs.cpsc210.translink.model.Arrival;
import ca.ubc.cs.cpsc210.translink.model.RouteManager;
import ca.ubc.cs.cpsc210.translink.model.Stop;
import ca.ubc.cs.cpsc210.translink.parsers.exception.ArrivalsDataMissingException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * A parser for the data returned by the Translink arrivals at a stop query
 */
public class ArrivalsParser {

    /**
     * Parse arrivals from JSON response produced by TransLink query.  All parsed arrivals are
     * added to the given stop assuming that corresponding JSON object has a RouteNo: and an
     * array of Schedules:
     * Each schedule must have an ExpectedCountdown, ScheduleStatus, and Destination.  If
     * any of the aforementioned elements is missing, the arrival is not added to the stop.
     *
     * @param stop             stop to which parsed arrivals are to be added
     * @param jsonResponse    the JSON response produced by Translink
     * @throws JSONException  when:
     * <ul>
     *     <li>JSON response does not have expected format (JSON syntax problem)</li>
     *     <li>JSON response is not an array</li>
     * </ul>
     * @throws ArrivalsDataMissingException  when no arrivals are found in the reply
     */
    public static void parseArrivals(Stop stop, String jsonResponse)
            throws JSONException, ArrivalsDataMissingException {
        // TODO: Task 4: Implement this method

        try{
            JSONArray stop43= new JSONArray(jsonResponse);
        }catch(JSONException e)
        {
            throw new JSONException("Wrong arrival format");
        }
            JSONObject s43 = new JSONArray(jsonResponse).getJSONObject(0);
        if(s43.has("RouteNo")==false)
            throw new ArrivalsDataMissingException();

            String routeNo=s43.getString("RouteNo");
            JSONArray schedules =null;
            try {
                schedules = s43.getJSONArray("Schedules");
            }
            catch (JSONException e)
            {
                throw new JSONException("Wrong schedule format");
            }
            for(int j=0;j<schedules.length();j++)
            {
                JSONObject schedule = schedules.getJSONObject(j);
                if(!schedule.has("ExpectedCountdown")
                        &&schedule.has("Destination")
                        &&schedule.has("ScheduleStatus"))
                    throw new ArrivalsDataMissingException();

                int expectedCountDown=schedule.getInt("ExpectedCountdown");
                String destination=schedule.getString("Destination");
                String status = schedule.getString("ScheduleStatus");
                stop.addArrival(new Arrival(expectedCountDown,destination, RouteManager.getInstance().getRouteWithNumber(routeNo)));
            }
        }


}
