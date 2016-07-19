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

package com.bitctrl.i18n;

import java.text.MessageFormat;
import java.util.Locale;

/**
 * Liefert lokalisierte Meldungen.
 * 
 * @author BitCtrl Systems GmbH, Falko Schumann
 */
public final class Messages {

	/**
	 * Ersetzt in einer Meldung die enthaltenen Variablen und gibt sie
	 * lokalisiert zurück. Eine Meldung mit Variablen hat die Form <code>text {0} text {1}"</code>
	 * . <code>{...}</code> wird durch den Inhalt der übergebenen Argumente
	 * ersetzt.
	 * 
	 * @param key
	 *            der Typ der Meldung.
	 * @param arguments
	 *            die Argumente, mit denen die Variaben in der Meldung ersetzt
	 *            werden sollen.
	 * @return die lokalisierte Meldung.
	 */
	public static String get(final MessageHandler key,
			final Object... arguments) {
		final String txt = key.getResourceBundle().getString(key.name());

		if (arguments != null) {
			return MessageFormat.format(txt, arguments);
		}

		return txt;
	}

	/**
	 * Ersetzt in einer Meldung die enthaltenen Variablen und gibt sie
	 * lokalisiert zurück. Eine Meldung mit Variablen hat die Form <code>text {0} text {1}"</code>
	 * . <code>{...}</code> wird durch den Inhalt der übergebenen Argumente
	 * ersetzt.
	 * 
	 * @param key
	 *            der Typ der Meldung.
	 * @param locale
	 *            die Sprache der Meldung.
	 * @param arguments
	 *            die Argumente, mit denen die Variaben in der Meldung ersetzt
	 *            werden sollen.
	 * @return die lokalisierte Meldung.
	 */
	public static String get(final MessageHandler2 key, final Locale locale,
			final Object... arguments) {
		final String txt = key.getResourceBundle(locale).getString(key.name());

		if (arguments != null) {
			return MessageFormat.format(txt, arguments);
		}

		return txt;
	}

	/**
	 * Standardkosntruktor verstecken.
	 */
	private Messages() {
		// nix
	}

}
