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

package com.bitctrl.junit;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;
import java.util.jar.JarFile;

import org.junit.Test;
import org.junit.runner.Description;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunListener;

import com.bitctrl.Constants;
import com.bitctrl.resource.Classpath;

/**
 * Führt JUnit-Tests von Konsole aus. Jeder ausgeführte Test wird mit dem
 * jeweiligen Testergebnis aufgeführt. Am Ende ergolgt eine Zusammenfassung.
 * 
 * @author BitCtrl Systems GmbH, Falko Schumann
 */
public final class TestRunner {

	/**
	 * Prüft ob eine Klasse eine JUnit-Testfall ist. Es wird dazu in der Klasse
	 * nach Methoden gesucht, die die Annotation {@link Test} besitzen.
	 * 
	 * @param c
	 *            eine Klasse.
	 * @return {@code true}, wenn die Klasse JUnit-Tests enthällt.
	 */
	public static boolean isTestCase(final Class<?> c) {
		for (final Method m : c.getMethods()) {
			if (m.isAnnotationPresent(Test.class)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Führt für ein oder mehrere Packages JUnit-Test aus. In den angegebenen
	 * Packages wird rekursiv nach Testklassen gesucht und diese ausgeführt.
	 * 
	 * @param args
	 *            die zu testenden Packages.
	 */
	public static void main(final String[] args) {
		Set<Class<?>> classes;
		JUnitCore junit;

		classes = new HashSet<Class<?>>();
		for (final String resource : args) {

			if (resource.endsWith(".jar")) {
				JarFile jar = null;

				try {
					jar = new JarFile(resource);
				} catch (final IOException ex) {
					System.err.println(ex);
				}
				if (jar == null) {
					continue;
				}

				for (final Class<?> c : Classpath.findClasses(jar, null)) {
					if (isTestCase(c)) {
						classes.add(c);
					}
				}
			} else {
				for (final Class<?> c : Classpath.findClasses(resource, true)) {
					if (isTestCase(c)) {
						classes.add(c);
					}
				}
			}
		}

		junit = new JUnitCore();
		junit.addListener(new RunListener() {

			private boolean lastResult = true;

			@Override
			public void testFailure(final Failure failure) throws Exception {
				lastResult = false;
				System.out.println("nicht bestanden");
				System.out.println();
				System.out.println("Fehlermeldung: " + failure.getMessage());
				System.out.println("Beschreibung: " + failure.getDescription());
				System.out.println("\n" + failure.getTrace());
			}

			@Override
			public void testFinished(final Description description)
					throws Exception {
				if (lastResult) {
					System.out.println("O.k.");
				}
				lastResult = true;
			}

			@Override
			public void testRunFinished(final Result result) throws Exception {
				System.out.println();
				System.out.println("Anzahl durchgeführter Tests: "
						+ result.getRunCount());
				System.out.println("Anzahl fehlerhafter Tests: "
						+ result.getFailureCount());
				System.out.println("Anzahl übersprungener Tests: "
						+ result.getIgnoreCount());
				System.out
						.println("Benötigte Zeit: "
								+ ((double) result.getRunTime() / Constants.MILLIS_PER_SECOND)
								+ " Sekunden");
				if (result.wasSuccessful()) {
					System.out.println("\nTest erfolgreich.");
				}
			}

			@Override
			public void testStarted(final Description description)
					throws Exception {
				System.out.print(description + " ... ");
			}

		});

		junit.run(classes.toArray(new Class<?>[classes.size()]));
		System.exit(0);
	}

	private TestRunner() {
		// Konstruktor verstecken
	}

}
