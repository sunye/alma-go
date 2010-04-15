// File BadGobanStateException.java
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
// along with this program;
//
// The above copyright notice and this permission notice shall be
// included in all copies or substantial portions of this program.
package fr.alma.atarigo.utils.exceptions;

/**
 *
 * @author judu
 */
public class BadGobanStateException extends Exception {

    /**
     * Exception = Bad Goban State.
     */
    public BadGobanStateException() {
    }

    /**
     * Exception = Bad Goban State.
     * @param message : message to display.
     */
    public BadGobanStateException(final String message) {
        super(message);
    }

    /**
     * Exception = Bad Goban State.
     * @param cause : cause of this exception.
     */
    public BadGobanStateException(final Throwable cause) {
        super(cause);
    }

    /**
     * Exception = Bad Goban State.
     * @param message : message to display.
     * @param cause : cause of this exception.
     */
    public BadGobanStateException(final String message, final Throwable cause) {
        super(message, cause);
    }
}


//~ Formatted by Jindent --- http://www.jindent.com

