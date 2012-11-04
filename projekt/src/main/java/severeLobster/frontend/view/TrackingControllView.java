package severeLobster.frontend.view;

import infrastructure.ResourceManager;
import infrastructure.constants.GlobaleKonstanten;
import severeLobster.frontend.controller.TrackingControllViewController;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Image;
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
    private static final ResourceManager resourceManager = ResourceManager.get();
    private TrackingControllViewController currentController;
    private final JButton zurueckZumLetztenPunktBtn;
    private final JButton setzeTrackingPunktBtn;
    private final JButton zurueckZumFehlerBtn;

    public TrackingControllView() {
        setBackground(Color.BLACK);
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
                    .setToolTipText(resourceManager.getText("tracking.backToLastTrackingPoint"));
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
            setzeTrackingPunktBtn.setToolTipText(resourceManager.getText("tracking.setPoint"));
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
            zurueckZumFehlerBtn.setToolTipText(resourceManager.getText("tracking.backToMistake"));
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
    @Override
    public void paintComponent(Graphics g) {
    	super.paintComponent(g);
   Image sImage = getToolkit().getImage(
                resourceManager
                        .getGraphicURL("trackingview.jpg"));

        g.drawImage(sImage, 0, 0, this);
      validate();
        repaint();
    }
}
