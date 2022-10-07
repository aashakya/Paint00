package com.example.paint00;

import javafx.scene.paint.Color;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

class ToolBoxTopTest {
    @BeforeEach
    void setUp() {}

    @Test
    void testGetColorPicker() throws FileNotFoundException {
        System.out.println("testing getColorPicker");
        Color colorSelect = Color.WHITE;
        ToolBoxTop instance = new ToolBoxTop();
        assertEquals(colorSelect, instance.getColorPicker());// checks if colorPicker returns WHITE
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
        int brushSizeValue = 25;
        System.out.println("testing getBrushSize");
        ToolBoxTop instance = new ToolBoxTop();
        assertEquals(brushSizeValue, instance.getBrushSize());
    }
}