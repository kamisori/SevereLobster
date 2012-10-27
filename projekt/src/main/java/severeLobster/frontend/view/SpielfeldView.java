package severeLobster.frontend.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import severeLobster.backend.spiel.KeinStein;
import severeLobster.backend.spiel.Spielfeld;
import severeLobster.backend.spiel.Spielstein;
import severeLobster.frontend.controller.SpielfeldController;

/**
 * Darstellung eines Spielfeldes mit den enthaltenen Spielsteinen.
 * 
 * @author LKleiber
 */
public class SpielfeldView extends JPanel implements IControllableSpielfeldView {

    private SpielsteinView[][] spielsteinViews;
    private SpielfeldController spielfeldController;
    // Oberer Balken - waagerecht
    private JLabel[] spaltenPfeilAnzahlenViews;

    private JLabel[] reihenPfeilAnzahlenViews;

    public SpielfeldView() {

    }

    public SpielfeldView(final Spielfeld spielfeld) {
        if (null == spielfeld) {
            throw new NullPointerException("Spielfeld ist null");
        }
        setOpaque(false);

        final int laenge = spielfeld.getHoehe();
        final int breite = spielfeld.getBreite();

        /**
         * Gridlayout bietet die tabellenartige Darstellung der zum Panel
         * hinzugefuegten Komponenten.
         */
        final int laengeMitAnzahlPfeilenInZeileView = laenge + 1;
        final int breiteMitAnzahlPfeilenInSpalteView = breite + 1;
        this.setLayout(new GridLayout(laengeMitAnzahlPfeilenInZeileView,
                breiteMitAnzahlPfeilenInSpalteView));
        /**
         * Erzeuge array mit views:
         */
        spielsteinViews = new SpielsteinView[breite][laenge];

        Spielstein spielstein = null;
        SpielsteinView view = null;

        // Fuege oben links ein leeres Label ein
        add(new JLabel("", JLabel.CENTER));
        JLabel pfeilAnzahlView;
        spaltenPfeilAnzahlenViews = new JLabel[breite];
        // Erstelle oberen Balken fuer Anzahl der Pfeile in den Spalten:
        for (int breiteIndex = 0; breiteIndex < breite; breiteIndex++) {

            int anzahlSterne = spielfeld.countSterneSpalte(breiteIndex);
            pfeilAnzahlView = new JLabel(String.valueOf(anzahlSterne),
                    JLabel.CENTER);
            spaltenPfeilAnzahlenViews[breiteIndex] = pfeilAnzahlView;
            pfeilAnzahlView.setBorder(BorderFactory
                    .createLineBorder(Color.DARK_GRAY));
            // Vergroessere Schrift:
            pfeilAnzahlView.setFont(new Font("Serif", Font.PLAIN, 14));
            pfeilAnzahlView.setForeground(Color.white);
            this.add(pfeilAnzahlView);
        }
        reihenPfeilAnzahlenViews = new JLabel[laenge];
        /**
         * Durchlaufe das Spielfeld zeilenweise und fuege in der Reihenfolge die
         * Spielsteinansichten zum Panel hinzu.
         */
        for (int laengeIndex = 0; laengeIndex < laenge; laengeIndex++) {
            for (int breiteIndex = 0; breiteIndex < breite; breiteIndex++) {
                // Am Anfang jeder Zeile einen PfeilAnzahlView einfuegen:
                if (0 == breiteIndex) {
                    int anzahlSterne = spielfeld.countSterneZeile(laengeIndex);
                    pfeilAnzahlView = new JLabel(String.valueOf(anzahlSterne),
                            JLabel.CENTER);
                    pfeilAnzahlView.setBorder(BorderFactory
                            .createLineBorder(Color.DARK_GRAY));
                    reihenPfeilAnzahlenViews[laengeIndex] = pfeilAnzahlView;
                    pfeilAnzahlView.setFont(new Font("Serif", Font.PLAIN, 20));
                    pfeilAnzahlView.setForeground(Color.white);
                    this.add(pfeilAnzahlView);
                }
                /** Hole naechsten Spielstein */
                spielstein = spielfeld.getSpielstein(breiteIndex, laengeIndex);
                /** Erstelle neue Ansichtskomponente fuer diesen Spielstein */
                view = new SpielsteinView(spielstein, breiteIndex, laengeIndex,
                        spielfeldController);
                view.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
                /**
                 * Speichere Komponente in Array, fuer leichteren Zugriff auf
                 * einzelne SpielsteinViews
                 */
                spielsteinViews[breiteIndex][laengeIndex] = view;

                /** Fuege Ansicht zum Panel hinzu */
                this.add(view);
            }
        }
    }

