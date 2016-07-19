/*
 * Java Common Library
 * Copyright (c) 2008 BitCtrl Systems GmbH
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

package com.bitctrl.util;

import java.util.HashMap;
import java.util.Map;

import com.bitctrl.Constants;

/**
 * Ein einfacher Scheduler für {@link AbstractCronJob}s. "Einfach" heißt, dass beim
 * ausführen von Aktionen nicht geprüft wird, ob ein Cron-Job noch läuft. Ein
 * einmal gestarteter Cron-Job wird "vergessen" und darauf vertraut, dass er
 * sich rechtzeitig vor dem nächsten Zyklus beendet und überhaupt irgendwann
 * beendet.
 * 
 * @author BitCtrl Systems GmbH, Falko Schumann
 * @version $Id: CronScheduler.java 13881 2008-11-06 13:37:21Z Schumann $
 */
public class CronScheduler {

	private class SchedulerThread extends Thread {

		SchedulerThread() {
			setName(getClass().getName());
		}

		/**
		 * Prüft zyklisch ob und welche Cron-Jobs ausgeführt werden müssen und
		 * führt sie auch aus. Jeder Cron-Job wird in einem eigenen Thread
		 * ausgeführt.
		 * 
		 * {@inheritDoc}
		 */
		@Override
		public void run() {
			do {
				final long currentTime;
				final long nextMinute;

				currentTime = getTime();

				// Warte bis zur nächsten Minute
				nextMinute = ((currentTime / 60000) + 1) * 60000;
				while (getTime() < nextMinute) {
					try {
						sleep(Constants.MILLIS_PER_SECOND);
					} catch (final InterruptedException ex) {
						interrupt(); // weil Flag zurückgesetzt wurde
						break; // Warteschleife beenden
					}
					if (isInterrupted()) {
						break; // Warteschleife beenden
					}
				}

				if (isInterrupted()) {
					break; // Thread beenden
				}

				// Führe alle Cron-Jobs aus, die jetzt dran sind
				for (final AbstractCronJob job : jobs.values()) {
					if (job.getPattern() != null
							&& job.getPattern().match(currentTime)) {
						new Thread(job, job.getName() + ", gestartet: "
								+ Timestamp.absoluteTime(currentTime)).start();
					}
				}
			} while (true);
		}

	}

	private final Map<Long, AbstractCronJob> jobs = new HashMap<Long, AbstractCronJob>();
	private final boolean daemon;
	private SchedulerThread thread;
	private boolean started = false;

	/**
	 * Erzeugt einen Scheduler. Entspricht {@code new CronScheduler(false)}.
	 * 
	 * @see #CronScheduler(boolean)
	 */
	public CronScheduler() {
		this(false);
	}

	/**
	 * Erzeugt einen Scheduler.
	 * 
	 * @param daemon
	 *            {@code true}, wenn der Scheduler-Thread als Daemon laufen
	 *            soll.
	 */
	public CronScheduler(final boolean daemon) {
		this.daemon = daemon;
	}

	/**
	 * Nimmt einen neuen Cron-Job in die Jobliste auf. Existiert bereits ein Job
	 * mit der selben Job-ID, dann wird dieser überschrieben.
	 * 
	 * @param job
	 *            ein Cron-Job
	 * @return die Id des geplanten Cron-Jobs (entspricht
	 *         {@link AbstractCronJob#getId()}).
	 */
	public long schedule(final AbstractCronJob job) {
		synchronized (jobs) {
			jobs.put(job.getId(), job);
		}
		return job.getId();
	}

	/**
	 * Entfernt einen Job aus der Jobliste. Existiert kein Job mit der ID,
	 * passiert nichts.
	 * 
	 * @param jobId
	 *            eine Job-ID.
	 */
	public void deschedule(final long jobId) {
		synchronized (jobs) {
			jobs.remove(jobId);
		}
	}

	/**
	 * Gibt den Cron-Job mit einer bestimmten ID zurück. Existiert unter der ID
	 * kein Job, wird {@code null} zurückgegeben.
	 * 
	 * @param jobId
	 *            eine Job-ID.
	 * @return der Cron-Job oder {@code null}.
	 */
	public AbstractCronJob getCronJob(final long jobId) {
		synchronized (jobs) {
			return jobs.get(jobId);
		}
	}

	/**
	 * Startet den Scheduler. Es wird zyklisch geprüft, welche Cron-Jobs
	 * ausgeführt werden müssen. Auszuführende Jobs werden entsprechend jeweils
	 * in einem eigenen Thread gestartet.
	 */
	public final synchronized void start() {
		if (started) {
			throw new IllegalStateException(
					"Der Cron-Scheduler wurde bereits gestartet.");
		}

		thread = new SchedulerThread();
		thread.setDaemon(daemon);
		thread.start();
		started = true;
	}

	/**
	 * Stopt den Scheduler. Laufende Cron-Jobs werden bis zu ihrem normalen Ende
	 * ausgeführt. Es werden keine neuen Jobs mehr angestoßen.
	 */
	public final synchronized void stop() {
		if (!started) {
			throw new IllegalStateException(
					"Der Cron-Scheduler wurde nicht gestartet.");
		}

		thread.interrupt();
		do {
			try {
				thread.join();
				break;
			} catch (final InterruptedException ex) {
				// Interessiert uns nicht
				ex.printStackTrace();
			}
		} while (true);
		started = false;
	}

	/**
	 * Fragt, ob der Scheduler gestartet wurde.
	 * 
	 * @return {@code true}, wenn der Scheduler gestartet ist.
	 */
	public final synchronized boolean isStarted() {
		return started;
	}

	/**
	 * Läßt den aktuellen Thread für die angegebene Zeit schlafen. Diese Methode
	 * kann von abgeleiteten Klassen überschrieben werden um eigene
	 * Pausemethoden zu implementieren. Die Standardimplementierung verwendet
	 * {@link Thread#sleep(long)}.
	 * 
	 * @param millis
	 *            die Länge der gewünschten Pause in Millisekunden.
	 * @throws InterruptedException
	 *             wenn das Sleep unterbrochen wurde.
	 */
	public void sleep(final long millis) throws InterruptedException {
		Thread.sleep(millis);
	}

	/**
	 * Gibt die aktuelle Zeit zurück. Diese Methode kann von abgeleiteten
	 * Klassen überschrieben werden um eigene Zeitbestimmung zu implementieren.
	 * Die Standardimplementierung verwendet {@link System#currentTimeMillis()}.
	 * 
	 * @return die aktuelle Zeit in Millisekunden.
	 */
	public long getTime() {
		return System.currentTimeMillis();
	}

}
