package severeLobster.frontend.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import severeLobster.backend.spiel.Spielfeld;

public class MainView extends JPanel {

    public MainView() {
        // TODO: Layout wird zwecks Platzhalter auf null gesetzt -> Layout!
        setLayout(null);
        //JPanel spielfeld = new SpielfeldView_fwenisch();
        //spielfeld.setBounds(50, 50, 500, 500);
        SpielfeldView spielfeld = new SpielfeldView(new Spielfeld(20, 15));
        spielfeld.setBounds(50, 50, 500, 500);
        JPanel spielinfo = new SpielinfoView();
        spielinfo.setBounds(550, 50, 200, 500);
        add(spielfeld);
        add(spielinfo);
        setVisible(true);
    }

    public void paintComponent(Graphics g) {
        Image sImage = getToolkit().getImage(
                getClass().getResource("sternenhimmel.jpg"));
        g.drawImage(sImage, 0, 0, this);
    }
}
