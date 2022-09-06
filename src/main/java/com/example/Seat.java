package com.example;

public class Seat {
    private int seatNumber;
    private boolean available;

    public Seat(int seatNumber){
        this.seatNumber = seatNumber;
        this.available = true;
    }

    public int getSeatNumber() {
        return seatNumber;
    }
    public boolean isAvailable(){
        return available;
    }

    public void reserveSeat(){
        this.available = false;
    }

    public void cancelSeat(){
        this.available = true;
    }

    public String toString(){
        if (isAvailable()){
            return "Seat number: " + this.seatNumber + " is available";
        } else{
            return "Seat number: " + this.seatNumber + " is not available";
        }
    }
}