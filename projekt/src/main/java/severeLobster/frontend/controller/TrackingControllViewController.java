package severeLobster.frontend.controller;

import severeLobster.backend.spiel.SternenSpielApplicationBackend;

/**
 * Verbindung zwischen Tracking UI und der Schnittstelle im Backend, ueber die
 * gesteuert werden kann.
 * 
 * @author LKleiber
 * 
 */
public class TrackingControllViewController {

    public static final TrackingControllViewController NULL_OBJECT_INSTANCE = new TrackingControllViewController(
            null) {
        public void setzeTrackingPunkt() {
            // Dummy
        }

        public void zurueckZumFehler() {
            // Dummy
        }

        public void zurueckZumLetztenTrackingPunkt() {
            // Dummy
        }
    };

    private final SternenSpielApplicationBackend backend;

    public TrackingControllViewController(
            final SternenSpielApplicationBackend backend) {
        this.backend = backend;
    }

    public void setzeTrackingPunkt() {
        backend.setzeTrackingPunkt();
    }

    public void zurueckZumFehler() {
        backend.zurueckZumLetztenFehlerfreienSpielzug();
    }

    public void zurueckZumLetztenTrackingPunkt() {
        backend.zurueckZumLetztenTrackingPunkt();
    }
}
