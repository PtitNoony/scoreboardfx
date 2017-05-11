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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 *
 * @author Arnaud HAMON-KEROMEN
 */
public class SevenWondersGameFactory {

    private static final Logger LOG = Logger.getGlobal();

    private static final Map<Integer, SevenWondersGame> GAMES = new HashMap<>();

    private static final int ID_INCR = 7;

    private static int nextUniqueID = 1;

    private SevenWondersGameFactory() {
        // private utility constructor
    }

    public static List<SevenWondersGame> getGames() {
        // doc that time consuming method
        return GAMES.entrySet().stream().map(entry -> entry.getValue()).collect(Collectors.toList());
    }

    public static SevenWondersGame createGame() {
        while (GAMES.containsKey(nextUniqueID)) {
            nextUniqueID++;
        }
        final SevenWondersGame game = new GameImpl(nextUniqueID);
        GAMES.put(nextUniqueID, game);
        incrementUniqueID();
        return game;
    }

    public static SevenWondersGame createPlayer(int id) {
        if (GAMES.containsKey(id)) {
            //TODO: message
            throw new IllegalStateException("Error");

        }
        throw new IllegalStateException("Error TODO");
    }

    public static SevenWondersGame getGamerFromID(int gameID) {
        return GAMES.get(gameID);
    }

    private static void incrementUniqueID() {
        nextUniqueID++;
        while (GAMES.containsKey(nextUniqueID)) {
            nextUniqueID += ID_INCR;
        }
    }

    private static class GameImpl implements SevenWondersGame {

        private final Map<Player, SevenWondersScore> scores;
        private final int id;

        private Map<Player, Integer> rankings;

        private GameImpl(int id) {
            this.id = id;
            scores = new HashMap<>();
        }

        @Override
        public void addScore(SevenWondersScore score) {
            if (scores.containsKey(score.getPlayer())) {
                LOG.log(Level.INFO, "Trying to add a score for an already existing player: {0}", score.getPlayer());
                return;
            }
            scores.put(score.getPlayer(), score);
            recalculateRanking();
        }

        @Override
        public int getId() {
            return id;
        }

        @Override
        public List<SevenWondersScore> getScores() {
            return scores.values().stream().collect(Collectors.toList());
        }

        @Override
        public SevenWondersScore getPlayerScore(Player player) {
            return scores.get(player);
        }

        @Override
        public boolean hasPlayer(Player player) {
            return scores.containsKey(player);
        }

        @Override
        public int getPlayerRanking(Player player) {
            return rankings.get(player);
        }

        private void recalculateRanking() {
            // not optimized
            //TODO: next step is to use a function for all != games
            List<SevenWondersScore> orderedGames = scores.values().stream().sorted((g1, g2) -> -Integer.compare(g1.getTotalScore(), g2.getTotalScore())).collect(Collectors.toList());
            rankings = new HashMap<>();
            int lastScore = Integer.MIN_VALUE;
            int lastRanking = 0;
            for (int i = 0; i < orderedGames.size(); i++) {
                SevenWondersScore score = orderedGames.get(i);
                if (lastScore == score.getTotalScore()) {
                    rankings.put(score.getPlayer(), lastRanking);
                } else {
                    rankings.put(score.getPlayer(), i + 1);
                    lastRanking = i + 1;
                    lastScore = score.getTotalScore();
                }
            }

        }

    }

}
