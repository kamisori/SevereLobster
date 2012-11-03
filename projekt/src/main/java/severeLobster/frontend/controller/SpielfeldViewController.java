package severeLobster.frontend.controller;

import infrastructure.constants.enums.SpielmodusEnumeration;

import java.awt.event.MouseEvent;
import java.util.List;

import severeLobster.backend.spiel.Ausschluss;
import severeLobster.backend.spiel.ISpielfeldReadOnly;
import severeLobster.backend.spiel.ISternenSpielApplicationBackendListener;
import severeLobster.backend.spiel.KeinStein;
import severeLobster.backend.spiel.Spiel;
import severeLobster.backend.spiel.Spielstein;
import severeLobster.backend.spiel.Stern;
import severeLobster.backend.spiel.SternenSpielApplicationBackend;
import severeLobster.frontend.view.PopupMenuForSpielsteinChoice;
import severeLobster.frontend.view.SpielfeldView;

/**
 * Steuerung fuer SpielfeldView. Verbindet SternenSpielApplicationBackend und
 * SpielfeldView. Reagiert auf Veraenderungen von
 * SternenspielApplicationBackend, Spiel und Spielfeld und aktualisiert
 * entsprechend die Darstellung. Rohversion: Zeichnet bei jeder Aenderung immer
 * alles neu, damit Sternanzahlen oben und links bei Spiel- und Editmodus immer
 * aktuell sind. Testbar mit SpielfeldViewTestOhneJUnit.
 * 
 * @author Lutz Kleiber
 * 
 */
public class SpielfeldViewController {

    private final SpielfeldView spielfeldView;
    private final SternenSpielApplicationBackend backend;

    public SpielfeldViewController(SpielfeldView spielfeldView,
            SternenSpielApplicationBackend applicationBackend) {
        this.spielfeldView = spielfeldView;
        this.backend = applicationBackend;
        spielfeldView.setSpielfeldController(this);
        backend.addApplicationBackendListener(new InnerSternenSpielBackendListener());

    }

    public SpielmodusEnumeration getSpielmodus() {
        return backend.getSpiel().getSpielmodus();
    }

    public void setSpielstein(Spielstein spielstein, int x, int y) {
        backend.setSpielstein(x, y, spielstein);
    }

    public Spielstein getSpielstein(int x, int y) {
        return backend.getSpielstein(x, y);
    }

    public List<? extends Spielstein> listAvailableStates(int x, int y) {
        return backend.listAvailableStates(x, y);
    }

    private void refreshDisplayedSpielfeldCompletely() {

        final int laenge = backend.getSpielfeldHoehe();
        final int breite = backend.getSpielfeldBreite();

        spielfeldView.setSpielfeldAbmessungen(breite, laenge);

        Spielstein spielstein = null;

        // Erstelle oberen Balken fuer Anzahl der Pfeile in den Spalten:
        for (int breiteIndex = 0; breiteIndex < breite; breiteIndex++) {

            int anzahlSterne = backend.getCountSterneSpale(breiteIndex);

            spielfeldView.setSpaltenPfeilAnzahl(breiteIndex, anzahlSterne);
        }
        /**
         * Durchlaufe das Spielfeld zeilenweise und fuege in der Reihenfolge die
         * Spielsteinansichten zum Panel hinzu.
         */
        for (int laengeIndex = 0; laengeIndex < laenge; laengeIndex++) {
            for (int breiteIndex = 0; breiteIndex < breite; breiteIndex++) {
                // Am Anfang jeder Zeile einen PfeilAnzahlView einstellen:
                if (0 == breiteIndex) {
                    int anzahlSterne = backend.getCountSterneZeile(laengeIndex);

                    spielfeldView.setReihenPfeilAnzahl(laengeIndex,
                            anzahlSterne);
                }
                /** Hole naechsten Spielstein */
                spielstein = backend.getSpielstein(breiteIndex, laengeIndex);
                /** Setze neue Ansichtskomponente fuer diesen Spielstein */
                spielfeldView.setDisplayedSpielstein(breiteIndex, laengeIndex,
                        spielstein);

            }
        }
    }

    public void spielSteinClick(int x, int y, MouseEvent mouseEvent) {
        if (isSpielModus()) {
            if (isLeftClick(mouseEvent)) {
                guessStern(x, y);
                return;
            }
            if (isRightClick(mouseEvent)) {
                guessAusschluss(x, y);
                return;
            }
        }
        /** Editiermodus: */
        if (!isSpielModus()) {
            if (isLeftClick(mouseEvent)) {
                resetSpielsteinState(x, y);
                return;
            }
            if (isRightClick(mouseEvent)) {
                new PopupMenuForSpielsteinChoice(this,
                        listAvailableStates(x, y), x, y).show(
                        mouseEvent.getComponent(), mouseEvent.getX(),
                        mouseEvent.getY());

                return;
            }
        }
    }

    private boolean isLeftClick(final MouseEvent e) {
        return e.getButton() == MouseEvent.BUTTON1;

    }

    private boolean isRightClick(final MouseEvent e) {
        return e.getButton() == MouseEvent.BUTTON3;

    }

    private void resetSpielsteinState(final int x, final int y) {
        final Spielstein nullState = KeinStein.getInstance();
        setSpielstein(nullState, x, y);
    }

    private boolean isSpielModus() {
        return getSpielmodus().equals(SpielmodusEnumeration.SPIELEN);
    }

    private void guessStern(final int x, final int y) {
        if (getSpielstein(x, y).equals(Stern.getInstance())) {
            setSpielstein(KeinStein.getInstance(), x, y);
        } else {
            setSpielstein(Stern.getInstance(), x, y);
        }

    }

    private void guessAusschluss(final int x, final int y) {
        if (getSpielstein(x, y).equals(Ausschluss.getInstance())) {
            setSpielstein(KeinStein.getInstance(), x, y);
        } else {
            setSpielstein(Ausschluss.getInstance(), x, y);
        }
    }

    private class InnerSternenSpielBackendListener implements
            ISternenSpielApplicationBackendListener {

        @Override
        public void spielmodusChanged(
                SternenSpielApplicationBackend sternenSpielApplicationBackend,
                Spiel spiel, SpielmodusEnumeration newSpielmodus) {
            refreshDisplayedSpielfeldCompletely();
        }

        @Override
        public void spielsteinChanged(
                SternenSpielApplicationBackend sternenSpielApplicationBackend,
                Spiel spiel, ISpielfeldReadOnly spielfeld, int x, int y,
                Spielstein newStein) {
            // // TEMP: Zeichne immer alles neu, damit Sternanzahlen oben und
            // links
            // // immer aktuell sind.
            // if (currentSpielfeld == spielfeld) {
            // // spielfeldView.setDisplayedSpielstein(x, y, newStein);
            // } else {
            // currentSpielfeld = spielfeld;
            //
            // }
            refreshDisplayedSpielfeldCompletely();

        }

        @Override
        public void spielfeldChanged(
                SternenSpielApplicationBackend sternenSpielApplicationBackend,
                Spiel spiel, ISpielfeldReadOnly newSpielfeld) {
            refreshDisplayedSpielfeldCompletely();
        }

        @Override
        public void spielChanged(
                SternenSpielApplicationBackend sternenSpielApplicationBackend,
                Spiel spiel) {
            refreshDisplayedSpielfeldCompletely();
        }

    }

}
