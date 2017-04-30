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

import fr.noony.scoreboardfx.Player;

/**
 *
 * @author ahamon
 */
public class SevenWondersScore {

    private Player player;
    private int armyScore;
    private int goldScore;
    private int wonderScore;
    private int civilScore;
    private int tradeScore;
    private int guildScore;
    private int scienceScore;
    //
    private int totalScore;

    public SevenWondersScore(Player player, int armyScore, int goldScore, int wonderScore, int civilScore, int tradeScore, int guildScore, int scienceScore) {
        this.player = player;
        this.armyScore = armyScore;
        this.goldScore = goldScore;
        this.wonderScore = wonderScore;
        this.civilScore = civilScore;
        this.tradeScore = tradeScore;
        this.guildScore = guildScore;
        this.scienceScore = scienceScore;
        updateTotalScore();
    }

    public SevenWondersScore(Player player) {
        this(player, 0, 0, 0, 0, 0, 0, 0);
    }

    public Player getPlayer() {
        return player;
    }

    public int getArmyScore() {
        return armyScore;
    }

    public int getGoldScore() {
        return goldScore;
    }

    public int getWonderScore() {
        return wonderScore;
    }

    public int getCivilScore() {
        return civilScore;
    }

    public int getTradeScore() {
        return tradeScore;
    }

    public int getGuildScore() {
        return guildScore;
    }

    public int getScienceScore() {
        return scienceScore;
    }

    public int getTotalScore() {
        return totalScore;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void setArmyScore(int armyScore) {
        this.armyScore = armyScore;
        updateTotalScore();
    }

    public void setGoldScore(int goldScore) {
        this.goldScore = goldScore;
        updateTotalScore();
    }

    public void setWonderScore(int wonderScore) {
        this.wonderScore = wonderScore;
        updateTotalScore();
    }

    public void setCivilScore(int civilScore) {
        this.civilScore = civilScore;
        updateTotalScore();
    }

    public void setTradeScore(int tradeScore) {
        this.tradeScore = tradeScore;
        updateTotalScore();
    }

    public void setGuildScore(int guildScore) {
        this.guildScore = guildScore;
        updateTotalScore();
    }

    public void setScienceScore(int scienceScore) {
        this.scienceScore = scienceScore;
        updateTotalScore();
    }

    public int getScore(SevenWondersGame.SCORE_CATEGORY category) {
        switch (category) {
            case ARMY:
                return getArmyScore();
            case WONDER:
                return getWonderScore();
            case GOLD:
                return getGoldScore();
            case CIVIL:
                return getCivilScore();
            case TRADE:
                return getTradeScore();
            case GUILD:
                return getGuildScore();
            case SCIENCE:
                return getScienceScore();
            default:
                throw new IllegalArgumentException("Score category unknown:: " + category);
        }
    }

    private void updateTotalScore() {
        this.totalScore = armyScore + goldScore + wonderScore + civilScore + tradeScore + guildScore + scienceScore;
    }

}
