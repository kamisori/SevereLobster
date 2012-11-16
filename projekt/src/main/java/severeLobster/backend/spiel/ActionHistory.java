package severeLobster.backend.spiel;

import severeLobster.backend.command.Aktion;

import java.util.LinkedList;
import java.util.Vector;

public class ActionHistory {
    private final LinkedList<ActionHistoryObject> spielzuege_;

    public ActionHistory()
    {
        spielzuege_ = new LinkedList<ActionHistoryObject>();
        spielzuege_.add(new ActionHistoryObject());
    }

    public void neuerSpielzug(Aktion spielZug, boolean fehler){
        spielzuege_.add(new ActionHistoryObject(spielZug, fehler, false));
    }

    public void setzeTrackingPunktNachDiesemZug(boolean istVorTrackingPunkt) {
        spielzuege_.getLast().setzeTrackingPunktNachDiesemZug(istVorTrackingPunkt);
    }

    public boolean istVorTrackingPunkt() {
        return spielzuege_.getLast().istVorTrackingPunkt();
    }

    public boolean verursachtFehler() {
        return spielzuege_.getLast().verursachtFehler();
    }

    private LinkedList<ActionHistoryObject> moveLastToAlternativeHistory(LinkedList<ActionHistoryObject> alternativeHistory) {
        ActionHistoryObject current = spielzuege_.getLast();
        spielzuege_.removeLast();
        current.getSpielzug().undo();
        
        if(alternativeHistory.isEmpty())
            alternativeHistory.add(current);
        else
            alternativeHistory.add(0, current);
        return alternativeHistory;
    }

    public void zurueckZuLetztemFehlerfreiemZug(){
        ActionHistoryObject letzterZugOhneFehler = null;
        int anzahlZuege = spielzuege_.size();
        for(int i = anzahlZuege - 1; i >= 0; i--)
        {
            if(!spielzuege_.get(i).verursachtFehler())
            {
                letzterZugOhneFehler = spielzuege_.get(i);
                break;
            }
        }

        if(letzterZugOhneFehler != null)
        {
            LinkedList<ActionHistoryObject> alternativeHistory = new LinkedList<ActionHistoryObject>();
            while(spielzuege_.getLast().verursachtFehler())
            {
                alternativeHistory = moveLastToAlternativeHistory(alternativeHistory);
            }
            letzterZugOhneFehler.addToAlternativePath(alternativeHistory);
        }
    }

    public void zurueckZuLetztemCheckpoint(){
        ActionHistoryObject letzterZugVorCheckpoint = null;
        int anzahlZuege = spielzuege_.size();
        for(int i = anzahlZuege - 1; i >= 0; i--)
        {
            if(spielzuege_.get(i).istVorTrackingPunkt())
            {
                letzterZugVorCheckpoint = spielzuege_.get(i);
                break;
            }
        }

        if(letzterZugVorCheckpoint != null)
        {
            LinkedList<ActionHistoryObject> alternativeHistory = new LinkedList<ActionHistoryObject>();
            while(!spielzuege_.getLast().istVorTrackingPunkt())
            {
                alternativeHistory = moveLastToAlternativeHistory(alternativeHistory);
            }
            letzterZugVorCheckpoint.addToAlternativePath(alternativeHistory);
        }
    }

    public ActionHistoryObject getCurrent()
    {
        return spielzuege_.getLast();
    }
}
