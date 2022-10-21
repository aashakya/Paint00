package com.example.paint00;

import javafx.geometry.Point2D;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.shape.StrokeLineJoin;

import java.util.Stack;

/**
 * CanvasPane is the canvas that we draw upon
 */
public class CanvasPane extends myCanvas{
    //default width and height of image
    static double width = 900;
    static double height = 700;
    private Image imagePiece = null; // initializing the imagePiece to null, tool for select/move and copy/paste
    double x,y,x1,y1; // Stores the x and y coordinate of the position where the mouse is clicked
    private final Stack<Image> undoSteps; // Image stack for undo
    private final Stack<Image> redoSteps; // Image stack for redo
    private Point2D initialPoints; // for initial coordinates of portion of selected portion
    private String selectedTool; // to know which tool is currently selected
    private ImageView iv; // image view for screen rotate
    private static boolean imageSavedAs = false; // to keep track of if saved as is applied to canvas
    CanvasPane(){
        super();
        // initializing the stacks for undo and redo
        undoSteps = new Stack<>();
        redoSteps = new Stack<>();
        resize(width,height); // resizing the canvas to the default width and height
        initDraw(gc, width, height); // calling the initDraw function
        this.undoSteps.push(this.getRegion(0,0,this.getWidth(),this.getHeight()));// pushing to the undo stack

        // canvas actions for mouse movement
        this.addEventHandler(MouseEvent.MOUSE_PRESSED, event -> { // action for when mouse is pressed
            // get the initial x and y coordinate
            x = event.getX();
            y = event.getY();
            gc.setFill(ToolBoxTop.getColorPicker()); // change the setFill value to the colorPicker's value
            selectedTool = ToolBoxTop.getSelectedTool(); // get the currently selected tool
            switch (selectedTool) {
                case ("eraser")->{ // case for when eraser tool is selected
                    gc.setStroke(Color.WHITE); // drawing a white line as the "eraser"
                    // making the eraser stroke smooth
                    gc.setLineCap(StrokeLineCap.ROUND);
                    gc.setLineJoin(StrokeLineJoin.ROUND);
                    gc.setLineWidth(ToolBoxTop.getBrushSize()); // matching the eraser size to the brushSize
                    gc.beginPath();
                    gc.stroke();
                }
                case ("pen")->{ // case for when pen tool is selected
                    gc.setStroke(ToolBoxTop.getColorPicker()); // setting the stroke to colorPicker's value
                    // making the pen stroke smooth
                    gc.setLineCap(StrokeLineCap.ROUND);
                    gc.setLineJoin(StrokeLineJoin.ROUND);
                    gc.setLineWidth(ToolBoxTop.getBrushSize()); // matching the pen size to the brushSize
                    gc.beginPath();
                    gc.stroke();
                }
                // calling function according to the shape selected
                case ("rect")-> this.drawRect(x,y,x,y);
                case ("square")-> this.drawSquare(x,y,x,y);
                case ("ellipse")-> this.drawEllipse(x,y,x,y);
                case ("circle")-> this.drawCircle(x,y,x,y);
                case ("line")->{ // For drawing straight line
                    gc.setStroke(ToolBoxTop.getColorPicker()); // set Stroke to colorPicker's color
                    gc.setLineWidth(ToolBoxTop.getBrushSize()); // set line width to brush size
                }
                case ("dashedLine")->{ // For drawing dashed line
                    gc.setStroke(ToolBoxTop.getColorPicker()); // get color picker value
                    gc.setLineWidth(ToolBoxTop.getBrushSize()); // get line width
                    // for creating dashed line effects
                    gc.setLineDashes(ToolBoxTop.getBrushSize()*2);
                    gc.setLineDashOffset(ToolBoxTop.getBrushSize()*2);
                }
                case ("grabColor")-> ToolBoxTop.setColorPicker(this.getColor(x,y)); // for color grabber
                // for drawing shapes
                case ("drawPolygon")-> this.drawPolygon(x,y,x,y, ToolBoxTop.getSizeNo());
                case ("drawPent")-> this.drawPolygon(x,y,x,y,5);
                //getting the initialPoints for select/Move and copy/Paste
                case ("selectMove"),("copyMove") -> initialPoints = new Point2D(x,y);
                // starting x,y of the canvas select tool
                case("selectRotate") -> {
                    imagePiece = null;
                    initialPoints = new Point2D(x,y);
                    x1 = x;
                    y1 = y;
                }
                default -> {
                }
            }
            System.out.println(ToolBoxTop.getSelectedTool()); // print the selected tool
        });

        this.addEventHandler(MouseEvent.MOUSE_DRAGGED,
                event -> {
                    switch (selectedTool) {
                        case ("eraser"), ("pen") -> {
                            // draw pen/eraser as it is dragged
                            gc.lineTo(event.getX(), event.getY());
                            gc.stroke();
                        }
                        // grab color as it is dragged
                        case ("grabColor")-> ToolBoxTop.setColorPicker(this.getColor(event.getX(),event.getY()));
                        default -> {}
                    }
                });

        this.addEventHandler(MouseEvent.MOUSE_RELEASED, event -> {
            switch (selectedTool) {
                case ("eraser"), ("pen") -> this.updateStack(); // update the stacks
                case ("rect")->{
                    // adding the final points of the rectangle and updating the stacks
                    this.drawRect(x,y,event.getX(),event.getY());
                    this.updateStack();
                }
                case ("square")->{
                    // adding the final points of the square and updating the stacks
                    this.drawSquare(x,y,event.getX(),event.getY());
                    this.updateStack();
                }
                case ("ellipse")->{
                    // drawing an ellipse and updating the stack
                    this.drawEllipse(x,y,event.getX(),event.getY());
                    this.updateStack();
                }
                case ("circle")->{
                    // drawing a circle and updating the stack
                    this.drawCircle(x,y,event.getX(),event.getY());
                    this.updateStack();
                }
                case ("line")->{gc.strokeLine(x,y,event.getX(),event.getY());
                    this.updateStack();
                }
                case ("dashedLine")->{
                    gc.strokeLine(x,y,event.getX(),event.getY());
                    gc.setLineDashes(0);
                    gc.setLineDashOffset(0);
                    this.updateStack();
                }
                case ("grabColor")->{
                    ToolBoxTop.setColorPicker(this.getColor(event.getX(),event.getY()));
                    this.updateStack();
                }
                case ("drawPolygon")->{
                    this.drawPolygon(x,y,event.getX(),event.getY(),ToolBoxTop.getSizeNo());
                    this.updateStack();
                }
                case ("drawPent")->{
                    this.drawPolygon(x,y,event.getX(),event.getY(),5);
                    this.updateStack();
                }
                case ("selectMove"),("copyMove"),("selectRotate")->{ // for selecting portion of canvas
                    if (imagePiece == null) { // if portion is not selected yet
                        this.updateStack(); // adding canvas snapshot to stack before cutting the portion
                        // getting certain portion of the screen
                        imagePiece = this.getRegion(initialPoints.getX(), initialPoints.getY(), event.getX(), event.getY());
                        // setting the cut part of the image to white
                        if (selectedTool.equals("selectMove"))
                        {
                            gc.setFill(Color.TRANSPARENT);
                            gc.fillRect(Math.min(initialPoints.getX(),event.getX()),// drawing a white rectangle
                                    Math.min(initialPoints.getY(),event.getY()),
                                    Math.abs(event.getX() - initialPoints.getX()),
                                    Math.abs(event.getY() - initialPoints.getY()));
                        }
                        return;
                    }
                    if (selectedTool.equals("selectRotate")){
                        return;}
                    // if snapshot is already taken of certain portion of canvas
                    this.gc.drawImage(
                            imagePiece,
                            event.getX(),
                            event.getY()
                    );
                    // pushing the new canvas image to stack
                    //set the image back to null
                    imagePiece = null;
                }
                default -> {}
            }
            Main.getActiveTab().updateTabTitle();
            //
            this.getScene().addEventFilter(KeyEvent.KEY_PRESSED, ke -> {
                if (ke.getCode() == KeyCode.J) {
                    System.out.println("Key Pressed: " + ke.getCode());
                    rotatePiece(90);
                    ke.consume(); // stops passing the event to next node
                }
                if (ke.getCode() == KeyCode.K) {
                    System.out.println("Key Pressed: " + ke.getCode());
                    rotatePiece(180);
                    ke.consume(); // stops passing the event to next node
                }
                if (ke.getCode() == KeyCode.L) {
                    System.out.println("Key Pressed: " + ke.getCode());
                    rotatePiece(270);
                    ke.consume(); // stops passing the event to next node
                }
                if (ke.getCode() == KeyCode.R) {
                    System.out.println("Key Pressed: " + ke.getCode());
                    rotatePiece(360);
                    ke.consume(); // stops passing the event to next node
                }
            });
            //
        });
    }

