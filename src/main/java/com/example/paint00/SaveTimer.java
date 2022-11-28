package com.example.paint00;

import javafx.application.Platform;

import java.util.Timer;
import java.util.TimerTask;

/**
 * The Timer class for the auto save feature
 */
public class SaveTimer{
    static Timer timer = new Timer(); // defining the timer
    long timePeriod; // defining the time period
    TimerTask saveTask; // TimerTask for saving the canvas

    SaveTimer(long timePeriodInput){
        timePeriod = timePeriodInput; // initial time as 10 seconds
        saveTask = new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                        saveActionSteps();
                });
            }
        };
        timer.scheduleAtFixedRate(saveTask, 1000*timePeriod, 1000*timePeriod); // Delay with certain time period

    }

    /**
     * Either calls the Save function or SaveAction function depending on
     * if the SaveAs is already applied or not
     * SaveAs is called if the image is not Saved As already
     */
    private void saveActionSteps(){
        if (TabPlus.canvasPane.getImageSavedAs())
        {
            AppMenu.saveAction(); // call save if saveAs is already applied to the canvas
        }
        else {
            AppMenu.saveAsAction(); // call saveAs if saveAs is not yet applied to the canvas
        }
    }
}
