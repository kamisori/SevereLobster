package severeLobster.frontend.view;

import severeLobster.backend.spiel.Spielstein;
import severeLobster.backend.spiel.SpielsteinState;
import severeLobster.frontend.controller.SpielsteinController;

import javax.swing.JFrame;
import java.awt.BorderLayout;
import java.util.List;

public class SpielsteinViewTestOhneJUnit {

	public static void main(String[] args) {
		final Spielstein spielstein = new Spielstein();

		final SpielsteinViewImpl view = new SpielsteinViewImpl();
		new SpielsteinController(view, spielstein);

		final JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(view, BorderLayout.CENTER);
		frame.pack();
		frame.setVisible(true);

		/**
		 * �ndere die Stati in spielstein.
		 */
		final List<? extends SpielsteinState> allowedStates = spielstein
				.listAvailableStates();
		for (SpielsteinState currentState : allowedStates) {
			spielstein.setVisibleState(currentState);
			System.out.println("Status geaendert in:" + currentState);
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}
}