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

package com.bitctrl.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import javax.swing.event.EventListenerList;

/**
 * Eine Liste, die bei Strukturänderung einen Listener benachricht.
 * <p>
 * <em>Hinweis:</em> Änderungen an den enthaltenen Objekten werden nicht
 * registriert, nur wenn welche der Liste hinzugefügt oder aus ihr entfernt
 * werden!
 * 
 * @author BitCtrl Systems GmbH, Schumann
 * 
 * @param <T>
 *            der Typ der Listenelemente
 */
public class ObservableList<T> implements List<T> {

	private final EventListenerList listener = new EventListenerList();
	private List<T> input;

	/**
	 * Registriert einen Listener.
	 * 
	 * @param l
	 *            der Listener.
	 */
	public void addListChangeListener(final ListChangedListener l) {
		listener.add(ListChangedListener.class, l);
	}

	/**
	 * Meldet einen Listener wieder ab.
	 * 
	 * @param l
	 *            der Listener.
	 */
	public void removeListChangeListener(final ListChangedListener l) {
		listener.remove(ListChangedListener.class, l);
	}

	public boolean add(final T e) {
		final int index = input.size();
		final boolean b = input.add(e);
		if (b) {
			fireAdded(index, e);
		}

		return b;
	}

	public void add(final int index, final T element) {
		input.add(index, element);
		fireAdded(index, element);
	}

	public boolean addAll(final Collection<? extends T> c) {
		final int index = input.size();
		final boolean b = input.addAll(c);
		fireAdded(index, input.size() - 1, new ArrayList<T>(c));
		return b;
	}

	public boolean addAll(final int index, final Collection<? extends T> c) {
		final boolean b = input.addAll(index, c);
		fireAdded(index, input.size() - 1, new ArrayList<T>(c));
		return b;
	}

	public void clear() {
		final List<T> removed = new ArrayList<T>(input);
		input.clear();
		fireRemoved(0, removed.size() - 1, removed);
	}

	public boolean contains(final Object o) {
		return input.contains(o);
	}

	public boolean containsAll(final Collection<?> c) {
		return input.containsAll(c);
	}

	public T get(final int index) {
		return input.get(index);
	}

	public int indexOf(final Object o) {
		return input.indexOf(o);
	}

	public boolean isEmpty() {
		return input.isEmpty();
	}

	public Iterator<T> iterator() {

		return new Iterator<T>() {

			private final Iterator<T> iterator = input.iterator();
			private T current;
			private int index = -1;

			public boolean hasNext() {
				return iterator.hasNext();
			}

			public T next() {
				++index;
				current = iterator.next();
				return current;
			}

			public void remove() {
				iterator.remove();
				fireRemoved(index, current);
			}

		};
	}

	public int lastIndexOf(final Object o) {
		return input.lastIndexOf(o);
	}

	public ListIterator<T> listIterator() {
		return new ListIterator<T>() {

			private final ListIterator<T> iterator = input.listIterator();
			private T current;
			private int index = -1;

			public void add(final T e) {
				iterator.add(e);
				fireAdded(input.size() - 1, e);
			}

			public boolean hasNext() {
				return iterator.hasNext();
			}

			public boolean hasPrevious() {
				return iterator.hasNext();
			}

			public T next() {
				++index;
				current = iterator.next();
				return current;
			}

			public int nextIndex() {
				return iterator.nextIndex();
			}

			public T previous() {
				return iterator.previous();
			}

			public int previousIndex() {
				return iterator.previousIndex();
			}

			public void remove() {
				iterator.remove();
				fireRemoved(index, current);
			}

			public void set(final T e) {
				iterator.set(e);
				fireChanged(index, e, current);
			}

		};
	}

	public ListIterator<T> listIterator(final int index) {
		return new ListIterator<T>() {

			private final ListIterator<T> iterator = input.listIterator(index);
			private T current;
			private int currentIndex = -1;

			public void add(final T e) {
				iterator.add(e);
				fireAdded(input.size() - 1, e);
			}

			public boolean hasNext() {
				return iterator.hasNext();
			}

			public boolean hasPrevious() {
				return iterator.hasNext();
			}

			public T next() {
				++currentIndex;
				current = iterator.next();
				return current;
			}

			public int nextIndex() {
				return iterator.nextIndex();
			}

			public T previous() {
				return iterator.previous();
			}

			public int previousIndex() {
				return iterator.previousIndex();
			}

			public void remove() {
				iterator.remove();
				fireRemoved(currentIndex, current);
			}

			public void set(final T e) {
				iterator.set(e);
				fireChanged(currentIndex, e, current);
			}

		};
	}

