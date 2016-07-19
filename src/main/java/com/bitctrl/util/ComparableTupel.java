package com.bitctrl.util;

import java.util.Comparator;

/**
 * <p>
 * Erweitert {@link ComparatorTupel} und erwartet, dass die Kinder ebenfalls
 * {@link Comparable} sind.
 * </p>
 * <p>
 * Die statische Funktion {@link #comparator()} gibt eine typisierte
 * {@link Comparator Comparator-Instanz} für {@link Comparable Comparables}
 * zurück.
 * </p>
 * 
 * @author BitCtrl Systems GmbH, schnepel
 * 
 * @param <S1>
 *            Typ des ersten Elements des Tupels
 * @param <S2>
 *            Typ des zweiten Elements des Tupels
 */
@SuppressWarnings("unchecked")
public class ComparableTupel<S1 extends Comparable<S1>, S2 extends Comparable<S2>> extends ComparatorTupel<S1, S2> {

	private static class ComparableComparator<T extends Comparable<T>> implements Comparator<T> {

		public int compare(final T a, final T b) {
			if (a == b) {
				return 0;
			}
			if (null != a) {
				return a.compareTo(b);
			}
			/* bei null == b verzweigt er in die oberen if's. */
			return -b.compareTo(a);
		}
	}

	private final static Comparator COMPARATOR = new ComparableComparator();

	/**
	 * Gibt einen {@link Comparator} für {@link Comparable Comparables} zurück.
	 * 
	 * @param <T>
	 *            Der {@link Comparable} Typ
	 * @return der {@link Comparator}
	 */
	public static <T extends Comparable<T>> Comparator<T> comparator() {
		return COMPARATOR;
	}

	/**
	 * Konstruktor mit null-Werten.
	 */
	public ComparableTupel() {
		super(COMPARATOR, COMPARATOR);
	}

	/**
	 * Konstruktor mit spezifischen Werten.
	 * 
	 * @param first
	 *            der erste Wert des Tupels
	 * @param second
	 *            der zweite Wert des Tupels
	 */
	public ComparableTupel(final S1 first, final S2 second) {
		super(first, second, COMPARATOR, COMPARATOR);
	}
}
