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

package com.bitctrl;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.jar.Attributes;
import java.util.jar.Manifest;

/**
 * Beschreibt eine Applikation oder Bibliothek.
 * 
 * @author BitCtrl Systems GmbH, Falko Schumann
 */
public class VersionInfo {

	private final String title;
	private final String version;
	private final String vendor;
	private final String vendorUrl;

	/**
	 * Liest die Beschreibung aus dem Manifest-File.
	 * 
	 * @param manifest
	 *            ein Manifestfile.
	 * @see Attributes.Name#IMPLEMENTATION_TITLE
	 * @see Attributes.Name#IMPLEMENTATION_VERSION
	 * @see Attributes.Name#IMPLEMENTATION_VENDOR
	 * @see Attributes.Name#IMPLEMENTATION_URL
	 */
	public VersionInfo(final Manifest manifest) {
		Attributes attributes;

		attributes = manifest.getMainAttributes();
		title = attributes.getValue(Attributes.Name.IMPLEMENTATION_TITLE);
		version = attributes.getValue(Attributes.Name.IMPLEMENTATION_VERSION);
		vendor = attributes.getValue(Attributes.Name.IMPLEMENTATION_VENDOR);
		vendorUrl = attributes.getValue(Attributes.Name.IMPLEMENTATION_URL);
	}

	/**
	 * Erzeugt eine Beschreibung.
	 * 
	 * @param title
	 *            der Name der Applikation oder der Bibliothek.
	 * @param version
	 *            die Versionsnummer.
	 * @param vendor
	 *            das Unternehmen.
	 * @param vendorUrl
	 *            die Internetseite des Unternehmens.
	 */
	public VersionInfo(final String title, final String version,
			final String vendor, final String vendorUrl) {
		this.title = title;
		this.version = version;
		this.vendor = vendor;
		this.vendorUrl = vendorUrl;
	}

	/**
	 * Gibt die Versionsnummer als {@code ReleaseInfo} zurück. Dies ist nur
	 * möglich, wenn die Versionsnummer einer Releasenummer entspricht.
	 * 
	 * @return das Release.
	 */
	public ReleaseInfo getReleaseInfo() {
		return new ReleaseInfo(getVersion());
	}

	/**
	 * Gibt den Namen der Applikation oder der Bibliothek zurück.
	 * 
	 * @return der Name.
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Gibt die Internetseite des Unternehmens zurück.
	 * 
	 * @return die Unternehmensseite.
	 */
	public URL getURL() {
		try {
			return new URL(getVendorUrl());
		} catch (MalformedURLException ex) {
			throw new IllegalStateException(ex);
		}
	}

	/**
	 * Gibt das Unternehmen zurück.
	 * 
	 * @return das Unternehmen.
	 */
	public String getVendor() {
		return vendor;
	}

	/**
	 * Gibt die Internetseite des Unternehmens zurück.
	 * 
	 * @return die Unternehmensseite.
	 */
	public String getVendorUrl() {
		return vendorUrl;
	}

	/**
	 * Gibt die Version zurück.
	 * 
	 * @return die Version.
	 */
	public String getVersion() {
		return version;
	}

}
