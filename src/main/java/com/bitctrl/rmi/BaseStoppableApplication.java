/*
 * Java Common Library
 * Copyright (c) 2008 BitCtrl Systems GmbH
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
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

import java.rmi.NotBoundException;
import java.rmi.RemoteException;

/**
 * Standardimplementierung der Schnittstelle.
 * 
 * @author BitCtrl Systems GmbH, Falko Schumann
 * @version $Id: BaseStoppableApplication.java 13881 2008-11-06 13:37:21Z Schumann $
 */
public class BaseStoppableApplication implements StoppableApplication {

	/**
	 * Ruft {@code System.exit(0)} auf.
	 * 
	 * {@inheritDoc}
	 */
	public void exit() throws RemoteException {
		try {
			RemoteTools.unbind(getServiceName());
		} catch (final NotBoundException ex) {
			ex.printStackTrace();
		}
		System.exit(0);
	}

	/**
	 * Gibt den Klassennamen samt Package-Struktur zurück.
	 * 
	 * {@inheritDoc}
	 */
	public String getServiceName() {
		return getClass().getName();
	}

}
