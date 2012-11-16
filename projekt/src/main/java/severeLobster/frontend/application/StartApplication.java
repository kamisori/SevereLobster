package severeLobster.frontend.application;

import infrastructure.ResourceManager;

import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import infrastructure.components.FTPConnector;

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

    private final ResourceManager resourceManager = ResourceManager.get();

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
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace(); // TODO ...
        } catch (InstantiationException e) {
            e.printStackTrace(); // TODO ...
        } catch (IllegalAccessException e) {
            e.printStackTrace(); // TODO ...
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace(); // TODO ...
        }

        Thread SC_MAIN = null;
        setSize(550, 360);
        setLocationRelativeTo(null);
        setUndecorated(true);
        setVisible(true);

        try {
            // TODO: Bilder & Sonstige Sachen laden
            SC_MAIN = new Thread(new MainFrame());
          MainFrame.oFTP = new FTPConnector("ftp.strato.de",
                    "user@sternenhimmel-deluxe.de", "12YXasdfg", 21);
            
            Thread.sleep(2000);
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
                resourceManager.getGraphicURL("Splashscreen.jpg"));
        g.drawImage(sImage, 0, 0, this);
        Font myFont = new Font("Arial", Font.PLAIN, 12);
        g.setFont(myFont);
        g.setColor(Color.YELLOW);
        g.drawString(
                resourceManager.getText("splashscreen.text.1") + " "
                        + System.getProperty("user.name") + " "
                        + resourceManager.getText("splashscreen.text.2"), 5,
                280);
    }

}
