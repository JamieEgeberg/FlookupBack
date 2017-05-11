/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import entity.Reservation;
import entity.ReservationResponse;
import utils.util;

import javax.net.ssl.HttpsURLConnection;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.io.*;
import java.net.URL;
import java.util.HashMap;
import java.util.function.Function;

/**
 * REST Web Service
 *
 * @author Jamie
 */
@Path("reservation")
public class ReservationResource {

    private static Gson gson = new Gson();
    private static GsonBuilder builder;
    private static Gson gsonOut;

    private static HashMap<String, String> airlineUrls = util.urls();

    private static Function<Reservation, ReservationResponse> e =
            (Reservation res) -> {
                HttpsURLConnection conn = null;
                try {
                    URL url = new URL(airlineUrls.get(res.getAirline())
                                              + "reservation/"
                                              + res.getFlightID());
                    conn = (HttpsURLConnection) url.openConnection();
                    conn.setRequestProperty("Method", "POST");
                    conn.setDoOutput(true);
                    OutputStream os = conn.getOutputStream();
                    PrintWriter pw = new PrintWriter(os, true);
                    String output = gsonOut.toJson(res);
                    pw.println(output);
                    pw.close();
                    InputStream is = conn.getInputStream();
                    BufferedReader rd = new BufferedReader(new InputStreamReader(is));
                    StringBuilder sb = new StringBuilder();
                    String line;
                    while ((line = rd.readLine()) != null) {
                        sb.append(line);
                        sb.append('\r');
                    }
                    rd.close();
                    return gson.fromJson(sb.toString(), ReservationResponse
                            .class);
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                } finally {
                    if (conn != null) conn.disconnect();
                }
            };

    /**
     * Creates a new instance of ReservationResource
     */
    public ReservationResource() {
        builder = new GsonBuilder();
        builder.excludeFieldsWithoutExposeAnnotation();
        gsonOut = builder.create();
    }

    @POST
    @Path("{flightId}")
    @Produces(MediaType.APPLICATION_JSON)
    public String postFlightsReservation(@PathParam("flightId") String
                                                 flightId, String json) {
        Reservation res = gson.fromJson(json, Reservation.class);
        return gsonOut.toJson(e.apply(res));
    }
}
