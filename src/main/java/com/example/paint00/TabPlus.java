package com.example.paint00;

import javafx.application.Platform;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.layout.Pane;

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
    String titleCanvas; // title of the tab
    Pane cp;
    private Timer loggerTimer;
    private TimerTask logger;
    public final static String LOGGER_TXT = "src/main/java/com/example/paint00/Mylogs.txt";
    public static CanvasPane canvasPane; // creating a canvasPane
    public boolean saveFile; // indicating if saved file

    TabPlus(){
        scrollpane = new ScrollPane(); // initializing the scrollPane
//        cp = new Pane();
        canvasPane = new CanvasPane(); // initializing the canvasPane
//
        scrollpane.setContent(canvasPane); // setting the canvasPane to the scrollPane
        this.setContent(scrollpane);
        //updateTabTitle();
        this.setOnCloseRequest(e-> DialogBox.unsavedAlert()); // alert when user closes the tab
        initLogger();
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

    /**
     * Rotate the canvas 90 degree clockwise
     */
    public void rotateCanvas(){TabPlus.canvasPane.setRotate(canvasPane.getRotate()+90);}

    public void flipH(){
        canvasPane.setScaleX(-1*canvasPane.getScaleX()); //
    }
    public void flipV(){
        canvasPane.setScaleY(-1*canvasPane.getScaleY());
    }

    /**
     * initialization of the Logger with timer
     */
    private void initLogger(){
        this.loggerTimer = new Timer(); // creating a timer for the logger
        this.logger = new TimerTask() { // creating a timer task
            @Override
            public void run() {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
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
                    }
                });
            }
        };
        this.loggerTimer.scheduleAtFixedRate(this.logger, 4000, 20000);
    }
}
