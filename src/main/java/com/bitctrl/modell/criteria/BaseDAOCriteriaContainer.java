package com.bitctrl.modell.criteria;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Abstrakte Implentierung der {@link DAOCriteriaContainer}
 * 
 * @author BitCtrl Systems GmbH, krosse
 */
@Deprecated(since = "3.0.0", forRemoval = true)
abstract class BaseDAOCriteriaContainer implements DAOCriteriaContainer {

	private final Set<DAOCriterion> criterias = new HashSet<>();

	@Override
	public void addDAOCriterion(final DAOCriterion criterion) {
		criterias.add(criterion);
	}

	@Override
	public Collection<DAOCriterion> getDAOCriterias() {
		return criterias;
	}

	@Override
	public void removeDAOCriterion(final DAOCriterion criterion) {
		criterias.remove(criterion);
	}
}
