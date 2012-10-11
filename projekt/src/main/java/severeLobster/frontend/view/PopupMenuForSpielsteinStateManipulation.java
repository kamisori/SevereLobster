package severeLobster.frontend.view;

import severeLobster.backend.spiel.SpielsteinState;
import severeLobster.frontend.controller.SpielsteinController;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * Popup Maus Menu zum Editieren des SpielsteinState im Editier Modus dies
 * Spiels.
 * 
 * @author Lutz Kleiber
 * 
 */
public class PopupMenuForSpielsteinStateManipulation extends JPopupMenu {

	public PopupMenuForSpielsteinStateManipulation(
			final SpielsteinController spielsteinController,
			List<? extends SpielsteinState> choosableStates) {

		JMenuItem menuItem;

		/**
		 * Durchlaufe die fuer diesen Spielstein moeglichen Stati und stelle jeden
		 * dieser Stati mit einem Icon und Text zur Auswahl dar.
		 */
		for (SpielsteinState currentState : choosableStates) {
			/** Hole Icon und Namen fuer diesen SpielsteinState: */

			menuItem = new JMenuItem(currentState.toString(), SimpleIconFactory
					.getInstance().getIconForState(currentState));
			/**
			 * Sinn dieser Variable ist es der Anonymen Klasse eine Konstante
			 * mitzugeben:
			 */
			final SpielsteinState finalCurrentState = currentState;
			menuItem.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent e) {
					/**
					 * Bei Klick auf den Status soll eine Statusaenderung hierhin
					 * gemacht werden.
					 */
					spielsteinController.setState(finalCurrentState);
				}
			});
			this.add(menuItem);
		}

	}
}
