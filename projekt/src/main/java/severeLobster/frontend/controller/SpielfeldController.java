package severeLobster.frontend.controller;

import java.util.ArrayList;
import java.util.Stack;

import infrastructure.constants.enums.SpielmodusEnumeration;

import severeLobster.backend.command.Aktion;
import severeLobster.backend.command.PrimaerAktion;
import severeLobster.backend.spiel.ISternenSpielApplicationBackendListener;
import severeLobster.backend.spiel.Spiel;
import severeLobster.backend.spiel.Spielfeld;
import severeLobster.backend.spiel.Spielstein;
import severeLobster.backend.spiel.SternenSpielApplicationBackend;
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
public class SpielfeldController {

    private final SpielfeldView spielfeldView;
    private final SternenSpielApplicationBackend backend;
    private Spielfeld currentSpielfeld;
    private Stack<Aktion> spielZuege;
    private Stack<Integer> trackingPunkte;
    private int letzterFehlerfreierSpielzug;

    public SpielfeldController(SpielfeldView spielfeldView,
            SternenSpielApplicationBackend applicationBackend) {
        this.spielfeldView = spielfeldView;
        this.backend = applicationBackend;
        this.currentSpielfeld = backend.getSpiel().getSpielfeld();
        spielfeldView.setSpielfeldController(this);
        backend.addApplicationBackendListener(new InnerSternenSpielBackendListener());
        spielZuege = new Stack<Aktion>();
        trackingPunkte = new Stack<Integer>();
        letzterFehlerfreierSpielzug = 0;
    }

    public Spielfeld getSpielfeld() {
        return this.currentSpielfeld;
    }

    public SpielmodusEnumeration getSpielmodus() {
        return backend.getSpiel().getSpielmodus();
    }

    public void setSpielstein(Spielstein spielstein, int x, int y) {
        PrimaerAktion spielZug = new PrimaerAktion(backend.getSpiel());
        if(!spielZug.execute(x, y, spielstein)) {
            letzterFehlerfreierSpielzug = spielZuege.size();
        }
        spielZuege.push(spielZug);
    }
    
    public void setzeTrackingPunkt() {
        trackingPunkte.push(spielZuege.size());
    }
    
    private void nimmSpielzugZurueck() {
        spielZuege.pop().undo();
    }
    
    public void zurueckZumFehler() {
        while(spielZuege.size() > letzterFehlerfreierSpielzug) {
            nimmSpielzugZurueck();
        }
    }
    
    public void zurueckZumLetztenTrackingPunkt() {
        int trackingPunkt = trackingPunkte.pop();
        while(spielZuege.size() > trackingPunkt) {
            nimmSpielzugZurueck();
        }
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
            // TEMP: Zeichne immer alles neu, damit Sternanzahlen oben und links
            // immer aktuell sind.
            if (currentSpielfeld == spielfeld) {
                // spielfeldView.setDisplayedSpielstein(x, y, newStein);
            } else {
                currentSpielfeld = spielfeld;

            }
            changeDisplayedSpielfeldTo(currentSpielfeld);

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
