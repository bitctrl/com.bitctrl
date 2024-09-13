package com.bitctrl.model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;

import jakarta.xml.bind.annotation.XmlTransient;

/**
 * Basisklasse für Modellklassen mit Property Change Support. Modellobjekte sind
 * serialisierbar, sowohl binär ({@link Serializable}) als auch in XML (Java XML
 * Binding).
 * 
 * @author BitCtrl Systems GmbH, Falko Schumann
 */
public class ModelElement implements Serializable {

	private static final long serialVersionUID = 1L;

	@XmlTransient
	private transient PropertyChangeSupport pcsDelegate = new PropertyChangeSupport(this);

	/**
	 * Registriert einen Property Listener für alle <em>bound properties</em>.
	 * 
	 * @param listener der zu registrierende Listener.
	 */
	public void addPropertyChangeListener(final PropertyChangeListener listener) {
		pcsDelegate.addPropertyChangeListener(listener);
	}

	/**
	 * Registriert einen Property Listener für eine einzelne <em>bound
	 * property</em>.
	 * 
	 * @param propertyName der Name der Property, die �berwacht werden soll.
	 * @param listener     der zu registrierende Listener.
	 */
	public void addPropertyChangeListener(final String propertyName, final PropertyChangeListener listener) {
		pcsDelegate.addPropertyChangeListener(propertyName, listener);
	}

	/**
	 * Entfernt einen registrierten Property Listener für alle <em>bound
	 * properties</em>.
	 * 
	 * @param listener der zu entfernende Listener.
	 */
	public void removePropertyChangeListener(final PropertyChangeListener listener) {
		pcsDelegate.removePropertyChangeListener(listener);
	}

	/**
	 * Entfernt einen registrierten Property Listener für eine einzelne <em>bound
	 * property</em>.
	 * 
	 * @param propertyName der Name der Property, die nicht mehr überwacht werden
	 *                     soll.
	 * @param listener     der zu entfernende Listener.
	 */
	public void removePropertyChangeListener(final String propertyName, final PropertyChangeListener listener) {
		pcsDelegate.removePropertyChangeListener(propertyName, listener);
	}

	/**
	 * Feuert ein Event für eine <em>bound property</em>.
	 * 
	 * @param propertyName der Name der Property die sich geändert hat.
	 * @param oldValue     der alte Wert oder <code>null</code>, wenn der alte Wert
	 *                     nicht bekannt ist.
	 * @param newValue     der neue Wert.
	 */
	protected synchronized void firePropertyChanged(final String propertyName, final Object oldValue,
			final Object newValue) {
		pcsDelegate.firePropertyChange(propertyName, oldValue, newValue);
	}

	/**
	 * Feuert ein Event für eine <em>indexed bound property</em>.
	 * 
	 * @param propertyName der Name der Property die sich geändert hat.
	 * @param index        der Index des Elements das sich geändert hat.
	 * @param oldValue     der alte Wert oder <code>null</code>, wenn der alte Wert
	 *                     nicht bekannt ist.
	 * @param newValue     der neue Wert.
	 */
	protected synchronized void fireIndexedPropertyChanged(final String propertyName, final int index,
			final Object oldValue, final Object newValue) {
		pcsDelegate.fireIndexedPropertyChange(propertyName, index, oldValue, newValue);
	}

	private void readObject(final ObjectInputStream in) throws IOException, ClassNotFoundException {
		in.defaultReadObject();
		pcsDelegate = new PropertyChangeSupport(this);
	}

}
