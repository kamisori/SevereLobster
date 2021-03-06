package severeLobster.frontend.view;

import infrastructure.constants.enums.SpielmodusEnumeration;
import infrastructure.exceptions.LoesungswegNichtEindeutigException;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import severeLobster.backend.spiel.SternenSpielApplicationBackend;
import severeLobster.frontend.controller.SpielfeldDarstellungsSteuerung;

/**
 * Test fuer SpielfeldView, SpielmodusView und Spielfeld. Einfaches Frame, das
 * SpielfeldView und SpielmodusChoiceView zum testen darstellt.
 * 
 * 
 * Wenn DO_RAMDOM_TEST auf true gesetzt wird, wird zufaellig aus den fuer das
 * jeweilige Feld fuer diesen Spielmodus moeglichen Spielsteinen einer getippt.
 * Dabei wird zyklisch der Spielmodus geaendert, um das Verhalten von
 * Spielfeld.getSpielstein(), Spielfeld.setSpielstein() und der automatischen
 * Aktualisierung von SpielfeldView und SpielmodusView zu testen.
 * 
 * @author Lutz Kleiber
 * 
 */
public class SpielfeldViewTestOhneJUnit {

    public static final boolean DO_RANDOM_TEST = false;
    public static final int SPIELFELD_BREITE = 2;
    public static final int SPIELFELD_HOEHE = 2;

    public static void main(String[] args) {
        try {

            SwingUtilities.invokeAndWait(new Runnable() {

                @Override
                public void run() {
                    final SternenSpielApplicationBackend backend = SternenSpielApplicationBackend
                            .getInstance();
                    backend.getSpiel().initializeNewSpielfeld(SPIELFELD_BREITE,
                            SPIELFELD_HOEHE);
                    try {
                        backend.getSpiel().setSpielmodus(
                                SpielmodusEnumeration.EDITIEREN);
                    } catch (LoesungswegNichtEindeutigException e) {
                        e.printStackTrace();
                    }
                    final SpielfeldDarstellung view = new SpielfeldDarstellung();
                    new SpielfeldDarstellungsSteuerung(view, backend);

                    final JFrame frame = new JFrame();
                    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    frame.getContentPane().add(view, BorderLayout.CENTER);
                    frame.pack();
                    frame.setVisible(true);
                }
            });

            /**
             * Aendere die Stati.
             */
            // if (DO_RANDOM_TEST) {
            // while (true) {
            // Random random = new Random();
            // int randomX = 0;
            // int randomY = 0;
            // Spielfeld spielfeld = backend.getSpiel().getSpielfeld();
            // System.out.println(spielfeld.listAvailableStates(0, 0));
            // Spielstein currentStein;
            // List<? extends Spielstein> allowedStates;
            // while (true) {
            // for (int i = 0; i < 20; i++) {
            // randomX = random.nextInt(spielfeld.getBreite());
            // randomY = random.nextInt(spielfeld.getHoehe());
            // allowedStates = spielfeld.listAvailableStates(
            // randomX, randomY);
            // if (allowedStates.size() != 0) {
            // int randomNextListIndex = random
            // .nextInt(allowedStates.size());
            // currentStein = allowedStates
            // .get(randomNextListIndex);
            //
            // backend.getSpiel()
            // .getSpielfeld()
            // .setSpielstein(randomX, randomY,
            // currentStein);
            // }
            // try {
            // Thread.sleep(200);
            // } catch (InterruptedException e) {
            // e.printStackTrace();
            // }
            // }
            //
            // if (backend.getSpiel().getSpielmodus() ==
            // SpielmodusEnumeration.EDITIEREN) {
            // backend.getSpiel().setSpielmodus(
            // SpielmodusEnumeration.SPIELEN);
            // System.out.println("Spielmodus");
            // frame.setTitle("Spielmodus");
            // } else {
            // backend.getSpiel().setSpielmodus(
            // SpielmodusEnumeration.EDITIEREN);
            // System.out.println("Editmodus");
            // frame.setTitle("Editmodus");
            // }
            // }
            // }
            // }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
