

package org.zet.components.model.editor.floor;

import java.util.EventListener;

/**
 *
 * @author Jan-Philipp Kappmeier
 */
public interface SelectionListener extends EventListener {
    public void selectionChanged(SelectionEvent event);
    public void selectionCleared(SelectionEvent event);
    public void selectionEdge(SelectionEvent event);
}
