package rest;

import com.google.gson.Gson;
import entity.Airline;

import javax.net.ssl.HttpsURLConnection;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.ws.rs.POST;

/**
 * Created by Niki on 2017-05-02.
 *
 * @author Niki
 */
@Path("flights")
public class Flights {

    private static Gson gson = new Gson();

    private List<String> urls;

    private final Function<String, Airline> c = (String airline) -> {
        HttpsURLConnection conn = null;
        try {
            URL url = new URL(airline);
            conn = (HttpsURLConnection) url.openConnection();
            InputStream is = conn.getInputStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is));
            StringBuilder res = new StringBuilder();
            String line;
            while ((line = rd.readLine()) != null) {
                res.append(line);
                res.append('\r');
            }
            rd.close();
            return gson.fromJson(res.toString(), Airline.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
    };

    public Flights() {
        urls = new ArrayList<>();
        urls.add("https://airline.skaarup.io/api/flights/");
        //urls.add("https://airline-plaul.rhcloud.com/api/flightinfo/");
    }

    //Test path: CPH/2017-05-02T00:00:00.000Z/2
    @GET
    @Path("/{from}/{date}/{tickets}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getFlightsFromDateTickets(@PathParam("from") String from,
            @PathParam("date") String date,
            @PathParam("tickets") int tickets) {
        List<Airline> airlines;
        System.out.println(gson.toJson(c.apply("https://airline.skaarup"
                + ".io/api/flights/")));
        airlines = urls.parallelStream()
                .map(string -> c.apply(string + from
                        + "/" + date
                        + "/" + tickets))
                .collect(Collectors.toList());

        return gson.toJson(airlines);
    }

    @GET
    @Path("/{from}/{to}/{date}/{tickets}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getFlightsFromDateTickets(@PathParam("from") String from,
            @PathParam("to") String to,
            @PathParam("date") String date,
            @PathParam("tickets") int tickets) {
        List<Airline> airlines;

        airlines = urls.parallelStream()
                .map(string -> c.apply(string + from
                        + "/" + to
                        + "/" + date
                        + "/" + tickets))
                .collect(Collectors.toList());

        return gson.toJson(airlines);
    }

    
}
