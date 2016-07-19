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

package com.bitctrl.i18n;

import java.util.ResourceBundle;

/**
 * Schnittstelle für Message-Handler. Die implementierende Klasse sollte eine
 * Enum-Klasse sein, jeder Enum-Eintrag entspricht einem Meldungstyp.
 * 
 * @author BitCtrl Systems GmbH, Falko Schumann
 */
public interface MessageHandler {

	/**
	 * Gibt das Resource-Bundle für diesen Message-Handler zurück.
	 * 
	 * @return Resource-Bundle
	 */
	ResourceBundle getResourceBundle();

	/**
	 * Gibt den Namen des Meldungstyps zurück. Unter diesem Namen wird im
	 * Resource-Bundle nach der Nachricht gesucht.
	 * 
	 * @return der Name.
	 */
	String name();

	/**
	 * Gibt die Nachricht als Text zurück. Eventuell vorhandene Platzhalter
	 * werden nicht ersetzt und bleiben erhalten.
	 * 
	 * @return die Nachricht.
	 */
	String toString();

}
