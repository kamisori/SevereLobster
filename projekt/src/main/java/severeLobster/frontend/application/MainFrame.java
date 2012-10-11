/*
 * SC_MAIN.java
 *
 * Created on 17.10.2010, 20:21:14
 */

package severeLobster.frontend.application;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import java.awt.Rectangle;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.net.URL;

/**
 *Initialisiert Grafiken
 *@author Jean-Fabian Wenisch
 */
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import severeLobster.frontend.view.MainView;

public class MainFrame extends JMenuBar implements Runnable {
    JMenu jm_Spiel;
    JMenu jm_Grafik;
    JMenu jm_Eigenschaften;
    static JFrame frame;
    private static MainView MainPanel;
    private static Point m_Windowlocation;

    /**
     * Initialisiert das Men�
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
        MainPanel = new MainView();
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
         * JMen� wird mit allen Eintr�gen erzeugt Actionlistener f�r die
         * Men�eintr�ge wird hinzugef�gt
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
            public void mousePressed(MouseEvent e) {
                m_Windowlocation.x = e.getX();
                m_Windowlocation.y = e.getY();
            }
        });
        oMenu.addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent e) {
                Point p = frame.getLocation();
                frame.setLocation(p.x + e.getX() - m_Windowlocation.x,
                        p.y + e.getY() - m_Windowlocation.y);
            }
        });
        add(oMenu);
        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                m_Windowlocation.x = e.getX();
                m_Windowlocation.y = e.getY();
            }
        });
        addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent e) {
                Point p = frame.getLocation();
                frame.setLocation(p.x + e.getX() - m_Windowlocation.x,
                        p.y + e.getY() - m_Windowlocation.y);
            }
        });
        frame.setJMenuBar(this);
    }

    /**
     * Frame wird initialisiert & Hauptpanel wird hinzugef�gt Au�erdem werden
     * Mouselistener hinzugef�gt mit denen sich das Frame verschieben l�sst
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
    public void run() {
        frame.setVisible(true);
        init();
    }
}