package com.example;
import java.util.ArrayList;

public class Airplane {
    private String planeId;
    private String airline;
    private int capacity;
    private String from;
    private String to;
    private ArrayList<Seat> seats;

    public Airplane(String id, String airline,String from, String to, int capacity){
        this.planeId = id;
        this.airline = airline;
        this.capacity = capacity;
        this.from = from;
        this.to = to;
        this.seats = new ArrayList<>();
    }

    public void reserveSeat(ArrayList<TicketGenerator> tickets){
        int i = 0;
        boolean state = false;
        while (i < this.seats.size()){
            if (this.seats.get(i).isAvailable()){
                this.seats.get(i).reserveSeat();
                state = true;
                break;
            }
            i++;
        }
        if (state) {
            TicketGenerator ticketer = new TicketGenerator(this.planeId, this.seats.get(i));
            tickets.add(ticketer);
        } else {
            System.out.println("The flight is full");
        }
    }

    public void cancelReservedSeat(String number){
        int i = 0;
        int Inumber = Integer.valueOf(number);
        while (i <= this.seats.size()-1){
            if (this.seats.get(i).getSeatNumber() == (Inumber)){
                this.seats.get(i).cancelSeat();
            }
            i++;
        }
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

    public void showSeats(){
        this.seats.forEach(System.out::println);
    }

    public void prepareSeats(){
        int i = 1;
        while (i <= capacity){
            seats.add(new Seat(i));
            i++;
        }
    }

    public boolean isItFull(){
        int i = 0;
        while (i < capacity){
            if (!(this.seats.get(i).isAvailable())){
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

    public String toString(){
        return this.getFrom() + "->"+ this.getTo()+ " Airline: " + this.airline + " || Seats available: " + (availableSeats());
    }


}