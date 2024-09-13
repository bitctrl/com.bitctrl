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

import java.util.Comparator;

/**
 * Ein allgemeiner Comparator, der zuerst prüft, ob die zu vergleichenden
 * Objekte {@link Comparable} implementieren, bevor einen Strinvergleich
 * durchführt.
 * 
 * @author BitCtrl Systems GmbH, Falko Schumann
 */
public class DefaultComparator implements Comparator<Object> {

	/**
	 * {@inheritDoc}
	 * 
	 * Falls beide Objekte {@link Comparable} implementieren und
	 * zuweisungskompatibel sind ({@link Class#isAssignableFrom(Class)}), dann wird
	 * die {@code compare()}-Methode von {@code o1} zum Vergleich verwendet.
	 * Anderfalls werden beide Objekte in Strings überführt ({@code
	 * toString()}) und dann die Strings miteinander verglichen.
	 */
	@Override
	public int compare(final Object o1, final Object o2) {
		if (o1 instanceof final Comparable c1 && o2 instanceof final Comparable c2
				&& (o1.getClass().isAssignableFrom(o2.getClass()) || o2.getClass().isAssignableFrom(o1.getClass()))) {
			return c1.compareTo(c2);
		}

		return String.CASE_INSENSITIVE_ORDER.compare(o1.toString(), o2.toString());
	}

}
