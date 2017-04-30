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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 *
 * @author ahamon
 */
public class SevenWondersGameFactory {

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
        final SevenWondersGame game = new SevenWondersGame(); // TODO: ID
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

}
