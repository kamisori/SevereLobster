package severeLobster.backend.spiel;

import infrastructure.constants.enums.PfeilrichtungEnumeration;

/**
 * Pfeilspielstein - Zeigt immer auf mindestens einen Pfeil
 *
 * @author Lars Schlegelmilch
 */
public class Pfeil extends SpielsteinState {

    private PfeilrichtungEnumeration pfeilrichtung;

    public PfeilrichtungEnumeration getPfeilrichtung() {
        return pfeilrichtung;
    }

    public void setPfeilrichtung(PfeilrichtungEnumeration pfeilrichtung) {
        this.pfeilrichtung = pfeilrichtung;
    }
}
