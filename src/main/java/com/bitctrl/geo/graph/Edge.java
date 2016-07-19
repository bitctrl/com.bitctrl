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

package com.bitctrl.geo.graph;

/**
 * Repräsentiert eine Kante in einem Graphen.
 * 
 * @author BitCtrl Systems GmbH, Falko Schumann
 * @version $Id$
 */
public interface Edge {

	/**
	 * Gibt den Namen der Kante zurück.
	 * 
	 * @return der Kantenname.
	 */
	String getName();

	/**
	 * Legt den Namen der Kante fest.
	 * 
	 * @param name
	 *            der neue Kantenname.
	 */
	void setName(String name);

	/**
	 * Gibt den Startknoten der Kante zurück.
	 * 
	 * @return der Startknoten, niemals {@code null}.
	 */
	Node getSource();

	/**
	 * Gibt den Endknoten der Kante zurück.
	 * 
	 * @return der Endknoten, niemals {@code null}.
	 */
	Node getTarget();

}
