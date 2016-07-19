package com.bitctrl.util;

import java.util.Comparator;
import java.util.List;

/**
 * {@link Comparator} zum Vergleichen von zwei Elementen anhand des Indexes in
 * einer {@link List Liste}.
 * 
 * @author BitCtrl Systems GmbH, schnepel
 * 
 * @param <T>
 *            der Typ der Listenelemente
 */
public class ListIndexComparator<T> implements Comparator<T> {

	private final List<T> list;

	/**
	 * Konstruktor.
	 * 
	 * @param list
	 *            die zu verwendende Liste
	 */
	public ListIndexComparator(final List<T> list) {
		this.list = list;
	}

	public int compare(final T o1, final T o2) {
		return list.indexOf(o1) - list.indexOf(o2);
	}
}
