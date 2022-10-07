/**
 * @author Aakrista Shakya
 */
package com.example.paint00;

import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;
import java.io.FileNotFoundException;

/**
 * The Paint application
 */
public class Main extends Application {
    public static Stage myStage; // declaring the stage
    static TabPane tabPane; // declaring the tabPane

    static TabPlus tab1; // declaring the tab object as a TabPlus

    /**
     * @param primaryStage the main stage of the application
     */
    @Override
    public void start(Stage primaryStage) throws FileNotFoundException {
        myStage = primaryStage;
        //set up the title for the stage
        primaryStage.setTitle("Paint 00");
        // Get primary screen bounds
        Rectangle2D screenBounds = Screen.getPrimary().getBounds();
        primaryStage.setX(screenBounds.getMinX());
        primaryStage.setY(screenBounds.getMinY());
        VBox layout = new VBox(7);// create a vbox with 7 spacing between each child

        tabPane = new TabPane(); // create a tab pane

        tab1 = new TabPlus();

        tabPane.getTabs().addAll(tab1, newTabButton(tabPane));

        ToolBoxTop toolBoxTop = new ToolBoxTop();
        // Menu bar
        AppMenu topMenu = new AppMenu();
        layout.getChildren().add(topMenu.menuBar);
        layout.getChildren().add(toolBoxTop.toolBoxTop);
        layout.getChildren().add(tabPane);

        Scene scene = new Scene(layout, 10000,1000);
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setOnCloseRequest(e-> DialogBox.unsavedAlert());
    }

    public static Stage getStage() {
        return myStage;
    }

    public static void main(String[] args) {
        launch();
    }

    /**
     * @param tabPane The tabPane the application is using
     * @return a new tab added to the tabPane
     */
    private Tab newTabButton(TabPane tabPane) {
        Tab addTab = new Tab("Create Tab"); // Tab button text for adding a new tab
        addTab.setClosable(false);
        tabPane.getSelectionModel().selectedItemProperty().addListener((observable, oldTab, newTab) -> {
            if(newTab == addTab) {
                tabPane.getTabs().add(tabPane.getTabs().size() - 1, new TabPlus()); // Adding new tab before the "button" tab
                tabPane.getSelectionModel().select(tabPane.getTabs().size() - 2); // Selecting the tab before the button, which is the newly created one
            }
        });
        return addTab;
    }

    /**
     * @return the tab that user is currently working on
     */
    public static TabPlus getActiveTab(){
        return (TabPlus) tabPane.getSelectionModel().getSelectedItem();
    }

    /**
     * Closes the selected tab
     */
    public static void closeTab() {
        Main.tabPane.getTabs().remove(Main.getActiveTab());
    }
}