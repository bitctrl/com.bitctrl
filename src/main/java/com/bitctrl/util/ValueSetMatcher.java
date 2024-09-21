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
import java.util.List;

/**
 * Fasst mehrere Klassen der Schnittstelle {@link ValueMatcher} zu einer
 * zusammen. Dieser Matcher prüft, ob irgendein gekapselter Matcher matcht.
 * 
 * @author BitCtrl Systems GmbH, Falko Schumann
 * 
 * @param <T> der Typ der zu prüfenden Werte.
 */
public class ValueSetMatcher<T> implements ValueMatcher<T> {

	private final List<ValueMatcher<T>> matchers = new ArrayList<>();

	/**
	 * Fügt einen Matcher der Matcher-Menge hinzu.
	 * 
	 * @param matcher ein beliebiger Matcher.
	 */
	public void add(final ValueMatcher<T> matcher) {
		matchers.add(matcher);
	}

	/**
	 * Gibt {@code true} zurück, wenn irgendein Matcher der Matcher-Menge matcht und
	 * {@code false}, wenn kein Matcher matcht.
	 * 
	 * {@inheritDoc}
	 */
	@Override
	public boolean match(final T value) {
		for (final ValueMatcher<T> vm : matchers) {
			if (vm.match(value)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public String toString() {
		String s;

		s = getClass().getName() + "[";
		s += "matchers=" + matchers;
		s += "]";

		return s;
	}

}
