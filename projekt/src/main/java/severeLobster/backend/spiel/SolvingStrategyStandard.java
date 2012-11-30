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

        // Einmal-Schritte ausführen
        SolvingStep[] initialSteps = new SolvingStep[] { new SolvingStepPossibleStars(),
                new SolvingStepExcludeImpossibles(),
                new SolvingStepCheckZeroColumns(),
                new SolvingStepCheckZeroRows() };

        for (SolvingStep currentStep : initialSteps) {
            solvedField = currentStep.execute(input);
        }

        // Schritte ausführen die mehrmals aufgerufen werden
        SolvingStep [] stepsPerRound = new SolvingStep[] {


        };

        Spielfeld before = solvedField;

        for (SolvingStep currentStep : stepsPerRound) {
            solvedField = currentStep.execute(input);
        }

        /**
         * Wenn sich in einem Spielfeld nach einem Durchlauf nichts geaendert hat,
         * ist das Spiel nicht lösbar
         */
        if (solvedField.equals(before))
        {
            // NICHT EINDEUTIG LÖSBAR!
            // evtl. raten um anzuzeigen wo es nicht eindeutig ist
        }

        return solvedField;
    }

}
