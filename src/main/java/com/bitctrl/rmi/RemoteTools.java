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

import java.net.SocketException;
import java.rmi.AlreadyBoundException;
import java.rmi.ConnectException;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.UnmarshalException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.RemoteServer;
import java.rmi.server.UnicastRemoteObject;

/**
 * Tools für die Nutzung der RMI-Schnittstelle.
 * 
 * @author BitCtrl Systems GmbH, Falko Schumann
 */
public final class RemoteTools {

	/**
	 * Registriert eine Anwendung bei der RMI-Registry.
	 * 
	 * @param name
	 *            der Name unter dem die Applikation registriert werden soll.
	 *            Der Name muss in der gesamten Registry eindeutig sein.
	 * @param service
	 *            die zu registrierende Applikation.
	 * @throws RemoteException
	 *             bei Fehlern beim RMI-Zugriff.
	 * @throws AlreadyBoundException
	 *             wenn unter dem Namen bereist ein Dienst registriert wurde.
	 */
	public static void bind(final String name, final Remote service)
			throws RemoteException, AlreadyBoundException {
		Registry registry;
		Remote stub;

		RemoteServer.setLog(System.out);
		stub = UnicastRemoteObject.exportObject(service, 0);
		registry = LocateRegistry.getRegistry();
		registry.bind(name, stub);
	}

	/**
	 * Initialisiert eine beendbare Applikation. Ist eines der
	 * Kommandozeilenargumente {@code [-|--|/]stop} wird die
	 * {@link StoppableApplication#exit()}-Methode der Applikation aufgerufen.
	 * 
	 * @param application
	 *            die zu registrierende Applikation.
	 * @param args
	 *            die Argumente der {@code main()}-Methode.
	 * @throws RemoteException
	 *             bei Fehlern beim RMI-Zugriff.
	 */
	public static void init(final StoppableApplication application,
			final String[] args) throws RemoteException {
		for (final String arg : args) {
			if (arg.equals("stop") || arg.equals("-stop")
					|| arg.equals("--stop") || arg.equals("/stop")) {
				Registry registry;
				StoppableApplication stub;

				registry = LocateRegistry.getRegistry();
				try {
					stub = (StoppableApplication) registry.lookup(application
							.getServiceName());
					stub.exit();
				} catch (final NotBoundException ex) {
					throw new IllegalStateException(ex);
				} catch (final UnmarshalException ex) {
					if (ex.getCause() instanceof SocketException) {
						// Verbindungsabruch, weil Applikation beendet wurde
						System.exit(0);
					} else {
						throw ex;
					}
				}

				assert false : "Dieser Code darf nicht erreicht werden.";
			}
		}

		try {
			rebind(application.getServiceName(), application);
		} catch (final ConnectException ex) {
			System.err
					.println("Anwendungssteuerung per RMI steht nicht zur Verfügung: "
							+ ex);
		}
	}

	/**
	 * Registriert eine Anwendung bei der RMI-Registry, eine bereits vorhandene
	 * Registrierung wird überschrieben.
	 * 
	 * @param name
	 *            der Name unter dem die Applikation registriert werden soll.
	 *            Der Name muss in der gesamten Registry eindeutig sein.
	 * @param service
	 *            die zu registrierende Applikation.
	 * @throws RemoteException
	 *             bei Fehlern beim RMI-Zugriff.
	 */
	public static void rebind(final String name, final Remote service)
			throws RemoteException {
		Registry registry;
		Remote stub;
		boolean exists;

		stub = UnicastRemoteObject.exportObject(service, 0);
		registry = LocateRegistry.getRegistry();
		try {
			registry.lookup(name);
			exists = true;
		} catch (final NotBoundException ex) {
			exists = false;
		}
		if (exists) {
			System.err.println("Unter dem Namen \"" + name
					+ "\" war bereits ein RMI-Service registriert.");
		}
		registry.rebind(name, stub);
	}

	/**
	 * Meldet eine Anwendung bei der RMI-Registry wieder ab.
	 * 
	 * @param name
	 *            der Name unter dem die Applikation registriert wurde.
	 * @throws RemoteException
	 *             bei Fehlern beim RMI-Zugriff.
	 * @throws NotBoundException
	 *             wenn kein Service unter dem Namen registriert wurde.
	 */
	public static void unbind(final String name) throws RemoteException,
			NotBoundException {
		Registry registry;

		registry = LocateRegistry.getRegistry();
		registry.unbind(name);
	}

	private RemoteTools() {
		// Konstruktor verstecken
	}

}
