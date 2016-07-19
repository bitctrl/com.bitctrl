package com.bitctrl.graph;

/**
 * Basisschnittstelle für einen Bogen in einem {@link Graph}.
 * 
 * @author BitCtrl Systems GmbH, Falko Schumann
 */
public interface Bogen {

	/**
	 * Gibt den Anfangsknoten des Bogen zurück.
	 * 
	 * @return der Anfangsknoten des Bogen.
	 */
	Knoten getAnfangsKnoten();

	/**
	 * Gibt den Endknoten des Bogen zurück.
	 * 
	 * @return der Endknoten des Bogen.
	 */
	Knoten getEndKnoten();

	/**
	 * Gibt die Länge des Bogens zurück. Die Länge kann auch als Kosten
	 * interpretiert, die aufgebracht werden müssen, um den Bogen zu
	 * durchlaufen.
	 * 
	 * @return die Bogenlänge.
	 */
	double getLength();

}
