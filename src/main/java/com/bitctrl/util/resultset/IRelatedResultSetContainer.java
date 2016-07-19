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
 * Wei�enfelser Stra�e 67
 * 04229 Leipzig
 * Phone: +49 341-490670
 * mailto: info@bitctrl.de
 */
package com.bitctrl.util.resultset;

/**
 * Schnittstelle, um sich �ber Vollst�ndigkeit bzw. Timeout von Ergebnismengen
 * benachrichtigen zu lassen.
 * 
 * @author BitCtrl Systems GmbH, Albrecht Uhlmann
 */
public interface IRelatedResultSetContainer {

	/**
	 * Callback, wenn entweder alle Ergebnisse da sind oder der Timeout
	 * abgelaufen ist.
	 * 
	 * Innerhalb des Callbacks kann man noch die individuellen Ergebnisse der
	 * Werte feststellen, also welche Ergebnisse vor Ablauf des Timeouts kamen
	 * und welche fehlten. Nach der R�ckkehr wird die Ergebnismenge
	 * zur�ckgesetzt.
	 * 
	 * @param timeout
	 *            true - Timeout abgelaufen
	 */
	void resultSetComplete(boolean timeout);
}
