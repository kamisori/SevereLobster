/*
 * SC_MAIN.java
 *
 * Created on 17.10.2010, 20:21:14
 */

package severeLobster.frontend.application;

import infrastructure.ResourceManager;
import infrastructure.components.PuzzleView;
import infrastructure.components.SpielView;
import infrastructure.constants.GlobaleKonstanten;
import severeLobster.frontend.dialogs.LoadGamePreview;
import severeLobster.frontend.dialogs.NewGamePreview;
import severeLobster.frontend.view.MainView;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.Color;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.IOException;

/**
 * Initialisiert Grafiken
 * 
 * @author Jean-Fabian Wenisch, Lars Schlegelmilch
 */

public class MainFrame extends JMenuBar implements Runnable {
    public JMenu jm_Spiel;
    public JMenu jm_Editieren;
    public JMenu jm_Grafik;
    public JMenu jm_Eigenschaften;
    private JFileChooser loadGameChooser;
    private JFileChooser newGameChooser;
    private JFileChooser saveGameChooser;
    public static JFrame frame;
    private static MainView mainPanel;
    private static Point m_Windowlocation;

    private final ResourceManager resourceManager = ResourceManager.get();

    /**
     * Initialisiert das Menue
     * 
     * @author Jean-Fabian Wenisch
     * @version 1.0 06.12.2010
     */
    public MainFrame() throws IOException {
        // ////////////////////////////////////////////////////////////////////////////////////////////////
        /*
         * Frame wird erzeugt
         */
        // ////////////////////////////////////////////////////////////////////////////////////////////////
        mainPanel = new MainView();
        frame = new JFrame("Sternenhimmel - Gruppe 3");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        //frame.setUndecorated(true);
        frame.setLocationRelativeTo(null);
        frame.setBackground(Color.white);
        frame.add(mainPanel);
        frame.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                m_Windowlocation.x = e.getX();
                m_Windowlocation.y = e.getY();
            }
        });
        frame.addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent e) {
                Point p = frame.getLocation();
                frame.setLocation(p.x + e.getX() - m_Windowlocation.x,
                        p.y + e.getY() - m_Windowlocation.y);
            }
        });

        // ////////////////////////////////////////////////////////////////////////////////////////////////
        /*
         * JMenue wird mit allen Eintraegen erzeugt Actionlistener fuer die
         * Menueeintraege wird hinzugefuegt
         */
        // ////////////////////////////////////////////////////////////////////////////////////////////////
        jm_Spiel = new JMenu(resourceManager.getText("spiel.menu.text"));
        jm_Grafik = new JMenu("Grafik");
        jm_Editieren = new JMenu(resourceManager.getText("editieren.menu.text"));
        jm_Eigenschaften = new JMenu("Einstellungen");
        m_Windowlocation = new Point();
        ActionListener menuAction = new ActionListener() {
            public void actionPerformed(ActionEvent event) {

                if (event.getActionCommand().equals(resourceManager.getText("neues.spiel.text"))) {
                    int result = newGameChooser.showOpenDialog(frame);
                    if (result == JFileChooser.APPROVE_OPTION) {
                        try {
                            mainPanel = new MainView(((NewGamePreview)newGameChooser.getAccessory()).getSpiel());
                            frame.remove(mainPanel);
                            frame.add(mainPanel);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                if (event.getActionCommand().equals(resourceManager.getText("load.text"))) {
                    int result = loadGameChooser.showOpenDialog(frame);
                    if (result == JFileChooser.APPROVE_OPTION) {
                        try {
                            mainPanel = new MainView(((LoadGamePreview)loadGameChooser.getAccessory()).getSpiel());
                            frame.remove(mainPanel);
                            frame.add(mainPanel);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                if (event.getActionCommand().equals(resourceManager.getText("save.text"))) {
                    int result = saveGameChooser.showSaveDialog(frame);
                    if (result == JFileChooser.APPROVE_OPTION) {
                        String filename = saveGameChooser.getSelectedFile().getName().replace("." + GlobaleKonstanten.SPIELSTAND_DATEITYP, "");
                        mainPanel.getCurrentSpiel().save(filename);
                    }
                }
                if (event.getActionCommand().equals(resourceManager.getText("exit.text"))) {
                    frame.dispose();
                }
                if (event.getActionCommand().equals(resourceManager.getText("puzzle.erstellen"))) {
                    JOptionPane.showMessageDialog(frame, "Diese Funktion ist zurzeit nicht verfügbar!", "Under Construction", JOptionPane.WARNING_MESSAGE);
                }
                if (event.getActionCommand().equals(resourceManager.getText("load.puzzle"))) {
                    JOptionPane.showMessageDialog(frame, "Diese Funktion ist zurzeit nicht verfügbar!", "Under Construction", JOptionPane.WARNING_MESSAGE);
                }
                if (event.getActionCommand().equals(resourceManager.getText("save.puzzle"))) {
                    JOptionPane.showMessageDialog(frame, "Diese Funktion ist zurzeit nicht verfügbar!", "Under Construction", JOptionPane.WARNING_MESSAGE);
                }
                if (event.getActionCommand().equals(resourceManager.getText("puzzle.freigeben"))) {
                    JOptionPane.showMessageDialog(frame, "Diese Funktion ist zurzeit nicht verfügbar!", "Under Construction", JOptionPane.WARNING_MESSAGE);
                }
                if (event.getActionCommand().equals(resourceManager.getText("check.puzzle"))) {
                    JOptionPane.showMessageDialog(frame, "Diese Funktion ist zurzeit nicht verfügbar!", "Under Construction", JOptionPane.WARNING_MESSAGE);
                }
            }
        };

        JMenuItem item;

        jm_Spiel.add(item = new JMenuItem(resourceManager.getText("neues.spiel.text")));
        item.addActionListener(menuAction);
        jm_Spiel.add(item = new JMenuItem(resourceManager.getText("save.text")));
        item.addActionListener(menuAction);
        jm_Spiel.add(item = new JMenuItem(resourceManager.getText("load.text")));
        item.addActionListener(menuAction);
        jm_Spiel.add(item = new JMenuItem(resourceManager.getText("exit.text")));
        item.addActionListener(menuAction);

        jm_Editieren.add(item = new JMenuItem(resourceManager.getText("puzzle.erstellen")));
        item.addActionListener(menuAction);
        jm_Editieren.add(item = new JMenuItem(resourceManager.getText("load.puzzle")));
        item.addActionListener(menuAction);
        jm_Editieren.add(item = new JMenuItem(resourceManager.getText("save.puzzle")));
        item.setEnabled(false);
        item.addActionListener(menuAction);
        jm_Editieren.add(item = new JMenuItem(resourceManager.getText("puzzle.freigeben")));
        item.setEnabled(false);
        item.addActionListener(menuAction);
        jm_Editieren.add(item = new JMenuItem(resourceManager.getText("check.puzzle")));
        item.setEnabled(false);
        item.addActionListener(menuAction);

        jm_Grafik.add(item = new JMenuItem("Aufloesung"));
        item.addActionListener(menuAction);
        jm_Grafik.add(item = new JMenuItem("Farbe"));
        item.addActionListener(menuAction);
        jm_Grafik.add(item = new JMenuItem("Hintergrund"));
        item.addActionListener(menuAction);

        jm_Eigenschaften.add(item = new JMenuItem("Lizenz"));
        item.addActionListener(menuAction);
        jm_Eigenschaften.add(item = new JMenuItem("Info"));
        item.addActionListener(menuAction);

        jm_Spiel.insertSeparator(1);
        jm_Spiel.insertSeparator(4);

        jm_Editieren.insertSeparator(1);
        jm_Editieren.insertSeparator(4);

        add(jm_Spiel);
        add(jm_Editieren);
        add(jm_Grafik);
        add(jm_Eigenschaften);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                m_Windowlocation.x = e.getX();
                m_Windowlocation.y = e.getY();
            }
        });

        frame.setJMenuBar(this);
    }

    /**
     * Frame wird initialisiert & Hauptpanel wird hinzugefuegt Ausserdem werden
     * Mouselistener hinzugefuegt mit denen sich das Frame verschieben laesst
     * 
     * @author fwenisch
     * @version 1.0 08.10.2012
     */
    private void init() {
        // Spiel laden Dialog
        loadGameChooser = new JFileChooser(GlobaleKonstanten.DEFAULT_SPIEL_SAVE_DIR);
        loadGameChooser.setFileSystemView(new SpielView());
        loadGameChooser.setAcceptAllFileFilterUsed(false);
        loadGameChooser.setFileFilter(new FileNameExtensionFilter(
                resourceManager.getText("load.dialog.extension.description"),
                GlobaleKonstanten.SPIELSTAND_DATEITYP));
        loadGameChooser.setApproveButtonText(resourceManager.getText("load.dialog.text"));
        loadGameChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        loadGameChooser.setDialogTitle(resourceManager.getText("load.dialog.title"));
        loadGameChooser.setMultiSelectionEnabled(false);
        loadGameChooser.setAccessory(new LoadGamePreview(loadGameChooser));
        // Neues Spiel Dialog
        newGameChooser = new JFileChooser(GlobaleKonstanten.DEFAULT_PUZZLE_SAVE_DIR);
        newGameChooser.setFileSystemView(new PuzzleView());
        newGameChooser.setAcceptAllFileFilterUsed(false);
        newGameChooser.setFileFilter(new FileNameExtensionFilter(
                resourceManager.getText("new.dialog.extension.description"),
                GlobaleKonstanten.PUZZLE_DATEITYP));
        newGameChooser.setApproveButtonText(resourceManager.getText("new.dialog.text"));
        newGameChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        newGameChooser.setDialogTitle(resourceManager.getText("new.dialog.title"));
        newGameChooser.setMultiSelectionEnabled(false);
        newGameChooser.setAccessory(new NewGamePreview(newGameChooser));
        // Save Spiel Dialog
        saveGameChooser = new JFileChooser(GlobaleKonstanten.DEFAULT_SPIEL_SAVE_DIR);
        saveGameChooser.setFileSystemView(new SpielView());
        saveGameChooser.setAcceptAllFileFilterUsed(false);
        saveGameChooser.setFileFilter(new FileNameExtensionFilter(
                resourceManager.getText("save.dialog.extension.description"),
                GlobaleKonstanten.SPIELSTAND_DATEITYP));
        saveGameChooser.setApproveButtonText(resourceManager.getText("save.dialog.text"));
        saveGameChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        saveGameChooser.setDialogTitle(resourceManager.getText("save.dialog.title"));
        saveGameChooser.setMultiSelectionEnabled(false);
    }

    /**
     * Beim starten des Hauptthreads wird die Methode <init()> Aufgerufen in der
     * die Gesamte GUI aufgebaut werden muss
     * 
     * @author fwenisch
     * @version 08.10.2012
     */
    @Override
    public void run() {
        frame.setVisible(true);
        init();
    }
}
