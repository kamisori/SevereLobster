package severeLobster.backend.spiel;

import javax.swing.event.EventListenerList;
import java.io.Serializable;

/**
 * Abstrakter Spielstein eines Spielfeldes - Kann Sichtbar sein, oder auch nicht.
 *
 * @author Lars Schlegelmilch
 */
public class Spielstein implements Serializable {

    private EventListenerList listeners;
    private SpielsteinState visibleState;
    private SpielsteinState realState;

    public EventListenerList getListeners() {
        return listeners;
    }

    public void setListeners(EventListenerList listeners) {
        this.listeners = listeners;
    }

    public SpielsteinState getVisibleState() {
        return visibleState;
    }

    public void setVisibleState(SpielsteinState visibleState) {
        this.visibleState = visibleState;
    }

    public SpielsteinState getRealState() {
        return realState;
    }

    public void setRealState(SpielsteinState realState) {
        this.realState = realState;
    }
}
