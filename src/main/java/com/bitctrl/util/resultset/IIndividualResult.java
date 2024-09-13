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
 * Individuelles Ergebnis innerhalb einer Ergebnismenge.
 * 
 * @author BitCtrl Systems GmbH, Albrecht Uhlmann
 * 
 * @param <T> der Typ des individuellen Ergebnis, z.B. Long
 * @param <B> der Typ des zugrunde liegenden Datensatzes, z.B. SQLRow
 */
public interface IIndividualResult<T, B> {

	/**
	 * Liefert den aktuellen Wert.
	 * 
	 * @return der Wert
	 */
	T getCurrentValue();

	/**
	 * Liefert den kompletten Datensatz, aus dem getCurrentValue() extrahiert wurde.
	 * Die Methode dient dazu, in Systemen, auf denen man sich nicht auf einen
	 * individuellen Datenpunkt anmelden kann, das zugrundeliegende Datenobjekt zu
	 * bekommen (z.B. eine Data oder ResultData im Datenverteiler oder eine SQL-Row
	 * bei einer Datenbank.
	 * 
	 * @return der Basiswert.
	 */
	B getCurrentBaseSetValue();

	/**
	 * Liefert einen Namen für dieses individuelle Ergebnisobjekt.
	 * 
	 * @return der Name
	 */
	String getName();

	/**
	 * Trennt dieses individuelle Ergebnisobjekt von seiner Datenquelle.
	 */
	void dispose();
}
