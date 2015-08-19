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
public class RoomChangeEvent extends AbstractChangeEvent<RoomChangeEvent.RoomChange> {

    public enum RoomChange {
        Name, Delete, RefineCoordinates
    }
    
    public RoomChangeEvent(Object source, RoomChange change) {
        super(source, change);
    }
    
}
