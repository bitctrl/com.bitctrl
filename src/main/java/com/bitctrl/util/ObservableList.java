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
 * @param <T> der Typ der Listenelemente
 */
public class ObservableList<T> implements List<T> {

	private final EventListenerList listener = new EventListenerList();
	private List<T> input;

	/**
	 * Registriert einen Listener.
	 * 
	 * @param l der Listener.
	 */
	public void addListChangeListener(final ListChangedListener l) {
		listener.add(ListChangedListener.class, l);
	}

	/**
	 * Meldet einen Listener wieder ab.
	 * 
	 * @param l der Listener.
	 */
	public void removeListChangeListener(final ListChangedListener l) {
		listener.remove(ListChangedListener.class, l);
	}

	@Override
	public boolean add(final T e) {
		final int index = input.size();
		final boolean b = input.add(e);
		if (b) {
			fireAdded(index, e);
		}

		return b;
	}

	@Override
	public void add(final int index, final T element) {
		input.add(index, element);
		fireAdded(index, element);
	}

	@Override
	public boolean addAll(final Collection<? extends T> c) {
		final int index = input.size();
		final boolean b = input.addAll(c);
		fireAdded(index, input.size() - 1, new ArrayList<>(c));
		return b;
	}

	@Override
	public boolean addAll(final int index, final Collection<? extends T> c) {
		final boolean b = input.addAll(index, c);
		fireAdded(index, input.size() - 1, new ArrayList<>(c));
		return b;
	}

	@Override
	public void clear() {
		final List<T> removed = new ArrayList<>(input);
		input.clear();
		fireRemoved(0, removed.size() - 1, removed);
	}

	@Override
	public boolean contains(final Object o) {
		return input.contains(o);
	}

	@Override
	public boolean containsAll(final Collection<?> c) {
		return input.containsAll(c);
	}

	@Override
	public T get(final int index) {
		return input.get(index);
	}

	@Override
	public int indexOf(final Object o) {
		return input.indexOf(o);
	}

	@Override
	public boolean isEmpty() {
		return input.isEmpty();
	}

	@Override
	public Iterator<T> iterator() {

		return new Iterator<>() {

			private final Iterator<T> iterator = input.iterator();
			private T current;
			private int index = -1;

			@Override
			public boolean hasNext() {
				return iterator.hasNext();
			}

			@Override
			public T next() {
				++index;
				current = iterator.next();
				return current;
			}

			@Override
			public void remove() {
				iterator.remove();
				fireRemoved(index, current);
			}

		};
	}

	@Override
	public int lastIndexOf(final Object o) {
		return input.lastIndexOf(o);
	}

	@Override
	public ListIterator<T> listIterator() {
		return new ListIterator<>() {

			private final ListIterator<T> iterator = input.listIterator();
			private T current;
			private int index = -1;

			@Override
			public void add(final T e) {
				iterator.add(e);
				fireAdded(input.size() - 1, e);
			}

			@Override
			public boolean hasNext() {
				return iterator.hasNext();
			}

			@Override
			public boolean hasPrevious() {
				return iterator.hasNext();
			}

			@Override
			public T next() {
				++index;
				current = iterator.next();
				return current;
			}

			@Override
			public int nextIndex() {
				return iterator.nextIndex();
			}

			@Override
			public T previous() {
				return iterator.previous();
			}

			@Override
			public int previousIndex() {
				return iterator.previousIndex();
			}

			@Override
			public void remove() {
				iterator.remove();
				fireRemoved(index, current);
			}

			@Override
			public void set(final T e) {
				iterator.set(e);
				fireChanged(index, e, current);
			}

		};
	}

	@Override
	public ListIterator<T> listIterator(final int index) {
		return new ListIterator<>() {

			private final ListIterator<T> iterator = input.listIterator(index);
			private T current;
			private int currentIndex = -1;

			@Override
			public void add(final T e) {
				iterator.add(e);
				fireAdded(input.size() - 1, e);
			}

			@Override
			public boolean hasNext() {
				return iterator.hasNext();
			}

			@Override
			public boolean hasPrevious() {
				return iterator.hasNext();
			}

			@Override
			public T next() {
				++currentIndex;
				current = iterator.next();
				return current;
			}

			@Override
			public int nextIndex() {
				return iterator.nextIndex();
			}

			@Override
			public T previous() {
				return iterator.previous();
			}

			@Override
			public int previousIndex() {
				return iterator.previousIndex();
			}

			@Override
			public void remove() {
				iterator.remove();
				fireRemoved(currentIndex, current);
			}

			@Override
			public void set(final T e) {
				iterator.set(e);
				fireChanged(currentIndex, e, current);
			}

		};
	}

