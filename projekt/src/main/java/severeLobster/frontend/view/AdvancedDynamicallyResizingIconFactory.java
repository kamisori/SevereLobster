package severeLobster.frontend.view;

import infrastructure.ResourceManager;

import javax.swing.ImageIcon;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

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

        final int defaultWidth = SimpleDynamicallyResizingIconFactory.DEFAULT_ICON_WIDTH;
        final int defaultHeight = SimpleDynamicallyResizingIconFactory.DEFAULT_ICON_HEIGHT;

        try {
            /* Nord Pfeile in verschiedenen Aufloesungen */
            final BufferedImage pfeilNord64 = ResourceManager.get()
                    .getIconAsBufferedImage("Pfeil_0_64.png");
            final BufferedImage pfeilNord128 = ResourceManager.get()
                    .getIconAsBufferedImage("Pfeil_0_128.png");
            final BufferedImage pfeilNord256 = ResourceManager.get()
                    .getIconAsBufferedImage("Pfeil_0_256.png");
            final BufferedImage pfeilNord512 = ResourceManager.get()
                    .getIconAsBufferedImage("Pfeil_0.png");

            /* Nord Ost Pfeile in verschiedenen Aufloesungen */
            final BufferedImage pfeilNordOst64 = ResourceManager.get()
                    .getIconAsBufferedImage("Pfeil_45_64.png");
            final BufferedImage pfeilNordOst128 = ResourceManager.get()
                    .getIconAsBufferedImage("Pfeil_45_128.png");
            final BufferedImage pfeilNordOst256 = ResourceManager.get()
                    .getIconAsBufferedImage("Pfeil_45_256.png");
            final BufferedImage pfeilNordOst512 = ResourceManager.get()
                    .getIconAsBufferedImage("Pfeil_45.png");

            /* Stern Icons in verschiedenen Aufloesungen */
            final BufferedImage stern64 = ResourceManager.get()
                    .getIconAsBufferedImage("Stern_64.png");
            final BufferedImage stern128 = ResourceManager.get()
                    .getIconAsBufferedImage("Stern_128.png");
            final BufferedImage stern256 = ResourceManager.get()
                    .getIconAsBufferedImage("Stern_256.png");
            final BufferedImage stern512 = ResourceManager.get()
                    .getIconAsBufferedImage("Stern.png");

            /* Ausschluss Icons in verschiedenen Aufloesungen */
            final BufferedImage ausschluss64 = ResourceManager.get()
                    .getIconAsBufferedImage("Ausschluss_64.png");
            final BufferedImage ausschluss128 = ResourceManager.get()
                    .getIconAsBufferedImage("Ausschluss_128.png");
            final BufferedImage ausschluss256 = ResourceManager.get()
                    .getIconAsBufferedImage("Ausschluss_256.png");
            final BufferedImage ausschluss512 = ResourceManager.get()
                    .getIconAsBufferedImage("Ausschluss.png");

            /* KeinStein Icons in verschiedenen Aufloesungen */
            final BufferedImage keinStein64 = ResourceManager.get()
                    .getIconAsBufferedImage("Blank_64.png");
            final BufferedImage keinStein128 = ResourceManager.get()
                    .getIconAsBufferedImage("Blank_128.png");
            final BufferedImage keinStein256 = ResourceManager.get()
                    .getIconAsBufferedImage("Blank_256.png");
            final BufferedImage keinStein512 = ResourceManager.get()
                    .getIconAsBufferedImage("Blank.png");

            /* Setze allgemeines pfeilNorthIcon als dynamisch skalierend */
            {
                final List<BufferedImage> pfeilNordImages = getRotatedImageListOf(
                        0, pfeilNord64, pfeilNord128, pfeilNord256,
                        pfeilNord512);
                pfeilNorthIcon = new DynamischSkalierendesIcon(pfeilNordImages,
                        defaultWidth, defaultHeight);
            }

            /* Setze allgemeines pfeilNorthEastIcon als dynamisch skalierend */
            {
                final List<BufferedImage> pfeilNordOstImages = getRotatedImageListOf(
                        0, pfeilNordOst64, pfeilNordOst128, pfeilNordOst256,
                        pfeilNordOst512);
                pfeilNorthEastIcon = new DynamischSkalierendesIcon(
                        pfeilNordOstImages, defaultWidth, defaultHeight);
            }

            /* Setze allgemeines pfeilEastIcon als dynamisch skalierend */
            {
                final List<BufferedImage> pfeilOstImages = getRotatedImageListOf(
                        90, pfeilNord64, pfeilNord128, pfeilNord256,
                        pfeilNord512);
                pfeilEastIcon = new DynamischSkalierendesIcon(pfeilOstImages,
                        defaultWidth, defaultHeight);
            }

            /* Setze allgemeines pfeilSouthEastIcon als dynamisch skalierend */
            {
                final List<BufferedImage> pfeilSuedOstImages = getRotatedImageListOf(
                        90, pfeilNordOst64, pfeilNordOst128, pfeilNordOst256,
                        pfeilNordOst512);
                pfeilSouthEastIcon = new DynamischSkalierendesIcon(
                        pfeilSuedOstImages, defaultWidth, defaultHeight);
            }

            /* Setze allgemeines pfeilSouthIcon als dynamisch skalierend */
            {
                final List<BufferedImage> pfeilSuedImages = getRotatedImageListOf(
                        180, pfeilNord64, pfeilNord128, pfeilNord256,
                        pfeilNord512);
                pfeilSouthIcon = new DynamischSkalierendesIcon(pfeilSuedImages,
                        defaultWidth, defaultHeight);
            }

            /* Setze allgemeines pfeilSouthWestIcon als dynamisch skalierend */
            {
                final List<BufferedImage> pfeilSuedWestImages = getRotatedImageListOf(
                        180, pfeilNordOst64, pfeilNordOst128, pfeilNordOst256,
                        pfeilNordOst512);
                pfeilSouthWestIcon = new DynamischSkalierendesIcon(
                        pfeilSuedWestImages, defaultWidth, defaultHeight);
            }

            /* Setze allgemeines pfeilWestIcon als dynamisch skalierend */
            {
                final List<BufferedImage> pfeilWestImages = getRotatedImageListOf(
                        270, pfeilNord64, pfeilNord128, pfeilNord256,
                        pfeilNord512);
                pfeilWestIcon = new DynamischSkalierendesIcon(pfeilWestImages,
                        defaultWidth, defaultHeight);
            }

            /* Setze allgemeines pfeilNorthWestIcon als dynamisch skalierend */
            {
                final List<BufferedImage> pfeilNordWestImages = getRotatedImageListOf(
                        270, pfeilNordOst64, pfeilNordOst128, pfeilNordOst256,
                        pfeilNordOst512);
                pfeilNorthWestIcon = new DynamischSkalierendesIcon(
                        pfeilNordWestImages, defaultWidth, defaultHeight);
            }

            /* Setze allgemeines SternIcon als dynamisch skalierend */
            {
                final List<BufferedImage> sternImages = getRotatedImageListOf(
                        0, stern64, stern128, stern256, stern512);
                sternIcon = new DynamischSkalierendesIcon(sternImages,
                        defaultWidth, defaultHeight);
            }

            /* Setze allgemeines ausschlussIcon als dynamisch skalierend */
            {
                final List<BufferedImage> ausschlussImages = getRotatedImageListOf(
                        0, ausschluss64, ausschluss128, ausschluss256,
                        ausschluss512);
                ausschlussIcon = new DynamischSkalierendesIcon(
                        ausschlussImages, defaultWidth, defaultHeight);
            }

            /* Setze allgemeines blankIcon als dynamisch skalierend */
            {
                final List<BufferedImage> blankImages = getRotatedImageListOf(
                        0, keinStein64, keinStein128, keinStein256,
                        keinStein512);
                blankIcon = new DynamischSkalierendesIcon(blankImages,
                        defaultWidth, defaultHeight);
            }

        } catch (IOException e) {
            e.printStackTrace();
            // Wenn das fehlschlaegt ist was kaputt
            throw new IllegalStateException(
                    "IconFactory kann nicht alle Bilder laden");
        }

    }

    private static List<BufferedImage> getRotatedImageListOf(final int degree,
            BufferedImage... images) {
        final List<BufferedImage> result = new LinkedList<BufferedImage>();
        for (BufferedImage currentImage : images) {
            result.add(getRotatedImageOf(currentImage, degree));
        }

        return result;
    }

    public static AdvancedDynamicallyResizingIconFactory getInstance() {
        return INSTANCE;
    }
}
