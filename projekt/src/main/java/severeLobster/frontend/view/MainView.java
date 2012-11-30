package severeLobster.frontend.view;

import infrastructure.ResourceManager;
import infrastructure.components.FTPConnector;
import infrastructure.components.Koordinaten;
import infrastructure.components.MenuButton;
import infrastructure.exceptions.LoesungswegNichtEindeutigException;
import severeLobster.backend.spiel.Spiel;
import severeLobster.backend.spiel.SternenSpielApplicationBackend;
import severeLobster.frontend.application.MainFrame;
import severeLobster.frontend.controller.SpielfeldDarstellungsSteuerung;
import severeLobster.frontend.controller.TrackingControllViewController;
import severeLobster.frontend.dialogs.SpielfeldGroessenDialog;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class MainView extends JPanel {

    public static FTPConnector ftpConnector;
    private final ResourceManager resourceManager = ResourceManager.get();
    private final Image backgroundimage = getToolkit().getImage(
            resourceManager.getGraphicURL("sternenhimmel.jpg"));
    private JPanel jpMenu = null;
    /**
     * Wird am Anfang initialisiert und je nach Methode immer wieder vom Panel
     * genommen oder neu drauf gesetzt, aber nie wieder neu erzeugt.
     */
    private final SpielfeldDarstellung spielfeldDarstellung;
    private final TrackingControllView trackingView;
    private final SpielinfoView spielInfoView;
    private final EditiermodusView editiermodusView;

    private final SternenSpielApplicationBackend backend;

    public SternenSpielApplicationBackend getBackend() {
        return this.backend;
    }

    public SpielinfoView getSpielInfoView() {
        return spielInfoView;
    }

    public MainView(final SternenSpielApplicationBackend backend)
            throws IOException {
        this.backend = backend;
        this.spielfeldDarstellung = new SpielfeldDarstellung();
        new SpielfeldDarstellungsSteuerung(spielfeldDarstellung, backend);

        this.trackingView = new TrackingControllView();
        new TrackingControllViewController(trackingView, backend);

        this.spielInfoView = new SpielinfoView(this.trackingView, this.backend);
        this.editiermodusView = new EditiermodusView(this.backend);
        addMenuPanel();
        setVisible(true);
    }

    /**
     * Stellt das Spielmodus Panel dar und laedt ein Spiel anhand des
     * uebergebenen PuzzleNamens aus dem Resource Ordner
     *
     * @param strPuzzleName - Der Name des zu ladenen Puzzles (ohne ".puz")
     * @return - JPanel auf dem das Spielfeld und das SpielInfo panel zu sehen
     *         sind
     * @author fwenisch
     * @date 04.11.2012
     */
    public void addSpielmodusPanelAndStartSpiel(String strPuzzleName,
                                                boolean continueGame) {
        try {
            if (!continueGame) {
                backend.startNewSpielFrom(strPuzzleName);
            } else {
                backend.loadSpielFrom(strPuzzleName);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (LoesungswegNichtEindeutigException e) {
            // TODO Vernuenftig loesen
            e.printStackTrace();
        }
        JPanel spielfeldUndInfoViewPanel = new JPanel();
        spielfeldUndInfoViewPanel.setLayout(new BorderLayout());
        spielfeldUndInfoViewPanel.add(this.spielfeldDarstellung,
                BorderLayout.CENTER);
        spielfeldUndInfoViewPanel.add(this.spielInfoView, BorderLayout.EAST);
        spielfeldUndInfoViewPanel.setOpaque(false);
        removeAll();
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        add(spielfeldUndInfoViewPanel);
        MainFrame.controlSpielMenue(true);
        MainFrame.controlEditierMenue(false);
        validate();
        repaint();
    }

    /**
     * Stellt das Edit Modus Panel mit einem Spielfeld der uebergebenen
     * Spielfeldgroesse dar.
     *
     * @param x x-Achsengroesse des Spielfeldes
     * @param y y-Achsengroesse des Spielfeldes
     */
    public void addEditModusPanelAndCreateNewSpielfeld(int x, int y) {
        Spiel spiel = new Spiel();
        spiel.initializeNewSpielfeld(x, y);
        backend.setSpiel(spiel);

        JPanel spielfeldCenteringPanel = new JPanel();
        spielfeldCenteringPanel.setLayout(new BorderLayout());
        spielfeldCenteringPanel.add(this.spielfeldDarstellung,
                BorderLayout.CENTER);
        spielfeldCenteringPanel.setOpaque(false);
        removeAll();
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        add(spielfeldCenteringPanel);
        MainFrame.controlSpielMenue(false);
        MainFrame.controlEditierMenue(true);
        validate();
        repaint();
    }

    /**
     * Fuegt das Hauptmenue hinzu
     */
    public void addMenuPanel() {
        removeAll();
        setLayout(new FlowLayout());
        if (jpMenu == null) {
            jpMenu = new JPanel();
            jpMenu.setOpaque(false);
            jpMenu.setLayout(new BoxLayout(jpMenu, BoxLayout.Y_AXIS));

            ImageIcon logo = resourceManager.getImageIcon("logo_menu.png");
            ImageIcon iconSpiel = resourceManager.getImageIcon("Menu_neues_Spiel.png");
            ImageIcon iconLaden = resourceManager.getImageIcon("Menu_Spiel_fortsetzen.png");
            ImageIcon iconErstellen = resourceManager.getImageIcon("Menu_Puzzle_erstellen.png");

            MenuButton neuesSpiel = new MenuButton(iconSpiel, iconSpiel, iconSpiel);
            neuesSpiel.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    MainFrame.neuesSpielOeffnen();
                }
            });
            MenuButton spielLaden = new MenuButton(iconLaden, iconLaden, iconLaden);
            spielLaden.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    MainFrame.loadSpiel();
                }
            });

            MenuButton puzzleErstellen = new MenuButton(iconErstellen, iconErstellen, iconErstellen);
            puzzleErstellen.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    addSpielErstellenPanel();
                }
            });
            MainFrame.controlSpielMenue(false);
            MainFrame.controlEditierMenue(false);

            jpMenu.add(new JLabel(logo));
            jpMenu.add(neuesSpiel);
            jpMenu.add(spielLaden);
            jpMenu.add(puzzleErstellen);
        }
        add(jpMenu);

        validate();
        repaint();
    }

    public void addSpielErstellenPanel() {
        Koordinaten koordinaten = SpielfeldGroessenDialog.show(MainFrame.frame);
        if (koordinaten == null) {
            return;
        }
        addEditModusPanelAndCreateNewSpielfeld(koordinaten.getX(),
                koordinaten.getY());

        JPanel spielfeldUndInfoViewPanel = new JPanel();
        spielfeldUndInfoViewPanel.setLayout(new BorderLayout());
        spielfeldUndInfoViewPanel.add(this.spielfeldDarstellung,
                BorderLayout.CENTER);
        spielfeldUndInfoViewPanel.add(this.editiermodusView, BorderLayout.EAST);
        spielfeldUndInfoViewPanel.setOpaque(false);
        removeAll();
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        add(spielfeldUndInfoViewPanel);
        MainFrame.controlSpielMenue(false);
        MainFrame.controlEditierMenue(true);
        validate();
        repaint();
    }

    public void addSpielErstellenPanel(String spielname) throws IOException {
        getBackend().loadPuzzleFrom(spielname);
        JPanel spielfeldUndInfoViewPanel = new JPanel();
        spielfeldUndInfoViewPanel.setLayout(new BorderLayout());
        spielfeldUndInfoViewPanel.add(this.spielfeldDarstellung,
                BorderLayout.CENTER);
        spielfeldUndInfoViewPanel.add(this.editiermodusView, BorderLayout.EAST);
        spielfeldUndInfoViewPanel.setOpaque(false);
        removeAll();
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        add(spielfeldUndInfoViewPanel);
        MainFrame.controlSpielMenue(false);
        MainFrame.controlEditierMenue(true);
        validate();
        repaint();
    }

    public void addOnlineSpielAuswahlPanel() {

        ftpConnector.connect();

        if (ftpConnector.isOnline()) {
            ftpConnector.updateFiles();
            JPanel jpAuswahl = new JPanel();
            jpAuswahl.setOpaque(false);
            jpAuswahl.setPreferredSize(new Dimension(700, 600));
            jpAuswahl.setMinimumSize(new Dimension(700, 600));
            jpAuswahl.setMaximumSize(new Dimension(700, 600));
            JPanel jpSpielfeldAuswahl = new JPanel();

            GridLayout layout = new GridLayout(ftpConnector.files.length, 1);
            jpSpielfeldAuswahl.setLayout(layout);
            jpSpielfeldAuswahl.setOpaque(false);

            for (int i = 0; i < ftpConnector.files.length; i++) {
                jpSpielfeldAuswahl.add(new OnlinePuzzlePreviewView(this,
                        ftpConnector.files[i].getName(), ftpConnector));
            }
            JPanel jpBottom = new JPanel();
            jpBottom.setOpaque(false);
            jpBottom.setPreferredSize(new Dimension(600, 30));
            JButton jbBackToMenu = new JButton(
                    resourceManager.getText("back.to.main.menu"));
            jbBackToMenu.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent event) {

                    try {
                        addMenuPanel();
                    } catch (Exception e) {
                        removeAll();
                        addMenuPanel();
                    }
                }
            });
            jpBottom.add(jbBackToMenu);
            JScrollPane jpScroll = new JScrollPane();
            jpScroll.setOpaque(false);
            jpScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
            jpScroll.getViewport().add(jpSpielfeldAuswahl);
            jpScroll.setPreferredSize(new Dimension(700, 400));
            jpScroll.getViewport().setOpaque(false);
            JLabel jlUeberschrift = new JLabel();
            jlUeberschrift.setPreferredSize(new Dimension(700, 50));
            jlUeberschrift.setForeground(Color.YELLOW);
            jlUeberschrift.setFont(new Font("Verdana", 0, 36));
            jlUeberschrift.setHorizontalAlignment(JLabel.CENTER);
            jlUeberschrift.setText(resourceManager.getText("online.archive"));
            jpAuswahl.add(jlUeberschrift, BorderLayout.NORTH);
            jpAuswahl.add(jpScroll, BorderLayout.CENTER);
            jpAuswahl.add(jpBottom, BorderLayout.SOUTH);

            removeAll();
            add(jpAuswahl);
            validate();
            repaint();
        } else {
            JOptionPane.showMessageDialog(MainFrame.frame,
                    resourceManager.getText("online.archive.no.connection.body"),
                    resourceManager.getText("online.archive.no.connection.title"),
                    JOptionPane.WARNING_MESSAGE);
        }
    }

    @Override
    /**
     * Hintergrundbild wird skaliert gezeichnet
     * @author fwenisch
     * @date 04.11.2012
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
