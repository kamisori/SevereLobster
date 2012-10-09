package severeLobster.backend.spiel;

/**
 * Wenn bisher noch kein anderer Status f�r das Feld gesetzt wurde oder hier
 * einfach nichts ist. Hie� vorher BlankState.
 * 
 * @author Lutz Kleiber
 * 
 */
public class NullState extends SpielsteinState {

	private static final NullState INSTANCE = new NullState();

	public static NullState getInstance() {
		return INSTANCE;
	}
}
