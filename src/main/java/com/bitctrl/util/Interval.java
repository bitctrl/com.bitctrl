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

/**
 * Repräsentiert ein Intervall für <code>long</code>-Werte. Wird für
 * Zeitintervalle genutzt, die mit Zeitstempeln arbeiten.
 * 
 * @author BitCtrl Systems GmbH, Schumann
 * @version $Id: Interval.java 10808 2008-07-31 14:23:28Z schumann $
 */
public class Interval implements Cloneable {

	private final long start;

	private final long end;

	private final long length;

	private final boolean timestamp;

	/**
	 * Konstruiert ein Zeitintervall mit dem angegebenen Grenzen.
	 * 
	 * @param start
	 *            Start des Intervalls
	 * @param end
	 *            Ende des Intervalls
	 */
	public Interval(final long start, final long end) {
		this(start, end, true);
	}

	/**
	 * Konstruiert ein Intervall mit dem angegebenen Grenzen.
	 * 
	 * @param start
	 *            Start des Intervalls
	 * @param end
	 *            Ende des Intervalls
	 * @param timestamp
	 *            handelt es sich um ein zeitliches Intervall?
	 */
	public Interval(final long start, final long end, final boolean timestamp) {
		if (start > end) {
			throw new IllegalArgumentException(
					"Das Ende des Intervalls darf nicht vor dessen Start liegen (start="
							+ Timestamp.absoluteTime(start) + " / " + start
							+ ", ende=" + Timestamp.absoluteTime(end) + " / "
							+ end + ").");
		}

		this.start = start;
		this.end = end;
		this.timestamp = timestamp;
		this.length = end - start;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Interval clone() {
		try {
			return (Interval) super.clone();
		} catch (final CloneNotSupportedException ex) {
			throw new IllegalStateException();
		}
	}

	/**
	 * Prüft ob ein anderes Intervall in diesem Intervall enhalten ist,
	 * inklusive der Intervallgrenzen.
	 * 
	 * @param interval
	 *            ein Intervall.
	 * @return {@code true}, wenn das andere Intervall innerhalb dieses
	 *         Intervalls liegt oder mit ihm identisch ist.
	 */
	public boolean contains(final Interval interval) {
		return contains(interval.start) && contains(interval.end);
	}

	/**
	 * Prüft ob ein Wert im Intervall enhalten ist, inklusive der
	 * Intervallgrenzen.
	 * 
	 * @param value
	 *            Ein Wert
	 * @return {@code true}, wenn der Wert innerhalb des Intervalls oder auf
	 *         einer der Intervallgrenzen liegt
	 */
	public boolean contains(final long value) {
		return start <= value && value <= end;
	}

	/**
	 * Zwei Intervalle sind gleich, wenn sie den selben Start- und Endwert
	 * besitzen.
	 * 
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(final Object obj) {
		if (obj instanceof Interval) {
			final Interval intervall = (Interval) obj;

			return start == intervall.start && end == intervall.end;
		}

		return false;
	}

	/**
	 * Gibt das Ende des Intervalls zurück.
	 * 
	 * @return Zeitstempel
	 */
	public long getEnd() {
		return end;
	}

	/**
	 * Gibt die Breite des Intervalls zurück.
	 * 
	 * @return Intervallbreite
	 */
	public long getLength() {
		return length;
	}

	/**
	 * Gibt den Anfang des Intervalls zurück.
	 * 
	 * @return Zeitstempel
	 */
	public long getStart() {
		return start;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		int hash = 7;

		hash = 89 * hash + (int) (this.start ^ (this.start >>> 32));
		hash = 89 * hash + (int) (this.end ^ (this.end >>> 32));

		return hash;
	}

	/**
	 * Prüft ob sich zwei Intervalle schneiden. Zwei Intervalle schneiden sich,
	 * wenn sie mindestens einen Punkt gemeinsam haben (exklusive den
	 * Intervallgrenzen) und ein Intervall nicht das andere enthält.
	 * 
	 * @param interval
	 *            Ein anderes Intervall
	 * @return {@code true}, wenn sich dieses Intervall mit dem anderen
	 *         schneidet
	 */
	public boolean intersect(final Interval interval) {
		return (!contains(interval) && !interval.contains(this))
				&& (interval.isWithin(start) || interval.isWithin(end)
						|| isWithin(interval.start) || isWithin(interval.end));
	}

	/**
	 * Prüft ob sich zwei Intervalle berühren. Zwei Intervalle berühren sich,
	 * wenn sie mindestens einen Punkt gemeinsam haben (inklusive den
	 * Intervallgrenzen).
	 * 
	 * @param interval
	 *            ein anderes Interval.
	 * @return {@code true}, wenn dieses Intervall das andere berührt.
	 */
	public boolean touch(final Interval interval) {
		return interval.contains(start) || interval.contains(end)
				|| contains(interval.start) || contains(interval.end);
	}

	/**
	 * Stellt dieses Intervall einen Zeitraum dar.
	 * 
	 * @return {@code true}, wenn das Intervall einen Zeitraum beschreibt.
	 */
	public boolean isTimestamp() {
		return timestamp;
	}

	/**
	 * Prüft ob sich ein anderes Intervall innerhalb der Intervallgrenzen dieses
	 * Intervalls befindet.
	 * 
	 * @param interval
	 *            ein Intervall.
	 * @return {@code true}, wenn das andere Intervall innerhalb dieses
	 *         Intervalls liegt, aber nicht mit ihm identisch ist.
	 */
	public boolean isWithin(final Interval interval) {
		return isWithin(interval.start) && isWithin(interval.end);
	}

	/**
	 * Prüft ob sich ein Wert innerhalb der Intervallgrenzen befindet.
	 * 
	 * @param value
	 *            Ein Wert
	 * @return {@code true}, wenn der Wert innerhalb des Intervalls, aber nicht
	 *         auf einer der Intervallgrenzen liegt
	 */
	public boolean isWithin(final long value) {
		return start < value && value < end;
	}

	/**
	 * Gibt das Intervall als für den Menschen lesbaren Text zurück. Handelt es
	 * sich um ein Zeitintervall, werden die Zeitstempel als Datum und Uhrzeit
	 * formatiert.
	 * 
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		if (timestamp) {
			return "[" + Timestamp.absoluteTime(start) + ", "
					+ Timestamp.absoluteTime(end) + "]";
		}
		return "[" + start + ", " + end + "]";
	}

}
