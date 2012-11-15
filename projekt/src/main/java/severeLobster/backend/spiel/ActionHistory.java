package severeLobster.backend.spiel;

import java.util.LinkedList;
import java.util.Vector;

import severeLobster.backend.command.Aktion;

public class ActionHistory extends LinkedList<ActionHistory> {
    Vector<ActionHistory> alternativeSpielzuege_;
    boolean verursachtFehler_;
    boolean istVorTrackingPunkt_;
    Aktion spielZug_;
    boolean wurzel_;

    public ActionHistory()
    {
        super();
        alternativeSpielzuege_ = new Vector<ActionHistory>();
        spielZug_ = null;
        verursachtFehler_ = false;
        istVorTrackingPunkt_ = false;
        wurzel_ = true;
    }

    public ActionHistory(Aktion spielZug, boolean verursachtFehler, boolean istVorTrackingPunkt)
    {
        super();
        alternativeSpielzuege_ = new Vector<ActionHistory>();
        spielZug_ = spielZug;
        verursachtFehler_ = verursachtFehler;
        istVorTrackingPunkt_ = istVorTrackingPunkt;
        wurzel_ = false;
    }

    public void setzeTrackingPunktNachDiesemZug(boolean istVorTrackingPunkt) {
        istVorTrackingPunkt_ = istVorTrackingPunkt_;
    }

    public boolean istVorTrackingPunkt() {
        return istVorTrackingPunkt_;
    }

    public boolean verursachtFehler() {
        return verursachtFehler_;
    }

    private void moveToAlternativePathOfTarget(ActionHistory target) {
        ActionHistory current = this.getLast();
        this.removeLast();
        current.spielZug_.undo();
        if(target.alternativeSpielzuege_.isEmpty())
            target.alternativeSpielzuege_.add(current);
        else
            target.alternativeSpielzuege_.lastElement().add(0, current);
    }

    public void zurueckZuLetztemFehlerfreiemZug(){
        ActionHistory letzterZugOhneFehler = null;
        int anzahlZuege = this.size();
        for(int i = anzahlZuege; i >= 0; i--)
        {
            if(!this.get(i).verursachtFehler())
            {
                letzterZugOhneFehler = this.get(i);
                break;
            }
        }

        if(letzterZugOhneFehler != null)
        {
            while(this.getLast().verursachtFehler())
            {
                moveToAlternativePathOfTarget(letzterZugOhneFehler);
            }
        }
    }

    public void zurueckZuLetztemCheckpoint(){
        ActionHistory letzterZugVorCheckpoint = null;
        int anzahlZuege = this.size();
        for(int i = anzahlZuege; i >= 0; i--)
        {
            if(this.get(i).istVorTrackingPunkt())
            {
                letzterZugVorCheckpoint = this.get(i);
                break;
            }
        }

        if(letzterZugVorCheckpoint != null)
        {
            while(!this.getLast().istVorTrackingPunkt())
            {
                moveToAlternativePathOfTarget(letzterZugVorCheckpoint);
            }
        }
    }

    public ActionHistory getCurrent()
    {
        return this.getLast();
    }
}
