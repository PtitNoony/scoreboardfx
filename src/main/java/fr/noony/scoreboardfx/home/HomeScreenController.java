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
package fr.noony.scoreboardfx.home;

import fr.noony.fxapp.ScreenController;
import fr.noony.scoreboardfx.ScreenEvents;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

/**
 *
 * @author Arnaud HAMON-KEROMEN
 */
public class HomeScreenController implements ScreenController {

    private final PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(HomeScreenController.this);

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //TODO
    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.addPropertyChangeListener(listener);
    }

    @FXML
    protected void onSevenWondersAction(ActionEvent event) {
        propertyChangeSupport.firePropertyChange(ScreenEvents.LOAD_7_WONDERS, null, null);
    }

    protected void refresh() {
        //TODO
    }

}
