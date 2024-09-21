package com.bitctrl.util.monitor;

/**
 * does nothing.
 * 
 * @author BitCtrl Systems GmbH, meissner
 */
public class NullMonitor extends AbstractMonitor {
	private boolean canceled;

	@Override
	public void beginTask(final String name, final int totalWork) {
		// stub
	}

	@Override
	public void done() {
		// stub
	}

	@Override
	public boolean isCanceled() {
		return canceled;
	}

	@Override
	public void setCanceled(final boolean canceled) {
		this.canceled = canceled;
	}

	@Override
	public void setTaskName(final String name) {
		// stub
	}

	@Override
	public void subTask(final String name) {
		// stub
	}

	@Override
	public void worked(final int work) {
		// stub
	}
}
