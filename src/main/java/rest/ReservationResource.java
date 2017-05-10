/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import com.google.gson.Gson;
import entity.Airline;
import javafx.util.Pair;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Function;

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

    private HashMap<String, String> airlineUrls;

    private final Function<Pair<String, String>, Pair<String, Airline>> d =
            (Pair<String, String> airline) -> {
                return new Pair<>(airline.getKey(),
                                  Flights.c.apply(airline.getValue()));
            };

    /**
     * Creates a new instance of ReservationResource
     */
    public ReservationResource() {
        List<Pair<String, String>> airlines = new ArrayList<>();
        Flights.urls.forEach((url -> airlines.add(makeUrl(url))));
        airlineUrls = makeUrlMap(airlines);
    }

    private Pair<String, String> makeUrl(String url) {
        // the value of the pair is default things
        // so we can get the airline name
        return new Pair<>(url, url + "CPH/" + getToday() + "/" + 1);
    }

    private HashMap<String, String> makeUrlMap(List<Pair<String, String>>
                                                       urls) {
        HashMap<String, String> alUrls = new HashMap<>();
        urls.stream()
            .map(d)
            .forEach((pair) -> alUrls.put(pair.getValue().airline, // name
                                          pair.getKey())); // url
        return alUrls;
    }


    /**
     * Retrieves representation of an instance of rest.ReservationResource
     *
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getJson() {
        //TODO return proper representation object
        throw new UnsupportedOperationException();
    }

    @POST
    @Path("/{flightId}")
    @Produces(MediaType.APPLICATION_JSON)
    public String postFlightsReservation(@PathParam("flightId") String flightId) {

        return gson.toJson("stuff");    //DO STUFF!!!
    }
}
