package zet.gui.main.tabs.editor.panel;

import java.io.IOException;

/**
 *
 * @author Jan-Philipp Kappmeier
 * @param <E> The event type
 */
public class AbstractChangeEvent<E> extends ChangeEvent {
    private final E changeType;

    public AbstractChangeEvent(Object source, E type) {
        super(source);
        this.changeType = type;
    }

    public E getChangeType() {
        return changeType;
    }

    /** Prohibits serialization. */
    private synchronized void writeObject(java.io.ObjectOutputStream s) throws IOException {
        throw new UnsupportedOperationException("Serialization not supported");
    }
    
    /** Prohibits serialization. */
    private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException {
        throw new UnsupportedOperationException("Serialization not supported");
    }
}
