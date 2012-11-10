package severeLobster.frontend.view;

import infrastructure.ResourceManager;
import infrastructure.graphics.GraphicUtils;
import java.awt.BorderLayout;
import java.awt.Dimension;
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
 * Erstellt das Ein Panel auf die Preview zu sehen ist und ein Button zum Starten des Puzzles
 * @author fwenisch
 * @date	10.11.2012
 *
 */
public class PuzzlePreviewView  extends JPanel
{
	private final ResourceManager resourceManager = ResourceManager.get();
	private String strPuzzleName=null;

	PuzzlePreviewView(String strPuzzleName)
	{
		JPanel  SpielfeldInfo = new JPanel();
		JLabel spielfeldPreviewLabel = new JLabel();
		try 
		{
			this.strPuzzleName=strPuzzleName;
			SternenSpielApplicationBackend	backend = new SternenSpielApplicationBackend();

			backend.startNewSpielFrom(strPuzzleName);

			Spiel spiel =backend.getSpiel();
			SpielfeldDarstellung spielfeldView = new SpielfeldDarstellung();
			spielfeldView.setAngezeigtesSpielfeld(spiel
					.getSpielfeld());
			spielfeldView.setSize(200, 200);
			BufferedImage bufferedImage = GraphicUtils
					.createComponentShot(spielfeldView);
			bufferedImage = GraphicUtils.getScaledIconImage(
					bufferedImage, 100, 100);
			
			spielfeldPreviewLabel .setIcon(new ImageIcon(bufferedImage));
			/*
			SpielfeldInfo.setLayout(new GridLayout(3,0));
			JLabel schwierigkeitsgradTitle = new JLabel("");
			JLabel schwierigkeitsgradValue = new JLabel("");
			schwierigkeitsgradTitle.setText(resourceManager.getText("load.dialog.difficulty"));
			schwierigkeitsgradValue.setText(spiel.getSchwierigkeitsgrad().toString());
			 */
	
			JButton jbSSpielen = new JButton("Spielen");
			jbSSpielen.addActionListener(new ActionListener() 
			{
				public void actionPerformed(ActionEvent event) 
				{

					try 
					{
						MainFrame.mainPanel.addNewSpielfeld(getSpielName());
					}
					catch(Exception ex)
					{

					}
				}}
					);

			SpielfeldInfo.add(jbSSpielen);
			SpielfeldInfo.setOpaque(false);
		} 
		catch (Exception e) 
		{
			spielfeldPreviewLabel .setIcon(resourceManager.getImageIcon("Ausschluss_128.png"));
			SpielfeldInfo.add(new JLabel("Nicht verfügbar"));
		}
		this.add(spielfeldPreviewLabel,BorderLayout.CENTER);
		this.add(SpielfeldInfo,BorderLayout.SOUTH);
		this.setPreferredSize(new Dimension (128,170));
		this.setOpaque(false);
		this.setVisible(true);


	}
	private String getSpielName()
	{
		return strPuzzleName;
	}
}
