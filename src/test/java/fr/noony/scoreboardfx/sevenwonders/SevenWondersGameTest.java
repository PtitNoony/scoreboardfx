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
import fr.noony.gameutils.PlayerFactory;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author ahamon
 */
public class SevenWondersGameTest {

    private static Player playerA;
    private static Player playerB;
    private static Player playerC;
    private static Player playerD;

    @BeforeClass
    public static void setUpClass() {
        playerA = PlayerFactory.createPlayer("A", "a", "Aa");
        playerB = PlayerFactory.createPlayer("B", "b", "Bb");
        playerC = PlayerFactory.createPlayer("C", "c", "Cc");
        playerD = PlayerFactory.createPlayer("D", "d", "Dd");
    }

    /**
     * Test of addScore method, of class SevenWondersGame.
     */
    @Test
    public void testAddHasScore() {
        SevenWondersScore score = new SevenWondersScore(playerA);
        SevenWondersGame instance = SevenWondersGameFactory.createGame();
        instance.addScore(score);
        assertTrue(instance.hasPlayer(playerA));
        //
        instance.addScore(score);
        assertEquals(1, instance.getScores().size());
    }

    /**
     * Test of getId method, of class SevenWondersGame.
     */
    @Test
    public void testGetId() {
        SevenWondersGame instance1 = SevenWondersGameFactory.createGame();
        SevenWondersGame instance2 = SevenWondersGameFactory.createGame();
        int id1 = instance1.getId();
        int id2 = instance2.getId();
        assertNotEquals(id1, id2);
        assertTrue(id1 >= 0);
        assertTrue(id2 >= 0);
    }

    /**
     * Test of getScores method, of class SevenWondersGame.
     */
    @Test
    public void testGetScores() {
        SevenWondersScore score = new SevenWondersScore(playerA);
        SevenWondersGame instance = SevenWondersGameFactory.createGame();
        instance.addScore(score);
        assertTrue(instance.getScores() != null);
        assertTrue(instance.getScores().contains(score));
    }

    /**
     * Test of getPlayerScore method, of class SevenWondersGame.
     */
    @Test
    public void testGetPlayerScore() {
        SevenWondersScore scoreA = new SevenWondersScore(playerA);
        SevenWondersScore scoreB = new SevenWondersScore(playerB);
        SevenWondersGame instance = SevenWondersGameFactory.createGame();
        instance.addScore(scoreA);
        instance.addScore(scoreB);
        assertEquals(scoreA, instance.getPlayerScore(playerA));
        assertEquals(scoreB, instance.getPlayerScore(playerB));
        assertNull(instance.getPlayerScore(playerC));
    }

    /**
     * Test of getPlayerRanking method, of class SevenWondersGame.
     */
    @Test
    public void testGetPlayerRanking() {
        assertTrue(testRankingAllZeros());
        assertTrue(testRankingOneOneTwo());
        assertTrue(testRankingOneTwoTwoFour());
        assertTrue(testRankingOneTwoThreeFour());

    }

    private boolean testRankingAllZeros() {
        SevenWondersScore scoreA = new SevenWondersScore(playerA);
        SevenWondersScore scoreB = new SevenWondersScore(playerB);
        SevenWondersGame instance = SevenWondersGameFactory.createGame();
        instance.addScore(scoreA);
        instance.addScore(scoreB);
        assertEquals(1, instance.getPlayerRanking(playerA));
        assertEquals(1, instance.getPlayerRanking(playerB));
        return true;
    }

    private boolean testRankingOneOneTwo() {
        SevenWondersScore scoreA = new SevenWondersScore(playerA, 1, 0, 0, 0, 0, 0, 0);
        SevenWondersScore scoreB = new SevenWondersScore(playerB, 1, 0, 0, 0, 0, 0, 0);
        SevenWondersScore scoreC = new SevenWondersScore(playerC, 0, 0, 0, 0, 0, 0, 0);
        SevenWondersGame instance = SevenWondersGameFactory.createGame();
        instance.addScore(scoreA);
        instance.addScore(scoreB);
        instance.addScore(scoreC);
        assertEquals(1, instance.getPlayerRanking(playerA));
        assertEquals(1, instance.getPlayerRanking(playerB));
        assertEquals(3, instance.getPlayerRanking(playerC));
        return true;
    }

    private boolean testRankingOneTwoTwoFour() {
        SevenWondersScore scoreA = new SevenWondersScore(playerA, 10, 0, 0, 0, 0, 0, 0);
        SevenWondersScore scoreB = new SevenWondersScore(playerB, 1, 0, 0, 0, 0, 0, 0);
        SevenWondersScore scoreC = new SevenWondersScore(playerC, 1, 0, 0, 0, 0, 0, 0);
        SevenWondersScore scoreD = new SevenWondersScore(playerD, 0, 0, 0, 0, 0, 0, 0);
        SevenWondersGame instance = SevenWondersGameFactory.createGame();
        instance.addScore(scoreD);
        instance.addScore(scoreC);
        instance.addScore(scoreB);
        instance.addScore(scoreA);
        assertEquals(1, instance.getPlayerRanking(playerA));
        assertEquals(2, instance.getPlayerRanking(playerB));
        assertEquals(2, instance.getPlayerRanking(playerC));
        assertEquals(4, instance.getPlayerRanking(playerD));
        return true;
    }

    private boolean testRankingOneTwoThreeFour() {
        SevenWondersScore scoreA = new SevenWondersScore(playerA, 10, 0, 0, 0, 0, 0, 0);
        SevenWondersScore scoreB = new SevenWondersScore(playerB, 9, 0, 0, 0, 0, 0, 0);
        SevenWondersScore scoreC = new SevenWondersScore(playerC, 8, 0, 0, 0, 0, 0, 0);
        SevenWondersScore scoreD = new SevenWondersScore(playerD, 7, 0, 0, 0, 0, 0, 0);
        SevenWondersGame instance = SevenWondersGameFactory.createGame();
        instance.addScore(scoreD);
        instance.addScore(scoreA);
        instance.addScore(scoreC);
        instance.addScore(scoreB);
        assertEquals(1, instance.getPlayerRanking(playerA));
        assertEquals(2, instance.getPlayerRanking(playerB));
        assertEquals(3, instance.getPlayerRanking(playerC));
        assertEquals(4, instance.getPlayerRanking(playerD));
        return true;
    }

}
