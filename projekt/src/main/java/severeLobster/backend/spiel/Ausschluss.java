package severeLobster.backend.spiel;

/**
 * Ausschlussspielstein - Kennzeichnet ein Feld, auf dem sich kein Stern
 * befindet
 * 
 * @author Lars Schlegelmilch
 */
public class Ausschluss extends SpielsteinState {

	private static final Ausschluss INSTANCE = new Ausschluss();

	public static Ausschluss getInstance() {
		return INSTANCE;
	}
}
