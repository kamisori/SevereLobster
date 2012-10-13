package severeLobster.backend.spiel;

import java.util.EventListener;

/**
 * 
 * @author Lutz Kleiber
 *
 */
public interface ISpielfeldListener extends EventListener {

    void spielsteinChanged(Spielfeld spielfeld, int x, int y,
            Spielstein changedStein);

}
