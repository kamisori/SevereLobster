package infrastructure.graphics;

import javax.swing.Icon;

/**
 * Schnittstelle um verschiedene Iconpakete bereitzustellen, so dass zwischen
 * "Themes" gewechselt werden kann.
 * 
 * @author LKleiber
 * 
 */
public interface IIconPackage {

	public Icon sternIcon();

	public Icon pfeilIcon();

	public Icon ausschlussIcon();

	public Icon blankIcon();
}
