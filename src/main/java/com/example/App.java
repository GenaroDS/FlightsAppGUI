package com.example;
import java.util.ArrayList;
import java.util.List;

public class App {
    private App() {
        throw new IllegalStateException("Functionality class");
      }

    public static void bookFlights2(List<Airplane> flights, String flight, List<TicketGenerator> tickets) {
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

    public static void cancelFlights(List<Airplane> flights, String planeID, String seatID){      
        int index = 0;
        while (index <= flights.size()-1){            
            if (flights.get(index).getPlaneId().equals(planeID)){              
                flights.get(index).cancelReservedSeat(seatID);
            }
            index++;
        }
    }

    public static String showFlights(List<Airplane> flights){
        StringBuilder toReturn = new StringBuilder();
        for (Airplane plane : flights){
            toReturn.append(plane.toString());
            toReturn.append("\n");
        }
        return new String(toReturn);
    }

    public static List<Airplane> initialize(){
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

    public static List<Airplane> filterFlights(List<Airplane> flights, String from, String to){
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

