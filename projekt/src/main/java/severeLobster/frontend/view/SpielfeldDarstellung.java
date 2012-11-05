package severeLobster.frontend.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.LayoutManager;
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
 * Darstellung eines Spielfeldes mit den enthaltenen Spielsteinen. In dieser
 * Klasse wird absichtlich nichts in den AWT Thread eingereiht. Wenn von
 * ausserhalb des AWT Event Dispatch Threads Aenderungen am Zustand einer
 * Instanz dieser Klasse gemacht werden, sollten diese manuell in den AWT Thread
 * eingereiht werden (SwingUtils.invokeLater oder SwingUtils.invokeAndWait).
 * 
 * @author Lutz Kleiber
 */
public class SpielfeldDarstellung extends JPanel {

    private static IconFactory ICON_FACTORY = AdvancedDynamicallyResizingIconFactory
            .getInstance();
    /**
     * Steuert SpielfeldDarstellung und hat Strategie fuer Mausklicks, die bei
     * den Spielsteinen gemacht werden.
     */
    private SpielfeldDarstellungsSteuerung spielfeldDarstellungsSteuerung;
    /** Die Ansichten der einzelnen Spielfeldzellen/Spielsteine */
    private JLabel[][] spielsteinDarstellungen;
    /**
     * Der obere Balken mit der Anzeige der Sternanzahl in den Spalten -
     * waagerecht ueber Spielfeld dargestellt
     */
    private JLabel[] sternAnzahlInSpaltenAnzeigen;
    /**
     * Der linke Balken mit der Anzeige der Sternanzahl in den Zeilen -
     * senkrecht neben Spielfeld dargestellt
     */
    private JLabel[] sternAnzahlInZeilenAnzeigen;

    public SpielfeldDarstellung() {
        this(false);
    }

    public SpielfeldDarstellung(final boolean isDoubleBuffered) {
        super(new QuadratischeZellenGridLayout(1, 1), isDoubleBuffered);
        setOpaque(false);
    }

    /**
     * Setzt die IconFactories fuer alle Instanzen neu. Aktualisiert nicht die
     * Darstellung.
     * 
     * @param newIconFactory
     */
    public static void setIconFactory(final IconFactory newIconFactory) {
        if (null == newIconFactory) {
            throw new NullPointerException("Neue IconFactory ist null");
        }
        SpielfeldDarstellung.ICON_FACTORY = newIconFactory;
    }

    public void setAngezeigtesSpielfeld(final ISpielfeldReadOnly newSpielfeld) {
        if (null == newSpielfeld) {
            throw new NullPointerException("Spielfeld ist null");
        }
        final int spaltenAnzahl = newSpielfeld.getBreite();
        final int zeilenAnzahl = newSpielfeld.getHoehe();

        setSpielfeldAbmessungen(spaltenAnzahl, zeilenAnzahl);

        /** Setze Anzeigen der Sternanzahl in den Spalten */
        int anzahlSterneInSpalte;
        for (int spaltenIndex = 0; spaltenIndex < spaltenAnzahl; spaltenIndex++) {

            anzahlSterneInSpalte = newSpielfeld.countSterneSpalte(spaltenIndex);
            setSternAnzahlInSpalte(spaltenIndex, anzahlSterneInSpalte);
        }
        /** Setze Anzeigen der Sternanzahl in den Zeilen */
        int anzahlSterneInZeile;
        for (int zeilenIndex = 0; zeilenIndex < zeilenAnzahl; zeilenIndex++) {

            anzahlSterneInZeile = newSpielfeld.countSterneZeile(zeilenIndex);
            setSternAnzahlInZeile(zeilenIndex, anzahlSterneInZeile);
        }
        /**
         * Durchlaufe das Spielfeld und setze fuer die Spielsteine die Icons an
         * der entsprechenden Koordinate im Panel
         */
        Spielstein aktuellerStein;
        for (int zeilenIndex = 0; zeilenIndex < zeilenAnzahl; zeilenIndex++) {
            for (int spaltenIndex = 0; spaltenIndex < spaltenAnzahl; spaltenIndex++) {

                /** Hole Spielstein fuer diese Koordinate */
                aktuellerStein = newSpielfeld.getSpielstein(spaltenIndex,
                        zeilenIndex);
                /** Setze neue Ansicht fuer diesen Spielstein */
                setAngezeigterSpielstein(spaltenIndex, zeilenIndex,
                        aktuellerStein);
            }
        }
    }

    public void setAngezeigterSpielstein(final int spaltenIndex,
            final int zeilenIndex, Spielstein newSpielstein) {

        throwExceptionIfIndexOutOfBounds(spaltenIndex, zeilenIndex);

        if (null == newSpielstein) {
            newSpielstein = KeinStein.getInstance();
        }
        final Icon newIcon = ICON_FACTORY.getIconForSpielstein(newSpielstein);
        spielsteinDarstellungen[spaltenIndex][zeilenIndex].setIcon(newIcon);
    }

    public int getSpaltenAnzahl() {
        return spielsteinDarstellungen.length;
    }

    public int getZeilenAnzahl() {
        return spielsteinDarstellungen[0].length;
    }

    public void setSternAnzahlInSpalte(final int x, final int neuerWert) {
        this.sternAnzahlInSpaltenAnzeigen[x].setText(Integer
                .toString(neuerWert));
    }

    public void setSternAnzahlInZeile(final int y, final int neuerWert) {
        this.sternAnzahlInZeilenAnzeigen[y]
                .setText(Integer.toString(neuerWert));
    }

