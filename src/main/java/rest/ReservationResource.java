/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import entity.Airline;
import entity.Reservation;
import entity.ReservationResponse;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URL;
import javafx.util.Pair;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Function;
import javax.net.ssl.HttpsURLConnection;

import static utils.util.getToday;

/**
 * REST Web Service
 *
 * @author Jamie
 */
@Path("reservation")
public class ReservationResource {

    @Context
    private UriInfo context;

    private static Gson gson = new Gson();
    private static GsonBuilder builder;
    private static Gson gsonOut;

    private HashMap<String, String> airlineUrls;

    private final Function<Pair<String, String>, Pair<String, Airline>> d
            = (Pair<String, String> airline) -> {
                return new Pair<>(airline.getKey(),
                        Flights.c.apply(airline.getValue()));
            };

    public final Function<Reservation, ReservationResponse> e = (Reservation res) -> {
        HttpsURLConnection conn = null;
        try {
            URL url = new URL(airlineUrls.get(res.getAirline()) + "reservation/" + res.getFlightID());
            conn = (HttpsURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            OutputStream os = conn.getOutputStream();
            PrintWriter pw = new PrintWriter(os, true);

            String output = gsonOut.toJson(res);
            pw.println(output);

            InputStream is = conn.getInputStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = rd.readLine()) != null) {
                sb.append(line);
                sb.append('\r');
            }
            rd.close();
            return gson.fromJson(res.toString(), ReservationResponse.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
    };

    /**
     * Creates a new instance of ReservationResource
     */
    public ReservationResource() {
        builder = new GsonBuilder();
        builder.excludeFieldsWithoutExposeAnnotation();
        gsonOut = builder.create();
        List<Pair<String, String>> airlines = new ArrayList<>();
        Flights.urls.forEach((url -> airlines.add(makeUrl(url))));
        airlineUrls = makeUrlMap(airlines);
    }

    private Pair<String, String> makeUrl(String url) {
        // the value of the pair is default things
        // so we can get the airline name
        return new Pair<>(url, url + "flights/" + "CPH/" + getToday() + "/" + 1);
    }

    private HashMap<String, String> makeUrlMap(List<Pair<String, String>> urls) {
        HashMap<String, String> alUrls = new HashMap<>();
        urls.stream()
                .map(d)
                .forEach((pair) -> alUrls.put(pair.getValue().airline, // name
                        pair.getKey())); // url
        return alUrls;
    }

    @POST
    @Path("/{flightId}")
    @Produces(MediaType.APPLICATION_JSON)
    public String postFlightsReservation(@PathParam("flightId") String flightId, String json) {
        Reservation res = gson.fromJson(json, Reservation.class);
        return gson.toJson(e.apply(res));
    }
}
