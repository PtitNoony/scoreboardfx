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
import java.util.stream.Collectors;

/**
 *
 * @author ahamon
 */
public class SevenWondersGame {

    public enum SCORE_CATEGORY {
        ARMY, WONDER, GOLD, CIVIL, TRADE, GUILD, SCIENCE
    }

    private final Map<Player, SevenWondersScore> scores;
    private final int id;

    private Map<Player, Integer> rankings;

    protected SevenWondersGame(int id) {
        this.id = id;
        scores = new HashMap<>();
    }

    public void addScore(SevenWondersScore score) {
        //TODO: test player
        scores.put(score.getPlayer(), score);
        recalculateRanking();
    }

    public int getId() {
        return id;
    }

    public List<SevenWondersScore> getScores() {
        return scores.values().stream().collect(Collectors.toList());
    }

    public SevenWondersScore getPlayerScore(Player player) {
        return scores.get(player);
    }

    public boolean hasPlayer(Player player) {
        return scores.containsKey(player);
    }

    public int getPlayerRanking(Player player) {
        return rankings.get(player);
    }

    private void recalculateRanking() {
        // not optimized
        //TODO: next step is to use a function for all != games
        List<SevenWondersScore> orderedGames = scores.values().stream().sorted((g1, g2) -> Integer.compare(g1.getTotalScore(), g2.getTotalScore())).collect(Collectors.toList());
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
