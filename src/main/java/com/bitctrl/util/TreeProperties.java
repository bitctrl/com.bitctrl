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

package com.bitctrl.util;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Date;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;
import java.util.SortedSet;
import java.util.Stack;
import java.util.TreeSet;

import com.bitctrl.resource.WritableConfiguration;

/**
 * Erweitert die Properties um die Fähigkeit mit Gruppen und Feldern umzugehen.
 * 
 * @author BitCtrl Systems GmbH, Falko Schumann
 */
public class TreeProperties extends Properties implements WritableConfiguration {

	private class Group {

		private final String name;
		private int index;
		private int arraySize;

		/**
		 * Erzeugt eine Gruppe.
		 * 
		 * @param name
		 *            der Name der Gruppe
		 */
		public Group(final String name) {
			this.name = name;
			index = -1;
			arraySize = -1;
		}

		/**
		 * Erzeugt ein Feld.
		 * 
		 * @param name
		 *            der Name des Feldes
		 * @param guessArraySize
		 *            {@code true}, wenn die Feldgröße mit gespeichert wird.
		 */
		public Group(final String name, final boolean guessArraySize) {
			this.name = name;
			index = 0;
			arraySize = guessArraySize ? 0 : -1;
		}

		/**
		 * Gibt die aktuelle Feldgröße zurück.
		 * 
		 * @return die aktuelle Größe des Feldes oder {@code -1}, wenn die
		 *         Feldgröße unbekannt ist oder es sich um eine Gruppe handelt.
		 */
		public int arraySizeGuess() {
			return arraySize;
		}

		/**
		 * Gibt den aktuellen Index zurück.
		 * 
		 * @return der Index.
		 */
		public int getIndex() {
			return index;
		}

		/**
		 * Gibt den Namen der Gruppe zurück.
		 * 
		 * @return der Gruppenname.
		 */
		public String getName() {
			return name;
		}

		/**
		 * Fragt, ob dies ein Feld ist.
		 * 
		 * @return {@code true}, wenn es ein Feld ist und {@code false}, wenn es
		 *         ein einfache Gruppe ist.
		 */
		public boolean isArray() {
			return index != -1;
		}

		/**
		 * Legt den aktuellen Feldindex fest.
		 * 
		 * @param index
		 *            der Index.
		 */
		public void setIndex(final int index) {
			this.index = index + 1;
			if (arraySize != -1 && index > arraySize) {
				arraySize = index;
			}
		}

		@Override
		public String toString() {
			String s = name + ".";

			if (index > 0) {
				s += index + ".";
			}

			return s;
		}

	}

