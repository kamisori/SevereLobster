package severeLobster.frontend.view;

import infrastructure.ResourceManager;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import severeLobster.frontend.controller.TrackingControllViewController;

/**
 * Erstes Skelett fuer das Tracking Kontroll UI.
 * 
 * @author Lutz Kleiber
 * 
 */
public class TrackingControllView extends JPanel {

    private TrackingControllViewController currentController;

    public TrackingControllView() {
        setBackground(Color.DARK_GRAY);
        setLayout(new FlowLayout(FlowLayout.CENTER));

        // Zurueck zum letzten Tracking punkt Button setzen
        {
            ImageIcon zurueckZumLetztenPunktIcon = ResourceManager.get()
                    .getImageIcon("Pfeillinks48.png");
            final JButton zurueckZumLetztenPunktBtn = new JButton(
                    zurueckZumLetztenPunktIcon);
            zurueckZumLetztenPunktBtn
                    .setToolTipText("Zurueck zum letzten Trackingpunkt");
            zurueckZumLetztenPunktBtn.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent arg0) {
                    getCurrentTrackingController()
                            .zurueckZumLetztenTrackingPunkt();
                }
            });
            zurueckZumLetztenPunktBtn.setOpaque(false);
            this.add(zurueckZumLetztenPunktBtn);
        }
        // Tracking Punkt Button setzen
        {
            ImageIcon trackingPunktIcon = ResourceManager.get().getImageIcon(
                    "SetzeTrackingPointIcon48.png");
            final JButton setzeTrackingPunktBtn = new JButton(trackingPunktIcon);
            setzeTrackingPunktBtn.setToolTipText("Setze Trackingpunkt");
            setzeTrackingPunktBtn.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent arg0) {
                    getCurrentTrackingController().setzeTrackingPunkt();
                }
            });
            setzeTrackingPunktBtn.setOpaque(false);
            this.add(setzeTrackingPunktBtn);
        }
        // Zurueck zum Fehler Button setzen
        {
            ImageIcon zurueckZumFehler = ResourceManager.get().getImageIcon(
                    "DoppelPfeillinks48.png");
            final JButton zurueckZumFehlerBtn = new JButton(zurueckZumFehler);
            zurueckZumFehlerBtn.setToolTipText("Zurueck zum Fehler");
            zurueckZumFehlerBtn.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent arg0) {
                    getCurrentTrackingController().zurueckZumFehler();
                }
            });
            zurueckZumFehlerBtn.setOpaque(false);
            this.add(zurueckZumFehlerBtn);
        }

    }

    private TrackingControllViewController getCurrentTrackingController() {
        if (null == currentController) {
            return TrackingControllViewController.NULL_OBJECT_INSTANCE;
        } else {
            return this.currentController;
        }

    }

    public void setTrackingControllViewController(
            final TrackingControllViewController controller) {
        this.currentController = controller;
    }

}
