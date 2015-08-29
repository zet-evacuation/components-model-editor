package org.zet.components.model.editor.panel;

/**
 *
 * @author Jan-Philipp Kappmeier
 * @param <M>
 */
@FunctionalInterface
public interface Displayable<M> {
    void setModel(M model);
}
