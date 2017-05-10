/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import com.google.gson.annotations.Expose;
import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 *
 * @author Jamie
 */
@Entity(name = "Reservation")
public class Reservation implements Serializable {

    public Reservation() {
    }

    public Reservation(String flightID, int numberOfSeats, String reserveeName, String reversePhone, String reserveeEmail, List<Passenger> passengers) {
        this.flightID = flightID;
        this.numberOfSeats = numberOfSeats;
        this.reserveeName = reserveeName;
        this.reversePhone = reversePhone;
        this.reserveeEmail = reserveeEmail;
        this.passengers = passengers;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    String airline;
    @Expose
    String flightID;

    @Expose
    int numberOfSeats;

    @Expose
    String reserveeName;

    @Expose
    String reversePhone;

    @Expose
    String reserveeEmail;

    @OneToMany(cascade = CascadeType.PERSIST)
    @Expose
    List<Passenger> passengers;

    public int getId() {
        return id;
    }

    public String getAirline() {
        return airline;
    }

    public String getFlightID() {
        return flightID;
    }

    public void setFlightID(String flightID) {
        this.flightID = flightID;
    }

    public int getNumberOfSeats() {
        return numberOfSeats;
    }

    public void setNumberOfSeats(int numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }

    public String getReserveeName() {
        return reserveeName;
    }

    public void setReserveeName(String reserveeName) {
        this.reserveeName = reserveeName;
    }

    public String getReversePhone() {
        return reversePhone;
    }

    public void setReversePhone(String reversePhone) {
        this.reversePhone = reversePhone;
    }

    public String getReserveeEmail() {
        return reserveeEmail;
    }

    public void setReserveeEmail(String reserveeEmail) {
        this.reserveeEmail = reserveeEmail;
    }

    public List<Passenger> getPassengers() {
        return passengers;
    }

    public void setPassengers(List<Passenger> passengers) {
        this.passengers = passengers;
    }

}
