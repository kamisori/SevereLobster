package severeLobster.frontend.view;

import infrastructure.graphics.IIconPackage;

import javax.swing.Icon;

import severeLobster.backend.spiel.Ausschluss;
import severeLobster.backend.spiel.Pfeil;
import severeLobster.backend.spiel.SpielsteinState;
import severeLobster.backend.spiel.Stern;

/**
 * Abstract Factory zur Anforderung Icons.
 * 
 * @author LKleiber
 * 
 */
public abstract class IconFactory {

	private final IIconPackage iconPackage;

	public IconFactory(final IIconPackage iconPackage) {
		this.iconPackage = iconPackage;
	}

	public final Icon getIconForState(final SpielsteinState state) {
		if (null == state) {
			return iconPackage.blankIcon();
		}
		if (state instanceof Stern) {
			return iconPackage.sternIcon();
		}
		if (state instanceof Ausschluss) {
			return iconPackage.ausschlussIcon();
		}
		if (state instanceof Pfeil) {
			return iconPackage.pfeilIcon();
		}
		return iconPackage.blankIcon();
	}
}
