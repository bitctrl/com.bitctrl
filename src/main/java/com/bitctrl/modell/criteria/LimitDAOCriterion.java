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
 * Repräsentiert eine Begrenzung der DAO Ergebnisse (analog LIMIT und OFFSET bei
 * SQL).
 * 
 * @author BitCtrl Systems GmbH, Görlitz
 */
public class LimitDAOCriterion implements DAOCriterion {

	private final long offset;
	private final int limit;

	/**
	 * Initialisiert das Criteria mit dem Offset und der Anzahl Datenobjekte.
	 * 
	 * @param offset
	 *            der Offset (inklusive) ab dem die Ergebnisse geliefert werden
	 *            sollen.
	 * @param limit
	 *            die maximale Anzahl an Ergebnissen.
	 */
	public LimitDAOCriterion(final long offset, final int limit) {
		this.offset = offset;
		this.limit = limit;
	}

	/**
	 * Initialisiert das Criteria mit dem Offset und der maximal möglichen
	 * Anzahl Datenobjekte.
	 * 
	 * @param offset
	 *            der Offset (inklusive) ab dem die Ergebnisse geliefert werden
	 *            sollen.
	 */
	public LimitDAOCriterion(final long offset) {
		this(offset, Integer.MAX_VALUE);
	}

	/**
	 * Initialisiert das Criteria mit dem Offset des ersten Elements und der
	 * Anzahl Datenobjekte.
	 * 
	 * @param limit
	 *            die maximale Anzahl an Ergebnissen.
	 */
	public LimitDAOCriterion(final int limit) {
		this(0, limit);
	}

	/**
	 * Liefert den Offset, ab dem Datenobjekte geliefert werden sollen. Das
	 * Element, welches der Offset addressiert, ist im Ergebnis enthalten.
	 * 
	 * @return der Offset.
	 */
	public long getOffset() {
		return offset;
	}

	/**
	 * Liefert das Limit, also die maximale Anzahl von Datenobjekten im
	 * Ergebnis.
	 * 
	 * @return das Limit.
	 */
	public int getLimit() {
		return limit;
	}

	@Override
	public String toString() {
		return getClass().getName() + "[offset=" + offset + ", limit=" + limit
				+ "]";
	}

}
