package severeLobster.frontend.view;

import infrastructure.ResourceManager;
import infrastructure.graphics.GraphicUtils;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import severeLobster.backend.spiel.Spiel;
import severeLobster.backend.spiel.SternenSpielApplicationBackend;
import severeLobster.frontend.application.MainFrame;

/**
 * Erstellt das Ein Panel auf die Preview zu sehen ist und ein Button zum
 * Starten des Puzzles
 * 
 * @author fwenisch
 * @date 10.11.2012
 * 
 */
public class PuzzlePreviewView extends JPanel {
    private final ResourceManager resourceManager = ResourceManager.get();
    private String strPuzzleName = null;

    PuzzlePreviewView(String strPuzzleName) {
        JPanel SpielfeldInfo = new JPanel();
        SpielfeldInfo.setLayout(new FlowLayout());
        SpielfeldInfo.setPreferredSize(new Dimension(150, 100));
        JLabel spielfeldPreviewLabel = new JLabel();
        try {
            this.strPuzzleName = strPuzzleName;
            SternenSpielApplicationBackend backend = SternenSpielApplicationBackend
                    .getInstance();

            backend.startNewSpielFrom(strPuzzleName);

            Spiel spiel = backend.getSpiel();
            SpielfeldDarstellung spielfeldView = new SpielfeldDarstellung();
            spielfeldView.setAngezeigtesSpielfeld(spiel.getSpielfeld());
            spielfeldView.setSize(200, 200);
            BufferedImage bufferedImage = GraphicUtils
                    .createComponentShot(spielfeldView);
            bufferedImage = GraphicUtils.getScaledIconImage(bufferedImage, 100,
                    100);

            spielfeldPreviewLabel.setIcon(new ImageIcon(bufferedImage));
            /*
             * 
             * JLabel schwierigkeitsgradTitle = new JLabel(""); JLabel
             * schwierigkeitsgradValue = new JLabel("");
             * schwierigkeitsgradTitle.
             * setText(resourceManager.getText("load.dialog.difficulty"));
             * schwierigkeitsgradValue
             * .setText(spiel.getSchwierigkeitsgrad().toString());
             */

            JLabel jlName = new JLabel(strPuzzleName);
            JLabel jlSchwierigkeit = new JLabel(
                    resourceManager.getText("difficulty") + " "
                            + spiel.getSchwierigkeitsgrad().toString());
            JLabel jlFelder = new JLabel(resourceManager.getText("fields")
                    + " " + spiel.getSpielfeld().getHoehe()
                    * spiel.getSpielfeld().getBreite());

            // Layout Hack
            while (jlFelder.getText().length() < 30) {
                jlFelder.setText(jlFelder.getText() + " ");
                jlFelder.setText(" " + jlFelder.getText());
            }
            JButton jbSSpielen = new JButton(resourceManager.getText("play"));
            jbSSpielen.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent event) {

                    try {
                        MainFrame.mainPanel.addNewSpielfeld(getSpielName());
                    } catch (Exception ex) {

                    }
                }
            });
            SpielfeldInfo.add(jlName);
            SpielfeldInfo.add(jlSchwierigkeit);
            SpielfeldInfo.add(jlFelder);
            SpielfeldInfo.add(jbSSpielen);

            // SpielfeldInfo.setOpaque(false);
        } catch (Exception e) {
            spielfeldPreviewLabel.setIcon(resourceManager
                    .getImageIcon("Ausschluss_128.png"));
            SpielfeldInfo.add(new JLabel(resourceManager
                    .getText("not.available")));
        }
        this.add(spielfeldPreviewLabel, BorderLayout.CENTER);
        this.add(SpielfeldInfo, BorderLayout.SOUTH);
        this.setPreferredSize(new Dimension(128, 200));
        this.setOpaque(false);
        this.setVisible(true);

    }

    private String getSpielName() {
        return strPuzzleName;
    }
}
