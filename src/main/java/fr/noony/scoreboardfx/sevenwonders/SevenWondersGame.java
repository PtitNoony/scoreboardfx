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
import java.util.List;

/**
 *
 * @author Arnaud HAMON-KEROMEN
 */
public interface SevenWondersGame {

    /**
     * Different score categories for the 7W game
     */
    enum SCORE_CATEGORY {
        ARMY, WONDER, GOLD, CIVIL, TRADE, GUILD, SCIENCE
    }

    /**
     * Adds the score to the game.
     *
     * @param score the score to add
     */
    void addScore(SevenWondersScore score);

    /**
     * Each game has a uniqueID given by the factory upon creation.
     *
     * @return the game uniqueID
     */
    int getId();

    /**
     * Returns the scores added in the 7W game.
     *
     * @return the list of the game scores
     */
    List<SevenWondersScore> getScores();

    /**
     *
     * @param player the 7W player
     * @return the score of the given player is played the game, null otherwise
     */
    SevenWondersScore getPlayerScore(Player player);

    /**
     *
     * @param player the 7W player
     * @return if the player has been added to the game
     */
    boolean hasPlayer(Player player);

    /**
     *
     * @param player the 7W player
     * @return the current rank in the game among players added
     */
    int getPlayerRanking(Player player);

}
