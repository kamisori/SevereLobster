package severeLobster.frontend.view;

import java.awt.GridLayout;

import javax.swing.JPanel;

import severeLobster.backend.spiel.Spielfeld;
import severeLobster.backend.spiel.Spielstein;
import severeLobster.frontend.controller.SpielsteinController;

/**
 * TODO Kommentar
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
		this.setLayout(new GridLayout(laenge, breite));
		Spielstein spielstein = null;
		SpielsteinViewImpl view = null;

		for (int laengeIndex = 0; laengeIndex < laenge; laengeIndex++) {
			for (int breiteIndex = 0; breiteIndex < breite; breiteIndex++) {
				spielstein = spielfeld.getSpielstein(breiteIndex, laengeIndex);
				view = new SpielsteinViewImpl();
				new SpielsteinController(view, spielstein);
				this.add(view);
			}
		}
	}
}
