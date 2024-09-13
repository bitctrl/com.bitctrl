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

package com.bitctrl.modell;

import java.util.EventObject;

/**
 * Repräsentiert ein Ereignis eines DAO.
 * 
 * @author BitCtrl Systems GmbH, Falko Schumann
 */
@Deprecated(since = "3.0.0", forRemoval = true)
public class DAOEvent extends EventObject {

	private static final long serialVersionUID = 1;

	/** Die Typen einer Änderung. */
	public static enum Type {

		/** Hinzufügen. */
		Add,

		/** Aktualisieren. */
		Update,

		/** Löschen. */
		Delete,

		/**
		 * Unbekannte Änderung. Dieser Typ kann verwendet werden, wenn eine DAO die
		 * Aktualisierung nicht unterscheiden kann, z.&nbsp;B. wenn sich alle Daten der
		 * Quelle geändert haben.
		 */
		Unknown;

	}

	private final Type type;
	private final Object object;

	/**
	 * Erzeugt ein neues Event.
	 * 
	 * @param source die Quelle des Events, in der Regel eine DAO.
	 * @param type   der Typ des Events.
	 * @param object das aktualiserte Objekt.
	 * @deprecated Der Parameter <code>source</code> hat den falschen Typ, besser
	 *             den anderen Konstruktor verwenden!!
	 */
	@Deprecated
	public DAOEvent(final Object source, final Type type, final Object object) {
		super(source);

		if (type == null) {
			throw new IllegalArgumentException("null type");
		}
		this.type = type;
		this.object = object;
	}

	/**
	 * Erzeugt ein neues Event.
	 * 
	 * @param source die Quelle des Events, in der Regel eine DAO.
	 * @param type   der Typ des Events.
	 * @param object das aktualiserte Objekt.
	 */
	public DAOEvent(final DAO<?, ?> source, final Type type, final Object object) {
		super(source);

		if (type == null) {
			throw new IllegalArgumentException("null type");
		}
		this.type = type;
		this.object = object;
	}

	@Override
	public DAO<?, ?> getSource() {
		return (DAO<?, ?>) super.getSource();
	}

	/**
	 * Gibt den Typ der Aktualisierung zurück.
	 * 
	 * @return der Eventtyp.
	 */
	public Type getType() {
		return type;
	}

	/**
	 * Gibt das aktualisierte Objekt zurück. Für den Typ {@link Type#Unknown} kann
	 * der Rückgabewert {@code null} sein.
	 * 
	 * @return das aktualisierte Objekt.
	 */
	public Object getObject() {
		return object;
	}

}
