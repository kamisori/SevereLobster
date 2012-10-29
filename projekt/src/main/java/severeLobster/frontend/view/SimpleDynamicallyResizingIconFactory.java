package severeLobster.frontend.view;

/**
 * Icon factory, die dynamisch skalierence Icons zurueckgibt. Die Icons
 * skalieren sich immer zur Groesse der Zielkomponente.
 * 
 * @author Lutz Kleiber
 * 
 */
public class SimpleDynamicallyResizingIconFactory extends SimpleIconFactory {

    private static final SimpleDynamicallyResizingIconFactory INSTANCE = new SimpleDynamicallyResizingIconFactory();

    protected SimpleDynamicallyResizingIconFactory() {
        // Funktioniert im moment net
        // sternIcon = new DynamischSkalierendesIcon(sternIcon);
        // ausschlussIcon = new DynamischSkalierendesIcon(ausschlussIcon);
        // blankIcon = new DynamischSkalierendesIcon(blankIcon);
        // pfeilSouthIcon = new DynamischSkalierendesIcon(pfeilSouthIcon);
        // pfeilSouthWestIcon = new
        // DynamischSkalierendesIcon(pfeilSouthWestIcon);
        // pfeilWestIcon = new DynamischSkalierendesIcon(pfeilWestIcon);
        // pfeilNorthWestIcon = new
        // DynamischSkalierendesIcon(pfeilNorthWestIcon);
        // pfeilNorthIcon = new DynamischSkalierendesIcon(pfeilNorthIcon);
        // pfeilNorthEastIcon = new
        // DynamischSkalierendesIcon(pfeilNorthEastIcon);
        // pfeilEastIcon = new DynamischSkalierendesIcon(pfeilEastIcon);
        // pfeilSouthEastIcon = new
        // DynamischSkalierendesIcon(pfeilSouthEastIcon);
    }

    public static SimpleDynamicallyResizingIconFactory getInstance() {
        return INSTANCE;

    }

}
