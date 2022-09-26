package com.example.paint00;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

public class ToolBoxTop {
    static ColorPicker colorPicker = new ColorPicker();
    static Slider brushSize = new Slider(1,100, 10);

    static ToggleButton pen = new ToggleButton("Pen");
    static ToggleButton eraser = new ToggleButton("Eraser");
    ToggleButton drawLine = new ToggleButton("Draw Line");
    ToggleButton drawDashed = new ToggleButton("Draw Dashed Line");
    ToggleButton drawSquare = new ToggleButton("Draw Square");
    ToggleButton drawCircle = new ToggleButton("Draw Circle");
    ToggleButton drawRect = new ToggleButton("Draw Rectangle");
    ToggleButton drawEllipse = new ToggleButton("Draw ellipse");
    ToggleButton grabColor = new ToggleButton("Grab Color");
    ToggleGroup toggleGroup = new ToggleGroup();
    TextField canvasWidth = new TextField();

    HBox toolBoxTop = new HBox();

    static String selectedTool;

    public static Color getColorPicker() {
        return colorPicker.getValue();
    }

    public static void setColorPicker(Color color) {
        ToolBoxTop.colorPicker.setValue(color);
    }

    public static double getBrushSize() {
        return brushSize.getValue();
    }
    public static String getSelectedTool() {
        if(selectedTool != null){
            return selectedTool;}
        else return "None";
    }

    ToolBoxTop(){
        toggleGroup.getToggles().addAll(pen,eraser,drawLine,drawDashed,drawSquare,drawCircle,drawRect,drawEllipse,grabColor);
        colorPicker.setValue(Color.BLACK);

        toolBoxTop.setPadding(new Insets(15, 12, 15, 12));
        toolBoxTop.setSpacing(10);

        brushSize.setShowTickLabels(true);
        brushSize.setMajorTickUnit(10f);
        brushSize.setBlockIncrement(10f);

        canvasWidth.setPromptText("Enter width");
        toolBoxTop.getChildren().addAll(colorPicker,brushSize,canvasWidth,pen,eraser,drawLine,drawDashed,drawSquare,drawCircle,drawRect,drawEllipse,grabColor);

        toggleGroup.selectedToggleProperty().addListener((observable) -> {
            if (pen.isSelected()){selectedTool = "pen";}
            else if (eraser.isSelected()) {selectedTool = "eraser";}
            else if (drawLine.isSelected()){selectedTool="line";}
            else if (drawDashed.isSelected()){selectedTool="dashedLine";}
            else if (drawRect.isSelected()) {selectedTool="rect";}
            else if (drawSquare.isSelected()) {selectedTool="square";}
            else if (drawCircle.isSelected()) {selectedTool="circle";}
            else if (drawEllipse.isSelected()) {selectedTool="ellipse";}
            else if (grabColor.isSelected()){selectedTool="grabColor";}
            else selectedTool="none";
        });

        canvasWidth.textProperty().addListener((observable, oldValue, newValue) -> {
//            CanvasPane.setWidth(Double.parseDouble(canvasWidth.getText()));
        });
    }
}
