package severeLobster.frontend.controller;

/**
<<<<<<< HEAD
 * 
 * TODO Passt nicht mehr zur restlichen Architektur. Nicht löschen, weil Logik
 * noch zu gebrauchen. Der Vermittler zwischen dem Spielstein und der
 * darstellenden Komponente. Alle im View angestoï¿½enen Aktionen werden an den
 * entsprechenden SpielsteinController weitergeleitet. Umgekehrt leitet der
 * SpielsteinController ï¿½nderungen beim Spielstein an den View weiter.
=======
 * Der Vermittler zwischen dem Spielstein und der darstellenden Komponente. Alle
 * im View angestossenen Aktionen werden an den entsprechenden
 * SpielsteinController weitergeleitet. Umgekehrt leitet der
 * SpielsteinController aenderungen beim Spielstein an den View weiter.
>>>>>>> origin/master
 * 
 * @author Lutz Kleiber
 * 
 */
public class SpielsteinController {

//<<<<<<< HEAD
    // /**
    // * TODO AENDERN - Nur zum Testen: Muss spaeter vom Spiel geholt werdens
    // */
    // public SpielmodusEnumeration spielModus =
    // SpielmodusEnumeration.EDITIEREN;
    //
    // private final IControllableSpielsteinView spielsteinView;
    // private final SpielsteinS2 spielsteinModel;
    //
    // public SpielsteinController(
    // final IControllableSpielsteinView spielsteinView,
    // final SpielsteinS2 spielstein) {
    // this.spielsteinView = spielsteinView;
    // this.spielsteinModel = spielstein;
    // spielsteinView.setSpielsteinController(this);
    // spielstein.addSpielsteinListener(new InnerSpielsteinListener());
    // }
    //
    // /**
    // * Wird vom View aufgerufen. Verï¿½ndert den wert von visibleState des
    // * zugrundeliegenden Spielsteins.
    // *
    // * @param spielsteinState SpielsteinState
    // */
    // public void setState(final Spielstein spielsteinState) {
    // spielsteinModel.setVisibleState(spielsteinState);
    // }
    //
    // /**
    // * Wird vom View aufgerufen. Holt den aktuellen wert von visibleState vom
    // * Spielstein.
    // *
    // * @return spielsteinState
    // */
    // public Spielstein getState() {
    // return spielsteinModel.getVisibleState();
    // }
    //
    // private class InnerSpielsteinListener implements ISpielsteinListener {
    //
    // @Override
    // public void spielsteinStateChanged(final SpielsteinS2 spielstein,
    // final Spielstein newState) {
    //
    // /**
    // * ï¿½berprï¿½fe ob der benachrichtigende Spielstein auch der ist, den
    // * man gerade beobachtet.
    // */
    // if (spielstein == spielsteinModel) {
    // /**
    // * Leite ï¿½nderung an view weiter.
    // */
    // spielsteinView.setDisplayedState(newState);
    // }
    //
    // }
    //
    // }
    //
    // /**
    // * Methode behandelt komplette Logik fuer Mausklicks: Im Spielmodus:
    // * Linksklick: Stern setzen; Rechtsklick: Ausschluss setzen.
    // *
    // * Im Editiermodus: Linksklick: Feld auf NullState resetten; Rechtsklick:
    // * Editiermenu anzeigen.
    // *
    // */
    // public void clickAction(MouseEvent mouseEvent) {
    //
    // if (isSpielModus()) {
    // if (isLeftClick(mouseEvent)) {
    // guessStern();
    // return;
    // }
    // if (isRightClick(mouseEvent)) {
    // guessAusschluss();
    // return;
    // }
    // }
    // /** Editiermodus: */
    // if (!isSpielModus()) {
    // if (isLeftClick(mouseEvent)) {
    // resetSpielsteinState();
    // return;
    // }
    // if (isRightClick(mouseEvent)) {
    // new PopupMenuForSpielsteinStateManipulation(this,
    // spielsteinModel.listAvailableStates()).show(
    // mouseEvent.getComponent(), mouseEvent.getX(),
    // mouseEvent.getY());
    // return;
    // }
    // }
    //
    // }
    //
    // private void guessStern() {
    // final Spielstein sternState = Stern.getInstance();
    // if (isAllowedState(sternState)) {
    // spielsteinModel.setVisibleState(sternState);
    // }
    // }
    //
    // private void guessAusschluss() {
    // final Spielstein ausschlussState = Ausschluss.getInstance();
    //
    // if (isAllowedState(ausschlussState)) {
    // spielsteinModel.setVisibleState(ausschlussState);
    // }
    // }
    //
    // private void resetSpielsteinState() {
    // final Spielstein nullState = KeinStein.getInstance();
    // if (isAllowedState(nullState)) {
    // spielsteinModel.setVisibleState(nullState);
    // }
    // }
    //
    // private boolean isAllowedState(final Spielstein state) {
    // final List<? extends Spielstein> allowedStates = spielsteinModel
    // .listAvailableStates();
    // return allowedStates.contains(state);
    // }
    //
    // /**
    // * TODO aendern: Vom Spiel holen
    // *
    // * @return Spielmodus
    // */
    // private SpielmodusEnumeration getSpielmodus() {
    // return this.spielModus;
    // }
    //
    // private boolean isSpielModus() {
    // return getSpielmodus().equals(SpielmodusEnumeration.SPIELEN);
    // }
    //
    // private boolean isLeftClick(final MouseEvent e) {
    // return e.getButton() == MouseEvent.BUTTON1;
    //
    // }
    //
    // private boolean isRightClick(final MouseEvent e) {
    // return e.getButton() == MouseEvent.BUTTON3;
    //
    // }
    // =======
    // /**
    // * TODO AENDERN - Nur zum Testen: Muss spaeter vom Spiel geholt werdens
    // */
    // public SpielmodusEnumeration spielModus =
    // SpielmodusEnumeration.EDITIEREN;
    //
    // private final IControllableSpielsteinView spielsteinView;
    // private final Spielstein spielsteinModel;
    //
    // public SpielsteinController(
    // final IControllableSpielsteinView spielsteinView,
    // final Spielstein spielstein) {
    // this.spielsteinView = spielsteinView;
    // this.spielsteinModel = spielstein;
    // spielsteinView.setSpielsteinController(this);
    // spielstein.addSpielsteinListener(new InnerSpielsteinListener());
    // }
    //
    // /**
    // * Wird vom View aufgerufen. Verï¿½ndert den wert von visibleState des
    // * zugrundeliegenden Spielsteins.
    // *
    // * @param spielsteinState
    // * SpielsteinState
    // */
    // public void setState(final SpielsteinState spielsteinState) {
    // spielsteinModel.setVisibleState(spielsteinState);
    // }
    //
    // /**
    // * Wird vom View aufgerufen. Holt den aktuellen wert von visibleState vom
    // * Spielstein.
    // *
    // * @return spielsteinState
    // */
    // public SpielsteinState getState() {
    // return spielsteinModel.getVisibleState();
    // }
    //
    // private class InnerSpielsteinListener implements ISpielsteinListener {
    //
    // @Override
    // public void spielsteinStateChanged(final Spielstein spielstein,
    // final SpielsteinState newState) {
    //
    // /**
    // * ueberpruefe ob der benachrichtigende Spielstein auch der ist, den
    // * man gerade beobachtet.
    // */
    // if (spielstein == spielsteinModel) {
    // /**
    // * Leite aenderung an view weiter.
    // */
    // spielsteinView.setDisplayedState(newState);
    // }
    //
    // }
    //
    // }
    //
    // /**
    // * Methode behandelt komplette Logik fuer Mausklicks: Im Spielmodus:
    // * Linksklick: Stern setzen; Rechtsklick: Ausschluss setzen.
    // *
    // * Im Editiermodus: Linksklick: Feld auf NullState resetten; Rechtsklick:
    // * Editiermenu anzeigen.
    // *
    // */
    // public void clickAction(MouseEvent mouseEvent) {
    //
    // if (isSpielModus()) {
    // if (isLeftClick(mouseEvent)) {
    // guessStern();
    // return;
    // }
    // if (isRightClick(mouseEvent)) {
    // guessAusschluss();
    // return;
    // }
    // }
    // /** Editiermodus: */
    // if (!isSpielModus()) {
    // if (isLeftClick(mouseEvent)) {
    // resetSpielsteinState();
    // return;
    // }
    // if (isRightClick(mouseEvent)) {
    // new PopupMenuForSpielsteinStateManipulation(this,
    // spielsteinModel.listAvailableStates()).show(
    // mouseEvent.getComponent(), mouseEvent.getX(),
    // mouseEvent.getY());
    // return;
    // }
    // }
    //
    // }
    //
    // private void guessStern() {
    // final SpielsteinState sternState = Stern.getInstance();
    // if (isAllowedState(sternState)) {
    // spielsteinModel.setVisibleState(sternState);
    // }
    // }
    //
    // private void guessAusschluss() {
    // final SpielsteinState ausschlussState = Ausschluss.getInstance();
    //
    // if (isAllowedState(ausschlussState)) {
    // spielsteinModel.setVisibleState(ausschlussState);
    // }
    // }
    //
    // private void resetSpielsteinState() {
    // final SpielsteinState nullState = NullState.getInstance();
    // if (isAllowedState(nullState)) {
    // spielsteinModel.setVisibleState(nullState);
    // }
    // }
    //
    // private boolean isAllowedState(final SpielsteinState state) {
    // final List<? extends SpielsteinState> allowedStates = spielsteinModel
    // .listAvailableStates();
    // return allowedStates.contains(state);
    // }
    //
    // /**
    // * TODO aendern: Vom Spiel holen
    // *
    // * @return Spielmodus
    // */
    // private SpielmodusEnumeration getSpielmodus() {
    // return this.spielModus;
    // }
    //
    // private boolean isSpielModus() {
    // return getSpielmodus().equals(SpielmodusEnumeration.SPIELEN);
    // }
    //
    // private boolean isLeftClick(final MouseEvent e) {
    // return e.getButton() == MouseEvent.BUTTON1;
    //
    // }
    //
    // private boolean isRightClick(final MouseEvent e) {
    // return e.getButton() == MouseEvent.BUTTON3;
    //
    // }
    // >>>>>>> origin/master
}
