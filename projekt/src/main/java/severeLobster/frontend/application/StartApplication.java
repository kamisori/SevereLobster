package severeLobster.frontend.application;

import infrastructure.graphics.GraphicsGetter;

import javax.swing.JFrame;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;

/**
 * 
 * @author Jean-Fabian Wenisch
 * @version 1.0
 */
public class StartApplication extends JFrame implements Runnable {

    /**
     * Startet das Programm(startet Splaschreen und startet Initialisierung)
     * 
     * @param args
     *            - werden momentan nicht verwendet
     * @author fwenisch
     * @version 1.0 08.10.2012
     */
    public static void main(String[] args) {
        Thread Splashscreen = new Thread(new StartApplication());
        Splashscreen.start();

    }

    /**
     * Initialisiert Mainframe & laedt Ressourcen bevor das Mainframe gestartet
     * wird
     * 
     * @author fwenisch
     * @version 1.0 07.10.2012
     */
    @Override
    public void run() {
        Thread SC_MAIN = null;
        setSize(550, 360);
        setLocationRelativeTo(null);
        setUndecorated(true);
        setVisible(true);

        try {
            // TODO: Bilder & Sonstige Sachen laden
            SC_MAIN = new Thread(new MainFrame());
            // Zum Testen auskommentiert
            // Thread.sleep(10000);
        } catch (Exception e) {
            dispose();
            e.printStackTrace();
        } finally {
            dispose();
            SC_MAIN.start();
        }
    }

    /**
     * Wird ueberschrieben um Splash anzuzeigen
     * 
     * @author fwenisch
     * @version 1.0 08.10.2012
     */
    public void paint(Graphics g) {
        Image sImage = getToolkit().getImage(
                GraphicsGetter.getGraphic("Splashscreen.jpg"));
        g.drawImage(sImage, 0, 0, this);
        Font myFont = new Font("Arial", Font.PLAIN, 12);
        g.setFont(myFont);
        g.setColor(Color.YELLOW);
        g.drawString(
                "Sternenkonstellation wird für "
                        + System.getProperty("user.name") + " berechnet...", 5,
                280);
    }

}
