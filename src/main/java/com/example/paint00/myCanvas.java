package com.example.paint00;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

public class myCanvas extends Canvas {
    GraphicsContext gc;
    myCanvas(){
        this.gc = this.getGraphicsContext2D();
    }

    private void createSquare(){

    }


}
