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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Erstes Skelett fuer das Tracking Kontroll UI.
 * 
 * @author Lutz Kleiber
 * 
 */
public class TrackingControllView extends JPanel {

    private TrackingControllViewController currentController;
    private final JButton zurueckZumLetztenPunktBtn;
    private final JButton setzeTrackingPunktBtn;
    private final JButton zurueckZumFehlerBtn;

    public TrackingControllView() {
        setBackground(Color.DARK_GRAY);
        setLayout(new FlowLayout(FlowLayout.CENTER));

        final AnAusAuswahl auswahl = new AnAusAuswahl(true);
        auswahl.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                zurueckZumLetztenPunktBtn.setVisible(!auswahl.isSelected());
                setzeTrackingPunktBtn.setVisible(!auswahl.isSelected());
                zurueckZumFehlerBtn.setVisible(auswahl.isSelected());
            }
        });
        this.add(auswahl);

        // Zurueck zum letzten Tracking punkt Button setzen
        {
            ImageIcon zurueckZumLetztenPunktIcon = ResourceManager.get()
                    .getImageIcon("PfeilLinks48.png");
            zurueckZumLetztenPunktBtn = new JButton(
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
            zurueckZumLetztenPunktBtn.setVisible(false);
            zurueckZumLetztenPunktBtn.setOpaque(false);
            this.add(zurueckZumLetztenPunktBtn);
        }
        // Tracking Punkt Button setzen
        {
            ImageIcon trackingPunktIcon = ResourceManager.get().getImageIcon(
                    "SetzeTrackingPointIcon48.png");
            setzeTrackingPunktBtn = new JButton(trackingPunktIcon);
            setzeTrackingPunktBtn.setToolTipText("Setze Trackingpunkt");
            setzeTrackingPunktBtn.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent arg0) {
                    getCurrentTrackingController().setzeTrackingPunkt();
                }
            });
            setzeTrackingPunktBtn.setVisible(false);
            setzeTrackingPunktBtn.setOpaque(false);
            this.add(setzeTrackingPunktBtn);
        }
        // Zurueck zum Fehler Button setzen
        {
            ImageIcon zurueckZumFehler = ResourceManager.get().getImageIcon(
                    "DoppelPfeilLinks48.png");
            zurueckZumFehlerBtn = new JButton(zurueckZumFehler);
            zurueckZumFehlerBtn.setToolTipText("Zurueck zum Fehler");
            zurueckZumFehlerBtn.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent arg0) {
                    getCurrentTrackingController().zurueckZumFehler();
                }
            });
            zurueckZumFehlerBtn.setOpaque(false);
            zurueckZumFehlerBtn.setVisible(true);
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
