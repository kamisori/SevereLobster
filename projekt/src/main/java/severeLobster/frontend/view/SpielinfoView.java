package severeLobster.frontend.view;

import infrastructure.ResourceManager;

import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;

public class SpielinfoView extends JPanel {

    private final ResourceManager resourceManager = ResourceManager.get();

    public SpielinfoView() {
        add(new JLabel("Spielinfo"));
        setBackground(Color.GRAY);

        setVisible(true);
    }

    @Override
    public void paintComponent(Graphics g) {
        Image sImage = getToolkit().getImage(
                resourceManager.getGraphic("spielinfo.jpg"));
        g.drawImage(sImage, 0, 0, this);

        Font myFont = new Font("Arial", Font.PLAIN, 22);
        g.setFont(myFont);
        g.setColor(Color.YELLOW);
        g.drawString(System.getProperty("user.name"), 60, 140);
    }

}
