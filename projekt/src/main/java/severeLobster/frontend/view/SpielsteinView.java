package severeLobster.frontend.view;

import infrastructure.constants.enums.SpielmodusEnumeration;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JLabel;

import severeLobster.backend.spiel.Ausschluss;
import severeLobster.backend.spiel.KeinStein;
import severeLobster.backend.spiel.Spielstein;
import severeLobster.backend.spiel.Stern;
import severeLobster.frontend.controller.SpielfeldViewController;

/**
 * Darstellung eines einzelnen Spielsteins.
 * 
 * @author Lutz Kleiber
 * 
 */
public class SpielsteinView extends JLabel {

    private static final IconFactory ICON_FACTORY = AdvancedDynamicallyResizingIconFactory
            .getInstance();
    private final SpielfeldViewController controller;
    private final int x;
    private final int y;

    private SpielsteinView(int x, int y, SpielfeldViewController controller) {
        this.x = x;
        this.y = y;
        this.controller = controller;
        this.addMouseListener(new InnerMouseListener());
        initializeColorAndBorderOf(this);
    }

    public static SpielsteinView createSpielsteinView(int x, int y,
            SpielfeldViewController controller) {
        return new SpielsteinView(x, y, controller);
    }

    public static JLabel createLabel() {
        final JLabel result = new JLabel();
        initializeColorAndBorderOf(result);
        return result;
    }

    public static JLabel createPfeilAnzahlLabel() {
        final JLabel result = createLabel();
        result.setFont(new Font("Serif", Font.PLAIN, 20));
        result.setForeground(Color.white);
        return result;
    }

    private static void initializeColorAndBorderOf(JLabel label) {
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setBackground(Color.DARK_GRAY);
        label.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
    }

    public void setDisplayedSpielstein(final Spielstein spielstein) {
        final Icon newIcon = ICON_FACTORY.getIconForSpielstein(spielstein);
        this.setIcon(newIcon);
    }

    /**
     * Methode behandelt komplette Logik fuer Mausklicks: Im Spielmodus:
     * Linksklick: Stern setzen; Rechtsklick: Ausschluss setzen.
     * 
     * Im Editiermodus: Linksklick: Feld auf NullState resetten; Rechtsklick:
     * Editiermenu anzeigen.
     * 
     */
    public void clickAction(MouseEvent mouseEvent) {

        if (isSpielModus()) {
            if (isLeftClick(mouseEvent)) {
                guessStern();
                return;
            }
            if (isRightClick(mouseEvent)) {
                guessAusschluss();
                return;
            }
        }
        /** Editiermodus: */
        if (!isSpielModus()) {
            if (isLeftClick(mouseEvent)) {
                resetSpielsteinState();
                return;
            }
            if (isRightClick(mouseEvent)) {
                new PopupMenuForSpielsteinChoice(controller,
                        controller.listAvailableStates(x, y), x, y).show(
                        mouseEvent.getComponent(), mouseEvent.getX(),
                        mouseEvent.getY());

                return;
            }
        }

    }

    private boolean isLeftClick(final MouseEvent e) {
        return e.getButton() == MouseEvent.BUTTON1;

    }

    private boolean isRightClick(final MouseEvent e) {
        return e.getButton() == MouseEvent.BUTTON3;

    }

    private void resetSpielsteinState() {
        final Spielstein nullState = KeinStein.getInstance();
        controller.setSpielstein(nullState, x, y);
    }

    private boolean isSpielModus() {
        return controller.getSpielmodus().equals(SpielmodusEnumeration.SPIELEN);
    }

    private void guessStern() {
        if (controller.getSpielstein(x, y).equals(Stern.getInstance())) {
            controller.setSpielstein(KeinStein.getInstance(), x, y);
        } else {
            controller.setSpielstein(Stern.getInstance(), x, y);
        }

    }

    private void guessAusschluss() {
        if (controller.getSpielstein(x, y).equals(Ausschluss.getInstance())) {
            controller.setSpielstein(KeinStein.getInstance(), x, y);
        } else {
            controller.setSpielstein(Ausschluss.getInstance(), x, y);
        }
    }

    private class InnerMouseListener extends MouseAdapter {

        @Override
        public void mouseClicked(MouseEvent arg0) {
            clickAction(arg0);
        }
    }
}
