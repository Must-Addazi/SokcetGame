package org.example.client;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class ClientChat extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Client Chat");

        // Host and Port input
        Label labelHost = new Label("Host:");
        TextField textFieldHost = new TextField("localhost");
        Label labelPort = new Label("Port:");
        TextField textFieldPort = new TextField("1234");

        // Connect button
        Button buttonConnect = new Button("Connect");
        buttonConnect.setOnAction(e -> connectToServer(textFieldHost.getText(), textFieldPort.getText()));

        // Top HBox layout
        HBox hBox = new HBox(10, labelHost, textFieldHost, labelPort, textFieldPort, buttonConnect);
        hBox.setPadding(new Insets(10));
        hBox.setBackground(new Background(new BackgroundFill(Color.ORANGE, null, null)));

        // Chat area and message input
        TextArea chatArea = new TextArea();
        chatArea.setEditable(false);
        chatArea.setPrefHeight(500);

        TextField messageField = new TextField();
        messageField.setPromptText("Enter your message...");
        Button buttonSend = new Button("Send");

        // Action on send button
        buttonSend.setOnAction(e -> sendMessage(messageField.getText(), chatArea, messageField));

        HBox messageBox = new HBox(10, messageField, buttonSend);
        messageBox.setPadding(new Insets(10));

        // Layout setup
        BorderPane borderPane = new BorderPane();
        borderPane.setTop(hBox);
        borderPane.setCenter(chatArea);
        borderPane.setBottom(messageBox);

        Scene scene = new Scene(borderPane, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void connectToServer(String host, String port) {
        // Logique de connexion au serveur
        System.out.println("Connecting to server at " + host + ":" + port);
        // Ici, vous ajouteriez votre logique pour établir une connexion socket
    }

    private void sendMessage(String message, TextArea chatArea, TextField messageField) {
        if (!message.isEmpty()) {
            // Logique pour envoyer le message au serveur
            chatArea.appendText("You: " + message + "\n");
            messageField.clear();
        }
    }
}

