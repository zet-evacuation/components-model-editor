/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zet.gui.main.tabs.editor.panel;

/**
 *
 * @author Jan-Philipp Kappmeier
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
}
