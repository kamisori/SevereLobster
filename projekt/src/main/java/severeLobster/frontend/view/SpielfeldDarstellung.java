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
import severeLobster.frontend.controller.SpielfeldDarstellungsSteuerung;

/**
 * Darstellung eines Spielfeldes mit den enthaltenen Spielsteinen. Diese
 * Implementation geht davon aus, dass sich das gesamte Programm im AWT
 * EventDispatch Thread abspielt und wir keine asynchronen Aktionen haben, die
 * später wieder eingereiht werden muessen. Von daher wird in dieser Klasse
 * absichtlich nichts manuell in den AWT Thread eingereiht (kein
 * SwingUtils.invokeAndWait da laut doku: "This method should be used when an
 * application thread needs to update the GUI. It should'nt be called from the
 * EventDispatchThread." ).
 * 
 * @author Lutz Kleiber
 */
public class SpielfeldDarstellung extends JPanel {

    private static final IconFactory ICON_FACTORY = AdvancedDynamicallyResizingIconFactory
            .getInstance();
    /**
     * Steuert SpielfeldDarstellung und hat Strategie fuer Mausklicks, die bei
     * den Spielsteinen gemacht werden.
     */
    private SpielfeldDarstellungsSteuerung spielfeldDarstellungsSteuerung;
    /** Die Ansichten der einzelnen Spielfeldzellen/Spielsteine */
    private JLabel[][] spielsteinDarstellungen;
    /** Oberer Balken - waagerecht ueber Spielfeld dargestellt */
    private JLabel[] sternAnzahlInSpaltenAnzeigen;
    /** Linker Balken - senkrecht neben Spielfeld dargestellt */
    private JLabel[] sternAnzahlInZeilenAnzeigen;

    public SpielfeldDarstellung() {
        setOpaque(false);
    }

    public void setAngezeigtesSpielfeld(final ISpielfeldReadOnly newSpielfeld) {
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
                setSternAnzahlInSpalte(breiteIndex, anzahlSterne);
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
                        setSternAnzahlInZeile(laengeIndex, anzahlSterne);
                    }
                    /** Hole Spielstein fuer diese Koordinate */
                    spielstein = newSpielfeld.getSpielstein(breiteIndex,
                            laengeIndex);
                    /** Setze neue Ansicht fuer diesen Spielstein */
                    setAngezeigterSpielstein(breiteIndex, laengeIndex,
                            spielstein);
                }
            }
        }
    }

    public void setAngezeigterSpielstein(final int x, final int y,
            Spielstein newSpielstein) {

        if (null == newSpielstein) {
            newSpielstein = KeinStein.getInstance();
        }
        final Icon newIcon = ICON_FACTORY.getIconForSpielstein(newSpielstein);
        spielsteinDarstellungen[x][y].setIcon(newIcon);
    }

    public void setSternAnzahlInSpalte(int x, int neuerWert) {
        this.sternAnzahlInSpaltenAnzeigen[x].setText(Integer
                .toString(neuerWert));
    }

    public void setSternAnzahlInZeile(int y, int neuerWert) {
        this.sternAnzahlInZeilenAnzeigen[y]
                .setText(Integer.toString(neuerWert));
    }

    public void setSpielfeldAbmessungen(final int spielfeldBreite,
            final int spielfeldHoehe) {

        /** Nimm alles bisherige aus dem Panel raus */
        this.removeAll();

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
            this.sternAnzahlInSpaltenAnzeigen = new JLabel[spielfeldBreite];
            JLabel pfeilAnzahlView;

            for (int breiteIndex = 0; breiteIndex < spielfeldBreite; breiteIndex++) {

                pfeilAnzahlView = createSternAnzahlAnzeige();
                this.sternAnzahlInSpaltenAnzeigen[breiteIndex] = pfeilAnzahlView;
                this.add(pfeilAnzahlView);
            }
        }
        /**
         * Durchlaufe das Spielfeld zeilenweise und fuege in der Reihenfolge die
         * Spielsteinansichten zum Panel hinzu.
         */
        {
            /** Erzeuge array mit views */
            spielsteinDarstellungen = new JLabel[spielfeldBreite][spielfeldHoehe];
            sternAnzahlInZeilenAnzeigen = new JLabel[spielfeldHoehe];
            JLabel view = null;
            JLabel pfeilAnzahlView;
            for (int laengeIndex = 0; laengeIndex < spielfeldHoehe; laengeIndex++) {
                for (int breiteIndex = 0; breiteIndex < spielfeldBreite; breiteIndex++) {
                    /** Am Anfang jeder Zeile einen PfeilAnzahlView einfuegen */
                    if (0 == breiteIndex) {
                        pfeilAnzahlView = createSternAnzahlAnzeige();
                        this.sternAnzahlInZeilenAnzeigen[laengeIndex] = pfeilAnzahlView;
                        this.add(pfeilAnzahlView);
                    }
                    /**
                     * Erstelle neue Ansichtskomponente fuer Spielstein mit
                     * diesen Koordinaten
                     */
                    view = createSpielsteinAnsicht(breiteIndex, laengeIndex,
                            getSpielfeldDarstellungsSteuerung());
                    /**
                     * Speichere Komponente in Array, fuer leichteren Zugriff
                     * auf einzelne SpielsteinViews
                     */
                    this.spielsteinDarstellungen[breiteIndex][laengeIndex] = view;

                    /** Fuege Ansicht zum Panel hinzu */
                    this.add(view);
                }
            }
        }
        revalidate();
    }

    public void setSpielfeldDarstellungsSteuerung(
            SpielfeldDarstellungsSteuerung spielfeldController) {
        this.spielfeldDarstellungsSteuerung = spielfeldController;
    }

    private SpielfeldDarstellungsSteuerung getSpielfeldDarstellungsSteuerung() {
        return this.spielfeldDarstellungsSteuerung;
    }

    public static JLabel createLabel() {
        final JLabel result = new JLabel();
        initialisiereAllgemeinenLabelStilVon(result);
        return result;
    }

    private static JLabel createSternAnzahlAnzeige() {
        final JLabel result = createLabel();
        result.setFont(new Font("Serif", Font.PLAIN, 20));
        result.setForeground(Color.white);
        return result;
    }

    private static JLabel createSpielsteinAnsicht(final int x, final int y,
            final SpielfeldDarstellungsSteuerung viewController) {
        final JLabel result = createLabel();

        result.addMouseListener(new MouseAdapter() {
            /**
             * Verhalten von mousePressed ist dynamischer als bei mouseClicked ,
             * da auch Klicks beim Bewegen der Maus genommen werden.
             */
            @Override
            public void mousePressed(MouseEvent mouseEvent) {
                viewController.spielSteinClick(x, y, mouseEvent);
            }
        });

        return result;
    }

    private static void initialisiereAllgemeinenLabelStilVon(JLabel label) {
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setBackground(Color.DARK_GRAY);
        label.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
    }
}
