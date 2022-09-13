package com.example;
import java.util.ArrayList;
import java.util.List;
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

    public static void main(String[] args) {        
        App.initialize();        
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
        stage.setTitle("FlightsApp");
        stage.setScene(scene1);
        stage.show();

        // SHOW FLIGHTS WINDOW
        showButton.setOnAction(e -> {
            BorderPane layout2 =new BorderPane();
            VBox topTittle = new VBox();
            StackPane centerTextField = new StackPane();
            VBox bottomTittle = new VBox();
            Button menuButton1 = new Button("Main Menu");
            Label flightsLabel = new Label("These are the available flights");      
            String flightsToText = App.showFlights();
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
            test1.setText(App.showFlights());
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
            Button showFlightsButton = new Button("See available flights");
            Button confirmBookFlightButton = new Button("Book selected flight");
            layout3.getChildren().addAll(showFlightsButton);
            ObservableList<String> options3 = FXCollections.observableArrayList();
            ComboBox<String> flightsComboBox = new ComboBox<>(options3);    
            stage.setScene(scene3);
            menuButton2.setOnAction(i -> stage.setScene(scene1));
            // BOOK THE FLIGHT FUNCTIONALITY
            showFlightsButton.setOnAction(o -> {
                // CHECK IF COMBOBOXES HAVE VALUE, ELSE POP-UP WINDOW
                if ((fromComboBox.getValue() != null) && (toComboBox.getValue() != null)){
                    layout3.getChildren().add(new Label("These are the available flights:"));
                    showFlightsButton.setDisable(true);  
                    fromComboBox.setDisable(true);   
                    toComboBox.setDisable(true);           
                    String fromToFilter = fromComboBox.getValue().toString();
                    String toToFilter = toComboBox.getValue().toString();
                    App.filterFlights(fromToFilter, toToFilter); // NI ENVIAMOS FLIGHTS NI CREAMOS FILTER FLIGHTS
                    App.getFlightsList().forEach((f) -> options3.add(f.toString()));
                    layout3.getChildren().addAll(flightsComboBox,confirmBookFlightButton);
                }else{
                    final Stage dialog = new Stage();
                    VBox dialogVbox = new VBox(5);
                    dialogVbox.getChildren().add(new Label("Please select you departure and destination!"));
                    Button confirm = new Button("Close");
                    dialogVbox.getChildren().add(confirm);
                    dialogVbox.setAlignment(Pos.BASELINE_CENTER);
                    Scene dialogScene = new Scene(dialogVbox, 250, 70);
                    dialog.setScene(dialogScene);
                    dialog.show();
                    confirm.setOnAction(i ->dialog.close());
                }
               
            });
           // CONFIRMED FLIGHTS POP UP WINDOW
             confirmBookFlightButton.setOnAction(u -> {
                final Stage dialog = new Stage();
                VBox dialogVbox = new VBox(5);
                dialogVbox.getChildren().add(new Label("Your flight has been successfully booked!"));
                Button mainMenuButton = new Button("Main Menu");
                dialogVbox.getChildren().add(mainMenuButton);
                dialogVbox.setAlignment(Pos.BASELINE_CENTER);
                Scene dialogScene = new Scene(dialogVbox, 250, 70);
                String[] seatsAvailableString = flightsComboBox.getValue().split("Seats available: ");
                int seatsAvailable = Integer.valueOf(seatsAvailableString[1]);
                if (flightsComboBox.getValue() != null && seatsAvailable > 0){
                    dialog.setScene(dialogScene);
                    dialog.show();
                    fromComboBox.setDisable(true);
                    toComboBox.setDisable(true);
                    String specifiedFlight = flightsComboBox.getValue().toString();
                    App.bookFlights2(specifiedFlight); //                 
                    mainMenuButton.setOnAction(f -> {
                        dialog.close();
                        stage.setScene(scene1);                            
                    });
                } else if (seatsAvailable == 0){
                    dialogVbox.getChildren().clear();
                    Button closeButton = new Button("Close");
                    dialogVbox.getChildren().addAll(new Label("The selected flight has no seats avaiables."), closeButton);
                    dialog.setScene(dialogScene);   
                    dialog.show();
                    closeButton.setOnAction(f ->{
                        dialog.close();
                        stage.setScene(scene1);
                    }); 
                }
                else {
                    dialogVbox.getChildren().clear();
                    Button closeButton = new Button("Close");
                    dialogVbox.getChildren().addAll(new Label("Please select your desired flight!"), closeButton);
                    dialog.setScene(dialogScene);
                    dialog.show();
                    closeButton.setOnAction(f -> dialog.close());
                }
            });
        });

        // CANCEL A FLIGHT WINDOW
        cancelButton.setOnAction(e ->{
            Button menuButton = new Button("Main Menu");
            Button confirmCancel = new Button("Cancel selected flight");
            VBox layout = new VBox(10);
            layout.setAlignment(Pos.BASELINE_CENTER);
            Scene scene3 = new Scene(layout, 340, 150);
            ObservableList<String> options = FXCollections.observableArrayList();
            ComboBox<String> flightsComboBox = new ComboBox<>(options);
            App.getTickets().forEach((f) -> options.add(f.toString()));
            layout.getChildren().addAll(new Label("These are your booked flights:"),flightsComboBox);
            layout.getChildren().addAll(confirmCancel,menuButton);
            confirmCancel.setOnAction(u -> {
                // CHECK IF THERE'S A SELECTED VALUE, IF THERE'S NOT, DIALOG WINDOW WILL POP UP
                if (flightsComboBox.getValue() == null){
                    final Stage dialog = new Stage();
                    VBox dialogVbox = new VBox(5);
                    dialogVbox.getChildren().add(new Label("Please select a ticket to cancel"));
                    Button closeButton = new Button("Close");
                    dialogVbox.getChildren().add(closeButton);
                    dialogVbox.setAlignment(Pos.BASELINE_CENTER);
                    Scene dialogScene = new Scene(dialogVbox, 250, 70);
                    dialog.setScene(dialogScene);
                    dialog.show();
                    closeButton.setOnAction(f -> dialog.close());
                }else{// IF THERE'S IS A VALUE, CENCEL THE TICKET
                    String ticketName = flightsComboBox.getValue();
                    String[] newTicketName = ticketName.split("@");
                    String flightID = newTicketName[0].toString().substring(16,19);
                    String seatID = newTicketName[0].toString().substring(19);
                    App.cancelFlights(flightID, seatID);    
                    App.ticketEraser(ticketName);
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
                }
            });
            stage.setScene(scene3);
            menuButton.setOnAction(i -> stage.setScene(scene1));
        });

        // EXIT
        exitButton.setOnAction(e -> {
            stage.close();
        });
    }

}
