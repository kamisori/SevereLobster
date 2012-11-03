package severeLobster.frontend.view;

import javax.swing.JLabel;
import javax.swing.JPanel;

import severeLobster.backend.spiel.ISpielfeldReadOnly;
import severeLobster.backend.spiel.KeinStein;
import severeLobster.backend.spiel.Spielstein;
import severeLobster.frontend.controller.SpielfeldViewController;

/**
 * Darstellung eines Spielfeldes mit den enthaltenen Spielsteinen.
 * 
 * @author Lutz Kleiber
 */
public class SpielfeldView extends JPanel {

    private SpielsteinView[][] spielsteinViews;
    private SpielfeldViewController spielfeldController;
    // Oberer Balken - waagerecht
    private JLabel[] spaltenPfeilAnzahlenViews;
    // Linker Balken - senkrecht
    private JLabel[] reihenPfeilAnzahlenViews;

    public SpielfeldView() {
        setOpaque(false);
    }

    /**
     * Konstruktor sollte nur fuer Vorschau benutzt werden. Es werden keine
     * Klicks unterstuetzt, da die SpielSteinView Instanzen einen Controller
     * uebergeben bekommen, der hier noch null ist.
     * 
     * @param newSpielfeld
     */
    @Deprecated
    public SpielfeldView(final ISpielfeldReadOnly newSpielfeld) {

        this();
        if (null == newSpielfeld) {
            throw new NullPointerException("Spielfeld ist null");
        }
        final int laenge = newSpielfeld.getHoehe();
        final int breite = newSpielfeld.getBreite();

        setSpielfeldAbmessungen(breite, laenge);

        /** Setze Werte in oberem Balken fuer Anzahl der Pfeile in den Spalten */
        {
            int anzahlSterne = 0;
            for (int breiteIndex = 0; breiteIndex < breite; breiteIndex++) {

                anzahlSterne = newSpielfeld.countSterneSpalte(breiteIndex);
                getSpaltenPfeilAnzahlView(breiteIndex).setText(
                        String.valueOf(anzahlSterne));
            }
        }
        /**
         * Durchlaufe das Spielfeld zeilenweise und setze in der Reihenfolge die
         * Icons der Spielsteinansichten.
         */
        {
            Spielstein spielstein = null;
            int anzahlSterne = 0;
            for (int laengeIndex = 0; laengeIndex < laenge; laengeIndex++) {
                for (int breiteIndex = 0; breiteIndex < breite; breiteIndex++) {
                    /** Am Anfang jeder Zeile einen PfeilAnzahlView einstellen */
                    if (0 == breiteIndex) {
                        anzahlSterne = newSpielfeld
                                .countSterneZeile(laengeIndex);
                        getReihenPfeilAnzahlView(laengeIndex).setText(
                                String.valueOf(anzahlSterne));
                    }
                    /** Hole Spielstein fuer diese Koordinate */
                    spielstein = newSpielfeld.getSpielstein(breiteIndex,
                            laengeIndex);
                    /** Setze neue Ansicht fuer diesen Spielstein */
                    setDisplayedSpielstein(breiteIndex, laengeIndex, spielstein);
                }
            }
        }
    }

    public void setSpielfeldAbmessungen(final int spielfeldBreite,
            final int spielfeldHoehe) {

        /** Nimm alles bisherige aus dem Panel raus */
        this.removeAll();
        this.revalidate();

        /**
         * Intitialisiere Layout immer neu, da sich die Ausmasse geaendert haben
         * koennten. Zu Hoehe und Breite kommt je 1 hinzu, da die Anzahl der
         * Pfeile in Spalten und Zeilen noch dargestellt werden muessen (oben
         * und links). Durch QuadratischeZellenGridLayout sind alle Zellen
         * gleichgross und quadratisch.
         */
        this.setLayout(new QuadratischeZellenGridLayout(spielfeldHoehe + 1,
                spielfeldBreite + 1));
        /**
         * Fuege oben links ein leeres Label ein. Das Label dient nur als
         * Platzhalter und hat keinen Inhalt
         */
        {
            final JLabel dummyObenLinks = SpielsteinView.createLabel();
            this.add(dummyObenLinks);
        }
        /**
         * Erstelle oberen Balken fuer Anzeige der Pfeilanzahl in den Spalten
         */
        {
            this.spaltenPfeilAnzahlenViews = new JLabel[spielfeldBreite];
            JLabel pfeilAnzahlView;

            for (int breiteIndex = 0; breiteIndex < spielfeldBreite; breiteIndex++) {

                pfeilAnzahlView = SpielsteinView.createPfeilAnzahlLabel();
                this.spaltenPfeilAnzahlenViews[breiteIndex] = pfeilAnzahlView;
                this.add(pfeilAnzahlView);
            }
        }
        /**
         * Durchlaufe das Spielfeld zeilenweise und fuege in der Reihenfolge die
         * Spielsteinansichten zum Panel hinzu.
         */
        {
            /** Erzeuge array mit views */
            spielsteinViews = new SpielsteinView[spielfeldBreite][spielfeldHoehe];
            reihenPfeilAnzahlenViews = new JLabel[spielfeldHoehe];
            SpielsteinView view = null;
            JLabel pfeilAnzahlView;
            for (int laengeIndex = 0; laengeIndex < spielfeldHoehe; laengeIndex++) {
                for (int breiteIndex = 0; breiteIndex < spielfeldBreite; breiteIndex++) {
                    /** Am Anfang jeder Zeile einen PfeilAnzahlView einfuegen */
                    if (0 == breiteIndex) {
                        pfeilAnzahlView = SpielsteinView
                                .createPfeilAnzahlLabel();
                        this.reihenPfeilAnzahlenViews[laengeIndex] = pfeilAnzahlView;
                        this.add(pfeilAnzahlView);
                    }
                    /**
                     * Erstelle neue Ansichtskomponente fuer Spielstein mit
                     * diesen Koordinaten
                     */
                    view = SpielsteinView.createSpielsteinView(breiteIndex,
                            laengeIndex, spielfeldController);
                    /**
                     * Speichere Komponente in Array, fuer leichteren Zugriff
                     * auf einzelne SpielsteinViews
                     */
                    this.spielsteinViews[breiteIndex][laengeIndex] = view;

                    /** Fuege Ansicht zum Panel hinzu */
                    this.add(view);
                }
            }
        }
        revalidate();
    }

    public JLabel getSpaltenPfeilAnzahlView(int x) {
        return spaltenPfeilAnzahlenViews[x];
    }

    public JLabel getReihenPfeilAnzahlView(int y) {
        return reihenPfeilAnzahlenViews[y];
    }

    public void setDisplayedSpielstein(int x, int y, Spielstein newSpielstein) {

        if (null == newSpielstein) {
            newSpielstein = KeinStein.getInstance();
        }
        spielsteinViews[x][y].setDisplayedSpielstein(newSpielstein);
    }

    public void setSpielfeldController(
            SpielfeldViewController spielfeldController) {
        this.spielfeldController = spielfeldController;

    }

}
