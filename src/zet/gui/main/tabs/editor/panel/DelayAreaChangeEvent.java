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
public class DelayAreaChangeEvent extends AbstractChangeEvent<DelayAreaChangeEvent.DelayAreaChange> {

    public enum DelayAreaChange {
        Type, Factor, DefaultType
    }
    
    public DelayAreaChangeEvent(Object source, DelayAreaChange change) {
        super(source, change);
    }
}
