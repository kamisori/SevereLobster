package severeLobster.frontend.view;

import infrastructure.constants.enums.SpielmodusEnumeration;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

import severeLobster.backend.spiel.Ausschluss;
import severeLobster.backend.spiel.KeinStein;
import severeLobster.backend.spiel.Spielstein;
import severeLobster.backend.spiel.Stern;
import severeLobster.frontend.controller.SpielfeldController;

/**
 * Darstellung eines einzelnen Spielsteins.
 * 
 * @author LKleiber
 * 
 */
public class SpielsteinView extends JLabel {

    private static final IconFactory ICON_FACTORY = SimpleIconFactory
            .getInstance();
    private final int x;
    private final int y;
    private final SpielfeldController controller;

    public SpielsteinView(Spielstein spielstein, int x, int y,
            SpielfeldController controller) {
        super(ICON_FACTORY.getIconForState(spielstein), JLabel.CENTER);
        this.x = x;
        this.y = y;
        this.controller = controller;
        this.addMouseListener(new InnerMouseListener());
    }

    public void setDisplayedStein(final Spielstein newSpielstein) {
        final Icon newIcon = ICON_FACTORY.getIconForState(newSpielstein);
        setIcon(newIcon);
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
                new PopupMenuForSpielsteinChoice(controller, controller
                        .getSpielfeld().listAvailableStates(x, y), x, y).show(
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
        if (controller.getSpielfeld().getSpielstein(x, y)
                .equals(Stern.getInstance())) {
            controller.setSpielstein(KeinStein.getInstance(), x, y);
        } else {
            controller.setSpielstein(Stern.getInstance(), x, y);
        }

    }

    private void guessAusschluss() {
        if (controller.getSpielfeld().getSpielstein(x, y)
                .equals(Ausschluss.getInstance())) {
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
