package com.example;
import java.util.ArrayList;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AppGui extends Application {
    public static ArrayList<Airplane> flights;
    public static ArrayList<Airplane> flightsList;
    public static ArrayList<TicketGenerator> tickets;
    public String fromToFilter;
    public String toToFilter;
    public static void main(String[] args) {
        flights = App.initialize();
        tickets = new ArrayList<>();    
        flights.forEach(Airplane::prepareSeats);
            
        launch();
    }


    @Override
    public void start(Stage stage) throws Exception {
        // PRIMARY WINDOW
        Label welcomeLabel = new Label("Welcome to FlightsApps");
        Button showButton = new Button("Show all Flights");
        Button bookButton = new Button("Book a flight");
        Button cancelButton = new Button("Cancel a flight");
        Button exitButton = new Button("Exit Application");
        VBox layout1 =new VBox(20);
        layout1.setAlignment(Pos.BASELINE_CENTER);
        layout1.getChildren().addAll(welcomeLabel,showButton,bookButton,cancelButton, exitButton);
        Scene scene1 = new Scene(layout1, 320, 240);

        // SHOW FLIGHTS WINDOW
        showButton.setOnAction(e -> {
            BorderPane layout2 =new BorderPane();
            VBox topTittle = new VBox();
            StackPane centerTextField = new StackPane();
            VBox bottomTittle = new VBox();
            Button menuButton1 = new Button("Main Menu");
            Label flightsLabel = new Label("These are the available flights");      
            String flightsToText = App.showFlights(flights);
            TextArea test1 = new TextArea(flightsToText);
            topTittle.getChildren().add(flightsLabel);
            centerTextField.getChildren().add(test1);
            bottomTittle.getChildren().add(menuButton1);
            topTittle.setAlignment(Pos.CENTER);
            bottomTittle.setAlignment(Pos.CENTER);
            layout2.setTop(topTittle);
            layout2.setCenter(centerTextField);
            layout2.setBottom(bottomTittle);
            Scene scene2 = new Scene(layout2, 480, 130);
            stage.setScene(scene2);
            test1.setText(App.showFlights(flights));
            menuButton1.setOnAction(i -> stage.setScene(scene1));
        });

        // BOOK A FLIGHT WINDOW
        bookButton.setOnAction(e -> {
            Button menuButton2 = new Button("Main Menu");
            VBox layout3 = new VBox(10);
            Label bookLabel = new Label("Book a flight:");
            layout3.getChildren().addAll(bookLabel, menuButton2);
            layout3.setAlignment(Pos.BASELINE_CENTER);
            Scene scene3 = new Scene(layout3, 320, 330);
            ComboBox<String> fromComboBox = new ComboBox<>(FXCollections.observableArrayList("ROS","BAS"));
            ComboBox<String> toComboBox = new ComboBox<>(FXCollections.observableArrayList("BCN","MDR"));
            layout3.getChildren().addAll(new Label("From:"),fromComboBox,new Label("To:"),toComboBox);
            Button bookFlightButton = new Button("See available flights");
            Button confirmBookFlightButton = new Button("Book selected flight");
            layout3.getChildren().addAll(bookFlightButton);
            ObservableList<String> options3 = FXCollections.observableArrayList();
            ComboBox<String> flightsComboBox = new ComboBox<>(options3);    
            stage.setScene(scene3);
            menuButton2.setOnAction(i -> stage.setScene(scene1));
            // BOOK THE FLIGHT FUNCTIONALITY
            bookFlightButton.setOnAction(o -> {
                layout3.getChildren().add(new Label("These are the available flights:"));
                bookFlightButton.setDisable(true);
                fromToFilter = fromComboBox.getValue().toString();
                toToFilter = toComboBox.getValue().toString();
                flightsList = App.filterFlights(flights, fromToFilter, toToFilter);
                flightsList.forEach((f) -> options3.add(f.toString()));
                layout3.getChildren().addAll(flightsComboBox,confirmBookFlightButton);
            });
           // CONFIRMED FLIGHTS POP UP WINDOW
            confirmBookFlightButton.setOnAction(u -> {
                final Stage dialog = new Stage();
                VBox dialogVbox = new VBox(5);
                dialogVbox.getChildren().add(new Label("Your flight has been successfully booked!"));
                Button goBackButton = new Button("Go back");
                dialogVbox.getChildren().add(goBackButton);
                dialogVbox.setAlignment(Pos.BASELINE_CENTER);
                Scene dialogScene = new Scene(dialogVbox, 250, 70);
                dialog.setScene(dialogScene);
                dialog.show();
                goBackButton.setOnAction(f -> {
                    dialog.close();
                    stage.setScene(scene1);
                    String specifiedFlight = flightsComboBox.getValue().toString();
                    System.out.println(flightsList.get(0).toString().equals(specifiedFlight));
                    App.bookFlights2(flightsList, specifiedFlight, tickets);
                });
            });
        });

        //CANCEL A FLIGHT WINDOW
        cancelButton.setOnAction(e ->{
            Button menuButton = new Button("Main Menu");
            Button confirmCancel = new Button("Cancel selected flight");
            VBox layout = new VBox(10);
            layout.setAlignment(Pos.BASELINE_CENTER);
            Scene scene3 = new Scene(layout, 320, 150);
            ObservableList<String> options = FXCollections.observableArrayList();
            ComboBox<String> flightsComboBox = new ComboBox<>(options);
            tickets.forEach((f) -> options.add(f.toString()));
            layout.getChildren().addAll(new Label("These are your booked flights:"),flightsComboBox);
            layout.getChildren().addAll(confirmCancel,menuButton);
            confirmCancel.setOnAction(u -> {
                String ticketName = flightsComboBox.getValue();
                String flightID = ticketName.substring(11,14);
                String seatID = ticketName.substring(28) ;
                System.out.println(flightID);
                System.out.println(seatID);
                App.cancelFlights(flights, flightID, seatID);    
                ticketEraser(tickets,ticketName);
                final Stage dialog = new Stage();
                VBox dialogVbox = new VBox(5);
                dialogVbox.getChildren().add(new Label("Your flight has been successfully cancelled!"));
                Button mainMenu = new Button("Main menu");
                dialogVbox.getChildren().add(mainMenu);
                dialogVbox.setAlignment(Pos.BASELINE_CENTER);
                Scene dialogScene = new Scene(dialogVbox, 250, 70);
                dialog.setScene(dialogScene);
                dialog.show();
                mainMenu.setOnAction(a -> {
                    dialog.close();
                    stage.setScene(scene1);                    
                });
                
            });
            stage.setScene(scene3);
            menuButton.setOnAction(i -> stage.setScene(scene1));
        });

        // EXIT
        exitButton.setOnAction(e -> {
            stage.close();
        });
        

        // FUNCTIONALITIES        

        stage.setTitle("FlightsApp");
        stage.setScene(scene1);
        stage.show();

    }

    public void ticketEraser(ArrayList<TicketGenerator> tickets, String ticket){
        System.out.println(tickets);
        String trys = tickets.get(0).toString();
        System.out.println(trys.equals(ticket));
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

}
