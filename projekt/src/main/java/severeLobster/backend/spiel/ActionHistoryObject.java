package severeLobster.backend.spiel;

import java.util.LinkedList;
import java.util.Vector;

import severeLobster.backend.command.Aktion;

public class ActionHistoryObject {
    private Vector<LinkedList<ActionHistoryObject>> alternativeSpielzuege_;
    private boolean verursachtFehler_;
    private boolean istVorTrackingPunkt_;
    private Aktion spielZug_;

    public ActionHistoryObject(Aktion spielZug, boolean verursachtFehler, boolean istVorTrackingPunkt)
    {
        alternativeSpielzuege_ = new Vector<LinkedList<ActionHistoryObject>>();
        spielZug_ = spielZug;
        verursachtFehler_ = verursachtFehler;
        istVorTrackingPunkt_ = istVorTrackingPunkt;
    }

    public ActionHistoryObject()
    {
        alternativeSpielzuege_ = new Vector<LinkedList<ActionHistoryObject>>();
        spielZug_ = null;
        verursachtFehler_ = false;
        istVorTrackingPunkt_ = true;
    }

    public void setzeTrackingPunktNachDiesemZug(boolean istVorTrackingPunkt) {
        istVorTrackingPunkt_ = istVorTrackingPunkt;
    }

    public boolean istVorTrackingPunkt() {
        return istVorTrackingPunkt_;
    }

    public boolean verursachtFehler() {
        return verursachtFehler_;
    }

    public void addToAlternativePath(LinkedList<ActionHistoryObject> alternativePathToUndo) {
        alternativeSpielzuege_.add(alternativePathToUndo);
    }

    public LinkedList<ActionHistoryObject> getAlternativePath(int i)
    {
        return alternativeSpielzuege_.get(i);
    }

    public int getAlternativePathCount(){
        return alternativeSpielzuege_.size();
    }

    public Aktion getSpielzug(){
        return spielZug_;
    }
}
