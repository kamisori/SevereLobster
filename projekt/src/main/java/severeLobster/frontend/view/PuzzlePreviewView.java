package severeLobster.frontend.view;

import infrastructure.ResourceManager;
import infrastructure.constants.enums.SpielmodusEnumeration;
import infrastructure.graphics.GraphicUtils;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
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

			JLabel jlName = new JLabel("Spielname: "+ strPuzzleName.substring(0,strPuzzleName.indexOf('-')));
			while(jlName.getText().length()<55)
				jlName.setText(jlName.getText()+" ");
			jlName.setForeground(Color.YELLOW);
			jlName.setFont(new Font("Verdana", 0, 18));
			JLabel jlSchwierigkeit = new JLabel(
					resourceManager.getText("difficulty") + " "
					+ spiel.getSchwierigkeitsgrad().toString());
			while(jlSchwierigkeit.getText().length()<55)
				jlSchwierigkeit.setText(jlSchwierigkeit.getText()+" ");
			jlSchwierigkeit.setForeground(Color.YELLOW);
			jlSchwierigkeit.setFont(new Font("Verdana", 0, 18));
			JLabel jlFelder = new JLabel(resourceManager.getText("fields")
					+ " " + spiel.getSpielfeld().getHoehe()
					* spiel.getSpielfeld().getBreite());
			while(jlFelder.getText().length()<55)
				jlFelder.setText(jlFelder.getText()+" ");
			jlFelder.setForeground(Color.YELLOW);
			jlFelder.setFont(new Font("Verdana", 0, 18));


			JButton jbSSpielen = new JButton(resourceManager.getText("play"));
			jbSSpielen.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent event) {

					try {
						MainFrame.mainPanel.addSpielmodusPanelAndStartSpiel(getSpielName(), false);
					} catch (Exception ex) {

					}
				}
			});
			jbSSpielen.setPreferredSize(new Dimension (500,50));
			SpielfeldInfo.setPreferredSize(new Dimension (500,200));
			SpielfeldInfo.add(jlName);
			SpielfeldInfo.add(jlSchwierigkeit);
			SpielfeldInfo.add(jlFelder);
			SpielfeldInfo.add(jbSSpielen);
			SpielfeldInfo.setOpaque(false);

		} catch (Exception e) {
			spielfeldPreviewLabel.setIcon(resourceManager
					.getImageIcon("Ausschluss_128.png"));
			SpielfeldInfo.add(new JLabel(resourceManager
					.getText("not.available")));
		}
		this.add(spielfeldPreviewLabel, BorderLayout.WEST);
		this.add(SpielfeldInfo, BorderLayout.CENTER);
		this.setPreferredSize(new Dimension(700, 200));
		this.setOpaque(false);
		this.setVisible(true);

	}

	private String getSpielName() {
		return strPuzzleName;
	}
}
