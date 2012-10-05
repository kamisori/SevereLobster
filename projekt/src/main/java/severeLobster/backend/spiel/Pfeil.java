package severeLobster.backend.spiel;

import com.google.common.base.Preconditions;

/**
 * Pfeilspielstein - Zeigt immer auf mindestens einen Pfeil
 *
 * @author Lars Schlegelmilch
 */
public class Pfeil extends Spielstein {

    public Pfeil(boolean sichtbar) {
        super(sichtbar);
    }

    @Override
    public boolean isSichtbar() {
        return true;
    }

    @Override
    public void setSichtbar(boolean sichtbar) {
        Preconditions.checkState(sichtbar);
    }
}
