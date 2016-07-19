package com.bitctrl.model;

/**
 * Ein benanntes Modellelement.
 * 
 * @author BitCtrl Systems GmbH, Falko Schumann
 */
public class NamedObject extends ModelElement {

	/** Konstante für den Namen der entsprechenden Property. */
	public static final String PROP_NAME = "name";

	private static final long serialVersionUID = 1L;

	private String name;

	/**
	 * Gibt den Namen des Objekts zurück.
	 * 
	 * @return der Objektname.
	 */
	public String getName() {
		if (name == null) {
			return "";
		}
		return name;
	}

	/**
	 * Legt einen Namen für das Objekt fest.
	 * 
	 * <p>
	 * <em>bound property</em>.
	 * 
	 * @param name
	 *            der neue Objektname.
	 * 
	 */
	public void setName(final String name) {
		final String oldValue = this.name;
		this.name = name;
		firePropertyChanged(PROP_NAME, oldValue, name);
	}

	@Override
	public String toString() {
		if (name == null || name.isEmpty()) {
			return "Unbenannt";
		}
		return name;
	}

}
