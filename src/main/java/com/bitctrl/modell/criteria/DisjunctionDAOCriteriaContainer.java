package com.bitctrl.modell.criteria;

/**
 * Disjunktion von {@link DAOCriterion}
 * 
 * @author BitCtrl Systems GmbH, krosse
 */
@Deprecated(since = "3.0.0", forRemoval = true)
public class DisjunctionDAOCriteriaContainer extends BaseDAOCriteriaContainer {

	public DisjunctionDAOCriteriaContainer(final DAOCriterion... criterion) {
		if (criterion != null) {
			for (final DAOCriterion e : criterion) {
				addDAOCriterion(e);
			}
		}
	}

}
