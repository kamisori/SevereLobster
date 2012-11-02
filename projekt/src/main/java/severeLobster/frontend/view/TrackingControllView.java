package severeLobster.frontend.view;

import infrastructure.ResourceManager;
import severeLobster.frontend.controller.TrackingControllViewController;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Erstes Skelett fuer das Tracking Kontroll UI.
 * 
 * @author Lutz Kleiber
 * 
 */
public class TrackingControllView extends JPanel {

    private TrackingControllViewController currentController;

    private static final ResourceManager resourceManager = ResourceManager.get();

    public TrackingControllView() {
        setBackground(Color.DARK_GRAY);
        setLayout(new FlowLayout(FlowLayout.CENTER));

        // Zurueck zum letzten Tracking punkt Button setzen
        {
            ImageIcon zurueckZumLetztenPunktIcon = ResourceManager.get()
                    .getImageIcon("PfeilLinks48.png");
            final JButton zurueckZumLetztenPunktBtn = new JButton(
                    zurueckZumLetztenPunktIcon);
            zurueckZumLetztenPunktBtn
                    .setToolTipText(resourceManager.getText("tracking.backToLastTrackingPoint"));
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
            setzeTrackingPunktBtn.setToolTipText(resourceManager.getText("tracking.setPoint"));
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
                    "DoppelPfeilLinks48.png");
            final JButton zurueckZumFehlerBtn = new JButton(zurueckZumFehler);
            zurueckZumFehlerBtn.setToolTipText(resourceManager.getText("tracking.backToMistake"));
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