    public void setSpielfeldAbmessungen(final int anzahlSpalten,
            final int anzahlZeilen) {

        /** Nimm alles bisherige aus dem Panel raus */
        this.removeAll();

        /**
         * Intitialisiere Layout immer neu, da sich die Ausmasse geaendert haben
         * koennten. Zu Hoehe und Breite kommt je 1 hinzu, da die Anzahl der
         * Pfeile in Spalten und Zeilen noch dargestellt werden muessen (oben
         * und links). Durch QuadratischeZellenGridLayout sind alle Zellen
         * gleichgross und quadratisch.
         */
        this.setLayout(new QuadratischeZellenGridLayout(anzahlZeilen + 1,
                anzahlSpalten + 1));
        /**
         * Fuege oben links ein leeres Label ein. Das Label dient nur als
         * Platzhalter und hat keinen Inhalt
         */
        final JLabel dummyObenLinks = createLabel();
        this.add(dummyObenLinks);
        /**
         * Erstelle neues Array zur Speicherung der Spaltensternanzahl
         * Anzeigelabels
         */
        this.sternAnzahlInSpaltenAnzeigen = new JLabel[anzahlSpalten];
        /**
         * Erstelle oberen Balken fuer Anzeige der Sternanzahl in den Spalten
         * und speichere die neu erstellten JLabels im Array
         */
        JLabel pfeilAnzahlView;
        for (int spaltenIndex = 0; spaltenIndex < anzahlSpalten; spaltenIndex++) {

            pfeilAnzahlView = createSternAnzahlAnzeige();
            this.sternAnzahlInSpaltenAnzeigen[spaltenIndex] = pfeilAnzahlView;
            this.add(pfeilAnzahlView);
        }

        /** Fuege die Spielsteinansichten zum Panel hinzu */
        /** Erzeuge arrays mit views */
        this.spielsteinDarstellungen = new JLabel[anzahlSpalten][anzahlZeilen];
        this.sternAnzahlInZeilenAnzeigen = new JLabel[anzahlZeilen];

        JLabel label;
        for (int zeilenIndex = 0; zeilenIndex < anzahlZeilen; zeilenIndex++) {
            for (int spaltenIndex = 0; spaltenIndex < anzahlSpalten; spaltenIndex++) {
                /** Am Anfang jeder Zeile eine Sternanzahlanzeige einfuegen */
                if (0 == spaltenIndex) {
                    label = createSternAnzahlAnzeige();
                    this.sternAnzahlInZeilenAnzeigen[zeilenIndex] = label;
                    this.add(label);
                }
                /**
                 * Erstelle neue Ansichtskomponente fuer Spielstein mit diesen
                 * Koordinaten
                 */
                label = createSpielsteinAnsicht(spaltenIndex, zeilenIndex,
                        getSpielfeldDarstellungsSteuerung());
                /**
                 * Speichere Komponente in Array, fuer leichteren Zugriff auf
                 * einzelne SpielsteinViews
                 */
                this.spielsteinDarstellungen[spaltenIndex][zeilenIndex] = label;

                /** Fuege Ansicht zum Panel hinzu */
                this.add(label);
            }
        }
        revalidate();
    }

    public void setSpielfeldDarstellungsSteuerung(
            final SpielfeldDarstellungsSteuerung spielfeldController) {
        this.spielfeldDarstellungsSteuerung = spielfeldController;
    }

    /**
     * Akzeptiert nur QuadratischeZellenGridLayout, weil sonst die Darstellung
     * des Spielfeldes verzerrt sein kann und die Felder nicht quadratisch sind.
     */
    @Override
    public void setLayout(final LayoutManager layoutManager) {
        if (null == layoutManager) {
            throw new NullPointerException("LayoutManager ist null");
        }
        if (!(layoutManager instanceof QuadratischeZellenGridLayout)) {
            throw new IllegalArgumentException(
                    "SpielfeldDarstellung funktioniert nur mit QuadratischeZellenGridLayout");
        }
        super.setLayout(layoutManager);
    }

    private void throwExceptionIfIndexOutOfBounds(final int spaltenIndex,
            final int zeilenIndex) throws ArrayIndexOutOfBoundsException {
        if ((spaltenIndex < 0) || (spaltenIndex > getSpaltenAnzahl() - 1)
                || (zeilenIndex < 0) || (zeilenIndex > getZeilenAnzahl() - 1)) {
            throw new ArrayIndexOutOfBoundsException(
                    "Die uebergebenen Koordinaten X:"
                            + spaltenIndex
                            + " Y:"
                            + zeilenIndex
                            + " sind ausserhalb des aktuell gezeichneten Spielfelds");
        }
    }

    private SpielfeldDarstellungsSteuerung getSpielfeldDarstellungsSteuerung() {
        if (null == this.spielfeldDarstellungsSteuerung) {
            return SpielfeldDarstellungsSteuerung.NULL_OBJECT_INSTANCE;
        } else {
            return this.spielfeldDarstellungsSteuerung;
        }
    }

    private static JLabel createLabel() {
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
            public void mousePressed(final MouseEvent mouseEvent) {
                viewController.spielSteinClick(x, y, mouseEvent);
            }
        });

        return result;
    }

    private static void initialisiereAllgemeinenLabelStilVon(final JLabel label) {
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setBackground(Color.DARK_GRAY);
        label.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
    }
}
