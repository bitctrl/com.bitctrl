package com.bitctrl.util;

/**
 * Ein Tupel mit zwei Elementen oder ein geordnetes Paar.
 * 
 * @author BitCtrl Systems GmbH, Falko Schumann
 * 
 * @param <S1>
 *            der Typ des ersten Elements.
 * @param <S2>
 *            der Typ des zweiten Elements.
 */
public class Tupel<S1, S2> {

	private S1 first;
	private S2 second;

	/**
	 * Der Standardkonstruktor tut nix.
	 */
	public Tupel() {
		// tut nix
	}

	/**
	 * Initialisierten die beiden Elemente.
	 * 
	 * @param first
	 *            das erste Element.
	 * @param second
	 *            das zweite Element.
	 */
	public Tupel(final S1 first, final S2 second) {
		this.first = first;
		this.second = second;
	}

	public S1 getFirst() {
		return first;
	}

	public S2 getSecond() {
		return second;
	}

	public void setFirst(final S1 first) {
		this.first = first;
	}

	public void setSecond(final S2 second) {
		this.second = second;
	}

	@Override
	public boolean equals(final Object obj) {
		if (obj == this) {
			return true;
		}
		if (obj instanceof Tupel<?, ?>) {
			final Tupel<?, ?> other = (Tupel<?, ?>) obj;
			boolean equals = true;
			equals &= first != null && first.equals(other.first)
					|| first == other.first;
			equals &= second != null && second.equals(other.second)
					|| second == other.second;
			return equals;
		}
		return false;

	}

	@Override
	public int hashCode() {
		return (null != first ? first.hashCode() : 0)
				^ (null != second ? second.hashCode() : 0);
	}

	@Override
	public String toString() {
		return "(" + first + ", " + second + ")";
	}

}
