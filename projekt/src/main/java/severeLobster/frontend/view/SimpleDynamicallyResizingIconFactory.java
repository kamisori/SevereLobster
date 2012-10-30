package severeLobster.frontend.view;

import infrastructure.graphics.icons.SimpleDynamicallyResizingIconPackage;
import infrastructure.graphics.icons.SimpleIconPackage;

/**
 * Icon factory, die dynamisch skalierence Icons zurueckgibt. Die Icons
 * skalieren sich immer zur Groesse der Zielkomponente.
 * 
 * @author Lutz Kleiber
 * 
 */
public class SimpleDynamicallyResizingIconFactory extends IconFactory {

    private static final SimpleDynamicallyResizingIconFactory INSTANCE = new SimpleDynamicallyResizingIconFactory();

    private SimpleDynamicallyResizingIconFactory() {
        super(SimpleDynamicallyResizingIconPackage.getInstance());
    }

    public static SimpleDynamicallyResizingIconFactory getInstance() {
        return INSTANCE;

    }

}
