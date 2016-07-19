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

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Basisimplementierung eines Graphen.
 * <p>
 * Die notwendige Logik der Kanten ist aus Gründen der Performance mit {@code
 * protected}-Membern anstelle von Gettern und Settern ausgelegt.
 * 
 * @author BitCtrl Systems GmbH, Falko Schumann
 * 
 * @param <N>
 *            der Typ der Knoten.
 * @param <E>
 *            der Typ der Kanten.
 */
public class BaseGraph<N extends Node, E extends Edge> implements Graph<N, E> {

	/** Die Liste der Knoten im Graphen. */
	protected final List<Node> nodes = new LinkedList<Node>();

	/** Die Liste der Kanten im Graphen. */
	protected final List<Edge> edges = new LinkedList<Edge>();

	public void addNode(final N node) {
		nodes.add(node);
	}

	public void removeNode(final N node) {
		nodes.remove(node);
	}

	public boolean containsNode(final N node) {
		return nodes.contains(node);
	}

	public Iterable<Node> nodes() {
		return nodes;
	}

	public void insertEdge(final E edge) {
		edges.add(edge);
	}

	public void removeEdge(final E edge) {
		edges.remove(edge);
	}

	public Iterable<Edge> edges() {
		return edges;
	}

	public boolean containsEdge(final E edge) {
		return edges.contains(edge);
	}

	public boolean containsEdge(final N source, final N target) {
		for (final Edge e : edges()) {
			if (source.equals(e.getSource()) && target.equals(e.getTarget())) {
				return true;
			}
		}
		return false;
	}

	public Iterable<Edge> inEdges(final N node) {
		final List<Edge> inEdges = new ArrayList<Edge>();
		BaseEdge edge;

		// Alle Eingangsbögen
		edge = ((BaseNode) node).firstIn;
		inEdges.add(edge);
		while (edge.nextIn != null) {
			inEdges.add(edge.nextIn);
			edge = edge.nextIn;
		}

		return inEdges;
	}

	public Iterable<Edge> outEdges(final N node) {
		final List<Edge> outEdges = new ArrayList<Edge>();
		BaseEdge edge;

		// Alle Ausgangsbögen
		edge = ((BaseNode) node).firstOut;
		outEdges.add(edge);
		while (edge.nextOut != null) {
			outEdges.add(edge.nextOut);
			edge = edge.nextOut;
		}

		return outEdges;
	}

	public Iterable<Edge> incidentEdges(final N node) {
		final List<Edge> incidentEdges = new ArrayList<Edge>();
		BaseEdge edge;

		// Alle Ausgangsbögen
		edge = ((BaseNode) node).firstOut;
		incidentEdges.add(edge);
		while (edge.nextOut != null) {
			incidentEdges.add(edge.nextOut);
			edge = edge.nextOut;
		}

		// Alle Eingangsbögen
		edge = ((BaseNode) node).firstIn;
		incidentEdges.add(edge);
		while (edge.nextIn != null) {
			incidentEdges.add(edge.nextIn);
			edge = edge.nextIn;
		}

		return incidentEdges;
	}
}
