/*
 * Java Common Library
 * Copyright (c) 2008 BitCtrl Systems GmbH
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 3.0 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this library; if not, write to the Free Software Foundation, Inc.,
 * 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA.
 *
 * Contact Information:
 * BitCtrl Systems GmbH
 * Weißenfelser Straße 67
 * 04229 Leipzig
 * Phone: +49 341-490670
 * mailto: info@bitctrl.de
 */

package com.bitctrl.geo.graph;

/**
 * Repräsentiert einen Graphen.
 * 
 * @author BitCtrl Systems GmbH, Falko Schumann
 * 
 * @param <N>
 *            der Typ der Knoten.
 * @param <E>
 *            der Typ der Kanten.
 */
public interface Graph<N extends Node, E extends Edge> {

	/**
	 * Fügt einen neuen Knoten ohne Verbindung in den Graphen ein.
	 * 
	 * @param node
	 *            der neue Knoten.
	 */
	void addNode(N node);

	/**
	 * Entfernt den angegebenen Knoten aus dem Graphen.
	 * 
	 * @param node
	 *            ein Knoten des Graph.
	 */
	void removeNode(N node);

	/**
	 * Prüft ob ein bestimmter Knoten im Graph enthalten ist.
	 * 
	 * @param node
	 *            ein Knoten.
	 * @return {@code true}, wenn der Knoten im Graph enthalten ist.
	 */
	boolean containsNode(N node);

	/**
	 * Gibt einen Iterator für die Knoten des Graphen zurück.
	 * 
	 * @return ein Knoteniterator.
	 */
	Iterable<Node> nodes();

	/**
	 * Fügt eine neue Kanten zwischen zwei Knoten in den Graphen ein.
	 * 
	 * @param edge
	 *            die neue Kante.
	 */
	void insertEdge(E edge);

	/**
	 * Entfernt die angegebene Kante aus dem Graphen.
	 * 
	 * @param edge
	 *            eine Kante des Graphen.
	 */
	void removeEdge(E edge);

	/**
	 * Prüft ob der Graph eine Kante zwischen den beiden angegebenen Knoten
	 * besitzt.
	 * 
	 * @param edge
	 *            eine Kante.
	 * @return {@code true}, wenn der Graph die Kante enthält.
	 */
	boolean containsEdge(E edge);

	/**
	 * Prüft ob der Graph eine Kante zwischen den beiden angegebenen Knoten
	 * besitzt.
	 * 
	 * @param source
	 *            der Startknoten der Kante.
	 * @param target
	 *            der Endknoten der Kante.
	 * @return {@code true}, wenn der Graph die Kante enthält.
	 */
	boolean containsEdge(N source, N target);

	/**
	 * Gibt einen Iterator über die Kanten des Graphen zurück.
	 * 
	 * @return ein Kanteniterator.
	 */
	Iterable<Edge> edges();

	/**
	 * Gibt einen Iterator der Ausgangsbögen eines Knotens zurück.
	 * 
	 * @param node
	 *            ein Konten im Graph.
	 * @return der Ausgangsbogeniterator.
	 */
	Iterable<Edge> outEdges(N node);

	/**
	 * Gibt einen Iterator der Eingangsbögen eines Knotens zurück.
	 * 
	 * @param node
	 *            ein Konten im Graph.
	 * @return der Eingangsbogeniterator.
	 */
	Iterable<Edge> inEdges(N node);

	/**
	 * Gibt einen Iterator über inzidenten Kanten eines Knoten zurück.
	 * 
	 * @param node
	 *            ein Knoten im Graph.
	 * @return der Iterator der inzidenten Kanten.
	 */
	Iterable<Edge> incidentEdges(N node);

}
