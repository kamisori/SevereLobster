package severeLobster.frontend.view;

import infrastructure.graphics.simpleIcons.SimpleIconPackage;

/**
 * Factory Implementation fuer das Simple Icon Paket
 * 
 * @author LKleiber
 * 
 */
public class SimpleIconFactory extends IconFactory {

    private static final SimpleIconFactory INSTANCE = new SimpleIconFactory();

    private SimpleIconFactory() {
        super(SimpleIconPackage.getInstance());
    }

    public static SimpleIconFactory getInstance() {
        return INSTANCE;

    }

}
