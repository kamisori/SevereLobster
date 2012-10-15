package severeLobster.frontend.view;

import java.awt.GridLayout;

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
 * 
 */
public class SpielfeldView extends JPanel implements IControllableSpielfeldView {

    private SpielsteinView[][] spielsteinViews;
    private SpielfeldController spielfeldController;

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
        this.setLayout(new GridLayout(laenge, breite));
        /**
         * Erzeuge array mit views:
         */
        spielsteinViews = new SpielsteinView[breite][laenge];

        Spielstein spielstein = null;
        SpielsteinView view = null;

        /**
         * Durchlaufe das Spielfeld zeilenweise und fuege in der Reihenfolge die
         * Spielsteinansichten zum Panel hinzu.
         */
        for (int laengeIndex = 0; laengeIndex < laenge; laengeIndex++) {
            for (int breiteIndex = 0; breiteIndex < breite; breiteIndex++) {
                /** Hole naechsten Spielstein */
                spielstein = spielfeld.getSpielstein(breiteIndex, laengeIndex);
                /** Erstelle neue Ansichtskomponente fuer diesen Spielstein */
                view = new SpielsteinView(spielstein);
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
                setLayout(new GridLayout(laenge, breite));
                /**
                 * Erzeuge array mit views:
                 */
                spielsteinViews = new SpielsteinView[breite][laenge];

                Spielstein spielstein = null;
                SpielsteinView view = null;

                /**
                 * Durchlaufe das Spielfeld zeilenweise und fuege in der
                 * Reihenfolge die Spielsteinansichten zum Panel hinzu.
                 */
                for (int laengeIndex = 0; laengeIndex < laenge; laengeIndex++) {
                    for (int breiteIndex = 0; breiteIndex < breite; breiteIndex++) {
                        /** Hole naechsten Spielstein */
                        spielstein = spielfeld.getSpielstein(breiteIndex,
                                laengeIndex);
                        /**
                         * Erstelle neue Ansichtskomponente fuer diesen
                         * Spielstein
                         */
                        view = new SpielsteinView(spielstein);
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
