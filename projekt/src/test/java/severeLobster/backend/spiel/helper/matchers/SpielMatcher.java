package severeLobster.backend.spiel.helper.matchers;

import infrastructure.constants.enums.SchwierigkeitsgradEnumeration;
import infrastructure.constants.enums.SpielmodusEnumeration;
import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import severeLobster.backend.spiel.Spiel;

/**
 * Hamcrest Matcher für ein Spiel,
 * vergleicht ein Spiel und seine Attribute
 *
 * @author Lars Schlegelmilch
 */
public class SpielMatcher extends TypeSafeMatcher<Spiel> {

    private final Matcher<SpielmodusEnumeration> spielmodus;
    private final Matcher<SchwierigkeitsgradEnumeration> schwierigkeitsgrad;

    public SpielMatcher(Matcher<SpielmodusEnumeration> spielmodus,
                        Matcher<SchwierigkeitsgradEnumeration> schwierigkeitsgrad) {
        this.spielmodus = spielmodus;
        this.schwierigkeitsgrad = schwierigkeitsgrad;
    }

    @Override
    protected boolean matchesSafely(Spiel spiel) {
        if (!spielmodus.matches(spiel.getSpielmodus())) {
            return false;
        }
        if (!schwierigkeitsgrad.matches(spiel.getSchwierigkeitsgrad())) {
            return false;
        }
        return true;
    }

    @Override
    public void describeTo(Description description) {
    }

    @Factory
    public static SpielMatcher spiel(Matcher<SpielmodusEnumeration> spielmodus,
                                     Matcher<SchwierigkeitsgradEnumeration> schwierigkeitsgrad) {
        return new SpielMatcher(spielmodus, schwierigkeitsgrad);
    }
}
