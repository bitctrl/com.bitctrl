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

package com.bitctrl.util;

/**
 * Repräsentiert den Wert {@code null} oder "nichts". Eine Implementierung
 * dieser Schnittstelle kann anstelle von Java-{@code null} verwendet werden, um
 * den Test auf {@code null} zu vermeiden. Zusätzlich kann so die
 * Textrepräsentation von {@code null} leichter an die eigenen Bedürfnisse
 * angepasst werden, in dem einfach die Methode {@link #toString()}
 * überschrieben wird.
 * <p>
 * Alle Methoden (außer denen von {@link Object} geerbten) sollten als
 * Methodenstub ausgelegt sein, also keine Anweisungen beinhalten.
 * 
 * @author BitCtrl Systems GmbH, Falko Schumann
 * @version $Id$
 */
public interface NullObject {

	/**
	 * Ein exemplarisches Null-Objekt.
	 */
	NullObject NULL = new NullObject() {

		/**
		 * {@inheritDoc}
		 * 
		 * Gibt den Text "null" zurück.
		 */
		@Override
		public String toString() {
			return "null";
		}

	};

	/**
	 * Gibt einen lesbaren String für das Objekt zurück.
	 * 
	 * @return ein Text wie "null", "nichts", "nicht vorhanden" o.&nbsp;ä.
	 */
	String toString();

}
