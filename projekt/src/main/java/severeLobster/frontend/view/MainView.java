package severeLobster.frontend.view;

import infrastructure.ResourceManager;
import severeLobster.backend.spiel.Spiel;
import severeLobster.backend.spiel.SternenSpielApplicationBackend;
import severeLobster.frontend.controller.SpielfeldDarstellungsSteuerung;
import severeLobster.frontend.controller.SpielmodusViewController;
import severeLobster.frontend.controller.TrackingControllViewController;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;
import java.io.FileNotFoundException;
import java.io.IOException;

public class MainView extends JPanel {

	private final ResourceManager resourceManager = ResourceManager.get();
	private final Image	backgroundimage = getToolkit().getImage(resourceManager.getGraphicURL("sternenhimmel.jpg"));
	

	public SternenSpielApplicationBackend getBackend() {
		return backend;
	}

	private static SternenSpielApplicationBackend backend;

	public MainView() throws IOException 
	{

		
		
		add(getNewSpielfeld("Standardspiel01"));
		
		
		setVisible(true);
	}
/**
 * Erstellt ein neues SpiefeldPanel anhand des übergebenen PuzzleNamens aus dem Resource Ordner
 * @param strPuzzleName - Der Name des zu ladenen Puzzles (ohne ".puz")
 * @return - JPanel auf dem das Spielfeld und das SpielInfo panel zu sehen sind
 * @author fwenisch
 * @date 04.11.2012
 */
	public JPanel getNewSpielfeld(String strPuzzleName)
	{
		backend = new SternenSpielApplicationBackend();
		try 
		{
			backend.startNewSpielFrom(strPuzzleName);
		} catch (FileNotFoundException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JPanel Spielfeld = new JPanel();
		final SpielfeldDarstellung spielfeldView = new SpielfeldDarstellung();
		new SpielfeldDarstellungsSteuerung(spielfeldView, backend);
		final SpielmodusViewPanel spielmodusView = new SpielmodusViewPanel();
		new SpielmodusViewController(spielmodusView, backend);
		spielfeldView.setPreferredSize(new Dimension(500, 500));
		final TrackingControllView trackingView = new TrackingControllView();
		final TrackingControllViewController trackingViewCtrl = new TrackingControllViewController(
				backend);
		trackingView.setTrackingControllViewController(trackingViewCtrl);
		JPanel spielinfo = new SpielinfoView(trackingView);
		Spielfeld.add(spielfeldView, BorderLayout.CENTER);
		Spielfeld.add(spielinfo, BorderLayout.EAST);
		Spielfeld.setOpaque(false);
		return Spielfeld;
	}

	@Override
	/**
	 * Hintergrundbild wird skaliert gezeichnet
	 * @author fwenisch
	 * @date	04.11.2012
	 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		
		Insets insets = getInsets();
		int x = insets.left;
		int y = insets.top;
		int width = getWidth() - x - insets.right;
		int height = getHeight() - y - insets.bottom;
		g.drawImage(backgroundimage, x, y, width, height, this);

	}

	public static Spiel getCurrentSpiel() {
		return backend.getSpiel();
	}
}