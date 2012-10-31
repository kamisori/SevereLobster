package severeLobster.frontend.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import severeLobster.backend.spiel.KeinStein;
import severeLobster.backend.spiel.Spielfeld;
import severeLobster.backend.spiel.Spielstein;
import severeLobster.frontend.controller.SpielfeldViewController;

/**
 * Darstellung eines Spielfeldes mit den enthaltenen Spielsteinen.
 * 
 * @author LKleiber
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
    public SpielfeldView(final Spielfeld newSpielfeld) {

        if (null == newSpielfeld) {
            throw new NullPointerException("Spielfeld ist null");
        }
        setOpaque(false);
        final int laenge = newSpielfeld.getHoehe();
        final int breite = newSpielfeld.getBreite();

        setSpielfeldAbmessungen(breite, laenge);

        Spielstein spielstein = null;

        // Erstelle oberen Balken fuer Anzahl der Pfeile in den Spalten:
        for (int breiteIndex = 0; breiteIndex < breite; breiteIndex++) {

            int anzahlSterne = newSpielfeld.countSterneSpalte(breiteIndex);
            getSpaltenPfeilAnzahlView(breiteIndex).setText(
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
                    getReihenPfeilAnzahlView(laengeIndex).setText(
                            String.valueOf(anzahlSterne));
                }
                /** Hole naechsten Spielstein */
                spielstein = newSpielfeld.getSpielstein(breiteIndex,
                        laengeIndex);
                /** Setze neue Ansichtskomponente fuer diesen Spielstein */
                setDisplayedSpielstein(breiteIndex, laengeIndex, spielstein);

            }
        }

    }

    public void setSpielfeldAbmessungen(int breite, int laenge) {

        removeAll();
        revalidate();

        final int laengeMitAnzahlPfeilenInZeileView = laenge + 1;
        final int breiteMitAnzahlPfeilenInSpalteView = breite + 1;
        setLayout(new GridLayout(laengeMitAnzahlPfeilenInZeileView,
                breiteMitAnzahlPfeilenInSpalteView));
        // setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
        /**
         * Erzeuge array mit views:
         */
        spielsteinViews = new SpielsteinView[breite][laenge];
        Spielstein spielstein = null;
        SpielsteinView view = null;

        // Fuege oben links ein leeres Label ein
        JLabel dummyObenLinks = new JLabel("", JLabel.CENTER);
        dummyObenLinks.setBorder(BorderFactory
                .createLineBorder(Color.DARK_GRAY));
        add(dummyObenLinks);
        JLabel pfeilAnzahlView;
        spaltenPfeilAnzahlenViews = new JLabel[breite];
        // Erstelle oberen Balken fuer Anzahl der Pfeile in den Spalten:
        for (int breiteIndex = 0; breiteIndex < breite; breiteIndex++) {

            pfeilAnzahlView = new JLabel("", JLabel.CENTER);
            pfeilAnzahlView.setBorder(BorderFactory
                    .createLineBorder(Color.DARK_GRAY));
            spaltenPfeilAnzahlenViews[breiteIndex] = pfeilAnzahlView;
            // Vergroessere Schrift:
            pfeilAnzahlView.setFont(new Font("Serif", Font.PLAIN, 20));
            pfeilAnzahlView.setForeground(Color.white);
            add(pfeilAnzahlView);
        }
        reihenPfeilAnzahlenViews = new JLabel[laenge];
        /**
         * Durchlaufe das Spielfeld zeilenweise und fuege in der Reihenfolge die
         * Spielsteinansichten zum Panel hinzu.
         */
        for (int laengeIndex = 0; laengeIndex < laenge; laengeIndex++) {
            for (int breiteIndex = 0; breiteIndex < breite; breiteIndex++) {
                // Am Anfang jeder Zeile einen PfeilAnzahlView
                // einfuegen:
                if (0 == breiteIndex) {
                    // int anzahlSterne = spielfeld
                    // .countSterneZeile(laengeIndex);
                    pfeilAnzahlView = new JLabel("", JLabel.CENTER);
                    reihenPfeilAnzahlenViews[laengeIndex] = pfeilAnzahlView;
                    pfeilAnzahlView.setBorder(BorderFactory
                            .createLineBorder(Color.DARK_GRAY));
                    pfeilAnzahlView.setFont(new Font("Serif", Font.PLAIN, 20));
                    pfeilAnzahlView.setForeground(Color.white);
                    add(pfeilAnzahlView);
                }
                /** Hole naechsten Spielstein */
                // spielstein = spielfeld.getSpielstein(breiteIndex,
                // laengeIndex);
                /**
                 * Erstelle neue Ansichtskomponente fuer diesen Spielstein
                 */
                view = new SpielsteinView(spielstein, breiteIndex, laengeIndex,
                        spielfeldController);
                view.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
                /**
                 * Speichere Komponente in Array, fuer leichteren Zugriff auf
                 * einzelne SpielsteinViews
                 */
                spielsteinViews[breiteIndex][laengeIndex] = view;

                /** Fuege Ansicht zum Panel hinzu */
                add(view);
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

    public void setDisplayedSpielstein(int x, int y, Spielstein spielstein) {

        final SpielsteinView[][] currentViews = spielsteinViews;
        if (null != currentViews) {
            if (null == spielstein) {
                spielstein = KeinStein.getInstance();
            }
            currentViews[x][y].setDisplayedStein(spielstein);
        }

    }

    public void setSpielfeldController(SpielfeldViewController spielfeldController) {
        this.spielfeldController = spielfeldController;

    }
}
