package com.example.paint00;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class DialogBox {
    static AppMenu appMenu;
    public static void unsavedAlert(){
        ButtonType[] buttons = {new ButtonType("Save"), new ButtonType("Save As"),
                new ButtonType("Cancel"), new ButtonType("Close Tab"),
                new ButtonType("Close All")};
        Alert unsavedChanges = new Alert(Alert.AlertType.WARNING,
                "Unsaved changes !!! Are you sure you want to close?",
                buttons[0], buttons[1], buttons[2], buttons[3], buttons[4]);
        unsavedChanges.setTitle("Unsaved Changes");
        ButtonType selectedBtn = unsavedChanges.showAndWait().get();
        appMenu = new AppMenu();
        //Save
        if(selectedBtn == buttons[0]){
            appMenu.saveAction();
        }
        //Save as
        else if(selectedBtn == buttons[1]){
            appMenu.saveAsAction();
        }
        //Close tab
        else if(selectedBtn == buttons[3]){
            Main.closeTab();
        }
        //Close all tabs
        else if (selectedBtn == buttons[4]) {
            Main.myStage.close();
        }
    }

    public static void clearCAlert(){
        ButtonType[] buttons = {new ButtonType("Yes"), new ButtonType("No")};
        Alert unsavedChanges = new Alert(Alert.AlertType.WARNING,
                "Are you sure you want to clear canvas",
                buttons[0], buttons[1]);
        unsavedChanges.setTitle("Clear Canvas ???");
        ButtonType selectedBtn = unsavedChanges.showAndWait().get();
        appMenu = new AppMenu();
        //Save
        if(selectedBtn == buttons[0]){
            Main.getActiveTab().clearCanvas();
        }
        //Save as
        else if(selectedBtn == buttons[1]){
            appMenu.saveAsAction();
        }
        //Close tab
        else if(selectedBtn == buttons[3]){
            Main.closeTab();
        }
    }
}
