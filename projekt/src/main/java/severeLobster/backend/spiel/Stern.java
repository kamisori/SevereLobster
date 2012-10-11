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

	@Override
	public String toString() {
		return "Stern";
	}
	@Override
	public boolean equals(final Object obj) {
		return (null != obj && obj instanceof Stern);
	}
}
