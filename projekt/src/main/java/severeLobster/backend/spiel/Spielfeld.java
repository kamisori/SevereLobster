package severeLobster.backend.spiel;

import infrastructure.constants.enums.SchwierigkeitsgradEnumeration;

import java.io.Serializable;

/**
 * Spielfeld eines Spiels - Besteht aus einem 2Dimensionalem-Spielstein
 * Koordinatensysten. Vorschlag für geänderte API (ohne auskommentierte Sachen):
 * Eingeschränkte API für weniger mögliche Zustände, die man kontrollieren muss.
 * Für Größenänderunge einfach neue Instanz erzeugen. Wenn einmal erstellt, ist
 * das Feld mit den Spielsteinen konstant.
 * 
 * 
 * @author Lars Schlegelmilch, Lutz Kleiber
 */
public class Spielfeld implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4673868060555706754L;
	// private Spielstein[][] koordinaten;
	private final Spielstein[][] koordinaten;

	// public Spielfeld() {
	// }

	/**
	 * Im Vergleich zur vorherigen API sind breite und laenge bei parameterliste
	 * vertauscht, um Einheitlichkeit mit getSpielstein() zu haben.
	 * 
	 * @param breite
	 * @param laenge
	 */
	public Spielfeld(final int breite, final int laenge) {
		if (breite < 1 || laenge < 1) {
			throw new IllegalArgumentException("Nicht erlaubte Laenge/Breite");
		}
		this.koordinaten = new Spielstein[breite][laenge];

		/** Feld mit NullState Spielsteinen füllen */
		for (int laengeIndex = 0; laengeIndex < laenge; laengeIndex++) {
			for (int breiteIndex = 0; breiteIndex < breite; breiteIndex++) {
				koordinaten[breiteIndex][laengeIndex] = new Spielstein();
			}
		}
	}

	/**
	 * Bestimmt Anhang der der Groesse des Spielfeldes und der Anzahl an
	 * Spielsteinen einen Schwierigkeitsgrad
	 * 
	 * @return Schwierigkeitsgrad des Spielfeldes
	 */
	public SchwierigkeitsgradEnumeration getSchwierigkeitsgrad() {
		// TODO Anhand von Groesse und Spielsteinen einen Schwierigkeitsgrad
		// ermitteln
		return SchwierigkeitsgradEnumeration.LEICHT;
	}

	public Spielstein getSpielstein(int x, int y) {
		return koordinaten[x][y];
	}

	// public void setSpielstein(int x, int y, Spielstein spielstein) {
	// koordinaten[x][y] = spielstein;
	// }
	//
	// public void setGroesse(int laenge, int breite) {
	// koordinaten = new Spielstein[laenge][breite];
	// }

}
