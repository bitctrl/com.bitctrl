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

package com.bitctrl.modell.util;

import java.beans.BeanInfo;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.bitctrl.beans.BeanUtils;
import com.bitctrl.geo.graph.tree.TreeUtil;
import com.bitctrl.modell.DAO;
import com.bitctrl.modell.DAOException;
import com.bitctrl.modell.criteria.DAOCriterion;
import com.bitctrl.modell.criteria.LimitDAOCriterion;

/**
 * Kapselt alle Informationen die für das Anzeigen einer Seite beim Paging von
 * DAO-Elementen benötigt werden. Eine Datenabfrage erfolgt erst bei Bedarf.
 * 
 * @author BitCtrl Systems GmbH, Falko Schumann
 */
@Deprecated(since = "3.0.0", forRemoval = true)
public class DAOPage implements DAOElementContainer, Comparable<DAOPage> {

	/** Die DAO bei der Daten bei Bedarf abgerufen werden. */
	private final DAO<?, ?> dao;

	/** Die Gesamtanzahl der Elemente. */
	private final long count;

	/** Die maximale Anzahl der Elemente pro Seite. */
	private final int maxPageSize;

	/** Der Index ab dem Daten abgerufen werden sollen (inklusive, ab 0). */
	private final long from;

	/** Der Index bis zu dem Daten abgerufen werden sollen (exklusive, ab 0). */
	private final long to;

	/** Enthält die notwendigen Kriterien für den Datenabruf. */
	private final DAOCriterion[] criteria;

	private final boolean showPageName;

	/**
	 * Initialisiert die Seite mit den notwendigen Informationen zum späteren Abruf
	 * der Daten.
	 * 
	 * @param dao          die DAO bei der Daten bei Bedarf abgerufen werden.
	 * @param count        die Gesamtanzahl der Elemente.
	 * @param pageSize     die maximale Anzahl der Elemente pro Seite.
	 * @param showPageName Flag, ob {@link #toString()} nur den Bereich oder auch
	 *                     den Namen der Page zurückgeben soll. Der Name der Page
	 *                     wird anhand des Typs bzw. der BeanInfo bestimmt.
	 * @param from         der Index ab dem Daten abgerufen werden sollen
	 *                     (inklusive, ab 0).
	 * @param to           der Index bis zu dem Daten abgerufen werden sollen
	 *                     (exklusive, ab 0).
	 * @param criteria     die Kritierien für den Datenabruf.
	 */
	protected DAOPage(final DAO<?, ?> dao, final long count, final int pageSize, final boolean showPageName,
			final long from, final long to, final DAOCriterion... criteria) {
		if (count <= 0) {
			throw new IllegalArgumentException("Count must be greater than 0.");
		}
		if (pageSize <= 0) {
			throw new IllegalArgumentException("Page size must be greater than 0.");
		}
		if (from > to) {
			throw new IllegalArgumentException("From must be smaller than to.");
		}
		this.dao = dao;
		this.count = count;
		maxPageSize = pageSize;
		this.showPageName = showPageName;
		this.from = from;
		this.to = to;
		this.criteria = criteria;
	}

	/**
	 * Flag, ob diese Page Datenelemente oder weitere Pages als Kinder besitzt. Eine
	 * Subpage liefert mit {@link #getElements()} weitere {@link DAOPage} Objekte
	 * zurück. Ein normale Page liefert die Datenelemente.
	 * 
	 * @return {@code true}, wenn die Page eine Subpage ist.
	 */
	public boolean isSubpage() {
		return to - from > maxPageSize;
	}

