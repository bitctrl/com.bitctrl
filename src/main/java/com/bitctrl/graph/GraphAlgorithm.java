package com.bitctrl.graph;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

/**
 * Stellt allgemeine Algorithmen auf Graphen bereit.
 * 
 * @author BitCtrl Systems GmbH, Falko Schumann
 * @version $Id$
 */
public final class GraphAlgorithm {

	/**
	 * Führt eine Breitensuche ausgehend von einem Knoten im Graph durch. Es
	 * wird ein Stützgerüst erzeugt über das alle erreichbaren Knoten vom
	 * Startknoten aus enthält.
	 * 
	 * <p>
	 * Ein bereits vorhandenes Gerüst im Graph wird überschrieben!
	 * 
	 * @param graph
	 *            der Graph in dem die Breitensuche durchgeführt werden soll.
	 * @param startKnoten
	 *            der Startknoten.
	 */
	public static void breitensuche(final Graph graph, final Knoten startKnoten) {
		if (startKnoten == null) {
			throw new IllegalArgumentException(
					"Der Startknoten muss ungleich null sein.");
		}

		graph.initStuetzBogen(startKnoten);

		final Queue<Knoten> queue = new LinkedList<Knoten>();
		queue.add(startKnoten);

		Knoten current = queue.poll();
		while (current != null) {
			for (final Bogen b : current.ausgangsBogenIterator()) {
				final Knoten nachfolger = b.getEndKnoten();

				if (nachfolger.getStuetzBogen() != null) {
					continue;
				}

				nachfolger.setStuetzBogen(b);
				if (nachfolger.getStuetzBogen() != null) {
					queue.add(nachfolger);
				}
			}

			current = queue.poll();
		}
	}

	public static void dijkstra(final Graph graph, final Knoten startKnoten) {
		if (startKnoten == null) {
			throw new IllegalArgumentException(
					"Der Startknoten muss ungleich null sein.");
		}

		graph.initStuetzBogen(startKnoten);
		graph.initPotential(0);

		Bogen minK;
		double tj;

		do {
			double minT = Double.MAX_VALUE;
			minK = null;

			for (final Bogen k : graph.getBoegen()) {
				final Knoten ak = k.getAnfangsKnoten();
				final Knoten ek = k.getEndKnoten();

				if (ak.getStuetzBogen() != null && ek.getStuetzBogen() == null) {
					tj = ak.getPotential() + k.getLength();
					if (tj < minT) {
						minT = tj;
						minK = k;
					}
				}
			}

			if (minK != null) {
				minK.getEndKnoten().setPotential(minT);
				minK.getEndKnoten().setStuetzBogen(minK);
			}
		} while (minK != null);
	}

	/**
	 * Bestimmt den Pfad von der Wurzel zu einem bestimmten Knoten.
	 * 
	 * <p>
	 * Setzt voraus, das z.&nbsp;B. mit der Breitensuche ein entsprechendes
	 * Gerüst erzeugt wurde.
	 * 
	 * @param zielKnoten
	 *            der Zielknoten
	 * @return der Pfad, eine leere ist, wenn der Zielknoten der Wurzelknoten
	 *         ist oder <code>null</code>, wenn es keinen Pfad gibt.
	 */
	public static List<Bogen> getPfadVonWurzel(final Knoten zielKnoten) {
		if (zielKnoten.getStuetzBogen() != null) {
			return getPfadVonWurzel(zielKnoten, new ArrayList<Bogen>());
		}

		return null;
	}

	private static List<Bogen> getPfadVonWurzel(final Knoten zielKnoten,
			final ArrayList<Bogen> pfad) {
		final Bogen stuetzBogen = zielKnoten.getStuetzBogen();

		if (stuetzBogen == Knoten.WURZEL_BOGEN) {
			return pfad;
		}

		pfad.add(0, stuetzBogen);
		return getPfadVonWurzel(stuetzBogen.getAnfangsKnoten(), pfad);
	}

	/**
	 * Bestimmt alle Nachfolgerknoten eines Knotens zurück.
	 * 
	 * @param knoten
	 *            der zu untersuchende Knoten.
	 * @return alle Nachfolgerknoten zu denen ein Bogen hinführt.
	 */
	public static Set<Knoten> getNachfolger(final Knoten knoten) {
		final Set<Knoten> result = new HashSet<Knoten>();

		for (final Bogen b : knoten.ausgangsBogenIterator()) {
			result.add(b.getEndKnoten());
		}

		return result;
	}

	/**
	 * Bestimmt alle Vorgängerknoten eines Knotens zurück.
	 * 
	 * @param knoten
	 *            der zu untersuchende Knoten.
	 * @return alle Vorgängerknoten von denen ein Bogen herführt.
	 */
	public static Set<Knoten> getVorgaenger(final Knoten knoten) {
		final Set<Knoten> result = new HashSet<Knoten>();

		for (final Bogen b : knoten.eingangsBogenIterator()) {
			result.add(b.getAnfangsKnoten());
		}

		return result;
	}

	private GraphAlgorithm() {
		// utility class
	}

}
