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
 * Implementiert allgemeine Funktionen einer Ticket-DAO.
 * 
 * @author BitCtrl Systems GmbH, Falko Schumann
 * 
 * @param <T>
 *            der Typ der Datenobjekte.
 * @param <ID>
 *            der Schlüssel für die Datenobjekte.
 */
public abstract class AbstractTicketDAO<T, ID> extends AbstractDAO<T, ID> implements TicketDAO<T, ID> {

	private DAOTicket ticket;

	public DAOTicket getTicket() {
		return ticket;
	}

	/**
	 * Legt das Ticket für die DAO fest.
	 * <p>
	 * <em>Hinweis:</em> Das Ticket kann nur einmal gesetzt und dann nicht mehr
	 * verändert werden.
	 * 
	 * @param ticket
	 *            das Ticket, welches die DAO verwendet soll.
	 */
	public void setTicket(final DAOTicket ticket) {
		if (this.ticket != null) {
			throw new IllegalStateException("Ticket was already assigned.");
		}
		this.ticket = ticket;
	}

}
