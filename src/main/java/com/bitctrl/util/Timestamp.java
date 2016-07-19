/*
 * Java Common Library
 * Copyright (c) 2008 BitCtrl Systems GmbH
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
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

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import com.bitctrl.Constants;

/**
 * Repräsentiert eine absolute oder relative Zeitangabe.
 * 
 * @author BitCtrl Systems GmbH, Falko Schumann
 * @version $Id: Timestamp.java 27988 2010-12-15 14:26:14Z schumann $
 */
public class Timestamp implements Serializable {

	private static final long serialVersionUID = 0L;

	/**
	 * Konvertiert einen Zeitstempel in eine lesbare absolute Zeitangabe.
	 * 
	 * @param zeitstempel
	 *            ein Zeitstempel.
	 * @return die entsprechende Zeit als lesbaren String.
	 */
	public static String absoluteTime(final long zeitstempel) {
		return new Timestamp(zeitstempel).absoluteTime();
	}

	/**
	 * Konvertiert einen Zeitstempel in eine lesbare absolute Zeitangabe.
	 * 
	 * @param zeitstempel
	 *            ein Zeitstempel.
	 * @param date
	 *            Flag, ob das Datum mit ausgegeben werden soll.
	 * @param time
	 *            Falg, ob die Zeit mit ausgegeben werden soll.
	 * @return die entsprechende Zeit als lesbaren String.
	 */
	public static String absoluteTime(final long zeitstempel,
			final boolean date, final boolean time) {
		return new Timestamp(zeitstempel).absoluteTime(date, time);
	}

	/**
	 * Konvertiert einen Zeitstempel in eine lesbare relative Zeitangabe.
	 * 
	 * @param zeitstempel
	 *            ein Zeitstempel.
	 * @return die entsprechende Zeit als lesbaren String.
	 */
	public static String relativeTime(final long zeitstempel) {
		return new Timestamp(zeitstempel).relativeTime();
	}

	private final long timestamp;

	/**
	 * Erzeugt eine (absolute) Zeitangabe. Der Wert wird mit der aktuellen Zeit
	 * initialisiert.
	 */
	public Timestamp() {
		timestamp = System.currentTimeMillis();
	}

	/**
	 * Erzeugt eine Zeitangabe.
	 * 
	 * @param time
	 *            der Zeitwert.
	 */
	public Timestamp(final long time) {
		timestamp = time;
	}

