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

package com.bitctrl.geo.graph;

/**
 * Basisimplementierung eines Knoten.
 * <p>
 * Die notwendige Logik der Kanten ist aus Gründen der Performance mit {@code
 * protected}-Membern anstelle von Gettern und Settern ausgelegt.
 * 
 * @author BitCtrl Systems GmbH, Falko Schumann
 */
public class BaseNode implements Node {

	private String name;

	/** Der erste Eingangsbogen des Knoten. */
	protected BaseEdge firstIn;

	/** Der erste Ausgangsbogen des Knoten. */
	protected BaseEdge firstOut;

	/**
	 * Initialisiert den Knoten mit seinem Namen.
	 * 
	 * @param name
	 *            der Knotenname.
	 */
	public BaseNode(final String name) {
		this.name = name;
	}

	public String getName() {
		assert name != null;
		return name;
	}

	public void setName(final String name) {
		if (name == null) {
			throw new NullPointerException("name must not be null");
		}
		this.name = name;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj instanceof Node) {
			final Node other = (Node) obj;
			return getName().equals(other.getName());
		}
		return false;
	}

	@Override
	public int hashCode() {
		throw new UnsupportedOperationException();
	}
	
	/**
	 * Gibt den Namen des Knoten zurück.
	 * 
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return getName();
	}

}
