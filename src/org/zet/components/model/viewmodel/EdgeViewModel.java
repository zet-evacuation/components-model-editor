package org.zet.components.model.viewmodel;

import de.zet_evakuierung.model.EvacuationArea;

/**
 * @author Jan-Philipp Kappmeier
 */
public interface EdgeViewModel {

    public enum EdgeType {
        EXIT(true), FLOOR_PASSAGE(true), PASSAGE(true), WALL(false), AREA_BOUNDARY(false);
        private final boolean passable;

        private EdgeType(boolean passable) {
            this.passable = passable;
        }
        
        public boolean isPassable() {
            return passable;
        }
    }
    
    public enum EdgeOrientation {
        Horizontal, Vertical, Oblique;
    }

    default public EdgeType getEdgeType() {
        return EdgeType.WALL;
    }

    default public double getLength() {
        return 0;
    }

    default public EdgeOrientation getOrientation() {
        return EdgeOrientation.Oblique;
    }
    
    default public EvacuationArea getAssociatedExit() {
        return new EvacuationArea(null, 1, "DummyExit");
    }

}
