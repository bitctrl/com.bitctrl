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

package com.bitctrl.geo;

import java.awt.Dimension;

/**
 * Allgemeine geometrische Hilfsmethoden.
 * 
 * @author BitCtrl Systems GmbH, Falko Schumann
 */
public final class GeoUtil {

	/**
	 * Skaliert unter Berücksichtigung des Seitenverhältnisses die Breite und Höhe
	 * derart, dass die Ausnahme ein bestimmtes Maximum nicht überschreiten.
	 * 
	 * @param toLarge die zu skalierende Dimension.
	 * @param maximum das angestrebte Maximum.
	 * @return die herunterskalierte Dimension im gleichen Seitenverhältnis.
	 */
	public static Dimension resizeToFit(final Dimension toLarge, final Dimension maximum) {
		if (toLarge == null) {
			throw new IllegalArgumentException("toLarge is null");
		}
		if (maximum == null) {
			throw new IllegalArgumentException("maximum is null");
		}

		final double factorWidth = maximum.getWidth() / toLarge.getWidth();
		final double factorHeight = maximum.getHeight() / toLarge.getHeight();
		final double factor = Math.min(factorWidth, factorHeight);
		final int width = (int) Math.floor(toLarge.width * factor);
		final int height = (int) Math.floor(toLarge.height * factor);
		return new Dimension(width, height);
	}

	private GeoUtil() {
		// Konstruktor verstecken
	}

}
