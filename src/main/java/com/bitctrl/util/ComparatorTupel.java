package com.bitctrl.util;

import java.util.Comparator;

/**
 * <p>
 * Implementiert {@link Comparable} und reicht die {@link #compareTo} Aufrufe an
 * zwei {@link Comparator Comparatoren} weiter.
 * </p>
 * <p>
 * Einen {@link Comparator} für @{@link Comparable Comparables} gibt
 * {@link ComparableTupel#comparator()} zurück.
 * </p>
 * 
 * @author BitCtrl Systems GmbH, schnepel
 * @version $Id$
 * @param <S1> der Typ des ersten Elements des Tupels
 * @param <S2> der Typ des zweiten Elements des Tupels
 * @see ComparableTupel#comparator()
 */
public class ComparatorTupel<S1, S2> extends Tupel<S1, S2> implements
		Comparable<ComparatorTupel<S1, S2>> {
	private Comparator<S1> compFirst;
	private Comparator<S2> compSecond;

	public ComparatorTupel(final Comparator<S1> compFirst,
			final Comparator<S2> compSecond) {
		super();
		setFirstComparator(compFirst);
		setSecondComparator(compSecond);
	}

	public ComparatorTupel(final S1 first, final S2 second,
			final Comparator<S1> compFirst, final Comparator<S2> compSecond) {
		super(first, second);
		setFirstComparator(compFirst);
		setSecondComparator(compSecond);
	}

	public void setFirstComparator(final Comparator<S1> compFirst) {
		this.compFirst = compFirst;
	}

	public void setSecondComparator(final Comparator<S2> compSecond) {
		this.compSecond = compSecond;
	}

	public int compareTo(final ComparatorTupel<S1, S2> o) {
		final int ret = compFirst.compare(getFirst(), o.getFirst());
		if (0 != ret) {
			return ret;
		}
		return compSecond.compare(getSecond(), o.getSecond());
	}
}
