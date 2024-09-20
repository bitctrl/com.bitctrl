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
 * Basisimplementierung einer Kante.
 * <p>
 * Die notwendige Logik der Knoten und Kanten ist aus Gründen der Performance
 * mit {@code protected}-Membern anstelle von Gettern und Settern ausgelegt.
 * 
 * @author BitCtrl Systems GmbH, Falko Schumann
 */
public class BaseEdge implements Edge {

	private String name;

	/** Der Startknoten des Bogens. */
	protected BaseNode source;

	/** Der Endknoten des Bogens. */
	protected BaseNode target;

	/** Gibt den nächsten Eingangsbogen des Endknoten zurück. */
	protected BaseEdge nextIn;

	/** Gibt den nächsten Ausgangsbogen des Ausgangsknoten zurück. */
	protected BaseEdge nextOut;

	/**
	 * Standardkonstruktor.
	 */
	public BaseEdge() {
		// nix zu tun
	}

	/**
	 * Initialisiert die Kante mit ihren Knoten.
	 * 
	 * @param source der Startknoten.
	 * @param target der Endknoten.
	 */
	public BaseEdge(final BaseNode source, final BaseNode target) {
		this.source = source;
		this.target = target;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public Node getSource() {
		return source;
	}

	@Override
	public Node getTarget() {
		return target;
	}

	@Override
	public void setName(final String name) {
		this.name = name;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj instanceof final Node other) {
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
