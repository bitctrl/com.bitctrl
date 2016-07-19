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

package com.bitctrl.util;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Iterator, der über Feld läuft.
 * 
 * @author BitCtrl Systems GmbH, Falko Schumann
 * @version $Id: FieldIterator.java 9128 2008-05-23 15:14:20Z Schumann $
 * @param <T>
 *            der Feldtyp.
 *            
 * @deprecated use foreach loop           
 */
@Deprecated
public class FieldIterator<T> implements Iterator<T> {

	private final T[] data;
	private int cursor = 0;

	/**
	 * Initialisiert das Objekt, indem die Referenz gesichert wird.
	 * 
	 * @param data
	 *            das Feld.
	 */
	public FieldIterator(final T... data) {
		this.data = data;
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean hasNext() {
		return data != null ? cursor < data.length : false;
	}

	/**
	 * {@inheritDoc}
	 */
	public T next() {
		if (!hasNext()) {
			throw new NoSuchElementException();
		}
		return data[cursor++];
	}

	/**
	 * Wirft immer eine {@link UnsupportedOperationException}.
	 * 
	 * {@inheritDoc}
	 */
	public void remove() {
		throw new UnsupportedOperationException();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		if (hasNext()) {
			return getClass().getName() + "[cursor=" + cursor + ", next="
					+ data[cursor] + "]";
		}
		return getClass().getName() + "[cursor=" + cursor + ", next=EOL]";
	}

}
