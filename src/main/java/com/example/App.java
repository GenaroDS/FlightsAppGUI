package com.example;
import java.util.ArrayList;
import java.util.List;

import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class App {
    private static List<Airplane> flights;
    private static List<Airplane> flightsList;
    private static List<TicketGenerator> tickets;
    
    private App() {
        throw new IllegalStateException("Functionality class");
      }

    public static void initialize(){
        flights = new ArrayList<>();
        tickets = new ArrayList<>();
        flightsList = new ArrayList<>();
        Airplane flight1 = new Airplane("001", "Fly Emirates", "ROS", "BCN", 2);
        Airplane flight2 = new Airplane("002", "Fly Emirates", "BAS", "BCN", 30);
        Airplane flight3 = new Airplane("003", "Fly Bondi", "ROS", "MDR", 20);
        Airplane flight4 = new Airplane("004", "Fly Bondi", "BAS", "MDR", 30);
        flights.add(flight1);
        flights.add(flight2);
        flights.add(flight3);
        flights.add(flight4);
        flights.forEach(Airplane::prepareSeats);        
    }

    public static void filterFlights(String from, String to){
        ArrayList<Airplane> newList = new ArrayList<>();
        int index = 0;
        while (flights.size() > index){
            if ((flights.get(index).getFrom().equals(from)) && (flights.get(index).getTo().equals(to))){
                newList.add(flights.get(index));
            }
            index++;
        }
        flightsList = newList;
    }

    public static void bookFlights(String flight) {
        if (!(flights.isEmpty())) {
            int index = 0;
            while (index <= flights.size() - 1) {
                if (flights.get(index).toString().equals(flight)) {
                    flights.get(index).reserveSeat(tickets);
                }
                index++;
            }
        }
    }

    public static void cancelFlights(String planeID, String seatID){      
        int index = 0;
        while (index <= flights.size()-1){            
            if (flights.get(index).getPlaneId().equals(planeID)){              
                flights.get(index).cancelReservedSeat(seatID);
            }
            index++;
        }
    }

    public static String showFlights(){
        StringBuilder toReturn = new StringBuilder();
        for (Airplane plane : flights){
            toReturn.append(plane.toString());
            toReturn.append("\n");
        }
        return new String(toReturn);
    }
    
    public static List<Airplane> getFlights() {
        return flights;
    }

    public static void setFlights(List<Airplane> flights) {
        App.flights = flights;
    }

    public static List<Airplane> getFlightsList() {
        return flightsList;
    }

    public static void setFlightsList(List<Airplane> flightsList) {
        App.flightsList = flightsList;
    }
    
    public static List<TicketGenerator> getTickets() {
        return tickets;
    }

    public static void setTickets(List<TicketGenerator> tickets) {
        App.tickets = tickets;
    }

    public static void ticketEraser(String ticket){
        int i = 0;
        String ticketToString = "";
        while ( i < tickets.size()){
            ticketToString = tickets.get(i).toString();
            if (ticketToString.equals(ticket)){
                tickets.remove(i);
            }
            i++;
        }        
    }

    public static String dialogBoxProcessing(ComboBox<String> comboBoxValue, VBox dialogVBox){
        if (comboBoxValue.getValue() == null){
            dialogVBox.getChildren().clear();
            Button closeButton = new Button("Close");
            dialogVBox.getChildren().addAll(new Label("Please select your desired flight!"), closeButton);
            return "A" ;
        }
        String[] seatsAvailableString = comboBoxValue.getValue().split("Seats available: ");
        int seatsAvailable = Integer.parseInt(seatsAvailableString[1]);
        if (seatsAvailable == 0){
            dialogVBox.getChildren().clear();            
            dialogVBox.getChildren().addAll(new Label("The selected flight has no seats availables."));
            return "B";
        }
        if (comboBoxValue.getValue() != null && seatsAvailable > 0){
            dialogVBox.getChildren().add(new Label("Your flight has been successfully booked!"));
            Button mainMenuButton = new Button("Main Menu");
            dialogVBox.getChildren().add(mainMenuButton);
            return "C";
        }
        return "D";

    }


}

