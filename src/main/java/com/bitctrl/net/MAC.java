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

package com.bitctrl.net;

import java.io.Serializable;
import java.util.Arrays;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

/**
 * Diese Klasse stellt eine MAC-Adresse dar.
 * 
 * @author BitCtrl Systems GmbH, Schumann
 */
public class MAC implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * Bestimmt aus einem String die MAC-Adresse. Der String muss im Format
	 * {@code x:x:x:x:x:x} vorliegen.
	 * 
	 * @param address die Adresse als Zeichenkette
	 * @return die Adresse
	 * @throws IllegalArgumentException die übergebene Zeichenkette konnte nicht
	 *                                  interpretiert werden
	 */
	public static MAC valueOf(final String address) throws IllegalArgumentException {
		if (address == null || address.length() == 0) {
			return null;
		}

		final String hex = address.replace(":", "");
		try {
			final byte[] data = Hex.decodeHex(hex.toCharArray());
			if (data.length != 6) {
				throw new IllegalArgumentException("Can not determine MAC address.");
			}
			return new MAC(data);
		} catch (final DecoderException ex) {
			throw new IllegalArgumentException("Can not determine MAC address.", ex);
		}
	}

	private final byte[] address;

	/**
	 * Initialisiert die MAC-Adresse.
	 * 
	 * @param address die sechs Bytes der MAC-Adresse.
	 */
	public MAC(final byte[] address) {
		if (address.length != 6) {
			throw new IllegalArgumentException("MAC address must have 6 bytes.");
		}
		this.address = address;
	}

	/**
	 * Verwendet {@link #valueOf(String)} zur Initialisierung der MAC-Adresse.
	 * 
	 * @param address ein String, der eine MAC-Adresse darstellt.
	 * @throws IllegalArgumentException die übergebene Zeichenkette konnte nicht
	 *                                  interpretiert werden
	 */
	public MAC(final String address) throws IllegalArgumentException {
		this.address = valueOf(address).getAddress();
	}

	/**
	 * Gibt die MAC-Adresse als Bytefeld zurück.
	 * 
	 * @return das Bytefeld
	 */
	public byte[] getAddress() {
		return address;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		} else if (obj instanceof final MAC o) {
			return Arrays.equals(address, o.address);
		}

		return false;
	}

	@Override
	public int hashCode() {
		return Arrays.hashCode(address);
	}

	@Override
	public String toString() {
		final char[] hex = Hex.encodeHex(address);
		StringBuilder s = new StringBuilder();
		for (int i = 0; i < hex.length; ++i) {
			s.append(hex[i]);
			if (i % 2 == 1 && i < hex.length - 1) {
				// Nach jedem Byte (2 Hexziffern) Trenzeichen einfügen.
				s.append(':');
			}
		}

		return s.toString();
	}
}
