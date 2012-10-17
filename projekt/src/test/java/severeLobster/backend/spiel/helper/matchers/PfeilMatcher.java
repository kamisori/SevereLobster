package severeLobster.backend.spiel.helper.matchers;

import infrastructure.constants.enums.PfeilrichtungEnumeration;
import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import severeLobster.backend.spiel.Pfeil;

/**
 * Hamcrest Matcher fuer einen Pfeil,
 * vergleicht einen Pfeilspielstein und seine Attribute
 *
 * @author Lars Schlegelmilch
 */
public class PfeilMatcher extends TypeSafeMatcher<Pfeil> {

    private final Matcher<PfeilrichtungEnumeration> pfeilrichtung;


    public PfeilMatcher(Matcher<PfeilrichtungEnumeration> pfeilrichtung) {
        this.pfeilrichtung = pfeilrichtung;
    }

    @Override
    protected boolean matchesSafely(Pfeil pfeil) {
        return pfeilrichtung.matches(pfeil.getPfeilrichtung());

    }

    @Override
    public void describeTo(Description description) {
    }

    @Factory
    public static PfeilMatcher pfeil(Matcher<PfeilrichtungEnumeration> pfeilrichtung) {
        return new PfeilMatcher(pfeilrichtung);
    }
}
