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
import fr.noony.gameutils.Player;
import fr.noony.gameutils.PlayerFactory;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.ThreadLocalRandom;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.SelectionMode;
import javafx.scene.layout.AnchorPane;
import org.controlsfx.control.CheckListView;

/**
 *
 * @author ahamon
 */
public class SevenWondersViewerController implements ScreenController {

    private static final Logger LOGGER = Logger.getGlobal();

    private enum ViewState {
        SUMMARY, COMPARE, PLAYER
    }

    //debug
    private final List<SevenWondersGame> games = new LinkedList<>();
    //
    private ViewState viewState;
    private Player selectedPlayer = null;
    //
    private Node compareScreenNode;
    private SevenWondersViewerCompareController compareScreenController;
    //
    private Node summaryScreenNode;
    private SevenWondersViewerSummaryController summaryScreenController;
    //
    private Node playerScreenNode;
    private SevenWondersViewerPlayerController playerScreenController;

    @FXML
    private CheckListView<Player> playerListView;
    @FXML
    private AnchorPane viewContentPane;
    @FXML
    private Button showSummaryB;
    @FXML
    private Button compareB;
    @FXML
    private Button showSelectedPB;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //TODO
        //temp code for debug
        System.err.println("Starting random generator for debug /TODO: remove");
        List<Player> players = PlayerFactory.getCreatedPlayers();
        int nbGames = 15;

        for (int i = 0; i < nbGames; i++) {
            final SevenWondersGame game = SevenWondersGameFactory.createGame();
            players.forEach(player -> {
                SevenWondersScore score = new SevenWondersScore(
                        player, generateRandom(-10, 18),
                        generateRandom(0, 15),
                        generateRandom(0, 15),
                        generateRandom(0, 35),
                        generateRandom(0, 15),
                        generateRandom(0, 35),
                        generateRandom(0, 35));
                game.addScore(score);
            });
            games.add(game);
        }
        //
        playerListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        playerListView.getSelectionModel().getSelectedItems().addListener((ListChangeListener.Change<? extends Object> c) -> {
            //TODO: log
            selectedPlayer = playerListView.getSelectionModel().getSelectedItem();
            setState(ViewState.PLAYER);
        });
        playerListView.getCheckModel().getCheckedIndices().addListener((ListChangeListener.Change<? extends Integer> c) -> {
            //TODO: log
            compareB.setDisable(viewState == ViewState.COMPARE || playerListView.getCheckModel().getCheckedIndices().size() < 2);
        });
        //
        playerListView.getItems().addAll(players);
        setState(ViewState.SUMMARY);
    }

    @FXML
    protected void handleShowSummary(ActionEvent event) {
        setState(ViewState.SUMMARY);
    }

    @FXML
    protected void handleShowComparison(ActionEvent event) {
        setState(ViewState.COMPARE);
    }

    @FXML
    protected void handleShowSelectedPlayer(ActionEvent event) {
        setState(ViewState.COMPARE);
    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        //TODO
    }

    private int generateRandom(int min, int max) {
        // + 1 to make the top inclusive
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }

    private void setState(ViewState newState) {
        switch (newState) {
            case COMPARE:
                if (newState != viewState) {
                    setCompareScreen();
                }
                showSummaryB.setDisable(false);
                compareB.setDisable(true);
                showSelectedPB.setDisable(selectedPlayer == null);
                break;
            case SUMMARY:
                if (newState != viewState) {
                    setSummaryScreen();
                }
                showSummaryB.setDisable(true);
                compareB.setDisable(playerListView.getCheckModel().getCheckedIndices().size() < 2);
                showSelectedPB.setDisable(selectedPlayer == null);
                break;
            case PLAYER:
                if (newState != viewState) {
                    setPlayerScreen();
                } else {
                    playerScreenController.setPlayer(selectedPlayer);
                }
                showSummaryB.setDisable(false);
                compareB.setDisable(playerListView.getCheckModel().getCheckedIndices().size() < 2);
                showSelectedPB.setDisable(true);
                break;
        }
        viewState = newState;
    }

    private void loadCompareScreen() {
        //make sure it is not loaded yet
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("SevenWondersViewerCompare.fxml"));
            compareScreenNode = loader.load();
            compareScreenController = loader.getController();
        } catch (IOException ex) {
            LOGGER.log(Level.SEVERE, "Exception while loading seven wonders viewer compare screen fxml file:: {0}", ex);
        }
    }

    private void loadSummaryScreen() {
        //make sure it is not loaded yet
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("SevenWondersViewerSummary.fxml"));
            summaryScreenNode = loader.load();
            summaryScreenController = loader.getController();
        } catch (IOException ex) {
            LOGGER.log(Level.SEVERE, "Exception while loading seven wonders viewer summary screen fxml file:: {0}", ex);
        }
    }

    private void loadPlayerScreen() {
        //make sure it is not loaded yet
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("SevenWondersViewerPlayer.fxml"));
            playerScreenNode = loader.load();
            playerScreenController = loader.getController();
        } catch (IOException ex) {
            LOGGER.log(Level.SEVERE, "Exception while loading seven wonders viewer player screen fxml file:: {0}", ex);
        }
    }

    private void setCompareScreen() {
        if (compareScreenNode == null) {
            loadCompareScreen();
        }
        setAnchorPaneContent(compareScreenNode);
    }

    private void setSummaryScreen() {
        if (summaryScreenNode == null) {
            loadSummaryScreen();
        }
        setAnchorPaneContent(summaryScreenNode);
        showSummaryB.setDisable(true);
        deselectPlayer();
    }

    private void setPlayerScreen() {
        if (playerScreenNode == null) {
            loadPlayerScreen();
        }
        setAnchorPaneContent(playerScreenNode);
        playerScreenController.setPlayer(selectedPlayer);
    }

    private void setAnchorPaneContent(Node node) {
        viewContentPane.getChildren().setAll(node);
        AnchorPane.setBottomAnchor(node, 0.0);
        AnchorPane.setLeftAnchor(node, 0.0);
        AnchorPane.setRightAnchor(node, 0.0);
        AnchorPane.setTopAnchor(node, 0.0);
    }

    private void deselectPlayer() {
        selectedPlayer = null;
        playerListView.getSelectionModel().clearSelection();
    }

}
