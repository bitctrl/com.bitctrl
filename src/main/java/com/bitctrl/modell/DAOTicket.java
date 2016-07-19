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

package com.bitctrl.modell;

/**
 * Repräsentiert ein DAO-Ticket. Alle DAOs, die über das Ticket bezogen werden
 * arbeiten im selben Kontext. Auch ist möglich mit Hilfe des Tickts Operationen
 * atomar als Transaktion auszuführen.
 * 
 * @author BitCtrl Systems GmbH, Falko Schumann
 */
public interface DAOTicket {

	/**
	 * Gibt die DAO-Factory zurück, die exklusiv für dieses Ticket verwendet
	 * werden muss.
	 * 
	 * @return die DAO-Factory für dieses Ticket.
	 */
	DAOFactory getDAOFactory();

	/**
	 * Import ein Objekt, welches in einem anderen Ticket angelegt wurde.
	 * 
	 * @param object
	 *            ein "fremdes" Objekt.
	 * @throws DAOException
	 *             bei einem Fehler.
	 */
	void importFromOtherTicket(final Object object) throws DAOException;

	/**
	 * Sucht das übergebene Objekt in der Session des Tickets. Der Zustand des
	 * übergebenen Objekts wird dann in das der Ticketsession kopiert.
	 * 
	 * @param <T>
	 *            der Typ des Objekts um Rückgabewert anzupassen. *
	 * @param object
	 *            ein Objekt.
	 * @return das Objekt auf das der Schlüssel passt.
	 * @throws DAOException
	 *             bei einem Fehler.
	 */
	<T> T merge(final T object) throws DAOException;

	/**
	 * Prüft, ob das Ticket bereits freigegeben wurde.
	 * 
	 * @return {@code true}, wenn das Ticket schon freigegeben ist.
	 */
	boolean isDestroyed();

	/**
	 * Gibt das Ticket wieder frei.
	 */
	void destroy();

	/**
	 * Startet eine neue Transaktion. Alle nachfolgenden Operationen werden in
	 * dieser Transaktion ausgeführt.
	 * 
	 * @throws DAOException
	 *             bei einem Fehler beim Anlegend er Transaktion.
	 * @see #commitTransaction()
	 * @see #rollbackTransaction()
	 */
	void beginTransaction() throws DAOException;

	/**
	 * Beendet die aktuelle Transaktion und schreibt alle gepufferten
	 * Operationen.
	 * 
	 * @throws DAOException
	 *             bei einem Fehler beim Ausführen der Transaktion.
	 * @see #beginTransaction()
	 * @see #rollbackTransaction()
	 */
	void commitTransaction() throws DAOException;

	/**
	 * Führt ein Rollback auf der aktuellen Transaktion aus.
	 * 
	 * @throws DAOException
	 *             bei einem Fehler beim Rollback der Transaktion
	 * @see #beginTransaction()
	 * @see #commitTransaction()
	 */
	void rollbackTransaction() throws DAOException;

}
