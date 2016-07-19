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

package com.bitctrl.modell.util;

import java.util.ArrayList;
import java.util.List;

import com.bitctrl.modell.DAO;
import com.bitctrl.modell.DAOException;
import com.bitctrl.modell.criteria.DAOCriterion;

/**
 * Führt ein Paging auf einer DAO aus. Wenn die Elemente einer DAO zuviel sind,
 * kann diese Klasse die Elemente in gleichgroße Blöcke aufteilen. Der letzte
 * Block enthält nur noch die restlichen Elemente und kann also auch kleiner
 * sein, als die vorherigen.
 * 
 * @author BitCtrl Systems GmbH, Falko Schumann
 * @version $Id$
 */
public final class DAOPaging {

	/**
	 * Erzeugt abhängig von der Anzahl der erwarteten Objekte beim Datenabruf
	 * ein Paging oder gibt die Liste der Datenobjekte direkt zurück.
	 * <p>
	 * Ist die Gesamtanzahl der gefundenen Objekte größer als die maximale
	 * Anzahl von Objekten pro Seite, werden die notwendigen Seiten zur Anzeige
	 * zurückgegeben. Der Typ der Liste ist dann {@link DAOPage}.
	 * <p>
	 * Ist die Anzahl der erwarteten Datenelemente kleiner oder gleich der
	 * maximalen Anzahl Element pro Seite, werden die Datenelemente direkt als
	 * Liste zurückgegeben. Der Typ der Liste ist dann der Typ der Datenobjekte.
	 * 
	 * @param dao
	 *            die DAO, von der die Daten abgerufen werden sollen.
	 * @param pageSize
	 *            die maximale Anzahl der Elemente pro Seite.
	 * @param showPageName
	 *            Flag, ob {@link #toString()} nur den Bereich oder auch den
	 *            Namen der Page zurückgeben soll. Der Name der Page wird anhand
	 *            des Typs bzw. der BeanInfo bestimmt.
	 * @param createSubpages
	 *            Flag, ob das Anlegen von Subpages bei sehr großen Listen
	 *            erlaubt ist.
	 * @param criteria
	 *            mögliche Kriterien.
	 * @return entweder die Liste der {@link DAOPage}s oder die Liste der
	 *         Datenobjekte.
	 * @throws DAOException
	 *             bei einem Fehler beim Datenabruf.
	 */
	public static List<?> getPagesOrElements(final DAO<?, ?> dao,
			final int pageSize, final boolean showPageName,
			final boolean createSubpages, final DAOCriterion... criteria)
			throws DAOException {
		if (dao.count(criteria) > pageSize) {
			return getPages(dao, pageSize, showPageName, createSubpages,
					criteria);
		}
		return dao.retrieve(criteria);
	}

	/**
	 * Die Liste der benötigen Seiten zurück, um die Elemente der DAO
	 * anzuzeigen.
	 * 
	 * @param dao
	 *            die DAO, von der die Daten abgerufen werden sollen.
	 * @param pageSize
	 *            die maximale Anzahl der Elemente pro Seite.
	 * @param showPageName
	 *            Flag, ob {@link #toString()} nur den Bereich oder auch den
	 *            Namen der Page zurückgeben soll. Der Name der Page wird anhand
	 *            des Typs bzw. der BeanInfo bestimmt.
	 * @param createSubpages
	 *            Flag, ob das Anlegen von Subpages bei sehr großen Listen
	 *            erlaubt ist.
	 * @param criteria
	 *            mögliche Kriterien.
	 * @return die Seitenliste.
	 * @throws DAOException
	 *             bei einem Fehler beim Datenabruf.
	 */
	@SuppressWarnings("unchecked")
	public static List<DAOPage> getPages(final DAO<?, ?> dao,
			final int pageSize, final boolean showPageName,
			final boolean createSubpages, final DAOCriterion... criteria)
			throws DAOException {
		if (pageSize <= 0) {
			throw new IllegalArgumentException(
					"Page size must be greater than 0.");
		}

		final List<DAOPage> pages = new ArrayList<DAOPage>();
		final long count = dao.count(criteria);

		if (pageSize < count && createSubpages) {
			return (List<DAOPage>) new DAOPage(dao, count, pageSize,
					showPageName, 0, count, criteria).getElements();
		}

		// Keine Subpages anlegen

		for (long offset = 0; offset < count; offset += pageSize) {
			final int currentSize;

			if (offset + pageSize <= count) {
				currentSize = pageSize;
			} else {
				currentSize = (int) (count - offset);
			}

			pages.add(new DAOPage(dao, count, pageSize, showPageName, offset,
					offset + currentSize, criteria));
		}

		return pages;
	}

	private DAOPaging() {
		// nix
	}

}
