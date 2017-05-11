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

import fr.noony.gameutils.Player;
import fr.noony.fxapp.ScreenController;
import java.beans.PropertyChangeListener;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Accordion;
import javafx.scene.control.Label;

/**
 *
 * @author ahamon
 */
public class SevenWondersViewerPlayerController implements ScreenController {

    private static final Logger LOG = Logger.getGlobal();

    @FXML
    private Label playerLabel;
    @FXML
    private Accordion accordion;
    @FXML
    private LineChart<String, Number> scoresChart;
    @FXML
    private StackedBarChart<String, Number> scoreBreakdownChart;
    @FXML
    private LineChart<String, Number> rankingChart;
    @FXML
    private Label winPercentageL;

    //
    private List<SevenWondersGame> games;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        scoresChart.getXAxis().setLabel("GAMES");
        scoresChart.getYAxis().setLabel("SCORE");
        //
        scoreBreakdownChart.getXAxis().setLabel("GAMES");
        scoreBreakdownChart.getYAxis().setLabel("SCORE");
        //
        rankingChart.getXAxis().setLabel("GAMES");
        rankingChart.getYAxis().setLabel("RANKING");
        try {
            NumberAxis rYAxis = (NumberAxis) rankingChart.getYAxis();
            rYAxis.setMinorTickVisible(false);
            rYAxis.setTickUnit(1);
            rYAxis.setUpperBound(0);
            rYAxis.setLowerBound(7);
        } catch (Exception e) {
            LOG.log(Level.SEVERE, "Exception while casting ranking chart axis: {0}", e);
        }
        //
        accordion.setExpandedPane(accordion.getPanes().get(1));
    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {
    }

    protected void setPlayer(Player selectedPlayer) {
        if (selectedPlayer != null) {
            playerLabel.setText(selectedPlayer.getNickName());
            //
            games = SevenWondersGameFactory.getGames().stream().filter(game -> game.getPlayerScore(selectedPlayer) != null).collect(Collectors.toList());
            populateScoresChart(selectedPlayer);
            populateScoresBreakdownChart(selectedPlayer);
            populateRankingChart(selectedPlayer);
            //
            SevenWondersPlayerRankingHistory history = new SevenWondersPlayerRankingHistory(selectedPlayer);
            winPercentageL.setText(Integer.toString((int) (history.getWinPercentage() * 100)));
        }
    }

    private void populateScoresChart(Player selectedPlayer) {
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Scores");
        SevenWondersScore score;
        for (int i = 0; i < games.size(); i++) {
            score = games.get(i).getPlayerScore(selectedPlayer);
            String seriesName = Integer.toString(i);
            series.getData().add(new XYChart.Data<>(seriesName, score.getTotalScore()));
        }
        scoresChart.getData().setAll(series);
    }

    private void populateScoresBreakdownChart(Player selectedPlayer) {
        // In order to avoid any bugs, forced to create new series each time...
        Map<SevenWondersGame.SCORE_CATEGORY, XYChart.Series<String, Number>> series = new HashMap<>();
        for (SevenWondersGame.SCORE_CATEGORY cat : SevenWondersGame.SCORE_CATEGORY.values()) {
            series.put(cat, new XYChart.Series<>());
        }
        series.values().forEach(s -> s.getData().clear());
        // this approach is not yet usable since I did not set a way to get the unique id / date from the game in the score class
//            games.stream().map(game -> game.getPlayerScore(selectedPlayer))
//                    .forEach(score -> {
//                        for (SevenWondersGame.SCORE_CATEGORY cat : SevenWondersGame.SCORE_CATEGORY.values()) {
//                            series.get(cat).getData().add(new XYChart.Data<>(cat.name(), score.getScore(cat)));
//                        }
//                    });
        SevenWondersScore score;
        for (int i = 0; i < games.size(); i++) {
            score = games.get(i).getPlayerScore(selectedPlayer);
            String seriesName = Integer.toString(i);
            for (SevenWondersGame.SCORE_CATEGORY cat : SevenWondersGame.SCORE_CATEGORY.values()) {
                series.get(cat).getData().add(new XYChart.Data<>(seriesName, score.getScore(cat)));
            }
        }
        //
        scoreBreakdownChart.getData().setAll(series.values());
    }

    private void populateRankingChart(Player selectedPlayer) {
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Rankings");
        SevenWondersGame game;
        for (int i = 0; i < games.size(); i++) {
            game = games.get(i);
            String seriesName = Integer.toString(i);
            series.getData().add(new XYChart.Data<>(seriesName, game.getPlayerRanking(selectedPlayer)));
        }
        rankingChart.getData().setAll(series);
    }

}
