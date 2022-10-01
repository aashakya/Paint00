package com.example.paint00;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import java.util.Stack;

public class CanvasPane extends myCanvas{
    //default width and height of image
    static double width = 900;
    static double height = 700;
    double x,y;
    private Stack<Image> undoSteps; // Image stack for undo
    private Stack<Image> redoSteps; // Image stack for redo
    CanvasPane(){
        super();
        undoSteps = new Stack<>();
        redoSteps = new Stack<>();
        resize(width,height);
        initDraw(gc, width, height);
        // canvas actions for mouse movement
        this.undoSteps.push(this.getRegion(0,0,this.getWidth(),this.getHeight()));
        this.addEventHandler(MouseEvent.MOUSE_PRESSED, event -> {
            x = event.getX();
            y = event.getY();
            gc.setFill(ToolBoxTop.getColorPicker());
            switch (ToolBoxTop.getSelectedTool()) {
                case ("eraser")->{
                    gc.setStroke(Color.WHITE);
                    gc.setLineWidth(ToolBoxTop.getBrushSize());
                    gc.beginPath();
                    gc.moveTo(event.getX(), event.getY());
                    gc.stroke();
                }
                case ("pen")->{
                    gc.setStroke(ToolBoxTop.getColorPicker());
                    gc.setLineWidth(ToolBoxTop.getBrushSize());
                    gc.beginPath();
                    gc.moveTo(event.getX(), event.getY());
                    gc.stroke();
                }
                case ("rect")->{
                    this.drawRect(x,y,x,y);
                }
                case ("square")->{
                    this.drawSquare(x,y,x,y);
                }
                case ("ellipse")->{
                    this.drawEllipse(x,y,x,y);
                }
                case ("circle")->{
                    this.drawCircle(x,y,x,y);
                }
                case ("line")->{
                    gc.setStroke(ToolBoxTop.getColorPicker());
                    gc.setLineWidth(ToolBoxTop.getBrushSize());
                }
                case ("dashedLine")->{
                    gc.setStroke(ToolBoxTop.getColorPicker());
                    gc.setLineWidth(ToolBoxTop.getBrushSize());
                    gc.setLineDashes(ToolBoxTop.getBrushSize()*2);
                    gc.setLineDashOffset(ToolBoxTop.getBrushSize()*2);
                }
                case ("grabColor")->{
                    ToolBoxTop.setColorPicker(this.getColor(x,y));
                }
                case ("drawPolygon")->{
                    this.drawPolygon(x,y,x,y, ToolBoxTop.getSizeNo());
                }
                case ("drawPent")->{
                    this.drawPolygon(x,y,x,y,5);
                }
                default -> {
                }
            }
            System.out.println(ToolBoxTop.getSelectedTool());
        });

        this.addEventHandler(MouseEvent.MOUSE_DRAGGED,
                event -> {
                    switch (ToolBoxTop.getSelectedTool()) {
                        case ("eraser"), ("pen") -> {
                            gc.lineTo(event.getX(), event.getY());
                            gc.stroke();
                        }
                        case ("grabColor")->{
                            ToolBoxTop.setColorPicker(this.getColor(event.getX(),event.getY()));
                        }
                        default -> {}
                    }
                });

        this.addEventHandler(MouseEvent.MOUSE_RELEASED, event -> {
            if(ToolBoxTop.getSelectedTool()=="line"){
                gc.strokeLine(x,y,event.getX(),event.getY());
                this.updateStack();
            }
            else if (ToolBoxTop.getSelectedTool()=="dashedLine"){
                gc.strokeLine(x,y,event.getX(),event.getY());
                gc.setLineDashes(null);
                gc.setLineDashOffset(0);
                this.updateStack();
            }
            switch (ToolBoxTop.getSelectedTool()) {
                case ("eraser"), ("pen") -> {}
                case ("rect")->{
                    this.drawRect(x,y,event.getX(),event.getY());
                    this.updateStack();
                }
                case ("square")->{
                    this.drawSquare(x,y,event.getX(),event.getY());
                    this.updateStack();
                }
                case ("ellipse")->{
                    this.drawEllipse(x,y,event.getX(),event.getY());
                    this.updateStack();
                }
                case ("circle")->{
                    this.drawCircle(x,y,event.getX(),event.getY());
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
                default -> {}
            }
        });

    }

    /**
     * Update the canvas stacks
     */
    public void updateStack(){
        undoSteps.push(this.getRegion(0,0,this.getWidth(),this.getHeight()));
        redoSteps.clear();
    }

    /**
     * Undo changes to the canvas
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

    public void resize(double width, double height) {
        this.setWidth(width);
        this.setHeight(height);
    }
    protected void initDraw(GraphicsContext gc, double canvasWidth, double canvasHeight){
        gc.setFill(Color.WHITE);
        gc.fillRect(0, 0, canvasWidth, canvasHeight);
    }
}
