package severeLobster.frontend.controller;

import infrastructure.components.Koordinaten;
import infrastructure.constants.enums.SpielmodusEnumeration;
import severeLobster.backend.spiel.Ausschluss;
import severeLobster.backend.spiel.ISpielfeldReadOnly;
import severeLobster.backend.spiel.ISternenSpielApplicationBackendListener;
import severeLobster.backend.spiel.KeinStein;
import severeLobster.backend.spiel.Pfeil;
import severeLobster.backend.spiel.Spiel;
import severeLobster.backend.spiel.Spielstein;
import severeLobster.backend.spiel.Stern;
import severeLobster.backend.spiel.SternenSpielApplicationBackend;
import severeLobster.frontend.view.PopupMenuForSpielsteinChoice;
import severeLobster.frontend.view.SpielfeldDarstellung;

import java.awt.event.MouseEvent;
import java.util.List;

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

    public static final SpielfeldDarstellungsSteuerung NULL_OBJECT_INSTANCE = new SpielfeldDarstellungsSteuerung() {
        @Override
        public void setSpielstein(Spielstein spielstein, int x, int y) {
            /** Macht nichts */
        }

        public Spielstein spielSteinClick(int x, int y, MouseEvent mouseEvent) {
            /** Macht nichts */
            return null;
        }
    };

    private final SpielfeldDarstellung spielfeldDarstellung;
    private final SternenSpielApplicationBackend backend;

    /**
     * Konstruktor fuer Null Object Instanz
     */
    private SpielfeldDarstellungsSteuerung() {
        this.spielfeldDarstellung = null;
        this.backend = null;
    }

    public SpielfeldDarstellungsSteuerung(SpielfeldDarstellung spielfeldView,
            SternenSpielApplicationBackend applicationBackend) {
        this.spielfeldDarstellung = spielfeldView;
        this.backend = applicationBackend;
        spielfeldView.setSpielfeldDarstellungsSteuerung(this);
        backend.addApplicationBackendListener(new InnerSternenSpielBackendListener());

    }

    public void setSpielstein(Spielstein spielstein, int x, int y) {
        backend.setSpielstein(x, y, spielstein);
    }

    /**
     * Reagiert auf Klicks des Spielsteins
     * 
     * @param x
     *            X-Koordinate des Spielsteins
     * @param y
     *            Y-Koordinate des Spielsteins
     * @param mouseEvent
     *            Mouseevent des Spielsteins
     * @return Spielstein der geklickt wurde
     */
    public Spielstein spielSteinClick(int x, int y, MouseEvent mouseEvent) {
        if (isSpielModus()) {
            if (!(getSpielstein(x, y) instanceof Pfeil)) {
                if (isLeftClick(mouseEvent)) {
                    guessStern(x, y);
                } else if (isRightClick(mouseEvent)) {
                    guessAusschluss(x, y);
                }
            }
        }
        /** Editiermodus: */
        else if (!isSpielModus()) {
            if (isLeftClick(mouseEvent)) {
                new PopupMenuForSpielsteinChoice(this,
                        listAvailableStates(x, y), x, y).show(
                        mouseEvent.getComponent(), mouseEvent.getX(),
                        mouseEvent.getY());
            } else if (isRightClick(mouseEvent)) {
                resetSpielsteinState(x, y);
            }
        }
        return getSpielstein(x, y);
    }

    public void spielSteinEntered(int x, int y, MouseEvent mouseEvent) {
        spielfeldDarstellung.highlightSpielstein(x, y);
        /* Wenn Pfeil, Zeigerichtung highlighten */
        final Spielstein stein = backend.getSpielstein(x, y);
        if (stein instanceof Pfeil) {
            final Pfeil pfeil = (Pfeil) stein;
            final Koordinaten richtungsVektor = pfeil.getRichtungsKoordinaten();
            final int spalten = spielfeldDarstellung
                    .getAngezeigteSpaltenAnzahl();
            final int zeilen = spielfeldDarstellung.getAngezeigteZeilenAnzahl();

            /* Gehe die Pfeilzeigerichtung ab und markiere die Spielsteine */
            Koordinaten aktK = new Koordinaten(x, y);
            aktK = aktK.getSummeWith(richtungsVektor);
            while (aktK.getX() > -1 && aktK.getX() < spalten
                    && aktK.getY() < zeilen && aktK.getY() > -1) {
                /*
                 * Nur Spielsteine in Pfeilrichtung highlighten, die selbst
                 * keine Pfeile sind.
                 */
                if (!(backend.getSpielstein(aktK.getX(), aktK.getY()) instanceof Pfeil)) {
                    spielfeldDarstellung.highlightSpielsteinInPfeilrichtung(
                            aktK.getX(), aktK.getY());

                }
                aktK = aktK.getSummeWith(richtungsVektor);

            }
        }
    }

    public void spielSteinExited(int x, int y, MouseEvent mouseEvent) {
        spielfeldDarstellung.unhighlightAll();
    }

    private SpielmodusEnumeration getSpielmodus() {
        return backend.getSpiel().getSpielmodus();
    }

    private Spielstein getSpielstein(int x, int y) {
        return backend.getSpielstein(x, y);
    }

    private List<? extends Spielstein> listAvailableStates(int x, int y) {
        return backend.listAvailableStates(x, y);
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

    public boolean isSpielModus() {
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
            spielfeldDarstellung.setAngezeigteSternAnzahlInSpalte(x,
                    sternAnzahlInSpalte);
            final int sternAnzahlInZeile = spielfeld.countSterneZeile(y);
            spielfeldDarstellung.setAngezeigteSternAnzahlInZeile(y,
                    sternAnzahlInZeile);

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
