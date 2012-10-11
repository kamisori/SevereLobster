package severeLobster.backend.spiel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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

	/**
	 * Es wird immer dieselbst Liste zur�ckgegeben, daher muss sie unver�nderbar
	 * sein.
	 */
	private static final List<Pfeil> ALLE_MOEGLICHEN_PFEIL_INSTANZEN;
	static {
		final List<Pfeil> modifiableList = new ArrayList<Pfeil>(8);
		modifiableList.add(NORD_PFEIL);
		modifiableList.add(NORD_OST_PFEIL);
		modifiableList.add(OST_PFEIL);
		modifiableList.add(SUED_OST_PFEIL);
		modifiableList.add(SUED_PFEIL);
		modifiableList.add(SUED_WEST_PFEIL);
		modifiableList.add(WEST_PFEIL);
		modifiableList.add(NORD_WEST_PFEIL);
		ALLE_MOEGLICHEN_PFEIL_INSTANZEN = Collections
				.unmodifiableList(modifiableList);
	}

	private final PfeilrichtungEnumeration pfeilrichtung;

	/**
	 * Statt des Konstruktors lieber die statischen Factory Methoden nehmen, da
	 * spart man sich das setzen von pfeilrichtung.
	 * 
	 * @param pfeilrichtung
	 */
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

	/**
	 * Liste ist unver�nderbar. �nderungsversuche f�hren zu einer
	 * UnsupportedOperationException.
	 * 
	 * @return
	 */
	public static List<Pfeil> listAlleMoeglichenPfeile() {
		return ALLE_MOEGLICHEN_PFEIL_INSTANZEN;
	}

	@Override
	public String toString() {
		return "Pfeil:" + getPfeilrichtung().toString();
	}

	@Override
	public boolean equals(Object obj) {
		boolean result = false;
		if (null != obj && obj instanceof Pfeil) {
			/**
			 * Vergleiche die fremde und die eigene Pfeilrichtung.
			 */
			if (((Pfeil) obj).pfeilrichtung.equals(this.pfeilrichtung)) {
				result = true;
			}
		}
		return result;
	}

}
