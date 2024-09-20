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

import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.nio.charset.Charset;
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
public class CSVWriter extends CSVProperties {

	private final Writer out;

	/**
	 * Verwendet einen {@link java.io.InputStream} als Quelle für CSV-Daten.
	 * 
	 * @param out ein Eingabestrom, z.&nbsp;B. von einem File.
	 */
	public CSVWriter(final OutputStream out) {
		try {
			this.out = new OutputStreamWriter(out, Charset.defaultCharset().name());
		} catch (final UnsupportedEncodingException e) {
			throw new IllegalStateException("Kann nicht passieren", e);
		}
	}

	/**
	 * Parst einen String als CSV-Daten.
	 * 
	 * @param out ein String, z.&nbsp; der Inhalt eines Files.
	 */
	public CSVWriter(final StringWriter out) {
		this.out = out;
	}

	public CSVWriter(final FileWriter out2) {
		out = out2;
	}

	/**
	 * Schreibt die Liste als einen Datensatz in das File. Jedes Listenelement wird
	 * als ein Feld interpretiert.
	 * 
	 * @param record der zu schreibende Datensatz.
	 * @throws IOException bei Fehlern beim Schreiben ins File.
	 */
	public void write(final List<String> record) throws IOException {
		write(record.toArray(new String[record.size()]));
	}

	/**
	 * Schreibt das Feld als einen Datensatz in das File. Jedes Feldelement wird als
	 * eine (CSV-)Feld interpretiert.
	 * 
	 * @param record der zu schreibende Datensatz.
	 * @throws IOException bei Fehlern beim Schreiben ins File.
	 */
	public void write(final String... record) throws IOException {
		for (final String field : record) {
			out.write(getEscape() + field + getEscape() + getDelimiter());
		}
		out.write(getNewline());
	}

	/**
	 * Schreibt das Feld als einen Datensatz in das File. Jedes Feldelement wird als
	 * eine (CSV-)Feld interpretiert.
	 * 
	 * @param record der zu schreibende Datensatz.
	 * @throws IOException bei Fehlern beim Schreiben ins File.
	 */
	public void writeOhneEscape(final String... record) throws IOException {
		for (final String field : record) {
			out.write(field + getDelimiter());
		}
		out.write(getNewline());
	}

}
