package severeLobster.frontend.view;

import java.awt.GridLayout;

import javax.swing.JPanel;

import severeLobster.backend.spiel.Spielfeld;
import severeLobster.backend.spiel.Spielstein;
import severeLobster.frontend.controller.SpielsteinController;

/**
 * Darstellung eines Spielfeldes mit den enthaltenen Spielsteinen.
 * 
 * @author LKleiber
 * 
 */
public class SpielfeldView extends JPanel {

	public SpielfeldView(final Spielfeld spielfeld) {
		if (null == spielfeld) {
			throw new NullPointerException("Spielfeld ist null");
		}
		final int laenge = spielfeld.getLaenge();
		final int breite = spielfeld.getBreite();
		/**
		 * Gridlayout bietet die tabellenartige Darstellung der zum Panel
		 * hinzugef�gten Komponenten.
		 */
		this.setLayout(new GridLayout(laenge, breite));
		Spielstein spielstein = null;
		SpielsteinViewImpl view = null;

		/**
		 * Durchlaufe das Spielfeld zeilenweise und f�ge in der Reihenfolge die
		 * Spielsteinansichten zum Panel hinzu.
		 */
		for (int laengeIndex = 0; laengeIndex < laenge; laengeIndex++) {
			for (int breiteIndex = 0; breiteIndex < breite; breiteIndex++) {
				/** Hole n�chsten Spielstein */
				spielstein = spielfeld.getSpielstein(breiteIndex, laengeIndex);
				/** Erstelle neue Ansichtskomponente f�r diesen Spielstein */
				view = new SpielsteinViewImpl();
				/** Verkn�pfe Ansicht mit Spielstein: */
				new SpielsteinController(view, spielstein);
				/** F�ge Ansicht zum Panel hinzu */
				this.add(view);
			}
		}
	}
}
