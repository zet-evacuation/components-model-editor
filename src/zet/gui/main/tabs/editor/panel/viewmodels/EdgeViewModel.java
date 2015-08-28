package zet.gui.main.tabs.editor.panel.viewmodels;

import de.zet_evakuierung.model.EvacuationArea;

/**
 * @author Jan-Philipp Kappmeier
 */
public interface EdgeViewModel {

    public enum EdgeType {
        Exit(true), FloorPassage(true), Passage(true), Wall(false), AreaBoundary(false);
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
        return EdgeType.Wall;
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
