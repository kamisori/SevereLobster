package severeLobster.frontend.controller;

import infrastructure.constants.enums.SpielmodusEnumeration;
import severeLobster.backend.command.Aktion;
import severeLobster.backend.command.PrimaerAktion;
import severeLobster.backend.spiel.ISternenSpielApplicationBackendListener;
import severeLobster.backend.spiel.Spiel;
import severeLobster.backend.spiel.Spielfeld;
import severeLobster.backend.spiel.Spielstein;
import severeLobster.backend.spiel.SternenSpielApplicationBackend;
import severeLobster.frontend.view.SpielfeldView;

import java.util.List;
import java.util.Stack;

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
    private Stack<Aktion> spielZuege;
    private Stack<Integer> trackingPunkte;
    private int letzterFehlerfreierSpielzug;

    public SpielfeldViewController(SpielfeldView spielfeldView,
            SternenSpielApplicationBackend applicationBackend) {
        this.spielfeldView = spielfeldView;
        this.backend = applicationBackend;
        spielfeldView.setSpielfeldController(this);
        backend.addApplicationBackendListener(new InnerSternenSpielBackendListener());
        spielZuege = new Stack<Aktion>();
        trackingPunkte = new Stack<Integer>();
        letzterFehlerfreierSpielzug = 0;
    }

    public SpielmodusEnumeration getSpielmodus() {
        return backend.getSpiel().getSpielmodus();
    }

    public void setSpielstein(Spielstein spielstein, int x, int y) {
        PrimaerAktion spielZug = new PrimaerAktion(backend.getSpiel());
        spielZuege.push(spielZug);
        if (!spielZug.execute(x, y, spielstein)) {
            letzterFehlerfreierSpielzug = spielZuege.size();
        }
    }

    public void setzeTrackingPunkt() {
        trackingPunkte.push(spielZuege.size());
    }

    private void nimmSpielzugZurueck() {
        spielZuege.pop().undo();
    }

    public void zurueckZumLetztenFehlerfreienSpielzug() {
        while (spielZuege.size() > letzterFehlerfreierSpielzug) {
            nimmSpielzugZurueck();
        }
    }

    public void zurueckZumLetztenTrackingPunkt() {
        int trackingPunkt = trackingPunkte.pop();
        while (spielZuege.size() > trackingPunkt) {
            nimmSpielzugZurueck();
        }
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
                    int anzahlSterne = backend.getCountSterneZeile(laengeIndex);

                    spielfeldView.getReihenPfeilAnzahlView(laengeIndex)
                            .setText(String.valueOf(anzahlSterne));
                }
                /** Hole naechsten Spielstein */
                spielstein = backend.getSpielstein(breiteIndex, laengeIndex);
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
            refreshDisplayedSpielfeldCompletely();
        }

        @Override
        public void spielsteinChanged(
                SternenSpielApplicationBackend sternenSpielApplicationBackend,
                Spiel spiel, Spielfeld spielfeld, int x, int y,
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
                Spiel spiel, Spielfeld newSpielfeld) {
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