package com.bitctrl.util.monitor;

/**
 * does nothing.
 * 
 * @author BitCtrl Systems GmbH, meissner
 */
public class NullMonitor extends AbstractMonitor {
	private boolean canceled;

	public void beginTask(final String name, final int totalWork) {
		// stub
	}

	public void done() {
		// stub
	}

	public boolean isCanceled() {
		return canceled;
	}

	public void setCanceled(final boolean canceled) {
		this.canceled = canceled;
	}

	public void setTaskName(final String name) {
		// stub
	}

	public void subTask(final String name) {
		// stub
	}

	public void worked(final int work) {
		// stub
	}
}
