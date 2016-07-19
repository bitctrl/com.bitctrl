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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Allgemeine Hilfsmethoden für den Umgang mit dem Java-Collection-Framework.
 * 
 * @author BitCtrl Systems GmbH, Falko Schumann
 */
public final class CollectionUtilities {

	/**
	 * Bestimmt die Schnittmenge zweier Listen.
	 * <p>
	 * <em>Hinweis:</em> Da dies eigentlich eine Mengenoperation ist, sollten
	 * bei der Verwendung dieser Methode keine Annahmen über die Reihenfolgen
	 * der Elemente im Ergebnis getroffen werden.
	 * 
	 * @param <T>
	 *            der Typ der Listen.
	 * @param list1
	 *            die erste Liste.
	 * @param list2
	 *            die zweite Liste.
	 * @return die Schnittliste.
	 */
	public static <T> List<T> intersection(final List<T> list1,
			final List<T> list2) {
		final List<T> result = new ArrayList<T>();

		for (final T e : list1) {
			if (list2.contains(e)) {
				result.add(e);
			}
		}

		return result;
	}

	/**
	 * Bestimmt die Differenz zweier Listen.
	 * <p>
	 * <em>Hinweis:</em> Da dies eigentlich eine Mengenoperation ist, sollten
	 * bei der Verwendung dieser Methode keine Annahmen über die Reihenfolgen
	 * der Elemente im Ergebnis getroffen werden.
	 * 
	 * @param <T>
	 *            der Typ der Listen.
	 * @param list1
	 *            die Liste von der abgezogen wird.
	 * @param list2
	 *            die Elemente dieser Liste werden abgezogen.
	 * @return die Listendifferenz.
	 */
	public static <T> List<T> difference(final List<T> list1,
			final List<T> list2) {
		final List<T> result = new ArrayList<T>(list1);

		for (final T e : list2) {
			result.remove(e);
		}

		return result;
	}

	/**
	 * Bestimmt die Vereinigung zweier Listen. Im Ergebnis sind die Elemente
	 * doppelt vorhanden, die in beiden Listen vorhanden sind.
	 * <p>
	 * <em>Hinweis:</em> Da dies eigentlich eine Mengenoperation ist, sollten
	 * bei der Verwendung dieser Methode keine Annahmen über die Reihenfolgen
	 * der Elemente im Ergebnis getroffen werden.
	 * 
	 * @param <T>
	 *            der Typ der Listen.
	 * @param list1
	 *            die erste Liste.
	 * @param list2
	 *            die zweite Liste.
	 * @return die Listenvereinigung.
	 */
	public static <T> List<T> union(final List<T> list1, final List<T> list2) {
		final List<T> result = new ArrayList<T>();
		result.addAll(list1);
		result.addAll(list2);

		return result;
	}

	/**
	 * Bestimmt die Summe zweier Listen. Die Elemente die in beiden Listen
	 * vorkommen, werden nur einmal im Ergebnis aufgenommen.
	 * <p>
	 * <em>Hinweis:</em> Da dies eigentlich eine Mengenoperation ist, sollten
	 * bei der Verwendung dieser Methode keine Annahmen über die Reihenfolgen
	 * der Elemente im Ergebnis getroffen werden.
	 * 
	 * @param <T>
	 *            der Typ der Listen.
	 * @param list1
	 *            die erste Liste.
	 * @param list2
	 *            die zweite Liste.
	 * @return die Listenvereinigung.
	 */
	public static <T> List<T> sum(final List<T> list1, final List<T> list2) {
		return difference(union(list1, list2), intersection(list1, list2));
	}

	/**
	 * Bestimmt das Komplement zweier Listen (symmetrische Differenz).
	 * <p>
	 * <em>Hinweis:</em> Da dies eigentlich eine Mengenoperation ist, sollten
	 * bei der Verwendung dieser Methode keine Annahmen über die Reihenfolgen
	 * der Elemente im Ergebnis getroffen werden.
	 * 
	 * @param <T>
	 *            der Typ der Listen.
	 * @param list1
	 *            die erste Liste.
	 * @param list2
	 *            die zweite Liste.
	 * @return die Komplementärliste.
	 */
	public static <T> List<T> complement(final List<T> list1,
			final List<T> list2) {
		return union(difference(list1, list2), difference(list2, list1));
	}

	/**
	 * Bestimmt die Schnittmenge zweier Mengen.
	 * 
	 * @param <T>
	 *            der Typ der Mengen.
	 * @param set1
	 *            die erste Menge.
	 * @param set2
	 *            die zweite Menge.
	 * @return die Schnittmenge.
	 */
	public static <T> Set<T> intersection(final Set<T> set1, final Set<T> set2) {
		final Set<T> result = new HashSet<T>();

		for (final T e : set1) {
			if (set2.contains(e)) {
				result.add(e);
			}
		}

		return result;
	}

	/**
	 * Bestimmt die Differenz zweier Mengen.
	 * 
	 * @param <T>
	 *            der Typ der Mengen.
	 * @param set1
	 *            die Menge von der abgezogen wird.
	 * @param set2
	 *            die Elemente dieser Liste werden abgezogen.
	 * @return die Mengendifferenz.
	 */
	public static <T> Set<T> difference(final Set<T> set1, final Set<T> set2) {
		final Set<T> result = new HashSet<T>(set1);

		for (final T e : set2) {
			result.remove(e);
		}

		return result;
	}

	/**
	 * Bestimmt die Vereinigung zweier Mengen.
	 * 
	 * @param <T>
	 *            der Typ der Mengen.
	 * @param set1
	 *            die erste Menge.
	 * @param set2
	 *            die zweite Menge.
	 * @return die Mengenvereinigung.
	 */
	public static <T> Set<T> union(final Set<T> set1, final Set<T> set2) {
		final Set<T> result = new HashSet<T>();
		result.addAll(set1);
		result.addAll(set2);

		return result;
	}

	/**
	 * Bestimmt das Komplement zweier Mengen (symmetrische Differenz).
	 * 
	 * @param <T>
	 *            der Typ der Mengen.
	 * @param set1
	 *            die erste Menge.
	 * @param set2
	 *            die zweite Menge.
	 * @return die Komplementärmenge.
	 */
	public static <T> Set<T> complement(final Set<T> set1, final Set<T> set2) {
		return union(difference(set1, set2), difference(set2, set1));
	}

	private CollectionUtilities() {
		// nix
	}

}
