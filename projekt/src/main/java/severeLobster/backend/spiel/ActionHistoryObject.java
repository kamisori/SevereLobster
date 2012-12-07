package severeLobster.backend.spiel;

import java.util.LinkedList;
import java.util.Vector;

import com.thoughtworks.xstream.io.path.Path;

import severeLobster.backend.command.Aktion;

public class ActionHistoryObject {
    public enum PathAndStepStatus {pathValid, stepGotValidPath, pathInvalid}

    private Vector<LinkedList<ActionHistoryObject>> alternativeSpielzuege_;
    private int path_;
    private int step_;
    private boolean zurueck_;
    private boolean verursachtFehler_;
    private boolean istVorTrackingPunkt_;
    private Aktion spielZug_;

    public ActionHistoryObject(Aktion spielZug, boolean verursachtFehler, boolean istVorTrackingPunkt)
    {
        path_ = 0;
        step_ = 0;
        zurueck_ = false;
        alternativeSpielzuege_ = new Vector<LinkedList<ActionHistoryObject>>();
        spielZug_ = spielZug;
        verursachtFehler_ = verursachtFehler;
        istVorTrackingPunkt_ = istVorTrackingPunkt;
    }

    public ActionHistoryObject()
    {
        path_ = 0;
        step_ = 0;
        zurueck_ = false;
        alternativeSpielzuege_ = new Vector<LinkedList<ActionHistoryObject>>();
        spielZug_ = null;
        verursachtFehler_ = false;
        istVorTrackingPunkt_ = false;
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

    private PathAndStepStatus tryThisPath(LinkedList<ActionHistoryObject> currentPath, int stepsInPath)
    {
        if (!zurueck_)
        {
            step_++;
            if(step_ >= stepsInPath)
            {
                step_ = stepsInPath - 1;
                zurueck_ = true;
            }
        }
        else
        {
            step_--;
            if(step_ < 0)
            {
                step_ = 0;
                zurueck_ = false;
                return PathAndStepStatus.pathInvalid;
            }
        }
        return PathAndStepStatus.stepGotValidPath;
    }

    private PathAndStepStatus tryNextPath( boolean zurueck, int paths )
    {
        path_++;
        step_ = 0;
        if(path_ < paths)
        {
            return PathAndStepStatus.stepGotValidPath;
        }
        else
        {
            path_ = 0;
            replayMove(zurueck);
            return PathAndStepStatus.pathInvalid;
        }
    }

    public PathAndStepStatus traverse(boolean zurueck)
    {
        System.out.print(" path: " + path_ + " step: " + step_);
        if(!hasAlternativePaths())
        {
            replayMove(zurueck);
            return PathAndStepStatus.pathValid;
        }
        else
        {
            int paths = alternativeSpielzuege_.size();
            if(path_ < paths && path_ >= 0)
            {
                LinkedList<ActionHistoryObject> currentPath =
                        alternativeSpielzuege_.get(path_);
                int stepsInPath = currentPath.size();
                if(step_ < stepsInPath && step_ >= 0)
                {
                    ActionHistoryObject currentStep =
                            currentPath.get(step_);
                    PathAndStepStatus traversionStatus = currentStep.traverse(zurueck_);
                    if(traversionStatus == PathAndStepStatus.pathValid)
                    {
                        return tryThisPath(currentPath, stepsInPath);
                    }
                    else if (traversionStatus == PathAndStepStatus.pathInvalid)
                    {
                        return tryNextPath(zurueck, paths);
                    }
                    else
                    {
                        return PathAndStepStatus.stepGotValidPath;
                    }
                }
            }
        }
        return PathAndStepStatus.pathInvalid;
    }

    void replayMove(boolean zurueck)
    {
        if(!zurueck)
            spielZug_.redo();
        else
            spielZug_.reundo();
    }

    private boolean hasAlternativePaths()
    {
        return alternativeSpielzuege_.size() != 0;
    }

    public Aktion getSpielzug(){
        return spielZug_;
    }
}
