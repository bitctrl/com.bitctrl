package com.bitctrl.util;

public final class FinalVariable<T> {
	private T variable;

	public FinalVariable() {
		setVariable(null);
	}

	public FinalVariable(T var) {
		setVariable(var);
	}

	public void setVariable(T variable) {
		this.variable = variable;
	}

	public T getVariable() {
		return variable;
	}
}
