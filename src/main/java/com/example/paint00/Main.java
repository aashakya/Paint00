package com.example.paint00;

import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {
        //set up the title for the stage
        primaryStage.setTitle("Paint 00");

        final String[] nameOfFile = new String[1];
        //create a grid pane & border pane
        GridPane gridPane = new GridPane();
        BorderPane borderPane = new BorderPane();
        borderPane.setPrefSize(500,400);

        //create a canvas
        //Canvas canvas = new Canvas(1080, 790);
        //GraphicsContext gc = canvas.getGraphicsContext2D();
        //gc.setFill(Color.WHITE);
        //create the menu
        Menu topMenu = new Menu("File");

        ImageView imageInserted = new ImageView();

        //crete menu items
        MenuItem saveItem = new MenuItem("Save");
        MenuItem saveAsItem = new MenuItem("Save As");
        MenuItem openItem = new MenuItem("Open Image");

        //add menu item to menu
        topMenu.getItems().add(saveItem);
        topMenu.getItems().add(saveAsItem);
        topMenu.getItems().add(openItem);

        //create a menu bar
        MenuBar topBar = new MenuBar();

        //add menu to the menu bar
        topBar.getMenus().add(topMenu);

        // for opening the file
        FileChooser fileChooser = new FileChooser();
        openItem.setOnAction(new EventHandler<>() {
                                 @Override
                                 public void handle(ActionEvent actionEvent) {
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
                                             imageInserted.setFitWidth(320);
                                             imageInserted.setFitHeight(320);
                                             imageInserted.setPreserveRatio(true);
                                             gridPane.add(imageInserted, 4,4);
                                         } catch (IOException ex) {
                                             System.out.println("Error!");
                                         }
                                     }
                                 }
        });
        saveAsItem.setOnAction(new EventHandler<>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                FileChooser saveImgAs = new FileChooser();
                saveImgAs.setTitle("Save image as");
                saveImgAs.getExtensionFilters().addAll(
                        new ExtensionFilter("PNG file","*.png"),
                        new ExtensionFilter("PDF file","*.pdf"),
                        new ExtensionFilter("BMP file","*.bmp"));
                File savedImg = saveImgAs.showSaveDialog(null);
                nameOfFile[0] = savedImg.getName();
                String ext = nameOfFile[0].substring(1 + nameOfFile[0].lastIndexOf(".")).toLowerCase();
                try {           //attempt to make a save file from the inserted image
                    ImageIO.write(SwingFXUtils.fromFXImage(imageInserted.getImage(),null), ext, savedImg);
                } catch (IOException o) {   //If the above line breaks, throw an exception
                    throw new RuntimeException(o);
                }
            }

        });
        saveItem.setOnAction(new EventHandler<>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {           //attempt to make a save file from the inserted image
                    ImageIO.write(SwingFXUtils.fromFXImage(imageInserted.getImage(),null), "png", new File(nameOfFile[0]));
                } catch (IOException o) {   //If the above line breaks, throw an exception
                    throw new RuntimeException(o);
                }
            }

        });
        borderPane.setTop(topBar);
        borderPane.setCenter(gridPane);
        Scene scene = new Scene(borderPane, 1000,1000);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}



