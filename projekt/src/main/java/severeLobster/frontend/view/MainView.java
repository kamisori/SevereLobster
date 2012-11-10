package severeLobster.frontend.view;

import infrastructure.ResourceManager;
import infrastructure.constants.GlobaleKonstanten;
import severeLobster.backend.spiel.Spiel;
import severeLobster.backend.spiel.SternenSpielApplicationBackend;
import severeLobster.frontend.application.MainFrame;
import severeLobster.frontend.controller.SpielfeldDarstellungsSteuerung;
import severeLobster.frontend.controller.SpielmodusViewController;
import severeLobster.frontend.controller.TrackingControllViewController;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;

public class MainView extends JPanel {

	private final ResourceManager resourceManager = ResourceManager.get();
	private final Image	backgroundimage = getToolkit().getImage(resourceManager.getGraphicURL("sternenhimmel.jpg"));
	private JPanel jpMenu= null;
	private JPanel jpKampagne = null;


	public SternenSpielApplicationBackend getBackend() {
		return backend;
	}

	private SternenSpielApplicationBackend backend;

	public MainView() throws IOException
	{
		addMenuPanel();
		setVisible(true);
	}
	/**
	 * Erstellt ein neues SpiefeldPanel anhand des übergebenen PuzzleNamens aus dem Resource Ordner
	 * @param strPuzzleName - Der Name des zu ladenen Puzzles (ohne ".puz")
	 * @return - JPanel auf dem das Spielfeld und das SpielInfo panel zu sehen sind
	 * @author fwenisch
	 * @date 04.11.2012
	 */
	public void addNewSpielfeld(String strPuzzleName)
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
		JPanel spielinfo = new SpielinfoView(trackingView, backend);
		Spielfeld.add(spielfeldView, BorderLayout.CENTER);
		Spielfeld.add(spielinfo, BorderLayout.EAST);
		Spielfeld.setOpaque(false);
		removeAll();
		add(Spielfeld);
		validate();
		repaint();
	}
	/**
	 * Fügt das KampagnenPanel hinzu
	 * @author fwenisch
	 * @date 10.11.2012
	 */
	public void addKampagnenPanel()
	{
		if (jpKampagne==null)
		{
			jpKampagne = new JPanel();
			jpKampagne.setPreferredSize(new Dimension(700, 600));
			jpKampagne.setMinimumSize(new Dimension(700, 600));
			JPanel jpSpielfeldAuswahl = new JPanel();
			jpSpielfeldAuswahl.setLayout(new GridLayout(2,5));
			jpSpielfeldAuswahl.setOpaque(false);
			
			
			for(int i=0; i <10;i++)
			{
				jpSpielfeldAuswahl.add(new PuzzlePreviewView("Standardspiel0"+(i+1)));
				}
			JPanel jpBottom = new JPanel();
			jpBottom.setOpaque(false);
			jpBottom.setPreferredSize(new Dimension(600, 30));
			JButton jbBackToMenu = new JButton("Zurück zum Hauptmenü");
			jbBackToMenu.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent event) 
				{

					try 
					{
						addMenuPanel();
					}
					catch(Exception e)
					{
						removeAll();
						addMenuPanel();
					}
				}}
					);
			jpBottom.add(jbBackToMenu);
			jpKampagne.add(jpSpielfeldAuswahl,BorderLayout.CENTER);
			jpKampagne.add(jpBottom,BorderLayout.SOUTH);
			jpKampagne.setOpaque(false);
		}
		removeAll();
		add(jpKampagne);
		validate();
		repaint();
	}
	
	/**
	 * Fügt das Hauptmenü hinzu
	 * @author fwenisch
	 * @date 10.11.2012
	 */
	public void addMenuPanel()
	{
		if (jpMenu==null)
		{
			jpMenu = new JPanel();
			jpMenu.setPreferredSize(new Dimension(450,450));
			jpMenu.setLayout(new GridLayout(5,0));
			jpMenu.setOpaque(false);
			JLabel jlLogo = new JLabel(resourceManager.getImageIcon("Logo.png"));
			jlLogo.setMinimumSize(new Dimension(450, 200));
			JButton jbKampagneSpielen= new JButton("Kampagne Starten");
			jbKampagneSpielen.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent event) 
				{

					try 
					{
						addKampagnenPanel();
					}
					catch(Exception e)
					{
						removeAll();
						addMenuPanel();
					}
				}}
					);
			JButton jbSpielSpielen= new JButton("Neues Spiel starten");
			jbSpielSpielen.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent event) 
				{
					try 
					{
						MainFrame.neuesSpielOeffnen();
					}
					catch(Exception e)
					{
						removeAll();
						addMenuPanel();
					}
				}
			});
			JButton jbSpielErstellen= new JButton("Neues Puzzle erstellen");
			JButton jbSpielBeenden= new JButton("Beenden");

			jpMenu.add(jlLogo);
			jpMenu.add(jbKampagneSpielen);
			jpMenu.add(jbSpielSpielen);
			jpMenu.add(jbSpielErstellen);

		}
		removeAll();
		add(jpMenu);
		validate();
		repaint();
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
