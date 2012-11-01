package severeLobster.frontend.controller;

import infrastructure.constants.enums.SpielmodusEnumeration;
import severeLobster.backend.spiel.ISpielfeldReadOnly;
import severeLobster.backend.spiel.ISternenSpielApplicationBackendListener;
import severeLobster.backend.spiel.Spiel;
import severeLobster.backend.spiel.Spielstein;
import severeLobster.backend.spiel.SternenSpielApplicationBackend;

public class SpielmodusViewController {

    private final SternenSpielApplicationBackend backend;
    private final ISpielmodusView view;

    public SpielmodusViewController(final ISpielmodusView spielmodusView,
            final SternenSpielApplicationBackend backend) {
        this.backend = backend;
        this.view = spielmodusView;
        view.setSpielmodusController(this);
        backend.addApplicationBackendListener(new InnerBackendListener());
    }

    public void setSpielmodus(final SpielmodusEnumeration newSpielmodus) {
        this.backend.getSpiel().setSpielmodus(newSpielmodus);
    }

    private class InnerBackendListener implements
            ISternenSpielApplicationBackendListener {

        @Override
        public void spielmodusChanged(
                SternenSpielApplicationBackend sternenSpielApplicationBackend,
                Spiel spiel, SpielmodusEnumeration newSpielmodus) {
            view.setDisplayedSpielmodus(newSpielmodus);
        }

        @Override
        public void spielsteinChanged(
                SternenSpielApplicationBackend sternenSpielApplicationBackend,
                Spiel spiel, ISpielfeldReadOnly spielfeld, int x, int y,
                Spielstein newStein) {

        }

        @Override
        public void spielfeldChanged(
                SternenSpielApplicationBackend sternenSpielApplicationBackend,
                Spiel spiel, ISpielfeldReadOnly newSpielfeld) {

        }

        @Override
        public void spielChanged(
                SternenSpielApplicationBackend sternenSpielApplicationBackend,
                Spiel spiel) {

        }

    }

    public SpielmodusEnumeration getSelectionSpielmodus() {
        return backend.getSpiel().getSpielmodus();
    }
}
