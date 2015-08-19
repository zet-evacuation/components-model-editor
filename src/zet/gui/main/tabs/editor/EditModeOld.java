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

package zet.gui.main.tabs.editor;

import de.zet_evakuierung.components.model.editor.DefaultGraphicsStyle;
import de.zet_evakuierung.model.AssignmentArea;
import de.zet_evakuierung.model.Barrier;
import de.zet_evakuierung.model.DelayArea;
import de.zet_evakuierung.model.EvacuationArea;
import de.zet_evakuierung.model.InaccessibleArea;
import de.zet_evakuierung.model.Room;
import de.zet_evakuierung.model.SaveArea;
import de.zet_evakuierung.model.StairArea;
import de.zet_evakuierung.model.TeleportArea;
import gui.editor.Areas;
//@//import gui.GUIOptionManager;
import java.awt.Color;
import java.util.LinkedList;
import zet.gui.components.editor.EditorLocalization;
//@//import zet.gui.GUILocalization;

/**
 * An enumeration of all possible edit modes of the editor.
 * @author Jan-Philipp Kappmeier, Timon Kelter
 */
public enum EditModeOld {
	Selection( "Selection", Type.Selection, null, false, null ),
	RoomCreation( "RoomCreation", Type.CreationRectangled, new DefaultGraphicsStyle().getWallColor(), false, Room.class ),
	InaccessibleAreaCreation( "InaccessibleAreaCreation", Type.CreationRectangled, new DefaultGraphicsStyle().getColorForArea(Areas.Inaccessible), true, InaccessibleArea.class ),
	AssignmentAreaCreation( "AssignmentAreaCreation", Type.CreationRectangled, new DefaultGraphicsStyle().getColorForArea(Areas.Assignment), true, AssignmentArea.class ),
	DelayAreaCreation( "DelayAreaCreation", Type.CreationRectangled, new DefaultGraphicsStyle().getColorForArea(Areas.Delay), true, DelayArea.class ),
	StairAreaCreation( "CreateStairArea", Type.CreationRectangled, new DefaultGraphicsStyle().getColorForArea(Areas.Stair), true, StairArea.class ),
	StairAreaMarkLowerLevel( "StairAreaMarkLowerLevel", Type.EditExisting, new DefaultGraphicsStyle().getColorForArea(Areas.Stair), true, null ),
	StairAreaMarkUpperLevel( "StairAreaMarkUpperLevel", Type.EditExisting, new DefaultGraphicsStyle().getColorForArea(Areas.Stair), true, null ),
	SaveAreaCreation( "SaveAreaCreation", Type.CreationRectangled, new DefaultGraphicsStyle().getColorForArea(Areas.Save), true, SaveArea.class ),
	EvacuationAreaCreation( "EvacuationAreaAreaCreation", Type.CreationRectangled, new DefaultGraphicsStyle().getColorForArea(Areas.Evacuation), true, EvacuationArea.class ),
	RoomCreationPointwise( "RoomCreationPointwise", Type.CreationPointwise, new DefaultGraphicsStyle().getWallColor(), false, Room.class ),
	BarrierCreationPointwise( "BarrierCreationPointwise", Type.CreationPointwise, new DefaultGraphicsStyle().getWallColor(), true, Barrier.class ),
	InaccessibleAreaCreationPointwise( "InaccessibleAreaCreationPointwise", Type.CreationPointwise, new DefaultGraphicsStyle().getColorForArea(Areas.Inaccessible), true, InaccessibleArea.class ),
	AssignmentAreaCreationPointwise( "AssignmentAreaCreationPointwise", Type.CreationPointwise, new DefaultGraphicsStyle().getColorForArea(Areas.Assignment), true, AssignmentArea.class ),
	DelayAreaCreationPointwise( "DelayAreaCreationPointwise", Type.CreationPointwise, new DefaultGraphicsStyle().getColorForArea(Areas.Delay), true, DelayArea.class ),
	StairAreaCreationPointwise( "CreateStairAreaPointwise", Type.CreationPointwise, new DefaultGraphicsStyle().getColorForArea(Areas.Stair), true, StairArea.class ),
	SaveAreaCreationPointwise( "SaveAreaCreationPointwise", Type.CreationPointwise, new DefaultGraphicsStyle().getColorForArea(Areas.Save), true, SaveArea.class ),
	EvacuationAreaCreationPointwise( "EvacuationAreaCreationPointwise", Type.CreationPointwise, new DefaultGraphicsStyle().getColorForArea(Areas.Evacuation), true, EvacuationArea.class ),
	TeleportEdgeCreation( "TeleportEdgeCreation", Type.EditExisting, new DefaultGraphicsStyle().getWallColor(), false, null ),
	PassableRoomCreation( "CreatePassageRoom", Type.EditExisting, new DefaultGraphicsStyle().getWallColor(), false, null ),
	TeleportAreaCreationPointwise( "CreateTeleportArea", Type.CreationPointwise, new DefaultGraphicsStyle().getColorForArea(Areas.Teleportation), true, TeleportArea.class ),
	TeleportAreaCreation( "CreateTeleportAreaPointwise", Type.CreationRectangled, new DefaultGraphicsStyle().getColorForArea(Areas.Teleportation), true, TeleportArea.class );

