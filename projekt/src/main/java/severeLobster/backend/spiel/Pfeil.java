package severeLobster.backend.spiel;

import infrastructure.constants.enums.PfeilrichtungEnumeration;

/**
 * Pfeilspielstein - Zeigt immer auf mindestens einen Pfeil
 * 
 * @author Lars Schlegelmilch, Lutz Kleiber
 */
public class Pfeil extends SpielsteinState {

	private static final Pfeil NORD_PFEIL = new Pfeil(
			PfeilrichtungEnumeration.NORD);
	private static final Pfeil NORD_OST_PFEIL = new Pfeil(
			PfeilrichtungEnumeration.NORDOST);
	private static final Pfeil OST_PFEIL = new Pfeil(
			PfeilrichtungEnumeration.OST);
	private static final Pfeil SUED_OST_PFEIL = new Pfeil(
			PfeilrichtungEnumeration.SUEDOST);
	private static final Pfeil SUED_PFEIL = new Pfeil(
			PfeilrichtungEnumeration.SUED);

	private static final Pfeil SUED_WEST_PFEIL = new Pfeil(
			PfeilrichtungEnumeration.SUEDWEST);
	private static final Pfeil WEST_PFEIL = new Pfeil(
			PfeilrichtungEnumeration.WEST);
	private static final Pfeil NORD_WEST_PFEIL = new Pfeil(
			PfeilrichtungEnumeration.NORDWEST);

	private final PfeilrichtungEnumeration pfeilrichtung;

	public Pfeil(final PfeilrichtungEnumeration pfeilrichtung) {
		if (null == pfeilrichtung) {
			throw new NullPointerException("Pfeilrichtung is null");
		}
		this.pfeilrichtung = pfeilrichtung;
	}

	public PfeilrichtungEnumeration getPfeilrichtung() {
		return pfeilrichtung;
	}

	public static Pfeil getNordPfeil() {
		return NORD_PFEIL;
	}

	public static Pfeil getNordOstPfeil() {
		return NORD_OST_PFEIL;
	}

	public static Pfeil getOstPfeil() {
		return OST_PFEIL;
	}

	public static Pfeil getSuedOstPfeil() {
		return SUED_OST_PFEIL;
	}

	public static Pfeil getSuedPfeil() {
		return SUED_PFEIL;
	}

	public static Pfeil getSuedWestPfeil() {
		return SUED_WEST_PFEIL;
	}

	public static Pfeil getWestPfeil() {
		return WEST_PFEIL;
	}

	public static Pfeil getNordWestPfeil() {
		return NORD_WEST_PFEIL;
	}

}
