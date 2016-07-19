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

package com.bitctrl;

import java.util.regex.Pattern;

/**
 * Beschreibt die komplette Versionsnummer eines Release nach dem allgemeinen
 * Schema {major release}.{minor release}.{patch level}-{build number}. Bis auf
 * Major-Release sind die Angaben optional.
 * 
 * @author BitCtrl Systems GmbH, Falko Schumann
 * @version $Id: ReleaseInfo.java 12279 2008-09-19 08:24:36Z Schumann $
 */
public class ReleaseInfo {

	/** Der regüläre Ausdruck, der eine Versionsnummer beschreibt. */
	private static final String PATTERN = "(\\d)+(\\.(\\d)+(\\.(\\d)+(-(\\d)+)?)?)?";
	private final Integer major;
	private final Integer minor;
	private final Integer patchLevel;
	private final Integer buildNumber;

	/**
	 * Erzeugt eine Versionsnummer.
	 * 
	 * @param major
	 *            das Major-Release.
	 */
	public ReleaseInfo(final int major) {
		this.major = major;
		this.minor = null;
		this.patchLevel = null;
		this.buildNumber = null;
	}

	/**
	 * Erzeugt eine Versionsnummer.
	 * 
	 * @param major
	 *            das Major-Release.
	 * @param minor
	 *            das Minor-Release.
	 */
	public ReleaseInfo(final int major, final int minor) {
		this.major = major;
		this.minor = minor;
		this.patchLevel = null;
		this.buildNumber = null;
	}

	/**
	 * Erzeugt eine Versionsnummer.
	 * 
	 * @param major
	 *            das Major-Release.
	 * @param minor
	 *            das Minor-Release.
	 * @param patchLevel
	 *            der Patch-Level.
	 */
	public ReleaseInfo(final int major, final int minor, final int patchLevel) {
		this.major = major;
		this.minor = minor;
		this.patchLevel = patchLevel;
		this.buildNumber = null;
	}

	/**
	 * Erzeugt eine Versionsnummer.
	 * 
	 * @param major
	 *            das Major-Release.
	 * @param minor
	 *            das Minor-Release.
	 * @param patchLevel
	 *            der Patch-Level.
	 * @param buildNumber
	 *            die Build-Number.
	 */
	public ReleaseInfo(final int major, final int minor, final int patchLevel,
			final int buildNumber) {
		this.major = major;
		this.minor = minor;
		this.patchLevel = patchLevel;
		this.buildNumber = buildNumber;
	}

	/**
	 * Erzeugt eine Versionsnummer.
	 * 
	 * @param release
	 *            ein String, der die Versionsnummer enthält.
	 */
	public ReleaseInfo(final String release) {
		if (Pattern.matches(PATTERN, release)) {
			int start, end;

			start = 0;
			end = release.indexOf(".");
			if (end < 0) {
				major = Integer.valueOf(release.substring(start));
				minor = null;
				patchLevel = null;
				buildNumber = null;
				return;
			}
			major = Integer.valueOf(release.substring(start, end));

			start = end + 1;
			end = release.indexOf(".", start);
			if (end < 0) {
				minor = Integer.valueOf(release.substring(start));
				patchLevel = null;
				buildNumber = null;
				return;
			}
			minor = Integer.valueOf(release.substring(start, end));

			start = end + 1;
			end = release.indexOf("-", start);
			if (end < 0) {
				patchLevel = Integer.valueOf(release.substring(start));
				buildNumber = null;
				return;
			}
			patchLevel = Integer.valueOf(release.substring(start, end));

			start = end + 1;
			if (start == release.length()) {
				buildNumber = null;
				return;
			}
			buildNumber = Integer.valueOf(release.substring(start));
		} else {
			throw new IllegalArgumentException(
					"Release muss dem Muster 1[.4[.2[-15]]] entsprechen.");
		}
	}

	/**
	 * Gibt falls vorhanden die Build-Number zurück.
	 * 
	 * @return die Build-Number.
	 */
	public Integer getBuildNumber() {
		return buildNumber;
	}

	/**
	 * Gibt das Major-Release zurück.
	 * 
	 * @return das Major-Release.
	 */
	public Integer getMajor() {
		return major;
	}

	/**
	 * Gibt falls vorhanden das Minor-Release zurück.
	 * 
	 * @return das Minor-Release.
	 */
	public Integer getMinor() {
		return minor;
	}

	/**
	 * Gibt falls vorhanden den Patch-Level zurück.
	 * 
	 * @return der Patch-Level.
	 */
	public Integer getPatchLevel() {
		return patchLevel;
	}

	/**
	 * Gibt das Release für den Menschen lesbar zurück. Teile die nicht
	 * angegeben sind, werden nicht ausgegeben. Fehlt z.&nbsp;B. die Build
	 * Number wird diese auch nicht berücksichtigt.
	 * 
	 * @return ein Text nach dem Muster 1[.4[.2[-15]]].
	 */
	@Override
	public String toString() {
		String s;

		s = major.toString();
		if (minor != null) {
			s += "." + minor;
			if (patchLevel != null) {
				s += "." + patchLevel;
				if (buildNumber != null) {
					s += "-" + buildNumber;
				}
			}
		}

		return s;
	}

}
