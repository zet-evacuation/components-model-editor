package zet.gui.main.tabs.editor.panel;

import de.zet_evakuierung.model.EvacuationArea;
import de.zet_evakuierung.model.ZControl;
import zet.gui.main.tabs.editor.panel.viewmodels.EvacuationAreaViewModel;
import zet.gui.main.tabs.editor.panel.viewmodels.EvacuationAreaViewModelImpl;

/**
 * A control class for evacuation areas.
 *
 * @author Jan-Philipp Kappmeier
 */
public class EvacuationAreaControl extends AbstractInformationPanelControl<JEvacuationAreaInformationPanel, EvacuationAreaViewModel> {
    private EvacuationArea model;

    public EvacuationAreaControl(ZControl control) {
        super(generateView(), control);
        getView().setControl(this);
    }

    private static JEvacuationAreaInformationPanel generateView() {
        return new JEvacuationAreaInformationPanel();
    }
    
    public void setModel(EvacuationArea model) {
        getView().setModel(new EvacuationAreaViewModelImpl(model, control.getProject()));
        this.model = model;
    }

    void setName(String name) {
        model.setName(name);
        //((EvacuationArea) getLeftPanel().getMainComponent().getSelectedPolygons().get(0).getPlanPolygon()).setName(txtEvacuationAreaName.getText());
    }

    void setAttractivity(int attractivity) {
        EvacuationArea ea = model;
        model.setAttractivity(attractivity);
    }
}
