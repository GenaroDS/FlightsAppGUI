package com.example;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Stack;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class AppGui extends Application {
    public static ArrayList<Airplane> flights;
    public static ArrayList<Airplane> flightsList;
    public String fromToFilter;
    public String toToFilter;
    public static HashMap<String, String> tickets;
    public static void main(String[] args) {
        flights = App.initialize();
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
        Button consultButton = new Button("Consult a flight");
        Button exitButton = new Button("Exit Application");
        VBox layout1 =new VBox(20);
        layout1.setAlignment(Pos.BASELINE_CENTER);
        layout1.getChildren().addAll(welcomeLabel,showButton,bookButton,cancelButton,consultButton, exitButton);
        Scene scene1 = new Scene(layout1, 320, 280);

        // SHOW FLIGHTS WINDOW
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

        // BOOK A FLIGHT WINDOW
        Button menuButton2 = new Button("Main Menu");
        VBox layout3 = new VBox(10);
        Label bookLabel = new Label("Book a flight:");
        layout3.getChildren().addAll(bookLabel, menuButton2);
        layout3.setAlignment(Pos.BASELINE_CENTER);
        Scene scene3 = new Scene(layout3, 320, 330);
        ObservableList<String> options =
                FXCollections.observableArrayList(
                        "ROS",
                        "BAS"
                );
        final ComboBox comboBox = new ComboBox(options);
        layout3.getChildren().addAll(new Label("From:"),comboBox);
        ObservableList<String> options2 =
                FXCollections.observableArrayList(
                        "BCN",
                        "MDR"
                );
        final ComboBox comboBox2 = new ComboBox(options2);
        layout3.getChildren().addAll(new Label("To:"),comboBox2);
        Button bookFlightButton = new Button("See available flights");
        Button ConfirmBookFlightButton = new Button("Book selected flight");
        layout3.getChildren().addAll(bookFlightButton);
        ObservableList<String> options3 =
                FXCollections.observableArrayList(

                );
        final ComboBox comboBox3 = new ComboBox(options3);

        // CONFIRMED FLIGHTS POP UP WINDOW

        final Stage dialog = new Stage();
        VBox dialogVbox = new VBox(5);
        dialogVbox.getChildren().add(new Label("Your flight has been successfully booked!"));
        Button goBackButton = new Button("Go back");
        dialogVbox.getChildren().add(goBackButton);
        dialogVbox.setAlignment(Pos.BASELINE_CENTER);
        Scene dialogScene = new Scene(dialogVbox, 250, 70);

        //CANCEL A FLIGHT WINDOW


        // EXIT
        exitButton.setOnAction(e -> stage.close());


        // FUNCTIONALITIES
        showButton.setOnAction(e -> {
            stage.setScene(scene2);
            test1.setText(App.showFlights(flights));
        });
        menuButton1.setOnAction(e -> stage.setScene(scene1));
        bookButton.setOnAction(e -> stage.setScene(scene3));
        menuButton2.setOnAction(e -> stage.setScene(scene1));

        bookFlightButton.setOnAction(e -> {
            layout3.getChildren().add(new Label("These are the available flights:"));
            bookFlightButton.setDisable(true);
            fromToFilter = comboBox.getValue().toString();
            toToFilter = comboBox2.getValue().toString();
            flightsList = App.filterFlights(flights, fromToFilter, toToFilter);
            System.out.println("Available flights");
            System.out.println(flightsList);
            flightsList.forEach((f) -> options3.add(f.toString()));
            layout3.getChildren().addAll(comboBox3,ConfirmBookFlightButton);
        });

        ConfirmBookFlightButton.setOnAction(e -> {
            dialog.setScene(dialogScene);
            dialog.show();
            goBackButton.setOnAction(f -> {
                dialog.close();
                stage.setScene(scene1);
                String specifiedFlight = comboBox3.getValue().toString();
                System.out.println(flightsList.get(0).toString().equals(specifiedFlight));
                App.bookFlights2(flightsList, specifiedFlight);
                System.out.println(flightsList);
            });
        });




        stage.setTitle("FlightsApp");
        stage.setScene(scene1);
        stage.show();

    }



}
