package severeLobster.backend.spiel;

/**
 * Sternenspielstein
 * 
 * @author Lars Schlegelmilch
 */
public class Stern extends SpielsteinState {

	private static final Stern INSTANCE = new Stern();

	public static Stern getInstance() {
		return INSTANCE;
	}
}
