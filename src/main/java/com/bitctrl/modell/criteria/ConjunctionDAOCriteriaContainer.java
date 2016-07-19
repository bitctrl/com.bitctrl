package com.bitctrl.modell.criteria;

/**
 * Konjunktion von {@link DAOCriterion}
 * 
 * @author BitCtrl Systems GmbH, krosse
 */
public class ConjunctionDAOCriteriaContainer extends BaseDAOCriteriaContainer {

	public ConjunctionDAOCriteriaContainer(final DAOCriterion... criterion) {
		if (criterion != null) {
			for (final DAOCriterion e : criterion) {
				addDAOCriterion(e);
			}
		}
	}

}
