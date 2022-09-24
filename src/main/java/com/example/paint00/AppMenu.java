package com.example.paint00;

import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.stage.FileChooser;
import javax.imageio.ImageIO;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;

public class AppMenu{
    //create menu bar
    MenuBar menuBar = new MenuBar();
    String nameOfFile = "Untitled"; // to save file
    String[] ext = new String[1];
    File saveFile;

    AppMenu(CanvasPane canvas){
        //create menu
        GraphicsContext gc = canvas.gc;
        Menu fileMenu = new Menu("File");
        Menu helpMenu = new Menu("Help");
        
        //crete menu items
        // For File
        MenuItem saveItem = new MenuItem("Save");
        MenuItem saveAsItem = new MenuItem("Save As");
        MenuItem openItem = new MenuItem("Open Image");
        MenuItem closeApp = new MenuItem("Close");
        
        // For Help
        MenuItem helpItem = new MenuItem("Help");
        MenuItem aboutItem= new MenuItem("About");
        //add menu item to menu
        //for file menu items
        fileMenu.getItems().addAll(saveItem,saveAsItem,openItem,closeApp);
        //for help menu items
        helpMenu.getItems().addAll(helpItem,aboutItem);

        //add menu to the menu bar
        menuBar.getMenus().addAll(fileMenu,helpMenu);
        // for opening the file
        FileChooser fileChooser = new FileChooser();
        openItem.setOnAction(e -> {
            fileChooser.setTitle("Open File");
            fileChooser.getExtensionFilters().addAll(              //Including filters for extension types
                    new FileChooser.ExtensionFilter("Image Files", "*.png"),
                    new FileChooser.ExtensionFilter("JPEG File", "*.jpeg"),
                    new FileChooser.ExtensionFilter("BMP file","*.bmp"),
                    new FileChooser.ExtensionFilter("PDF File", "*.pdf")
            );
            File insImg = fileChooser.showOpenDialog(Main.getStage());
            if (insImg != null) {
                Image img = new Image(insImg.toURI().toString());
                saveFile = insImg;
                canvas.setWidth(img.getWidth());
                canvas.setHeight(img.getHeight());
                gc.drawImage(img, 0,0);
            }
        });
        saveAsItem.setOnAction(e -> {
            FileChooser saveImgAs = new FileChooser();
            saveImgAs.setTitle("Save image as");
            saveImgAs.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("PNG file","*.png"),
                    new FileChooser.ExtensionFilter("PDF file","*.pdf"),
                    new FileChooser.ExtensionFilter("BMP file","*.bmp"));
            File savedImg = saveImgAs.showSaveDialog(null);
            nameOfFile = savedImg.getName();
            ext[0] = nameOfFile.substring(1 + nameOfFile.lastIndexOf(".")).toLowerCase();
            try {           //attempt to make a save file from the inserted image
                WritableImage writableImage = new WritableImage((int) canvas.getWidth(), (int) canvas.getHeight());
                canvas.snapshot(null,writableImage);
                ImageIO.write(SwingFXUtils.fromFXImage(writableImage,null), ext[0], savedImg);
            } catch (IOException o) {   //If the above line breaks, throw an exception
                throw new RuntimeException(o);
            }
            saveFile = savedImg;
        });
        saveItem.setOnAction(e -> {
            try {           //attempt to make a save file from the inserted image
                WritableImage writableImage = new WritableImage((int) canvas.getWidth(), (int) canvas.getHeight());
                canvas.snapshot(null,writableImage);
                RenderedImage ri = SwingFXUtils.fromFXImage(writableImage,null);
                ImageIO.write(ri, "png", saveFile);
                System.out.println(new File(nameOfFile));

            } catch (IOException o) {   //If the above line breaks, throw an exception

            }
        });
        closeApp.setOnAction(e-> Platform.exit());

        openItem.setAccelerator(new KeyCodeCombination(KeyCode.O, KeyCombination.CONTROL_DOWN));
        saveItem.setAccelerator(new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN));
        closeApp.setAccelerator(new KeyCodeCombination(KeyCode.Q, KeyCombination.CONTROL_DOWN));
        saveAsItem.setAccelerator(new KeyCodeCombination(KeyCode.S, KeyCombination.SHIFT_DOWN, KeyCombination.CONTROL_DOWN));
    }


}
