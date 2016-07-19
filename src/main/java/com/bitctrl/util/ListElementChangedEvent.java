/**
 * 
 */
package com.bitctrl.util;

import java.util.EventObject;

/**
 * @author BitCtrl Systems GmbH, Schumann
 * @version $Id: ListElementChangedEvent.java 13881 2008-11-06 13:37:21Z Schumann $
 */
public class ListElementChangedEvent<T> extends EventObject {

	private final int index;
	private final T element;
	private final T previous;

	public ListElementChangedEvent(final Object source, final int index,
			final T element, final T previous) {
		super(source);

		this.index = index;
		this.element = element;
		this.previous = previous;

	}

	/**
	 * @return the index
	 */
	public int getIndex() {
		return index;
	}

	/**
	 * @return the element
	 */
	public T getElement() {
		return element;
	}

	/**
	 * @return the previous
	 */
	public T getPrevious() {
		return previous;
	}

}