	/**
	 * Generiert einen Zeitstempel anhand eines Strings in Kodierung nach ISO
	 * 8601. Es wird das Format "yyyy-MM-dd'T'HH:mm:ss,SSSZ" verwendet,
	 * z.&nbsp;B. 2008-04-24T22:08:15,124+0100 für den 24.&nbsp;April 2008 um
	 * 22:08:15,124 mitteleuropäischer Zeit (GMT + 1 Stunde).
	 * 
	 * @param iso8601
	 *            eine Zeitangabe nach ISO 8601.
	 * @throws ParseException
	 *             bei einem ungültigen String.
	 */
	public Timestamp(final String iso8601) throws ParseException {
		long t;

		try {
			t = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss,SSSZ").parse(
					iso8601).getTime();
		} catch (final ParseException ex1) {
			try {
				t = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss,SSS").parse(
						iso8601).getTime();
			} catch (final ParseException ex2) {
				try {
					t = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(
							iso8601).getTime();
				} catch (final ParseException ex3) {
					try {
						t = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm").parse(
								iso8601)

						.getTime();
					} catch (final ParseException ex4) {
						t = new SimpleDateFormat("yyyy-MM-dd").parse(iso8601)
								.getTime();
					}
				}
			}
		}

		timestamp = t;
	}

	/**
	 * Generiert einen Zeitstempel anhand eines Strings in Kodierung nach
	 * ISO&nbsp;8601. Es wird dabei das übergebene Format verwendet.
	 * 
	 * @param iso8601
	 *            eine Zeitangabe nach ISO&nbsp;8601.
	 * @param pattern
	 *            das zu verwendende Stringmuster.
	 * @throws ParseException
	 *             bei einem ungültigen String.
	 */
	public Timestamp(final String iso8601, final String pattern)
			throws ParseException {

		long t;

		try {
			t = new SimpleDateFormat(pattern, Locale.ENGLISH).parse(iso8601)
					.getTime();
		} catch (final ParseException ex) {
			t = new Timestamp(iso8601).getTime();
		}

		timestamp = t;
	}

	/**
	 * Generiert einen Zeitstempel anhand eines Strings in Kodierung nach
	 * ISO&nbsp;8601. Es wird dabei das übergebene Format verwendet.
	 * 
	 * @param iso8601
	 *            eine Zeitangabe nach ISO&nbsp;8601.
	 * @param pattern
	 *            das zu verwendende Stringmuster.
	 * @param local
	 *            die Sprachumgebung
	 * @throws ParseException
	 *             bei einem ungültigen String.
	 */
	public Timestamp(final String iso8601, final String pattern,
			final Locale local) throws ParseException {

		long t;

		try {
			t = new SimpleDateFormat(pattern, local).parse(iso8601).getTime();
		} catch (final ParseException ex) {
			t = new Timestamp(iso8601).getTime();
		}

		timestamp = t;
	}

	/**
	 * Gibt den Wert der Zeitangabe als Zeitstempel zurück.
	 * 
	 * @return ein Zeitstempel.
	 */
	public long getTime() {
		return timestamp;
	}

	/**
	 * Gibt den Wert der Zeitangabe als lesbaren absoluten Wert zurück. Für die
	 * Konvertierung wird das aktuelle {@link java.util.Locale} genutzt.
	 * 
	 * @return die absolute Zeitangabe.
	 */
	public String absoluteTime() {
		return absoluteTime(true, true);
	}

	/**
	 * Gibt den Wert der Zeitangabe als lesbaren absoluten Wert zurück. Für die
	 * Konvertierung wird das aktuelle {@link java.util.Locale} genutzt.
	 * 
	 * @param date
	 *            Flag, ob das Datum mit ausgegeben werden soll.
	 * @param time
	 *            Falg, ob die Zeit mit ausgegeben werden soll.
	 * @return die absolute Zeitangabe oder ein leerer String, wenn beide
	 *         Parameter {@code false} sind.
	 */
	public String absoluteTime(final boolean date, final boolean time) {
		if (date && time) {
			return DateFormat.getDateTimeInstance().format(new Date(timestamp));
		}

		if (date) {
			return DateFormat.getDateInstance().format(new Date(timestamp));
		}

		if (time) {
			return DateFormat.getTimeInstance().format(new Date(timestamp));
		}

		return "";
	}

	/**
	 * Gibt den Wert der Zeitangabe als lesbaren relativen Wert zurück. Für
	 * negative Zeitstempel wird {@code null} zurückgegeben.
	 * 
	 * @return die relative Zeitangabe.
	 */
	public String relativeTime() {
		if (timestamp < 0) {
			return null;
		}

		final StringBuffer result = new StringBuffer();
		long remainder = timestamp;

		remainder = remainder(result, remainder, Constants.MILLIS_PER_DAY,
				"Tage");
		remainder = remainder(result, remainder, Constants.MILLIS_PER_HOUR,
				"Stunden");
		remainder = remainder(result, remainder, Constants.MILLIS_PER_MINUTE,
				"Minuten");
		remainder = remainder(result, remainder, Constants.MILLIS_PER_SECOND,
				"Sekunden");
		remainder(result, remainder, 1, "Millisekunden");

		if (result.length() <= 0) {
			result.append("0 Millisekunden");
		}

		return result.toString();
	}

	/**
	 * Gibt den Wert der Zeitangabe als lesbaren relativen Wert zurück.
	 * 
	 * @param trenner
	 *            der zu verwendende Trenner.
	 * 
	 * @return die relative Zeitangabe.
	 */
	public String relativeTime(final String trenner) {
		if (timestamp < 0) {
			return null;
		}

		if (timestamp == 0) {
			return "0" + trenner + "00" + trenner + "00";
		}

		final StringBuffer result = new StringBuffer();
		long remainder = timestamp;

		remainder = remainder(result, remainder, Constants.MILLIS_PER_HOUR, "",
				trenner);
		remainder = remainder(result, remainder, Constants.MILLIS_PER_MINUTE,
				"", trenner);
		remainder = remainder(result, remainder, Constants.MILLIS_PER_SECOND,
				"", trenner);

		return result.toString();
	}

	private long remainder(final StringBuffer result, final long remainder,
			final long factor, final String measure) {
		return remainder(result, remainder, factor, measure, " ");
	}

	private long remainder(final StringBuffer result, final long remainder,
			final long factor, final String measure, final String pattern) {
		final long part = remainder / factor;
		if (part > 0) {
			if (result.length() > 0) {
				result.append(' ');
			}
			result.append(part);
			result.append(pattern);
			result.append(measure);
		}
		return remainder - part * factor;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(final Object obj) {
		if (obj == this) {
			return true;
		}
		if (obj instanceof Timestamp) {
			final Timestamp o = (Timestamp) obj;

			return o.timestamp == timestamp;
		}
		return false;
	}

	/**
	 * Der Hash entspricht dem Hash des Zeitstempels.
	 * 
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		return Long.valueOf(timestamp).hashCode();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return getClass() + "[time=" + timestamp + "]";
	}

}
