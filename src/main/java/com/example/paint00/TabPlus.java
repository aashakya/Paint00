package com.example.paint00;

import javafx.application.Platform;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Tab class for the Tab feature of the application
 */
public class TabPlus extends Tab {
    ScrollPane scrollpane; // for a scrollable tab
    File path; // stores the tab's file path
    private TimerTask logger;
    public final static String LOGGER_TXT = "src/main/java/com/example/paint00/Mylogs.txt"; // Location of the log file
    public static CanvasPane canvasPane; // creating a canvasPane
    public boolean unSavedChange; // indicating if saved file

    /**
     * Constructor for the TabPlus class
     */
    TabPlus(){
        scrollpane = new ScrollPane(); // initializing the scrollPane
        canvasPane = new CanvasPane(); // initializing the canvasPane
        scrollpane.setContent(canvasPane); // setting the canvasPane to the scrollPane
        this.setContent(scrollpane);
        this.setOnCloseRequest(e-> DialogBox.unsavedAlert()); // alert when user closes the tab
        initLogger();
        this.path = null; // set the path to null
        // set the title of the tab to "Untitled"
        this.setText("Untitled");
        unSavedChange = true; // set to true
    }

    /**
     * Get the canvasPane used
     * @return the canvasPane used to draw
     */
    public static CanvasPane getCanvasPane(){
        return canvasPane;
    }

    /**
     * Calls the undo function implemented in canvasPane
     */
    public void undo() { canvasPane.undo(); }

    /**
     * Calls the redo function implemented in canvasPane
     */
    public void redo() { canvasPane.redo(); }

    /**
     * Rotate the canvas 90 degree clockwise
     */
    public void rotateCanvas(){canvasPane.setRotate(canvasPane.getRotate()+90);}

    /**
     * Flip the canvas horizontally
     */
    public void flipH(){
        canvasPane.setScaleX(-1*canvasPane.getScaleX());
    }

    /**
     * Flip the canvas vertically
     */
    public void flipV(){
        canvasPane.setScaleY(-1*canvasPane.getScaleY());
    }

    /**
     * initialization of the Logger with timer
     */
    private void initLogger(){
        Timer loggerTimer = new Timer(); // creating a timer for the logger
        this.logger = new TimerTask() { // creating a timer task
            @Override
            public void run() {
                Platform.runLater(() -> {
                    File loggerFile = new File(LOGGER_TXT);
                    try {
                        loggerFile.createNewFile(); // creating a new txt file
                    } catch (Exception ex) {
                        System.out.println(ex); // print exception
                    }
                    try {
                        FileWriter writer = new FileWriter(LOGGER_TXT, true); // writing the to txt file
                        BufferedWriter writer_buffer = new BufferedWriter(writer);
                        writer_buffer.write(LocalDate.now() + " "+ LocalTime.now()+ " "
                                + ToolBoxTop.getSelectedTool() + " tool is selected");
                        // format = date time -- tool is selected
                        writer_buffer.newLine();
                        writer_buffer.close();
                    } catch (Exception ex) {
                        System.out.println(ex);
                    }
                });
            }
        };
        loggerTimer.scheduleAtFixedRate(this.logger, 4000, 20000);
    }

    /**
     * zoom into the canvas
     */
    public void zoomIn() {
        // zoom in the canvas x and y axis
        canvasPane.setScaleX(canvasPane.getScaleX()+0.1);
        canvasPane.setScaleY(canvasPane.getScaleY()+0.1);
    }

    /**
     * zoom out of the canvas
     */
    public void zoomOut() {
        // zoom out the canvas x and y axis
        canvasPane.setScaleX(canvasPane.getScaleX()-0.1);
        canvasPane.setScaleY(canvasPane.getScaleY()-0.1);
    }
}
