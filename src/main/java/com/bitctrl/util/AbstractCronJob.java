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

package com.bitctrl.util;

/**
 * Beschreibt einen Cron-Job.
 * 
 * @author BitCtrl Systems GmbH, Falko Schumann
 */
public abstract class AbstractCronJob implements Runnable {

	private static long counter = 0L;
	private final long id;
	private final String name;
	private CronPattern pattern;

	/**
	 * Erzeugt einen neuen Cron-Job mit Standardnamen. Der Name des Jobs wird durch
	 * die Job-ID gebildet.
	 * 
	 * @param pattern ein Cron-Pattern.
	 */
	public AbstractCronJob(final CronPattern pattern) {
		this(null, pattern);
	}

	/**
	 * Erzeugt einen neuen Cron-Job. Die ID des Cron-Jobs wird fortlaufend beginnend
	 * bei 1 gebildet.
	 * 
	 * @param name    der Name des Cron-Jobs.
	 * @param pattern ein Cron-Pattern.
	 */
	public AbstractCronJob(final String name, final CronPattern pattern) {
		this.pattern = pattern;
		synchronized (AbstractCronJob.class) {
			id = ++counter;
		}
		if (name != null) {
			this.name = name;
		} else {
			this.name = getClass().getSimpleName() + " " + getId();
		}
	}

	/**
	 * Gibt das aktuelle Cron-Pattern zurück.
	 * 
	 * @return das Cron-Pattern.
	 */
	public CronPattern getPattern() {
		return pattern;
	}

	/**
	 * Ändert das Pattern des Cron-Jobs.
	 * 
	 * @param pattern ein Cron-Pattern.
	 */
	public void setPattern(final CronPattern pattern) {
		this.pattern = pattern;
	}

	/**
	 * Gibt die ID des Cron-Jobs zurück.
	 * 
	 * @return die Cron-Job-ID.
	 */
	public final long getId() {
		return id;
	}

	/**
	 * Gibt den Namen des Jobs zurück.
	 * 
	 * @return der Jobname.
	 */
	public String getName() {
		return name;
	}

}