	/**
	 * {@inheritDoc}
	 * <p>
	 * Ruft die Daten zu der Seite von der Datenquelle ab oder gibt die Subpages
	 * zurück.
	 * 
	 * @return die Daten oder Subpages.
	 * @throws DAOException bei einem Fehler.
	 */
	public List<?> getElements() throws DAOException {
		if (isSubpage()) {
			final List<Object> subpages = new ArrayList<Object>();

			long subFrom = from;
			final long subCount = to - from;
			int height = 0;
			long subPageSize;
			do {
				height++;
				subPageSize = TreeUtil.maxElementsOfBPlusTree(maxPageSize, height);
			} while (subPageSize < subCount);
			subPageSize = TreeUtil.maxElementsOfBPlusTree(maxPageSize, height - 1);

			for (long i = 0; i < subCount; i += subPageSize) {
				long subTo = subFrom + subPageSize;
				if (subTo > to) {
					subTo = to;
				}
				subpages.add(new DAOPage(dao, count, maxPageSize, showPageName, subFrom, subTo, criteria));
				subFrom = subTo;
			}

			return subpages;
		}

		// Keine Subpage, ergo Elemente ausliefern

		// Kopie der Kriterien plus einmal Platz für Limitkriterium
		final DAOCriterion[] criteriaWithLimit = new DAOCriterion[criteria.length + 1];
		System.arraycopy(criteria, 0, criteriaWithLimit, 0, criteria.length);
		criteriaWithLimit[criteriaWithLimit.length - 1] = new LimitDAOCriterion(from, (int) (to - from));

		return dao.retrieve(criteriaWithLimit);
	}

	/**
	 * Liefert alle Element unter dieser {@link DAOPage} außer den zusätzlich
	 * eingebauten weiteren {@link DAOPage}s.
	 * 
	 * @return die Liste der ermittelten Elemente
	 * @throws DAOException die Elemente konnten nicht abgerufen werden
	 */
	public List<?> getAllElements() throws DAOException {
		// Kopie der Kriterien plus einmal Platz für Limitkriterium
		final DAOCriterion[] criteriaWithLimit = new DAOCriterion[criteria.length + 1];
		System.arraycopy(criteria, 0, criteriaWithLimit, 0, criteria.length);
		criteriaWithLimit[criteriaWithLimit.length - 1] = new LimitDAOCriterion(from, (int) (to - from));

		return dao.retrieve(criteriaWithLimit);
	}

	public Class<?> getLeafElementsType() {
		return dao.getPersistentClass();
	}

	public DAOCriterion[] getLeafElementsCriteriaWithLimit(final int index) {
		final DAOCriterion[] criteriaWithLimit = new DAOCriterion[criteria.length + 1];
		System.arraycopy(criteria, 0, criteriaWithLimit, 0, criteria.length);
		criteriaWithLimit[criteriaWithLimit.length - 1] = new LimitDAOCriterion(from + index, 1);
		return criteriaWithLimit;
	}

	/**
	 * Liefert die aktuelle Seitengröße.
	 * 
	 * @return die Größe der Seite.
	 */
	public int getPageSize() {
		return (int) (to - from);
	}

	public int getLeafElementsCount() {
		return getPageSize();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * Gibt die Seite als lesbaren String in der Form "[1...100]" zurück.
	 */
	@Override
	public String toString() {
		String name = "";
		if (showPageName) {
			final BeanInfo info = BeanUtils.getBeanInfo(getLeafElementsType());
			final String displayName = info.getBeanDescriptor().getDisplayName();
			final Class<?> type = getLeafElementsType().getClass();
			if (displayName.equals(type.getName())) {
				name = type.getSimpleName();
			} else {
				name = displayName;
			}
			name += " ";
		}

		name += "[" + (from + 1) + "..." + to + "]";
		return name;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj instanceof DAOPage) {
			final DAOPage other = (DAOPage) obj;
			return Objects.equals(dao.getClass(), other.dao.getClass()) && from == other.from && to == other.to;
		}
		return false;
	}

	@Override
	public int hashCode() {
		return Objects.hash(dao, from, to);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * Vergleicht gegen nur die Werte von {@code from} dieser und der anderen Page.
	 */
	public int compareTo(final DAOPage o) {
		return Long.compare(from, o.from);
	}

	/**
	 * @return the from
	 */
	public final long getFrom() {
		return from;
	}

	/**
	 * @return the to
	 */
	public final long getTo() {
		return to;
	}

}
