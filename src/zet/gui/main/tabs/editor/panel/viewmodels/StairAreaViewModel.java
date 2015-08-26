package zet.gui.main.tabs.editor.panel.viewmodels;

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
