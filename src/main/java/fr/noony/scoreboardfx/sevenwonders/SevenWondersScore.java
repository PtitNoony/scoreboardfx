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

/**
 *
 * @author Arnaud HAMON-KEROMEN
 */
public class SevenWondersScore {

    public static final int ARMY_MIN = -6;
    public static final int ARMY_MAX = 18;

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
        if (player == null) {
            throw new IllegalArgumentException("Cannot create a score with a player null");
        }
        this.player = player;
        if (!isArmyScoreValid(armyScore)) {
            throw new IllegalArgumentException("Army score shall be a value between " + ARMY_MIN + " and " + ARMY_MAX + " but was " + armyScore);
        }
        this.armyScore = armyScore;
        if (goldScore < 0) {
            throw new IllegalArgumentException("Gold score shall be posotive but was " + goldScore);
        }
        this.goldScore = goldScore;
        if (wonderScore < 0) {
            throw new IllegalArgumentException("Wonder score shall be posotive but was " + wonderScore);
        }
        this.wonderScore = wonderScore;
        if (civilScore < 0) {
            throw new IllegalArgumentException("Civil score shall be posotive but was " + civilScore);
        }
        this.civilScore = civilScore;
        if (tradeScore < 0) {
            throw new IllegalArgumentException("Trade score shall be posotive but was " + tradeScore);
        }
        this.tradeScore = tradeScore;
        if (guildScore < 0) {
            throw new IllegalArgumentException("Guild score shall be posotive but was " + guildScore);
        }
        this.guildScore = guildScore;
        if (scienceScore < 0) {
            throw new IllegalArgumentException("Science score shall be posotive but was " + scienceScore);
        }
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
        if (player == null) {
            throw new IllegalArgumentException("Player cannot be null");
        }
        this.player = player;
    }

    public void setArmyScore(int armyScore) {
        if (!isArmyScoreValid(armyScore)) {
            throw new IllegalArgumentException("Army score shall be a value between " + ARMY_MIN + " and " + ARMY_MAX + " but was " + armyScore);
        }
        this.armyScore = armyScore;
        updateTotalScore();
    }

    public void setGoldScore(int goldScore) {
        if (goldScore < 0) {
            throw new IllegalArgumentException("Gold score shall be posotive but was " + goldScore);
        }
        this.goldScore = goldScore;
        updateTotalScore();
    }

    public void setWonderScore(int wonderScore) {
        if (wonderScore < 0) {
            throw new IllegalArgumentException("Civil score shall be posotive but was " + civilScore);
        }
        this.wonderScore = wonderScore;
        updateTotalScore();
    }

    public void setCivilScore(int civilScore) {
        if (civilScore < 0) {
            throw new IllegalArgumentException("Civil score shall be posotive but was " + civilScore);
        }
        this.civilScore = civilScore;
        updateTotalScore();
    }

    public void setTradeScore(int tradeScore) {
        if (tradeScore < 0) {
            throw new IllegalArgumentException("Guild score shall be posotive but was " + guildScore);
        }
        this.tradeScore = tradeScore;
        updateTotalScore();
    }

    public void setGuildScore(int guildScore) {
        if (guildScore < 0) {
            throw new IllegalArgumentException("Guild score shall be posotive but was " + guildScore);
        }
        this.guildScore = guildScore;
        updateTotalScore();
    }

    public void setScienceScore(int scienceScore) {
        if (scienceScore < 0) {
            throw new IllegalArgumentException("Science score shall be posotive but was " + scienceScore);
        }
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

    private boolean isArmyScoreValid(int score) {
        return score >= ARMY_MIN && score <= ARMY_MAX;
    }

}
