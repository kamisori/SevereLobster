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
public class AdvancedDynamicallyResizingIconFactory extends
        SimpleDynamicallyResizingIconFactory {

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

        final int defaultWidth = SimpleDynamicallyResizingIconFactory.DEFAULT_ICON_WIDTH;
        final int defaultHeight = SimpleDynamicallyResizingIconFactory.DEFAULT_ICON_HEIGHT;

        pfeilNorthIcon = new DynamischSkalierendesIcon(new ImageIcon(
                pfeilNorthBuffImage), defaultWidth, defaultHeight);
        pfeilNorthEastIcon = new DynamischSkalierendesIcon(new ImageIcon(
                pfeilNorthEastBuffImage), defaultWidth, defaultHeight);
        pfeilEastIcon = new DynamischSkalierendesIcon(new ImageIcon(
                getRotatedImageOf(pfeilNorthBuffImage, 90)), defaultWidth,
                defaultHeight);
        pfeilSouthEastIcon = new DynamischSkalierendesIcon(new ImageIcon(
                getRotatedImageOf(pfeilNorthEastBuffImage, 90)), defaultWidth,
                defaultHeight);

        pfeilSouthIcon = new DynamischSkalierendesIcon(new ImageIcon(
                getRotatedImageOf(pfeilNorthBuffImage, 180)), defaultWidth,
                defaultHeight);

        pfeilSouthWestIcon = new DynamischSkalierendesIcon(new ImageIcon(
                getRotatedImageOf(pfeilNorthEastBuffImage, 180)), defaultWidth,
                defaultHeight);
        pfeilWestIcon = new DynamischSkalierendesIcon(new ImageIcon(
                getRotatedImageOf(pfeilNorthBuffImage, 270)), defaultWidth,
                defaultHeight);
        pfeilNorthWestIcon = new DynamischSkalierendesIcon(new ImageIcon(
                getRotatedImageOf(pfeilNorthEastBuffImage, 270)), defaultWidth,
                defaultHeight);
        /**
         * TEMP, bis andere Icons fertig sind:
         */
        final BufferedImage sternGrossBuffImage;
        final BufferedImage ausschlussGrossBuffImage;
        final BufferedImage blankGrossBuffImage;
        try {
            sternGrossBuffImage = ResourceManager.get().getIconAsBufferedImage(
                    "SternIcon128.png");
            ausschlussGrossBuffImage = ResourceManager.get()
                    .getIconAsBufferedImage("AusschlussIcon128.png");
            blankGrossBuffImage = ResourceManager.get().getIconAsBufferedImage(
                    "BlankIcon128.png");
        } catch (IOException e) {
            e.printStackTrace();
            // Wenn das fehlschlaegt ist was kaputt
            throw new IllegalStateException(
                    "IconFactory kann nicht alle Bilder laden");
        }
        sternIcon = new DynamischSkalierendesIcon(new ImageIcon(
                sternGrossBuffImage), defaultWidth, defaultHeight);
        ausschlussIcon = new DynamischSkalierendesIcon(new ImageIcon(
                ausschlussGrossBuffImage), defaultWidth, defaultHeight);
        blankIcon = new DynamischSkalierendesIcon(new ImageIcon(
                blankGrossBuffImage), defaultWidth, defaultHeight);
        /**
         * ENDE TEMP
         */
    }

    public static AdvancedDynamicallyResizingIconFactory getInstance() {
        return INSTANCE;
    }
}
