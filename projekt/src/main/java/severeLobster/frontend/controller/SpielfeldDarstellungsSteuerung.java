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
import severeLobster.frontend.view.SpielfeldDarstellung;

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
public class SpielfeldDarstellungsSteuerung {

    private final SpielfeldDarstellung spielfeldDarstellung;
    private final SternenSpielApplicationBackend backend;

    public SpielfeldDarstellungsSteuerung(SpielfeldDarstellung spielfeldView,
            SternenSpielApplicationBackend applicationBackend) {
        this.spielfeldDarstellung = spielfeldView;
        this.backend = applicationBackend;
        spielfeldView.setSpielfeldDarstellungsSteuerung(this);
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
            /**
             * Wenn sich der Spielmodus geaendert hat, koennte sich fast jeder
             * Stein geaendert haben -> komplett neuzeichnen
             */
            spielfeldDarstellung.setAngezeigtesSpielfeld(spiel.getSpielfeld());
            // TODO Optimieren: da Spielfeldgroesse gleich bleibt, braucht net
            // alles neu gesetzt werden.
        }

        @Override
        public void spielsteinChanged(
                SternenSpielApplicationBackend sternenSpielApplicationBackend,
                Spiel spiel, ISpielfeldReadOnly spielfeld, int x, int y,
                Spielstein newStein) {
            /**
             * Setze nur einen Spielstein neu, da die restlichen Spielsteine
             * gleich geblieben sind
             */
            spielfeldDarstellung.setAngezeigterSpielstein(x, y, newStein);
            /** Sternanzahl Anzeigen anpassen */
            final int sternAnzahlInSpalte = spielfeld.countSterneSpalte(x);
            spielfeldDarstellung.setSternAnzahlInSpalte(x, sternAnzahlInSpalte);
            final int sternAnzahlInZeile = spielfeld.countSterneZeile(y);
            spielfeldDarstellung.setSternAnzahlInZeile(y, sternAnzahlInZeile);

        }

        @Override
        public void spielfeldChanged(
                SternenSpielApplicationBackend sternenSpielApplicationBackend,
                Spiel spiel, ISpielfeldReadOnly newSpielfeld) {
            /** Wenn Spielfeld sich gaendert hat, alles neuzeichnen */
            spielfeldDarstellung.setAngezeigtesSpielfeld(spiel.getSpielfeld());
        }

        @Override
        public void spielChanged(
                SternenSpielApplicationBackend sternenSpielApplicationBackend,
                Spiel spiel) {
            /**
             * Wenn Spiel sich gaendert hat, hat sich auch das Spielfeld
             * geaendert -> alles neuzeichnen
             */
            spielfeldDarstellung.setAngezeigtesSpielfeld(spiel.getSpielfeld());
        }

    }

}
