/* zet evacuation tool copyright (c) 2007-15 zet evacuation team
 *
 * This program is free software; you can redistribute it and/or
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */
package zet.gui.main.tabs.editor.floor;

import java.awt.Component;
import java.awt.Point;
import java.util.List;
import zet.gui.main.tabs.base.JPolygon;

/**
 *
 * @author Jan-Philipp Kappmeier
 */
public class FloorClickCreationPostActionAdapter implements FloorClickHandler {

    private final FloorClickCreationHandler stairBuilder;
    private final PostActionHandler postActionHandler;

    public FloorClickCreationPostActionAdapter(FloorClickCreationHandler creationHandler, PostActionHandler postActionHandler) {
        this.stairBuilder = creationHandler;
        System.out.println("CREATIANG A NEW STAIR WITH LOWER AND UPPER SIDE");
        this.postActionHandler = postActionHandler;
    }

    @Override
    public void mouseDown(Point p, List<JPolygon> elements) {
        if( postActionHandler.isActive() ) {
            postActionHandler.mouseDown(p, elements);
        } else {
            stairBuilder.mouseDown(p, elements);        
            if( stairBuilder.isCreationComplete() ) {
                postActionHandler.activate();                
            }
        } 
    }

    @Override
    public void mouseUp(Point p, List<Component> components) {
        if( postActionHandler.isActive() ) {
            postActionHandler.mouseUp(p, components);
        } else {
            stairBuilder.mouseUp(p, components);
            if( stairBuilder.isCreationComplete() ) {
                postActionHandler.activate();                
            }
        } 
    }

    @Override
    public void doubleClick(Point p, List<JPolygon> elements) {
        if( postActionHandler.isActive() ) {
            postActionHandler.doubleClick(p, elements);
        } else {
            stairBuilder.doubleClick(p, elements);
            if( stairBuilder.isCreationComplete() ) {
                postActionHandler.activate();                
            }
        } 
    }

    @Override
    public void mouseMove(Point p) {
        if( postActionHandler.isActive() ) {
            postActionHandler.mouseMove(p);
        } else {
            stairBuilder.mouseMove(p);
        } 
    }

    @Override
    public void rightClick() {
        if( postActionHandler.isActive() ) {
            postActionHandler.rightClick();
        } else {
            stairBuilder.rightClick();
            // We are done, switching over to post processing
            if( stairBuilder.isCreationComplete() ) {
                postActionHandler.activate();                
            }
        }
    }

    @Override
    public Point getCurrentPosition() {
        return stairBuilder.getCurrentPosition();
    }
}
