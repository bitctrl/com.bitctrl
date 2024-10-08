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

package com.bitctrl.modell;

import java.lang.reflect.ParameterizedType;

import javax.swing.event.EventListenerList;

import com.bitctrl.modell.criteria.OrderDAOCriterion;

/**
 * Implementiert allgemeine Funktionen einer DAO.
 * 
 * @author BitCtrl Systems GmbH, Falko Schumann
 * 
 * @param <T>  der Typ der Datenobjekte.
 * @param <ID> der Schlüssel für die Datenobjekte.
 */
@Deprecated(since = "3.0.0", forRemoval = true)
public abstract class AbstractDAO<T, ID> implements DAO<T, ID> {

	private final EventListenerList listenerList = new EventListenerList();
	private final Class<T> persistentClass;
	private final Class<ID> keyClass;

	private OrderDAOCriterion[] defaultOrder;

	/**
	 * Initialisiert die DAO mit dem Typ der DAO-Elemente und deren Schlüsseltyp.
	 */
	@SuppressWarnings("unchecked")
	protected AbstractDAO() {
		persistentClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass())
				.getActualTypeArguments()[0];
		keyClass = (Class<ID>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[1];
	}

	@Override
	public Class<T> getPersistentClass() {
		return persistentClass;
	}

	@Override
	public Class<ID> getKeyClass() {
		return keyClass;
	}

	@Override
	public OrderDAOCriterion[] getDefaultOrder() {
		return defaultOrder != null ? defaultOrder : new OrderDAOCriterion[0];
	}

	/**
	 * Legt die Defaultsortierung der Daten in der DAO fest.
	 * 
	 * @param defaultOrder die Defaultsortierung der DAO-Daten.
	 */
	protected void setDefaultOrder(final OrderDAOCriterion... defaultOrder) {
		this.defaultOrder = defaultOrder;
	}

	@Override
	public void addDAOListener(final DAOListener l) {
		listenerList.add(DAOListener.class, l);
	}

	@Override
	public void removeDAOListener(final DAOListener l) {
		listenerList.remove(DAOListener.class, l);
	}

	/**
	 * Benachrichtigt alle angemeldeten Listener über Datenänderungen. Muss nach
	 * einer Änderung der Daten der DAO aufgerufen werden.
	 */
	protected synchronized void fireDataChanged() {
		fireDataChanged(DAOEvent.Type.Unknown, null);
	}

	/**
	 * Benachrichtigt alle angemeldeten Listener über Datenänderungen. Muss nach
	 * einer Änderung der Daten der DAO aufgerufen werden.
	 * 
	 * @param type   der Typ der Aktualisierung.
	 * @param object das aktualisierte Objekt.
	 */
	protected synchronized void fireDataChanged(final DAOEvent.Type type, final Object object) {
		DAOEvent e;

		e = new DAOEvent(this, type, object);
		for (final DAOListener l : listenerList.getListeners(DAOListener.class)) {
			l.dataChanged(e);
		}
	}

}
