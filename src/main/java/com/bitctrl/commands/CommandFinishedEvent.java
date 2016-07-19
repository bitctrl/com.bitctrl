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

package com.bitctrl.commands;

import java.util.EventObject;

/**
 * Dieses Event wird getriggert, wenn ein Befehl beendet wurde.
 * 
 * @author BitCtrl Systems GmbH, Falko Schumann
 * @version $Id: CommandFinishedEvent.java 13881 2008-11-06 13:37:21Z Schumann $
 */
public class CommandFinishedEvent extends EventObject {

	private static final long serialVersionUID = 1L;
	private final boolean successful;
	private final String message;

	/**
	 * Initialisiert das Objekt {@code CommandFinishedEvent}.
	 * 
	 * @param source
	 *            die Quelle des Events.
	 * @param successful
	 *            {@code true}, wenn der Befehl erfolgreich beendet wurde.
	 * @param message
	 *            eine Vollzugsmitteilung oder Fehlernachricht.
	 */
	public CommandFinishedEvent(final Command source, final boolean successful,
			final String message) {
		super(source);
		this.successful = successful;
		this.message = message;
	}

	/**
	 * Gibt die Quelle des Events als Befehl zurück.
	 * 
	 * @return der beendete Befehl.
	 */
	public Command getCommand() {
		return (Command) source;
	}

	/**
	 * Flag, ob der Befehl ohne Fehler abgeschlossen wurde.
	 * 
	 * @return {@code true}, wenn der Befehl erfolgreich beendet wurde.
	 */
	public boolean isSuccessful() {
		return successful;
	}

	/**
	 * Gibt eine Nachricht zurück, die etwas über das Ende des Befehls aussagt.
	 * Wurde der Befehl mit einem Fehler beendet, wird die Fehlerbeschreibung
	 * zurückgegeben.
	 * 
	 * @return die Nachricht.
	 */
	public String getMessage() {
		return message;
	}

}
