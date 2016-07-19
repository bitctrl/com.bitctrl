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

package com.bitctrl.resource;

import java.util.Set;

/**
 * Schnittstelle für eine nur lesbare Konfiguration.
 * 
 * @author BitCtrl Systems GmbH, Falko Schumann
 * @version $Id: ReadOnlyConfiguration.java 12417 2008-09-25 12:19:22Z Schumann $
 */
public interface ReadOnlyConfiguration {

	/** Der Standardwert für den Typ Boolean ist {@value}. */
	boolean DEFAULT_BOOLEAN = false;

	/** Der Standardwert für den Typ Integer ist {@value}. */
	int DEFAULT_INT = 0;

	/** Der Standardwert für den Typ Long ist {@value}. */
	int DEFAULT_LONG = 0;

	/** Der Standardwert für den Typ Double ist {@value}. */
	double DEFAULT_DOUBLE = 0.0;

	/** Der Standardwert für den Typ String ist {@value}. */
	String DEFAULT_STRING = "";

	/**
	 * Öffnet eine neue Gruppe.
	 * 
	 * @param name
	 *            der Name der Gruppe.
	 */
	void beginGroup(final String name);

	/**
	 * Öffnet ein neues Feld zum Lesen.
	 * 
	 * @param name
	 *            der Name des Felds.
	 * @return die Länge des Felds.
	 */
	int beginReadArray(final String name);

	/**
	 * Schließt ein offenes Feld.
	 * 
	 * @param name
	 *            der Name des Felds.
	 */
	void endArray(final String name);

	/**
	 * Schließt eine offene Gruppe.
	 * 
	 * @param name
	 *            der Name der Gruppe.
	 */
	void endGroup(final String name);

	/**
	 * Entspricht {@code getBoolean(key, DEFAULT_BOOLEAN)}.
	 * 
	 * @param key
	 *            ein Schlüssel;
	 * @return der hinterlegt Wert oder, falls nicht vorhanden
	 *         {@link #DEFAULT_BOOLEAN}.
	 * @see #getBoolean(String, boolean)
	 */
	boolean getBoolean(final String key);

	/**
	 * Gibt den Wert der unter dem Schlü;ssel hinterlegt ist als booleschen Wert
	 * zurück.
	 * 
	 * @param key
	 *            der Schlüssel.
	 * @param defaultValue
	 *            ein Wert der zurückgegeben wird, wenn unter dem Schlüssel kein
	 *            Wert hinterlegt ist.
	 * @return der hinterlegt Wert oder, falls nicht vorhanden, der angegebene
	 *         Standardwert.
	 */
	boolean getBoolean(final String key, final boolean defaultValue);

	/**
	 * Entspricht {@code getDouble(key, DEFAULT_DOUBLE)}.
	 * 
	 * @param key
	 *            ein Schlüssel;
	 * @return der hinterlegt Wert oder, falls nicht vorhanden
	 *         {@link #DEFAULT_DOUBLE}.
	 * @see #getDouble(String, double)
	 */
	double getDouble(final String key);

	/**
	 * Gibt den Wert der unter dem Schlüssel hinterlegt ist als Gleitkommazahl
	 * zurück.
	 * 
	 * @param key
	 *            der Schlüssel.
	 * @param defaultValue
	 *            ein Wert der zurückgegeben wird, wenn unter dem Schlüssel kein
	 *            Wert hinterlegt ist.
	 * @return der hinterlegt Wert oder, falls nicht vorhanden, der angegebene
	 *         Standardwert.
	 */
	double getDouble(final String key, final double defaultValue);

	/**
	 * Entspricht {@code getInt(key, DEFAULT_INT)}.
	 * 
	 * @param key
	 *            ein Schlüssel;
	 * @return der hinterlegt Wert oder, falls nicht vorhanden
	 *         {@link #DEFAULT_INT}.
	 * @see #getInt(String, int)
	 */
	int getInt(final String key);

	/**
	 * Gibt den Wert der unter dem Schlüssel hinterlegt ist als Ganzzahl zurück.
	 * 
	 * @param key
	 *            der Schlüssel.
	 * @param defaultValue
	 *            ein Wert der zurückgegeben wird, wenn unter dem Schlüssel kein
	 *            Wert hinterlegt ist.
	 * @return der hinterlegt Wert oder, falls nicht vorhanden, der angegebene
	 *         Standardwert.
	 */
	int getInt(final String key, final int defaultValue);

	/**
	 * Entspricht {@code getInt(key, DEFAULT_LONG)}.
	 * 
	 * @param key
	 *            ein Schlüssel;
	 * @return der hinterlegt Wert oder, falls nicht vorhanden
	 *         {@link #DEFAULT_LONG}.
	 * @see #getLong(String, long)
	 */
	long getLong(final String key);

	/**
	 * Gibt den Wert der unter dem Schlüssel hinterlegt ist als Ganzzahl zurück.
	 * 
	 * @param key
	 *            der Schlüssel.
	 * @param defaultValue
	 *            ein Wert der zurückgegeben wird, wenn unter dem Schlüssel kein
	 *            Wert hinterlegt ist.
	 * @return der hinterlegt Wert oder, falls nicht vorhanden, der angegebene
	 *         Standardwert.
	 */
	long getLong(final String key, final long defaultValue);

	/**
	 * Entspricht {@code getString(key, DEFAULT_STRING)}.
	 * 
	 * @param key
	 *            ein Schlüssel;
	 * @return der hinterlegt Wert oder, falls nicht vorhanden
	 *         {@link #DEFAULT_STRING}.
	 * @see #getString(String, String)
	 */
	String getString(final String key);

	/**
	 * Gibt den Wert der unter dem Schlüssel hinterlegt ist als String zurück.
	 * 
	 * @param key
	 *            der Schlüssel.
	 * @param defaultValue
	 *            ein Wert der zurückgegeben wird, wenn unter dem Schlüssel kein
	 *            Wert hinterlegt ist.
	 * @return der hinterlegt Wert oder, falls nicht vorhanden, der angegebene
	 *         Standardwert.
	 */
	String getString(final String key, final String defaultValue);

	/**
	 * Legt den Index des aktuellen Feldes fest.
	 * 
	 * @param index
	 *            der neue Index.
	 */
	void setArrayIndex(final int index);

	/**
	 * Gibt die Namen der vorhandenen Schlüssel zurück.
	 * 
	 * @return die Schlüsselmenge.
	 */
	Set<String> stringPropertyNames();

	/**
	 * Prüft ob ein Schlüssel in der Schlüsselmenge enthalten ist.
	 * 
	 * @param key
	 *            ein beliebiger String.
	 * @return {@code true}, wenn der String ein gültiger Schlüssel ist.
	 */
	boolean containsKey(String key);

}
