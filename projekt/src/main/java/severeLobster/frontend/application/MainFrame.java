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
import severeLobster.backend.spiel.Spiel;
import severeLobster.frontend.dialogs.ExitDialog;
import severeLobster.frontend.dialogs.LoadGamePreview;
import severeLobster.frontend.dialogs.NewGamePreview;
import severeLobster.frontend.view.MainView;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.WindowConstants;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import java.awt.Color;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
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
    public static JMenuItem itemSave;
    public static JMenuItem itemSaveAs;
    public static JMenuItem puzzleSave;
    public static JMenuItem puzzleSaveAs;
    private static JFileChooser loadGameChooser;
    private static JFileChooser newGameChooser;
    private JFileChooser saveGameChooser;
    public static JFrame frame;
    public static MainView mainPanel;
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
        frame.setIconImage(getToolkit().getImage(resourceManager.getGraphicURL("icons/SternIcon128.png")));
        frame.setSize(800, 600);
        // frame.setUndecorated(true);
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

                if (event.getActionCommand().equals(
                        resourceManager.getText("neues.spiel.text"))) {
                    neuesSpielOeffnen();
                }
                if (event.getActionCommand().equals(
                        resourceManager.getText("load.text"))) {
                    int result = loadGameChooser.showOpenDialog(frame);
                    if (result == JFileChooser.APPROVE_OPTION) {
                        try {
                            mainPanel.getBackend().loadSpielFrom(loadGameChooser.getSelectedFile()
                                    .getName()
                                    .replace(
                                            "."
                                                    + GlobaleKonstanten.SPIELSTAND_DATEITYP,
                                            ""));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                if (event.getActionCommand().equals(
                        resourceManager.getText("save.text"))) {
                    Spiel spiel = mainPanel.getCurrentSpiel();
                    try {
                        if (spiel.getSaveName() == null) {
                            spielSpeichernUnter();
                        } else {
                            mainPanel.getBackend().saveCurrentSpielTo(spiel.getSaveName());
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (event.getActionCommand().equals(
                        resourceManager.getText("save.as.text"))) {
                    try {
                        spielSpeichernUnter();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (event.getActionCommand().equals(
                        resourceManager.getText("exit.text"))) {
                    spielBeenden();
                }
                if (event.getActionCommand().equals(
                        resourceManager.getText("puzzle.erstellen"))) {
                    JOptionPane.showMessageDialog(frame,
                            "Diese Funktion ist zurzeit nicht verfügbar!",
                            "Under Construction", JOptionPane.WARNING_MESSAGE);
                }
                if (event.getActionCommand().equals(
                        resourceManager.getText("load.puzzle"))) {
                    JOptionPane.showMessageDialog(frame,
                            "Diese Funktion ist zurzeit nicht verfügbar!",
                            "Under Construction", JOptionPane.WARNING_MESSAGE);
                }
                if (event.getActionCommand().equals(
                        resourceManager.getText("save.puzzle"))) {
                    JOptionPane.showMessageDialog(frame,
                            "Diese Funktion ist zurzeit nicht verfügbar!",
                            "Under Construction", JOptionPane.WARNING_MESSAGE);
                }
                if (event.getActionCommand().equals(
                        resourceManager.getText("puzzle.freigeben"))) {
                    JOptionPane.showMessageDialog(frame,
                            "Diese Funktion ist zurzeit nicht verfügbar!",
                            "Under Construction", JOptionPane.WARNING_MESSAGE);
                }
                if (event.getActionCommand().equals(
                        resourceManager.getText("check.puzzle"))) {

                /*    JOptionPane.showMessageDialog(frame,
                            "Diese Funktion ist zurzeit nicht verfügbar!",
                            "Under Construction", JOptionPane.WARNING_MESSAGE); */
                }
            }
        };

        JMenuItem item;

        jm_Spiel.add(item = new JMenuItem(resourceManager
                .getText("neues.spiel.text")));
        item.addActionListener(menuAction);
        jm_Spiel.add(item = new JMenuItem(resourceManager.getText("load.text")));
        item.addActionListener(menuAction);
        jm_Spiel.add(itemSave = new JMenuItem(resourceManager.getText("save.text")));
        itemSave.setEnabled(false);
        itemSave.addActionListener(menuAction);
        jm_Spiel.add(itemSaveAs = new JMenuItem(resourceManager
                .getText("save.as.text")));
        itemSaveAs.setEnabled(false);
        itemSaveAs.addActionListener(menuAction);
        jm_Spiel.add(item = new JMenuItem(resourceManager.getText("exit.text")));
        item.addActionListener(menuAction);

        jm_Editieren.add(item = new JMenuItem(resourceManager
                .getText("puzzle.erstellen")));
        item.addActionListener(menuAction);
        jm_Editieren.add(item = new JMenuItem(resourceManager
                .getText("load.puzzle")));
        item.addActionListener(menuAction);
        jm_Editieren.add(item = new JMenuItem(resourceManager
                .getText("save.puzzle")));
        item.setEnabled(false);
        item.addActionListener(menuAction);
        jm_Editieren.add(item = new JMenuItem(resourceManager
                .getText("save.as.puzzle")));
        item.setEnabled(false);
        item.addActionListener(menuAction);
        jm_Editieren.add(item = new JMenuItem(resourceManager
                .getText("puzzle.freigeben")));
        item.setEnabled(false);
        item.addActionListener(menuAction);
        jm_Editieren.add(item = new JMenuItem(resourceManager
                .getText("check.puzzle")));
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

        jm_Spiel.insertSeparator(4);

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

    public static void neuesSpielOeffnen() {
        int result = newGameChooser.showOpenDialog(frame);
        if (result == JFileChooser.APPROVE_OPTION) {
            mainPanel.addNewSpielfeld((newGameChooser.getSelectedFile().getName().replace("."+ GlobaleKonstanten.PUZZLE_DATEITYP,"")));
      }
        itemSave.setEnabled(true);
        itemSaveAs.setEnabled(true);
    }

    /**
     * Oeffnet den FileChooser, um das Spiel unter einem gewissen Namen
     * abzuspeichern
     */
    private void spielSpeichernUnter() throws IOException {
        int result = saveGameChooser.showSaveDialog(frame);
        if (result == JFileChooser.APPROVE_OPTION) {
            Spiel spiel = mainPanel.getCurrentSpiel();
            String filename = saveGameChooser.getSelectedFile().getName()
                    .replace("." + GlobaleKonstanten.SPIELSTAND_DATEITYP, "");
            spiel.setSaveName(filename);
            mainPanel.getBackend().saveCurrentSpielTo(filename);
        }
    }

    public void spielBeenden() {
        int result = ExitDialog.show(frame);
        if (ExitDialog.beenden_option.equals(ExitDialog.options[result])) {
            frame.dispose();
        }
    }

    /**
     * Frame wird initialisiert & Hauptpanel wird hinzugefuegt Ausserdem werden
     * Mouselistener hinzugefuegt mit denen sich das Frame verschieben laesst
     *
     * @author fwenisch
     * @version 1.0 08.10.2012
     */
    private void init() {
        loadGameChooser = initFileChooser(GlobaleKonstanten.DEFAULT_SPIEL_SAVE_DIR,
                new SpielView(),
                new FileNameExtensionFilter(
                        resourceManager.getText("load.dialog.extension.description"),
                        GlobaleKonstanten.SPIELSTAND_DATEITYP),
                resourceManager
                        .getText("load.dialog.text"),
                resourceManager
                        .getText("load.dialog.title")
                );
        loadGameChooser.setAccessory(new LoadGamePreview(loadGameChooser));

        newGameChooser = initFileChooser(GlobaleKonstanten.DEFAULT_PUZZLE_SAVE_DIR,
                new PuzzleView(),
                new FileNameExtensionFilter(
                        resourceManager.getText("new.dialog.extension.description"),
                        GlobaleKonstanten.PUZZLE_DATEITYP),
                        resourceManager
                                .getText("new.dialog.text"),
                        resourceManager
                                .getText("new.dialog.title")
                );
        newGameChooser.setAccessory(new NewGamePreview(newGameChooser));

        saveGameChooser = initFileChooser(GlobaleKonstanten.DEFAULT_SPIEL_SAVE_DIR,
                new SpielView(),
                new FileNameExtensionFilter(
                        resourceManager.getText("save.dialog.extension.description"),
                        GlobaleKonstanten.SPIELSTAND_DATEITYP),
                resourceManager
                        .getText("save.dialog.text"),
                resourceManager
                        .getText("save.dialog.title"));

        frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                spielBeenden();
            }
        });
    }

    private JFileChooser initFileChooser(File defaultDir,
                                         FileSystemView fileSystemView,
                                         FileFilter fileFilter,
                                         String approveButtonText,
                                         String dialogTitle) {
        JFileChooser fileChooser = new JFileChooser(
                defaultDir);
        fileChooser.setFileSystemView(fileSystemView);
        fileChooser.setAcceptAllFileFilterUsed(false);
        fileChooser.setFileFilter(fileFilter);
        fileChooser.setApproveButtonText(approveButtonText);
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setDialogTitle(dialogTitle);
        fileChooser.setMultiSelectionEnabled(false);
        return fileChooser;
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
