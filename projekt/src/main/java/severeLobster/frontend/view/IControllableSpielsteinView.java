package severeLobster.frontend.view;

import severeLobster.backend.spiel.SpielsteinState;
import severeLobster.frontend.controller.SpielsteinController;

/**
 * Eine ver�nderbare Darstellung eines Spielsteins.
 * 
 * @author Lutz Kleiber
 * 
 */
public interface IControllableSpielsteinView {

	/**
	 * Ver�ndert den aktuell dargestellten SpielsteinState dieser View.
	 * 
	 * @param newDisplayedState
	 */
	public void setDisplayedState(SpielsteinState newDisplayedState);

	/**
	 * Wird nur vom Controller selbst aufgerufen. �berschreibt den eventuell
	 * zuvor gesetzten Controller.
	 * 
	 * @param spielsteinController
	 */
	public void setSpielsteinController(
			SpielsteinController spielsteinController);

}
