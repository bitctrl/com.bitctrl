package com.bitctrl.i18n;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Schnittstelle für Message-Handler. Die implementierende Klasse sollte eine
 * Enum-Klasse sein, jeder Enum-Eintrag entspricht einem Meldungstyp.
 * 
 * @deprecated Mit {@link MessageHandler} zusammenführen.
 * 
 * @author BitCtrl Systems GmbH, Görlitz
 */
@Deprecated
public interface MessageHandler2 extends MessageHandler {

	/**
	 * Gibt das Resource-Bundle für diesen Message-Handler in der entsprechenden
	 * Sprache zurück.
	 * 
	 * @param locale
	 *            Die Sprache in der das Resource-Bundle zurückgegeben werden
	 *            soll.
	 * 
	 * @return Das Resource-Bundle.
	 */
	ResourceBundle getResourceBundle(Locale locale);

}
