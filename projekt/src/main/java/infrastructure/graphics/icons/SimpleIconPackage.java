package infrastructure.graphics.icons;

import infrastructure.ResourceManager;

import javax.swing.Icon;

/**
 * Icon Paket mit doofen Icons.
 * 
 * @author Lutz Kleiber
 * 
 */
public class SimpleIconPackage implements IIconPackage {

    private static final SimpleIconPackage INSTANCE = new SimpleIconPackage();

    private final ResourceManager resourceManager = ResourceManager.get();

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

    private SimpleIconPackage() {

        sternIcon = resourceManager.getImageIcon("SternIcon24.png");
        ausschlussIcon = resourceManager.getImageIcon("AusschlussIcon24.png");
        blankIcon = resourceManager.getImageIcon("BlankIcon24.png");
        pfeilIcon = resourceManager.getImageIcon("PfeilIcon24.png");

    }

    public static SimpleIconPackage getInstance() {
        return INSTANCE;
    }

    public Icon pfeilIcon() {
        return pfeilIcon;
    }

    public Icon sternIcon() {
        return sternIcon;
    }

    public Icon ausschlussIcon() {
        return ausschlussIcon;
    }

    public Icon blankIcon() {
        return blankIcon;
    }

}
