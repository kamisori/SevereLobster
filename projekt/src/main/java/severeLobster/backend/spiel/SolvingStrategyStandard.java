package severeLobster.backend.spiel;

import infrastructure.constants.enums.SpielmodusEnumeration;
import infrastructure.exceptions.LoesungswegNichtEindeutigException;

/**
 * Soll ein Spielfeld lösen.
 * 
 * @author Christian Lobach
 */
public class SolvingStrategyStandard implements SolvingStrategy {

    public boolean isSolvable(Spielfeld input) {

        return false;
    }

    public Spielfeld solve(Spielfeld input) {

        Spielfeld solvedField = input;
        try {
            solvedField.setSpielmodus(SpielmodusEnumeration.LOESEN);
        } catch (LoesungswegNichtEindeutigException e) {
            /*
             * Try-Catch nur formal. Vorerst wird die Exception nur beim
             * Umstellen auf Spielmodus geworfen.
             */
            e.printStackTrace();
        }

        SolvingStep[] steps;
        steps = new SolvingStep[] { new SolvingStepPossibleStars(),
                new SolvingStepExcludeImpossibles(),
                new SolvingStepCheckZeroColumns(),
                new SolvingStepCheckZeroRows() };

        for (SolvingStep currentStep : steps) {
            solvedField = currentStep.execute(input);
        }

        return solvedField;
    }

}
