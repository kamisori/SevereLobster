package severeLobster.frontend.view;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.List;

import infrastructure.ResourceManager;

import javax.swing.ImageIcon;

/**
 * Icon Implementation, die das gezeichnete Icon dynamisch immer auf die Groesse
 * der Zielkomponente skaliert. Dabei koennen als Quelle mehrere Aufloesungen
 * desselben Bildes uebergeben werden, um den Skalierungsaufwand gering zu
 * halten.
 * 
 * 
 * @author Lutz Kleiber
 * 
 */
public class DynamischSkalierendesIcon extends ImageIcon {

    private final ResourceManager resourceManager = ResourceManager.get();
    private final ImageIconInVerschiedenenAufloesungen sourceIcons;
    /** Aendert sich je nach Ausmassen der Zielkomponente: */
    private ImageIcon scaledImageIcon;

    public DynamischSkalierendesIcon(final ImageIcon icon,
            final int defaultWidth, final int defaultHeight) {
        this(new ImageIcon[] { icon }, defaultWidth, defaultHeight);
    }

    public DynamischSkalierendesIcon(final List<BufferedImage> bufferedImages,
            final int defaultWidth, final int defaultHeight) {
        this(convertToArrayOfImageIcon(bufferedImages), defaultWidth,
                defaultHeight);
    }

    public DynamischSkalierendesIcon(
            final ImageIcon[] verschiedeneAufloesungen, final int defaultWidth,
            final int defaultHeight) {
        if (null == verschiedeneAufloesungen) {
            throw new NullPointerException(resourceManager.getText("exception.icon.array.is.null"));
        }
        this.sourceIcons = new ImageIconInVerschiedenenAufloesungen(
                verschiedeneAufloesungen);
        final ImageIcon naechstHoehereStartGroesse = sourceIcons
                .getNaechstHoehereAufloesung(defaultWidth);
        /**
         * Sofort in default Groesse skalieren, damit getIconWidth() und
         * getIconHeight() kalkulierbare Werte zurueckgeben.
         */
        this.scaledImageIcon = getSkaliertesImageIcon(
                naechstHoehereStartGroesse, defaultWidth, defaultHeight);

    }

    @Override
    public int getIconHeight() {
        return scaledImageIcon.getIconHeight();
    }

    @Override
    public int getIconWidth() {
        return scaledImageIcon.getIconWidth();
    }

    @Override
    public void paintIcon(Component targetComponent, Graphics g, int x, int y) {

        /*
         * Ausmasse von Zielkomponente auslesen und mit Ausmassen von
         * gespeichertem, skaliertem ImageIcon vergleichen:
         */
        int targetWidth = targetComponent.getWidth() - 2;
        int targetHeigth = targetComponent.getHeight() - 2;

        boolean groesseIstSchonIdentisch = (targetWidth == scaledImageIcon
                .getIconWidth() && targetHeigth == scaledImageIcon
                .getIconHeight());

        /*
         * Wenn aktuelle Groesse bereits Zielgroesse entspricht, direkt zeichnen
         * -> sollte nach dem ersten laden staendig vorkommen. Ansonsten
         * skalieren:
         */
        if (!groesseIstSchonIdentisch) {
            // System.out.println("ImageIcon neu skalieren");
            /*
             * x und y fuer neue Groesse anpassen, da das Icon sonst nach dem
             * ersten Skalieren verschoben gezeichnet wird. x und y muessen um
             * die haelfte des Zuwachses verringert werden, oder um die Haelfte
             * der Abnahme erhoeht werden:
             */
            /* Bei Verbreiterung: */
            if (targetWidth > getIconWidth()) {
                /* Zuwachs ausrechnen: */
                final int zuwachs = targetWidth - getIconWidth();
                final int halberZuwachs = zuwachs / 2;
                x = x - halberZuwachs;
            } else {
                /* bei Verschmaelerung */
                /* abnahme ausrechnen: */
                final int abnahme = getIconWidth() - targetWidth;
                final int halbeeabnahme = abnahme / 2;
                x = x + halbeeabnahme;
            }
            /* Bei Verlaengerung (vertikal) */
            if (targetHeigth > getIconHeight()) {
                /* Zuwachs ausrechnen: */
                final int zuwachs = targetHeigth - getIconHeight();
                final int halberZuwachs = zuwachs / 2;
                y = y - halberZuwachs;
            } else {
                /* Bei Verkuerzung: (vertikal) */
                /* Abnahme ausrechnen: */
                final int abnahme = getIconHeight() - targetHeigth;
                final int halbeeabnahme = abnahme / 2;
                y = y + halbeeabnahme;
            }

            /* Bisheriges skaliertes ImageIcon mit neuem ueberschreiben */
            final ImageIcon naechstHoehereAufloesung = sourceIcons
                    .getNaechstHoehereAufloesung(targetWidth);

            this.scaledImageIcon = getSkaliertesImageIcon(
                    naechstHoehereAufloesung, targetWidth, targetHeigth);
        }
        /* Hier hat scaledImage in jedem Fall die richtige Groesse */
        /* Wirkliches Zeichnen in Zielkomponente */
        scaledImageIcon.paintIcon(targetComponent, g, x, y);
    }

    private static ImageIcon getSkaliertesImageIcon(final ImageIcon sourceIcon,
            final int targetWidth, final int targetHeight) {

        System.out.println("skalliere Quellicon der Groesse "
                + sourceIcon.getIconWidth() + "X" + sourceIcon.getIconHeight()
                + " herunter auf " + targetWidth + "X" + targetHeight);
        /* Quellimage zum bearbeiten laden: */
        final Image sourceImage = sourceIcon.getImage();
        /* Skalierte Version erzeugen */
        final Image scaledImage = sourceImage.getScaledInstance(targetWidth,
                targetHeight, Image.SCALE_SMOOTH);
        /* Skaliertes Image als IconImage zurueckgeben */
        return new ImageIcon(scaledImage);
    }

    private static ImageIcon[] convertToArrayOfImageIcon(
            final List<BufferedImage> bufferedImages) {
        if (null == bufferedImages) {
            throw new NullPointerException("BufferedImage Array ist null");
        }
        final ImageIcon[] icons = new ImageIcon[bufferedImages.size()];
        for (int index = 0; index < bufferedImages.size(); index++) {
            icons[index] = new ImageIcon(bufferedImages.get(index));
        }
        return icons;
    }
}
