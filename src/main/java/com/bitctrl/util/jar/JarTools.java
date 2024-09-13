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

package com.bitctrl.util.jar;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.jar.Attributes;
import java.util.jar.JarFile;
import java.util.jar.Manifest;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.bitctrl.VersionInfo;

/**
 * Enthält Hilfsmethoden für den Umgang mit Jar-Files.
 * 
 * @author BitCtrl Systems GmbH, Falko Schumann
 */
public final class JarTools {

	private static final class JarAnalyzer {

		private final File out;

		/**
		 * Erzeugt einen {@code JarAnalyzer} der alle Jar-Files rekursiv analysiert und
		 * das Ergebnis in ein File {@code jar.txt} schreibt.
		 * 
		 * @throws IOException
		 */
		public JarAnalyzer() throws IOException {
			out = new File("jar.txt").getAbsoluteFile();
			findJarsAndPrintInfo(out.getParentFile());
		}

		/**
		 * Erzeugt einen {@code JarAnalyzer} der ein bestimmtes Jar-File analisiert und
		 * das Ergebnis auf der Konsole ausgibt.
		 * 
		 * @param file der Pfad zu einem Jar-File.
		 * @throws IOException
		 */
		public JarAnalyzer(final String file) throws IOException {
			final JarFile jar;
			final Manifest manifest;
			final Attributes attributes;
			final Iterator<Object> iterator;

			out = null;

			jar = new JarFile(file);
			manifest = jar.getManifest();
			attributes = manifest.getMainAttributes();
			iterator = attributes.keySet().iterator();

			System.out.println("JAR-File:\n" + jar.getName());
			System.out.println("\nEigenschaften: ");
			while (iterator.hasNext()) {
				final Attributes.Name key = (Attributes.Name) iterator.next();

				System.out.println(key + " = " + attributes.getValue(key));
			}
		}

		/**
		 * Durchsucht eine Verzeichnisstruktur rekursiv nach Jar-Files und übergibt
		 * deren Informationen an {@link #printInfo(JarFile)}.
		 * 
		 * @param parentFile
		 * @throws IOException
		 */
		public void findJarsAndPrintInfo(final File parentFile) throws IOException {
			File[] files;

			files = parentFile.listFiles();
			if (files != null) {
				for (final File file : files) {
					if (file.getName().endsWith(".jar")) {
						printInfo(new JarFile(file));
					} else if (file.isDirectory()) {
						findJarsAndPrintInfo(file);
					}
				}
			}
		}

		/**
		 * Schreibt die Informationen zu einem Jar-File in einen Dateistrom.
		 * 
		 * @param jar ein Jar-File.
		 * @throws IOException
		 */
		public void printInfo(final JarFile jar) throws IOException {
			final Manifest manifest;
			final Attributes attributes;
			final Iterator<Object> iterator;

			try (BufferedWriter buf = new BufferedWriter(
					new OutputStreamWriter(new FileOutputStream(out, true), Charset.defaultCharset().name()))) {
				manifest = jar.getManifest();
				attributes = manifest.getMainAttributes();
				iterator = attributes.keySet().iterator();

				System.out.println("JAR-File:\n" + jar.getName());
				buf.write("JAR-File:\n" + jar.getName() + "\n");
				System.out.println("\nEigenschaften:");
				buf.write("\nEigenschaften:" + "\n");
				while (iterator.hasNext()) {
					final Attributes.Name key = (Attributes.Name) iterator.next();

					System.out.println(key + " = " + attributes.getValue(key));
					buf.write(key + " = " + attributes.getValue(key) + "\n");
				}
				System.out.println("\n");
				buf.write("\n\n");
				buf.flush();
			}
		}

	}

	/**
	 * Kleines Hilfsprogramm, um die Eigenschaften eines JAR-Files aus dem
	 * Manifest-File zu lesen. Wird ein Startparameter übergeben, wird dieser als
	 * Pfad zu einem JAR-File interpretiert und dessen Eigenschaften auf der Konsole
	 * ausgegeben. Wird kein Startparameter angegeben, wird rekursiv vom
	 * Startverzeichnis nach JAR-Files gesucht und dessen Eigenschaften auf der
	 * Konsole und in ein File jar.txt ausgegeben.
	 * 
	 * @param args nichts oder der Pfad zu einem JAR-File.
	 */
	public static void main(final String[] args) {
		try {
			if (args.length == 1) {
				new JarAnalyzer(args[0]);
			} else {
				new JarAnalyzer();
			}
		} catch (final IOException ex) {
			Logger.getLogger(JarTools.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	/**
	 * Gibt Daten zur aktuellen Version einer Klasse auf der Konsole aus. Die
	 * Versionsdaten werden aus dem Manifest-File des Jar-Files in dem sich die
	 * Klasse befindet ausgelesen.
	 * <p>
	 * Befindet sich die Klasse nicht in einem Jar-File, enthält das Jar-File kein
	 * Manifest-File oder fehlen die entsprechenden Einträge im Manifest, dann
	 * erfolgt keinerlei Ausgabe.
	 * 
	 * @param clazz die Klasse die zum Finden des Manifest-Files verwendet wird.
	 */
	public static void printVersionInfo(final Class<?> clazz) {
		try {
			URL url;

			url = clazz.getProtectionDomain().getCodeSource().getLocation();
			if (!url.toString().endsWith(".jar")) {
				return;
			}

			url = new URL("jar:" + url + "!/META-INF/MANIFEST.MF");
			final VersionInfo info = new VersionInfo(new Manifest(url.openStream()));

			System.out.println();
			System.out.println(info.getTitle());
			System.out.println("Version " + info.getVersion());
			System.out.println("Copyright (c) by " + info.getVendor() + ", " + info.getVendorUrl());
			System.out.println("---------------------------------------------------------------------");

			System.out.println();
		} catch (final IOException ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * Gibt Daten zur aktuellen Version einer Klasse zurück. Die Versionsdaten
	 * werden aus dem Manifest-File des Jar-Files in dem sich die Klasse befindet
	 * ausgelesen.
	 * 
	 * @param clazz die Klasse die zum Finden des Manifest-Files verwendet wird.
	 * @return die Versionsinformation.
	 * @throws IllegalArgumentException wenn die Klasse sich nicht in einem Jar-File
	 *                                  befindet oder das Jar-File bzw. das
	 *                                  Manifest-File nicht gelesen werden können.
	 */
	public static VersionInfo getVersionInfo(final Class<?> clazz) {
		try {
			URL url;

			url = clazz.getProtectionDomain().getCodeSource().getLocation();
			if (!url.toString().endsWith(".jar")) {
				throw new IllegalArgumentException("Class is not in a Jar-file.");
			}

			url = new URL("jar:" + url + "!/META-INF/MANIFEST.MF");
			return new VersionInfo(new Manifest(url.openStream()));
		} catch (final IOException ex) {
			throw new IllegalArgumentException("Can not read Jar-file.", ex);
		}
	}

	private JarTools() {
		// Konstruktor verstecken
	}

}
