package severeLobster.frontend.view;

import infrastructure.ResourceManager;
import severeLobster.backend.spiel.Spiel;
import severeLobster.backend.spiel.SternenSpielApplicationBackend;
import severeLobster.frontend.controller.SpielfeldController;
import severeLobster.frontend.controller.SpielmodusController;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;

public class MainView extends JPanel {

    private final ResourceManager resourceManager = ResourceManager.get();
    private final SternenSpielApplicationBackend backend;

    public MainView() throws IOException {

        setLayout(null);
        // JPanel spielfeld = new SpielfeldView_fwenisch();
        /**
         * Nur zum Testen
         */
        backend = new SternenSpielApplicationBackend();
        backend.startNewSpielFrom("Standardspiel01");
        /*
         * backend.getSpiel().initializeNewSpielfeld(20, 18);
         * backend.getSpiel().setSpielmodus(SpielmodusEnumeration.EDITIEREN);
         */
        final SpielfeldView view = new SpielfeldView(backend.getSpiel()
                .getSpielfeld());
        new SpielfeldController(view, backend);

        final SpielmodusViewPanel spielmodusView = new SpielmodusViewPanel();
        new SpielmodusController(spielmodusView, backend);

        JPanel spielfeld = new JPanel(false);
        spielfeld.setOpaque(false);
        spielfeld.setLayout(new BoxLayout(spielfeld, BoxLayout.Y_AXIS));
        spielfeld.add(view);
        //spielfeld.add(spielmodusView);

        /**
         * Ende Test
         */

        spielfeld.setBounds(50, 50, 500, 500);
        JPanel spielinfo = new SpielinfoView();
        spielinfo.setBounds(550, 50, 200, 500);
        add(spielfeld);
        add(spielinfo);
        setVisible(true);
    }

    public MainView(final Spiel spiel) throws IOException {
        // TODO: Layout wird zwecks Platzhalter auf null gesetzt -> Layout!
        setLayout(null);
        // JPanel spielfeld = new SpielfeldView_fwenisch();
        /**
         * Nur zum Testen
         */
        backend = new SternenSpielApplicationBackend();
        //backend.startNewSpielFrom("Standardspiel01");
        backend.setSpiel(spiel);
        /*
         * backend.getSpiel().initializeNewSpielfeld(20, 18);
         * backend.getSpiel().setSpielmodus(SpielmodusEnumeration.EDITIEREN);
         */
        final SpielfeldView view = new SpielfeldView(backend.getSpiel()
                .getSpielfeld());
        new SpielfeldController(view, backend);

        final SpielmodusViewPanel spielmodusView = new SpielmodusViewPanel();

        new SpielmodusController(spielmodusView, backend);

        JPanel spielfeld = new JPanel(false);
        spielfeld.setOpaque(false);
        spielfeld.setLayout(new BoxLayout(spielfeld, BoxLayout.Y_AXIS));
        spielfeld.add(view);
        spielfeld.add(spielmodusView);

        /**
         * Ende Test
         */

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
                resourceManager.getGraphicURL("sternenhimmel.jpg"));
        g.drawImage(sImage, 0, 0, this);
    }

    public Spiel getCurrentSpiel() {
        return backend.getSpiel();
    }
}
