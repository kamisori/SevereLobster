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

	/**
	 * Liefert immer dieselbe Instanz. Da sich die Instanzen nicht in ihren
	 * Zust�nden unterscheiden, kann man immer die selbe Instanz nehmen.
	 * 
	 * @return
	 */
	public static NullState getInstance() {
		return INSTANCE;
	}

	@Override
	public String toString() {
		return "NullState";
	}

	@Override
	public boolean equals(final Object obj) {
		return (null != obj && obj instanceof NullState);
	}
}
