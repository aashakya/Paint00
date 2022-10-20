package com.example.paint00;

import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;

import java.io.File;

/**
 * Tab class for the Tab feature of the application
 */
public class TabPlus extends Tab {
    ScrollPane scrollpane; // for a scrollable tab
    File path; // stores the tab's file path
    String titleCanvas; // title of the tab
    public static CanvasPane canvasPane; // creating a canvasPane
    public boolean saveFile; // indicating if saved file
    TabPlus(){
        scrollpane = new ScrollPane(); // initializing the scrollPane
        canvasPane = new CanvasPane(); // initializing the canvasPane
        scrollpane.setContent(canvasPane); // setting the canvasPane to the scrollPane
        this.setContent(scrollpane);
        //updateTabTitle();
        this.setOnCloseRequest(e-> DialogBox.unsavedAlert()); // alert when user closes the tab
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
    /**
     * @return the canvasPane used to draw
     */
    public static CanvasPane getCanvasPane(){
        return canvasPane;
    }

    /**
     * Calls the undo function implemented in canvasPane
     */
    public void undo() { TabPlus.canvasPane.undo(); }

    /**
     * Calls the redo function implemented in canvasPane
     */
    public void redo() { TabPlus.canvasPane.redo(); }

}
