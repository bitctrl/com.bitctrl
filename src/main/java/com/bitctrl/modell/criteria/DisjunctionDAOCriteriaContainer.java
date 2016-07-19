package com.bitctrl.modell.criteria;

/**
 * Disjunktion von {@link DAOCriterion}
 * 
 * @author BitCtrl Systems GmbH, krosse
 */
public class DisjunctionDAOCriteriaContainer extends BaseDAOCriteriaContainer {

	public DisjunctionDAOCriteriaContainer(final DAOCriterion... criterion) {
		if (criterion != null) {
			for (final DAOCriterion e : criterion) {
				addDAOCriterion(e);
			}
		}
	}

}
