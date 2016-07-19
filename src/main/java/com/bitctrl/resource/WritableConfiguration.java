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

package com.bitctrl.resource;

/**
 * Schnittstelle für eine Konfiguration, die lesbar und schreibbar ist.
 * 
 * @author BitCtrl Systems GmbH, Falko Schumann
 */
public interface WritableConfiguration extends ReadOnlyConfiguration {

	/**
	 * Öffnet ein neues Feld zum Schreiben.
	 * 
	 * @param name
	 *            der Name des Felds.
	 */
	void beginWriteArray(final String name);

	/**
	 * Öffnet ein neues Feld zum Schreiben.
	 * 
	 * @param name
	 *            der Name des Felds.
	 * @param size
	 *            die Gr��e die das Feld haben soll.
	 */
	void beginWriteArray(final String name, final int size);

	/**
	 * Legt einen booleschen Wert unter dem Schlüssel ab.
	 * 
	 * @param key
	 *            der Schlüssel.
	 * @param value
	 *            der Wert.
	 */
	void set(final String key, final boolean value);

	/**
	 * Legt einen Gleitkommawert unter dem Schlüssel ab.
	 * 
	 * @param key
	 *            der Schlüssel.
	 * @param value
	 *            der Wert.
	 */
	void set(final String key, final double value);

	/**
	 * Legt einen Ganzzahlwert unter dem Schlüssel ab.
	 * 
	 * @param key
	 *            der Schlüssel.
	 * @param value
	 *            der Wert.
	 */
	void set(final String key, final int value);

	/**
	 * Legt einen Ganzzahlwert unter dem Schlüssel ab.
	 * 
	 * @param key
	 *            der Schlüssel.
	 * @param value
	 *            der Wert.
	 */
	void set(final String key, final long value);

	/**
	 * Legt einen Stringwert unter dem Schlüssel ab.
	 * 
	 * @param key
	 *            der Schlüssel.
	 * @param value
	 *            der Wert.
	 */
	void set(final String key, final String value);

}
