package com.example.paint00;

import javafx.geometry.Insets;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.canvas.Canvas;

public class ToolBoxTop {
    ColorPicker colorPicker = new ColorPicker();
    Slider brushSize = new Slider(1,100, 10);

    ToggleButton pen = new ToggleButton("Pen");
    ToggleButton eraser = new ToggleButton("Eraser");
    ToggleButton drawLine = new ToggleButton("Draw Line");
    ToggleButton drawSquare = new ToggleButton("Draw Square");
    ToggleButton drawCircle = new ToggleButton("Draw Circle");
    ToggleButton drawRect = new ToggleButton("Draw Rectangle");
    ToggleButton drawEllipse = new ToggleButton("Draw ellipse");

    ToggleGroup toggleGroup = new ToggleGroup();
    TextField canvasWidth = new TextField();

    HBox toolBoxTop = new HBox();
    ToolBoxTop(Canvas canvas, GraphicsContext gc){
        pen.setSelected(true);
        toggleGroup.getToggles().addAll(pen,eraser,drawLine,drawSquare,drawCircle,drawRect,drawEllipse);
        colorPicker.setValue(Color.BLACK);

        toolBoxTop.setPadding(new Insets(15, 12, 15, 12));
        toolBoxTop.setSpacing(10);

        brushSize.setShowTickLabels(true);
        brushSize.setMajorTickUnit(10f);
        brushSize.setBlockIncrement(10f);

        canvasWidth.setPromptText("Enter width");
        toolBoxTop.getChildren().addAll(colorPicker,brushSize,canvasWidth,pen,eraser,drawLine,drawSquare,drawCircle,drawRect,drawEllipse);

        canvas.addEventHandler(MouseEvent.MOUSE_PRESSED, event -> {
            gc.setLineWidth(brushSize.getValue());
            if (eraser.isSelected()){
                gc.setStroke(Color.WHITE);
            }
            else if (pen.isSelected()){
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
