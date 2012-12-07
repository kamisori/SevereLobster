package severeLobster.backend.spiel;

import infrastructure.components.Koordinaten;
import infrastructure.constants.enums.SpielmodusEnumeration;
import infrastructure.exceptions.LoesungswegNichtEindeutigException;

import java.awt.*;
import java.util.ArrayList;

/**
 * Soll ein Spielfeld lösen.
 *
 * @author Christian Lobach
 */
public class SolvingStrategyStandard implements SolvingStrategy {

    public boolean isSolvable(Spielfeld input) {
        //TODO: Rueckgabe von Null soll nur provisorisch sein
        if(solve(input) != null){
            System.out.println("Spiel ist loesbar");

        }
        else {
            System.out.println("Spiel ist nicht loesbar!");
        }

        return solve(input) != null;
    }

    /**
     * Gibt die Felder zurueck, die ein SPielfeld nicht eindeutig loesbar machen
     * @param input Spielfeld
     * @return Array von Koordinaten der betroffenen Felder
     */
    public Koordinaten[] getNotUniques(Spielfeld input){

        return null;
    }

    public Spielfeld solve(Spielfeld input) {

        Spielfeld solvedField = new Spielfeld(input);

        try {
            solvedField.setSpielmodus(SpielmodusEnumeration.LOESEN);
        } catch (LoesungswegNichtEindeutigException e) {
            /*
             * Try-Catch nur formal. Vorerst wird die Exception nur beim
             * Umstellen auf Spielmodus geworfen.
             */
            e.printStackTrace();
        }

        // Sichtbare Sterne und Ausschluss leeren, damit auf diesem Spielfeld geloest werden kann
        solvedField = clearVisibles(solvedField);

        // Einmal-Schritte ausführen
        SolvingStep[] initialSteps = new SolvingStep[]{
                new SolvingStepPossibleStars(),
                new SolvingStepExcludeImpossibles(),
                new SolvingStepCheckZeroColumns(),
                new SolvingStepCheckZeroRows()};

        for (SolvingStep currentStep : initialSteps) {
            solvedField = currentStep.execute(solvedField);
        }

        // Schritte ausführen die mehrmals aufgerufen werden
        SolvingStep[] stepsPerRound = new SolvingStep[]{
                new SolvingStepMissingStarsInColumn(),
                new SolvingStepMissingStarsInRow(),
                new SolvingStepExcludeRestInColumn(),
                new SolvingStepExcludeRestInRow(),
                new SolvingStepSingleStarBeforeArrow()
        };

        boolean abbruch = false;
        while (!solvedField.isSolved() && !abbruch) {

            Spielfeld before = new Spielfeld(solvedField);

            for (SolvingStep currentStep : stepsPerRound) {
                solvedField = currentStep.execute(solvedField);
            }

            /**
             * Wenn sich in einem Spielfeld nach einem Durchlauf nichts geaendert hat,
             * ist das Spiel nicht lösbar
             */
            if (solvedField.equals(before)) {
                abbruch = true;
            }

        }

        // Das Spielfeld ist nicht, oder nicht eindeutig loesbar
        if (abbruch) {

            ArrayList loesungswegEinsprungspunkte = new ArrayList();

            for (int i = 1; i <= solvedField.countMoeglicheSterne(); i++) {

                Spielfeld raten = new Spielfeld(solvedField);

                // einen möglichen Stern setzen
                Point nextGuess = nextPossibleStar(solvedField, i - 1);
                loesungswegEinsprungspunkte.add(nextGuess);
                raten.setSpielstein(nextGuess.x, nextGuess.y, Stern.getInstance());


                // Wieder die Loesungsschritte anwenden
                boolean ratenAbbruch = false;
                while (!raten.isSolved() && !ratenAbbruch) {

                    Spielfeld beforeRaten = new Spielfeld(raten);

                    for (SolvingStep currentStep : stepsPerRound) {
                        raten = currentStep.execute(raten);
                    }
                    if (raten.equals(beforeRaten)) ratenAbbruch = true;
                }

                //Wenn es mit diesem erratenen Stern gelöst werden konnte, bleibt es als moeglicher Loesungsweg
                // in der Liste drin, sonst wird es wieder entfernt
                if (ratenAbbruch) {
                    loesungswegEinsprungspunkte.remove(nextGuess);
                }

            }
            // Konnte das Feld trotz ausprobieren der moeglichen Sterne nicht geloest werden, so ist es ueberhaupt nicht loesbar.

            if (loesungswegEinsprungspunkte.size() > 0) {
                // Spielfeld ist durch raten lösbar.
                System.out.println("Spielfeld ist loesbar, aber nicht eindeutig");
            }

            return null;
        } else return solvedField;

    }

    /**
     * gibt den nächstmöglichen Stern zurück
     *
     * @param offset die Anzahl der Sterne die übersprungen wird, bevor einer zurueckgegeben wird
     * @return Point mit den Koordinaten des nächstmöglichen Sterns
     */
    private Point nextPossibleStar(Spielfeld input, int offset) {
        // Die Anzahl moeglicher Sterne auf die man beim Durchlaufen trifft
        int metPossibilities = 0;

        for (int colums = 0; colums < input.getBreite(); colums++) {
            for (int rows = 0; rows < input.getHoehe(); rows++) {
                if (input.getSpielstein(colums, rows) instanceof MoeglicherStern) {
                    // Wurde die gewuenschte Anzahl gefundener Moeglichkeiten uebersprungen, wird die Position zurueckgegeben
                    if (offset == metPossibilities) {
                        return new Point(colums, rows);
                    }
                    metPossibilities++;
                }
            }

        }
        return null;
    }


    /**
     * Bekommt ein Spielfeld zurückgegeben und setzt alle Sterne und Ausschluss auf KeinStein
     * @param input Spielfeld
     */
    private Spielfeld clearVisibles(Spielfeld input){

        for (int colums = 0; colums < input.getBreite(); colums++) {
            for (int rows = 0; rows < input.getHoehe(); rows++) {
                if (input.getSpielstein(colums, rows) instanceof Stern || input.getSpielstein(colums, rows) instanceof Ausschluss) {
                    input.setSpielstein(colums,rows,KeinStein.getInstance());
                }
            }
        }
        return input;
    }


}