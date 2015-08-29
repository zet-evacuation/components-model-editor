package org.zet.components.model.viewmodel;

/**
 * @author Jan-Philipp Kappmeier
 */
public interface RoomViewModel {

    default public String getName() {
        return "Room";
    }

    default public double areaMeter() {
        return 0;
    }
}
