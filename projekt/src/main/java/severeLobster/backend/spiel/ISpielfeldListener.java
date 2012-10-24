package severeLobster.backend.spiel;

import java.io.Serializable;
import java.util.EventListener;

/**
 * 
 * @author Lutz Kleiber
 * 
 */
public interface ISpielfeldListener extends Serializable, EventListener{

    void spielsteinChanged(Spielfeld spielfeld, int x, int y,
            Spielstein changedStein);

}
