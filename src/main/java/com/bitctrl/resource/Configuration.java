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
 * Als Standardfile dient {@link Configuration#CONFIG_FILE}. Es kann aber auch
 * jedes beliebige andere Propertiesfile als Konfiguration dienen.
 * 
 * @author BitCtrl Systems GmbH, Falko Schumann
 */
public final class Configuration implements ReadOnlyConfiguration {

	/** Name der Konfigurationsdatei. */
	public static final String CONFIG_FILE = String.valueOf("Configuration.properties");
	private static Configuration singleton;

	/**
	 * Lädt die Konfiguration einer Applikation. Die Konfiguration steht in einem
	 * Properties-File. Das File {@link Configuration#CONFIG_FILE} wird im
	 * Arbeitsverzeichnis der Applikation gesucht. Im Defaultpackage des Classpath
	 * muss eine solches File existieren. Dieses enthält alle Standardwerte der
	 * Konfiguration.
	 * <p>
	 * Das externe Konfigurationsfile ist optional. Das File im Defaultpackage
	 * dagegen muss existieren.
	 * 
	 * @return die Einstellungen aus dem File oder <code>null</code>, wenn die
	 *         Einstellungen nicht geladen werden konnten. Letzteres darf nicht
	 *         eintreten, da die Standardeinstellungen im JAR-File enthalten sein
	 *         müssen.
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
						+ " aufgetreten. Die Konfiguration steht nicht zur" + " Verfügung. Grund: " + ex;
				singleton.log.severe(txt);
				System.err.println(txt);
				singleton = null;

			}
		}

		return singleton;
	}

	/**
	 * Lädt die Konfiguration der Applikation und überschreibt oder ergänzt diese
	 * mit Kommandozeilenargumenten.
	 * 
	 * @param args die Kommandozeilenargumente.
	 * @return die Konfiguration.
	 * @see #getConfiguration()
	 */
	public static synchronized Configuration getConfiguration(final String[] args) {
		getConfiguration();

		for (final String arg : args) {
			String[] param;
			param = arg.split("=");
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
	 * Lädt die Konfiguration der Applikation und überschreibt oder ergänzt diese
	 * mit der Konfiguration der URL.
	 * 
	 * @param url ein Propertiesfile.
	 * @return die Konfiguration.
	 * @see #getConfiguration()
	 */
	public static synchronized Configuration getConfiguration(final URL url) {
		getConfiguration();

		try {
			singleton.configuration.load(url.openStream());
		} catch (final FileNotFoundException ex) {
			throw new IllegalArgumentException("Das Konfigurationsfile " + url + " existiert nicht.", ex);
		} catch (final IOException ex) {
			throw new IllegalArgumentException("Fehler beim Lesen aus dem Konfigurationsfile " + url + " .", ex);
		}

		return singleton;
	}

	private final Logger log = Logger.getLogger(getClass().getName());
	private TreeProperties configuration;

	private Configuration() {
		// Konstruktor verstecken
	}

	@Override
	public void beginGroup(final String name) {
		configuration.beginGroup(name);
	}

	@Override
	public int beginReadArray(final String name) {
		return configuration.beginReadArray(name);
	}

	@Override
	public void endArray(final String name) {
		configuration.endArray(name);
	}

	@Override
	public void endGroup(final String name) {
		configuration.endGroup(name);
	}

	@Override
	public boolean getBoolean(final String key) {
		return configuration.getBoolean(key);
	}

	@Override
	public boolean getBoolean(final String key, final boolean defaultValue) {
		return configuration.getBoolean(key, defaultValue);
	}

	@Override
	public double getDouble(final String key) {
		return configuration.getDouble(key);
	}

	@Override
	public double getDouble(final String key, final double defaultValue) {
		return configuration.getDouble(key, defaultValue);
	}

	@Override
	public int getInt(final String key) {
		return configuration.getInt(key);
	}

	@Override
	public int getInt(final String key, final int defaultValue) {
		return configuration.getInt(key, defaultValue);
	}

	@Override
	public long getLong(final String key) {
		return configuration.getLong(key);
	}

	@Override
	public long getLong(final String key, final long defaultValue) {
		return configuration.getLong(key, defaultValue);
	}

	@Override
	public String getString(final String key) {
		return configuration.getString(key);
	}

	@Override
	public String getString(final String key, final String defaultValue) {
		return configuration.getString(key, defaultValue);
	}

	@Override
	public void setArrayIndex(final int index) {
		configuration.setArrayIndex(index);
	}

	@Override
	public String toString() {
		return configuration.toString();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * TODO Mit Umstellung auf Java 6 des Projekts vereinfachen!
	 */
	@Override
	public Set<String> stringPropertyNames() {
		// return configuration.stringPropertyNames();

		final Set<String> keys = new HashSet<>();
		for (final Object key : configuration.keySet()) {
			if (key instanceof String) {
				keys.add((String) key);
			}
		}
		return keys;
	}

	@Override
	public boolean containsKey(final String key) {
		return configuration.containsKey(key);
	}

}
