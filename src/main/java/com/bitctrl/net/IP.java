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

/**
 * Diese Klasse stellt eine IP-Adresse (Host, Subnet) dar. Die Klasse kann
 * sowohl für IPv4 als auch für IPv6 verwendet werden.
 * 
 * @author BitCtrl Systems GmbH, Schumann
 */
public class IP implements Serializable {

	private static final long serialVersionUID = 1L;
	private static final boolean IP_V4 = true;

	/**
	 * Bestimmt aus einem String die IP-Adresse. Der String muss im Format
	 * {@code x.x.x.x/x} vorliegen.
	 * 
	 * @param address
	 *            der zu parsende String.
	 * @return die IP-Adresse.
	 * @throws IllegalArgumentException
	 *             wenn der String keine gültige Adresse darstellt.
	 */
	public static IP valueOf(final String address) {
		if (IP_V4) {
			if (address == null || address.length() == 0) {
				return null;
			}

			final String[] s;
			final long host;
			final short maskLength;

			s = address.split("/");
			if (s.length != 1 && s.length != 2) {
				throw new IllegalArgumentException(
						"Can not determine host address and subnet mask length.");
			}

			try {
				host = parseAddress(s[0]);
			} catch (final IllegalArgumentException ex) {
				// Wird nur aufgefangen um die Fehlermeldung anzupassen.
				throw new IllegalArgumentException(
						"Can not determine host address.");
			}

			if (s.length == 2) {
				try {
					maskLength = Short.valueOf(s[1]);
				} catch (final NumberFormatException ex) {
					throw new IllegalArgumentException(
							"Can not determine subnet mask length.", ex);
				}
			} else {
				maskLength = 0;
			}

			return new IP(host, maskLength);
		}

		// TODO Implementieren.
		throw new UnsupportedOperationException("IPv6 not implemented yet.");
	}

	/**
	 * Bestimmt aus einem String die Adresse. Der String muss im Format {@code
	 * x.x.x.x} vorliegen.
	 * 
	 * @param addressString
	 * @return die Adresse.
	 * @throws IllegalArgumentException
	 */
	private static long parseAddress(final String addressString) {
		if (IP_V4) {
			String[] s;
			long address;

			s = addressString.split("\\.");
			if (s.length != 4) {
				throw new IllegalArgumentException(
						"Can not determine subnet address.");
			}
			address = Long.parseLong(s[0]) << 24;
			address |= Long.parseLong(s[1]) << 16;
			address |= Long.parseLong(s[2]) << 8;
			address |= Long.parseLong(s[3]);

			return address;
		}

		// TODO Implementieren.
		throw new UnsupportedOperationException("IPv6 not implemented yet.");
	}

	private final long host;
	private final short maskLength;

	/**
	 * Initialisiert die IP-Adresse mit dem angegebenen Host ohne Subnetmaske.
	 * 
	 * @param host
	 *            der Host.
	 */
	public IP(final long host) {
		this(host, (short) 0);
	}

	/**
	 * Initialisiert die IP-Adresse.
	 * 
	 * @param host
	 *            die Hostadresse.
	 * @param maskLength
	 *            die Länge der Subnetmaske.
	 */
	public IP(final long host, final short maskLength) {
		this.host = host;
		this.maskLength = maskLength;
	}

	/**
	 * Verwendet {@link #valueOf(String)} zur Initialisierung der IP-Adresse.
	 * 
	 * @param address
	 *            ein String, der eine IP-Adresse darstellt.
	 */
	public IP(final String address) {
		final IP ip = valueOf(address);
		host = ip.getHostIPv6();
		maskLength = ip.getMaskLength();
	}

	/**
	 * Gibt die IP-Adresse zurück. Zur Zeit verwendet diese Methode IPv4, dies
	 * kann sich in späteren Versionen zu gunsten von IPv6 ändern.
	 * 
	 * @return die IP-Adresse.
	 * @see #getHostIPv4()
	 * @see #getHostIPv6()
	 */
	public int getHost() {
		return (int) host;
	}

	/**
	 * Gibt die Anzahl Bits des Subnets zurück.
	 * 
	 * @return die Bitanzahl des Subnets.
	 */
	public short getMaskLength() {
		return maskLength;
	}

	/**
	 * Gibt die IP-Adresse in IPv4 zur�ck.
	 * 
	 * @return die IP-Adresse.
	 */
	public int getHostIPv4() {
		return (int) host;
	}

	/**
	 * Gibt die IP-Adresse in IPv6 zurück.
	 * 
	 * @return die IP-Adresse.
	 */
	public long getHostIPv6() {
		return host;
	}

	/**
	 * Gibt die Hostadresse als String zur�ck.
	 * 
	 * @return die Hostadresse.
	 */
	public String getHostAsString() {
		if (IP_V4) {
			final long[] addr = new long[4];
			addr[0] = (host & 0xFF000000L) >> 24;
			addr[1] = (host & 0x00FF0000L) >> 16;
			addr[2] = (host & 0x0000FF00L) >> 8;
			addr[3] = host & 0x000000FFL;

			return addr[0] + "." + addr[1] + "." + addr[2] + "." + addr[3];
		}

		// TODO Implementieren.
		throw new UnsupportedOperationException("IPv6 not implemented yet.");
	}

	/**
	 * Gibt die Subnetmaske als String zurück.
	 * 
	 * @return die Subnetmaske.
	 */
	public String getSubnetAsString() {
		if (IP_V4) {
			int subnet1 = 0;
			int subnet2 = 0;
			int subnet3 = 0;
			int subnet4 = 0;

			if (maskLength > 0) {
				if (maskLength < 8) {
					subnet1 = 256 - (int) Math.pow(2, 8 - maskLength);
				} else {
					subnet1 = 255;
				}
			}
			if (maskLength > 8) {
				if (maskLength < 16) {
					subnet2 = 256 - (int) Math.pow(2, 16 - maskLength);
				} else {
					subnet2 = 255;
				}
			}
			if (maskLength > 16) {
				if (maskLength < 24) {
					subnet3 = 256 - (int) Math.pow(2, 24 - maskLength);
				} else {
					subnet3 = 255;
				}
			}
			if (maskLength > 24) {
				if (maskLength < 32) {
					subnet4 = 256 - (int) Math.pow(2, 32 - maskLength);
				} else {
					subnet4 = 255;
				}
			}

			return subnet1 + "." + subnet2 + "." + subnet3 + "." + subnet4;
		}

		// TODO Implementieren.
		throw new UnsupportedOperationException("IPv6 not implemented yet.");
	}

	@Override
	public String toString() {
		String s = getHostAsString();
		if (getMaskLength() > 0) {
			s += "/" + getMaskLength();
		}
		return s;
	}

	/**
	 * Zwei IP-Adressen sind gleich, wenn sie den selben Host im selben Subnet
	 * adressieren.
	 * 
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj instanceof IP) {
			final IP o = (IP) obj;
			return host == o.host && maskLength == o.maskLength;
		}
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		return Long.valueOf(host).hashCode()
				^ Long.valueOf(maskLength).hashCode();
	}
}
