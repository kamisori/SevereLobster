package severeLobster.frontend.view;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
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

    public TrackingControllView() 
    {
        setBackground(Color.DARK_GRAY);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        // Tracking Punkt Button setzen
        {
            final JButton setzeTrackingPunktBtn = new JButton(
                    "Setze Trackingpunkt");
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
            final JButton zurueckZumFehlerBtn = new JButton("< zum Fehler");
            zurueckZumFehlerBtn.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent arg0) {
                    getCurrentTrackingController().zurueckZumFehler();
                }
            });
            zurueckZumFehlerBtn.setOpaque(false);
            this.add(zurueckZumFehlerBtn);
        }
        // Zurueck zum letzten Tracking punkt Button setzen
        {
            final JButton zurueckZumLetztenTrackingPunkt = new JButton(
                    "<letzter Tracking Punkt");
            zurueckZumLetztenTrackingPunkt
                    .addActionListener(new ActionListener() {

                        @Override
                        public void actionPerformed(ActionEvent arg0) {
                            getCurrentTrackingController()
                                    .zurueckZumLetztenTrackingPunkt();
                        }
                    });
            zurueckZumLetztenTrackingPunkt.setOpaque(false);
            this.add(zurueckZumLetztenTrackingPunkt);
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
