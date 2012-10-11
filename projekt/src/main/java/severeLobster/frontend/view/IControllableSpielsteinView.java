package severeLobster.frontend.view;

import severeLobster.backend.spiel.SpielsteinState;
import severeLobster.frontend.controller.SpielsteinController;

/**
 * Eine veränderbare Darstellung eines Spielsteins.
 * 
 * @author Lutz Kleiber
 * 
 */
public interface IControllableSpielsteinView {

	/**
	 * Verändert den aktuell dargestellten SpielsteinState dieser View.
	 * 
	 * @param newDisplayedState
	 */
	public void setDisplayedState(SpielsteinState newDisplayedState);

	/**
	 * Wird nur vom Controller selbst aufgerufen. Überschreibt den eventuell
	 * zuvor gesetzten Controller.
	 * 
	 * @param spielsteinController
	 */
	public void setSpielsteinController(
			SpielsteinController spielsteinController);

}
