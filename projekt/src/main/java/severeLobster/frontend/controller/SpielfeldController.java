package severeLobster.frontend.controller;

import infrastructure.constants.enums.SpielmodusEnumeration;
import severeLobster.backend.spiel.ISternenSpielApplicationBackendListener;
import severeLobster.backend.spiel.Spiel;
import severeLobster.backend.spiel.Spielfeld;
import severeLobster.backend.spiel.Spielstein;
import severeLobster.backend.spiel.SternenSpielApplicationBackend;
import severeLobster.frontend.view.IControllableSpielfeldView;

public class SpielfeldController {

    private final IControllableSpielfeldView spielfeldView;
    private final SternenSpielApplicationBackend backend;
    private Spielfeld currentSpielfeld;

    public SpielfeldController(IControllableSpielfeldView spielfeldView,
            SternenSpielApplicationBackend applicationBackend) {
        this.spielfeldView = spielfeldView;
        this.backend = applicationBackend;
        this.currentSpielfeld = backend.getSpiel().getSpielfeld();
        spielfeldView.setSpielfeldController(this);
        backend.addApplicationBackendListener(new InnerSternenSpielBackendListener());
    }

    private class InnerSternenSpielBackendListener implements
            ISternenSpielApplicationBackendListener {

        @Override
        public void spielmodusChanged(
                SternenSpielApplicationBackend sternenSpielApplicationBackend,
                Spiel spiel, SpielmodusEnumeration newSpielmodus) {
            Spielfeld newSpielfeld = spiel.getSpielfeld();
            currentSpielfeld = newSpielfeld;
            spielfeldView.setDisplayedSpielfeld(newSpielfeld);
        }

        @Override
        public void spielsteinChanged(
                SternenSpielApplicationBackend sternenSpielApplicationBackend,
                Spiel spiel, Spielfeld spielfeld, int x, int y,
                Spielstein newStein) {
            if (currentSpielfeld == spielfeld) {
                spielfeldView.setDisplayedSpielstein(x, y, newStein);
            } else {
                currentSpielfeld = spielfeld;
                spielfeldView.setDisplayedSpielfeld(spielfeld);
            }

        }

        @Override
        public void spielfeldChanged(
                SternenSpielApplicationBackend sternenSpielApplicationBackend,
                Spiel spiel, Spielfeld newSpielfeld) {
            currentSpielfeld = newSpielfeld;
            spielfeldView.setDisplayedSpielfeld(newSpielfeld);

        }

        @Override
        public void spielChanged(
                SternenSpielApplicationBackend sternenSpielApplicationBackend,
                Spiel spiel) {
            if (null != spiel) {
                final Spielfeld newSpielfeld = spiel.getSpielfeld();
                currentSpielfeld = newSpielfeld;
                spielfeldView.setDisplayedSpielfeld(newSpielfeld);
            }

        }

    }

    public Spielfeld getSpielfeld() {
        return this.currentSpielfeld;
    }

}
