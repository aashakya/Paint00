package com.example.paint00;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class DialogBox {
    public static void unsavedAlert(){
        ButtonType[] buttons = {new ButtonType("Save"), new ButtonType("Save As"),
                new ButtonType("Cancel"), new ButtonType("Close")};
        Alert unsavedChanges = new Alert(Alert.AlertType.WARNING,
                "Unsaved changes !!! Are you sure you want to close?",
                buttons[0], buttons[1], buttons[2], buttons[3]);
        unsavedChanges.setTitle("Unsaved Changes");
        ButtonType nextStep = unsavedChanges.showAndWait().get();
        //Save
        if(nextStep == buttons[0]){
            Main.getActiveTab().saveImage();
        }
        //Save as
        else if(nextStep == buttons[1]){
            Main.getActiveTab().saveImageAs();
        }
        //Close tab
        else if(nextStep == buttons[3]){
            Main.closeTab();
        }
    }
}
