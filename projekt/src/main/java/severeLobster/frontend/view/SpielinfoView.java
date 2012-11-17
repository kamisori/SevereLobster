package severeLobster.frontend.view;

import infrastructure.ResourceManager;
import infrastructure.constants.GlobaleKonstanten;
import severeLobster.backend.spiel.SternenSpielApplicationBackend;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.net.URL;

public class SpielinfoView extends JPanel {

    private final ResourceManager resourceManager = ResourceManager.get();
    private final SternenSpielApplicationBackend backend;
    private URL avatar = resourceManager.getUserAvatar();

    public SpielinfoView(SternenSpielApplicationBackend backend) {
        setLayout(null);
        this.backend = backend;
        setBackground(Color.BLACK);
        setPreferredSize(new Dimension(200, 500));
        setVisible(true);
    }

    public SpielinfoView(final TrackingControllView trackingControllView,
            SternenSpielApplicationBackend backend) {
        setLayout(null);
        this.backend = backend;
        setBackground(Color.BLACK);
        JPanel trackingView = trackingControllView;
        trackingView.setBounds(0, 365, 200, 131);
        add(trackingView, BorderLayout.SOUTH);
        setPreferredSize(new Dimension(200, 500));
        setVisible(true);
    }

    public void changeAvatar(URL avatar) {
        this.avatar = avatar;
        resourceManager.setAvatar(avatar);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        /**
         * Anfang Test von TrackingControllView
         */
        int zugcount = backend.getSpiel().getAnzahlZuege();
        String strSpielZeit = backend.getSpiel().getSpielZeit();

        Image sImage = getToolkit().getImage(avatar);

        /**
         * Ende Test von TrackingControllView
         */

        g.drawImage(sImage, 0, 0, this);

        g.setFont(GlobaleKonstanten.FONT);
        g.setColor(Color.YELLOW);
        g.drawString(System.getProperty("user.name"), 90, 80);
        g.setColor(Color.BLACK);
        g.drawString(resourceManager.getText("try") + " " + String.valueOf(zugcount), 40, 246);
        g.drawString(resourceManager.getText("time") + " " + strSpielZeit + " "
                   + resourceManager.getText("seconds"), 40, 286);
        validate();
        repaint();
    }

}
