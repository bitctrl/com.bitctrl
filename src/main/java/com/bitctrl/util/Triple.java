package com.bitctrl.util;

/**
 * Encapsulates three objects of possible different types.
 * 
 * @author BitCtrl Systems GmbH, schnepel
 * @version $Id$
 * @param <A>
 *            type of first object
 * @param <B>
 *            type of second object
 * @param <C>
 *            type of third object
 */
public class Triple<A, B, C> {
	/**
	 * the first object
	 */
	public final A a;

	/**
	 * the second object
	 */
	public final B b;

	/**
	 * the third object
	 */
	public final C c;

	/**
	 * Constructs a Triple of objects of different types.
	 * 
	 * @param a
	 *            the first object
	 * @param b
	 *            the second object
	 * @param c
	 *            the third object
	 */
	public Triple(final A a, final B b, final C c) {
		this.a = a;
		this.b = b;
		this.c = c;
	}

	/**
	 * Constructs a Triple of objects of different types.
	 * 
	 * @param <A>
	 *            type of first object
	 * @param <B>
	 *            type of second object
	 * @param <C>
	 *            type of third object
	 * @param a
	 *            the first object
	 * @param b
	 *            the second object
	 * @param c
	 *            the third object
	 * @return das erzeugte Triple
	 */
	public static final <A, B, C> Triple<A, B, C> create(final A a, final B b, final C c) {
		return new Triple<A, B, C>(a, b, c);
	}

	@Override
	public boolean equals(final Object obj) {
		if (!(obj instanceof Triple<?, ?, ?>)) {
			return false;
		}
		final Triple<?, ?, ?> o2 = (Triple<?, ?, ?>) obj;
		return equals(a, o2.a) && equals(b, o2.b) && equals(c, o2.c);
	}

	private <T> boolean equals(final T o1, final T o2) {
		return o1 == o2 || o1 != null && o1.equals(o2);
	}

	@Override
	public int hashCode() {
		return (null == a ? 0 : a.hashCode()) ^ (null == b ? 0 : b.hashCode()) ^ (null == c ? 0 : c.hashCode());
	}

	@Override
	public String toString() {
		return "Triple [a=" + a + ", b=" + b + ", c=" + c + "]";
	}

}
