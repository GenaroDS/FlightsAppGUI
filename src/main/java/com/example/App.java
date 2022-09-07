package com.example;
import java.util.ArrayList;
import java.util.Scanner;

public class App  {

    public static void bookFlights(ArrayList<Airplane> flights, Scanner scanner, ArrayList<TicketGenerator> tickets){
        System.out.println("Where do you want to travel?");
        String destination = scanner.nextLine();
        System.out.println("From where?");
        String departure = scanner.nextLine();
        ArrayList<Airplane> filteredFlights = filterFlights(flights,departure,destination);
        if (!(filteredFlights.isEmpty())){
            System.out.println("This are the available flights");
            filteredFlights.forEach(System.out::println);
            System.out.println("Select you desired flight (Flight ID)");
            String flightID = scanner.nextLine();
            int i = 0;
            while (i <= filteredFlights.size()-1){
                if (filteredFlights.get(i).getPlaneId().equals(flightID)){
                    filteredFlights.get(i).reserveSeat(tickets);
                }
                i++;
            }
        } else{
            System.out.println("There are no available flights that match your requisites.");
        }
    }
    public static void bookFlights2(ArrayList<Airplane> flights, String flight, ArrayList<TicketGenerator> tickets) {
        if (!(flights.isEmpty())) {
            int i = 0;
            while (i <= flights.size() - 1) {
                if (flights.get(i).toString().equals(flight)) {
                    flights.get(i).reserveSeat(tickets);
                }
                i++;
            }
        }
    }

    public static String showFlights(ArrayList<Airplane> flights){
        StringBuilder toReturn = new StringBuilder();
        for (Airplane plane : flights){
            toReturn.append(plane.toString());
            toReturn.append("\n");
        }
        return new String(toReturn);
    }


    public static void cancelFlights(ArrayList<Airplane> flights, Scanner scanner){        
        System.out.println("Insert the ID of the desired ticket to cancel.");
        String ticketToCancel = scanner.nextLine();
        String flightId = ticketToCancel.substring(1, 4);
        String seatId = ticketToCancel.substring(4);
        int i = 0;
        while (i <= flights.size()-1){
            if (flights.get(i).getPlaneId().equals(flightId)){
                flights.get(i).cancelReservedSeat(seatId);
                System.out.println("You flight has been succesfully canceled.");
            }
            i++;
        }
    }


    public static ArrayList<Airplane> initialize(){
        Airplane flight1 = new Airplane("001", "Fly Emirates", "ROS", "BCN", 2);
        Airplane flight2 = new Airplane("002", "Fly Emirates", "BAS", "BCN", 30);
        Airplane flight3 = new Airplane("003", "Fly Bondi", "ROS", "MDR", 20);
        Airplane flight4 = new Airplane("004", "Fly Bondi", "BAS", "MDR", 30);
        ArrayList<Airplane> flights = new ArrayList<>();
        
        
        flights.add(flight1);
        flights.add(flight2);
        flights.add(flight3);
        flights.add(flight4);
        return flights;
    }


    public static ArrayList<Airplane> filterFlights(ArrayList<Airplane> flights, String from, String to){
        ArrayList<Airplane> newList = new ArrayList<>();
        int index = 0;
        while (flights.size() > index){
            if ((flights.get(index).getFrom().equals(from)) && (flights.get(index).getTo().equals(to))){
                newList.add(flights.get(index));
            }
            index++;
        }
        return newList;
    }



}

