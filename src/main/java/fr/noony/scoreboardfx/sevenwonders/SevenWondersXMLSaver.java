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

import fr.noony.gameutils.PlayerFactory;
import fr.noony.gameutils.PlayerXmlUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Arnaud HAMON-KEROMEN
 */
public class SevenWondersXMLSaver {

    public static final String SEVEN_WONDERS_TAG = "7_WONDERS";

    public static final String PLAYER_GROUP = "PLAYERS";

    public static final String SEVEN_WONDERS_GAME_GROUP = "7_WONDERS_GAMES";
    public static final String SEVEN_WONDERS_GAME = "7_wonders_game";
    public static final String SEVEN_WONDERS_GAME_ID = "7_wonders_gameID";
    public static final String SEVEN_WONDERS_SCORE = "7_wonders_score";
    public static final String SEVEN_WONDERS_SCORE_ARMY = "7w_s_army";
    public static final String SEVEN_WONDERS_SCORE_GOLD = "7w_s_gold";
    public static final String SEVEN_WONDERS_SCORE_WONDER = "7w_s_wonder";
    public static final String SEVEN_WONDERS_SCORE_CIVIL = "7_wonders_score";
    public static final String SEVEN_WONDERS_SCORE_SCIENCE = "7w_s_science";
    public static final String SEVEN_WONDERS_SCORE_TRADE = "7w_s_trade";
    public static final String SEVEN_WONDERS_SCORE_GUILD = "7w_s_guild";

    private static final Logger LOG = Logger.getGlobal();

    private SevenWondersXMLSaver() {
        //empty constructor
    }

    public static boolean save(File destFile) {
        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            // root elements
            Document doc = docBuilder.newDocument();
            Element rootElement = doc.createElement(SEVEN_WONDERS_TAG);
            doc.appendChild(rootElement);

            // save players
            Element playerGroupElement = doc.createElement(PLAYER_GROUP);
            rootElement.appendChild(playerGroupElement);
            PlayerFactory.getCreatedPlayers().forEach(player -> PlayerXmlUtils.createPlayerXmlElement(doc, playerGroupElement, player));

            // save games
            Element gameGroupElement = doc.createElement(SEVEN_WONDERS_GAME_GROUP);
            rootElement.appendChild(gameGroupElement);
            SevenWondersGameFactory.getGames().forEach(game -> gameGroupElement.appendChild(getGameXMLElement(doc, game)));
            //
            rootElement.normalize();
            // write the content into xml file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(destFile);
            transformer.transform(source, result);
        } catch (ParserConfigurationException | TransformerException ex) {
            LOG.log(Level.SEVERE, " Exception while exporting atc geography :: {0}", ex);
            return false;
        }
        return true;
    }

    private static Node getGameXMLElement(Document document, SevenWondersGame game) {
        Element gameElement = document.createElement(SEVEN_WONDERS_GAME);
        gameElement.setAttribute(SEVEN_WONDERS_GAME_ID, Integer.toString(game.getId()));
        game.getScores().forEach(s -> gameElement.appendChild(getScoreXMLElement(document, s)));
        return gameElement;
    }

    private static Node getScoreXMLElement(Document document, SevenWondersScore score) {
        Element scoreElement = document.createElement(SEVEN_WONDERS_SCORE);
        scoreElement.setAttribute(PlayerXmlUtils.PLAYER_ELEMENT, Integer.toString(score.getPlayer().getID()));
        scoreElement.setAttribute(SEVEN_WONDERS_SCORE_ARMY, Integer.toString(score.getArmyScore()));
        scoreElement.setAttribute(SEVEN_WONDERS_SCORE_GOLD, Integer.toString(score.getGoldScore()));
        scoreElement.setAttribute(SEVEN_WONDERS_SCORE_WONDER, Integer.toString(score.getWonderScore()));
        scoreElement.setAttribute(SEVEN_WONDERS_SCORE_CIVIL, Integer.toString(score.getCivilScore()));
        scoreElement.setAttribute(SEVEN_WONDERS_SCORE_SCIENCE, Integer.toString(score.getScienceScore()));
        scoreElement.setAttribute(SEVEN_WONDERS_SCORE_TRADE, Integer.toString(score.getTradeScore()));
        scoreElement.setAttribute(SEVEN_WONDERS_SCORE_GUILD, Integer.toString(score.getGuildScore()));
        return scoreElement;
    }

}
