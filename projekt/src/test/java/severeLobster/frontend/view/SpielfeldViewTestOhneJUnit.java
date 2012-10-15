package severeLobster.frontend.view;

import infrastructure.constants.enums.SpielmodusEnumeration;
import severeLobster.backend.spiel.Spielfeld;
import severeLobster.backend.spiel.Spielstein;
import severeLobster.backend.spiel.SternenSpielApplicationBackend;
import severeLobster.frontend.controller.SpielfeldController;

import javax.swing.JFrame;
import java.awt.BorderLayout;
import java.util.List;
import java.util.Random;

/**
 * Test fuer SpielfeldView und Spielfeld. Es wird zufaellig aus den fuer das
 * jeweilige Feld fuer diesen Spielmodus moeglichen Spielsteinen einer getippt.
 * Dabei wird zyklisch der Spielmodus geaendert, um das Verhalten von
 * Spielfeld.getSpielstein(), Spielfeld.setSpielstein() und der automatischne
 * Aktualisierung von SpielfeldView zu testen.
 * 
 * @author Lutz Kleiber
 * 
 */
public class SpielfeldViewTestOhneJUnit {

    public static void main(String[] args) {
        try {

            final SternenSpielApplicationBackend backend = new SternenSpielApplicationBackend();

            backend.getSpiel().setSpielmodus(SpielmodusEnumeration.EDITIEREN);
            final SpielfeldView view = new SpielfeldView(backend.getSpiel()
                    .getSpielfeld());

            new SpielfeldController(view, backend);

            final JFrame frame = new JFrame();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.getContentPane().add(view, BorderLayout.CENTER);
            frame.pack();
            frame.setVisible(true);

            /**
             * Aendere die Stati.
             */
            Random random = new Random();
            int randomX = 0;
            int randomY = 0;
            Spielfeld spielfeld = backend.getSpiel().getSpielfeld();
            System.out.println(spielfeld.listAvailableStates(0, 0));
            Spielstein currentStein;
            List<? extends Spielstein> allowedStates;
            while (true) {
                for (int i = 0; i < 20; i++) {
                    randomX = random.nextInt(spielfeld.getBreite());
                    randomY = random.nextInt(spielfeld.getHoehe());
                    allowedStates = spielfeld.listAvailableStates(randomX,
                            randomY);
                    if (allowedStates.size() != 0) {
                        int randomNextListIndex = random.nextInt(allowedStates
                                .size());
                        currentStein = allowedStates.get(randomNextListIndex);

                        backend.getSpiel().getSpielfeld()
                                .setSpielstein(randomX, randomY, currentStein);
                    }
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                if (backend.getSpiel().getSpielmodus() == SpielmodusEnumeration.EDITIEREN) {
                    backend.getSpiel().setSpielmodus(
                            SpielmodusEnumeration.SPIELEN);
                    System.out.println("Spielmodus");
                    frame.setTitle("Spielmodus");
                } else {
                    backend.getSpiel().setSpielmodus(
                            SpielmodusEnumeration.EDITIEREN);
                    System.out.println("Editmodus");
                    frame.setTitle("Editmodus");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
