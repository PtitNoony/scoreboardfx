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
import fr.noony.scoreboardfx.PlayerFactory;
import fr.noony.scoreboardfx.PlayerXmlUtils;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 *
 * @author Arnaud HAMON-KEROMEN
 */
public final class SevenWondersXMLLoader {

    private static final Logger LOG = Logger.getGlobal();

    private SevenWondersXMLLoader() {
        //private utility constructor
    }

    public static void loadFile(File file) {
        if (file != null) {
            //
            Document document;
            DocumentBuilderFactory builderFactory;
            builderFactory = DocumentBuilderFactory.newInstance();
            try {
                DocumentBuilder builder = builderFactory.newDocumentBuilder();
                InputSource source = new InputSource(file.getAbsolutePath());
                document = builder.parse(source);
                Element e = document.getDocumentElement();
                // Players
                NodeList playerGroups = e.getElementsByTagName(SevenWondersXMLSaver.PLAYER_GROUP);
                //TODO: test size
                parsePlayers((Element) playerGroups.item(0));
                // Games
                NodeList gamesGroups = e.getElementsByTagName(SevenWondersXMLSaver.SEVEN_WONDERS_GAME_GROUP);
                //TODO: test size
                parseGames((Element) gamesGroups.item(0));

            } catch (IOException | SAXException | ParserConfigurationException ex) {
                LOG.log(Level.SEVERE, "Exception while loading file {0} :: {1}", new Object[]{file, ex});
            }

        }
    }

    private static void parsePlayers(Element playerRootElement) {
        NodeList playerElements = playerRootElement.getElementsByTagName(PlayerXmlUtils.PLAYER_ELEMENT);
        for (int i = 0; i < playerElements.getLength(); i++) {
            PlayerXmlUtils.parsePlayer((Element) playerElements.item(i));
        }
    }

    private static void parseGames(Element gamesGroupElement) {
        NodeList gamesElements = gamesGroupElement.getElementsByTagName(SevenWondersXMLSaver.SEVEN_WONDERS_GAME);
        for (int i = 0; i < gamesElements.getLength(); i++) {
            parseSingleGame((Element) gamesElements.item(i));
        }
    }

    private static void parseSingleGame(Element element) {
        int gameID = Integer.parseInt(element.getAttribute(SevenWondersXMLSaver.SEVEN_WONDERS_GAME_ID));
        SevenWondersGame sevenWondersGame = SevenWondersGameFactory.getGamerFromID(gameID);
        //
        NodeList scores = element.getElementsByTagName(SevenWondersXMLSaver.SEVEN_WONDERS_SCORE);
        for (int i = 0; i < scores.getLength(); i++) {
            final SevenWondersScore score = parseScore((Element) scores.item(i));
            sevenWondersGame.addScore(score);
        }
    }

    private static SevenWondersScore parseScore(Element e) {
        final int armyScore = Integer.parseInt(e.getAttribute(SevenWondersXMLSaver.SEVEN_WONDERS_SCORE_ARMY));
        final int goldScore = Integer.parseInt(e.getAttribute(SevenWondersXMLSaver.SEVEN_WONDERS_SCORE_GOLD));
        final int wonderScore = Integer.parseInt(e.getAttribute(SevenWondersXMLSaver.SEVEN_WONDERS_SCORE_WONDER));
        final int civilScore = Integer.parseInt(e.getAttribute(SevenWondersXMLSaver.SEVEN_WONDERS_SCORE_CIVIL));
        final int scienceScore = Integer.parseInt(e.getAttribute(SevenWondersXMLSaver.SEVEN_WONDERS_SCORE_SCIENCE));
        final int tradeScore = Integer.parseInt(e.getAttribute(SevenWondersXMLSaver.SEVEN_WONDERS_SCORE_TRADE));
        final int guildScore = Integer.parseInt(e.getAttribute(SevenWondersXMLSaver.SEVEN_WONDERS_SCORE_GUILD));
        //
        final int playerID = Integer.parseInt(e.getAttribute(PlayerXmlUtils.PLAYER_ELEMENT));
        Player player = PlayerFactory.getPlayerFromID(playerID);
        //
        return new SevenWondersScore(player, armyScore, goldScore, wonderScore, civilScore, tradeScore, guildScore, scienceScore);
    }

}
