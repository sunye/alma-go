// File Stone.java
// Last commited $Date$
// By $Author$
// Revision $Rev$
//
// Copyright (C) 2010 Clotilde Massot & Julien Durillon
//
// This program is free software; you can redistribute it and/or
// modify it under the terms of the GNU General Public License
// as published by the Free Software Foundation; either version 2
// of the License, or (at your option) any later version.
//
// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.
//
// You should have received a copy of the GNU General Public License
// along with this program; if not, write to the Free Software
// Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
//
// The above copyright notice and this permission notice shall be
// included in all copies or substantial portions of this program.

package fr.alma.atarigo.utils;


public class Stone {
    private short   column;
    private PionVal couleur;
    private short   line;

    public Stone(PionVal couleur2, int realLigne, int realCol) {
        this.couleur = couleur2;
        this.column  = (short) realCol;
        this.line    = (short) realLigne;
    }

    public Stone(PionVal couleur, short ligne, short colonne) {
        super();
        this.couleur = couleur;
        this.column  = colonne;
        this.line    = ligne;
    }

    public short getColumn() {
        return column;
    }

    public short getLine() {
        return line;
    }

    @Override
    public int hashCode() {
        return (13 * column + line) * ((couleur == null)
                ? 0
                : couleur.valeur());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null) {
            return false;
        }

        if (getClass() != obj.getClass()) {
            return false;
        }

        Stone other = (Stone) obj;

        if (column != other.column) {
            return false;
        }

        if (couleur == null) {
            if (other.couleur != null) {
                return false;
            }
        } else if (!couleur.equals(other.couleur)) {
            return false;
        }

        if (line != other.line) {
            return false;
        }

        return true;
    }

    @Override
    public String toString() {
        return "Stone(line:" + line + ",col:" + column + ",c:" + couleur + ")";
    }

    public PionVal getCouleur() {
        return couleur;
    }
}


//~ Formatted by Jindent --- http://www.jindent.com
