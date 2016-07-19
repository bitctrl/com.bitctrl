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

import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * Erlaubt das Lesen und Schreiben von CSV-Dateien.
 * <p>
 * <em>Hinweis:</em> Die Klasse ist noch nicht vollständig. Folgende
 * Möglichkeiten gibt es bereits:
 * <ul>
 * <li>Erste Zeile kann Spaltenüberschriften enthalten.</li>
 * <li>Jede Zeile wird durch ein Newline (\n, \r oder \r\n) beendet.</li>
 * <li>Der Trenner für die Spalten ist frei wählbar, Standard ist
 * {@link CSVTable#DEFAULT_DELIMITER} .</li>
 * <li>Die Spalten können in bestimmten Datentypen abgerufen werden.</li>
 * </ul>
 * 
 * @author BitCtrl Systems GmbH, Falko Schumann
 * 
 * TODO Schreiben implementieren 
 * TODO Weitere CSV-Eigenschaften hinzufügen
 */
public class CSVTable {

	/** Der Standardtrenner der Spalten ist "{@link CSVTable#DEFAULT_DELIMITER}". */
	public static final String DEFAULT_DELIMITER = String.valueOf(",");
	private final Pattern newlinePattern = Pattern.compile("\\n|\\r|(\\r\\n)");
	private final Scanner scanner;
	private final boolean firstLineIsHeader;
	private List<String> header;
	private String delimiter = DEFAULT_DELIMITER;
	private Scanner lineScanner;
	private long lineCounter = 0;

	/**
	 * Öffnet eine CSV-Datei unter Verwendung der Standardeinstellungen.
	 * 
	 * @param in
	 *            der Eingabestrom.
	 * @param firstLineIsHeader
	 *            {@code true}, wenn die erste Zeile die Spaltenüberschriften
	 *            enthält.
	 */
	public CSVTable(final InputStream in, final boolean firstLineIsHeader) {
		scanner = new Scanner(in, Charset.defaultCharset().name());
		scanner.useDelimiter(newlinePattern);
		this.firstLineIsHeader = firstLineIsHeader;
	}

	/**
	 * Verwendet einen String als CSV-"Datei".
	 * 
	 * @param source
	 *            die Eingangsdaten.
	 * @param firstLineIsHeader
	 *            {@code true}, wenn die erste Zeile die Spaltenüberschriften
	 */
	public CSVTable(final String source, final boolean firstLineIsHeader) {
		scanner = new Scanner(source);
		scanner.useDelimiter(newlinePattern);
		this.firstLineIsHeader = firstLineIsHeader;
	}

	/**
	 * Flag, ob die erste Zeile als Spaltenüberschriften interpretiert wird.
	 * 
	 * @return {@code true}, wenn es die Spaltenüberschriften gibt.
	 * @see #getHeader()
	 */
	public boolean isFirstLineIsHeader() {
		return firstLineIsHeader;
	}

	/**
	 * Gibt das Trennzeichen der Spalten zurück.
	 * 
	 * @return das Trennzeichen.
	 */
	public String getDelimiter() {
		return delimiter;
	}

	/**
	 * Gibt die Spaltenüberschriften als Liste zurück. Wurden keine
	 * Spaltenüberschriften definiert ist die Liste leer.
	 * 
	 * @return die Spaltenüberschriften.
	 */
	public List<String> getHeader() {
		setHeader();
		return header;
	}

	/**
	 * Prüft ob die aktuelle Zeile noch weitere Spalten enthält.
	 * 
	 * @return {@code false}, wenn das Zeilenende erreicht ist.
	 */
	public boolean hasNext() {
		return lineScanner.hasNext();
	}

	/**
	 * Gibt die aktuelle Spalte als {@code String} zurück und rückt den
	 * Spaltencursor weiter.
	 * 
	 * @return der Spalteninhalt.
	 */
	public String next() {
		return lineScanner.next();
	}

	/**
	 * Gibt die aktuelle Spalte als {@code boolean} zurück und rückt den
	 * Spaltencursor weiter.
	 * 
	 * @return der Spalteninhalt.
	 */
	public boolean nextBoolean() {
		return lineScanner.nextBoolean();
	}

	/**
	 * Gibt die aktuelle Spalte als {@code double} zurück und rückt den
	 * Spaltencursor weiter.
	 * 
	 * @return der Spalteninhalt.
	 */
	public double nextDouble() {
		return lineScanner.nextDouble();
	}

	/**
	 * Gibt die aktuelle Spalte als {@code int} zurück und rückt den
	 * Spaltencursor weiter.
	 * 
	 * @return der Spalteninhalt.
	 */
	public int nextInt() {
		return lineScanner.nextInt();
	}

	/**
	 * Setzt den Zeilencursor auf die nächste Zeile.
	 * 
	 * @return {@code false}, wenn das Dateiende erreicht wurde.
	 */
	public boolean nextLine() {
		setHeader();
		if (scanner.hasNextLine()) {
			lineScanner = new Scanner(scanner.nextLine());
			lineScanner.useDelimiter(delimiter);
			++lineCounter;
			return true;
		}
		return false;
	}

	/**
	 * Gibt die aktuelle Spalte als {@code long} zurück und rückt den
	 * Spaltencursor weiter.
	 * 
	 * @return der Spalteninhalt.
	 */
	public long nextLong() {
		return lineScanner.nextLong();
	}

	/**
	 * Legt das Trennzeichen der Spalten fest.
	 * 
	 * @param delimiter
	 *            ein Trennzeichen.
	 */
	public void setDelimiter(final String delimiter) {
		this.delimiter = delimiter;
	}

	private void setHeader() {
		if (isFirstLineIsHeader() && header == null) {
			header = new ArrayList<String>();
			if (nextLine()) {
				while (hasNext()) {
					header.add(next());
				}
			}
		}
	}

}
