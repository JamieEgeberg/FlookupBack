/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import com.google.gson.Gson;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;

/**
 * REST Web Service
 *
 * @author Jamie
 */
@Path("Reservation")
public class ReservationResource {

    @Context
    private UriInfo context;

    private static Gson gson = new Gson();

    /**
     * Creates a new instance of ReservationResource
     */
    public ReservationResource() {
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
