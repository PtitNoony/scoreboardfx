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
package fr.noony.scoreboardfx.sevenwonders;

import fr.noony.fxapp.ScreenController;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

/**
 *
 * @author Arnaud HAMON-KEROMEN
 */
public class SevenWondersMainScreenController implements ScreenController {

    private static final Logger LOGGER = Logger.getGlobal();

    private final PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(SevenWondersMainScreenController.this);

    @FXML
    private TabPane tabPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadViewer();
    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.addPropertyChangeListener(listener);
    }

    @FXML
    protected void onLoadAction(ActionEvent event) {

    }

    protected void refresh() {
        //TODO
    }

    private void loadViewer() {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("SevenWondersViewerScreen.fxml"));
            Node viewerNode = loader.load();
            Tab viewerTab = new Tab("7 Wonders viewer", viewerNode);
            tabPane.getTabs().add(viewerTab);
        } catch (IOException ex) {
            LOGGER.log(Level.SEVERE, "Exception while loading seven wonders main screen fxml file:: {0}", ex);
        }
    }

}
