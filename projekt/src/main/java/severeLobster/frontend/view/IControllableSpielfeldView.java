package severeLobster.frontend.view;

import severeLobster.backend.spiel.Spielfeld;
import severeLobster.backend.spiel.Spielstein;
import severeLobster.frontend.controller.SpielfeldController;

public interface IControllableSpielfeldView {

    public void setDisplayedSpielfeld(Spielfeld spielfeld);

    public void setDisplayedSpielstein(int x, int y, Spielstein spielstein);

    public void setSpielfeldController(SpielfeldController spielfeldController);
}
