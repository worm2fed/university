package com.spo.lab3;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Main extends Application {
    
    private Stage primaryStage;
    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader load = new FXMLLoader();
        AnchorPane root = (AnchorPane) load.load(getClass().getResourceAsStream("GUI.fxml"));
        primaryStage.setTitle("SPO3");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
        
        GUIController controller = load.getController();
        //controller.setMain(this);
    }
    
    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void main(String[] args) {
        launch(args);
    }
    
}
