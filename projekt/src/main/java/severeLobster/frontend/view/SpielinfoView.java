package severeLobster.frontend.view;

import infrastructure.ResourceManager;

import javax.swing.JLabel;
import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;

public class SpielinfoView extends JPanel {

    private final ResourceManager resourceManager = ResourceManager.get();

    public SpielinfoView() 
    {
    	setLayout(null);
        add(new JLabel("Spielinfo"));
        setBackground(Color.GRAY);
        JPanel trackingView  = new TrackingControllView();
        trackingView.setBounds(0, 365, 200, 131);
        add(trackingView,BorderLayout.SOUTH);
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
                resourceManager.getGraphicURL("spielinfo_untenAbgeschnitten.jpg"));

        /**
         * Ende Test von TrackingControllView
         */

        g.drawImage(sImage, 0, 0, this);

        Font myFont = new Font("Arial", Font.PLAIN, 22);
        g.setFont(myFont);
        g.setColor(Color.YELLOW);
        g.drawString(System.getProperty("user.name"), 60, 140);
    }

}
