package hrsindex;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Matt
 */
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;

public class MyMenu extends MenuBar {
    private Menu file = new Menu("File");
    private Menu edit = new Menu("Edit");

    MyMenu() {
        fileInit(file);
        editInit(edit);
        this.getMenus().addAll(file, edit);
    }

    private void fileInit(Menu menu) {
        MenuItem exit = new MenuItem("Exit");
        exit.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                System.exit(0);
            }
        });
        
        menu.getItems().addAll(exit);
    }
    
    private void editInit(Menu menu) {
        MenuItem preferences = new MenuItem("Preferences");
        menu.getItems().addAll(preferences);
    }
}
