package severeLobster.backend.command;

/**
 * Aktionsinterface
 *
 * @author Lars Schlegelmilch
 */
public interface Aktion {

    public void execute(int x, int y);
    public void undo();
}
