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
 * Shiftet ein Feld von Integerwerten. Hilfreich z.&nbsp; f�r kombinatorische
 * Aufgaben. Dabei werden die einzelnen Integerwerte der Reihe nach
 * durchgeshiftet und jede Kombination einmal ausgew�hlt. Das Z�hlen beginnt bei
 * 0. Wird beim Shiften ein Maximum �berschritten, wird das betreffende Feld
 * wieder auf 0 gesetzt. Ein Maximum von 3 f�hrt zu folgenden Shift verhalten:
 * 0, 1, 2, 3, 0, 1, 2, 3, 0, 1, 2, 3, ...
 * 
 * @author BitCtrl Systems GmbH, Falko Schumann
 */
public class Shifter {

	private final int[] values;
	private final int[] maximas;

	/**
	 * Initialisiert den Shifter. Die Anzahl der Elemente im Parameter
	 * {@code maximas} bestimmt die Anzahl der Elemente im internen Feld des
	 * Shifters.
	 * 
	 * @param maximas
	 *            die maximalen Werte der einzelnen Feldelemente.
	 */
	public Shifter(final int... maximas) {
		this.maximas = maximas.clone();
		values = new int[maximas.length];
	}

	/**
	 * Initialisiert den Shifter. Die Anzahl der Elemente im Parameter
	 * {@code maximas} bestimmt die Anzahl der Elemente im internen Feld des
	 * Shifters.
	 * 
	 * @param maximas
	 *            die maximalen Werte der einzelnen Feldelemente.
	 */
	public Shifter(final List<Integer> maximas) {
		this.maximas = new int[maximas.size()];
		for (int i = 0; i < maximas.size(); ++i) {
			this.maximas[i] = maximas.get(i);
		}

		values = new int[this.maximas.length];
	}

	/**
	 * Gibt die Länge des internen Felds zurück.
	 * 
	 * @return die Feldlänge.
	 */
	public int getLength() {
		return values.length;
	}

	/**
	 * Gibt die erlaubten Maximas der Elemente des internen Felds zurück.
	 * 
	 * @return die Maximas.
	 */
	public int[] getMaximas() {
		return maximas.clone();
	}

	/**
	 * Gibt den aktuellen Zustand des internen Felds zurück.
	 * 
	 * @return der aktuelle Zustand.
	 */
	public int[] getValues() {
		return values.clone();
	}

	/**
	 * Setzt den aktuellen Zustands des internen Felds. Die Länge des Felds im
	 * Parameter muss mit der des internen Felds übereinstimmen.
	 * 
	 * @param values
	 *            die neuen Werte.
	 */
	public void setValues(final int[] values) {
		if (values.length != this.values.length) {
			throw new IllegalArgumentException(
					"Die Feldlänge im Parameter stimmt nicht mit der Länge des internen Felds überein.");
		}

		for (int i = 0; i < values.length; ++i) {
			if (values[i] > maximas[i]) {
				throw new IllegalArgumentException("Der Wert (" + values[i]
						+ ") des Elements mit dem Index " + i
						+ " ist größer als sein erlaubtes Maximum ("
						+ maximas[i] + ").");
			}
			this.values[i] = values[i];
		}
	}

	/**
	 * Shiften das interne Feld um eine Kombination weiter. Das Shiften erfolgt
	 * von hinten nach vorne, d.&nbsp;h. zuerst wird das Element mit dem größten
	 * Index und zu letzt das Element mit dem kleinsten Index geshiftet.
	 * 
	 * @return {@code true}, wenn das Shiften ohne Überlauf stattfand und
	 *         {@code false}, wenn das das interne Feld einmal komplett
	 *         durchgeshiftet wurde und nun wieder auf dem Anfangswert steht.
	 */
	public boolean shift() {
		for (int i = values.length - 1; i >= 0; --i) {
			if (values[i] < maximas[i]) {
				++values[i];
				return true;
			}

			// Überlauf im aktuellen Element
			values[i] = 0;
		}

		return false;
	}

	@Override
	public String toString() {
		final List<Integer> helperValues, helperMaximas;
		String s;

		helperValues = new ArrayList<Integer>();
		for (final int i : values) {
			helperValues.add(i);
		}

		helperMaximas = new ArrayList<Integer>();
		for (final int i : values) {
			helperMaximas.add(i);
		}

		s = getClass().getName() + "[";
		s += "values=" + helperValues;
		s += ", maximas=" + helperMaximas;
		s += "]";

		return s;
	}

}
