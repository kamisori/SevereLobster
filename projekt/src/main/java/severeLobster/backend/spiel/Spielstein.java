package severeLobster.backend.spiel;

/**
 * Abstrakter Spielstein eines Spielfeldes - Kann Sichtbar sein, oder auch nicht.
 *
 * @author Lars Schlegelmilch
 */
public abstract class Spielstein {

    public Spielstein(boolean sichtbar) {
        this.sichtbar = sichtbar;
    }

    private boolean sichtbar;

    public boolean isSichtbar() {
        return sichtbar;
    }

    public void setSichtbar(boolean sichtbar) {
        this.sichtbar = sichtbar;
    }
}