	@Override
	public boolean remove(final Object o) {
		final int index = indexOf(o);
		final boolean b = input.remove(o);
		fireRemoved(index, (T) o);
		return b;
	}

	@Override
	public T remove(final int index) {
		final T prev = input.remove(index);
		fireRemoved(index, prev);
		return prev;
	}

	@Override
	public boolean removeAll(final Collection<?> c) {
		final boolean b = input.retainAll(c);
		final List<T> intersection = CollectionUtilities.intersection(input, new ArrayList(c));

		fireRemoved(-1, -1, intersection);
		return b;
	}

	@Override
	public boolean retainAll(final Collection<?> c) {
		final List<T> old = new ArrayList<>(input);
		final boolean b = input.retainAll(c);
		final List<T> difference = CollectionUtilities.difference(old, input);

		fireRemoved(-1, -1, difference);
		return b;
	}

	@Override
	public T set(final int index, final T element) {
		final T prev = input.set(index, element);
		fireChanged(index, element, prev);
		return prev;
	}

	@Override
	public int size() {
		return input.size();
	}

	@Override
	public List<T> subList(final int fromIndex, final int toIndex) {
		return input.subList(fromIndex, toIndex);
	}

	@Override
	public Object[] toArray() {
		return input.toArray();
	}

	@Override
	public <T0> T0[] toArray(final T0[] a) {
		return input.toArray(a);
	}

	/**
	 * Signalisiert, das ein einzelnes Element hinzugefügt wurde.
	 * 
	 * @param index der Index der Änderung
	 * @param added das hinzugefügte Element
	 */
	protected synchronized void fireAdded(final int index, final T added) {
		final ListChangedEvent<T> e = new ListChangedEvent<>(this, index, index, Collections.singletonList(added),
				new ArrayList<>());

		for (final ListChangedListener<T> l : listener.getListeners(ListChangedListener.class)) {
			l.listChanged(e);
		}
	}

	/**
	 * Signalisiert, das ein einzelnes Element aus der Liste entfernt wurde.
	 * 
	 * @param index   der Index der Änderung
	 * @param removed das entfernte Element
	 */
	protected synchronized void fireRemoved(final int index, final T removed) {
		final ListChangedEvent<T> e = new ListChangedEvent<>(this, index, index, new ArrayList<>(),
				Collections.singletonList(removed));

		for (final ListChangedListener<T> l : listener.getListeners(ListChangedListener.class)) {
			l.listChanged(e);
		}
	}

	/**
	 * Signalisiert, das ein einzelnes Element geändert wurde.
	 * 
	 * @param index    der Index der Änderung
	 * @param element  das neue Element
	 * @param previous das alte Element
	 */
	protected synchronized void fireChanged(final int index, final T element, final T previous) {
		final ListElementChangedEvent<T> e = new ListElementChangedEvent<>(this, index, element, previous);

		for (final ListChangedListener<T> l : listener.getListeners(ListChangedListener.class)) {
			l.elementChanged(e);
		}
	}

	protected synchronized void fireAdded(final int indexFrom, final int indexTo, final List<T> added) {
		final ListChangedEvent<T> e = new ListChangedEvent<>(this, indexFrom, indexTo, added, new ArrayList<>());

		for (final ListChangedListener<T> l : listener.getListeners(ListChangedListener.class)) {
			l.listChanged(e);
		}
	}

	protected synchronized void fireRemoved(final int indexFrom, final int indexTo, final List<T> removed) {
		final ListChangedEvent<T> e = new ListChangedEvent<>(this, indexFrom, indexTo, new ArrayList<>(), removed);

		for (final ListChangedListener<T> l : listener.getListeners(ListChangedListener.class)) {
			l.listChanged(e);
		}
	}

}
