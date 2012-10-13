package severeLobster.frontend.view;

import infrastructure.graphics.GraphicsGetter;
import severeLobster.backend.spiel.Spiel;
import severeLobster.backend.spiel.Spielfeld;

import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Image;

public class MainView extends JPanel {

    public MainView(final Spiel spiel) {
        // TODO: Layout wird zwecks Platzhalter auf null gesetzt -> Layout!
        setLayout(null);
        JPanel spielfeld = new SpielfeldView_fwenisch();

        spielfeld.setBounds(50, 50, 500, 500);
        JPanel spielinfo = new SpielinfoView();
        spielinfo.setBounds(550, 50, 200, 500);
        add(spielfeld);
        add(spielinfo);
        setVisible(true);
    }

    @Override
    public void paintComponent(Graphics g) {
        Image sImage = getToolkit().getImage(
                GraphicsGetter.getGraphic("sternenhimmel.jpg"));
        g.drawImage(sImage, 0, 0, this);
    }
}
