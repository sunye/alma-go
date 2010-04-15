/**
 * File {GameOptions.java}
 * Last commited $Date$
 * By $Author$
 * Revision $Rev$
 *
 * Copyright (C) 2010 Clotilde Massot & Julien Durillon
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program;
 *
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of this program.
 */
package fr.alma.atarigo.ihm;

/**
 *
 * @author judu
 */
public class GameOptions {

    /**
     * Number of players selected.
     */
    private int nbPlayer;

    /**
     * Constructor.
     */
    public GameOptions() {
    }

    /**
     * Getter nbPlayer.
     * @return nbPlayer
     */
    public final int getNbPlayer() {
        return nbPlayer;
    }

    /**
     * Setter nbPlayer.
     * @param nb : new nbPlayer
     */
    public final void setNbPlayer(final int nb) {
        this.nbPlayer = nb;
    }
}