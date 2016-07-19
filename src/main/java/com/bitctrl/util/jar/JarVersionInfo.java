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

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.jar.Attributes;
import java.util.jar.JarFile;
import java.util.jar.Manifest;

/**
 * Dieses Tool gibt Versionsinformationen für eine Reihe von Jar-Files aus. Die
 * Informationen werden aus dem Manifest-File der Jar-Files ausgelesen.
 * <p>
 * Folgende Informationen werden ausgelesen und angezeit:
 * <ul>
 * <li>{@link Attributes.Name#IMPLEMENTATION_TITLE}</li>
 * <li>{@link Attributes.Name#IMPLEMENTATION_VERSION}</li>
 * <li>{@link Attributes.Name#IMPLEMENTATION_VENDOR}</li>
 * <li>{@link Attributes.Name#IMPLEMENTATION_URL}</li>
 * </ul>
 * 
 * @author BitCtrl Systems GmbH, Falko Schumann
 */
public final class JarVersionInfo {

	private JarVersionInfo() {
		// Kosntruktor verstecken
	}

	/**
	 * Startet das Tool.
	 * 
	 * @param args
	 *            eine Liste von Jar-Files.
	 */
	public static void main(final String[] args) {
		final boolean onlyVersion;
		final List<String> files;

		if (args.length == 0) {
			System.out
					.println("Usage:\n\tJarVersionInfo [-short] jarfile1 jarfile2 ...");
		}

		files = new LinkedList<String>(Arrays.asList(args));
		if (args[0].equals("-short")) {
			onlyVersion = true;
			files.remove(0);
		} else {
			onlyVersion = false;
		}

		for (final String file : files) {
			final JarFile jarFile;
			final Manifest manifest;
			final Attributes attributes;
			final String jar, title, version, vendor, url;

			try {
				jarFile = new JarFile(file);
				manifest = jarFile.getManifest();
				attributes = manifest.getMainAttributes();

				jar = new File(file).getName();
				title = attributes
						.getValue(Attributes.Name.IMPLEMENTATION_TITLE);
				version = attributes
						.getValue(Attributes.Name.IMPLEMENTATION_VERSION);
				vendor = attributes
						.getValue(Attributes.Name.IMPLEMENTATION_VENDOR);
				url = attributes.getValue(Attributes.Name.IMPLEMENTATION_URL);

				if (onlyVersion) {
					System.out.println(jar + "," + version + "," + url);
				} else {
					System.out.println(jar + "," + title + "," + version + ","
							+ vendor + "," + url);
				}
			} catch (final IOException ex) {
				System.err
						.println("Fehler beim Lesen eines Jar-Files: " + file);
				ex.printStackTrace();
			}
		}
	}

}
