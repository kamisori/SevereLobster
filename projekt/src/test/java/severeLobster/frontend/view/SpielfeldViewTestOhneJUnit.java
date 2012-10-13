package severeLobster.frontend.view;

import java.awt.BorderLayout;
import java.util.List;
import java.util.Random;

import javax.swing.JFrame;

import severeLobster.backend.spiel.Spielfeld;
import severeLobster.backend.spiel.Spielstein;

/**
 * Passt net mehr zu neuen Architektur.
 * 
 * @author Lutz Kleiber
 * 
 */
public class SpielfeldViewTestOhneJUnit {

    // public static void main(String[] args) {
    // final Spielfeld spielfeld = new Spielfeld(15, 10);
    //
    // final SpielfeldView view = new SpielfeldView(spielfeld);
    //
    // final JFrame frame = new JFrame();
    // frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    // frame.getContentPane().add(view, BorderLayout.CENTER);
    // frame.pack();
    // frame.setVisible(true);
    //
    // /**
    // * Aendere die Stati in spielstein.
    // */
    // Random random = new Random();
    // int randomX = 0;
    // int randomY = 0;
    // SpielsteinS2 currentStein;
    // List<? extends Spielstein> allowedStates;
    // while (true) {
    // randomX = random.nextInt(spielfeld.getBreite() - 1);
    // randomY = random.nextInt(spielfeld.getHoehe() - 1);
    // currentStein = spielfeld.getSpielstein(randomX, randomY);
    // allowedStates = currentStein.listAvailableStates();
    // currentStein.setVisibleState(allowedStates.get(random
    // .nextInt(allowedStates.size() - 1)));
    // try {
    // Thread.sleep(2000);
    // } catch (InterruptedException e) {
    // e.printStackTrace();
    // }
    // }
    //
    // }
}
