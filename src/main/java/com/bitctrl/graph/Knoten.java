package com.bitctrl.graph;

/**
 * Basisschnittstelle für einen Knoten in einem {@link Graph}.
 * 
 * @author BitCtrl Systems GmbH, Falko Schumann
 */
public interface Knoten {

	/** Konstante für den Wurzelbogen als Stützbogen des Wurzelknotens. */
	Bogen WURZEL_BOGEN = new Bogen() {

		/**
		 * Als Spezialfall hat der Wurzelbogen weder Anfangs- noch Endknoten.
		 * Die entsprechenden Methode wirft deswegen eine
		 * {@link UnsupportedOperationException}.
		 */
		@Override
		public Knoten getAnfangsKnoten() {
			throw new UnsupportedOperationException();
		}

		/**
		 * Als Spezialfall hat der Wurzelbogen weder Anfangs- noch Endknoten.
		 * Die entsprechenden Methode wirft deswegen eine
		 * {@link UnsupportedOperationException}.
		 */
		@Override
		public Knoten getEndKnoten() {
			throw new UnsupportedOperationException();
		}

		/**
		 * {@inheritDoc}
		 * 
		 * Die Länge des Wurzelknoten ist 0.
		 */
		@Override
		public double getLength() {
			return 0;
		}

		@Override
		public String toString() {
			return "Wurzelbogen";
		}

	};

	/**
	 * Gibt einen Iterator über alle außen inzidenten, also ausgehenden, Bögen
	 * des Knoten zurück.
	 * 
	 * @return einen Ausgangsbogeniterator.
	 */
	Iterable<Bogen> ausgangsBogenIterator();

	/**
	 * Gibt einen Iterator über alle innen inzidenten, also eingehenden, Bögen
	 * des Knoten zurück.
	 * 
	 * @return einen Eingangsbogeniterator.
	 */
	Iterable<Bogen> eingangsBogenIterator();

	/**
	 * Gibt einen Iterator über alle überhaupt inzidenten Bögen des Knoten
	 * zurück.
	 * 
	 * @return einen Iterator über die inzidenten Bögen.
	 */
	Iterable<Bogen> inzidentBogenIterator();

	/**
	 * Gibt den Stützbogen dieses Knotens zurück.
	 * 
	 * @return der Stüzbogen oder <code>null</code>, wenn es keinen Stützbogen
	 *         gibt.
	 */
	Bogen getStuetzBogen();

	/**
	 * Legt den Stützbogen des Knotens fest.
	 * 
	 * @param stuetzBogen
	 *            ein Bogen, der zu diesen Knoten führt, der
	 *            {@link #WURZEL_BOGEN} oder <code>null</code>, um keinen
	 *            Stützbogen festzulegen.
	 */
	void setStuetzBogen(final Bogen stuetzBogen);

	/**
	 * Gibt das Potential des Knotens zurück. Das Potential kann z.&nbsp;B. als
	 * Entfernung zum Starknoten interpretriert werden.
	 * 
	 * @return das Knotenpotential.
	 */
	double getPotential();

	/**
	 * Legt das Potential des Knotens fest. Das Potential kann z.&nbsp;B. als
	 * Entfernung zum Starknoten interpretriert werden.
	 * 
	 * @param potential
	 *            das Knotenpotential.
	 */
	void setPotential(double potential);

}
