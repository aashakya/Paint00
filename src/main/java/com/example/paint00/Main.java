package com.example.paint00;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.atomic.AtomicReference;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws IOException {
        //set up the title for the stage
        primaryStage.setTitle("Paint 00");

        Canvas canvas= new Canvas(500,500); // create the canvas to paint on
        AtomicReference<GraphicsContext> g = new AtomicReference<>(canvas.getGraphicsContext2D());
        VBox layout = new VBox();// create a vbox with 7 spacing between each child

        final String[] nameOfFile = new String[1]; // to save file
        final String[] ext = new String[1];
        //create a grid pane & border pane
        GridPane gridPane = new GridPane();
//        BorderPane borderPane = new BorderPane();
//        borderPane.setPrefSize(500,400);
        //create the menu
        Menu topMenu = new Menu("File");

        ImageView imageInserted = new ImageView();

        //crete menu items
        MenuItem saveItem = new MenuItem("Save");
        MenuItem saveAsItem = new MenuItem("Save As");
        MenuItem openItem = new MenuItem("Open Image");
        MenuItem closeApp = new MenuItem("Close");

        //add menu item to menu
        topMenu.getItems().add(saveItem);
        topMenu.getItems().add(saveAsItem);
        topMenu.getItems().add(openItem);
        topMenu.getItems().add(closeApp);

        //create a menu bar
        MenuBar topBar = new MenuBar();

        //add menu to the menu bar
        topBar.getMenus().add(topMenu);


        // for opening the file
        FileChooser fileChooser = new FileChooser();
        openItem.setOnAction(e -> {
            fileChooser.setTitle("Open File");
            fileChooser.getExtensionFilters().addAll(              //Including filters for extension types
                    new ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif", "*.jpeg"),
                    new ExtensionFilter("All Files", "*.*")
            );
            File insImg = fileChooser.showOpenDialog(primaryStage);
            if (insImg != null) {
                try {
                    InputStream io = new FileInputStream(insImg);
                    Image img = new Image(io);

                    imageInserted.setImage(img);        //Specifying placement & sizing of selected image
                    imageInserted.setFitWidth(img.getWidth());
                    imageInserted.setFitHeight(img.getHeight());
                    imageInserted.setPreserveRatio(true);

                    g.set(canvas.getGraphicsContext2D());
                    g.get().getCanvas().setWidth(img.getWidth());
                    g.get().getCanvas().setHeight(img.getHeight());
                    g.get().drawImage(img, 0,0, img.getWidth(),img.getHeight());
                    gridPane.add(canvas,0,0);
                }
                catch (IOException ex) {
                    System.out.println("Error");
                }
            }
        });
        saveAsItem.setOnAction(e -> {
            FileChooser saveImgAs = new FileChooser();
            saveImgAs.setTitle("Save image as");
            saveImgAs.getExtensionFilters().addAll(
                    new ExtensionFilter("PNG file","*.png"),
                    new ExtensionFilter("PDF file","*.pdf"),
                    new ExtensionFilter("BMP file","*.bmp"));
            File savedImg = saveImgAs.showSaveDialog(null);
            nameOfFile[0] = savedImg.getName();
            ext[0] = nameOfFile[0].substring(1 + nameOfFile[0].lastIndexOf(".")).toLowerCase();
            try {           //attempt to make a save file from the inserted image
                WritableImage writableImage = new WritableImage((int) canvas.getWidth(), (int) canvas.getHeight());
                canvas.snapshot(null,writableImage);
                ImageIO.write(SwingFXUtils.fromFXImage(writableImage,null), ext[0], savedImg);
            } catch (IOException o) {   //If the above line breaks, throw an exception
                throw new RuntimeException(o);
            }
        });
        saveItem.setOnAction(e -> {
            try {           //attempt to make a save file from the inserted image
                WritableImage writableImage = new WritableImage((int) canvas.getWidth(), (int) canvas.getHeight());
                canvas.snapshot(null,writableImage);
                ImageIO.write(SwingFXUtils.fromFXImage(imageInserted.getImage(),null), ext[0], new File(nameOfFile[0]));
                System.out.println(new File(nameOfFile[0]));
            } catch (IOException o) {   //If the above line breaks, throw an exception
                throw new RuntimeException(o);
            }
        });
        closeApp.setOnAction(e-> Platform.exit());

        HBox toolBoxTop = new HBox();
        toolBoxTop.setPadding(new Insets(15, 12, 15, 12));
        toolBoxTop.setSpacing(10);
        ColorPicker colorPicker = new ColorPicker();
        TextField brushsize = new TextField();
        ToggleButton eraser = new ToggleButton("Eraser");

        toolBoxTop.getChildren().add(0, colorPicker);
        toolBoxTop.getChildren().add(1,brushsize);
        toolBoxTop.getChildren().add(2,eraser);

        canvas.setOnMouseDragged(e -> {
            double size = 25;
            double x = e.getX() - size/2;
            double y = e.getY() - size/2;
            if (eraser.isSelected()){
                g.get().clearRect(x, y, size, size);
            }
            else {
                g.get().setFill(colorPicker.getValue());
                g.get().fillRect(x,y,size,size);
            }
        });

        layout.getChildren().add(topBar);
        layout.getChildren().add(toolBoxTop);
        layout.getChildren().add(gridPane);

//        borderPane.setTop(layout);
//        borderPane.setCenter(canvasPane);
        Scene scene = new Scene(layout, 1000,1000);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}



