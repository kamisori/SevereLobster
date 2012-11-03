package severeLobster.frontend.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.Icon;
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

    private static final IconFactory ICON_FACTORY = AdvancedDynamicallyResizingIconFactory
            .getInstance();
    private SpielfeldViewController spielfeldController;
    private JLabel[][] spielsteinViews;
    // Oberer Balken - waagerecht
    private JLabel[] spaltenPfeilAnzahlenViews;
    // Linker Balken - senkrecht
    private JLabel[] reihenPfeilAnzahlenViews;

    public SpielfeldView() {
        setOpaque(false);
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
            final JLabel dummyObenLinks = createLabel();
            this.add(dummyObenLinks);
        }
        /**
         * Erstelle oberen Balken fuer Anzeige der Pfeilanzahl in den Spalten
         */
        {
            this.spaltenPfeilAnzahlenViews = new JLabel[spielfeldBreite];
            JLabel pfeilAnzahlView;

            for (int breiteIndex = 0; breiteIndex < spielfeldBreite; breiteIndex++) {

                pfeilAnzahlView = createPfeilAnzahlLabel();
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
            spielsteinViews = new JLabel[spielfeldBreite][spielfeldHoehe];
            reihenPfeilAnzahlenViews = new JLabel[spielfeldHoehe];
            JLabel view = null;
            JLabel pfeilAnzahlView;
            for (int laengeIndex = 0; laengeIndex < spielfeldHoehe; laengeIndex++) {
                for (int breiteIndex = 0; breiteIndex < spielfeldBreite; breiteIndex++) {
                    /** Am Anfang jeder Zeile einen PfeilAnzahlView einfuegen */
                    if (0 == breiteIndex) {
                        pfeilAnzahlView = createPfeilAnzahlLabel();
                        this.reihenPfeilAnzahlenViews[laengeIndex] = pfeilAnzahlView;
                        this.add(pfeilAnzahlView);
                    }
                    /**
                     * Erstelle neue Ansichtskomponente fuer Spielstein mit
                     * diesen Koordinaten
                     */
                    view = createSpielsteinView(breiteIndex, laengeIndex,
                            getController());
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

    public void setSpaltenPfeilAnzahl(int x, int neuerWert) {
        this.spaltenPfeilAnzahlenViews[x].setText(Integer.toString(neuerWert));
    }

    public void setReihenPfeilAnzahl(int y, int neuerWert) {
        this.reihenPfeilAnzahlenViews[y].setText(Integer.toString(neuerWert));
    }

    public void setDisplayedSpielstein(final int x, final int y,
            Spielstein newSpielstein) {

        if (null == newSpielstein) {
            newSpielstein = KeinStein.getInstance();
        }
        final Icon newIcon = ICON_FACTORY.getIconForSpielstein(newSpielstein);
        spielsteinViews[x][y].setIcon(newIcon);
    }

    public void setSpielfeldController(
            SpielfeldViewController spielfeldController) {
        this.spielfeldController = spielfeldController;

    }

    public void setDisplayedSpielfeld(final ISpielfeldReadOnly newSpielfeld) {
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
                setSpaltenPfeilAnzahl(breiteIndex, anzahlSterne);
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
                        setReihenPfeilAnzahl(laengeIndex, anzahlSterne);
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

    private SpielfeldViewController getController() {
        return this.spielfeldController;
    }

    private static JLabel createSpielsteinView(final int x, final int y,
            final SpielfeldViewController viewController) {
        final JLabel result = createLabel();

        result.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                viewController.spielSteinClick(x, y, mouseEvent);
            }
        });

        return result;
    }

    public static JLabel createLabel() {
        final JLabel result = new JLabel();
        initializeVisibleStyleOf(result);
        return result;
    }

    private static JLabel createPfeilAnzahlLabel() {
        final JLabel result = createLabel();
        result.setFont(new Font("Serif", Font.PLAIN, 20));
        result.setForeground(Color.white);
        return result;
    }

    private static void initializeVisibleStyleOf(JLabel label) {
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setBackground(Color.DARK_GRAY);
        label.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
    }

}
