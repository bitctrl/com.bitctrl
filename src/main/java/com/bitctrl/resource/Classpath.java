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

import java.io.File;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * Hilfsmethoden um auf Ressourcen im Classpath zuzugreifen.
 * 
 * @author BitCtrl Systems GmbH, Falko Schumann
 */
public final class Classpath {

	/**
	 * Sucht alle Klassen aus einem Jar-File heraus. Optional kann die Suche mit dem
	 * Package-Namen eingeschränkt werden.
	 * 
	 * @param jar         ein Jar-File.
	 * @param packageName das Package desses Klassen gesucht werden sollen.
	 *                    Subpackages werden mit einbezogen. Wenn {@code null}, dann
	 *                    werden alle Klassen ohne Einschränkung gesucht.
	 * @return die Liste der enthaltenen Klassen.
	 */
	public static List<Class<?>> findClasses(final JarFile jar, final String packageName) {
		Enumeration<JarEntry> enumeration;
		Set<Class<?>> classes;

		classes = new HashSet<>();
		enumeration = jar.entries();
		while (enumeration.hasMoreElements()) {
			String filename;

			filename = enumeration.nextElement().getName();
			if (filename.endsWith(".class")) {
				filename = filename.substring(0, filename.length() - 6);
				filename = filename.replace('/', '.');
				try {
					Class<?> c;

					c = Class.forName(filename, false, ClassLoader.getSystemClassLoader());
					if (packageName != null) {
						if (c.getPackage().getName().startsWith(packageName)) {
							classes.add(c);
						} else {
							classes.add(c);
						}
					}
				} catch (final ClassNotFoundException ex) {
					System.err.println(ex);
				}
			}
		}

		return new ArrayList<>(classes);
	}

	/**
	 * Sucht alle Klassen in einem Package heraus.
	 * 
	 * @param packageName der Name eines Packages.
	 * @param recursively {@code true}, wenn das Package rekursiv durchsucht werden
	 *                    soll.
	 * @return die Liste der gefundenen Klasse.
	 */
	public static List<Class<?>> findClasses(final String packageName, final boolean recursively) {
		final ArrayList<File> directories;
		final Enumeration<URL> resources;
		final Set<Class<?>> classes;

		classes = new HashSet<>();

		try {
			resources = ClassLoader.getSystemResources(packageName.replace('.', '/'));
			directories = new ArrayList<>();
			while (resources.hasMoreElements()) {
				directories.add(new File(URLDecoder.decode(resources.nextElement().getPath(), "UTF-8")));
			}

			for (final File directory : directories) {
				final File[] files;
				final String path;

				path = directory.getAbsolutePath();
				if (path.contains(".jar!")) {
					JarFile jar;

					jar = new JarFile(path.substring(path.indexOf("file:") + 6, path.indexOf(".jar!") + 4));
					classes.addAll(findClasses(jar, packageName));
				} else {
					files = directory.listFiles();
					if (files != null) {
						for (final File file : files) {
							if (file.isFile()) {
								final String filename;

								filename = file.getName();
								if (filename.endsWith(".class")) {
									final String classpath;

									classpath = packageName + '.' + filename.substring(0, filename.length() - 6);
									classes.add(Class.forName(classpath, false, ClassLoader.getSystemClassLoader()));
								}
							} else if (recursively && file.isDirectory()) {
								classes.addAll(findClasses(packageName + "." + file.getName(), true));
							}
						}
					}
				}
			}
		} catch (final Exception | Error err) {
			System.err.println(err);
		}
		return new ArrayList<>(classes);
	}

	private Classpath() {
		// Konstruktor verstecken
	}

}
