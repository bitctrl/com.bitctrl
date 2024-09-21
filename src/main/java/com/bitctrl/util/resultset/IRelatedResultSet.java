/*
 * BitCtrl- Funktionsbibliothek
 * Copyright (C) 2007-2010 BitCtrl Systems GmbH 
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
package com.bitctrl.util.resultset;

/**
 * Eine Menge von individuellen Ergebnissen.
 * 
 * @author BitCtrl Systems GmbH, Albrecht Uhlmann
 * 
 * @param <T> der Datentyp der Ergebnisse
 * @param <B> der Typ des zugrunde liegenden Datensatzes, z.B. SQLRow
 */
public interface IRelatedResultSet<T, B> {

	/**
	 * Ein individuelles Element hat einen neuen Wert geliefert.
	 * 
	 * @param element das Element, welches den Wert zur Verfügung stellt.
	 * @param wert    der Wert
	 */
	void neuerWert(IIndividualResult<T, B> element, T wert);

	/**
	 * Trennt alle individuellen Objekte in diesem Set von ihrer Datenquelle.
	 */
	void dispose();
}
