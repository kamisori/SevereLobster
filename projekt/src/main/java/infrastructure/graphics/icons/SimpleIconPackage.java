package infrastructure.graphics.icons;

import infrastructure.graphics.GraphicsGetter;
import infrastructure.graphics.IIconPackage;

import javax.swing.Icon;
import javax.swing.ImageIcon;

/**
 * Icon Paket mit doofen Icons.
 * 
 * @author Lutz Kleiber
 * 
 */
public class SimpleIconPackage implements IIconPackage {

    private static final SimpleIconPackage INSTANCE = new SimpleIconPackage();

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

        sternIcon = new ImageIcon(GraphicsGetter.getIcon("SternIcon24.png"));
        ausschlussIcon = new ImageIcon(
                GraphicsGetter.getIcon("AusschlussIcon24.png"));
        blankIcon = new ImageIcon(GraphicsGetter.getIcon("BlankIcon24.png"));
        pfeilIcon = new ImageIcon(GraphicsGetter.getIcon("PfeilIcon24.png"));

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
