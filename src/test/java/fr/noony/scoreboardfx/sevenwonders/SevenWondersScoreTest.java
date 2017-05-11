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
import java.util.logging.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author ahamon
 */
public class SevenWondersScoreTest {

    private static final Logger LOG = Logger.getGlobal();

    private static Player player;

    public SevenWondersScoreTest() {
    }

    @BeforeClass
    public static void setUpClass() {
        player = PlayerFactory.createPlayer("A", "a", "Aa");
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of getPlayer method, of class SevenWondersScore.
     */
    @Test
    public void testGetPlayer() {
        SevenWondersScore instance = new SevenWondersScore(player);
        Player expResult = player;
        Player result = instance.getPlayer();
        assertEquals(expResult, result);
    }

    @Test
    public void testSimpleConstructor() {
        SevenWondersScore instance = new SevenWondersScore(player);
        assertEquals(0, instance.getArmyScore());
        assertEquals(0, instance.getGoldScore());
        assertEquals(0, instance.getWonderScore());
        assertEquals(0, instance.getCivilScore());
        assertEquals(0, instance.getTradeScore());
        assertEquals(0, instance.getGuildScore());
        assertEquals(0, instance.getScienceScore());
        assertEquals(0, instance.getTotalScore());
    }

    @Test
    public void testDefaultConstructor() {
        SevenWondersScore instance = new SevenWondersScore(player, 1, 2, 3, 4, 5, 6, 7);
        assertEquals(1, instance.getArmyScore());
        assertEquals(2, instance.getGoldScore());
        assertEquals(3, instance.getWonderScore());
        assertEquals(4, instance.getCivilScore());
        assertEquals(5, instance.getTradeScore());
        assertEquals(6, instance.getGuildScore());
        assertEquals(7, instance.getScienceScore());
        assertEquals(28, instance.getTotalScore());
    }

    @Test
    public void testFullConstructor() {
        SevenWondersScore instance;
        try {
            instance = new SevenWondersScore(null);
            fail("Creation with player null shall fail but created:: " + instance);
        } catch (Exception e) {
        }
        try {
            instance = new SevenWondersScore(player, SevenWondersScore.ARMY_MIN - 1, 0, 0, 0, 0, 0, 0);
            fail("Should not be able to set army score less than ARMY_MIN " + instance);
        } catch (Exception e) {
        }
        try {
            instance = new SevenWondersScore(player, SevenWondersScore.ARMY_MAX + 1, 0, 0, 0, 0, 0, 0);
            fail("Should not be able to set army score more than ARMY_MAX " + instance);
        } catch (Exception e) {
        }
        try {
            instance = new SevenWondersScore(player, 1, -1, 1, 1, 1, 1, 1);
            fail("Should not be able to set gold score less than 0 " + instance);
        } catch (Exception e) {
        }
        try {
            instance = new SevenWondersScore(player, 1, 1, -1, 1, 1, 1, 1);
            fail("Should not be able to set wonder score less than 0 " + instance);
        } catch (Exception e) {
        }
        try {
            instance = new SevenWondersScore(player, 1, 1, 1, -1, 1, 1, 1);
            fail("Should not be able to set civil score less than 0 " + instance);
        } catch (Exception e) {
        }
        try {
            instance = new SevenWondersScore(player, 1, 1, 1, 1, -1, 1, 1);
            fail("Should not be able to set trade score less than 0 " + instance);
        } catch (Exception e) {
        }
        try {
            instance = new SevenWondersScore(player, 1, 1, 1, 1, 1, -1, 1);
            fail("Should not be able to set guild score less than 0 " + instance);
        } catch (Exception e) {
        }
        try {
            instance = new SevenWondersScore(player, 1, 1, 1, 1, 1, 1, -1);
            fail("Should not be able to set science score less than 0 " + instance);
        } catch (Exception e) {
        }
    }

    /**
     * Test of setPlayer method, of class SevenWondersScore.
     */
    @Test
    public void testSetPlayer() {
        try {
            SevenWondersScore instance = new SevenWondersScore(null);
            fail("Creation with player null shall fail but created:: " + instance);
        } catch (Exception e) {
        }
        SevenWondersScore instance = new SevenWondersScore(player);
        Player otherPlayer = PlayerFactory.createPlayer("B", "b", "Bb");
        instance.setPlayer(otherPlayer);
        assertEquals(otherPlayer, instance.getPlayer());
    }

    /**
     * Test of setArmyScore method, of class SevenWondersScore.
     */
    @Test
    public void testSetArmyScore() {
        SevenWondersScore instance = new SevenWondersScore(player);
        instance.setArmyScore(SevenWondersScore.ARMY_MIN);
        instance.setArmyScore(SevenWondersScore.ARMY_MAX);
        try {
            instance.setArmyScore(SevenWondersScore.ARMY_MIN - 1);
            fail("Should not be able to set army score less than ARMY_MIN");
        } catch (Exception e) {
        }
        try {
            instance.setArmyScore(SevenWondersScore.ARMY_MAX + 1);
            fail("Should not be able to set army score more than ARMY_MAX");
        } catch (Exception e) {
        }
    }

    /**
     * Test of setGoldScore method, of class SevenWondersScore.
     */
    @Test
    public void testSetGoldScore() {
        SevenWondersScore instance = new SevenWondersScore(player);
        instance.setGoldScore(17);
        try {
            instance.setGoldScore(- 1);
            fail("Should not be able to set gold score less than 0");
        } catch (Exception e) {
        }
    }

    /**
     * Test of setWonderScore method, of class SevenWondersScore.
     */
    @Test
    public void testSetWonderScore() {
        SevenWondersScore instance = new SevenWondersScore(player);
        instance.setWonderScore(17);
        try {
            instance.setWonderScore(- 1);
            fail("Should not be able to set wonder score less than 0");
        } catch (Exception e) {
        }
    }

    /**
     * Test of setCivilScore method, of class SevenWondersScore.
     */
    @Test
    public void testSetCivilScore() {
        SevenWondersScore instance = new SevenWondersScore(player);
        instance.setCivilScore(17);
        try {
            instance.setCivilScore(- 1);
            fail("Should not be able to set civil score less than 0");
        } catch (Exception e) {
        }
    }

    /**
     * Test of setTradeScore method, of class SevenWondersScore.
     */
    @Test
    public void testSetTradeScore() {
        SevenWondersScore instance = new SevenWondersScore(player);
        instance.setTradeScore(17);
        try {
            instance.setTradeScore(- 1);
            fail("Should not be able to set trade score less than 0");
        } catch (Exception e) {
        }
    }

    /**
     * Test of setGuildScore method, of class SevenWondersScore.
     */
    @Test
    public void testSetGuildScore() {
        SevenWondersScore instance = new SevenWondersScore(player);
        instance.setGuildScore(17);
        try {
            instance.setGuildScore(- 1);
            fail("Should not be able to set guild score less than 0");
        } catch (Exception e) {
        }
    }

    /**
     * Test of setScienceScore method, of class SevenWondersScore.
     */
    @Test
    public void testSetScienceScore() {
        SevenWondersScore instance = new SevenWondersScore(player);
        instance.setScienceScore(17);
        try {
            instance.setScienceScore(- 1);
            fail("Should not be able to set science score less than 0");
        } catch (Exception e) {
        }
    }

}
