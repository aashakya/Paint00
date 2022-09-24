package com.example.paint00;

import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
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
        int canvHeight = 1080;
        int canvWidth = 1080;

        Canvas canvas= new Canvas(canvHeight,canvWidth); // create the canvas to paint on

//        TabPane tabPane = new TabPane(); // create a tab pane
//
//        Tab tab1 = new Tab();


        GraphicsContext gc = canvas.getGraphicsContext2D();
        initDraw(gc, canvWidth,canvHeight);
        ScrollPane sp = new ScrollPane();

        //create a grid pane
        GridPane gridPane = new GridPane();
        gridPane.add(canvas,0,0);

        // Menu bar
        AppMenu topMenu = new AppMenu(primaryStage, gc,canvas);

        ToolBoxTop toolBoxTop = new ToolBoxTop(canvas,gc);

        layout.getChildren().add(topMenu.menuBar);
        layout.getChildren().add(toolBoxTop.toolBoxTop);
//        layout.getChildren().add(tabPane);
        layout.getChildren().add(gridPane);
        sp.setContent(gridPane);
        layout.getChildren().add(sp);

        Scene scene = new Scene(layout, 10000,1000);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    private void initDraw(GraphicsContext gc, int canvasWidth, int canvasHeight){
        gc.setFill(Color.WHITE);
        gc.fillRect(0, 0, canvasWidth, canvasHeight);
    }
    public static void main(String[] args) {
        launch();
    }
}



