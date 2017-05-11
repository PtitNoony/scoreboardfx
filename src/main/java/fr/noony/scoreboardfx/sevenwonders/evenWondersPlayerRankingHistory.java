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
 * @author ahamon
 */
public class evenWondersPlayerRankingHistory {

    private final Player player;

    private int nbGames;
    private int first;
    private int second;
    private int third;
    private int fourth;
    private int fifth;
    private int sixth;
    private int seventh;

    public evenWondersPlayerRankingHistory(Player player, List<SevenWondersGame> games) {
        this.player = player;
        calculateRankingHistory(games);
    }

    public evenWondersPlayerRankingHistory(Player player) {
        this(player, SevenWondersGameFactory.getGames());
    }

    public Player getPlayer() {
        return player;
    }

    public int getNbFirst() {
        return first;
    }

    public int getNbSecond() {
        return second;
    }

    public int getNbThird() {
        return third;
    }

    public int getNbFourth() {
        return fourth;
    }

    public int getNbFifth() {
        return fifth;
    }

    public int getNbSixth() {
        return sixth;
    }

    public int getNbSeventh() {
        return seventh;
    }

    public int getNbGames() {
        return nbGames;
    }

    public double getWinPercentage() {
        return (double) first / nbGames;
    }

    private void calculateRankingHistory(List<SevenWondersGame> games) {
        nbGames = 0;
        games.stream().filter(g -> g.hasPlayer(player))
                .forEach(g -> {
                    nbGames++;
                    switch (g.getPlayerRanking(player)) {
                        case 1:
                            first++;
                            break;
                        case 2:
                            second++;
                            break;
                        case 3:
                            third++;
                            break;
                        case 4:
                            fourth++;
                            break;
                        case 5:
                            fifth++;
                            break;
                        case 6:
                            sixth++;
                            break;
                        case 7:
                            seventh++;
                            break;
                        default:
                            throw new IllegalArgumentException("Illegal ranking:: " + g.getPlayerRanking(player));
                    }
                });
    }

}
