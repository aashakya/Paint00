package com.example.paint00;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class CanvasPane extends myCanvas{
    static double width = 900;
    static double height = 700;
    double x,y;
    CanvasPane(){
        super();
        resize(width,height);
        initDraw(gc, width, height);
        this.addEventHandler(MouseEvent.MOUSE_PRESSED, event -> {
            x = event.getX();
            y = event.getY();
            gc.setFill(ToolBoxTop.getColorPicker());
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
                case ("rect")->{
                    //gc.setFill(ToolBoxTop.getColorPicker());
                    this.drawRect(x,y,x,y);}
                case ("square")->{
                    //gc.setFill(ToolBoxTop.getColorPicker());
                    this.drawSquare(x,y,x,y);}
                case ("ellipse")->{
                    this.drawEllipse(x,y,x,y);}
                case ("circle")->{
                    this.drawCircle(x,y,x,y);}
                case ("line")->{
                    gc.setStroke(ToolBoxTop.getColorPicker());
                    gc.setLineWidth(ToolBoxTop.getBrushSize());
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
                            gc.stroke();}
                        case ("rect")->{
                            this.drawRect(x,y,event.getX(),event.getY());}
                        case ("square")->{
                            this.drawSquare(x,y,event.getX(),event.getY());}
                        case ("ellipse")->{
                            this.drawEllipse(x,y,event.getX(),event.getY());}
                        case ("circle")->{
                            this.drawCircle(x,y,event.getX(),event.getY());}
                        default -> {}
                    }
                });

        this.addEventHandler(MouseEvent.MOUSE_RELEASED, event -> {
            if(ToolBoxTop.getSelectedTool()=="line"){
                gc.strokeLine(x,y,event.getX(),event.getY());
            }
        });

    }

    @Override
    public boolean isResizable() {
        return true;
    }

    @Override
    public double maxHeight(double width) {
        return Double.POSITIVE_INFINITY;
    }

    @Override
    public double maxWidth(double height) {
        return Double.POSITIVE_INFINITY;
    }

    @Override
    public double minWidth(double height) {
        return 1D;
    }

    @Override
    public double minHeight(double width) {
        return 1D;
    }

    @Override
    public void resize(double width, double height) {
        this.setWidth(width);
        this.setHeight(height);
    }
    private void initDraw(GraphicsContext gc, double canvasWidth, double canvasHeight){
        gc.setFill(Color.WHITE);
        gc.fillRect(0, 0, canvasWidth, canvasHeight);
    }

//    public static void setCanvasWidth(double width) {
//        CanvasPane.resize(width,width);
//    }
}
