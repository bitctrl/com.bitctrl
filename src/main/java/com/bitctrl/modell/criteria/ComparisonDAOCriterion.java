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

package com.bitctrl.modell.criteria;

/**
 * Criteria fuer Vergleichsoperationen.
 * 
 * @author BitCtrl Systems GmbH, Krosse
 */
@Deprecated(since = "3.0.0", forRemoval = true)
public class ComparisonDAOCriterion implements DAOCriterion {

	/**
	 * Unterstuetzte Vergleichsoperationen.
	 */
	public enum RelationalTypes {

		/** equals. */
		EQUALS,

		/** greater than. */
		GREATER,

		/** greater than or equals. */
		GREATER_EQUALS,

		/** less than. */
		LESS,

		/** less than or equals. */
		LESS_EQUALS,

		/**
		 * between.
		 * 
		 * TODO Eventuell als eigenes Kriterium definieren.
		 */
		BETWEEN,

		/**
		 * in.
		 * 
		 * TODO Eventuell als eigenes Kriterium definieren.
		 */
		IN,

		/**
		 * case insensitive like.
		 * 
		 * TODOo Eventuell als eigenes Kriterium definieren, da nur für String sinnvoll.
		 */
		ILIKE,

	}

	private final RelationalTypes type;
	private final String propertyName;
	private final Object[] values;
	private final boolean not;

	/**
	 * Konstruktor.
	 * 
	 * @param propertyName Propertyname der Bean
	 * @param type         Typ des Vergleichsoperators
	 * @param values       Werte fuer Vergleichsoperation
	 */
	public ComparisonDAOCriterion(final String propertyName, final RelationalTypes type, final Object... values) {
		this(propertyName, type, false, values);
	}

	/**
	 * Konstruktor.
	 * 
	 * @param propertyName Propertyname der Bean.
	 * @param type         Typ des Vergleichsoperators.
	 * @param not          Wenn der Ausdruck verneint werden soll.
	 * @param values       Werte fuer Vergleichsoperation.
	 * 
	 *                     XXX: Ursprünglich war der Datentyp boolean in dem
	 *                     Konstruktor verwendet, aber wegen Bug
	 *                     https://bugs.eclipse.org/bugs/show_bug.cgi?id=384562 war
	 *                     der Konstruktor für den Eclipse Compiler nicht mehr
	 *                     eindeutig. Als Workaround verwenden wir einfach die
	 *                     Klasse Boolean statt boolean.
	 */
	public ComparisonDAOCriterion(final String propertyName, final RelationalTypes type, final Boolean not,
			final Object... values) {
		this.propertyName = propertyName;
		this.type = type;
		this.values = values;
		this.not = not;

		if (this.values == null || type != RelationalTypes.IN && this.values.length == 0) {
			throw new IllegalArgumentException("Values parameter can not be null or empty");
		}

		if (this.type == RelationalTypes.BETWEEN && this.values.length != 2) {
			throw new IllegalArgumentException("Values parameter need two objects for type between");
		}
	}

	/**
	 * Auslesen des Types der Vergleichsoperation.
	 * 
	 * @return Typ der Vergleichsoperation.
	 */
	public RelationalTypes getType() {
		return type;
	}

	/**
	 * Auslesen der Property.
	 * 
	 * @return Name der Property
	 */
	public String getPropertyName() {
		return propertyName;
	}

	/**
	 * Abfragen der Werte.
	 * 
	 * @return Menge von Werten.
	 */
	public Object[] getValues() {
		return values;
	}

	/**
	 * Gibt an ob dieser Ausdruck verneitn werden soll.
	 * 
	 * @return <code>true</code> wenn dieser Ausdruck verneint werden soll, sonst
	 *         <code>false</code>.
	 */
	public boolean isNot() {
		return not;
	}
}
