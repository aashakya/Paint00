package com.example.paint00;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class CanvasPane extends myCanvas{
    double width = 900;
    double height = 700;
    CanvasPane(){
        this.setWidth(width);
        this.setHeight(height);
        initDraw(gc, width, height);
    }
    private void initDraw(GraphicsContext gc, double canvasWidth, double canvasHeight){
        gc.setFill(Color.WHITE);
        gc.fillRect(0, 0, canvasWidth, canvasHeight);
    }
}
