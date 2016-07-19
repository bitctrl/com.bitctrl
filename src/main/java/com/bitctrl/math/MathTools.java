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

package com.bitctrl.math;

/**
 * Allgemeine mathematische Hilfsfunktionen.
 * 
 * @author BitCtrl Systems GmbH, Falko Schumann
 * @version $Id: MathTools.java 6848 2008-02-21 15:11:54Z Schumann $
 */
public final class MathTools {

	/**
	 * Testet ob die zwei Werte ungefähr gleich sind. Die maximal erlaubte
	 * Abweichung wird als dritter Parameter angegeben.
	 * 
	 * @param a
	 *            ein Wert.
	 * @param b
	 *            ein zweiter Wert.
	 * @param precision
	 *            die maximal erlaubte Abweichung, z.&nbsp;B. 0.001.
	 * @return {@code true}, wenn die beiden Werte ungefähr gleich sind.
	 */
	public static boolean ca(final double a, final double b,
			final double precision) {
		if (Math.abs(a - b) < precision) {
			return true;
		}

		return false;
	}

	/**
	 * Testet ob die zwei Werte ungefähr gleich sind. Die maximal erlaubte
	 * Abweichung wird als dritter Parameter angegeben.
	 * 
	 * @param a
	 *            ein Wert.
	 * @param b
	 *            ein zweiter Wert.
	 * @param precision
	 *            die maximal erlaubte Abweichung, z.&nbsp;B. 0.001.
	 * @return {@code true}, wenn die beiden Werte ungefähr gleich sind.
	 */
	public static boolean ca(final float a, final float b, final float precision) {
		if (Math.abs(a - b) < precision) {
			return true;
		}

		return false;
	}

	private MathTools() {
		// Konstruktor verstecken
	}

}
