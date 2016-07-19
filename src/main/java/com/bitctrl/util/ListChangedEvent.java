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

import java.util.EventObject;
import java.util.List;

/**
 * Das Event beschreibt die Änderung einer Liste.
 * 
 * @author BitCtrl Systems GmbH, Schumann
 * @version $Id: ListChangedEvent.java 13881 2008-11-06 13:37:21Z Schumann $
 * @param <T>
 *            der Typ der geänderten Liste
 */
public class ListChangedEvent<T> extends EventObject {

	private static final long serialVersionUID = 1L;
	private final int indexFrom;
	private final int indexTo;
	private final List<T> added;
	private final List<T> removed;

	/**
	 * Initialisierung.
	 * 
	 * @param source
	 *            die Quelle des Events.
	 * @param indexFrom
	 *            der erste Index der Änderungen
	 * @param indexTo
	 *            der letzte Index der Änderungen
	 * @param added
	 *            die Liste der hinzugefügten Elemente
	 * @param removed
	 *            die Liste der entfernten Elemente
	 */
	public ListChangedEvent(final Object source, final int indexFrom, final int indexTo, final List<T> added,
			final List<T> removed) {
		super(source);

		this.indexFrom = indexFrom;
		this.indexTo = indexTo;
		this.added = added;
		this.removed = removed;
	}

	/**
	 * @return the indexFrom
	 */
	public int getIndexFrom() {
		return indexFrom;
	}

	/**
	 * @return the indexTo
	 */
	public int getIndexTo() {
		return indexTo;
	}

	/**
	 * @return the added
	 */
	public List<T> getAdded() {
		return added;
	}

	/**
	 * @return the removed
	 */
	public List<T> getRemoved() {
		return removed;
	}

}
