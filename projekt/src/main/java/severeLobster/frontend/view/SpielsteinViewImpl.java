package severeLobster.frontend.view;

import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

import severeLobster.backend.spiel.NullState;
import severeLobster.backend.spiel.Spielstein;
import severeLobster.backend.spiel.SpielsteinState;
import severeLobster.frontend.controller.SpielsteinController;

public class SpielsteinViewImpl extends JLabel implements
		IControllableSpielsteinView {

	private static final IconFactory ICON_FACTORY = IconFactory.getInstance();
	private SpielsteinController spielsteinController;

	public SpielsteinViewImpl() {
		final Icon newIcon = ICON_FACTORY.getIconForState(NullState
				.getInstance());
		this.setIcon(newIcon);
	}

	public void setDisplayedState(final SpielsteinState newDisplayedState) {
		final Icon newIcon = ICON_FACTORY.getIconForState(newDisplayedState);
		SwingUtilities.invokeLater(new Runnable() {

			public void run() {
				setIcon(newIcon);
			}
		});
	}

	public void setSpielsteinController(
			SpielsteinController spielsteinController) {
		this.spielsteinController = spielsteinController;
		/**
		 * Aktualisiere direkt den dargestellten Zustand, da nun der wirkliche
		 * Zustand abgefragt werden kann.
		 */
		final SpielsteinState currentState = spielsteinController.getState();
		setDisplayedState(currentState);
	}

}
