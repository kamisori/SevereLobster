package severeLobster.backend.spiel;

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

        SolvingStep[] steps = {new SolvingStepPossibleStars(),
                new SolvingStepExcludeImpossibles(),
                new SolvingStepCheckZeroColumns(),
                new SolvingStepCheckZeroRows()};

        for (SolvingStep currentStep : steps){
            solvedField = currentStep.execute(input);
        }

        return solvedField;
    }
}
