package com.example.paint00;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

/**
 * Class for handling the dialog boxes for the application
 */
public class DialogBox {
    static AppMenu appMenu; // creating a appMenu object to use the appMenu features

    /**
     * Alerts user when the user closes the program without saving
     */
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

    /**
     * Alerts user when they try to clear the canvas
     */
    public static void clearCAlert(){
        ButtonType[] buttons = {new ButtonType("Yes"), new ButtonType("No")};
        Alert unsavedChanges = new Alert(Alert.AlertType.WARNING,
                "Are you sure you want to clear canvas",
                buttons[0], buttons[1]);
        unsavedChanges.setTitle("Clear Canvas ???");
        ButtonType selectedBtn = unsavedChanges.showAndWait().get();
        appMenu = new AppMenu();
        // Clear canvas
        if(selectedBtn == buttons[0]){
            Main.getActiveTab().canvasPane.clearCanvas();
            Main.getActiveTab().canvasPane.updateStack();
        }

    }

    /**
     * Asks user if they are sure they want to clear the content of the canvas
     * @return boolean based on user selection for Yes or No
     */
    public static boolean qualityAlert(){
        ButtonType[] buttons = {new ButtonType("Yes"), new ButtonType("No")};
        Alert unsavedChanges = new Alert(Alert.AlertType.WARNING,
                "Are you sure you want to continue? Data loss can happen",
                buttons[0], buttons[1]);
        unsavedChanges.setTitle("Continue");
        ButtonType selectedBtn = unsavedChanges.showAndWait().get();
        appMenu = new AppMenu();
        //Save
        if(selectedBtn == buttons[0]){
            return true;
        }
        //Save as
        else if(selectedBtn == buttons[1]){
            return false;
        }
        return false;
    }
}
