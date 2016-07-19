package com.bitctrl.modell.criteria;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Abstrakte Implentierung der {@link DAOCriteriaContainer}
 * 
 * @author BitCtrl Systems GmbH, krosse
 */
abstract class BaseDAOCriteriaContainer implements DAOCriteriaContainer {

	private final Set<DAOCriterion> criterias = new HashSet<DAOCriterion>();

	public void addDAOCriterion(final DAOCriterion criterion) {
		criterias.add(criterion);
	}

	public Collection<DAOCriterion> getDAOCriterias() {
		return criterias;
	}

	public void removeDAOCriterion(final DAOCriterion criterion) {
		criterias.remove(criterion);
	}
}
