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
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Button;

/**
 *
 * @author Arnaud HAMON-KEROMEN
 */
public class MainController implements Initializable {

    @FXML
    private AnchorPane mainPane;
    @FXML
    private Button homeButton;

    private Screen homeScreen = null;
    private Screen sevenWondersMainScreen = null;

    private Screen currentScreen;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadHomeScreen();
    }
    
    @FXML
    protected void onHomeAction(ActionEvent event){
        loadHomeScreen();
    }

    private void loadHomeScreen() {
        if (homeScreen == null) {
            homeScreen = new HomeScreen();
            homeScreen.addPropertyChangeListener(this::handleHomeEvents);
        }
        setAnchorPaneContent(homeScreen.getMainNode());
        currentScreen = homeScreen;
        homeButton.setDisable(true);
        homeScreen.refresh();
    }
    
    private void loadSevenWondersMainScreen(){
        if (sevenWondersMainScreen == null) {
            sevenWondersMainScreen = new SevenWondersMainScreen();
            sevenWondersMainScreen.addPropertyChangeListener(this::handleHomeEvents);
        }
        setAnchorPaneContent(sevenWondersMainScreen.getMainNode());
        currentScreen = sevenWondersMainScreen;
        homeButton.setDisable(false);
        sevenWondersMainScreen.refresh();
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
}
