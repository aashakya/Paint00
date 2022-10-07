package com.example.paint00;

import javafx.application.Platform;

import java.util.Timer;
import java.util.TimerTask;

public class SaveTimer{
    static Timer timer = new Timer(); // defining the timer
    long timePeriod; // defining the time period
    TimerTask saveTask;

    SaveTimer(long timePeriodInput){
        timePeriod = timePeriodInput; // initial time as 10 seconds
        saveTask = new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
//                    if (timePeriod > 0){
//                        System.out.println(timePeriod);
//                        timePeriod--;
//                    }
//                    else {
                        saveActionSteps();
//                        timer.cancel();
//                        timePeriod = timePeriodInput;
//                        //timer.scheduleAtFixedRate(saveTask, 0, 1000); // Delay with certain time period
//                    }
                });
            }
        };
        timer.scheduleAtFixedRate(saveTask, 10000, 1000*timePeriod); // Delay with certain time period

    }
    private void saveActionSteps(){
        if (TabPlus.canvasPane.getImageSavedAs())
        {
            AppMenu.saveAction();
        }
        else {
            AppMenu.saveAsAction();
        }
    }
}
