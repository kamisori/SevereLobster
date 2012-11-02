package severeLobster.frontend.view;

import infrastructure.ResourceManager;
import infrastructure.constants.GlobaleKonstanten;

import javax.swing.Icon;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.Insets;
import java.awt.event.MouseListener;

/**
 * An-Auswahl fuer den Tracking-Modus
 * @author Lars Schlegelmilch
 */
public class AnAusAuswahl extends JCheckBox
{
    private final ResourceManager resourceManager = ResourceManager.get();
    private JLabel label;
    private MouseListener mouseListener;
    private Icon icon = resourceManager.getImageIcon("switch-off.png");
    private Icon selected = resourceManager.getImageIcon("switch-on.png");

    public AnAusAuswahl(boolean visible)
    {
        super.setFont(GlobaleKonstanten.FONT.deriveFont((float) 17));
        super.setFocusable(false);
        super.setText("Tracking-Modus");
        super.setForeground(Color.YELLOW);
        super.setHorizontalTextPosition(2);
        super.setIcon(icon);
        super.setSelectedIcon(selected);
        super.setContentAreaFilled(false);
        super.setMargin(new Insets(0, 0, 0, 0));
        super.setSelected(false);
        super.setVisible(true);
    }
}
