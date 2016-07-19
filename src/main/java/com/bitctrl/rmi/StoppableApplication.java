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

package com.bitctrl.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Schnittstelle für Applikationen die sich von außen beenden lassen sollen.
 * 
 * @author BitCtrl Systems GmbH, Falko Schumann
 */
public interface StoppableApplication extends Remote {

	/**
	 * Wird aufgerufen, wenn die Applikation beendet werden soll. Die
	 * Applikation kann noch Aufräumarbeiten erledigen. Am Ende der Methode muss
	 * sich die Applikation an der RMI-Registry abmelden und sich anschließend
	 * selbst beenden.
	 * 
	 * @throws RemoteException
	 *             bei Fehlern beim RMI-Zugriff.
	 * @see RemoteTools#unbind(String)
	 */
	void exit() throws RemoteException;

	/**
	 * Gibt den Namen zurück, unter dem sich die Applikation als Service bei der
	 * RMI-Registry anmeldet.
	 * 
	 * @return der in der RMI-Registry eingetragene Name der Applikation.
	 * @throws RemoteException
	 *             bei Fehlern beim RMI-Zugriff.
	 * @see RemoteTools#init(StoppableApplication, String[])
	 */
	String getServiceName() throws RemoteException;

}
