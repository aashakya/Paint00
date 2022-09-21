package com.example.paint00;

import javafx.geometry.Insets;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.canvas.Canvas;

public class ToolBoxTop {
    ColorPicker colorPicker = new ColorPicker();
    HBox toolBoxTop = new HBox();
    ToolBoxTop(Canvas canvas, GraphicsContext gc){
        colorPicker.setValue(Color.BLACK);

        toolBoxTop.setPadding(new Insets(15, 12, 15, 12));
        toolBoxTop.setSpacing(10);
        Slider brushSize = new Slider(1,100, 10);

        brushSize.setShowTickLabels(true);
        brushSize.setMajorTickUnit(10f);
        brushSize.setBlockIncrement(10f);
        ToggleButton eraser = new ToggleButton("Eraser");

        toolBoxTop.getChildren().add(0, colorPicker);
        toolBoxTop.getChildren().add(1,brushSize);
        toolBoxTop.getChildren().add(2,eraser);

        canvas.addEventHandler(MouseEvent.MOUSE_PRESSED, event -> {
            gc.setLineWidth(brushSize.getValue());
            if (eraser.isSelected()){
                gc.setStroke(Color.WHITE);
            }
            else{
                gc.setStroke(colorPicker.getValue());
            }
            gc.beginPath();
            gc.moveTo(event.getX(), event.getY());
            gc.stroke();
        });

        canvas.addEventHandler(MouseEvent.MOUSE_DRAGGED,
                event -> {
                    gc.lineTo(event.getX(), event.getY());
                    gc.stroke();
                });

        canvas.addEventHandler(MouseEvent.MOUSE_RELEASED, event -> {

        });
    }
}
