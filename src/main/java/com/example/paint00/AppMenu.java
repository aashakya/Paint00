package com.example.paint00;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.control.*;
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
import java.net.URI;

/**
 * The class for the menu of the application
 */
public class AppMenu{
    //create menu bar
    MenuBar menuBar = new MenuBar();
    static String nameOfFile = "Untitled"; // name of the file
    static String ext; // extension of the file
    static File saveFile;
    SaveTimer timer; // timer for the auto save feature
    AppMenu(){
        //create menu
        Menu fileMenu = new Menu("File");
        Menu optionsMenu = new Menu("Options");
        Menu helpMenu = new Menu("Help");
        
        //crete menu items
        // For File
        MenuItem saveItem = new MenuItem("Save");
        MenuItem saveAsItem = new MenuItem("Save As");
        MenuItem openItem = new MenuItem("Open Image");
        MenuItem closeApp = new MenuItem("Close");

        // For Options
        MenuItem undoOpt = new MenuItem("Undo");
        MenuItem redoOpt = new MenuItem("Redo");
        MenuItem clearCanvas = new MenuItem("Clear Canvas");

        // For Help
        MenuItem helpItem = new MenuItem("Help");
        MenuItem aboutItem= new MenuItem("About");
        //add menu item to menu
        //for file menu items
        fileMenu.getItems().addAll(saveItem,saveAsItem,openItem,closeApp);
        //for option menu items
        optionsMenu.getItems().addAll(undoOpt,redoOpt,clearCanvas);
        //for help menu items
        helpMenu.getItems().addAll(helpItem,aboutItem);

        //add menu to the menu bar
        menuBar.getMenus().addAll(fileMenu,optionsMenu,helpMenu);

        timer = new SaveTimer(60);
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
                Main.getActiveTab().canvasPane.setWidth(img.getWidth());
                Main.getActiveTab().canvasPane.setHeight(img.getHeight());
                Main.getActiveTab().canvasPane.gc.drawImage(img, 0,0);
                Main.getActiveTab().canvasPane.updateStack();
            }
        });
        // call functions according to the menu item selected
        saveAsItem.setOnAction(e -> saveAsAction());
        saveItem.setOnAction(e -> saveAction());
        closeApp.setOnAction(e -> DialogBox.unsavedAlert());

        undoOpt.setOnAction(e -> Main.getActiveTab().undo());
        redoOpt.setOnAction(e -> Main.getActiveTab().redo());
        clearCanvas.setOnAction(e -> DialogBox.clearCAlert());
        // when about is clicked, open the hyperlink in the browser
        aboutItem.setOnAction(e -> {
            try {
                java.awt.Desktop.getDesktop().browse(URI.create("https://github.com/aashakya/Paint00"));
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        // keyboard shortcuts for the menu options
        openItem.setAccelerator(new KeyCodeCombination(KeyCode.O, KeyCombination.CONTROL_DOWN));
        saveItem.setAccelerator(new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN));
        closeApp.setAccelerator(new KeyCodeCombination(KeyCode.Q, KeyCombination.CONTROL_DOWN));
        saveAsItem.setAccelerator(new KeyCodeCombination(KeyCode.S, KeyCombination.SHIFT_DOWN, KeyCombination.CONTROL_DOWN));
        undoOpt.setAccelerator(new KeyCodeCombination(KeyCode.Z, KeyCombination.CONTROL_DOWN));
        redoOpt.setAccelerator(new KeyCodeCombination(KeyCode.Z, KeyCombination.SHIFT_DOWN, KeyCombination.CONTROL_DOWN));
    }

    /**
     * Save the files to the file user selected the name as
     */
    public static void saveAction(){
        try {           //attempt to make a save file from the inserted image
            WritableImage writableImage = new WritableImage((int) TabPlus.canvasPane.getWidth(), (int) TabPlus.canvasPane.getHeight());
            TabPlus.canvasPane.snapshot(null,writableImage);
            RenderedImage ri = SwingFXUtils.fromFXImage(writableImage,null);
            ImageIO.write(ri, "png", saveFile);
            //this.setTitle(this.getFilePath().getName());
        } catch (IOException o) {   //If the above line breaks, throw an exception
        }
    }

    /**
     * Ask user for the name, location, and extension of the file to be saved
     */
    public static void saveAsAction(){
        FileChooser saveImgAs = new FileChooser();
        saveImgAs.setTitle("Save image as");
        saveImgAs.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("PNG file","*.png"),
                new FileChooser.ExtensionFilter("JPG file","*.jpg"),
                new FileChooser.ExtensionFilter("BMP file","*.bmp"));
        File savedImg = saveImgAs.showSaveDialog(null);
        if (savedImg!= null) {
            nameOfFile = savedImg.getName();
            Main.getActiveTab().setText(nameOfFile); // set current TabPlus name to nameOfFile string
            ext = nameOfFile.substring(1 + nameOfFile.lastIndexOf(".")).toLowerCase();
            if (!ext.equals("png")){
                if (!DialogBox.qualityAlert()) return;
            }
            WritableImage writableImage = new WritableImage((int) TabPlus.canvasPane.getWidth(),
                    (int) TabPlus.canvasPane.getHeight());
            TabPlus.canvasPane.snapshot(null,writableImage);
            try {
                ImageIO.write(SwingFXUtils.fromFXImage(writableImage,null),"png", savedImg);
                TabPlus.canvasPane.setImageSavedAs(); // setting the image save as indicator to true
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        saveFile = savedImg;
    }
}
