package com.example;
import java.util.ArrayList;
import java.util.List;

public class Airplane {
    private String planeId;
    private String airline;
    private int capacity;
    private String from;
    private String to;
    private ArrayList<Seat> seats;

    public Airplane(String id, String airline, String from, String to, int capacity){
        this.planeId = id;
        this.airline = airline;
        this.capacity = capacity;
        this.from = from;
        this.to = to;
        this.seats = new ArrayList<>();
    }

    public void reserveSeat(List<TicketGenerator> tickets){
        int index = 0;
        boolean state = false;
        while (index < this.seats.size()){
            if (this.seats.get(index).isAvailable()){
                this.seats.get(index).reserveSeat();
                state = true;
                break;
            }
            index++;
        }
        if (state) {
            TicketGenerator ticket = new TicketGenerator(this.planeId, this.seats.get(index));
            ticket.setAirline(this.airline);
            ticket.setFrom(this.from);
            ticket.setTo(this.to);
            ticket.generateTicket();
            tickets.add(ticket);
        } else {
            System.out.println("The flight is full");
        }
    }

    public void cancelReservedSeat(String number){
        int index = 0;
        int seatNumber = Integer.parseInt(number);
        while (index <= this.seats.size()-1){
            if (this.seats.get(index).getSeatNumber() == (seatNumber)){
                this.seats.get(index).cancelSeat();
            }
            index++;
        }
    }

    public void prepareSeats(){
        int index = 1;
        while (index <= capacity){
            seats.add(new Seat(index));
            index++;
        }
    }

    public boolean isItFull(){
        int index = 0;
        while (index < capacity){
            if (!(this.seats.get(index).isAvailable())){
                return false;
            }
        }
        return true;
    }

    public int availableSeats(){
        int availables = 0;
        int index = 0;
        while (index < this.seats.size()){
            if (this.seats.get(index).isAvailable()){
                availables ++;
            }
            index++;
        }
        return availables;
    }

    public String getPlaneId() {
        return planeId;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public String toString(){
        return this.getFrom() + "->"+ this.getTo()+ " Airline: " + this.airline + " || Seats available: " + (availableSeats());
    }
}
