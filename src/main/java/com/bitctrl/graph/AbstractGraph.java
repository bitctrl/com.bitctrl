package com.bitctrl.graph;

/**
 * Basisklasse für eine {@link Graph}-Implementierung.
 * 
 * @author BitCtrl Systems GmbH, Falko Schumann
 * @version $Id$
 */
public abstract class AbstractGraph implements Graph {

	/**
	 * {@inheritDoc}
	 * 
	 * Wirft immer eine {@link UnsupportedOperationException}.
	 */
	@Override
	public boolean addKnoten(final Knoten knoten) {
		throw new UnsupportedOperationException();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * Wirft immer eine {@link UnsupportedOperationException}.
	 */
	@Override
	public boolean insertBogen(final Knoten startKnoten, final Knoten endKnoten) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void initStuetzBogen(final Knoten wurzel) {
		if (!getKnoten().contains(wurzel)) {
			throw new IllegalArgumentException(
					"Der Knoten ist im Netz nicht vorhanden: " + wurzel);
		}
		for (final Knoten knoten : getKnoten()) {
			if (knoten == wurzel) {
				knoten.setStuetzBogen(Knoten.WURZEL_BOGEN);
			} else {
				knoten.setStuetzBogen(null);
			}
		}
	}

	@Override
	public void initPotential(final double potential) {
		for (final Knoten knoten : getKnoten()) {
			knoten.setPotential(potential);
		}
	}

}
