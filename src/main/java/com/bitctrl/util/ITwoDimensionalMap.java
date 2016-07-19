package com.bitctrl.util;

import java.util.Collection;

public interface ITwoDimensionalMap<R, C, D> {

	/**
	 * Returns all known row keys. This is backed by the map so that changes to
	 * the map get reflected.
	 * 
	 * @return a collection containing all known row keys.
	 */
	public abstract Collection<R> getRowKeys();

	/**
	 * Returns all currently known row entry values for the given row key. The
	 * returned collection is unmodifiable and doesn't reflect changes to the
	 * map. The values are null for unset columns.
	 * 
	 * @param row
	 *            the row key
	 * @return all known values for the given row.
	 */
	public abstract Collection<D> getRow(final R row);

	/**
	 * Return all known column keys. This is backed by the map so that changes
	 * to the map get reflected.
	 * 
	 * @return a collection containing all known column keys.
	 */
	public abstract Collection<C> getColumnKeys();

	/**
	 * Returns all currently known column entry values for the given column key.
	 * The returned collection is unmodifiable and doesn't reflect changes to
	 * the map. The values are null for unset rows.
	 * 
	 * @param column
	 *            the column key
	 * @return all known values for the given column.
	 */
	public abstract Collection<D> getColumn(final C column);

	/**
	 * Stores a value in the map at a specific row/column position.
	 * 
	 * @param row
	 *            the row key
	 * @param column
	 *            the column key
	 * @param value
	 *            the value to be set.
	 * @return the previously stored value.
	 */
	public abstract D put(final R row, final C column, final D value);

	/**
	 * Returns a value from the map stored at a specific row/column position.
	 * 
	 * @param row
	 *            the row key
	 * @param column
	 *            the column key
	 * @return the stored value.
	 */
	public abstract D get(final R row, final C column);

	/**
	 * Returns all values as a collection.
	 * 
	 * @return all values
	 */
	public abstract Collection<D> values();

	/**
	 * Removes a row with all its values and returns them as collection.
	 * 
	 * @param row
	 *            the row key
	 * @return the removed entries.
	 */
	Collection<? extends D> removeRow(R row);

	void addRow(R row);

	void addColumn(C column);
}