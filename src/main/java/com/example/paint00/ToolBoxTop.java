package com.example.paint00;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
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

    //initializing tool tips
    Tooltip resizeTips = new Tooltip("Resize"); // resize
    Tooltip penTips = new Tooltip("Draw Pen"); // pen tool
    Tooltip eraserTips = new Tooltip("Erase"); // eraser tool
    Tooltip lineTips = new Tooltip("Draw Line"); // draw line
    Tooltip dashedLineTips = new Tooltip("Draw Dashed Line"); // draw dashed line
    Tooltip squareTips = new Tooltip("Draw Square"); // draw square
    Tooltip circleTips = new Tooltip("Draw Circle"); // draw circle
    Tooltip rectTips = new Tooltip("Draw Rectangle"); // draw rectangle
    Tooltip ellipseTips = new Tooltip("Draw Ellipse"); // draw ellipse
    Tooltip colorGrabTips = new Tooltip("Grab Color"); // grab color
    Tooltip polygonTips = new Tooltip("Draw Polygon"); // draw n-sided polygon
    Tooltip pentagonTips = new Tooltip("Draw Pentagon"); // draw pentagon
    Tooltip undoTips = new Tooltip("Undo Action"); // undo
    Tooltip redoTips = new Tooltip("Redo Action"); //redo
    Tooltip selectTimetips = new Tooltip("Select auto save timer"); // auto save timer

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
    ToggleGroup toggleGroup = new ToggleGroup(); // toggle group to un-toggle all buttons except selected
    TextField canvasWidth = new TextField(); // field for canvas width
    TextField canvasHeight = new TextField(); // field for canvas width
    static TextField sides = new TextField(); // field for side input
    ComboBox selectTime; // time options as a dropdown list
    ObservableList<String> timeOptions;
    static int sideNo = 3; // initializing sides of polygon to 3 as default sides
    FlowPane toolBoxTop = new FlowPane(); // flow-pane to move tools to next line if overflow

    static String selectedTool = null; // to keep account of selected tools

    /**
     * @return the color picker's color value
     */
    public static Color getColorPicker() {
        return colorPicker.getValue();
    } // getter for colorPicker

    /**
     * @param color the color value selected from the color picker tool
     */
    // setter for colorPicker
    public static void setColorPicker(Color color) {ToolBoxTop.colorPicker.setValue(color);}

    /**
     * @return the selected value of the brush size
     */
    public static double getBrushSize() {
        return brushSize.getValue();
    } // getter for brushSize

    /**
     * @return the name of the tool selected and if no tool is selected returns "None"
     */
    public static String getSelectedTool() {
        if(selectedTool != null) return selectedTool; // if tool selected return selected tool
        else return "None";
    }

    /**
     * @return the number of sides entered by the user
     */
    public static int getSizeNo(){
        try{
            sideNo = Integer.parseInt(sides.getText());
        }
        catch (Exception ignored) {
        }
        return sideNo;
    }

    /**
     * Initialization of the ToolBoxTop. ToolBoxTop contains tools to make changes to the canvas
     * @throws FileNotFoundException Throws exception if icon images are not found
     */
    ToolBoxTop() throws FileNotFoundException {
        resize.setTooltip(resizeTips);
        pen.setTooltip(penTips);
        eraser.setTooltip(eraserTips);
        drawLine.setTooltip(lineTips);
        drawDashed.setTooltip(dashedLineTips);
        drawSquare.setTooltip(squareTips);
        drawCircle.setTooltip(circleTips);
        drawRect.setTooltip(rectTips);
        drawEllipse.setTooltip(ellipseTips);
        grabColor.setTooltip(colorGrabTips);
        drawPolygon.setTooltip(polygonTips);
        drawPent.setTooltip(pentagonTips);
        undo.setTooltip(undoTips);
        redo.setTooltip(redoTips);
        //selectTime.setTooltip(selectTimetips);

        timeOptions = FXCollections.observableArrayList(
                "10 sec", // adding the list of time options for auto-save
                "30 sec",
                "45 sec",
                "60 sec"
        );
        selectTime = new ComboBox(timeOptions); // adding the time options to drop down list

        resize.setMinHeight(28); resize.setMinHeight(28); // To match the aesthetic of other buttons
        // adding buttons to toggle group to un-toggle when another button is clicked
        toggleGroup.getToggles().addAll(pen,eraser,drawLine,drawDashed,drawSquare,drawCircle,drawRect,drawEllipse,
                grabColor,drawPolygon, drawPent, selectMove, copyMove);
        colorPicker.setValue(Color.BLACK); // Initial colorPicker value to Black

        // adding padding and Horizontal gap for aesthetics
        toolBoxTop.setPadding(new Insets(15, 12, 15, 12));
        toolBoxTop.setHgap(10);

        // setting brushSize slider with tick marks
        brushSize.setShowTickLabels(true);
        brushSize.setMajorTickUnit(10f);
        brushSize.setBlockIncrement(10f);
        brushSize.setValue(25); //initializing the brush Size

        // initializing text label for canvas width and height
        canvasWidth.setPrefWidth(70); // setting width of the text label
        canvasWidth.setPromptText("Width");
        canvasHeight.setPrefWidth(70); // setting width of the text label
        canvasHeight.setPromptText("Height");

        // initializing sides text label
        sides.setPrefWidth(90); // setting width of the text label
        sides.setPromptText("No. of sides");

        // adding all the tools to the tool box menu
        toolBoxTop.getChildren().addAll(colorPicker,brushSize,canvasWidth,canvasHeight,resize,pen,eraser,drawLine,
                drawDashed,drawSquare,drawCircle,drawRect,drawEllipse,grabColor,sides,drawPolygon,drawPent,
                undo,redo,selectMove,copyMove,selectTime);

        // returning the tool selected in case of change of tool
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
                if (canvasHeight != null && canvasWidth != null){ // making sure both values are entered
                    TabPlus.getCanvasPane().resize(Double.parseDouble(canvasWidth.getText()),Double.
                            parseDouble(canvasHeight.getText())); // getting the text value from label
                }
            }
            catch (Exception ignored) {}
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
