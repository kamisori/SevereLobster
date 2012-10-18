package severeLobster.frontend.controller;

import infrastructure.constants.enums.SpielmodusEnumeration;

public interface ISpielmodusView {

    void setSpielmodusController(SpielmodusController spielmodusController);

    void setDisplayedSpielmodus(SpielmodusEnumeration newSpielmodus);

}
