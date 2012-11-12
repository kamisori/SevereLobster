package severeLobster.frontend.view;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;

import infrastructure.ResourceManager;
import infrastructure.constants.enums.PfeilrichtungEnumeration;

import javax.swing.Icon;
import javax.swing.ImageIcon;

/**
 * Factory Implementation fuer das Simple Icon Paket.
 * 
 * @author LKleiber
 * 
 */
public class SimpleIconFactory extends IconFactory {

    private static final SimpleIconFactory INSTANCE = new SimpleIconFactory();

    private final ResourceManager resourceManager = ResourceManager.get();

    /**
     * Quelle:
     * http://www.iconarchive.com/show/colobrush-icons-by-eponas-deeway/system
     * -star-icon.html
     */
    protected ImageIcon sternIcon;
    /**
     * Quelle:
     * http://www.iconarchive.com/show/button-icons-by-deleket/Button-Cancel
     * -icon.html
     */
    protected ImageIcon ausschlussIcon;
    /**
     * Quelle:
     * http://www.iconarchive.com/show/soft-scraps-icons-by-deleket/Button
     * -Blank-Blue-icon.html
     */
    protected ImageIcon blankIcon;

    /**
     * Quelle:
     * http://www.iconarchive.com/show/soft-scraps-icons-by-deleket/Button
     * -Download-icon.html
     */
    protected ImageIcon pfeilSouthIcon;
    protected ImageIcon pfeilSouthWestIcon;
    protected ImageIcon pfeilWestIcon;
    protected ImageIcon pfeilNorthWestIcon;
    protected ImageIcon pfeilNorthIcon;
    protected ImageIcon pfeilNorthEastIcon;
    protected ImageIcon pfeilEastIcon;
    protected ImageIcon pfeilSouthEastIcon;

    protected SimpleIconFactory() {

        sternIcon = resourceManager.getImageIcon("SternIcon24.png");
        ausschlussIcon = resourceManager.getImageIcon("AusschlussIcon24.png");
        blankIcon = resourceManager.getImageIcon("BlankIcon24.png");

        final BufferedImage pfeilSouthBuffImage;
        try {
            pfeilSouthBuffImage = resourceManager
                    .getIconAsBufferedImage("PfeilIcon24.png");
        } catch (IOException e) {
            e.printStackTrace();
            // Wenn das fehlschlaegt ist was kaputt
            throw new IllegalStateException(
                    resourceManager.getText("exception.simple.icon.factory.cannot.load.all.pictures"));
        }

        pfeilSouthIcon = new ImageIcon(pfeilSouthBuffImage);
        pfeilSouthWestIcon = new ImageIcon(getRotatedImageOf(
                pfeilSouthBuffImage, 45));
        pfeilWestIcon = new ImageIcon(
                getRotatedImageOf(pfeilSouthBuffImage, 90));
        pfeilNorthWestIcon = new ImageIcon(getRotatedImageOf(
                pfeilSouthBuffImage, 135));
        pfeilNorthIcon = new ImageIcon(getRotatedImageOf(pfeilSouthBuffImage,
                180));
        pfeilNorthEastIcon = new ImageIcon(getRotatedImageOf(
                pfeilSouthBuffImage, 225));
        pfeilEastIcon = new ImageIcon(getRotatedImageOf(pfeilSouthBuffImage,
                270));
        pfeilSouthEastIcon = new ImageIcon(getRotatedImageOf(
                pfeilSouthBuffImage, 315));

    }

    public static SimpleIconFactory getInstance() {
        return INSTANCE;
    }

    @Override
    public Icon sternIcon() {
        return sternIcon;
    }

    @Override
    public Icon ausschlussIcon() {
        return ausschlussIcon;
    }

    @Override
    public Icon blankIcon() {
        return blankIcon;
    }

    @Override
    public Icon pfeilIcon(PfeilrichtungEnumeration richtung) {
        if (richtung == null) {
            return ausschlussIcon;
        }
        if (richtung == PfeilrichtungEnumeration.SUED) {
            return pfeilSouthIcon;
        }
        if (richtung == PfeilrichtungEnumeration.SUEDWEST) {
            return pfeilSouthWestIcon;
        }
        if (richtung == PfeilrichtungEnumeration.WEST) {
            return pfeilWestIcon;
        }
        if (richtung == PfeilrichtungEnumeration.NORDWEST) {
            return pfeilNorthWestIcon;
        }
        if (richtung == PfeilrichtungEnumeration.NORD) {
            return pfeilNorthIcon;
        }
        if (richtung == PfeilrichtungEnumeration.NORDOST) {
            return pfeilNorthEastIcon;
        }
        if (richtung == PfeilrichtungEnumeration.OST) {
            return pfeilEastIcon;
        }
        if (richtung == PfeilrichtungEnumeration.SUEDOST) {
            return pfeilSouthEastIcon;
        }
        return ausschlussIcon;
    }

    /***
     * Ganz dreist geklaut.
     * http://www.tutorials.de/java/238054-bild-drehen.html#post1241082
     * 
     * @param src
     * @param degrees
     * @return
     */
    protected static BufferedImage getRotatedImageOf(BufferedImage src,
            double degrees) {
        AffineTransform affineTransform = AffineTransform.getRotateInstance(
                Math.toRadians(degrees), src.getWidth() / 2,
                src.getHeight() / 2);

        // TODO
        // Quickfix
        int imageType = src.getType();
        if (imageType == 0) {
            imageType = 6;
        }
        BufferedImage rotatedImage = new BufferedImage(src.getWidth(),
                src.getHeight(), imageType);
        Graphics2D g = (Graphics2D) rotatedImage.getGraphics();
        g.setTransform(affineTransform);
        g.drawImage(src, 0, 0, null);
        return rotatedImage;
    }

}
