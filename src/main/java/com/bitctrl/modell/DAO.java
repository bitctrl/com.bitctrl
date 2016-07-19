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

import java.util.List;

import com.bitctrl.modell.criteria.DAOCriterion;
import com.bitctrl.modell.criteria.OrderDAOCriterion;

/**
 * Delegiert den Zugriff auf Objekte in einer Datenquelle. Die Datenquelle kann
 * ein Datenbank(tabelle) oder auch etwas anderes sein. Jedes Datum der
 * Datenquelle wird als Java-Objekt behandelt.
 * 
 * @author BitCtrl Systems GmbH, Falko Schumann
 * 
 * @param <T>
 *            der Typ der Datenobjekte.
 * @param <ID>
 *            der Schlüssel für die Datenobjekte.
 */
public interface DAO<T, ID> {

	/**
	 * Gibt den Typ der Objekte zurück, die die DAO verwaltet.
	 * 
	 * @return der Objekttyp der DAO-Elemente.
	 */
	Class<T> getPersistentClass();

	/**
	 * Gibt den Typ des Schlüssels der DAO-Elemente zurück.
	 * 
	 * @return der Schlüsseltyp.
	 */
	Class<ID> getKeyClass();

	/**
	 * Registriert einen Listener, der über Änderungen am DAO informiert werden
	 * möchte.
	 * 
	 * @param l
	 *            ein DAO-Listener.
	 */
	void addDAOListener(DAOListener l);

	/**
	 * Deregistiert einen Listener.
	 * 
	 * @param l
	 *            ein DAO-Listener.
	 */
	void removeDAOListener(DAOListener l);

	/**
	 * Ruft alle Objekt von der Datenquelle ab.
	 * 
	 * @return die Liste der vorhandenen Objekte.
	 * @param criteria
	 *            die DAO Kriterien, darf nicht {@code null} sein.
	 * @throws DAOException
	 *             bei einem Fehler.
	 */
	List<T> retrieve(DAOCriterion... criteria) throws DAOException;

	/**
	 * Fügt der Datenquelle ein Objekt hinzu.
	 * <p>
	 * <em>Hinweis:</em> Die Datenquelle kann das Objekt beim Hinzufügen ändern,
	 * z.&nbsp;B. die Id ausfüllen. Deshalb darf nach dem Hinzufügen nicht mit
	 * dem übergebenen Objekt weitergearbeitet werden, sondern es muss das
	 * zurückgegebene Objekt weiterverwendet werden.
	 * 
	 * @param object
	 *            ein Objekt mit Schlüssel.
	 * @return das hinzugefügte Objekt.
	 * @throws DAOException
	 *             bei einem Fehler.
	 */
	T add(T object) throws DAOException;

	/**
	 * Aktualisiert ein vorhandenes Objekt der Datenquelle.
	 * 
	 * @param object
	 *            ein Objekt mit Schlüssel.
	 * @throws DAOException
	 *             bei einem Fehler.
	 */
	void update(T object) throws DAOException;

	/**
	 * Aktualisiert ein vorhandenes Objekt der Datenquelle. Zweite Objectinstanz
	 * in der Session darf existieren
	 * 
	 * @param object
	 *            ein Objekt mit Schlüssel.
	 * @throws DAOException
	 *             bei einem Fehler.
	 */
	void merge(T object) throws DAOException;

	/**
	 * Löscht das angegebene Objekt aus der Datenquelle. Das betroffene Objekt
	 * wird anhand seines Schlüssel identifiziert.
	 * 
	 * @param object
	 *            ein Objekt mit Schlüssel.
	 * @throws DAOException
	 *             bei einem Fehler.
	 */
	void delete(T object) throws DAOException;

	/**
	 * Sucht das Objekt, welches zu dem angegebenen Schlüssel passt.
	 * 
	 * @param key
	 *            ein Schlüssel.
	 * @return das Objekt auf das der Schlüssel passt.
	 * @throws DAOException
	 *             bei einem Fehler.
	 */
	T findById(ID key) throws DAOException;

	/**
	 * Gibt die erwartete Anzahl Datenobjekte zurück. Wird kein Criteria
	 * angegeben, wird die Gesamtanzahl zurückgegeben.
	 * 
	 * @param criteria
	 *            beliebige DAO-Kriterien.
	 * @return die Anzahl gemäß der übergebenen Kriterien.
	 * @throws DAOException
	 *             bei einem Fehler.
	 */
	long count(DAOCriterion... criteria) throws DAOException;

	/**
	 * Gibt die Standardsortierung der Datensätze zurück. Kann leer sein, wenn
	 * es keine sinnvolle Sortierung gibt.
	 * 
	 * @return die Standardsortierung oder ein leeres Array. Niemals
	 *         <code>null</code>.
	 */
	OrderDAOCriterion[] getDefaultOrder();

	/**
	 * Konvertiert die uebergebenen {@link DAOCriterion}s in ein Hibernate-
	 * Criteria Objekt.
	 * 
	 * @param isCount
	 *            <code>true</code> wenn es sich bei der Abfrage um ein
	 *            {@link #count(DAOCriterion...)} handelt, sonst
	 *            <code>false</code>.
	 * @param daoCriterias
	 *            Die {@link DAOCriterion}s.
	 * @return Das Hibernate-Criteria Object.
	 */

	Object createCriteria(final boolean isCount, final DAOCriterion... daoCriterias);

}
