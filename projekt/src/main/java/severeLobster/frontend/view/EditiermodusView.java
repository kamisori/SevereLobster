package severeLobster.frontend.view;

import infrastructure.ResourceManager;
import infrastructure.constants.GlobaleKonstanten;
import infrastructure.constants.enums.SchwierigkeitsgradEnumeration;
import severeLobster.backend.spiel.SternenSpielApplicationBackend;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;

/**
 * View des Editiermodus
 *
 * @author Lars Schlegelmilch
 */
public class EditiermodusView extends JPanel {

    private final ResourceManager resourceManager = ResourceManager.get();
    private final SternenSpielApplicationBackend backend;

    public EditiermodusView(SternenSpielApplicationBackend backend) {
        setLayout(null);
        this.backend = backend;
        setOpaque(false);
        setPreferredSize(new Dimension(200, 500));
        setVisible(true);
        JLabel l = new JLabel(resourceManager.getText("check.loesungsweg.btn.text"));
        l.setForeground(Color.YELLOW);
        l.setSize(80,50);
        l.setOpaque(false);
        l.setLocation(15, 288);
        l.setVisible(true);
        JButton t = new JButton("Check"); //TODO durch Image ersetzen
        t.setSize(70,50);
        t.setOpaque(false);
        t.setLocation(90, 288);
        t.setVisible(true);
        add(l);
        add(t);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d=(Graphics2D)g;

        /**
         * Anfang Test von TrackingControllView
         */
        int sterne = backend.getSpiel().getSpielfeld().countSterne();
        SchwierigkeitsgradEnumeration schwierigkeitsgrad = backend.getSpiel().getSchwierigkeitsgrad();

        Image sImage = getToolkit().getImage(resourceManager.getGraphicURL("editview.jpg"));
        /**
         * Ende Test von TrackingControllView
         */
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.drawImage(sImage, 0, 0, this);
        g2d.setFont(GlobaleKonstanten.FONT);
        g2d.setColor(Color.BLACK);

        g2d.drawString(resourceManager.getText("star.count")
                + " \t" + String.valueOf(sterne), 30, 170);
        g2d.drawString(resourceManager.getText("actual.difficulty"),30, 220);
        g2d.drawString(schwierigkeitsgrad.toString(),30, 239);

        g2d.setColor(Color.YELLOW);
        g2d.setFont(GlobaleKonstanten.FONT.deriveFont(Font.BOLD));
        g2d.drawString(resourceManager.getText("puzzle.edit.title"), 30, 70);

        validate();
        repaint();
    }

}
