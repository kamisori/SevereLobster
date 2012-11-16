package severeLobster.backend.spiel;

/**
 *  Prüft alle Spalten darauf ob die Anzahl der Sterne 0 ist und setzt dann alles auf Ausschluss
 *
 *  @author Christian Lobach
 */
public class SolvingStepCheckZeroColumns implements SolvingStep{


    public Spielfeld execute(Spielfeld input) {

        for (int i = 0; i < input.getBreite() ;i++)
        {
            if(input.countSterneSpalte(i) == 0)
            {
                //TODO: Alle Spielsteine dieser Spalte auf 0 setzen

            }
        }

        return input; //TODO: geändertes Spielfeld zurückgeben
    }
}
