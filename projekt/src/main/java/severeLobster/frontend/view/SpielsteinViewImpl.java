package severeLobster.frontend.view;

import severeLobster.backend.spiel.NullState;
import severeLobster.backend.spiel.SpielsteinState;
import severeLobster.frontend.controller.SpielsteinController;

import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Darstellung eines einzelnen Spielsteins.
 * 
 * @author LKleiber
 * 
 */
public class SpielsteinViewImpl extends JLabel implements
        IControllableSpielsteinView {

    private static final IconFactory ICON_FACTORY = SimpleIconFactory
            .getInstance();
    private SpielsteinController spielsteinController;

    public SpielsteinViewImpl() {
        final Icon newIcon = ICON_FACTORY.getIconForState(NullState
                .getInstance());
        this.setIcon(newIcon);
        this.addMouseListener(new InnerMouseListener());
    }

    @Override
    public void setDisplayedState(final SpielsteinState newDisplayedState) {
        final Icon newIcon = ICON_FACTORY.getIconForState(newDisplayedState);
        SwingUtilities.invokeLater(new Runnable() {

            public void run() {
                setIcon(newIcon);
            }
        });
    }

    @Override
    public void setSpielsteinController(
            SpielsteinController spielsteinController) {
        this.spielsteinController = spielsteinController;
        /**
         * Aktualisiere direkt den dargestellten Zustand, da nun der wirkliche
         * Zustand abgefragt werden kann.
         */
        final SpielsteinState currentState = spielsteinController.getState();
        setDisplayedState(currentState);
    }

    private class InnerMouseListener extends MouseAdapter {

        @Override
        public void mouseClicked(MouseEvent arg0) {
            spielsteinController.clickAction(arg0);
        }
    }
}
