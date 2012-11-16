package severeLobster.backend.spiel;

/**
 * Die Klasse soll eine Strategie zum lösen eines Spielfeldes darstellen
 * Sie kann nacheinander mehrere einzelne SolvingSteps ausführen
 *
 *  @author Christian Lobach
 */

public interface SolvingStrategy {

    public boolean isSolvable(Spielfeld input);
    public Spielfeld solve(Spielfeld input);
}

