package com.bitctrl.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.Map.Entry;

/**
 * Provides a two-dimensional map.
 * 
 * The data entry matrix are automatically filled up so that every row
 * respective every column has the same size.
 * 
 * @param <R>
 *            the key type for the rows
 * @param <C>
 *            the key type for the columns
 * @param <D>
 *            the value type
 * 
 * @author BitCtrl Systems GmbH, schnepel
 * @version $Id$
 */
public class TwoDimensionalMap<R, C, D> implements Serializable, ITwoDimensionalMap<R, C, D> {
	private static final long serialVersionUID = 3216584215464568453L;

	private final Map<R, Integer> rows;
	private final Map<C, Integer> columns;
	private final ArrayList<ArrayList<D>> data = new ArrayList<ArrayList<D>>();
	private final boolean orderedRows;
	private final boolean orderedColumns;

	public TwoDimensionalMap(final boolean orderedRows, final boolean orderedColumns) {
		this.orderedRows = orderedRows;
		this.orderedColumns = orderedColumns;
		if (orderedRows) {
			rows = new TreeMap<R, Integer>();
		} else {
			rows = new LinkedHashMap<R, Integer>();
		}
		if (orderedColumns) {
			columns = new TreeMap<C, Integer>();
		} else {
			columns = new LinkedHashMap<C, Integer>();
		}
	}

	/**
	 * Erezeugt eine neue {@link TwoDimensionalMap} mit eigenen
	 * {@link Comparator}en für Zeilen und Spalten. Wird als {@link Comparator}
	 * <code>null</code> übergeben, dann wird nicht sortiert.
	 * 
	 * @param rowComparator
	 *            der Comparator die Zeilen der Map
	 * @param columnComparator
	 *            der Comparator die Spalten der Map
	 */
	public TwoDimensionalMap(final Comparator<R> rowComparator, final Comparator<C> columnComparator) {
		if (rowComparator != null) {
			rows = new TreeMap<R, Integer>(rowComparator);
			orderedRows = true;
		} else {
			rows = new LinkedHashMap<R, Integer>();
			orderedRows = false;
		}

		if (columnComparator != null) {
			columns = new TreeMap<C, Integer>(columnComparator);
			orderedColumns = true;
		} else {
			columns = new LinkedHashMap<C, Integer>();
			orderedColumns = false;
		}

	}

	// row stuff ...

	/**
	 * {@inheritDoc}
	 */
	public Set<R> getRowKeys() {
		final Set<R> keySet = rows.keySet();
		if (orderedRows) {
			return Collections.unmodifiableSortedSet((SortedSet<R>) keySet);
		}
		return Collections.unmodifiableSet(keySet);
	}

	/**
	 * {@inheritDoc}
	 */
	public Collection<D> getRow(final R row) {
		return Collections.unmodifiableCollection(getRowInternal(row));
	}

	private List<D> getRowInternal(final R row) {
		Integer rIndex = rows.get(row);
		if (null == rIndex) {
			addRow(row);
			rIndex = rows.get(row);
		}
		final ArrayList<D> retData = new ArrayList<D>(columns.size());
		final ArrayList<D> rData = data.get(rIndex);
		for (final Integer cIndex : columns.values()) {
			retData.add(rData.get(cIndex));
		}
		return retData;
	}

	public void addRow(final R row) {
		final ArrayList<D> retData = new ArrayList<D>(columns.size());
		rows.put(row, rows.size());
		retData.addAll(Collections.nCopies(columns.size(), (D) null));
		data.add(retData);
	}

	public Collection<? extends D> removeRow(final R row) {
		final Integer rIndex = rows.remove(row);
		if (null == rIndex) {
			throw new ArrayIndexOutOfBoundsException();
		}
		final ArrayList<D> rData = data.remove(rIndex.intValue());
		for (final Entry<R, Integer> e : rows.entrySet()) {
			final Integer v = e.getValue();
			if (v > rIndex) {
				e.setValue(v - 1);
			}
		}
		return rData;
	}

	public boolean containsRowKey(final R row) {
		return rows.containsKey(row);
	}

	// column stuff ...

	/**
	 * {@inheritDoc}
	 */
	public Set<C> getColumnKeys() {
		final Set<C> keySet = columns.keySet();
		if (orderedColumns) {
			return Collections.unmodifiableSortedSet((SortedSet<C>) keySet);
		}
		return Collections.unmodifiableSet(keySet);
	}

	public Collection<D> getColumn(final C column) {
		return Collections.unmodifiableCollection(getColumnInternal(column));
	}

	private List<D> getColumnInternal(final C column) {
		Integer cIndex = columns.get(column);
		if (null == cIndex) {
			addColumn(column);
			cIndex = columns.get(column);
		}
		final ArrayList<D> cData = new ArrayList<D>(rows.size());
		for (final Integer rIndex : rows.values()) {
			cData.add(data.get(rIndex).get(cIndex));
		}
		return cData;
	}

	public void addColumn(final C column) {
		columns.put(column, columns.size());
		for (final ArrayList<D> rowsData : data) {
			rowsData.add(null);
		}
	}

	public Collection<D> removeColumn(final C column) {
		final Integer cIndex = columns.remove(column);
		if (null == cIndex) {
			throw new NoSuchElementException();
		}
		ArrayList<D> cData = null;
		cData = new ArrayList<D>(rows.size());
		for (final ArrayList<D> rowsData : data) {
			cData.add(rowsData.remove(cIndex.intValue()));
		}
		for (final Entry<C, Integer> e : columns.entrySet()) {
			final Integer v = e.getValue();
			if (v > cIndex) {
				e.setValue(v - 1);
			}
		}
		return cData;
	}

	public boolean containsColumnKey(final C column) {
		return columns.containsKey(column);
	}

	// other stuff ...

	/**
	 * {@inheritDoc}
	 */
	public D put(final R row, final C column, final D value) {
		Integer cIndex = columns.get(column);
		if (null == cIndex) {
			getColumnInternal(column);
			cIndex = columns.get(column);
		}
		Integer rIndex = rows.get(row);
		if (null == rIndex) {
			getRowInternal(row);
			rIndex = rows.get(row);
		}
		return this.data.get(rIndex).set(cIndex, value);
	}

	/**
	 * {@inheritDoc}
	 */
	public D get(final R row, final C column) {
		Integer cIndex = columns.get(column);
		if (null == cIndex) {
			getColumnInternal(column);
			cIndex = columns.get(column);
		}
		Integer rIndex = rows.get(row);
		if (null == rIndex) {
			getRowInternal(row);
			rIndex = rows.get(row);
		}
		return this.data.get(rIndex).get(cIndex);
	}

	/**
	 * {@inheritDoc}
	 */
	public Collection<D> values() {
		final ArrayList<D> values = new ArrayList<D>(rows.size() * columns.size());
		for (final ArrayList<D> ld : data) {
			values.addAll(ld);
		}
		return Collections.unmodifiableCollection(values);
	}

	/**
	 * Entfernt alle Objekte aus der Map.
	 */
	public void clear() {
		columns.clear();
		data.clear();
		rows.clear();
	}

}