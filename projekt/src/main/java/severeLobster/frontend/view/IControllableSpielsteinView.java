package severeLobster.frontend.view;

import severeLobster.backend.spiel.Spielstein;
import severeLobster.frontend.controller.SpielsteinController;

/**
 * Eine veraenderbare Darstellung eines Spielsteins.
 * 
 * @author Lutz Kleiber
 * 
 */
public interface IControllableSpielsteinView {

    /**
     * Veraendert den aktuell dargestellten SpielsteinState dieser View.
     * 
     * @param newDisplayedState SpielsteinState
     */
    public void setDisplayedState(Spielstein newDisplayedState);

    /**
     * Wird nur vom Controller selbst aufgerufen. ueberschreibt den eventuell
     * zuvor gesetzten Controller.
     * 
     * @param spielsteinController SpielsteinController
     */
    public void setSpielsteinController(
            SpielsteinController spielsteinController);

}
