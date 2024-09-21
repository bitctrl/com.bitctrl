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

package com.bitctrl;

/**
 * Enthält allgemeine Konstanten. Da der Java-Compiler die Referenz auf eine
 * Konstante durch das entsprechende Literal ersetzen darf, werden Konstanten
 * die sich später ändern könnten, als Objekte angelegt und nicht als Literale.
 * Entsprechende Konstanten sind mit einem Hinweis versehen.
 * 
 * @author BitCtrl Systems GmbH, Schumann
 */
public final class Constants {

	/** Anzahl Millisekunden pro Tag. */
	public static final long MILLIS_PER_DAY = 24 * 60 * 60 * 1000;

	/** Anzahl Millisekunden pro Stunde. */
	public static final long MILLIS_PER_HOUR = 60 * 60 * 1000;

	/** Anzahl Millisekunden pro Minute. */
	public static final long MILLIS_PER_MINUTE = 60 * 1000;

	/** Anzahl Millisekunden pro Sekunde. */
	public static final long MILLIS_PER_SECOND = 1000;

	/** Der systemabhängige Zeilenumbruch. */
	public static final String NL = System.getProperty("line.separator");

	/**
	 * Regular-Expression zur Überpruefung einer E-Mail Adresse (upper case).
	 * <p>
	 * <em>Hinweis:</em> Die Konstante kann nicht vom Compiler durch das
	 * entsprechende Literal ersetzt werden. Somit kann die Konstante auch bei
	 * Atualisierung verwendet werden ohne externen Quelltext neu zu übersetzen.
	 */
	public static final String REGEX_MAIL = String.valueOf(".+@.+\\..+");

	/**
	 * Ein leerer String.
	 * <p>
	 * <em>Hinweis</em>: Der Test mit {@code equals()} auf den Leerstring ist sehr
	 * ineffektiv, deswegen sollte bis Java 6 besser geprüft werden ob
	 * {@link String#length()} gleich 0 ist. Ab Java 6 kann für den Test auf den
	 * Leerstring {@link String#isEmpty()} verwendet werden.
	 */
	public static final String EMPTY_STRING = "";

	/** Faktor für Prozentrechnung. */
	public static final int PERCENT = 100;

	private Constants() {
		// Konstruktor verstecken
	}

}