	public boolean remove(final Object o) {
		final int index = indexOf(o);
		final boolean b = input.remove(o);
		fireRemoved(index, (T) o);
		return b;
	}

	public T remove(final int index) {
		final T prev = input.remove(index);
		fireRemoved(index, prev);
		return prev;
	}

	public boolean removeAll(final Collection<?> c) {
		final boolean b = input.retainAll(c);
		final List<T> intersection = CollectionUtilities.intersection(input, new ArrayList(c));

		fireRemoved(-1, -1, intersection);
		return b;
	}

	public boolean retainAll(final Collection<?> c) {
		final List<T> old = new ArrayList<T>(input);
		final boolean b = input.retainAll(c);
		final List<T> difference = CollectionUtilities.difference(old, input);

		fireRemoved(-1, -1, difference);
		return b;
	}

	public T set(final int index, final T element) {
		final T prev = input.set(index, element);
		fireChanged(index, element, prev);
		return prev;
	}

	public int size() {
		return input.size();
	}

	public List<T> subList(final int fromIndex, final int toIndex) {
		return input.subList(fromIndex, toIndex);
	}

	public Object[] toArray() {
		return input.toArray();
	}

	public <T0> T0[] toArray(final T0[] a) {
		return input.toArray(a);
	}

	/**
	 * Signalisiert, das ein einzelnes Element hinzugefügt wurde.
	 * 
	 * @param index
	 *            der Index der Änderung
	 * @param added
	 *            das hinzugefügte Element
	 */
	protected synchronized void fireAdded(final int index, final T added) {
		final ListChangedEvent<T> e = new ListChangedEvent<T>(this, index, index, Collections.singletonList(added),
				new ArrayList<T>());

		for (final ListChangedListener<T> l : listener.getListeners(ListChangedListener.class)) {
			l.listChanged(e);
		}
	}

	/**
	 * Signalisiert, das ein einzelnes Element aus der Liste entfernt wurde.
	 * 
	 * @param index
	 *            der Index der Änderung
	 * @param removed
	 *            das entfernte Element
	 */
	protected synchronized void fireRemoved(final int index, final T removed) {
		final ListChangedEvent<T> e = new ListChangedEvent<T>(this, index, index, new ArrayList<T>(),
				Collections.singletonList(removed));

		for (final ListChangedListener<T> l : listener.getListeners(ListChangedListener.class)) {
			l.listChanged(e);
		}
	}

	/**
	 * Signalisiert, das ein einzelnes Element geändert wurde.
	 * 
	 * @param index
	 *            der Index der Änderung
	 * @param element
	 *            das neue Element
	 * @param previous
	 *            das alte Element
	 */
	protected synchronized void fireChanged(final int index, final T element, final T previous) {
		final ListElementChangedEvent<T> e = new ListElementChangedEvent<T>(this, index, element, previous);

		for (final ListChangedListener<T> l : listener.getListeners(ListChangedListener.class)) {
			l.elementChanged(e);
		}
	}

	protected synchronized void fireAdded(final int indexFrom, final int indexTo, final List<T> added) {
		final ListChangedEvent<T> e = new ListChangedEvent<T>(this, indexFrom, indexTo, added, new ArrayList<T>());

		for (final ListChangedListener<T> l : listener.getListeners(ListChangedListener.class)) {
			l.listChanged(e);
		}
	}

	protected synchronized void fireRemoved(final int indexFrom, final int indexTo, final List<T> removed) {
		final ListChangedEvent<T> e = new ListChangedEvent<T>(this, indexFrom, indexTo, new ArrayList<T>(), removed);

		for (final ListChangedListener<T> l : listener.getListeners(ListChangedListener.class)) {
			l.listChanged(e);
		}
	}

}
