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

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Liest streambasiert ein CSV-File nach RFC&nbsp;4180.
 * 
 * <blockquote>The ABNF grammar [2] appears as follows:
 * <p>
 * <code>file = [header CRLF] record *(CRLF record) [CRLF]<br>
 * header = name *(COMMA name)<br>
 * record = field *(COMMA field)<br>
 * name = field<br>
 * field = (escaped / non-escaped)<br>
 * escaped = DQUOTE *(TEXTDATA / COMMA / CR / LF / 2DQUOTE) DQUOTE<br>
 * non-escaped = *TEXTDATA<br>
 * COMMA = %x2C<br>
 * CR = %x0D<br>
 * DQUOTE = %x22<br>
 * LF = %x0A<br>
 * CRLF = CR LF<br>
 * TEXTDATA = %x20-21 / %x23-2B / %x2D-7E</code></blockquote>
 * 
 * @author BitCtrl Systems GmbH, Falko Schumann
 */
public class CSVReader extends CSVProperties {

	private static final Character CR = '\r';
	private static final Character LF = '\n';
	private final Reader in;
	private String buffer = "";

	/**
	 * Verwendet einen {@link java.io.InputStream} als Quelle für CSV-Daten.
	 * 
	 * @param in      ein Eingabestrom, z.&nbsp;B. von einem File.
	 * @param charset der zu verwendende Zeichensatz.
	 * @throws UnsupportedEncodingException wenn der angegebene Zeichsatz nicht
	 *                                      unterstützt wird.
	 */
	public CSVReader(final InputStream in, final String charset) throws UnsupportedEncodingException {
		this.in = new InputStreamReader(in, charset);
	}

	/**
	 * Parst einen String als CSV-Daten.
	 * 
	 * @param in ein String, z.&nbsp; der Inhalt eines Files.
	 */
	public CSVReader(final String in) {
		this.in = new StringReader(in);
	}

	/**
	 * Liest den nächsten Datensatz aus der Datei.
	 * 
	 * @return der gelesene Datensatz.
	 * @throws IOException bei Fehlern vom Lesen aus dem Eingabestrom.
	 */
	public List<String> next() throws IOException {
		List<String> record = new ArrayList<>();
		boolean eof = false;
		boolean escaped = false;
		char lastSymbol;
		char currentSymbol = 0;

		while (!eof) {
			final int i;

			i = in.read();
			lastSymbol = currentSymbol;
			currentSymbol = (char) i;

			// Prüfen ob Ende des Eingabestroms erreicht wurde.
			eof = i == -1;
			if (eof) {
				if (buffer.length() > 0) {
					if (buffer.endsWith(CR.toString())) {
						buffer = buffer.substring(0, buffer.length() - 1);
					}
					record.add(buffer);
					buffer = "";
				}
			} else if (!escaped) {
				if (lastSymbol == getEscape() && currentSymbol == getEscape()) {
					buffer += getEscape();
					escaped = true;
				} else {
					if (currentSymbol == getDelimiter()) {
						// Ende des Felds erreicht
						record.add(buffer);
						buffer = "";
					} else if (currentSymbol == LF) {
						// Ende des Records erreicht (Unix, nicht Windows)
						if (lastSymbol != CR) {
							record.add(buffer);
							buffer = "";
							eof = true;
						}
					} else {
						buffer += currentSymbol;
					}

					if (lastSymbol == CR) {
						if (currentSymbol == LF) {
							// Ende des Records erreicht (Windows)
							record.add(buffer.substring(0, buffer.length() - 1));
							buffer = "";
						} else {
							// Ende des Records erreicht (Mac OS)
							record.add(buffer.substring(0, buffer.length() - 2));
							buffer = String.valueOf(currentSymbol);
						}
						eof = true;
					}

					if (buffer.length() == 0 && currentSymbol == getEscape()
							|| buffer.length() == 1 && buffer.charAt(0) == getEscape()) {
						buffer = "";
						escaped = true;
					}
				}
			} else {
				// Innerhalb eines escapten Felds ...
				if (currentSymbol != getEscape()) {
					buffer += currentSymbol;
				} else {
					escaped = false;
				}
			}
			if (eof && getHeader().isEmpty() && isFirstLineHeader()) {
				setHeader(record);
				record = new ArrayList<>();
				eof = false;
			}
		}

		while (record.isEmpty() && !eof) {
			record = next();
		}

		return record;
	}

}
