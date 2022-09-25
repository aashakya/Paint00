package com.example.paint00;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class CanvasPane extends myCanvas{
    double width = 900;
    double height = 700;
    CanvasPane(){
        this.setWidth(width);
        this.setHeight(height);
        initDraw(gc, width, height);
        this.addEventHandler(MouseEvent.MOUSE_PRESSED, event -> {
            switch (ToolBoxTop.getSelectedTool()) {
                case ("eraser") -> {
                    gc.setStroke(Color.WHITE);
                    gc.setLineWidth(ToolBoxTop.getBrushSize());
                    gc.beginPath();
                    gc.moveTo(event.getX(), event.getY());
                    gc.stroke();
                }
                case ("pen") -> {
                    gc.setStroke(ToolBoxTop.getColorPicker());
                    gc.setLineWidth(ToolBoxTop.getBrushSize());
                    gc.beginPath();
                    gc.moveTo(event.getX(), event.getY());
                    gc.stroke();
                }
                default -> {
                }
            }
        });

        this.addEventHandler(MouseEvent.MOUSE_DRAGGED,
                event -> {
                    switch (ToolBoxTop.getSelectedTool()) {
                        case ("eraser"), ("pen") -> {
                            gc.lineTo(event.getX(), event.getY());
                            gc.stroke();
                        }
                        default -> {
                        }
                    }
                });

        this.addEventHandler(MouseEvent.MOUSE_RELEASED, event -> {

        });
    }
    private void initDraw(GraphicsContext gc, double canvasWidth, double canvasHeight){
        gc.setFill(Color.WHITE);
        gc.fillRect(0, 0, canvasWidth, canvasHeight);
    }
}
