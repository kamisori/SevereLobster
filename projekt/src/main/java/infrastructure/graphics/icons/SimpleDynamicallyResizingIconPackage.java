package infrastructure.graphics.icons;

import infrastructure.ResourceManager;

import javax.swing.Icon;

import severeLobster.frontend.view.DynamischSkalierendesIcon;

/**
 * Icon Paket mit doofen Icons, die sich auf die Groesse der Zielkomponente
 * skalieren.
 * 
 * @author Lutz Kleiber
 * 
 */
public class SimpleDynamicallyResizingIconPackage implements IIconPackage {

    private static final SimpleDynamicallyResizingIconPackage INSTANCE = new SimpleDynamicallyResizingIconPackage();

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

    private SimpleDynamicallyResizingIconPackage() {

        sternIcon = new DynamischSkalierendesIcon(
                resourceManager.getImageIcon("SternIcon24.png"));
        ausschlussIcon = new DynamischSkalierendesIcon(
                resourceManager.getImageIcon("AusschlussIcon24.png"));
        blankIcon = new DynamischSkalierendesIcon(
                resourceManager.getImageIcon("BlankIcon24.png"));
        pfeilIcon = new DynamischSkalierendesIcon(
                resourceManager.getImageIcon("PfeilIcon24.png"));

    }

    public static SimpleDynamicallyResizingIconPackage getInstance() {
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
