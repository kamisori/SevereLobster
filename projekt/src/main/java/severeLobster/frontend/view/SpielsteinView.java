package severeLobster.frontend.view;

import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

import severeLobster.backend.spiel.Spielstein;

/**
 * Darstellung eines einzelnen Spielsteins.
 * 
 * @author LKleiber
 * 
 */
public class SpielsteinView extends JLabel {

    private static final IconFactory ICON_FACTORY = SimpleIconFactory
            .getInstance();

    public SpielsteinView(Spielstein spielstein) {
        final Icon newIcon = ICON_FACTORY.getIconForState(spielstein);
        this.setIcon(newIcon);
    }

    public void setDisplayedStein(final Spielstein newSpielstein) {
        final Icon newIcon = ICON_FACTORY.getIconForState(newSpielstein);
        SwingUtilities.invokeLater(new Runnable() {

            public void run() {
                setIcon(newIcon);
            }
        });
    }

}
