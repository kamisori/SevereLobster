package severeLobster.backend.spiel;

import infrastructure.constants.enums.SpielmodusEnumeration;

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
        solvedField.setSpielmodus(SpielmodusEnumeration.LOESEN);

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
