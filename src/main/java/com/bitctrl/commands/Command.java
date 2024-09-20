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

package com.bitctrl.commands;

/**
 * Ein ausführbarer Befehl. Der Befehl wird entweder synchron (blockiernd) mit
 * {@link #run()} oder asynchron (nicht blockierend) mit {@link #exec()}
 * ausgeführt werden.
 * 
 * @author BitCtrl Systems GmbH, Falko Schumann
 */
public interface Command extends Runnable {

	/**
	 * Führt den Befehl aus. Die Methode blockiert während der Ausführung des
	 * Befehls. Die Methode muss regelmäßig mit {@link #isCanceled()} prüfen, ob der
	 * Befehl abgebrochen werden soll.
	 * 
	 * @see #isCanceled()
	 */
	@Override
	void run();

	/**
	 * Führt den Befehl im Hintergrund aus. Die Methode blockiert nicht während der
	 * Ausführung des Befehls, da die {@link #run()}-Methode dazu in einem eigenen
	 * Thread gestartet wird.
	 * 
	 * @see #isCanceled()
	 */
	void exec();

	/**
	 * Signalisiert dem Befehl, dass er abgebrochen werden soll.
	 */
	void cancel();

	/**
	 * Fragt, ob der Befehl abgebrochen werden soll.
	 * 
	 * @return {@code true}, wenn der Befehl abgebrochen werden soll.
	 * @see #exec()
	 */
	boolean isCanceled();

	/**
	 * Registriert einen Listener.
	 * 
	 * @param l der Listener.
	 */
	void addCommandFinishedListener(CommandFinishedListener l);

	/**
	 * Meldet einen Listener wieder ab.
	 * 
	 * @param l der Listener.
	 */
	void removeCommandFinishedListener(CommandFinishedListener l);

}
