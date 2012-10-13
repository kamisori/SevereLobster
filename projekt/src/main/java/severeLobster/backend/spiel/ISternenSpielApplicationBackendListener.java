package severeLobster.backend.spiel;

import infrastructure.constants.enums.SpielmodusEnumeration;

import java.util.EventListener;

public interface ISternenSpielApplicationBackendListener extends EventListener {

    void spielmodusChanged(
            SternenSpielApplicationBackend sternenSpielApplicationBackend,
            Spiel spiel, SpielmodusEnumeration newSpielmodus);

    void spielsteinChanged(
            SternenSpielApplicationBackend sternenSpielApplicationBackend,
            Spiel spiel, Spielfeld spielfeld, int x, int y, Spielstein newStein);

    void spielfeldChanged(
            SternenSpielApplicationBackend sternenSpielApplicationBackend,
            Spiel spiel, Spielfeld newSpielfeld);

    void spielChanged(
            SternenSpielApplicationBackend sternenSpielApplicationBackend,
            Spiel spiel);

}
