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

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

import com.bitctrl.util.TreeProperties;

/**
 * Kapselt die Klasse {@link TreeProperties} um als Quelle für eine
 * Konfiguration zu dienen. Die Werte aus dem Propertiesfile können mit
 * Kommandozeilenargumenten überschrieben werden.
 * <p>
 * Als Standardfile dient {@link Configuration#CONFIG_FILE}. Es kann aber auch jedes
 * beliebige andere Propertiesfile als Konfiguration dienen.
 * 
 * @author BitCtrl Systems GmbH, Falko Schumann
 * @version $Id: Configuration.java 17160 2009-04-24 11:19:20Z goerlitz $
 */
public final class Configuration implements ReadOnlyConfiguration {

	/** Name der Konfigurationsdatei. */
	public static final String CONFIG_FILE = String
			.valueOf("Configuration.properties");
	private static Configuration singleton;

	/**
	 * Lädt die Konfiguration einer Applikation. Die Konfiguration steht in
	 * einem Properties-File. Das File {@link Configuration#CONFIG_FILE} wird im
	 * Arbeitsverzeichnis der Applikation gesucht. Im Defaultpackage des
	 * Classpath muss eine solches File existieren. Dieses enthält alle
	 * Standardwerte der Konfiguration.
	 * <p>
	 * Das externe Konfigurationsfile ist optional. Das File im Defaultpackage
	 * dagegen muss existieren.
	 * 
	 * @return die Einstellungen aus dem File oder <code>null</code>, wenn die
	 *         Einstellungen nicht geladen werden konnten. Letzteres darf nicht
	 *         eintreten, da die Standardeinstellungen im JAR-File enthalten
	 *         sein müssen.
	 * @see #getConfiguration(String[])
	 */
	public static synchronized Configuration getConfiguration() {
		if (singleton == null) {
			TreeProperties defaultConfig;
			InputStream input;

			singleton = new Configuration();
			defaultConfig = new TreeProperties();

			input = ClassLoader.getSystemResourceAsStream(CONFIG_FILE);

			// TODO Alle auffindbaren Files zusammenführen
			// try {
			// System.out.println("Suche per URL ...");
			// final Enumeration<URL> e = ClassLoader
			// .getSystemResources(CONFIG_FILE);
			// while (e.hasMoreElements()) {
			// System.out.println(e.nextElement());
			// }
			// } catch (final IOException ex) {
			// ex.printStackTrace();
			// }

			try {
				// Standardkonfiguration laden, falls vorhanden
				if (input != null) {
					defaultConfig.load(input);
				} else {
					final String txt;

					txt = "Es wurde keine Standardkonfiguration gefunden.";
					singleton.log.fine(txt);
					System.err.println(txt);
				}
				singleton.configuration = new TreeProperties(defaultConfig);
				try {
					input = new FileInputStream(CONFIG_FILE);
					// externe Konfiguration
					singleton.configuration.load(input);
				} catch (final FileNotFoundException ex) {
					final String txt;

					txt = "Es wurde keine erweiterte Konfiguration gefunden. Es wird, falls vorhanden, die Standardkonfiguration verwendet.";
					singleton.log.fine(txt);
					System.err.println(txt);
				}
			} catch (final Exception ex) {
				final String txt;

				txt = "Beim Laden des Konfigurationsfiles ist ein Fehler"
						+ " aufgetreten. Die Konfiguration steht nicht zur"
						+ " Verfügung. Grund: " + ex;
				singleton.log.severe(txt);
				System.err.println(txt);
				singleton = null;

			}
		}

		return singleton;
	}

	/**
	 * Lädt die Konfiguration der Applikation und überschreibt oder ergänzt
	 * diese mit Kommandozeilenargumenten.
	 * 
	 * @param args
	 *            die Kommandozeilenargumente.
	 * @return die Konfiguration.
	 * @see #getConfiguration()
	 */
	public static synchronized Configuration getConfiguration(
			final String[] args) {
		getConfiguration();

		for (int i = 0; i < args.length; i++) {
			String[] param;
			param = args[i].split("=");
			if (param[0].startsWith("-")) {
				param[0] = param[0].substring(1);
			}
			if (param.length == 2) {
				singleton.configuration.setProperty(param[0], param[1]);
			}
		}

		return singleton;
	}

	/**
	 * Lädt die Konfiguration der Applikation und überschreibt oder ergänzt
	 * diese mit der Konfiguration der URL.
	 * 
	 * @param url
	 *            ein Propertiesfile.
	 * @return die Konfiguration.
	 * @see #getConfiguration()
	 */
	public static synchronized Configuration getConfiguration(final URL url) {
		getConfiguration();

		try {
			singleton.configuration.load(url.openStream());
		} catch (final FileNotFoundException ex) {
			throw new IllegalArgumentException("Das Konfigurationsfile " + url
					+ " existiert nicht.", ex);
		} catch (final IOException ex) {
			throw new IllegalArgumentException(
					"Fehler beim Lesen aus dem Konfigurationsfile " + url
							+ " .", ex);
		}

		return singleton;
	}

	private final Logger log = Logger.getLogger(getClass().getName());
	private TreeProperties configuration;

	private Configuration() {
		// Konstruktor verstecken
	}

	/**
	 * {@inheritDoc}
	 */
	public void beginGroup(final String name) {
		configuration.beginGroup(name);
	}

	/**
	 * {@inheritDoc}
	 */
	public int beginReadArray(final String name) {
		return configuration.beginReadArray(name);
	}

	/**
	 * {@inheritDoc}
	 */
	public void endArray(final String name) {
		configuration.endArray(name);
	}

	/**
	 * {@inheritDoc}
	 */
	public void endGroup(final String name) {
		configuration.endGroup(name);
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean getBoolean(final String key) {
		return configuration.getBoolean(key);
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean getBoolean(final String key, final boolean defaultValue) {
		return configuration.getBoolean(key, defaultValue);
	}

	/**
	 * {@inheritDoc}
	 */
	public double getDouble(final String key) {
		return configuration.getDouble(key);
	}

	/**
	 * {@inheritDoc}
	 */
	public double getDouble(final String key, final double defaultValue) {
		return configuration.getDouble(key, defaultValue);
	}

	/**
	 * {@inheritDoc}
	 */
	public int getInt(final String key) {
		return configuration.getInt(key);
	}

	/**
	 * {@inheritDoc}
	 */
	public int getInt(final String key, final int defaultValue) {
		return configuration.getInt(key, defaultValue);
	}

	/**
	 * {@inheritDoc}
	 */
	public long getLong(final String key) {
		return configuration.getLong(key);
	}

	/**
	 * {@inheritDoc}
	 */
	public long getLong(final String key, final long defaultValue) {
		return configuration.getLong(key, defaultValue);
	}

	/**
	 * {@inheritDoc}
	 */
	public String getString(final String key) {
		return configuration.getString(key);
	}

	/**
	 * {@inheritDoc}
	 */
	public String getString(final String key, final String defaultValue) {
		return configuration.getString(key, defaultValue);
	}

	/**
	 * {@inheritDoc}
	 */
	public void setArrayIndex(final int index) {
		configuration.setArrayIndex(index);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return configuration.toString();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * TODO Mit Umstellung auf Java 6 des Projekts vereinfachen!
	 */
	public Set<String> stringPropertyNames() {
		// return configuration.stringPropertyNames();

		final Set<String> keys = new HashSet<String>();
		for (final Object key : configuration.keySet()) {
			if (key instanceof String) {
				keys.add((String) key);
			}
		}
		return keys;
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean containsKey(final String key) {
		return containsKey(key);
	}

}
