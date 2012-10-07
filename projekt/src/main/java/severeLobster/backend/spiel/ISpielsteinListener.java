package severeLobster.backend.spiel;

import java.util.EventListener;

/**
 * Schnittstelle, um bei Status�nderungen eines Spielfeldes informiert zu
 * werden.
 * 
 * @author Lutz Kleiber
 * 
 */
public interface ISpielsteinListener extends EventListener {
	void spielsteinStateChanged(Spielstein spielstein, SpielsteinState newState);
}
