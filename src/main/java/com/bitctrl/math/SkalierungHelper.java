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
package com.bitctrl.math;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;

/**
 * Hilfsklasse zum genauen Skalieren von Kommazahlen.
 * 
 * Beispiel: 188 * 0.01 soll als 1.88 rauskommen, Double.toString() liefert aber
 * 1.880000000001.
 * 
 * @author BitCtrl Systems GmbH, Albrecht Uhlmann
 */
public class SkalierungHelper {

	/**
	 * Test der notwendigen Anzahl Nachkommastellen.
	 */
	private final NumberFormat precesionTestFormat;

	/**
	 * Formatierer für skalierte Werte.
	 */
	private final NumberFormat integerNumberFormat;

	/**
	 * der Helper.
	 */
	private static final SkalierungHelper instanz = new SkalierungHelper();

	/**
	 * Liefert den Helper.
	 * 
	 * @return der Helper
	 */
	public static SkalierungHelper getInstanz() {
		return instanz;
	}

	/**
	 * Initialisierung der Formatierer.
	 */
	private SkalierungHelper() {
		final DecimalFormatSymbols isymbols = new DecimalFormatSymbols();
		isymbols.setDecimalSeparator('.');
		integerNumberFormat = new DecimalFormat("0", isymbols);
		integerNumberFormat.setMinimumIntegerDigits(1);
		integerNumberFormat.setMaximumIntegerDigits(999);
		integerNumberFormat.setGroupingUsed(false);
		final DecimalFormatSymbols symbols = new DecimalFormatSymbols();
		symbols.setDecimalSeparator('.');
		precesionTestFormat = new DecimalFormat("0.#", symbols);
		precesionTestFormat.setMaximumFractionDigits(999);
	}

	/**
	 * Skaliert und formatiert einen Wert.
	 * 
	 * @param unscaledValue
	 *            Unskalierter Wert
	 * @param conversionFactor
	 *            Faktor, mit dem der unskalierte Wert multipliziert wird.
	 * @return der Text
	 */
	public String format(final double unscaledValue,
			final double conversionFactor) {
		int precision = 0;
		synchronized (integerNumberFormat) {
			final String formatted = precesionTestFormat
					.format(conversionFactor);
			final int kommaPosition = formatted.lastIndexOf('.');
			if (kommaPosition >= 0) {
				precision = formatted.length() - kommaPosition - 1;
			}
			integerNumberFormat.setMinimumFractionDigits(precision);
			integerNumberFormat.setMaximumFractionDigits(precision);
			return integerNumberFormat.format(conversionFactor * unscaledValue);
		}
	}
}
