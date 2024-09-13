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
 * Repräsentiert einen Knoten in einem Graphen.
 * 
 * @author BitCtrl Systems GmbH, Falko Schumann
 */
public interface Node {

	/**
	 * Gibt den Namen des Knotens zurück.
	 * 
	 * @return der Knotenname, niemals {@code null}.
	 */
	String getName();

	/**
	 * Legt den Namen des Knotens fest.
	 * 
	 * @param name der neue Knotenname.
	 */
	void setName(String name);

	/**
	 * Vergleicht diesen Knoten mit einem anderen. Die Standardimplementierung
	 * vergleicht die Namen der beiden Knoten.
	 * 
	 * @param other ein anderer Knoten.
	 * @return {@code true}, wenn dieser Knoten und der andere gleich sind.
	 */
	@Override
	boolean equals(Object other);

}