    public void rotatePiece(int i){
        if (imagePiece != null){
            iv = new ImageView(imagePiece);
            iv.setRotate(i);
            SnapshotParameters param = new SnapshotParameters();
            param.setFill(Color.TRANSPARENT);
            Image rotatedImg = iv.snapshot(param,null);
            this.gc.drawImage(rotatedImg,x1,y1);
        }
    }

    /**
     * Update the canvas stacks
     */
    public void updateStack(){
        undoSteps.push(this.getRegion(0,0,this.getWidth(),this.getHeight()));
        redoSteps.clear();
    }

    /**
     * Undo the changes to the canvas
     */
    public void undo(){
        Image image = undoSteps.pop();
        if(!undoSteps.empty()){
            redoSteps.push(image);
            this.gc.clearRect(0,0, this.getWidth(), this.getHeight());
            this.setWidth(image.getWidth());
            this.setHeight(image.getHeight());
            this.gc.drawImage(undoSteps.peek(),0,0);
        }
        else {
            this.gc.clearRect(0,0, this.getWidth(), this.getHeight());
            this.setWidth(image.getWidth());
            this.setHeight(image.getHeight());
            this.gc.drawImage(image, 0, 0);
            undoSteps.push(image);
        }
    }

    /**
     * Redo the changes made to the canvas
     */
    public void redo(){
        if(!redoSteps.empty()){
            Image image = redoSteps.pop();
            undoSteps.push(image);
            this.gc.clearRect(0,0, this.getWidth(), this.getHeight());
            this.setWidth(image.getWidth());
            this.setHeight(image.getHeight());
            this.gc.drawImage(image, 0, 0);
        }
    }

    /**
     * Resizes the canvas to entered width and height
     * @param width the resize width of the canvas
     * @param height the resize height of the canvas
     */
    public void resize(double width, double height) {
        this.setWidth(width);
        this.setHeight(height);
    }

    /**
     * Clears the content of the canvas
     */
    public void clearCanvas() {// to clear the canvas
        updateStack(); // updating stack for undo/redo purpose
        // clearing canvas
        initDraw(gc, getWidth(), getHeight()); // bg-color to white
    }

    /**
     * @param gc the graphics context
     * @param canvasWidth the initial width of the canvas
     * @param canvasHeight the initial height of the canvas
     */
    protected void initDraw(GraphicsContext gc, double canvasWidth, double canvasHeight){
        gc.setFill(Color.WHITE);
        gc.fillRect(0, 0, canvasWidth, canvasHeight);
    }

    /**
     * @return boolean value indicating if the user saved the image as or not
     */
    public boolean getImageSavedAs(){return imageSavedAs;}
    public void setImageSavedAs(){imageSavedAs = true;}
}
