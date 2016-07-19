/*
 * BitCtrl- Funktionsbibliothek
 * Copyright (C) 2007-2010 BitCtrl Systems GmbH 
 * 
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
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
package com.bitctrl.util.resultset;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Basisimplementierung für eine Menge zusammengehöriger Ergebnisse.
 * 
 * @author BitCtrl Systems GmbH, Albrecht Uhlmann
 * @version $Id: RelatedResultSet.java 27388 2010-11-05 10:35:07Z uhlmann $
 * @param <T>
 *            ein beliebier Datentyp, typischerweise Zahlen
 * @param <B>
 *            der Typ des zugrunde liegenden Datensatzes, z.B. SQLRow
 */
public abstract class RelatedResultSet<T, B> implements IRelatedResultSet<T, B> {

	private static final Timer TIMER = new Timer();

	private final Map<IIndividualResult<T, B>, Boolean> backend = new LinkedHashMap<IIndividualResult<T, B>, Boolean>();

	private final IRelatedResultSetContainer container;

	private final int timeoutMs;

	private TimerTask timerTask;

	/**
	 * Konstruktor übernimmt nur die Member.
	 * 
	 * @param container
	 *            der Container, zu dem diese Ergebnisemenge gehört.
	 * @param timeoutMs
	 *            Timeout in Millisekunden.
	 */
	public RelatedResultSet(final IRelatedResultSetContainer container,
			final int timeoutMs) {
		super();
		this.container = container;
		this.timeoutMs = timeoutMs;
	}

	public void neuerWert(final IIndividualResult<T, B> element, final T wert) {
		timerStarten();
		backend.put(element, true);
		boolean complete = true;
		for (final Boolean w : backend.values()) {
			if (null == w || !w.booleanValue()) {
				complete = false;
				break;
			}
		}
		if (complete) {
			notifyParent(false);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void dispose() {
		for (final IIndividualResult<T, B> iRes : getIndividualResults()) {
			iRes.dispose();
		}
		getBackend().clear();
	}

	/**
	 * Liefert die aktuellen individuellen Werte.
	 * 
	 * @return die Werte. Kann leer, aber nicht <code>null</code> sein.
	 */
	public Collection<IIndividualResult<T, B>> getIndividualResults() {
		final Collection<IIndividualResult<T, B>> iRes = new ArrayList<IIndividualResult<T, B>>(
				backend.size());
		iRes.addAll(backend.keySet());
		return iRes;
	}

	/**
	 * Bestimmt, ob ein individuelles Ergebnis gültig ist oder nicht. Der Sinn
	 * der Methode besteht darin, dass man in der Methode
	 * {@link IRelatedResultSetContainer#resultSetComplete(boolean)}
	 * herausfinden kann, welche individuellen Ergebnisse IN DIESEM LAUF einen
	 * Wert geliefert haben.
	 * 
	 * @param result
	 *            das zu prüfende Ergebnis.
	 * 
	 * @return true - Ergebnis hat gültige Daten.
	 */
	public boolean isValid(final IIndividualResult<T, B> result) {
		final Boolean b = backend.get(result);
		return null != b && b;
	}

	private void timerStarten() {
		if (null != timerTask) {
			return;
		}
		TIMER.purge();
		timerTask = new TimerTask() {

			@Override
			public void run() {
				notifyParent(true);
			}
		};
		TIMER.schedule(timerTask, timeoutMs);
	}

	private void notifyParent(final boolean timeout) {
		if (null != timerTask) {
			timerTask.cancel();
			timerTask = null;
			TIMER.purge();
		}
		container.resultSetComplete(timeout);
		for (final IIndividualResult<T, B> result : backend.keySet()) {
			backend.put(result, false);
		}
	}

	/**
	 * Liefert die Datengrundlage.
	 * 
	 * @return the backend
	 */
	protected Map<IIndividualResult<T, B>, Boolean> getBackend() {
		return backend;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return backend.toString();
	}
}
