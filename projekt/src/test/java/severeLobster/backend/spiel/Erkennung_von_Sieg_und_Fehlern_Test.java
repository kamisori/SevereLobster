package severeLobster.backend.spiel;

import static org.junit.Assert.*;

import infrastructure.constants.enums.PfeilrichtungEnumeration;
import infrastructure.constants.enums.SpielmodusEnumeration;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class Erkennung_von_Sieg_und_Fehlern_Test {

	private Spiel testSpiel;
	private Spielfeld spielfeld;
	private Spielstein a,b,c,d;
	
	@Before
	public void setUp() throws Exception {
		testSpiel = new Spiel(new Spielfeld(4, 4),SpielmodusEnumeration.SPIELEN);
		spielfeld = testSpiel.getSpielfeld();
		a = new Spielstein();
		b = new Spielstein();
		c = new Spielstein();
		d = new Spielstein();
	}

	/**
	 * Prüft ob ein Spielfeld gelöst wurde, bei dem das Getippte exakt dem realen Spielfeld entspricht
	 */
	@Test
	public void erfolgreich_beendetes_spiel_erkennen() {
		
		// Stern da, Stern getippt
		a.setRealState(new Stern());
		a.setVisibleState(new Stern());
		
		// kein Stern, nichts getippt
		b.setRealState(new Blank());
		b.setVisibleState(new Blank());

		// kein Stern, getippt dass dort keiner ist
		c.setRealState(new Blank());
		c.setVisibleState(new Ausschluss());
		
		// Pfeil, Pfeil muss auch angezeigt werden
		d.setRealState(new Pfeil(PfeilrichtungEnumeration.NORDWEST));
		d.setVisibleState(new Pfeil(PfeilrichtungEnumeration.NORDWEST));
		
		spielfeld.setSpielstein(0, 0, a);
		spielfeld.setSpielstein(0, 1, b);
		spielfeld.setSpielstein(1, 0, c);
		spielfeld.setSpielstein(1, 1, d);
		
		assertTrue(testSpiel.isSolved());
		
	}

	/**
	 * Prüft ob ein Spielfeld gelöst wurde.
	 * Hier wurde explizit ein Ausschluss verwendet.
	 */
	@Test
	public void erfolgreich_beendetes_spiel_mit_ausschluss_erkennen() {
		
		// Stern da, Stern getippt
		a.setRealState(new Stern());
		a.setVisibleState(new Stern());
		
		// kein Stern, nichts getippt
		b.setRealState(new Blank());
		b.setVisibleState(new Ausschluss());

		// kein Stern, getippt dass dort keiner ist
		c.setRealState(new Blank());
		c.setVisibleState(new Ausschluss());
		
		// Pfeil, Pfeil muss auch angezeigt werden
		d.setRealState(new Pfeil(PfeilrichtungEnumeration.NORDWEST));
		d.setVisibleState(new Pfeil(PfeilrichtungEnumeration.NORDWEST));
		
		spielfeld.setSpielstein(0, 0, a);
		spielfeld.setSpielstein(0, 1, b);
		spielfeld.setSpielstein(1, 0, c);
		spielfeld.setSpielstein(1, 1, d);
		
		assertTrue(testSpiel.isSolved());
		
	}
	
	/**
	 * Prüft ob Fehler vorliegen. Hier wurde ein Stern noch nicht gefunden.
	 * Die Methode muss false zurückgeben, da noch kein Fehler vorliegt.
	 */
	@Test
	public void stern_noch_nicht_getippt_erkennen() {
		
		// Stern da, kein Stern getippt 
		// (hier darf kein Fehler auftreten, da noch nicht ausgeschlossen wurde, dass hier ein Stern liegt)
		a.setRealState(new Stern());
		a.setVisibleState(new Blank());
		
		// kein Stern, nichts getippt
		b.setRealState(new Blank());
		b.setVisibleState(new Blank());

		// kein Stern, getippt dass dort keiner ist
		c.setRealState(new Blank());
		c.setVisibleState(new Ausschluss());
		
		// Pfeil, Pfeil muss auch angezeigt werden
		d.setRealState(new Pfeil(PfeilrichtungEnumeration.NORDWEST));
		d.setVisibleState(new Pfeil(PfeilrichtungEnumeration.NORDWEST));
		
		spielfeld.setSpielstein(0, 0, a);
		spielfeld.setSpielstein(0, 1, b);
		spielfeld.setSpielstein(1, 0, c);
		spielfeld.setSpielstein(1, 1, d);
		
		assertFalse(testSpiel.hasErrors());
		
	}

	/**
	 * Prüft ob Fehler vorliegen. Hier wurde ein Stern getippt, wo keiner ist.
	 */
	@Test
	public void stern_zuviel_erkennen() {
		
		// Stern da, Stern getippt
		a.setRealState(new Stern());
		a.setVisibleState(new Stern());
		
		// kein Stern, Stern getippt (hier tritt der Fehler auf)
		b.setRealState(new Blank());
		b.setVisibleState(new Stern());

		// kein Stern, getippt dass dort keiner ist
		c.setRealState(new Blank());
		c.setVisibleState(new Ausschluss());
		
		// Pfeil, Pfeil muss auch angezeigt werden
		d.setRealState(new Pfeil(PfeilrichtungEnumeration.NORDWEST));
		d.setVisibleState(new Pfeil(PfeilrichtungEnumeration.NORDWEST));
		
		spielfeld.setSpielstein(0, 0, a);
		spielfeld.setSpielstein(0, 1, b);
		spielfeld.setSpielstein(1, 0, c);
		spielfeld.setSpielstein(1, 1, d);
		
		assertTrue(testSpiel.hasErrors());
		
	}


}
