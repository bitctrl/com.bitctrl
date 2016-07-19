package com.bitctrl.modell.criteria;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Abstrakte Implentierung der {@link DAOCriteriaContainer}
 * 
 * @author BitCtrl Systems GmbH, krosse
 * @version $Id$
 */
abstract class BaseDAOCriteriaContainer implements DAOCriteriaContainer {

	private final Set<DAOCriterion> criterias = new HashSet<DAOCriterion>();

	/**
	 * 
	 * {@inheritDoc}
	 */
	public void addDAOCriterion(final DAOCriterion criterion) {
		criterias.add(criterion);
	}

	/**
	 * 
	 * {@inheritDoc}
	 */
	public Collection<DAOCriterion> getDAOCriterias() {
		return criterias;
	}

	/**
	 * 
	 * {@inheritDoc}
	 */
	public void removeDAOCriterion(final DAOCriterion criterion) {
		criterias.remove(criterion);
	}

}
