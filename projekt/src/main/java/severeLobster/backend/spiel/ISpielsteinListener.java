package severeLobster.backend.spiel;

import java.util.EventListener;

/**
 * Schnittstelle, um bei Statusaenderungen eines Spielfeldes informiert zu
 * werden.
 * 
 * @author Lutz Kleiber
 * 
 */
public interface ISpielsteinListener extends EventListener {
	public void spielsteinStateChanged(Spielstein spielstein, SpielsteinState newState);
}
