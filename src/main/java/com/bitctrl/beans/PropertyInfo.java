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

package com.bitctrl.beans;

/**
 * Diese Schnittstelle beschreibt den Namen und die Aufzählungsposition einer
 * Property fest. Die Implementierung erfolgt am besten mit Hilfe einer {@code
 * enum}-Klasse, dies sichert die einmalig von Name und Position.
 * 
 * @author BitCtrl Systems GmbH, Falko Schumann
 * @version $Id: PropertyInfo.java 13881 2008-11-06 13:37:21Z Schumann $
 */
public interface PropertyInfo {

	/**
	 * Gibt den Namen der Property zurück.
	 * 
	 * @return der Propertyname.
	 */
	String name();

	/**
	 * Vergleicht zwei Infos anhand des Propertynamens.
	 * 
	 * @param other
	 *            eine andere {@link PropertyInfo}.
	 * @return {@code true}, wenn beide identisch sind.
	 * @see #name()
	 */
	boolean equals(Object other);

}
