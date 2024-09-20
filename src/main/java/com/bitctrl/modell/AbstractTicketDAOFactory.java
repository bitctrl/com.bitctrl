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
 * Implementiert die Ticketfähigkeit.
 * 
 * @author BitCtrl Systems GmbH, Falko Schumann
 */
@Deprecated(since = "3.0.0", forRemoval = true)
public abstract class AbstractTicketDAOFactory extends AbstractDAOFactory implements TicketDAOFactory {

	private DAOTicket ticket;

	@Override
	public DAOTicket getTicket() {
		return ticket;
	}

	/**
	 * Legt das Ticket für die DAO-Factory fest.
	 * <p>
	 * <em>Hinweis:</em> Das Ticket kann nur einmal gesetzt und dann nicht mehr
	 * verändert werden.
	 * 
	 * @param ticket das Ticket, welches die Factory verwendet soll.
	 */
	public void setTicket(final DAOTicket ticket) {
		if (this.ticket != null) {
			throw new IllegalStateException("Ticket was already assigned.");
		}
		this.ticket = ticket;
	}

	@Override
	public <T> TicketDAO<T, ?> findDAO(final Class<T> type) {
		return (TicketDAO<T, ?>) super.findDAO(type);
	}

}
