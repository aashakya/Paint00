package com.example.paint00;

import javafx.geometry.Rectangle2D;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

/**
 * myCanvas is the extension of the Canvas class
 */
public class myCanvas extends Canvas {
    GraphicsContext gc; // declaring the graphics context

    /**
     * Initializes the graphics context as the graphics context of the canvas
     */
    myCanvas(){
        this.gc = this.getGraphicsContext2D();
    }

    /**
     * Draws a rectangle with two end points input
     * @param x1 x coordinate of Point 1 of the rectangle
     * @param y1 y coordinate of Point 1 of the rectangle
     * @param x2 x coordinate of Point 2 of the rectangle
     * @param y2 y coordinate of Point 2 of the rectangle
     */
    public void drawRect(double x1, double y1, double x2, double y2) {
        double x = Math.min(x1, x2); // get the min x point
        double y = Math.min(y1, y2); // get the min y point
        // get the distance between the x and y coordinates of the points
        double w = Math.abs(x1 - x2);
        double h = Math.abs(y1 - y2);
        gc.fillRect(x, y, w, h);
    }

    /**
     * Draw a square from two points: Point 1 as center and Point 2 as end
     * @param x1 x coordinate of Point 1 of the square
     * @param y1 y coordinate of Point 1 of the square
     * @param x2 x coordinate of Point 2 of the square
     * @param y2 y coordinate of Point 2 of the square
     */
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

    /**
     * Draw a circle from center point to end point
     * @param x1 x coordinate of the center point of the circle
     * @param y1 y coordinate of the center point of the circle
     * @param x2 x coordinate of the end point of the circle
     * @param y2 y coordinate of the end point of the circle
     */
    public void drawCircle(double x1, double y1, double x2, double y2) {
        double r = Math.abs(x1 - x2);
        gc.fillOval(x1-r, y1-r, 2*r, 2*r);
    }

    /**
     * Draw a polygon with n sides from center point to end point
     * @param x1 x coordinate of the center point of the polygon
     * @param y1 y coordinate of the center point of the polygon
     * @param x2 x coordinate of the end point of the polygon
     * @param y2 y coordinate of the end point of the polygon
     * @param n Number of sides
     */
    public void drawPolygon(double x1, double y1, double x2, double y2, int n){
        double[] xPoints = new double[n];
        double[] yPoints = new double[n];
        double radius = Math.sqrt(Math.pow((x1 - x2), 2) + Math.pow((y1 - y2), 2));
        double startAngle = Math.atan2(y2 - y1, x2 - x1);
        for(int i = 0; i < n; i++){
            xPoints[i] = x1 + (radius * Math.cos(((2*Math.PI*i)/n) + startAngle));
            yPoints[i] = y1 + (radius * Math.sin(((2*Math.PI*i)/n) + startAngle));
        }
        this.gc.fillPolygon(xPoints, yPoints, n);
    }

    /**
     * draw an ellipse from center point to end point
     * @param x1 x coordinate of the center point of the ellipse
     * @param y1 y coordinate of the center point of the ellipse
     * @param x2 x coordinate of the end point of the ellipse
     * @param y2 y coordinate of the end point of the ellipse
     */
    public void drawEllipse(double x1, double y1, double x2, double y2){
        double x = Math.abs(x1-x2);
        double y = Math.abs(y1-y2);
        gc.fillOval(x1-x,y1-y,2*x, 2*y);
    }

    /**
     * Get snapshot starting from Point 1 to Point 2
     * @param x1 x coordinate of Point 1 of the snapshot region
     * @param y1 y coordinate of Point 1 of the snapshot region
     * @param x2 x coordinate of Point 2 of the snapshot region
     * @param y2 y coordinate of Point 2 of the snapshot region
     * @return Image of the snapshot
     */
    public Image getRegion(double x1, double y1, double x2, double y2){
        // Taking the snapshot of the selected region
        SnapshotParameters sp = new SnapshotParameters();
        WritableImage wi = new WritableImage((int)Math.abs(x1 - x2),(int)Math.abs(y1 - y2));
        // selecting rectangular region of the snapshot
        sp.setViewport(new Rectangle2D(
                (Math.min(x1, x2)),
                (Math.min(y1, y2)),
                Math.abs(x1 - x2),
                Math.abs(y1 - y2)));

        this.snapshot(sp, wi); // copying the snapshot to a writable image
        return wi;
    }

    /**
     * Get color of the pixel at the given coordinates
     * @param x x coordinate of selected point
     * @param y y coordinate of selected point
     * @return Color at the selected point
     */
    public Color getColor(double x, double y){
        return this.getRegion(x, y, x+1, y+1).getPixelReader().getColor(0, 0);
    }
}
