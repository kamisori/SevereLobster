/*
 * SC_MAIN.java
 *
 * Created on 17.10.2010, 20:21:14
 */

package severeLobster.frontend.application;

import severeLobster.backend.spiel.Spiel;
import severeLobster.frontend.view.MainView;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import java.awt.Color;
import java.awt.Frame;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

/**
 * Initialisiert Grafiken
 * 
 * @author Jean-Fabian Wenisch
 */

public class MainFrame extends JMenuBar implements Runnable {
    public JMenu jm_Spiel;
    public JMenu jm_Grafik;
    public JMenu jm_Eigenschaften;
    public static JFrame frame;
    private static MainView MainPanel;
    private static Point m_Windowlocation;

    /**
     * Initialisiert das Menue
     * 
     * @author Jean-Fabian Wenisch
     * @version 1.0 06.12.2010
     */
    public MainFrame() {
        // ////////////////////////////////////////////////////////////////////////////////////////////////
        /*
         * Frame wird erzeugt
         */
        // ////////////////////////////////////////////////////////////////////////////////////////////////
        MainPanel = new MainView(new Spiel());
        frame = new JFrame("Sternenhimmel");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setUndecorated(true);
        frame.setLocationRelativeTo(null);
        frame.setBackground(Color.white);
        frame.add(MainPanel);
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
        jm_Spiel = new JMenu("Spiel");
        jm_Grafik = new JMenu("Grafik");
        jm_Eigenschaften = new JMenu("Einstellungen");
        m_Windowlocation = new Point();

        ActionListener MenuAction = new ActionListener() {
            public void actionPerformed(ActionEvent event) {

                if (event.getActionCommand().equals("�berblick")) {

                }
                if (event.getActionCommand().equals("Laden")) {

                }
                if (event.getActionCommand().equals("Hardware Informationen")) {

                }
                if (event.getActionCommand().equals("Software Informationen")) {

                }
                if (event.getActionCommand().equals("Schlie�en")) {
                    frame.dispose();
                }
            }
        };

        JMenuItem item;

        jm_Spiel.add(item = new JMenuItem("Neues Spiel"));
        item.addActionListener(MenuAction);
        item.addActionListener(MenuAction);
        jm_Spiel.add(item = new JMenuItem("Speichern"));
        item.addActionListener(MenuAction);
        jm_Spiel.add(item = new JMenuItem("Laden"));
        item.addActionListener(MenuAction);
        jm_Spiel.add(item = new JMenuItem("Schlie�en"));
        item.addActionListener(MenuAction);

        jm_Grafik.add(item = new JMenuItem("Aufl�sung"));
        item.addActionListener(MenuAction);
        jm_Grafik.add(item = new JMenuItem("Farbe"));
        item.addActionListener(MenuAction);
        jm_Grafik.add(item = new JMenuItem("Hintergrund"));
        item.addActionListener(MenuAction);

        jm_Eigenschaften.add(item = new JMenuItem("Lizenz"));
        item.addActionListener(MenuAction);
        jm_Eigenschaften.add(item = new JMenuItem("Info"));
        item.addActionListener(MenuAction);

        jm_Spiel.insertSeparator(1);
        jm_Spiel.insertSeparator(4);

        add(jm_Spiel);
        add(jm_Grafik);
        add(jm_Eigenschaften);

        JMenu oMenu = new JMenu(
                "                                                            Sternenhimmel - Gruppe 3");
        oMenu.setEnabled(false);
        oMenu.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                m_Windowlocation.x = e.getX();
                m_Windowlocation.y = e.getY();
            }
        });
        oMenu.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                Point p = frame.getLocation();
                frame.setLocation(p.x + e.getX() - m_Windowlocation.x,
                        p.y + e.getY() - m_Windowlocation.y);
            }
        });
        add(oMenu);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                m_Windowlocation.x = e.getX();
                m_Windowlocation.y = e.getY();
            }
        });
        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                Point p = frame.getLocation();
                frame.setLocation(p.x + e.getX() - m_Windowlocation.x,
                        p.y + e.getY() - m_Windowlocation.y);
            }
        });
        JButton mButton = new JButton("_");
        mButton.setEnabled(true);
        mButton.setFocusable(false);
        mButton.setToolTipText("Minimieren");
        mButton.setSize(5,5);
        add(mButton);
        
        mButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                frame.setState(Frame.ICONIFIED);
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
