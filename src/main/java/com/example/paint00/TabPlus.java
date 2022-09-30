package com.example.paint00;

import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;

import java.io.File;

public class TabPlus extends Tab {
    ScrollPane scrollpane;
    File path;
    String titleCanvas; // title of the tab
    public static CanvasPane canvasPane;
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
        this.setContent(scrollpane);
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

    private void setFilePath(File path) {
        this.path = path;
    }
    public static CanvasPane getCanvasPane(){
        return canvasPane;
    }

    public void undo() {
        TabPlus.canvasPane.undo();
    }

    public void redo() {
        TabPlus.canvasPane.redo();
    }

    public void clearCanvas() {// to clear the canvas
        canvasPane.updateStack(); // updating stack for undo/redo purpose
        // clearing canvas
        TabPlus.canvasPane.gc.clearRect(0,0,canvasPane.getWidth(), canvasPane.getHeight());
        canvasPane.initDraw(canvasPane.gc, canvasPane.getWidth(),canvasPane.getHeight()); // bg-color to white
    }
}
