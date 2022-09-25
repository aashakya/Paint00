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
    ToggleButton drawSquare = new ToggleButton("Draw Square");
    ToggleButton drawCircle = new ToggleButton("Draw Circle");
    ToggleButton drawRect = new ToggleButton("Draw Rectangle");
    ToggleButton drawEllipse = new ToggleButton("Draw ellipse");
    ToggleGroup toggleGroup = new ToggleGroup();
    TextField canvasWidth = new TextField();

    HBox toolBoxTop = new HBox();

    static String selectedTool;

    public static Color getColorPicker() {
        return colorPicker.getValue();
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
        toggleGroup.getToggles().addAll(pen,eraser,drawLine,drawSquare,drawCircle,drawRect,drawEllipse);
        colorPicker.setValue(Color.BLACK);

        toolBoxTop.setPadding(new Insets(15, 12, 15, 12));
        toolBoxTop.setSpacing(10);

        brushSize.setShowTickLabels(true);
        brushSize.setMajorTickUnit(10f);
        brushSize.setBlockIncrement(10f);

        canvasWidth.setPromptText("Enter width");
        toolBoxTop.getChildren().addAll(colorPicker,brushSize,canvasWidth,pen,eraser,drawLine,drawSquare,drawCircle,drawRect,drawEllipse);

        if (pen.isSelected()){selectedTool = "pen";}
        else if (eraser.isSelected()) {selectedTool = "eraser";}
        else selectedTool="none";
    }
}
