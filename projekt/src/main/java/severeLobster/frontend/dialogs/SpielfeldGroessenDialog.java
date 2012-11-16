package severeLobster.frontend.dialogs;

/**
 * Groessendialog fuer Spielfeldgroesse
 *
 * @author Lars Schlegelmilch
 */

import infrastructure.ResourceManager;
import infrastructure.components.Koordinaten;

import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.Frame;

public class SpielfeldGroessenDialog {

    private static int[][] spielfeldgroesse;
    private final static ResourceManager resourceManager = ResourceManager.get();


    public static Koordinaten show(Frame owner) {
        JTextField xAchse = new JTextField(5);
        JTextField yAchse = new JTextField(5);

        JPanel myPanel = new JPanel();
        myPanel.add(new JLabel(resourceManager.getText("spielfeldgroesse.breite")));
        myPanel.add(xAchse);
        myPanel.add(Box.createHorizontalStrut(15)); // a spacer
        myPanel.add(new JLabel(resourceManager.getText("spielfeldgroesse.hoehe")));
        myPanel.add(yAchse);

        int result = JOptionPane.showConfirmDialog(owner, myPanel,
                resourceManager.getText("spielfeldgroesse.input.size"), JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
          return new Koordinaten(Integer.parseInt(xAchse.getText()), Integer.parseInt(yAchse.getText()));
        }
        return null;
    }
}
