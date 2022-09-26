package com.example.paint00;

import javafx.geometry.Rectangle2D;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class myCanvas extends Canvas {
    GraphicsContext gc;
    Line line = new Line();
    myCanvas(){
        this.gc = this.getGraphicsContext2D();
    }

    public void drawRect(double x1, double y1, double x2, double y2) {
        double x = Math.min(x1, x2);
        double y = Math.min(y1, y2);
        double w = Math.abs(x1 - x2);
        double h = Math.abs(y1 - y2);
        gc.fillRect(x, y, w, h);
    }

    public void drawSquare(double x1, double y1, double x2, double y2){
        final double ANGLE_45 = Math.PI/4.0;
        final int SIDES = 4;
        double[] xPoints = new double[SIDES];
        double[] yPoints = new double[SIDES];
        double radius = Math.sqrt(Math.pow((x1 - x2), 2) + Math.pow((y1 - y2), 2));

        for(int i = 0; i < SIDES; i++){
            xPoints[i] = x1 + (radius * Math.cos(((2*Math.PI*i)/4) + ANGLE_45));
            yPoints[i] = y1 + (radius * Math.sin(((2*Math.PI*i)/4) + ANGLE_45));
        }
        this.gc.fillPolygon(xPoints, yPoints, SIDES);
    }

    public void drawCircle(double x1, double y1, double x2, double y2) {
        double r = Math.abs(x1 - x2);
        gc.fillOval(x1-r, y1-r, 2*r, 2*r);
    }

    public void drawLine(double x1, double y1, double x2, double y2){
        line.setStartX(x1);
        line.setStartY(y1);
        line.setEndX(x2);
        line.setEndY(y2);
    }

    public void drawEllipse(double x1, double y1, double x2, double y2){
        double x = Math.abs(x1-x2);
        double y = Math.abs(y1-y2);
        gc.fillOval(x1-x,y1-y,2*x, 2*y);
    }

    public Image getRegion(double x1, double y1, double x2, double y2){
        SnapshotParameters sp = new SnapshotParameters();
        WritableImage wi = new WritableImage((int)Math.abs(x1 - x2),(int)Math.abs(y1 - y2));

        sp.setViewport(new Rectangle2D(
                (Math.min(x1, x2)),
                (Math.min(y1, y2)),
                Math.abs(x1 - x2),
                Math.abs(y1 - y2)));

        this.snapshot(sp, wi);
        return wi;
    }

    public Color getColor(double x, double y){
        return this.getRegion(x, y, x+1, y+1).getPixelReader().getColor(0, 0);
    }
}
