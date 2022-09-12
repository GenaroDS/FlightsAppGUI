package com.example;

public class TicketGenerator {
    private String ticket;
    private String flightID;
    private Seat seat;
    private String from;
    private String to;
    private String airline;

    public void setFrom(String from) {
        this.from = from;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public void setAirline(String airline) {
        this.airline = airline;
    }

    public void printThisTicket(){
        System.out.println(this.ticket);
    }

    public String getflightID(){
        return this.flightID;        
    }

    public String getSeat(){
        return Integer.toString(this.seat.getSeatNumber());
    }

    public TicketGenerator(String flightID, Seat seat){
        this.ticket = "";
        this.flightID = flightID;
        this.seat = seat;
        this.from = "";
        this.to = "";  
        this.airline = "";    
    }

    public String generateTicket(){
        this.ticket += flightID;
        this.ticket += seat.getSeatNumber();
        return this.ticket;
    }

    public String toString(){
        return "Ticket Number: #" + this.ticket + "@" + this.from + "->"+ this.to + " Airline: " + this.airline;
    }

}