	static {
		// Here the partnerModes are set. This cannot be done in the constructor because it would need
		// illegal forward references
		setPartners( RoomCreation, RoomCreationPointwise );
		setPartners( InaccessibleAreaCreation, InaccessibleAreaCreationPointwise );
		setPartners( AssignmentAreaCreation, AssignmentAreaCreationPointwise );
		setPartners( DelayAreaCreation, DelayAreaCreationPointwise );
		setPartners( StairAreaCreation, StairAreaCreationPointwise );
		setPartners( SaveAreaCreation, SaveAreaCreationPointwise );
		setPartners( EvacuationAreaCreation, EvacuationAreaCreationPointwise );
		setPartners( TeleportAreaCreation, TeleportAreaCreationPointwise );
	}

	private static void setPartners( EditModeOld e1, EditModeOld e2 ) {
		e1.partnerMode = e2;
		e2.partnerMode = e1;
	}
	//private String name;
//	private int ID;
	private String key;
	private Type editType;
	private Color editorColor;
	private boolean createSubpolygons;
	private LinkedList payload;
	private EditModeOld partnerMode;
	private Class c;

	EditModeOld( String key, Type editType, Color editorColor, boolean createSubpolygons, Class c ) {
		this.key = "gui.editor.EditMode." + key;
//		this.ID = ID;
		this.editType = editType;
		this.editorColor = editorColor;
		this.createSubpolygons = createSubpolygons;
		this.payload = new LinkedList();
		this.partnerMode = null;
		this.c = c;
	}

	/**
	 * Returns all EditModes in which new polygons can be created
	 * @return list of edit modes
	 */
	public static LinkedList<EditModeOld> getCreationModes() {
		LinkedList<EditModeOld> trueEditModes = new LinkedList<EditModeOld>();

		for( EditModeOld e : values() )
			if( e.getType().equals( Type.CreationPointwise ) || e.getType().equals( Type.CreationRectangled ) )
				trueEditModes.add( e );
		return trueEditModes;
	}

	/**
	 * Returns all EditModes of a specified type
	 * @param editType the creation type
	 * @return list of edit modes
	 */
	public static LinkedList<EditModeOld> getCreationModes( Type editType ) {
		LinkedList<EditModeOld> trueEditModes = new LinkedList<EditModeOld>();

		for( EditModeOld e : values() )
			if( e.getType().equals( editType ) )
				trueEditModes.add( e );
		return trueEditModes;
	}

	@Override
	/** @returns a reasonable description of the edit mode. */
	public String toString() {
		return getName();
	}

	/**
	 * @return a reasonable description of the edit mode. */
	public String getName() {
		return EditorLocalization.LOC.getString( key );
	}

	/**
	 * @return The type of edits actions that are performed in this edit mode. */
	public Type getType() {
		return editType;
	}

	/** @return The color that the editor shall use to paint helper edges when using this edit mode. */
	public Color getEditorColor() {
		return editorColor;
	}

	/** @return The partner mode of a creation mode (PointWise Creation <-> Rectangled Creation)
	 *  or null if the current mode is not a polygon creation mode.
	 */
	public EditModeOld getPartnerMode() {
		return partnerMode;
	}

	/** @return whether this edit mode creates polygons that are part of
	 * other polygons (f.e. Areas). This field is always false for edit modes
	 * that are not of type CreationPointwise or CreationRectangled */
	public boolean doesCreateSubpolygons() {
		return createSubpolygons;
	}

	public Class getC() {
		return c;
	}

	/**
	 * Returns a list of further objects that are associated with the edit mode.
	 * The returned list is fully editable. For example in TeleportEdgeCreation
	 * mode, this list is used to store the first edge which must be connected
	 * to the second one.
	 * @return the objects
	 */
	public LinkedList getPayload() {
		return payload;
	}

	/**
	 * The types of EditModes.
	 */
	public enum Type {
		/** Any edit mode that is focused on selection of polygons on screen. */
		Selection,
		/** Any edit mode that is used to create polygons on screen in rectangle form. */
		CreationRectangled,
		/** Any edit mode that is used to create polygons on screen in free form. */
		CreationPointwise,
		/** Any edit mode that is used to modify existing polygons. */
		EditExisting;
	}
}
