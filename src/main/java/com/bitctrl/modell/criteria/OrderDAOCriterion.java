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
package com.bitctrl.modell.criteria;

/**
 * Repräsentiert die Sortierung der Ergebnisse einer DAO nach dem Inhalte einer
 * Property.
 * 
 * @author BitCtrl Systems GmbH, Goerlitz
 */
public class OrderDAOCriterion implements DAOCriterion {

	private final String propertyName;
	private final boolean ascending;

	/**
	 * Erstellt eine aufsteigende Sortierung für Ergebnisse einer DAO.
	 * 
	 * @param propertyName
	 *            der Spaltenname ueber den sortiert werden soll.
	 */
	public OrderDAOCriterion(final String propertyName) {
		this(propertyName, true);
	}

	/**
	 * Erstellt eine Sortierung für Ergebnisse einer DAO.
	 * 
	 * @param propertyName
	 *            der Spaltenname ueber den sortiert werden soll.
	 * @param ascending
	 *            <code>true</code>, wenn aufsteigend sortiert werden soll,
	 *            sonst <code>false</code>.
	 */
	public OrderDAOCriterion(final String propertyName, final boolean ascending) {
		this.propertyName = propertyName;
		this.ascending = ascending;
	}

	/**
	 * Liefert den Propertynamen über welchen sortiert werden soll.
	 * 
	 * @return der Propertynamen über welchen sortiert werden soll.
	 */
	public String getPropertyName() {
		return propertyName;
	}

	/**
	 * Liefert die Sortierrichtiung.
	 * 
	 * @return <code>true</code>, wenn aufsteigend sortiert werden soll, sonst
	 *         <code>false</code>;
	 */
	public boolean isAscending() {
		return ascending;
	}

	@Override
	public String toString() {
		return getClass().getName() + "[propertyName=" + propertyName
				+ ", ascending=" + ascending + "]";
	}

}
