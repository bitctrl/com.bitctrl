package com.bitctrl.util.monitor;

import java.util.HashSet;
import java.util.Set;

public abstract class AbstractMonitor implements IMonitor {

	private Set<IMonitorProgressListener> listeners = null;

	@Override
	public synchronized final void addMonitorListener(final IMonitorProgressListener listener) {
		if (null == listeners) {
			listeners = new HashSet<>();
		}
		listeners.add(listener);
	}

	@Override
	public synchronized final void removeMonitorListener(final IMonitorProgressListener listener) {
		if (null != listener) {
			listeners.remove(listener);
			if (listeners.isEmpty()) {
				listeners = null;
			}
		}
	}

	protected synchronized final void notifyMonitorListeners(final double progress) {
		if (null != listeners) {
			for (final IMonitorProgressListener listener : listeners) {
				listener.progress(progress);
			}
		}
	}
}