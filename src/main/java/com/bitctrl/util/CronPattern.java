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

import java.util.Calendar;

/**
 * Hilfsklasse zur Bearbeitung eines String in Cron-Syntax. Folgende
 * Syntax-Elemente werden unterstützt:
 * <p>
 * Der String besteht aus fünf Bereichen:
 * <ol>
 * <li>Minute: 0-59 oder * für jede Minute</li>
 * <li>Stunde: 0-23 oder * für jede Stunde</li>
 * <li>Tag des Monats: 1-31 oder * für jeden Tag</li>
 * <li>Monat: 1-12 oder * für jeden Monat</li>
 * <li>Wochentag: 0-7 oder * für jeden Wochentag (0 und 7 stehen beide für
 * Sonntag)</li>
 * </ol>
 * 
 * TODO Listen unterstützen. TODO Bereiche unterstützen. TODO Schrittweiten
 * unterstützen. TODO Strings für Monat und Wochentag unterstützen.
 * 
 * @author BitCtrl Systems GmbH, Falko Schumann
 */
public class CronPattern implements ValueMatcher<Long> {

	private final String pattern;
	private final ValueSetMatcher<Integer> minuteMatcher = new ValueSetMatcher<>();
	private final ValueSetMatcher<Integer> hourMatcher = new ValueSetMatcher<>();
	private final ValueSetMatcher<Integer> dayOfMonthMatcher = new ValueSetMatcher<>();
	private final ValueSetMatcher<Integer> monthMatcher = new ValueSetMatcher<>();
	private final ValueSetMatcher<Integer> dayOfWeekMatcher = new ValueSetMatcher<>();

	/**
	 * Erzeugt ein neues Objekt anhand eines String.
	 * 
	 * @param pattern ein String in Cron-Syntax.
	 */
	public CronPattern(final String pattern) {
		this.pattern = pattern;
		setPattern(pattern);
	}

	/**
	 * Gibt den zugrundeliegenden String zurück.
	 * 
	 * @return der Original-String in Cron-Syntax.
	 */
	public String getPattern() {
		return pattern;
	}

	private void setPattern(final String pattern) {
		String[] parts;

		parts = pattern.split(" |\t");
		if (parts.length != 5) {
			throw new IllegalArgumentException("Das Pattern entspricht nicht der unterstützten Cron-Syntax.");
		}
		try {
			if ("*".equals(parts[0])) {
				minuteMatcher.add(new ValueMatcher.AlwaysMatcher<>());
			} else {
				minuteMatcher.add(new ValueMatcher.CollectionValueMatcher<>(Integer.valueOf(parts[0])));
			}

			if ("*".equals(parts[1])) {
				hourMatcher.add(new ValueMatcher.AlwaysMatcher<>());
			} else {
				hourMatcher.add(new ValueMatcher.CollectionValueMatcher<>(Integer.valueOf(parts[1])));
			}

			if ("*".equals(parts[2])) {
				dayOfMonthMatcher.add(new ValueMatcher.AlwaysMatcher<>());
			} else {
				dayOfMonthMatcher.add(new ValueMatcher.CollectionValueMatcher<>(Integer.valueOf(parts[2])));
			}

			if ("*".equals(parts[3])) {
				monthMatcher.add(new ValueMatcher.AlwaysMatcher<>());
			} else {
				// Java-Monate gehen bei 0 los
				monthMatcher.add(new ValueMatcher.CollectionValueMatcher<>(Integer.valueOf(parts[3])));
			}

			if ("*".equals(parts[4])) {
				dayOfWeekMatcher.add(new ValueMatcher.AlwaysMatcher<>());
			} else {
				// Java-Wochentage gehen bei 1 mit Sonntag los
				int day;

				day = Integer.parseInt(parts[4]);
				if (day == 7) {
					// Sonntag ist intern nur 0, nicht mehr auch 7
					day = 0;
				}
				dayOfWeekMatcher.add(new ValueMatcher.CollectionValueMatcher<>(day));
			}

		} catch (final NumberFormatException ex) {
			throw new UnsupportedOperationException(
					"Das Pattern entspricht nicht der unterstützten Cron-Syntax. Listen, Intervalle und Schrittweiten werden noch nicht untertsützt.");
		}
	}

	/**
	 * Prüft ob ein Zeitstampel mit dem Cron-Pattern matcht. Der Zeitstempel muss
	 * mit den Angaben von Wochentag, Monat, Tag, Stunde und Minute übereinstimmen.
	 * 
	 * @param timestamp ein beliebiger Zeitstempel.
	 * @return {@code true}, wenn der Zeitstempel mit dem Cron-Pattern matcht.
	 */
	@Override
	public boolean match(final Long timestamp) {
		final int minute, hour, dayOfMonth, month, dayOfWeek;
		Calendar cal;
		boolean result;

		cal = Calendar.getInstance();
		cal.setTimeInMillis(timestamp);
		minute = cal.get(Calendar.MINUTE);
		hour = cal.get(Calendar.HOUR_OF_DAY);
		dayOfMonth = cal.get(Calendar.DAY_OF_MONTH);

		// Java-Monate gehen bei 0 los
		month = cal.get(Calendar.MONTH) + 1;

		// Java-Wochentage gehen bei 1 mit Sonntag los
		dayOfWeek = cal.get(Calendar.DAY_OF_WEEK) - 1;

		result = minuteMatcher.match(minute);
		result &= hourMatcher.match(hour);
		result &= dayOfMonthMatcher.match(dayOfMonth);
		result &= monthMatcher.match(month);
		result &= dayOfWeekMatcher.match(dayOfWeek);

		return result;
	}

	@Override
	public String toString() {
		String s;

		s = getClass().getName() + "[";
		s += "pattern=" + getPattern();
		s += "]";
		return s;
	}

}
