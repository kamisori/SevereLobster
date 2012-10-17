package severeLobster.frontend.view;

import javax.swing.JPopupMenu;

/**
 * TODO An neue Architektur anpassen. Popup Maus Menu zum Editieren des
 * SpielsteinState im Editier Modus dies Spiels.
 * 
 * @author Lutz Kleiber
 * 
 */
public class PopupMenuForSpielsteinStateManipulation extends JPopupMenu {

    // public PopupMenuForSpielsteinStateManipulation(
    // final SpielsteinController spielsteinController,
    // List<? extends Spielstein> choosableStates) {
    //
    // JMenuItem menuItem;
    //
    // /**
    // * Durchlaufe die fuer diesen Spielstein moeglichen Stati und stelle
    // * jeden dieser Stati mit einem Icon und Text zur Auswahl dar.
    // */
    // for (Spielstein currentState : choosableStates) {
    // /** Hole Icon und Namen fuer diesen SpielsteinState: */
    //
    // menuItem = new JMenuItem(currentState.toString(), SimpleIconFactory
    // .getInstance().getIconForState(currentState));
    // /**
    // * Sinn dieser Variable ist es der Anonymen Klasse eine Konstante
    // * mitzugeben:
    // */
    // final Spielstein finalCurrentState = currentState;
    // menuItem.addActionListener(new ActionListener() {
    //
    // public void actionPerformed(ActionEvent e) {
    // /**
    // * Bei Klick auf den Status soll eine Statusaenderung
    // * hierhin gemacht werden.
    // */
    // spielsteinController.setState(finalCurrentState);
    // }
    // });
    // this.add(menuItem);
    // }
    //
    // }
}
