/*
 * BitCtrl- Funktionsbibliothek
 * Copyright (C) 2007-2010 BitCtrl Systems GmbH 
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
package com.bitctrl.util.resultset;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

/**
 * Ein unsynchronisiertes Schieberegister.
 * 
 * @author BitCtrl Systems GmbH, Albrecht Uhlmann
 * @version $Id: LinearBuffer.java 27732 2010-11-24 17:36:49Z uhlmann $
 * 
 * @param <T>
 *            der Typ der verwalteten Objekte
 */
public class LinearBuffer<T> implements Collection<T> {

	/**
	 * Das Backend, welches die Daten enthält.
	 */
	private final T[] buffer;

	/**
	 * Legt ein Schieberegister mit bestimmter Größe an.
	 * 
	 * @param size
	 *            die Größe in Anzahl Elementen. Muss größer 0 sein, sonst
	 *            {@link IllegalArgumentException}.
	 */
	public LinearBuffer(final int size) {
		super();
		if (size < 1) {
			throw new IllegalArgumentException(
					"Size must be at least 1 element.");
		}
		buffer = (T[]) new Object[size];
	}

	/**
	 * Rotiert den Inhalt um eins nach rechts. Beispiel: Vor Aufruf: A - B - C
	 * Nach Aufruf: C - A - B
	 */
	public void rotateRight() {
		int loop;
		final T saved = buffer[size() - 1];
		for (loop = size() - 1; loop > 0; --loop) {
			buffer[loop] = buffer[loop - 1];
		}
		buffer[loop] = saved;
	}

	/**
	 * Schiebt den Inhalt um eins nach rechts. Beispiel: Vor Aufruf: A - B - C
	 * Nach Aufruf: null - A - B
	 */
	public void shiftRight() {
		rotateRight();
		buffer[0] = null;
	}

	/**
	 * Fügt ein Element an Position 0 hinzu.
	 * 
	 * @param element
	 *            das Element
	 */
	public boolean add(final T element) {
		rotateRight();
		buffer[0] = element;
		return true;
	}

	/**
	 * Liefert die Größe des Schieberegisters.
	 * 
	 * @return die Größe in Anzahl Elemente
	 */
	public int size() {
		return buffer.length;
	}

	@Override
	public boolean addAll(final Collection<? extends T> c) {
		if (null != c && c.size() <= size()) {
			for (final T o : c) {
				add(o);
			}
			return true;
		}
		return false;
	}

	@Override
	public void clear() {
		int loop;
		for (loop = 0; loop < size(); ++loop) {
			buffer[loop] = null;
		}
	}

	@Override
	public boolean contains(final Object o) {
		int loop;
		for (loop = 0; loop < size(); ++loop) {
			if (null != buffer[loop] && buffer[loop].equals(o)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean containsAll(final Collection<?> c) {
		if (null != c) {
			for (final Object o : c) {
				if (!contains(o)) {
					return false;
				}
			}
		}
		return true;
	}

	@Override
	public boolean isEmpty() {
		int loop;
		for (loop = 0; loop < size(); ++loop) {
			if (null != buffer[loop]) {
				return false;
			}
		}
		return true;
	}

	@Override
	public Iterator<T> iterator() {
		return new Iterator<T>() {

			private int pos = 0;

			@Override
			public boolean hasNext() {
				return pos < size();
			}

			@Override
			public T next() {
				return buffer[pos++];
			}

			@Override
			public void remove() {
				buffer[pos++] = null;
			}
		};
	}

	@Override
	public boolean remove(final Object o) {
		int loop;
		for (loop = 0; loop < size(); ++loop) {
			if (null != buffer[loop] && buffer[loop].equals(o)) {
				buffer[loop] = null;
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean removeAll(final Collection<?> c) {
		boolean changed = false;
		if (null != c) {
			for (final Object o : c) {
				changed |= remove(o);
			}
		}
		return changed;
	}

	@Override
	public boolean retainAll(final Collection<?> c) {
		return false;
	}

	@Override
	public Object[] toArray() {
		return buffer;
	}

	@Override
	public <T> T[] toArray(final T[] a) {
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return Arrays.asList(buffer).toString();
	}
}
