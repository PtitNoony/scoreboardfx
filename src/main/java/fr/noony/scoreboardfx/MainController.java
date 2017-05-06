/*
 * Copyright (C) 2017 Arnaud HAMON-KEROMEN
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package fr.noony.scoreboardfx;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;

import fr.noony.scoreboardfx.home.HomeScreen;
import fr.noony.scoreboardfx.sevenwonders.SevenWondersMainScreen;
import java.beans.PropertyChangeEvent;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.Menu;

/**
 *
 * @author Arnaud HAMON-KEROMEN
 */
public class MainController implements Initializable {

    private static final Logger LOGGER = Logger.getGlobal();
    private final Map<Screen, CheckMenuItem> menuItems = new HashMap<>();

    @FXML
    private AnchorPane mainPane;
    @FXML
    private Menu screensMenu;

    private Screen homeScreen = null;
    private Screen sevenWondersMainScreen = null;

    private Screen currentScreen;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadHomeScreen();
    }

    @FXML
    protected void onHomeAction(ActionEvent event) {
        loadHomeScreen();
    }

    private void loadHomeScreen() {
        if (homeScreen == null) {
            homeScreen = new HomeScreen();
            homeScreen.addPropertyChangeListener(this::handleHomeEvents);
            EventHandler<ActionEvent> homeScreenAction = (event) -> {
                LOGGER.log(Level.INFO, "Selected Home screen on {0}", event);
                loadHomeScreen();
            };
            createScreenMenuItem(homeScreen, homeScreenAction);
        }
        setAnchorPaneContent(homeScreen.getMainNode());
        currentScreen = homeScreen;
        homeScreen.refresh();
        updateScreenMenuSelected();
    }

    private void loadSevenWondersMainScreen() {
        if (sevenWondersMainScreen == null) {
            sevenWondersMainScreen = new SevenWondersMainScreen();
            sevenWondersMainScreen.addPropertyChangeListener(this::handleHomeEvents);
            EventHandler<ActionEvent> sevenWondersMainScreenAction = (event) -> {
                LOGGER.log(Level.INFO, "Selected 7wonders main screen on {0}", event);
                loadSevenWondersMainScreen();
            };
            createScreenMenuItem(sevenWondersMainScreen, sevenWondersMainScreenAction);
        }
        setAnchorPaneContent(sevenWondersMainScreen.getMainNode());
        currentScreen = sevenWondersMainScreen;
        sevenWondersMainScreen.refresh();
        updateScreenMenuSelected();
    }

    //
    //// methods to handle screen changes
    //
    private void handleHomeEvents(PropertyChangeEvent event) {
        switch (event.getPropertyName()) {
            case ScreenEvents.LOAD_7_WONDERS:
                loadSevenWondersMainScreen();
                break;
            default:
                throw new UnsupportedOperationException("Unsupported event on HOME page:: " + event.getPropertyName());
        }

    }

    private void setAnchorPaneContent(Node node) {
        mainPane.getChildren().setAll(node);
        AnchorPane.setBottomAnchor(node, 0.0);
        AnchorPane.setLeftAnchor(node, 0.0);
        AnchorPane.setRightAnchor(node, 0.0);
        AnchorPane.setTopAnchor(node, 0.0);
    }

    private void updateScreenMenuSelected() {
        menuItems.forEach((screen, menuItem) -> menuItem.setSelected(screen == currentScreen));
    }

    private CheckMenuItem createScreenMenuItem(Screen screen, EventHandler<ActionEvent> handler) {
        CheckMenuItem item = new CheckMenuItem(screen.getName());
        item.setOnAction(handler);
        screensMenu.getItems().add(item);
        menuItems.put(screen, item);
        return item;
    }
}
