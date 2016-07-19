package com.bitctrl.util;

import java.util.ArrayList;
import java.util.List;

/**
 * Ermöglicht die Abbildung einer Menge von n Elementen des Typs I auf eine
 * Menge von n-1 Elementen des Typ O (wobei O == I sein kann) dadurch, dass
 * jeweils successive aufeinanderfolgenden Eingabeobjekten durch die Methode
 * {@link #combine(Object, Object)} ein neuer Wert zugewiesen wird.
 * 
 * @author BitCtrl Systems GmbH, Albrecht Uhlmann
 * @version $Id$
 * @param <I>
 *            Typ der Eingangsobjekte
 * @param <O>
 *            Typ der Ergebnisobjekte
 */
public abstract class ArrayMemberCombinator<I, O> implements Runnable {

	private final I[] inputs;

	private final boolean alwaysFirst;

	private final List<O> outputs;

	/**
	 * Implementiert die Zuordnungsvorschrift
	 * 
	 * @param i1
	 *            Eingabeobjekt 1
	 * @param i2
	 *            Eingabeobjekt 2
	 * @return Ergebnisobjet vom Typ O
	 */
	protected abstract O combine(I i1, I i2);

	/**
	 * Konstruktor übernimmt und prüft Argumente
	 * 
	 * @param inputs
	 *            Feld mit Eingabedaten. Muss mindestens ein Element enthalten
	 * @param alwaysFirst
	 *            Wenn true, wird immer mit dem ersten Element aus inputs
	 *            gearbeitet, ansonsten konsekutive Elemente.
	 */
	public ArrayMemberCombinator(final I[] inputs, final boolean alwaysFirst) {
		super();
		this.inputs = inputs;
		this.alwaysFirst = alwaysFirst;
		assert inputs != null;
		assert inputs.length > 0;
		outputs = new ArrayList<O>(inputs.length - 1);
	}

	public void run() {
		synchronized (inputs) {
			int loop;
			for (loop = 1; loop < inputs.length; ++loop) {
				final int index1 = alwaysFirst ? 0 : loop - 1;
				outputs.add(loop - 1, combine(inputs[index1], inputs[loop]));
			}
		}

	}

	/**
	 * Liefert die Ergebnisdaten
	 * 
	 * @return die Ergebnisse
	 */
	public List<O> getOutputs() {
		return outputs;
	}

}
