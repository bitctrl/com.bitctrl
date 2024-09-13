package com.bitctrl.modell.criteria;

import java.util.Collection;

/**
 * Container für UND oder ODER Verknüpfung von DAOCriterias
 * 
 * @author BitCtrl Systems GmbH, krosse
 */
@Deprecated(since = "3.0.0", forRemoval = true)
public interface DAOCriteriaContainer extends DAOCriterion {

	/**
	 * Collection der verknüpften Elemente
	 * 
	 * @return verknüpfte Elemente
	 */
	public Collection<DAOCriterion> getDAOCriterias();

	/**
	 * Hinzufügen eines neuen DAOCriterion
	 * 
	 * @param criterion neues {@link DAOCriterion}
	 */
	public void addDAOCriterion(DAOCriterion criterion);

	/**
	 * Entfernen des angegebenen {@link DAOCriterion}
	 * 
	 * @param criterion zu entferndes Element
	 */
	public void removeDAOCriterion(DAOCriterion criterion);
}
