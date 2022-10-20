package com.example.paint00;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

class ToolBoxTopTest {

    @Test
    void testGetSizeNo() throws FileNotFoundException {
        System.out.println("testing getSizeNo");
        int sizePolygon = 3;
        ToolBoxTop instance = new ToolBoxTop();
        assertEquals(sizePolygon, instance.getSizeNo());// checks if colorPicker returns WHITE
    }

    @Test
    void testGetSelectedTool() throws FileNotFoundException {
        System.out.println("testing getSelectedTool");
        ToolBoxTop instance= new ToolBoxTop();
        String selected = "None";
        assertEquals(selected, instance.getSelectedTool());// checks if selected tool is
    }
    @Test
    void testSetColorPicker() throws FileNotFoundException {
        System.out.println("testing setColorPicker");
        Color colorSelect = Color.TEAL;
        ToolBoxTop instance = new ToolBoxTop();
        instance.setColorPicker(colorSelect);
        assertEquals(colorSelect, instance.getColorPicker()); // checks if colorPicker gets set to TEAL
    }

    @Test
    void testGetBrushSize() throws FileNotFoundException {
        double brushSizeValue = 25;
        System.out.println("testing getBrushSize");
        ToolBoxTop instance = new ToolBoxTop();
        assertEquals(brushSizeValue, instance.getBrushSize()); // checks if brushSize is set to 25
    }

    @Test
    public void testPaint() throws InterruptedException{
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                new JFXPanel();
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            new Main().start(new Stage());
                        }
                        catch (Exception e){
                            System.out.println("The program did not work");
                        }
                    }
                });
            }
        });
        t.start();
        t.sleep(10000);
    }
}