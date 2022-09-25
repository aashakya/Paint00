package com.example.paint00;

import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;

import java.io.File;

public class TabPlus extends Tab {
    ScrollPane scrollpane;
    File path;
    String titleCanvas;

    CanvasPane canvasPane;
    TabPlus(){
        scrollpane = new ScrollPane();
        canvasPane = new CanvasPane();
        scrollpane.setContent(canvasPane);
        this.setContent(scrollpane);
        updateTabTitle();
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
}
