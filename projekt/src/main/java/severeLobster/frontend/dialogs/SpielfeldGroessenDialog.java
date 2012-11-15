package severeLobster.frontend.dialogs;

/**
 * Groessendialog fuer Spielfeldgroesse
 *
 * @author Lars Schlegelmilch
 */

import infrastructure.components.Koordinaten;

import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.Frame;

public class SpielfeldGroessenDialog {

    private static int[][] spielfeldgroesse;


    public static Koordinaten show(Frame owner) {
        JTextField xAchse = new JTextField(5);
        JTextField yAchse = new JTextField(5);

        JPanel myPanel = new JPanel();
        myPanel.add(new JLabel("Länge:"));
        myPanel.add(xAchse);
        myPanel.add(Box.createHorizontalStrut(15)); // a spacer
        myPanel.add(new JLabel("Breite:"));
        myPanel.add(yAchse);

        int result = JOptionPane.showConfirmDialog(owner, myPanel,
                "Bitte geben Sie die Spielfeldgröße an:", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
          return new Koordinaten(Integer.parseInt(xAchse.getText()), Integer.parseInt(yAchse.getText()));
        }
        return null;
    }
}
