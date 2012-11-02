package severeLobster.frontend.view;

import infrastructure.ResourceManager;
import infrastructure.constants.GlobaleKonstanten;

import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

public class SpielinfoView extends JPanel {

    private final ResourceManager resourceManager = ResourceManager.get();

    public SpielinfoView(final TrackingControllView trackingControllView) {
        setLayout(null);
        add(new JLabel("Spielinfo"));
        setBackground(Color.GRAY);
        JPanel trackingView = trackingControllView;
        trackingView.setBounds(0, 365, 200, 131);
        add(trackingView, BorderLayout.SOUTH);
        setPreferredSize(new Dimension(200, 500));
        setVisible(true);
    }

    @Override
    public void paintComponent(Graphics g) {
        // Auskommentiert fuer Test von TrackingControllView
        // Image sImage = getToolkit().getImage(
        // resourceManager.getGraphicURL("spielinfo.jpg"));
        /**
         * Anfang Test von TrackingControllView
         */
        Image sImage = getToolkit().getImage(
                resourceManager
                        .getGraphicURL("spielinfo_untenAbgeschnitten.jpg"));

        /**
         * Ende Test von TrackingControllView
         */

        g.drawImage(sImage, 0, 0, this);

        g.setFont(GlobaleKonstanten.FONT);
        g.setColor(Color.YELLOW);
        g.drawString(System.getProperty("user.name"), 60, 140);
    }

}
