package severeLobster.frontend.view;

import infrastructure.ResourceManager;
import infrastructure.constants.enums.PfeilrichtungEnumeration;

import javax.swing.Icon;

/**
 * Factory Implementation fuer das Simple Icon Paket.
 * 
 * @author LKleiber
 * 
 */
public class SimpleIconFactory extends IconFactory {

    private static final SimpleIconFactory INSTANCE = new SimpleIconFactory();

    private final ResourceManager resourceManager = ResourceManager.get();

    /**
     * Quelle:
     * http://www.iconarchive.com/show/soft-scraps-icons-by-deleket/Button
     * -Download-icon.html
     */
    protected Icon pfeilSouthIcon;
    protected Icon pfeilSouthWestIcon;
    protected Icon pfeilWestIcon;
    protected Icon pfeilNorthWestIcon;
    protected Icon pfeilNorthIcon;
    protected Icon pfeilNorthEastIcon;
    protected Icon pfeilEastIcon;
    protected Icon pfeilSouthEastIcon;
    /**
     * Quelle:
     * http://www.iconarchive.com/show/colobrush-icons-by-eponas-deeway/system
     * -star-icon.html
     */
    protected Icon sternIcon;
    /**
     * Quelle:
     * http://www.iconarchive.com/show/button-icons-by-deleket/Button-Cancel
     * -icon.html
     */
    protected Icon ausschlussIcon;
    /**
     * Quelle:
     * http://www.iconarchive.com/show/soft-scraps-icons-by-deleket/Button
     * -Blank-Blue-icon.html
     */
    protected Icon blankIcon;

    protected SimpleIconFactory() {

        sternIcon = resourceManager.getImageIcon("SternIcon24.png");
        ausschlussIcon = resourceManager.getImageIcon("AusschlussIcon24.png");
        blankIcon = resourceManager.getImageIcon("BlankIcon24.png");
        pfeilSouthIcon = resourceManager.getImageIcon("PfeilIcon24.png");
        pfeilSouthWestIcon = new RotatedIcon(pfeilSouthIcon, 45);
        pfeilWestIcon = new RotatedIcon(pfeilSouthIcon, 90);
        pfeilNorthWestIcon = new RotatedIcon(pfeilSouthIcon, 135);
        pfeilNorthIcon = new RotatedIcon(pfeilSouthIcon, 180);
        pfeilNorthEastIcon = new RotatedIcon(pfeilSouthIcon, 225);
        pfeilEastIcon = new RotatedIcon(pfeilSouthIcon, 270);
        pfeilSouthEastIcon = new RotatedIcon(pfeilSouthIcon, 315);
    }

    public static SimpleIconFactory getInstance() {
        return INSTANCE;
    }

    @Override
    public Icon sternIcon() {
        return sternIcon;
    }

    @Override
    public Icon ausschlussIcon() {
        return ausschlussIcon;
    }

    @Override
    public Icon blankIcon() {
        return blankIcon;
    }

    @Override
    public Icon pfeilIcon(PfeilrichtungEnumeration richtung) {
        if (richtung == null) {
            return ausschlussIcon;
        }
        if (richtung == PfeilrichtungEnumeration.SUED) {
            return pfeilSouthIcon;
        }
        if (richtung == PfeilrichtungEnumeration.SUEDWEST) {
            return pfeilSouthWestIcon;
        }
        if (richtung == PfeilrichtungEnumeration.WEST) {
            return pfeilWestIcon;
        }
        if (richtung == PfeilrichtungEnumeration.NORDWEST) {
            return pfeilNorthWestIcon;
        }
        if (richtung == PfeilrichtungEnumeration.NORD) {
            return pfeilNorthIcon;
        }
        if (richtung == PfeilrichtungEnumeration.NORDOST) {
            return pfeilNorthEastIcon;
        }
        if (richtung == PfeilrichtungEnumeration.OST) {
            return pfeilEastIcon;
        }
        if (richtung == PfeilrichtungEnumeration.SUEDOST) {
            return pfeilSouthEastIcon;
        }
        return ausschlussIcon;
    }

}
