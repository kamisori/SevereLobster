package severeLobster.frontend.view;

import infrastructure.constants.enums.PfeilrichtungEnumeration;
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
            // TODO Hier zwischen versch. Richtungen unterscheiden

            if (((Pfeil) state).getPfeilrichtung() == PfeilrichtungEnumeration.SUED) {
                return new RotatedIcon(iconPackage.pfeilIcon(), 0);
            }
            if (((Pfeil) state).getPfeilrichtung() == PfeilrichtungEnumeration.SUEDWEST) {
                return new RotatedIcon(iconPackage.pfeilIcon(), 45);
            }
            if (((Pfeil) state).getPfeilrichtung() == PfeilrichtungEnumeration.WEST) {
                return new RotatedIcon(iconPackage.pfeilIcon(), 90);
            }
            if (((Pfeil) state).getPfeilrichtung() == PfeilrichtungEnumeration.NORDWEST) {
                return new RotatedIcon(iconPackage.pfeilIcon(), 135);
            }

            if (((Pfeil) state).getPfeilrichtung() == PfeilrichtungEnumeration.NORD) {
                return new RotatedIcon(iconPackage.pfeilIcon(), 180);
            }
            if (((Pfeil) state).getPfeilrichtung() == PfeilrichtungEnumeration.NORDOST) {
                return new RotatedIcon(iconPackage.pfeilIcon(), 225);
            }
            if (((Pfeil) state).getPfeilrichtung() == PfeilrichtungEnumeration.OST) {
                return new RotatedIcon(iconPackage.pfeilIcon(), 270);
            }
            if (((Pfeil) state).getPfeilrichtung() == PfeilrichtungEnumeration.SUEDOST) {
                return new RotatedIcon(iconPackage.pfeilIcon(), 315);
            }

        }
        return iconPackage.blankIcon();
    }
}
