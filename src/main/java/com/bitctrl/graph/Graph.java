package com.bitctrl.graph;

import java.util.Collection;
import java.util.Set;

/**
 * Basisschnittstelle für einen Graphen.
 * 
 * @author BitCtrl Systems GmbH, Falko Schumann
 * @version $Id$
 */
public interface Graph {

	/**
	 * Gibt die Knoten des Graphen zurück.
	 * 
	 * @return die Knotenmenge im Graph.
	 */
	Set<Knoten> getKnoten();

	/**
	 * Fügt einen neuen Knoten in den Graphen ein. Ist der Knoten bereits im
	 * Graph enthalten passiert nichts.
	 * 
	 * <p>
	 * Die Methode ist optional.
	 * 
	 * @param knoten
	 *            der neue Knoten.
	 * @return <code>true</code> wenn der Knoten hinzugefügt wurde, sonst
	 *         <code>false</code>.
	 */
	boolean addKnoten(Knoten knoten);

	/**
	 * Gibt die Bögen des Graphen zurück.
	 * 
	 * @return die Bogenmenge im Graph.
	 */
	Collection<Bogen> getBoegen();

	/**
	 * Fügt einen Bogen in den Graphen ein. Ein Bogen kann abhängig von der
	 * Implementierung des Graphen auch mehrfach hinzugefügt werden.
	 * 
	 * @param startKnoten
	 *            der Startknoten des Bogens.
	 * @param endKnoten
	 *            der Endknoten des Bogens.
	 * @return <code>true</code> wenn der Bogen hinzugefügt wurde, sonst
	 *         <code>false</code>.
	 */
	boolean insertBogen(Knoten startKnoten, Knoten endKnoten);

	/**
	 * Legt einen Knoten als Wurzel fest. Dabei wird der Stutzbogen aller Knoten
	 * auf <code>null</code> gesetzt. Nur der Stützbogen der Wurzel wird auf
	 * {@link Knoten#WURZEL_BOGEN} gesetzt.
	 * 
	 * @param wurzel
	 *            die Wurzel für das Stützbogengerüst.
	 */
	void initStuetzBogen(Knoten wurzel);

	/**
	 * Initialisiert das Potential aller Knoten mit einem bestimmten Wert.
	 * 
	 * @param potential
	 *            der Initialwert für das Knotenpotential.
	 */
	void initPotential(double potential);

}
