package infrastructure.graphics.simpleIcons;

import infrastructure.graphics.IIconPackage;

import javax.swing.Icon;
import javax.swing.ImageIcon;

/**
 * TODO Kommentar
 * 
 * @author LKleiber
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

		Class<? extends SimpleIconPackage> currentClass = getClass();
		sternIcon = new ImageIcon(currentClass.getResource("SternIcon24.png"));
		ausschlussIcon = new ImageIcon(
				currentClass.getResource("AusschlussIcon24.png"));
		blankIcon = new ImageIcon(currentClass.getResource("BlankIcon24.png"));
		pfeilIcon = new ImageIcon(currentClass.getResource("PfeilIcon24.png"));

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
