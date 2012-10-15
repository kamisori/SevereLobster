package severeLobster.frontend.view;

import infrastructure.graphics.icons.IIconPackage;
import severeLobster.backend.spiel.Ausschluss;
import severeLobster.backend.spiel.Pfeil;
import severeLobster.backend.spiel.Spielstein;
import severeLobster.backend.spiel.Stern;

import javax.swing.Icon;

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

    public final Icon getIconForState(final Spielstein state) {
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
            //TODO Hier zwischen versch. Richtungen unterscheiden
            return iconPackage.pfeilIcon();
        }
        return iconPackage.blankIcon();
    }
}
