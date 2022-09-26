package com.example.paint00;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.image.WritableImage;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class TabPlus extends Tab {
    ScrollPane scrollpane;
    File path;
    String titleCanvas; // title of the tab
    CanvasPane canvasPane;
    public boolean saveFile; // indicating if saved file
    TabPlus(){
        scrollpane = new ScrollPane();
        canvasPane = new CanvasPane();
        scrollpane.setContent(canvasPane);
        this.setContent(scrollpane);
        updateTabTitle();
        this.setOnCloseRequest(e->{
            DialogBox.unsavedAlert();
        });
    }

    TabPlus(File pathName){
        scrollpane = new ScrollPane();
        canvasPane = new CanvasPane();
        scrollpane.setContent(canvasPane);
        this.path = pathName;
        updateTabTitle();
    }

    public void updateTabTitle(){
        if(this.path != null)
            this.titleCanvas = this.path.getName();//stores the current name of the title tab
        else
            this.titleCanvas = "Untitled";
        this.setText(this.titleCanvas);
    }

    public void saveImage() {
        WritableImage im = canvasPane.snapshot(new SnapshotParameters(), null);
        try{
            if(this.path != null){
                ImageIO.write(SwingFXUtils.fromFXImage(im, null),
                        "png", this.path);
                this.setUnsavedChanges(false);
                this.setTitle(this.getFilePath().getName());
            }
        }catch(IOException ex){
        }
        this.setUnsavedChanges(false);
        this.updateTabTitle();
    }

    private void setTitle(String name) {
        this.titleCanvas = name;
        updateTabTitle();
    }

    private File getFilePath() {
        return this.path;
    }

    private void setUnsavedChanges(boolean b) {
        this.saveFile = b;
    }

    public void saveImageAs() {
        this.setFilePath(path);
        this.saveImage();
    }

    private void setFilePath(File path) {
        this.path = path;
    }

}
