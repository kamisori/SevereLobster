package severeLobster.frontend.view;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import severeLobster.backend.spiel.Ausschluss;
import severeLobster.backend.spiel.Pfeil;
import severeLobster.backend.spiel.SpielsteinState;
import severeLobster.backend.spiel.Stern;

public class IconFactory {

	private static final IconFactory INSTANCE = new IconFactory();
	/**
	 * Quelle:
	 * http://www.iconarchive.com/show/soft-scraps-icons-by-deleket/Button
	 * -Download-icon.html
	 */
	private final Icon pfeilIcon;
	/**
	 * Quelle:
	 * http://www.iconarchive.com/show/colobrush-icons-by-eponas-deeway/system
	 * -star-icon.html
	 */
	private final Icon sternIcon;
	/**
	 * Quelle:
	 * http://www.iconarchive.com/show/button-icons-by-deleket/Button-Cancel
	 * -icon.html
	 */
	private final Icon ausschlussIcon;
	/**
	 * Quelle:
	 * http://www.iconarchive.com/show/soft-scraps-icons-by-deleket/Button
	 * -Blank-Blue-icon.html
	 */
	private final Icon blankIcon;

	private IconFactory() {

		Class<? extends IconFactory> currentClass = getClass();
		sternIcon = new ImageIcon(
				currentClass.getResource("icons/SternIcon24.png"));
		ausschlussIcon = new ImageIcon(
				currentClass.getResource("icons/AusschlussIcon24.png"));
		blankIcon = new ImageIcon(
				currentClass.getResource("icons/BlankIcon24.png"));
		pfeilIcon = new ImageIcon(
				currentClass.getResource("icons/PfeilIcon24.png"));

	}

	public static IconFactory getInstance() {
		return INSTANCE;

	}

	public Icon getIconForState(final SpielsteinState state) {
		if (null == state) {
			return blankIcon;
		}
		if (state instanceof Stern) {
			return sternIcon;
		}
		if (state instanceof Ausschluss) {
			return ausschlussIcon;
		}
		if (state instanceof Pfeil) {
			return pfeilIcon;
		}
		return blankIcon;
	}

}
