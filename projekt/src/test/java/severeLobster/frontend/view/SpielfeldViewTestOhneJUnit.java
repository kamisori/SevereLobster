package severeLobster.frontend.view;

import java.awt.BorderLayout;
import java.util.List;
import java.util.Random;

import javax.swing.JFrame;

import severeLobster.backend.spiel.Spielfeld;
import severeLobster.backend.spiel.Spielstein;
import severeLobster.backend.spiel.SpielsteinState;

public class SpielfeldViewTestOhneJUnit {

	public static void main(String[] args) {
		final Spielfeld spielfeld = new Spielfeld(15, 10);

		final SpielfeldView view = new SpielfeldView(spielfeld);

		final JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(view, BorderLayout.CENTER);
		frame.pack();
		frame.setVisible(true);

		/**
		 * Ändere die Stati in spielstein.
		 */
		Random random = new Random();
		int randomX = 0;
		int randomY = 0;
		Spielstein currentStein;
		List<? extends SpielsteinState> allowedStates;
		while (true) {
			randomX = random.nextInt(spielfeld.getBreite()-1);
			randomY = random.nextInt(spielfeld.getLaenge()-1);
			currentStein = spielfeld.getSpielstein(randomX, randomY);
			allowedStates = currentStein.listAvailableStates();
			currentStein.setVisibleState(allowedStates.get(random
					.nextInt(allowedStates.size()-1)));
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}
}
