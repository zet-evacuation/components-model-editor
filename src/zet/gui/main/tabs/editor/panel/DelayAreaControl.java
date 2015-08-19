/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zet.gui.main.tabs.editor.panel;

import de.zet_evakuierung.model.DelayArea;
import de.zet_evakuierung.model.ZControl;

/**
 * A control class for evacuation areas.
 * @author Jan-Philipp Kappmeier
 */
public class DelayAreaControl extends AbstractInformationPanelControl<JDelayAreaInformationPanel, DelayArea, DelayAreaChangeEvent> {
    private final ZControl control;
    
    public DelayAreaControl(ZControl zcontrol) {
        super(new JDelayAreaInformationPanel());
        this.control = zcontrol;
    }

    @Override
    public void changed(DelayAreaChangeEvent c) {
        switch(c.getChangeType()) {
            case DefaultType:
                double defaultSpeed = model.getDelayType().defaultSpeedFactor;
                control.setDelaySpeedFactor(model, defaultSpeed);
                //txtDelayFactor.setText( nfFloat.format( a.getSpeedFactor() ) );
                break;
            case Factor:
                double speedFactor = getView().getSpeedFactor();
                control.setDelaySpeedFactor(model, speedFactor);
                break;
            case Type:
                DelayArea.DelayType type = getView().getDelayType();
                control.setDelayType(model, type);
                break;
            default:
                throw new IllegalArgumentException("Unsupported delay area change event: " + c);

        }
    }
}
