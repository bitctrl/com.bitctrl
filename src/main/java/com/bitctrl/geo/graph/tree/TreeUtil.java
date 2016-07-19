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

package com.bitctrl.geo.graph.tree;

/**
 * Hilfsmethoden für Eigenschaften von Bäumen.
 * 
 * @author BitCtrl Systems GmbH, Falko Schumann
 * @version $Id$
 */
public final class TreeUtil {

	/**
	 * Bestimmt die notwendige Höhe eines B+-Baumes.
	 * 
	 * @param k
	 *            die maximale Anzahl der Elemente, die pro Blatt gesichert
	 *            werden dürfen.
	 * @param n
	 *            Anzahl der unterzubringenden Elemente.
	 * @return die notwendige Baumhöhe.
	 * @deprecated Funktion muss validiert werden, da scheinbar fehlerhaft.
	 */
	@Deprecated
	public static int necessaryHighOfBPlusTree(final int k, final long n) {
		if (n <= 1) {
			throw new IllegalArgumentException(
					"Number of elements must be greater than 1.");
		}
		if (k <= 0) {
			throw new IllegalArgumentException(
					"Number of leaf elements must be greater than 0.");
		}

		final int b = k;
		final double h = Math.ceil((float) (Math.log(n / b) / Math.log(k + 1))) + 1;
		return (int) h;
	}

	/**
	 * Bestimmt die maximale Anzahl von Elementen, die in einem B+-Baum
	 * untergebracht werden können.
	 * 
	 * @param k
	 *            die maximale Anzahl der Elemente, die pro Blatt gesichert
	 *            werden dürfen.
	 * @param height
	 *            die gewünschte Höhe des Baumes.
	 * @return die maximale Elementanzahl im Baum.
	 */
	public static long maxElementsOfBPlusTree(final int k, final int height) {
		if (k <= 0) {
			throw new IllegalArgumentException(
					"Number of leaf elements must be greater than 0.");
		}
		if (height <= 0) {
			throw new IllegalArgumentException("Height must be greater than 0.");
		}

		return (long) (Math.pow(k + 1, height - 1) * k);
	}

	private TreeUtil() {
		// nix
	}

}
