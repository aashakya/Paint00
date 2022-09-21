package com.example.paint00;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Insets;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Screen;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import javafx.scene.input.MouseEvent;


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
        GraphicsContext gc = canvas.getGraphicsContext2D();
        initDraw(gc, canvWidth,canvHeight);
        ScrollPane sp = new ScrollPane();
        ColorPicker colorPicker = new ColorPicker();
        colorPicker.setValue(Color.BLACK);


        //create a grid pane & border pane
        GridPane gridPane = new GridPane();
        gridPane.add(canvas,0,0);


        HBox toolBoxTop = new HBox();
        toolBoxTop.setPadding(new Insets(15, 12, 15, 12));
        toolBoxTop.setSpacing(10);
        Slider brushsize = new Slider(1,100, 10);

        brushsize.setShowTickLabels(true);
        brushsize.setMajorTickUnit(10f);
        brushsize.setBlockIncrement(10f);
        ToggleButton eraser = new ToggleButton("Eraser");

        toolBoxTop.getChildren().add(0, colorPicker);
        toolBoxTop.getChildren().add(1,brushsize);
        toolBoxTop.getChildren().add(2,eraser);

        canvas.addEventHandler(MouseEvent.MOUSE_PRESSED, event -> {
            gc.setLineWidth(brushsize.getValue());
            if (eraser.isSelected()){
                gc.setStroke(Color.WHITE);
            }
            else{
                gc.setStroke(colorPicker.getValue());
            }
            gc.beginPath();
            gc.moveTo(event.getX(), event.getY());
            gc.stroke();
        });

        canvas.addEventHandler(MouseEvent.MOUSE_DRAGGED,
                event -> {
                    gc.lineTo(event.getX(), event.getY());
                    gc.stroke();
                });

        canvas.addEventHandler(MouseEvent.MOUSE_RELEASED, event -> {

        });
        // Menu bar
        AppMenu topMenu = new AppMenu(primaryStage, layout, gc, gridPane,canvas);

        layout.getChildren().add(toolBoxTop);
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



