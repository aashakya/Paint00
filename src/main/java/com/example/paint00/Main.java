package com.example.paint00;

import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;


public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        //set up the title for the stage
        primaryStage.setTitle("Paint 00");
        // Get primary screen bounds
        Rectangle2D screenBounds = Screen.getPrimary().getBounds();
        primaryStage.setX(screenBounds.getMinX());
        primaryStage.setY(screenBounds.getMinY());
        VBox layout = new VBox(7);// create a vbox with 7 spacing between each child

        CanvasPane canvas= new CanvasPane(); // create the canvas to paint on

//        TabPane tabPane = new TabPane(); // create a tab pane
//
//        Tab tab1 = new Tab();

        ScrollPane sp = new ScrollPane();

        // Menu bar
        AppMenu topMenu = new AppMenu(primaryStage, canvas);

        ToolBoxTop toolBoxTop = new ToolBoxTop(canvas);

        layout.getChildren().add(topMenu.menuBar);
        layout.getChildren().add(toolBoxTop.toolBoxTop);
        sp.setContent(canvas);
        layout.getChildren().add(sp);

        Scene scene = new Scene(layout, 10000,1000);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}