    public JLabel getSpaltenPfeilAnzahlView(int x) {
        return spaltenPfeilAnzahlenViews[x];
    }

    public JLabel getReihenPfeilAnzahlView(int y) {
        return reihenPfeilAnzahlenViews[y];
    }

    @Override
    public void setDisplayedSpielfeld(final Spielfeld spielfeld) {

        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {

                removeAll();
                revalidate();
                // final SpielsteinView[][] currentViews = spielsteinViews;
                // if (null != currentViews) {
                // int breite = currentViews.length;
                // int laenge = currentViews[0].length;
                //
                // for (int laengeIndex = 0; laengeIndex < laenge;
                // laengeIndex++) {
                // for (int breiteIndex = 0; breiteIndex < breite;
                // breiteIndex++) {
                // spielsteinViews[breiteIndex][laengeIndex];
                //
                // /** Fuege Ansicht zum Panel hinzu */
                // this.add(view);
                // }
                // }
                // }
                // removeAll();
                final int laenge = spielfeld.getHoehe();
                final int breite = spielfeld.getBreite();

                /**
                 * Gridlayout bietet die tabellenartige Darstellung der zum
                 * Panel hinzugefuegten Komponenten.
                 */
                final int laengeMitAnzahlPfeilenInZeileView = laenge + 1;
                final int breiteMitAnzahlPfeilenInSpalteView = breite + 1;
                setLayout(new GridLayout(laengeMitAnzahlPfeilenInZeileView,
                        breiteMitAnzahlPfeilenInSpalteView));
                // setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
                /**
                 * Erzeuge array mit views:
                 */
                spielsteinViews = new SpielsteinView[breite][laenge];
                // add(new JLabel("1"));
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
                    int anzahlSterne = spielfeld.countSterneSpalte(breiteIndex);
                    pfeilAnzahlView = new JLabel(String.valueOf(anzahlSterne),
                            JLabel.CENTER);
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
                 * Durchlaufe das Spielfeld zeilenweise und fuege in der
                 * Reihenfolge die Spielsteinansichten zum Panel hinzu.
                 */
                for (int laengeIndex = 0; laengeIndex < laenge; laengeIndex++) {
                    for (int breiteIndex = 0; breiteIndex < breite; breiteIndex++) {
                        // Am Anfang jeder Zeile einen PfeilAnzahlView
                        // einfuegen:
                        if (0 == breiteIndex) {
                            int anzahlSterne = spielfeld
                                    .countSterneZeile(laengeIndex);
                            pfeilAnzahlView = new JLabel(String
                                    .valueOf(anzahlSterne), JLabel.CENTER);
                            reihenPfeilAnzahlenViews[laengeIndex] = pfeilAnzahlView;
                            pfeilAnzahlView.setBorder(BorderFactory
                                    .createLineBorder(Color.DARK_GRAY));
                            pfeilAnzahlView.setFont(new Font("Serif",
                                    Font.PLAIN, 20));
                            pfeilAnzahlView.setForeground(Color.white);
                            add(pfeilAnzahlView);
                        }
                        /** Hole naechsten Spielstein */
                        spielstein = spielfeld.getSpielstein(breiteIndex,
                                laengeIndex);
                        /**
                         * Erstelle neue Ansichtskomponente fuer diesen
                         * Spielstein
                         */
                        view = new SpielsteinView(spielstein, breiteIndex,
                                laengeIndex, spielfeldController);
                        view.setBorder(BorderFactory
                                .createLineBorder(Color.DARK_GRAY));
                        /**
                         * Speichere Komponente in Array, fuer leichteren
                         * Zugriff auf einzelne SpielsteinViews
                         */
                        spielsteinViews[breiteIndex][laengeIndex] = view;

                        /** Fuege Ansicht zum Panel hinzu */
                        add(view);
                    }
                }
                revalidate();

            }
        });

    }

    @Override
    public void setDisplayedSpielstein(int x, int y, Spielstein spielstein) {

        final SpielsteinView[][] currentViews = spielsteinViews;
        if (null != currentViews) {
            if (null == spielstein) {
                spielstein = KeinStein.getInstance();
            }
            currentViews[x][y].setDisplayedStein(spielstein);
        }

    }

    @Override
    public void setSpielfeldController(SpielfeldController spielfeldController) {
        this.spielfeldController = spielfeldController;
        this.setDisplayedSpielfeld(spielfeldController.getSpielfeld());

    }
}
