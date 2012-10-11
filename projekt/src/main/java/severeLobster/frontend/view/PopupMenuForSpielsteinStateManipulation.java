package severeLobster.frontend.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import severeLobster.backend.spiel.SpielsteinState;
import severeLobster.frontend.controller.SpielsteinController;

/**
 * TODO KOmmt noch
 * @author Lutz Kleiber
 *
 */
public class PopupMenuForSpielsteinStateManipulation extends JPopupMenu {

	public PopupMenuForSpielsteinStateManipulation(
			final SpielsteinController spielsteinController,
			List<? extends SpielsteinState> choosableStates) {

		JMenuItem menuItem;

		for (SpielsteinState currentState : choosableStates) {
			menuItem = new JMenuItem(currentState.toString(), SimpleIconFactory
					.getInstance().getIconForState(currentState));
			final SpielsteinState finalCurrentState = currentState;
			menuItem.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent e) {
					spielsteinController.setState(finalCurrentState);
				}
			});
			this.add(menuItem);
		}

	}
}