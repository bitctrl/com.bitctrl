/*
 * Java Common Library
 * Copyright (c) 2008 BitCtrl Systems GmbH
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 3.0 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this library; if not, write to the Free Software Foundation, Inc.,
 * 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA.
 *
 * Contact Information:
 * BitCtrl Systems GmbH
 * Weißenfelser Straße 67
 * 04229 Leipzig
 * Phone: +49 341-490670
 * mailto: info@bitctrl.de
 */

package com.bitctrl.modell;

/**
 * Dies ist eine allgemeine Exception für Fehler bei der Arbeit mit DAO. In der
 * Regel kapselt eine {@link DAOException} eine andere Exception wie z.&nbsp;B.
 * {@link java.sql.SQLException}.
 * 
 * @author BitCtrl Systems GmbH, Schumann
 */
public class DAOException extends Exception {

	private static final long serialVersionUID = 1L;

	/**
	 * Der Standardkonstruktor tut nichts.
	 */
	public DAOException() {
		// nix
	}

	/**
	 * Initialisiert die Exception.
	 * 
	 * @param message
	 *            ein Meldungstext.
	 */
	public DAOException(final String message) {
		super(message);
	}

	/**
	 * Initialisiert die Exception.
	 * 
	 * @param cause
	 *            der Grund für die Exception.
	 */
	public DAOException(final Throwable cause) {
		super(cause);
	}

	/**
	 * Initialisiert die Exception.
	 * 
	 * @param message
	 *            ein Meldungstext.
	 * @param cause
	 *            der Grund für die Exception.
	 */
	public DAOException(final String message, final Throwable cause) {
		super(message, cause);
	}

}
