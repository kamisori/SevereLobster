package severeLobster.frontend.controller;

import infrastructure.constants.enums.SpielmodusEnumeration;
import severeLobster.backend.spiel.ISternenSpielApplicationBackendListener;
import severeLobster.backend.spiel.Spiel;
import severeLobster.backend.spiel.Spielfeld;
import severeLobster.backend.spiel.Spielstein;
import severeLobster.backend.spiel.SternenSpielApplicationBackend;
import severeLobster.frontend.view.SpielfeldView;

public class SpielfeldController {

    private final SpielfeldView spielfeldView;
    private final SternenSpielApplicationBackend backend;
    private Spielfeld currentSpielfeld;

    public SpielfeldController(SpielfeldView spielfeldView,
            SternenSpielApplicationBackend applicationBackend) {
        this.spielfeldView = spielfeldView;
        this.backend = applicationBackend;
        this.currentSpielfeld = backend.getSpiel().getSpielfeld();
        spielfeldView.setSpielfeldController(this);
        backend.addApplicationBackendListener(new InnerSternenSpielBackendListener());
    }

    public Spielfeld getSpielfeld() {
        return this.currentSpielfeld;
    }

    public SpielmodusEnumeration getSpielmodus() {
        return backend.getSpiel().getSpielmodus();
    }

    public void setSpielstein(Spielstein spielstein, int x, int y) {
        this.currentSpielfeld.setSpielstein(x, y, spielstein);
    }

    private void changeDisplayedSpielfeldTo(Spielfeld newSpielfeld) {
        if (null == newSpielfeld) {
            throw new NullPointerException("Spielfeld ist null");
        }

        final int laenge = newSpielfeld.getHoehe();
        final int breite = newSpielfeld.getBreite();

        spielfeldView.setSpielfeldAbmessungen(breite, laenge);

        Spielstein spielstein = null;

        // Erstelle oberen Balken fuer Anzahl der Pfeile in den Spalten:
        for (int breiteIndex = 0; breiteIndex < breite; breiteIndex++) {

            int anzahlSterne = newSpielfeld.countSterneSpalte(breiteIndex);
            spielfeldView.getSpaltenPfeilAnzahlView(breiteIndex).setText(
                    String.valueOf(anzahlSterne));
        }
        /**
         * Durchlaufe das Spielfeld zeilenweise und fuege in der Reihenfolge die
         * Spielsteinansichten zum Panel hinzu.
         */
        for (int laengeIndex = 0; laengeIndex < laenge; laengeIndex++) {
            for (int breiteIndex = 0; breiteIndex < breite; breiteIndex++) {
                // Am Anfang jeder Zeile einen PfeilAnzahlView einstellen:
                if (0 == breiteIndex) {
                    int anzahlSterne = newSpielfeld
                            .countSterneZeile(laengeIndex);
                    spielfeldView.getReihenPfeilAnzahlView(laengeIndex)
                            .setText(String.valueOf(anzahlSterne));
                }
                /** Hole naechsten Spielstein */
                spielstein = newSpielfeld.getSpielstein(breiteIndex,
                        laengeIndex);
                /** Setze neue Ansichtskomponente fuer diesen Spielstein */
                spielfeldView.setDisplayedSpielstein(breiteIndex, laengeIndex,
                        spielstein);

            }
        }
    }

    private class InnerSternenSpielBackendListener implements
            ISternenSpielApplicationBackendListener {

        @Override
        public void spielmodusChanged(
                SternenSpielApplicationBackend sternenSpielApplicationBackend,
                Spiel spiel, SpielmodusEnumeration newSpielmodus) {
            Spielfeld newSpielfeld = spiel.getSpielfeld();
            currentSpielfeld = newSpielfeld;
            changeDisplayedSpielfeldTo(currentSpielfeld);
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
                changeDisplayedSpielfeldTo(currentSpielfeld);
            }

        }

        @Override
        public void spielfeldChanged(
                SternenSpielApplicationBackend sternenSpielApplicationBackend,
                Spiel spiel, Spielfeld newSpielfeld) {
            currentSpielfeld = newSpielfeld;
            changeDisplayedSpielfeldTo(currentSpielfeld);

        }

        @Override
        public void spielChanged(
                SternenSpielApplicationBackend sternenSpielApplicationBackend,
                Spiel spiel) {
            if (null != spiel) {
                final Spielfeld newSpielfeld = spiel.getSpielfeld();
                currentSpielfeld = newSpielfeld;
                changeDisplayedSpielfeldTo(currentSpielfeld);
            }

        }

    }

}
