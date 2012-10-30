package severeLobster.frontend.view;

import infrastructure.ResourceManager;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.swing.ImageIcon;

/**
 * IconFactory mit den neuen Icons. Funktioniert aber noch net.
 * 
 * @author Lutz Kleiber
 * 
 */
public class AdvancedDynamicallyResizingIconFactory extends SimpleIconFactory {

    private static final AdvancedDynamicallyResizingIconFactory INSTANCE = new AdvancedDynamicallyResizingIconFactory();

    protected AdvancedDynamicallyResizingIconFactory() {
        final BufferedImage pfeilNorthBuffImage;
        final BufferedImage pfeilNorthEastBuffImage;
        try {
            pfeilNorthBuffImage = ResourceManager.get().getIconAsBufferedImage(
                    "Pfeil_0.png");
            pfeilNorthEastBuffImage = ResourceManager.get()
                    .getIconAsBufferedImage("Pfeil_45.png");
        } catch (IOException e) {
            e.printStackTrace();
            // Wenn das fehlschlaegt ist was kaputt
            throw new IllegalStateException(
                    "IconFactory kann nicht alle Bilder laden");
        }

        pfeilNorthIcon = new DynamischSkalierendesIcon(new ImageIcon(
                pfeilNorthBuffImage));
        pfeilNorthEastIcon = new DynamischSkalierendesIcon(new ImageIcon(
                pfeilNorthEastBuffImage));
        pfeilEastIcon = new DynamischSkalierendesIcon(new ImageIcon(
                getRotatedImageOf(pfeilNorthBuffImage, 90)));
        pfeilSouthEastIcon = new DynamischSkalierendesIcon(new ImageIcon(
                getRotatedImageOf(pfeilNorthEastBuffImage, 90)));

        pfeilSouthIcon = new DynamischSkalierendesIcon(new ImageIcon(
                getRotatedImageOf(pfeilNorthBuffImage, 180)));

        pfeilSouthWestIcon = new DynamischSkalierendesIcon(new ImageIcon(
                getRotatedImageOf(pfeilNorthEastBuffImage, 180)));
        pfeilWestIcon = new DynamischSkalierendesIcon(new ImageIcon(
                getRotatedImageOf(pfeilNorthBuffImage, 270)));
        pfeilNorthWestIcon = new DynamischSkalierendesIcon(new ImageIcon(
                getRotatedImageOf(pfeilNorthEastBuffImage, 270)));

    }

    public static AdvancedDynamicallyResizingIconFactory getInstance() {
        return INSTANCE;
    }
}
