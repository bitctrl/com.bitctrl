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

import javax.swing.event.EventListenerList;

/**
 * Implementiert die allgemeinen Methoden eines Befehls.
 * 
 * @author BitCtrl Systems GmbH, Falko Schumann
 */
public abstract class AbstractCommand implements Command {

	private final EventListenerList listener = new EventListenerList();
	private boolean canceled = false;

	@Override
	public void exec() {
		new Thread(this, toString()).start();
	}

	@Override
	public void cancel() {
		canceled = true;
	}

	@Override
	public boolean isCanceled() {
		return canceled;
	}

	@Override
	public void addCommandFinishedListener(final CommandFinishedListener l) {
		listener.add(CommandFinishedListener.class, l);
	}

	@Override
	public void removeCommandFinishedListener(final CommandFinishedListener l) {
		listener.remove(CommandFinishedListener.class, l);
	}

	/**
	 * Informiert die angemeldeten Listener über das Ende des Befehls.
	 * 
	 * @param successful {@code true}, wenn der Befehl erfolgreich beendet wurde.
	 * @param message    eine Vollzugsmittuilung oder Fehlernachricht.
	 */
	protected void fireCommandFinished(final boolean successful, final String message) {
		final CommandFinishedEvent e = new CommandFinishedEvent(this, successful, message);

		for (final CommandFinishedListener l : listener.getListeners(CommandFinishedListener.class)) {
			l.commandFinished(e);
		}
	}

}
