package severeLobster.frontend.controller;

import infrastructure.constants.enums.SpielmodusEnumeration;

import java.awt.event.MouseEvent;
import java.util.List;

import severeLobster.backend.spiel.Ausschluss;
import severeLobster.backend.spiel.ISpielsteinListener;
import severeLobster.backend.spiel.NullState;
import severeLobster.backend.spiel.Spielstein;
import severeLobster.backend.spiel.SpielsteinState;
import severeLobster.backend.spiel.Stern;
import severeLobster.frontend.view.IControllableSpielsteinView;
import severeLobster.frontend.view.PopupMenuForSpielsteinStateManipulation;

/**
 * Der Vermittler zwischen dem Spielstein und der darstellenden Komponente. Alle
 * im View angestoï¿½enen Aktionen werden an den entsprechenden
 * SpielsteinController weitergeleitet. Umgekehrt leitet der
 * SpielsteinController ï¿½nderungen beim Spielstein an den View weiter.
 * 
 * @author Lutz Kleiber
 * 
 */
public class SpielsteinController {

	/**
	 * TODO ÄNDERN - Nur zum Testen: Muss später vom Spiel geholt werdens
	 */
	public SpielmodusEnumeration spielModus = SpielmodusEnumeration.EDITIEREN;

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
	 * Wird vom View aufgerufen. Verï¿½ndert den wert von visibleState des
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
	 * @return spielsteinState
	 */
	public SpielsteinState getState() {
		return spielsteinModel.getVisibleState();
	}

	private class InnerSpielsteinListener implements ISpielsteinListener {

		public void spielsteinStateChanged(final Spielstein spielstein,
				final SpielsteinState newState) {

			/**
			 * ï¿½berprï¿½fe ob der benachrichtigende Spielstein auch der ist,
			 * den man gerade beobachtet.
			 */
			if (spielstein == spielsteinModel) {
				/**
				 * Leite ï¿½nderung an view weiter.
				 */
				spielsteinView.setDisplayedState(newState);
			}

		}

	}

	public void clickAction(MouseEvent mouseEvent) {

		if (isSpielModus()) {
			if (isLeftClick(mouseEvent)) {
				guessStern();
				return;
			}
			if (isRightClick(mouseEvent)) {
				guessAusschluss();
				return;
			}
		}
		/** Editiermodus: */
		if (!isSpielModus()) {
			if (isLeftClick(mouseEvent)) {
				resetSpielsteinState();
				return;
			}
			if (isRightClick(mouseEvent)) {
				new PopupMenuForSpielsteinStateManipulation(this,
						spielsteinModel.listAvailableStates()).show(
						mouseEvent.getComponent(), mouseEvent.getX(),
						mouseEvent.getY());
				return;
			}
		}

	}

	private void guessStern() {
		final SpielsteinState sternState = Stern.getInstance();
		if (isAllowedState(sternState)) {
			spielsteinModel.setVisibleState(sternState);
		}
	}

	private void guessAusschluss() {
		final SpielsteinState ausschlussState = Ausschluss.getInstance();

		if (isAllowedState(ausschlussState)) {
			spielsteinModel.setVisibleState(ausschlussState);
		}
	}

	private void resetSpielsteinState() {
		final SpielsteinState nullState = NullState.getInstance();
		if (isAllowedState(nullState)) {
			spielsteinModel.setVisibleState(nullState);
		}
	}

	private boolean isAllowedState(final SpielsteinState state) {
		final List<? extends SpielsteinState> allowedStates = spielsteinModel
				.listAvailableStates();
		return allowedStates.contains(state);
	}

	/**
	 * TODO Ändern: Vom Spiel holen
	 * 
	 * @return
	 */
	private SpielmodusEnumeration getSpielmodus() {
		return this.spielModus;
	}

	private boolean isSpielModus() {
		return getSpielmodus().equals(SpielmodusEnumeration.SPIELEN);
	}

	private boolean isLeftClick(final MouseEvent e) {
		return e.getButton() == MouseEvent.BUTTON1;

	}

	private boolean isRightClick(final MouseEvent e) {
		return e.getButton() == MouseEvent.BUTTON3;

	}
}
