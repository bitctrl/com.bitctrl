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

package com.bitctrl.text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.bitctrl.Constants;

/**
 * Stellt Methoden zum Prüfen von Bedingungen an Textvariablen bereit.
 * 
 * @author BitCtrl Systems GmbH, Falko Schumann
 */
public final class CheckText {

	/**
	 * Prüft ob im String ein Zeilenumbruch enthalten sind. Zeilenumbruch: \n
	 * und \r
	 * 
	 * @param s
	 *            Zu testender Text
	 * @return {@code true}, wenn ein Zeilenumbruch enthalten sind
	 */
	public static boolean containsLineBreak(final String s) {
		if (s == null) {
			throw new NullPointerException();
		}

		for (final Character z : s.toCharArray()) {
			if (z.equals('\n') || z.equals('\r')) {
				return true;
			}
		}

		return false;
	}

	/**
	 * Prüft ob im String Leerzeichen enthalten sind. Als Leerzeichen gilt neben
	 * dem eigentlichen Leerzeichen auch Zeilenumbrüche und Tabulatoren.
	 * 
	 * @param s
	 *            Zu testender Text
	 * @return {@code true}, wenn Leerzeichen enthalten sind
	 * @see java.lang.Character#isWhitespace(char)
	 */
	public static boolean containsWhitespace(final String s) {
		if (s == null) {
			throw new NullPointerException();
		}

		for (final char z : s.toCharArray()) {
			if (Character.isWhitespace(z)) {
				return true;
			}
		}

		return false;
	}

	/**
	 * Testet ob der String {@code null} oder ein Leerstring ist. Strings am
	 * Anfang und Ende des Strings werden vor dem Test entfernt.
	 * 
	 * @param s
	 *            Ein String
	 * @return {@code true}, wenn der String druckbar bzw. anzeigbar ist
	 */
	public static boolean isPrintable(final String s) {
		if (s == null) {
			return false;
		}

		final String t = s.trim();

		if (t.equals("")) {
			return false;
		}

		return true;
	}

	/**
	 * Sucht den übergebenen Ausdruck in dem übergebenen String. Wenn der String
	 * oder Ausdruck gleich {@code null} sind, ist das Ergebnis immer {@code
	 * true}. Im Ausdruck nachdem gesucht wird, kann als Platzhalter der Stern
	 * {@code *} verwendet werden.
	 * 
	 * @param expr
	 *            der Ausdruck.
	 * @param str
	 *            der String in dem gesucht werden soll.
	 * @return {@code true}, wenn der Ausdruck gefunden wurde, der Ausdruck oder
	 *         String {@code null} ist, sonst {@code false}.
	 */
	public static final boolean matches(final String expr, final String str) {
		if (expr == null || str == null || expr.trim().length() <= 0) {
			return true;
		}

		final String tExpr = expr.trim().toLowerCase();
		final String tStr = str.trim().toLowerCase();

		final List<String> searchStrings = new ArrayList<String>(Arrays
				.asList(tExpr.toLowerCase().split("\\*")));

		// Leerstring wenn Platzhalter am Ende, da von String.split() ignoriert
		if (tExpr.endsWith("*")) {
			searchStrings.add(Constants.EMPTY_STRING);
		}

		boolean result = tStr.startsWith(searchStrings.get(0))
				&& tStr.endsWith(searchStrings.get(searchStrings.size() - 1));

		if (result) {
			for (final String arg : searchStrings) {
				if (!tStr.contains(arg)) {
					result = false;
					break;
				}
			}
		}

		return result;
	}

	private CheckText() {
		// Konstruktor verstecken
	}

}