	private static final long serialVersionUID = 1L;
	private static final char[] HEXDIGIT = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E',
			'F' };

	private static char toHex(final int nibble) {
		return HEXDIGIT[(nibble & 0xF)];
	}

	private static void writeln(final BufferedWriter bw, final String s) throws IOException {
		bw.write(s);
		bw.newLine();
	}

	private final Stack<Group> stack = new Stack<Group>();

	private String trace = "";

	/**
	 * Erzeugt eine leere Properties-Liste.
	 */
	public TreeProperties() {
		// nix
	}

	/**
	 * Erzeugt eine leere Properties-Liste mit Standardwerten.
	 * 
	 * @param defaults
	 *            die Standardwerte.
	 */
	public TreeProperties(final TreeProperties defaults) {
		super(defaults);
	}

	public void beginGroup(final String name) {
		beginGroupOrArray(new Group(name));
	}

	public int beginReadArray(final String name) {
		beginGroupOrArray(new Group(name, false));
		if (getProperty("size") != null) {
			return Integer.parseInt(getProperty("size"));
		}
		return 0;
	}

	public void beginWriteArray(final String name) {
		beginWriteArray(name, -1);
	}

	public void beginWriteArray(final String name, final int size) {
		beginGroupOrArray(new Group(name, size < 0));

		if (size < 0) {
			remove("size");
		} else {
			setProperty("size", String.valueOf(size));
		}
	}

	public void endArray(final String name) {
		final Group group = stack.pop();

		if (group == null || !group.getName().equals(name)) {
			throw new IllegalArgumentException("no array \"" + name + "\"");
		}

		if (group.arraySizeGuess() != -1) {
			setProperty(name + ".size", String.valueOf(group.arraySizeGuess()));
		}

		final int length = group.toString().length();
		if (stack.size() == 0) {
			trace = "";
		} else {
			trace = trace.substring(0, trace.length() - length);
		}
	}

	public void endGroup(final String name) {
		final Group group = stack.pop();

		if (group == null || !group.getName().equals(name)) {
			throw new IllegalArgumentException("no group \"" + name + "\"");
		}

		final int length = group.toString().length();
		if (stack.size() == 0) {
			trace = "";
		} else {
			trace = trace.substring(0, trace.length() - length - 1);
		}
	}

	public boolean getBoolean(final String key) {
		return getBoolean(key, DEFAULT_BOOLEAN);
	}

	public boolean getBoolean(final String key, final boolean defaultValue) {
		return Boolean.valueOf(getProperty(key, String.valueOf(defaultValue)));
	}

	public double getDouble(final String key) {
		return getDouble(key, DEFAULT_DOUBLE);
	}

	public double getDouble(final String key, final double defaultValue) {
		return Double.valueOf(getProperty(key, String.valueOf(defaultValue)));
	}

	public int getInt(final String key) {
		return getInt(key, DEFAULT_INT);
	}

	public int getInt(final String key, final int defaultValue) {
		return Integer.parseInt(getProperty(key, String.valueOf(defaultValue)));
	}

	public long getLong(final String key) {
		return getInt(key, DEFAULT_LONG);
	}

	public long getLong(final String key, final long defaultValue) {
		return Long.parseLong(getProperty(key, String.valueOf(defaultValue)));
	}

	@Override
	public String getProperty(final String key) {
		return super.getProperty(getAbsoluteKey(key));
	}

	@Override
	public String getProperty(final String key, final String defaultValue) {
		final String val = getProperty(key);
		return val == null ? defaultValue : val;
	}

	public String getString(final String key) {
		return getString(key, DEFAULT_STRING);
	}

	public String getString(final String key, final String defaultValue) {
		return getProperty(key, defaultValue);
	}

	/**
	 * @deprecated Die Klasse ist nur für Propertiesfiles gedacht.
	 */
	@Deprecated
	@Override
	public synchronized void loadFromXML(final InputStream in) {
		throw new UnsupportedOperationException();
	}

	@Override
	public synchronized Object remove(final Object key) {
		return super.remove(getAbsoluteKey(key.toString()));
	}

	public void set(final String key, final boolean value) {
		setProperty(key, String.valueOf(value));
	}

	public void set(final String key, final double value) {
		setProperty(key, String.valueOf(value));
	}

	public void set(final String key, final int value) {
		setProperty(key, String.valueOf(value));
	}

	public void set(final String key, final long value) {
		setProperty(key, String.valueOf(value));
	}

	public void set(final String key, final String value) {
		setProperty(key, value);
	}

	public void setArrayIndex(final int index) {
		final Group group = stack.peek();
		int length;

		if (stack.isEmpty() || !group.isArray()) {
			throw new IllegalStateException("no array");
		}

		length = group.toString().length();

		group.setIndex(Math.max(index, 0));
		trace = trace.substring(0, Math.max(trace.length() - length, 0)) + group.toString();
	}

	@Override
	public Object setProperty(final String key, final String value) {
		return super.setProperty(getAbsoluteKey(key), value);
	}

	/**
	 * Schreibt die Properties nach Schlüssel sortiert in den {@code
	 * OutputStream}.
	 * 
	 * {@inheritDoc}
	 */
	@Override
	public synchronized void store(final OutputStream out, final String comments) throws IOException {
		BufferedWriter awriter;

		awriter = new BufferedWriter(new OutputStreamWriter(out, "8859_1"));
		if (comments != null) {
			writeln(awriter, "#" + comments);
		}
		writeln(awriter, "#" + new Date().toString());

		final SortedSet<String> keys = new TreeSet<String>();
		for (final Object o : keySet()) {
			keys.add(o.toString());
		}

		for (String key : keys) {
			String val = (String) get(key);
			key = saveConvert(key, true);

			/*
			 * No need to escape embedded and trailing spaces for value, hence
			 * pass false to flag.
			 */
			val = saveConvert(val, false);
			writeln(awriter, key + "=" + val);
		}

		awriter.flush();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @deprecated Die Klasse ist nur für Propertiesfiles gedacht.
	 */
	@Deprecated
	@Override
	public synchronized void storeToXML(final OutputStream os, final String comment) {
		throw new UnsupportedOperationException();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @deprecated Die Klasse ist nur für Propertiesfiles gedacht.
	 */
	@Deprecated
	@Override
	public synchronized void storeToXML(final OutputStream os, final String comment, final String encoding) {
		throw new UnsupportedOperationException();
	}

	private void beginGroupOrArray(final Group group) {
		stack.push(group);
		trace += group.getName() + '.';
	}

	private String getAbsoluteKey(final String key) {
		return trace + normalize(key);
	}

	private String normalize(final String key) {
		String normalized = key;

		// Alle Leerzeichen entfernen
		normalized = normalized.replaceAll("\\s", "");

		// Punkte in der Mitte zusammenfassen
		while (normalized.contains("..")) {
			normalized = normalized.replaceAll("\\.\\.", "\\.");
		}

		// Punkt am Anfang entfernen
		if (normalized.startsWith(".")) {
			normalized = normalized.substring(1);
		}

		// Punkt am Ende entfernen
		if (normalized.endsWith(".")) {
			normalized = normalized.substring(0, normalized.length() - 1);
		}

		return normalized;
	}

	private String saveConvert(final String theString, final boolean escapeSpace) {
		final int len = theString.length();
		int bufLen = len * 2;
		if (bufLen < 0) {
			bufLen = Integer.MAX_VALUE;
		}
		final StringBuffer outBuffer = new StringBuffer(bufLen);

		for (int x = 0; x < len; x++) {
			final char aChar = theString.charAt(x);
			// Handle common case first, selecting largest block that
			// avoids the specials below
			if (aChar > 61 && aChar < 127) {
				if (aChar == '\\') {
					outBuffer.append('\\');
					outBuffer.append('\\');
					continue;
				}
				outBuffer.append(aChar);
				continue;
			}
			switch (aChar) {
			case ' ':
				if (x == 0 || escapeSpace) {
					outBuffer.append('\\');
				}
				outBuffer.append(' ');
				break;
			case '\t':
				outBuffer.append('\\');
				outBuffer.append('t');
				break;
			case '\n':
				outBuffer.append('\\');
				outBuffer.append('n');
				break;
			case '\r':
				outBuffer.append('\\');
				outBuffer.append('r');
				break;
			case '\f':
				outBuffer.append('\\');
				outBuffer.append('f');
				break;
			case '=': // Fall through
			case ':': // Fall through
			case '#': // Fall through
			case '!':
				outBuffer.append('\\');
				outBuffer.append(aChar);
				break;
			default:
				if (aChar < 0x0020 || aChar > 0x007e) {
					outBuffer.append('\\');
					outBuffer.append('u');
					outBuffer.append(toHex(aChar >> 12 & 0xF));
					outBuffer.append(toHex(aChar >> 8 & 0xF));
					outBuffer.append(toHex(aChar >> 4 & 0xF));
					outBuffer.append(toHex(aChar & 0xF));
				} else {
					outBuffer.append(aChar);
				}
			}
		}
		return outBuffer.toString();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * TODO Mit Umstellung auf Java 6 des Projekts vereinfachen!
	 */
	public Set<String> stringPropertyNames() {
		// return configuration.stringPropertyNames();

		final Set<String> keys = new HashSet<String>();
		for (final Object key : keySet()) {
			if (key instanceof String) {
				keys.add((String) key);
			}
		}
		return keys;
	}

	public boolean containsKey(final String key) {
		return contains(getAbsoluteKey(key)) || contains(key);
	}

}
