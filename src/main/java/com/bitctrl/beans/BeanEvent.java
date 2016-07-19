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

package com.bitctrl.beans;

import java.util.EventObject;

/**
 * Dieses Event enthält die Informationen über die Änderungen einer Bean.
 * 
 * @author BitCtrl Systems GmbH, Falko Schumann
 * @version $Id: BeanEvent.java 13881 2008-11-06 13:37:21Z Schumann $
 */
public class BeanEvent extends EventObject {

	private static final long serialVersionUID = 1L;

	private final Object beanData;

	/**
	 * Initialisert das Event.
	 * 
	 * @param source
	 *            die Quelle des Events.
	 * @param beanData
	 *            die neuen Daten der Bean.
	 */
	public BeanEvent(final Object source, final Object beanData) {
		super(source);
		this.beanData = beanData;
	}

	/**
	 * Gibt die neuen Daten der Bean zurück.
	 * 
	 * @return die Beandaten.
	 */
	public Object getBeanData() {
		return beanData;
	}

}
