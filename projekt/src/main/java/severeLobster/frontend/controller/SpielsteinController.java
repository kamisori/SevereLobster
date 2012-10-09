package severeLobster.frontend.controller;

import severeLobster.backend.spiel.ISpielsteinListener;
import severeLobster.backend.spiel.Spielstein;
import severeLobster.backend.spiel.SpielsteinState;
import severeLobster.frontend.view.IControllableSpielsteinView;

/**
 * Der Vermittler zwischen dem Spielstein und der darstellenden Komponente. Alle
 * im View angestoßenen Aktionen werden an den entsprechenden
 * SpielsteinController weitergeleitet. Umgekehrt leitet der
 * SpielsteinController Änderungen beim Spielstein an den View weiter.
 * 
 * @author Lutz Kleiber
 * 
 */
public class SpielsteinController {

	private final IControllableSpielsteinView spielsteinView;
	private final Spielstein spielsteinModel;

	public SpielsteinController(
			final IControllableSpielsteinView spielsteinView,
			final Spielstein spielstein) {
		this.spielsteinView = spielsteinView;
		this.spielsteinModel = spielstein;
		spielsteinView.setSpielsteinController(this);
		spielstein.addSpielsteinListener(new InnerSpielsteinListener());
	}

	/**
	 * Wird vom View aufgerufen. Verändert den wert von visibleState des
	 * zugrundeliegenden Spielsteins.
	 * 
	 * @param spielsteinState
	 */
	public void setState(final SpielsteinState spielsteinState) {
		spielsteinModel.setVisibleState(spielsteinState);
	}

	/**
	 * Wird vom View aufgerufen. Holt den aktuellen wert von visibleState vom
	 * Spielstein.
	 * 
	 * @param spielsteinState
	 */
	public SpielsteinState getState() {
		return spielsteinModel.getVisibleState();
	}

	private class InnerSpielsteinListener implements ISpielsteinListener {

		public void spielsteinStateChanged(final Spielstein spielstein,
				final SpielsteinState newState) {

			/**
			 * Überprüfe ob der benachrichtigende Spielstein auch der ist, den
			 * man gerade beobachtet.
			 */
			if (spielstein == spielsteinModel) {
				/**
				 * Leite Änderung an view weiter.
				 */
				spielsteinView.setDisplayedState(newState);
			}

		}

	}

}
