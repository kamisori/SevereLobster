package severeLobster.backend.spiel;

import infrastructure.constants.enums.PfeilrichtungEnumeration;
import infrastructure.constants.enums.SpielmodusEnumeration;
import infrastructure.exceptions.LoesungswegNichtEindeutigException;

import org.junit.Before;
import org.junit.Test;
import severeLobster.frontend.controller.SpielfeldDarstellungsSteuerung;
import severeLobster.frontend.view.SpielfeldDarstellung;

import javax.swing.JLabel;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.instanceOf;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static severeLobster.backend.spiel.helper.matchers.PfeilMatcher.pfeil;

/**
 * Unit-Test zur Zurueck-zum-letzen-fehlerfreien-Spielzug-Funktionalitaet
 * 
 * @author Lars Schlegelmilch
 */
public class Zurueck_zum_letzten_fehlerfreien_Spielzug_Test {

    private final SpielfeldDarstellung view = mock(SpielfeldDarstellung.class);
    private final SternenSpielApplicationBackend backend = SternenSpielApplicationBackend
            .getInstance();
    private final SpielfeldDarstellungsSteuerung controller = new SpielfeldDarstellungsSteuerung(
            view, backend);

    private Spiel spiel;

    @Before
    public void setUp() throws LoesungswegNichtEindeutigException {

        spiel = backend.getSpiel();
        spiel.setSpielmodus(SpielmodusEnumeration.EDITIEREN);
        spiel.initializeNewSpielfeld(3, 3);
        spiel.setSpielstein(0, 0, Pfeil.getSuedPfeil());
        spiel.setSpielstein(0, 1, Stern.getInstance());
        spiel.setSpielstein(0, 2, Stern.getInstance());
        spiel.setSpielmodus(SpielmodusEnumeration.SPIELEN);
        // when(backend.getSpiel()).thenReturn(spiel);
    }

    @Test
    public void bei_einem_fehler_im_ersten_spielzug_wird_das_spielfeld_komplett_zurueck_gesetzt() {
        controller.setSpielstein(Stern.getInstance(), 1, 0);

        backend.zurueckZumLetztenFehlerfreienSpielzug();
        assertThat((Pfeil) spiel.getSpielstein(0, 0),
                pfeil(equalTo(PfeilrichtungEnumeration.SUED)));
        assertThat(spiel.getSpielstein(0, 1), instanceOf(KeinStein.class));
        assertThat(spiel.getSpielstein(0, 2), instanceOf(KeinStein.class));
        assertThat(spiel.getSpielstein(1, 0), instanceOf(KeinStein.class));
        assertThat(spiel.getSpielstein(1, 1), instanceOf(KeinStein.class));
        assertThat(spiel.getSpielstein(1, 2), instanceOf(KeinStein.class));
        assertThat(spiel.getSpielstein(2, 0), instanceOf(KeinStein.class));
        assertThat(spiel.getSpielstein(2, 1), instanceOf(KeinStein.class));
        assertThat(spiel.getSpielstein(2, 2), instanceOf(KeinStein.class));
    }

    @Test
    public void bei_einem_fehler_durch_ausschluss_im_ersten_spielzug_wird_das_spielfeld_komplett_zurueck_gesetzt() {
        controller.setSpielstein(Ausschluss.getInstance(), 0, 1);

        backend.zurueckZumLetztenFehlerfreienSpielzug();
        assertThat((Pfeil) spiel.getSpielstein(0, 0),
                pfeil(equalTo(PfeilrichtungEnumeration.SUED)));
        assertThat(spiel.getSpielstein(0, 1), instanceOf(KeinStein.class));
        assertThat(spiel.getSpielstein(0, 2), instanceOf(KeinStein.class));
        assertThat(spiel.getSpielstein(1, 0), instanceOf(KeinStein.class));
        assertThat(spiel.getSpielstein(1, 1), instanceOf(KeinStein.class));
        assertThat(spiel.getSpielstein(1, 2), instanceOf(KeinStein.class));
        assertThat(spiel.getSpielstein(2, 0), instanceOf(KeinStein.class));
        assertThat(spiel.getSpielstein(2, 1), instanceOf(KeinStein.class));
        assertThat(spiel.getSpielstein(2, 2), instanceOf(KeinStein.class));
    }

    @Test
    public void bei_einem_fehler_nach_einem_richtigen_spielzug_wird_das_spiel_auf_den_richtigen_spielzug_gesetzt() {
        controller.setSpielstein(Stern.getInstance(), 0, 1);
        controller.setSpielstein(Stern.getInstance(), 1, 1);

        backend.zurueckZumLetztenFehlerfreienSpielzug();
        assertThat((Pfeil) spiel.getSpielstein(0, 0),
                pfeil(equalTo(PfeilrichtungEnumeration.SUED)));
        assertThat(spiel.getSpielstein(0, 1), instanceOf(Stern.class));
        assertThat(spiel.getSpielstein(0, 2), instanceOf(KeinStein.class));
        assertThat(spiel.getSpielstein(1, 0), instanceOf(KeinStein.class));
        assertThat(spiel.getSpielstein(1, 1), instanceOf(KeinStein.class));
        assertThat(spiel.getSpielstein(1, 2), instanceOf(KeinStein.class));
        assertThat(spiel.getSpielstein(2, 0), instanceOf(KeinStein.class));
        assertThat(spiel.getSpielstein(2, 1), instanceOf(KeinStein.class));
        assertThat(spiel.getSpielstein(2, 2), instanceOf(KeinStein.class));
    }

    @Test
    public void bei_einem_fehler_durch_ausschluss_nach_einem_richtigen_spielzug_wird_das_spiel_auf_den_richtigen_spielzug_gesetzt() {
        controller.setSpielstein(Stern.getInstance(), 0, 1);
        controller.setSpielstein(Ausschluss.getInstance(), 1, 0);
        controller.setSpielstein(Stern.getInstance(), 1, 1);

        backend.zurueckZumLetztenFehlerfreienSpielzug();
        assertThat((Pfeil) spiel.getSpielstein(0, 0),
                pfeil(equalTo(PfeilrichtungEnumeration.SUED)));
        assertThat(spiel.getSpielstein(0, 1), instanceOf(Stern.class));
        assertThat(spiel.getSpielstein(0, 2), instanceOf(KeinStein.class));
        assertThat(spiel.getSpielstein(1, 0), instanceOf(Ausschluss.class));
        assertThat(spiel.getSpielstein(1, 1), instanceOf(KeinStein.class));
        assertThat(spiel.getSpielstein(1, 2), instanceOf(KeinStein.class));
        assertThat(spiel.getSpielstein(2, 0), instanceOf(KeinStein.class));
        assertThat(spiel.getSpielstein(2, 1), instanceOf(KeinStein.class));
        assertThat(spiel.getSpielstein(2, 2), instanceOf(KeinStein.class));
    }
}
