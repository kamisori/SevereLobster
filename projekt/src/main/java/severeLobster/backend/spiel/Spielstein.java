package severeLobster.backend.spiel;

import javax.swing.event.EventListenerList;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

/**
 * Abstrakter Spielstein eines Spielfeldes - Kann Sichtbar sein, oder auch
 * nicht.
 * 
 * @author Lars Schlegelmilch, Lutz Kleiber
 */
public class Spielstein implements Serializable {

	private static final long serialVersionUID = -8849754506328607439L;
	private final EventListenerList listeners = new EventListenerList();
	private SpielsteinState visibleState;
	private SpielsteinState realState;

	/**
	 * Initialisiert beide state variablen mit NullState (Blank).
	 */
	public Spielstein() {
		visibleState = NullState.getInstance();
		realState = NullState.getInstance();
	}

	public SpielsteinState getVisibleState() {
		return visibleState;
	}

	public void setVisibleState(SpielsteinState visibleState) {
		this.visibleState = visibleState;
		fireSpielsteinStateChanged(visibleState);
	}

	public SpielsteinState getRealState() {
		return realState;
	}

	public void setRealState(SpielsteinState realState) {
		this.realState = realState;
	}

	/**
	 * Gibt eine Liste mit den f�r diesen Spielstein aktuell ausw�hlbaren/
	 * m�glichen Stati zur�ck. Der Status kann dann �ber {@link setVisibleState}
	 * ge�ndert werden.
	 * 
	 * @return Eine Liste mit den f�r diesen Spielstein aktuell ausw�hlbaren
	 *         Stati.
	 */
	public List<? extends SpielsteinState> listAvailableStates() {
		final List<SpielsteinState> defaultTestResult = new LinkedList<SpielsteinState>();
		defaultTestResult.add(new NullState());
		defaultTestResult.add(new Ausschluss());
		defaultTestResult.add(new Stern());
		defaultTestResult.addAll(Pfeil.listAlleMoeglichenPfeile());
		return defaultTestResult;

	}

	/**
	 * Benachrichtigt alle Listener dieses Spielsteins �ber einen neuen Wert von
	 * "visibleState". Implementation ist glaube ich aus JComponent oder
	 * Component kopiert.
	 * 
	 * @param newState
	 *            - Der neue Status, der an die Listener mitgeteilt wird.
	 */
	protected void fireSpielsteinStateChanged(final SpielsteinState newState) {
		/** Gibt ein Array zur�ck, das nicht null ist */
		final Object[] currentListeners = listeners.getListenerList();
		/**
		 * Rufe die Listener auf, die als ISpielfeldListener angemeldet sind.
		 */
		for (int i = currentListeners.length - 2; i >= 0; i -= 2) {
			if (currentListeners[i] == ISpielsteinListener.class) {
				((ISpielsteinListener) currentListeners[i + 1])
						.spielsteinStateChanged(this, newState);
			}
		}
	}

	/**
	 * F�gt listener zu der Liste hinzu.
	 * 
	 * @param listener
	 */
	public void addSpielsteinListener(final ISpielsteinListener listener) {
		listeners.add(ISpielsteinListener.class, listener);
	}

	/**
	 * Entfernt listener von der Liste.
	 * 
	 * @param listener
	 */
	public void removeSpielsteinListener(final ISpielsteinListener listener) {
		listeners.remove(ISpielsteinListener.class, listener);
	}

}
