package org.zet.components.model.viewmodel;

import de.zet_evakuierung.model.EvacuationArea;
import de.zet_evakuierung.model.ZControl;
import org.zet.components.model.editor.panel.JEvacuationAreaInformationPanel;

/**
 * A control class for evacuation areas.
 *
 * @author Jan-Philipp Kappmeier
 */
public class EvacuationAreaControl extends AbstractInformationControl<JEvacuationAreaInformationPanel, EvacuationAreaViewModel> {
    private EvacuationArea model;

    private EvacuationAreaControl(JEvacuationAreaInformationPanel view, ZControl control) {
        super(view, control);
    }

    public static EvacuationAreaControl create(ZControl control) {
        JEvacuationAreaInformationPanel panel = generateView();
        EvacuationAreaControl result = new EvacuationAreaControl(panel, control);
        panel.setControl(result);
        return result;
    }

    private static JEvacuationAreaInformationPanel generateView() {
        return new JEvacuationAreaInformationPanel();
    }
    
    public void setModel(EvacuationArea model) {
        EvacuationAreaViewModel vm = new EvacuationAreaViewModelImpl(model, control.getProject());
        setModel(vm);
        getView().setModel(vm);
        this.model = model;
    }

    public void setName(String name) {
        model.setName(name);
        //((EvacuationArea) getLeftPanel().getMainComponent().getSelectedPolygons().get(0).getPlanPolygon()).setName(txtEvacuationAreaName.getText());
    }

    public void setAttractivity(int attractivity) {
        EvacuationArea ea = model;
        model.setAttractivity(attractivity);
    }
}
