package com.example.paint00;

import javafx.geometry.Insets;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class ToolBoxTop {
    private final String filePath = "src/main/java/com/example/paint00/Images/"; // location for icons
    private ImageView insertIcon(String loc) throws FileNotFoundException {// function to add icons
        FileInputStream input = new FileInputStream(loc); // setting the input to the image file location
        Image image = new Image(input,20,20,false,false); // fitting the image into the icon size
        return new ImageView(image);
    }

    // Creating the tools to use
    static ColorPicker colorPicker = new ColorPicker();
    static Slider brushSize = new Slider(1,100, 10); // creating a slider

    // Initializing tools with icons
    ToggleButton pen = new ToggleButton("Pen", insertIcon(filePath+"pen.png"));
    ToggleButton eraser = new ToggleButton("Eraser",insertIcon(filePath+"eraser.png"));
    ToggleButton drawLine = new ToggleButton("Line",insertIcon(filePath+"line.png"));
    ToggleButton drawDashed = new ToggleButton("Dashed Line",insertIcon(filePath+"dashLine.png"));
    ToggleButton drawSquare = new ToggleButton("Square",insertIcon(filePath+"square.png"));
    ToggleButton drawCircle = new ToggleButton("Circle",insertIcon(filePath+"circle.png"));
    ToggleButton drawRect = new ToggleButton("Rect",insertIcon(filePath+"rectangle.png"));
    ToggleButton drawEllipse = new ToggleButton("Ellipse",insertIcon(filePath+"ellipse.png"));
    ToggleButton grabColor = new ToggleButton("Color Grab",insertIcon(filePath+"colorGrabber.png"));
    ToggleButton drawPolygon = new ToggleButton("Draw Polygon");
    ToggleButton drawPent = new ToggleButton("Draw Pentagon");
    ToggleButton selectMove = new ToggleButton("Select/Move");
    ToggleButton copyMove = new ToggleButton("Copy/Paste");
    ToggleButton resize = new ToggleButton("Resize");
    ToggleButton undo = new ToggleButton("Undo");
    ToggleButton redo = new ToggleButton("Redo");
    ToggleGroup toggleGroup = new ToggleGroup();
    TextField canvasWidth = new TextField();
    TextField canvasHeight = new TextField();
    static TextField sides = new TextField();
    static int sideNo = 3;

    CanvasPane canvas1;
    FlowPane toolBoxTop = new FlowPane();

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

    public static int getSizeNo(){
        try{
        sideNo = Integer.parseInt(sides.getText());}
        catch (Exception e) {
            throw new RuntimeException(e);
        }
        return sideNo;
    }
    ToolBoxTop() throws FileNotFoundException {
        resize.setMinHeight(28); resize.setMinHeight(28); // To match the aesthetic of other buttons
        toggleGroup.getToggles().addAll(pen,eraser,drawLine,drawDashed,drawSquare,drawCircle,drawRect,drawEllipse,
                grabColor,drawPolygon, drawPent, selectMove, copyMove);
        colorPicker.setValue(Color.BLACK);

        toolBoxTop.setPadding(new Insets(15, 12, 15, 12));
        toolBoxTop.setHgap(10);

        brushSize.setShowTickLabels(true);
        brushSize.setMajorTickUnit(10f);
        brushSize.setBlockIncrement(10f);

        canvasWidth.setPrefWidth(70);
        canvasWidth.setPromptText("Width");
        canvasHeight.setPrefWidth(70);
        canvasHeight.setPromptText("Height");
        sides.setPrefWidth(90);
        sides.setPromptText("No. of sides");
        canvas1 = new CanvasPane();
        toolBoxTop.getChildren().addAll(colorPicker,brushSize,canvasWidth,canvasHeight,resize,pen,eraser,drawLine,
                drawDashed,drawSquare,drawCircle,drawRect,drawEllipse,grabColor,sides,drawPolygon,drawPent,
                undo,redo,selectMove,copyMove);

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
            else if (drawPolygon.isSelected()){selectedTool="drawPolygon";}
            else if (drawPent.isSelected()){selectedTool="drawPent";}
            else if (selectMove.isSelected()) {selectedTool="selectMove";}
            else if (copyMove.isSelected()) {selectedTool="copyMove";}
            else selectedTool="none";
        });

        // On clicking resize, change canvas size
        resize.setOnAction(e -> {
            resize.setSelected(false);
            try{
                if (canvasHeight != null && canvasWidth != null){
                    TabPlus.getCanvasPane().resize(Double.parseDouble(canvasWidth.getText()),Double.
                            parseDouble(canvasHeight.getText()));
                }
            }
            catch (Exception ece) {
                throw new RuntimeException(ece);
            }
        });

        // On clicking the undo button
        undo.setOnAction(e -> {
            Main.getActiveTab().undo(); // call the undo function
            undo.setSelected(false); // deselect undo
        });
        redo.setOnAction(e -> {
            Main.getActiveTab().redo(); // call the redo function
            redo.setSelected(false); // deselect redo
        });
    }
}
