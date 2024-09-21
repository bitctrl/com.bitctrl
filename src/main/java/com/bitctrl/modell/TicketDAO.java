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
 * Erweiterung der DAO um das Konzept des Tickets. Für jede Aktion wird ein
 * Ticket benötigt. Damit Datenobjekte zwischen verschiedenen Operationen
 * ausgetauscht werden können, müssen diese alle das selbe Ticket verwenden.
 * 
 * @author BitCtrl Systems GmbH, krosse
 * 
 * @param <T>  der Typ der Datenobjekte.
 * @param <ID> der Schlüssel für die Datenobjekte.
 */
@Deprecated(since = "3.0.0", forRemoval = true)
public interface TicketDAO<T, ID> extends DAO<T, ID> {

	/**
	 * Gibt das Ticket zurück, welches diese DAO zur Sessionverwaltung verwendet.
	 * Das Ticket darf sich nach der Initialisierung nicht mehr ändern.
	 * 
	 * @return das Ticket dieser DAO.
	 */
	DAOTicket getTicket();

}
