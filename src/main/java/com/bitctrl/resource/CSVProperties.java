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

package com.bitctrl.resource;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Fasst die Einstellungen für eine CSV-Datei zusammen. *
 * 
 * @author BitCtrl Systems GmbH, Falko Schumann
 */
public class CSVProperties {

	private char delimiter = ',';
	private char escape = '"';
	private String newline = "\r\n";
	private boolean firstLineHeader = false;
	private List<String> headers = new ArrayList<>();

	/**
	 * Gibt das Trennzeichen der Spalten zurück.
	 * 
	 * @return der Trennzeichen.
	 */
	public char getDelimiter() {
		return delimiter;
	}

	/**
	 * Legt das Trennzeichen für Spalten fest.
	 * 
	 * @param delimiter das Trennzeichen.
	 */
	public void setDelimiter(final char delimiter) {
		this.delimiter = delimiter;
	}

	/**
	 * Gibt das Zeichen zurück, mit dem Felder optional eingeschlossen sein können.
	 * 
	 * @return das Escapezeichen.
	 */
	public char getEscape() {
		return escape;
	}

	/**
	 * Legt fest mit welchem Zeichen Felder optional eingeschlossen sein können.
	 * 
	 * @param escape das Escapezeichen.
	 */
	public void setEscape(final char escape) {
		this.escape = escape;
	}

	/**
	 * Flag, ob die erste Zeile als Spaltenüberschrift interpretiert wird.
	 * 
	 * @return {@code true}, wenn die erste Zeile die Spaltenüberschriften enthält.
	 */
	public boolean isFirstLineHeader() {
		return firstLineHeader;
	}

	/**
	 * Flag, ob die erste Zeile als Spaltenüberschrift interpretiert wird.
	 * 
	 * @param firstLineHeader {@code true}, wenn die erste Zeile die
	 *                        Spaltenüberschriften enthält.
	 */
	public void setFirstLineHeader(final boolean firstLineHeader) {
		this.firstLineHeader = firstLineHeader;
	}

	/**
	 * Gibt die Spaltenüberschriften zurück.
	 * 
	 * @return die Spaltenüberschrifen.
	 */
	public List<String> getHeader() {
		return Collections.unmodifiableList(headers);
	}

	/**
	 * Legt die Spaltenüberschriften fest.
	 * 
	 * @param header die Spaltenüberschrifen.
	 */
	public void setHeader(final List<String> header) {
		this.headers = new ArrayList<>(header);
	}

	/**
	 * Bestimmt zu einer Spaltenüberschrift den Spaltenindex.
	 * 
	 * @param header der gesuchte Spaltenname.
	 * @return der dazugehörige Spaltenindex.
	 */
	public int getColumnIndex(final String header) {
		return headers.indexOf(header);
	}

	/**
	 * Bestimmt zu einem Spaltenindex die Spaltenüberschrift.
	 * 
	 * @param index der gesuchte Spaltenindex.
	 * @return der dazugehörige Spaltenname.
	 */
	public String getHeader(final int index) {
		return headers.get(index);
	}

	/**
	 * Gibt die zu verwendende Zeichenkette für den Zeilenumbruch zurück. Funktionen
	 * die CSV-Daten lesen, sollten sich tolerant gegenüber dem tatsächlichen
	 * Zeilenumbruch verhalten. Funktionen die CSV-Daten schreiben, müssen den
	 * angegebenen Zeilenumbruch verwenden.
	 * 
	 * @return der Zeilenumbruch.
	 */
	public String getNewline() {
		return newline;
	}

	/**
	 * Legt den zu verwendenden Zeilenumbruch fest.
	 * 
	 * @param newline der gewünschte Zeilenumbruch.
	 * @see #getNewline()
	 */
	public void setNewline(final String newline) {
		this.newline = newline;
	}

	@Override
	public String toString() {
		String s;

		s = getClass().getName() + "[";
		s += "delimiter=" + delimiter;
		s += ", escape=" + escape;
		s += ", newline=" + newline;
		s += ", firstLineHeader=" + firstLineHeader;
		s += ", headers=" + headers;
		s += "]";

		return s;
	}

}
