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

package com.bitctrl.util.logging;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * Hilfsklasse für den Umgang mit {@link java.util.logging.Logger}.
 * 
 * @author BitCtrl Systems GmbH, Falko Schumann
 */
public final class LoggerTools {

	/** Der Name der Property, die das Logverzeichnis enthält. */
	public static final String CONFIG_LOG_DIR = String.valueOf("common.log.dir");

	/** Der Name der Property, die den Loglevel enthält. */
	public static final String CONFIG_LOG_LEVEL = String.valueOf("common.log.level");

	/**
	 * Ein Datum/Uhrzeit Formatierer für Logfiles, dieser schreibt auch die
	 * Millisekunden. Übernommen aus UZSHLIB/BcFunc.
	 */
	private static final SimpleDateFormat logFileDateFormatter = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss.SSS");

	private static final int MAX_FILE_SIZE = 1024 * 1024; // 1 MB
	private static final int MAX_FILE_COUNT = 10;

	/**
	 * Setzt rekursiv alle Meldungen einer Exception-Kette (
	 * {@link Throwable#getCause()}) zusammen.
	 * 
	 * @param t eine Exception.
	 * @return die Kette aller Meldungen in der Form "[Meldung] &gt; [Meldung].cause
	 *         &gt; [Meldung].cause.cause".
	 */
	public static String getMessages(final Throwable t) {
		final StringBuilder msg = new StringBuilder().append(t.getLocalizedMessage());
		if (t.getCause() != null && t.getCause() != t) {
			msg.append(" > ").append(getMessages(t.getCause()));
		}
		return msg.toString();
	}

	/**
	 * Gibt den Stack Trace einer Exception als String zurück. Mit {@code new
	 * Exception()} kann der aktuelle Stack Trace erfragt werden.
	 * 
	 * @param e eine Exception oder ein Error.
	 * @return der Stack Trace.
	 */
	public static String getStackTrace(final Throwable e) {
		final Writer result = new StringWriter();
		final PrintWriter printWriter = new PrintWriter(result);
		e.printStackTrace(printWriter);
		return result.toString();
	}

	/**
	 * Bestimmt die Position im Code einer Exception. Angegeben wird der
	 * Klassenname, der Methodenname und die Zeilennummer.
	 * 
	 * @param e eine Exception oder ein Error.
	 * @return die Aufrufposition der Betriebsmeldung
	 */
	public static String getCallPosition(final Throwable e) {
		final String s;

		if (e.getStackTrace().length > 1) {
			final StackTraceElement traceElement = e.getStackTrace()[1];
			s = traceElement.getClassName() + "." + traceElement.getMethodName() + "(" + traceElement.getFileName()
					+ ": " + traceElement.getLineNumber() + ")";
		} else {
			s = "Aufrufposition nicht ermittelbar!";
		}
		return s;
	}

	/**
	 * Liegt das verzeichnis fest, in dem Log-Files abgelegt werden soll. Existiert
	 * das Verzeichnis nicht, wird es angelegt.
	 * 
	 * @param dir             ein Verzeichnis.
	 * @param applicationName der Name der Appliktion.
	 * @return {@code true}, wenn die Operation erfolgreich war.
	 */
	public static boolean setLogDirectory(final String dir, final String applicationName) {
		FileHandler handler;
		String path;

		path = applicationName + ".%g.log";
		if (dir != null) {
			File file;

			file = new File(dir);
			if (!file.exists()) {
				file.mkdirs();
			}
			if (dir.endsWith("/")) {
				path = dir + path;
			} else {
				path = dir + "/" + path;
			}
		}
		try {
			handler = new FileHandler(path, MAX_FILE_SIZE, MAX_FILE_COUNT, true);
			handler.setFormatter(new SimpleFormatter());
			Logger.getLogger("").addHandler(handler);
			return true;
		} catch (final SecurityException | IOException ex) {
			return false;
		}
	}

	/**
	 * Legt den Log-Level des Root-Loggers und dessen Handler fest.
	 * 
	 * @param level der neue Log-Level.
	 */
	public static void setLoggerLevel(final Level level) {
		Handler[] handler;

		Logger.getLogger("").setLevel(level);
		handler = Logger.getLogger("").getHandlers();
		for (final Handler element : handler) {
			handler[0].setLevel(level);
		}
	}

	/**
	 * Prüft ob der Logger Ausgaben auf einem bestimmten Level macht.
	 * 
	 * @param logger ein Logger.
	 * @param level  der zu prüfende Level.
	 * @return <code>true</code>, wenn der Logger Ausgaben auf dem angegebenen Level
	 *         macht.
	 */
	public static boolean isLogable(final Logger logger, final Level level) {
		return isLogable(logger, level, null);
	}

	/**
	 * Prüft ob der Logger Ausgaben auf einem bestimmten Level, mit einem bestimmten
	 * Handler macht. Ist der Handler <code>null</code>, wird nur der Level geprüft.
	 * 
	 * @param logger       ein Logger.
	 * @param level        der zu prüfende Level.
	 * @param handlerClazz der zu prüfende Handler oder <code>null</code>.
	 * @return <code>true</code>, wenn der Logger Ausgaben auf dem angegebenen Level
	 *         mit dem angegebenen Handler macht. Wenn der Handler <code>null</code>
	 *         ist, wird er ignoriert.
	 */
	public static boolean isLogable(final Logger logger, final Level level,
			final Class<? extends Handler> handlerClazz) {
		if (!logger.isLoggable(level)) {
			return false;
		}

		final LogRecord logRecord = new LogRecord(level, "");
		for (final Handler h : logger.getHandlers()) {
			if (h.isLoggable(logRecord)) {
				if (handlerClazz != null) {
					return handlerClazz.isAssignableFrom(h.getClass());
				}
				return true;
			}
		}

		if (logger.getUseParentHandlers()) {
			return isLogable(logger.getParent(), level, handlerClazz);
		}

		return false;
	}

	/**
	 * Liefert einen Datum/Uhrzeit Formatierer für Logfiles, dieser schreibt auch
	 * die Millisekunden. Übernommen aus UZSHLIB/BcFunc.
	 * 
	 * @return logFileDateFormatter der Formatter
	 */
	public static SimpleDateFormat getLogFileDateFormatter() {
		return logFileDateFormatter;
	}

	private LoggerTools() {
		// Konstruktor verstecken
	}

}
