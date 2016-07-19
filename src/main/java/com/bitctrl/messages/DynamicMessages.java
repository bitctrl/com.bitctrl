/**
 * Rahmenwerk-Plug-in "Gangliniendarstellung fuer PuA-Protokolle"
 * Copyright (C) 2009 BitCtrl Systems GmbH 
 *  
 * This program is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later
 * version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * this program; if not, write to the Free Software Foundation, Inc., 51
 * Franklin Street, Fifth Floor, Boston, MA 02110-1301, USA.
 * 
 * Contact Information:
 * BitCtrl Systems GmbH
 * Weissenfelser Strasse 67
 * 04229 Leipzig
 * Phone: +49 341-490670
 * mailto: info@bitctrl.de
 */
package com.bitctrl.messages;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * Dynamische Schnittstelle zum Auslesen des ResourcenBundles.
 * 
 * @author BitCtrl Systems GmbH, Enrico Schnepel
 * @version $Id: $
 */
public class DynamicMessages {

	private final ResourceBundle resourceBundle;

	private DynamicMessages(final ResourceBundle resourceBundle) {
		this.resourceBundle = resourceBundle;
	}

	public static DynamicMessages getInstance(
			final ResourceBundle resourceBundle) {
		return new DynamicMessages(resourceBundle);
	}

	/**
	 * Liest den Wert zu einem Schluessel aus der Resource.
	 * 
	 * @param key
	 *            der Schluessel
	 * @return Wenn der Schluessel nicht gefunden wird, gibt die Funktion den
	 *         String "!Schluessel!" zurueck.
	 */
	public String getString(final String key) {
		try {
			return resourceBundle.getString(key);
		} catch (final MissingResourceException e) {
			return '!' + key + '!';
		}
	}

	/**
	 * Liest den Wert zu einem Schluessel aus der Resource.
	 * 
	 * @param key
	 *            der Schluessel
	 * @return Wenn der Schluessel nicht gefunden wird, gibt die Funktion
	 *         <b>null</b> zurueck.
	 */
	public String getStringOrNull(final String key) {
		try {
			return resourceBundle.getString(key);
		} catch (final MissingResourceException e) {
			return null;
		}
	}

	public String encloseWithCommas(String str) {
		if (null == str) {
			str = ""; //$NON-NLS-1$
		} else {
			str = "," + str.replaceAll("[^\\-_A-Za-z]", ",") + ",";
		}
		return str;
	}
}
