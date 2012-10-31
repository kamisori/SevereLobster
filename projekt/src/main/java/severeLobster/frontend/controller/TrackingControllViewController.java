package severeLobster.frontend.controller;

/**
 * Verbindung zwischen Tracking UI und der Schnittstelle im Backend, ueber die
 * gesteuert werden kann.
 * 
 * @author LKleiber
 * 
 */
public class TrackingControllViewController {

    public static final TrackingControllViewController NULL_OBJECT_INSTANCE = new TrackingControllViewController() {
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

    public void setzeTrackingPunkt() {
        // TODO
    }

    public void zurueckZumFehler() {
        // TODO
    }

    public void zurueckZumLetztenTrackingPunkt() {
        // TODO
    }
}
