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

package com.bitctrl.modell;

import java.util.HashMap;
import java.util.Map;

/**
 * Implementiert ein Caching für die DAOs, die die Factory anlegt.
 * 
 * @author BitCtrl Systems GmbH, Falko Schumann
 * @version $Id: AbstractDAOFactory.java 16303 2009-02-25 18:26:45Z Schumann $
 */
public abstract class AbstractDAOFactory implements DAOFactory {

	private final Map<Class<?>, DAO<?, ?>> daoCache = new HashMap<Class<?>, DAO<?, ?>>();

	/**
	 * {@inheritDoc}
	 * 
	 * Gibt falls möglich die gesuchte DAO aus dem Cache zurück. Falls die
	 * gesuchte DAO noch nicht im Cache vorliegt, wird sie mit
	 * {@link #doFindDAO(Class)} bestimmt.
	 * 
	 * @see #doFindDAO(Class)
	 */
	@SuppressWarnings("unchecked")
	public <T> DAO<T, ?> findDAO(final Class<T> type) {
		if (!daoCache.containsKey(type)) {
			daoCache.put(type, doFindDAO(type));
		}
		return (DAO<T, ?>) daoCache.get(type);
	}

	/**
	 * Wird von {@link #findDAO(Class)} aus deligiert.
	 * 
	 * @param <T>
	 *            der Typ der DAO.
	 * @param type
	 *            die Typklasse der DAO-Objekte.
	 * @return die DAO zum Typ oder {@code null}, wenn keine passende DAO
	 *         gefunden werden konnte.
	 * @see #findDAO(Class)
	 */
	protected abstract <T> DAO<T, ?> doFindDAO(Class<T> type);

	/**
	 * {@inheritDoc}
	 * 
	 * Prüft ob {@link #findDAO(Class)} einen Wert ungleich {@code null}
	 * zurückgibt.
	 */
	public boolean isDataObject(final Object object) {
		return findDAO(object.getClass()) != null;
	}

}
