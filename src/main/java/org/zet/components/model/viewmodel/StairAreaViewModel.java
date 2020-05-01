package org.zet.components.model.viewmodel;

/**
 * @author Jan-Philipp Kappmeier
 */
public interface StairAreaViewModel {

    default public double getSpeedFactorUp() {
        return 0.5;
    }

    default public double getSpeedFactorDown() {
        return 0.5;
    }

}
