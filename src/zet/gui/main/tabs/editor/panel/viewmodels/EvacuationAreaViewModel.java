package zet.gui.main.tabs.editor.panel.viewmodels;

/**
 * @author Jan-Philipp Kappmeier
 */
public interface EvacuationAreaViewModel {

    default public String getName() {
        return "EvacuationArea";
    }

    default public int getAttractivity() {
        return 1;
    }

}
