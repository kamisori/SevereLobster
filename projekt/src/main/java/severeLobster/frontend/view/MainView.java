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
import java.io.IOException;

public class MainView extends JPanel {

	private final ResourceManager resourceManager = ResourceManager.get();
	private final Image	backgroundimage = getToolkit().getImage(resourceManager.getGraphicURL("sternenhimmel.jpg"));
	

	public SternenSpielApplicationBackend getBackend() {
		return backend;
	}

	private final SternenSpielApplicationBackend backend;

	public MainView() throws IOException {

		backend = new SternenSpielApplicationBackend();
		backend.startNewSpielFrom("Standardspiel01");

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
		add(spielfeldView, BorderLayout.CENTER);
		add(spielinfo, BorderLayout.EAST);
		setVisible(true);
	}

	public MainView(final Spiel spiel) throws IOException {
		// TODO: Layout wird zwecks Platzhalter auf null gesetzt -> Layout!
		setLayout(null);
		// JPanel spielfeld = new SpielfeldView_fwenisch();
		/**
		 * Nur zum Testen
		 */
		backend = new SternenSpielApplicationBackend();
		// backend.startNewSpielFrom("Standardspiel01");
		backend.setSpiel(spiel);
		/*
		 * backend.getSpiel().initializeNewSpielfeld(20, 18);
		 * backend.getSpiel().setSpielmodus(SpielmodusEnumeration.EDITIEREN);
		 */
		final SpielfeldDarstellung view = new SpielfeldDarstellung();
		new SpielfeldDarstellungsSteuerung(view, backend);

		final SpielmodusViewPanel spielmodusView = new SpielmodusViewPanel();

		new SpielmodusViewController(spielmodusView, backend);

		JPanel spielfeld = new JPanel(false);
		spielfeld.setOpaque(false);
		spielfeld.setLayout(new BoxLayout(spielfeld, BoxLayout.Y_AXIS));
		spielfeld.add(view);
		// spielfeld.add(spielmodusView);

		/**
		 * Ende Test
		 */

		spielfeld.setBounds(50, 50, 500, 500);
		final TrackingControllView trackingView = new TrackingControllView();
		final TrackingControllViewController trackingViewCtrl = new TrackingControllViewController(
				backend);
		trackingView.setTrackingControllViewController(trackingViewCtrl);

		JPanel spielinfo = new SpielinfoView(trackingView);
		spielinfo.setBounds(550, 50, 200, 500);
		add(spielfeld);
		add(spielinfo);
		setVisible(true);
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

	public Spiel getCurrentSpiel() {
		return backend.getSpiel();
	}
}