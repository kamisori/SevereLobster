package severeLobster.frontend.dialogs;

import infrastructure.ResourceManager;
import infrastructure.constants.GlobaleKonstanten;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Component;

/**
 * Gewonnen-Dialog der Application
 *
 * @author Lars Schlegelmilch
 */
public class GewonnenDialog extends JOptionPane {
    //TODO Texte in Properties auslagern
    private static final ResourceManager resourceManager = ResourceManager.get();

    public static final String neues_spiel_starten = "Neues Spiel starten...";
    public static final String zurueck_zum_menue = "Zurück ins Hauptmenü";
    public static final String spiel_beenden = "Beenden";

    public static final String[] options = {neues_spiel_starten, zurueck_zum_menue, spiel_beenden};

    public static int show(Component parentComponent, int highscore, String spielzeit, int versuche) {
        JLabel title = new JLabel("Herzlichen Glückwunsch!");
        JLabel text = new JLabel("<html><body>Sie haben das Puzzle erfolgreich gelößt! <br>" +
                "Bei einer Spielzeit von " + spielzeit +" Sekunden haben Sie " + versuche + " Versuche benötigt. <br>" +
                "Ihre Highscore beträgt sagenhafte " + highscore + " Punkte!");
        title.setFont(GlobaleKonstanten.FONT.deriveFont((float) 20));
        title.setVisible(true);
        text.setVisible(true);
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(title, BorderLayout.NORTH);
        panel.add(text, BorderLayout.CENTER);


        return showOptionDialog(parentComponent, panel, "Gewonnen",
                DEFAULT_OPTION, PLAIN_MESSAGE, resourceManager.getImageIcon("sieg.png"), options, null);
    }

}
