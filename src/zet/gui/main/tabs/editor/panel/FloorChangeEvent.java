/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zet.gui.main.tabs.editor.panel;

/**
 *
 * @author kapman
 */
class FloorChangeEvent extends AbstractChangeEvent<FloorChangeEvent.FloorChange> {

    public enum FloorChange {
        MoveUp, MoveDown, Dimension, Rasterize, Rename
    }

    public FloorChangeEvent(Object source, FloorChange change) {
        super(source, change);
    }

}
