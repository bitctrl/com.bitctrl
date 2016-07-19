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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.regex.Pattern;

import com.bitctrl.Constants;

/**
 * Eine allgemeine Schnittstelle zum Prüfen einzelner Werte. Die Art des Tests
 * kann beliebig sein, es wird zu jedem Wert eine Antwort mit "Ja" oder "Nein"
 * generiert, ob der Wert "matched" oder nicht.
 * 
 * @author BitCtrl Systems GmbH, Falko Schumann
 * 
 * @param <T>
 *            der Typ der zu prüfenden Werte.
 */
public interface ValueMatcher<T> {

	/**
	 * Für Instanzen dieser Klasse matchen alle Werte.
	 * 
	 * @param <T>
	 *            der Typ der zu prüfenden Werte.
	 */
	static class AlwaysMatcher<T> implements ValueMatcher<T> {

		/**
		 * Gibt immer {@code true} zurück.
		 * 
		 * {@inheritDoc}
		 */
		public boolean match(final T value) {
			return true;
		}

	}

	/**
	 * Für Instanzen dieser Klasse matchen alle Werte ungleich {@code null}.
	 * 
	 * @param <T>
	 *            der Typ der zu prüfenden Werte.
	 */
	static class NotNullMatcher<T> implements ValueMatcher<T> {

		/**
		 * Gibt {@code true} zurück, wenn der Wert ungleich {@code null} ist.
		 * 
		 * {@inheritDoc}
		 */
		public boolean match(final T value) {
			return value != null;
		}

	}

	/**
	 * Instanzen dieser Klassen prüfen anhand einer {@link Collection} oder
	 * eines Feldes, ob Werte matchen. Alle in der Menge enthalten Werte
	 * matchen, alle nicht enthaltenen matchen nicht.
	 * 
	 * @param <T>
	 *            der Typ der zu prüfenden Werte.
	 */
	static class CollectionValueMatcher<T> implements ValueMatcher<T> {

		private final Collection<T> collection;

		/**
		 * Verwendet die übergebene {@link Collection} zum matchen. Es wird nur
		 * die Referenz der {@link Collection} benutzt und keine Kopien
		 * erstellt.
		 * 
		 * @param collection
		 *            eine beliebige Collection.
		 */
		public CollectionValueMatcher(final Collection<T> collection) {
			this.collection = collection;
		}

		/**
		 * Verwendet eine Feld zum matchen. Das Feld intern als
		 * {@link java.util.List} gehalten.
		 * 
		 * @param values
		 *            ein beliebiges Feld.
		 */
		public CollectionValueMatcher(final T... values) {
			collection = Arrays.asList(values);
		}

		/**
		 * Gibt {@code true}, wenn der Wert in der {@code Collection} enthalten
		 * ist und {@code false}, wenn der Wert nicht enthalten ist.
		 * 
		 * {@inheritDoc}
		 */
		public boolean match(final T value) {
			return collection.contains(value);
		}

		@Override
		public String toString() {
			String s;

			s = getClass().getName() + "[";
			s += "collection=" + collection;
			s += "]";

			return s;
		}

	}

	/**
	 * Für Instanzen dieser Klasse matchen alle Werte, für die alle gekapselten
	 * {@link ValueMatcher} matchen.
	 * 
	 * @param <T>
	 *            der Typ der zu prüfenden Werte.
	 */
	static class ComplexMatcher<T> extends ArrayList<ValueMatcher<T>> implements
			ValueMatcher<T> {

		private static final long serialVersionUID = 1L;

		/**
		 * {@inheritDoc}
		 * 
		 * Gibt nur dann {@code false} zurück, wenn mindestens ein registrierter
		 * {@code ValueMatcher} nicht matcht.
		 */
		public boolean match(final T value) {
			final Iterator<ValueMatcher<T>> iterator = iterator();
			while (iterator.hasNext()) {
				if (!iterator.next().match(value)) {
					return false;
				}
			}
			return true;
		}

	}

	/**
	 * Für Instanzen dieser Klasse matchen alle Werte ungleich {@code null} und
	 * ungleich {@code ""}.
	 */
	static class StringNotEmptyMatcher implements ValueMatcher<String> {

		/**
		 * {@inheritDoc}
		 * 
		 * Gibt {@code true} zurück, wenn der Wert ungleich {@code null} und
		 * ungleich {@code ""} ist.
		 */
		public boolean match(final String value) {
			return value != null && value.length() > 0;
		}
	}

	/**
	 * Für Instanzen dieser Klasse matchen alle Werte, die eine gültige
	 * E-Mail-Adresse darstellen.
	 */
	static class EmailMatcher implements ValueMatcher<String> {

		/**
		 * {@inheritDoc}
		 * 
		 * Gibt {@code true} zurück, wenn der Wert eine gültige E-Mail-Adresse
		 * darstellt.
		 */
		public boolean match(final String value) {
			return value != null
					&& Pattern.matches(Constants.REGEX_MAIL, value);
		}
	}

	/**
	 * Für Instanzen dieser Klasse matchen alle Werte, die zu gültige Password
	 * (mind 6 Zeichen, 2 davon digits) darstellen.
	 */
	static class PasswdMatcher implements ValueMatcher<String> {

		public boolean match(final String value) {
		return value != null && Pattern.matches("^[a-zA-Z0-9]{6,}$", value) //$NON-NLS-1$
				&& Pattern.matches(".*[0-9].*[0-9].*", value); //$NON-NLS-1$

		}
	}

	/**
	 * Für Instanzen dieser Klasse matchen alle Werte, die mit einem
	 * festgelegten regulären matchen.
	 */
	static class RegExMatcher implements ValueMatcher<String> {

		private final String regex;

		/**
		 * Legt den regulären Ausdruck fest, gegen den geprüft werden soll.
		 * 
		 * @param regex
		 *            ein regulärer Ausdruck.
		 */
		public RegExMatcher(final String regex) {
			this.regex = regex;
		}

		/**
		 * {@inheritDoc}
		 * 
		 * Gibt {@code true} zurück, wenn der Wert mit dem festgelegtem
		 * regulären Ausdruck matcht.
		 */
		public boolean match(final String value) {
			return value != null && Pattern.matches(regex, value);
		}
	}

	/**
	 * Für Instanzen dieser Klasse matchen alle Werte, die in einem festgelegten
	 * Intervall liegen. Die Intervallgrenzen gehören mit zu dem Intervall.
	 * 
	 * @param <T>
	 *            der Typ der zu prüfenden Werte.
	 */
	static class RangeMatcher<T> implements ValueMatcher<T> {

		private final Comparable<T> min;
		private final Comparable<T> max;

		/**
		 * Definiert das Intervall.
		 * 
		 * @param min
		 *            das Minimum des Intervalls.
		 * @param max
		 *            das Maximum des Intervalls.
		 */
		public RangeMatcher(final Comparable<T> min, final Comparable<T> max) {
			this.min = min;
			this.max = max;
		}

		/**
		 * {@inheritDoc}
		 * 
		 * Gibt {@code true} zurück, wenn der Wert innerhalb des definierten
		 * Intervalls oder auf einer seiner Grenzen liegt.
		 */
		public boolean match(final T value) {
			return value != null && min.compareTo(value) <= 0
					&& max.compareTo(value) >= 0;
		}
	}

	/**
	 * Gibt {@code true} zurück, wenn der übergebene Wert matcht und {@code
	 * false}, wenn er nicht matcht.
	 * 
	 * @param value
	 *            ein beliebiger Wert.
	 * @return ob der Wert matcht.
	 */
	boolean match(T value);

}
