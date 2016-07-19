package com.bitctrl.graph;

/**
 * Basisklasse für {@link Knoten}-Implementierungen.
 * 
 * @author BitCtrl Systems GmbH, Falko Schumann
 * @version $Id$
 */
public abstract class AbstractKnoten implements Knoten {

	private Bogen stuetzBogen;
	private double distance;

	@Override
	public Bogen getStuetzBogen() {
		return stuetzBogen;
	}

	@Override
	public void setStuetzBogen(final Bogen stuetzBogen) {
		this.stuetzBogen = stuetzBogen;
	}

	@Override
	public double getPotential() {
		return distance;
	}

	@Override
	public void setPotential(final double distance) {
		this.distance = distance;
	}

}
