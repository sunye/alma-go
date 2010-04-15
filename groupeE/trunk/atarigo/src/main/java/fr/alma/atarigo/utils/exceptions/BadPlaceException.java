// File BadPlaceException.java
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
 * @author judu
 */
public class BadPlaceException extends Exception {

    /**
     * Constant = serialVersionUID.
     */
    private static final long serialVersionUID = -7651675431627421683L;

    /**
     * Exception = Bad place.
     */
    public BadPlaceException() {
    }

    /**
     * Exception = Bad place.
     * @param message : message to display.
     */
    public BadPlaceException(final String message) {
        super(message);
    }

    /**
     * Exception = Bad place.
     * @param cause : cause of this exception.
     */
    public BadPlaceException(final Throwable cause) {
        super(cause);
    }

    /**
     * Exception = Bad place.
     * @param message : message to display.
     * @param cause : cause of this exception.
     */
    public BadPlaceException(final String message, final Throwable cause) {
        super(message, cause);
    }
}


//~ Formatted by Jindent --- http://www.jindent.com

