package severeLobster.frontend.view;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.Icon;
import javax.swing.ImageIcon;

/**
 * Icon Implementation, die das gezeichnete Icon dynamisch immer auf die Groesse
 * der Zielkomponente skaliert.
 * 
 * Rohversion - koennen wir nachher noch mit den verschieden grossen Icons
 * ergaenzen, so dass immer die bestmoegliche Aufloesung angezeigt wird.
 * 
 * @author Lutz Kleiber
 * 
 */
public class DynamischSkalierendesIcon implements Icon {

    private final ImageIcon sourceIcon;
    /** Aendert sich je nach Ausmassen der Zielkomponente: */
    private ImageIcon scaledImageIcon;

    public DynamischSkalierendesIcon(final ImageIcon icon) {
        this.sourceIcon = icon;
        this.scaledImageIcon = icon;
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

        /**
         * Ausmasse von Zielkomponente auslesen und mit Ausmassen von
         * gespeichertem, skaliertem ImageIcon vergleichen:
         */
        int targetWidth = targetComponent.getWidth() - 2;
        int targetHeigth = targetComponent.getHeight() - 2;

        boolean groesseIstSchonIdentisch = (targetWidth == scaledImageIcon
                .getIconWidth() && targetHeigth == scaledImageIcon
                .getIconHeight());
        // System.out.println("x=" + x);
        // System.out.println("y=" + y);
        // System.out.println("targetW:" + targetWidth);
        // System.out.println("targetH:" + targetHeigth);
        // System.out.println("currentWidth:" + getIconWidth());
        // System.out.println("currentHeigth:" + getIconHeight());
        // System.out.println("*****************************");

        // Koordinaten muessen um die haelfte des Zuwachses verringert werden:
        // x=23
        // y=23
        // targetW:69
        // targetH:69
        // currentWidth:24
        // currentHeigth:24
        // *****************************
        // ImageIcon neu skalieren
        // x=1
        // y=1
        // targetW:69
        // targetH:69
        // currentWidth:69
        // currentHeigth:69
        // *****************************

        /**
         * Wenn aktuelle Groesse bereits Zielgroesse entspricht, direkt zeichnen
         * -> sollte nach dem ersten laden staendig vorkommen. Ansonsten
         * skalieren:
         */
        if (!groesseIstSchonIdentisch) {
            System.out.println("ImageIcon neu skalieren");
            /**
             * Schneller Bugfix: x und y fuer neue Groesse anpassen, da das Icon
             * sonst nach dem ersten Skalieren verschoben gezeichnet wird. x und
             * y muessen um die haelfte des Zuwachses verringert werden:
             */
            // TODO auch fuer Verkleinerung
            if (targetWidth > getIconWidth()) {
                /** Zuwachs ausrechnen: */
                final int zuwachs = targetWidth - getIconWidth();
                final int halberZuwachs = zuwachs / 2;
                x = x - halberZuwachs;
            }
            // TODO auch fuer Verkleinerung
            if (targetHeigth > getIconHeight()) {
                /** Zuwachs ausrechnen: */
                final int zuwachs = targetHeigth - getIconHeight();
                final int halberZuwachs = zuwachs / 2;
                y = y - halberZuwachs;
            }

            /** Quellimage zum bearbeiten laden: */
            final Image sourceImage = sourceIcon.getImage();
            /** Skalierte Version erzeugen */
            final Image scaledImage = sourceImage.getScaledInstance(
                    targetWidth, targetHeigth, Image.SCALE_SMOOTH);
            /** Skaliertes Image als IconImage bereitstellen */
            this.scaledImageIcon = new ImageIcon(scaledImage);
        }
        /** Hier hat scaledImage in jedem Fall die richtige Groesse */
        /** Wirkliches Zeichnen in Zielkomponente */
        scaledImageIcon.paintIcon(targetComponent, g, x, y);
    }
}