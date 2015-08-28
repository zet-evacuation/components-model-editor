package test;

import de.zet_evakuierung.model.AssignmentArea;
import de.zet_evakuierung.model.AssignmentType;
import de.zet_evakuierung.model.DelayArea;
import de.zet_evakuierung.model.EvacuationArea;
import de.zet_evakuierung.model.FloorInterface;
import de.zet_evakuierung.model.InaccessibleArea;
import de.zet_evakuierung.model.PlanEdge;
import de.zet_evakuierung.model.PlanPoint;
import de.zet_evakuierung.model.Project;
import de.zet_evakuierung.model.Room;
import de.zet_evakuierung.model.RoomEdge;
import de.zet_evakuierung.model.SaveArea;
import de.zet_evakuierung.model.StairArea;
import de.zet_evakuierung.model.TeleportArea;
import de.zet_evakuierung.model.ZControl;
import de.zet_evakuierung.model.ZModelRoomEvent;
import event.EventServer;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.HeadlessException;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import zet.gui.main.tabs.EditViewControl;
import zet.gui.main.tabs.JEditView;
import zet.gui.main.tabs.editor.EditMode;

/**
 *
 * @author Jan-Philipp Kappmeier
 */
public class ComponentTestWindow extends JFrame {

    public ComponentTestWindow() throws HeadlessException {
        super("Component test window");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                ComponentTestWindow testWindow = new ComponentTestWindow();
                
                ZControl control = new ZControl();
                Project p = control.getProject();
                FloorInterface floor1 = p.getBuildingPlan().getFloors().get(1);
                
                control.createNewPolygon(Room.class, floor1);
                control.addPoint(new PlanPoint(0,0));
                control.addPoint(new PlanPoint(1000,0));
                control.addPoint(new PlanPoint(1000,1000));
                control.addPoint(new PlanPoint(0,1000));
                control.addPoint(new PlanPoint(0,0));
                Room room1 = (Room)control.latestPolygon();
                System.out.println(room1.getAreas());
                
                // create some areas
                int areaCount = 0;
                control.createNewPolygon(EvacuationArea.class, room1);
                control.addPoint(new PlanPoint(0,0));
                control.addPoint(new PlanPoint(400,0));
                control.addPoint(new PlanPoint(400,400));
                control.addPoint(new PlanPoint(0,400));
                control.addPoint(new PlanPoint(0,0));
                assert room1.getAreas().size() == ++areaCount;
                assert room1.getEvacuationAreas().size() == 1;
                EvacuationArea exit = room1.getEvacuationAreas().get(0);

                control.createNewPolygon(SaveArea.class, room1);
                control.addPoint(new PlanPoint(400,0));
                control.addPoint(new PlanPoint(800,0));
                control.addPoint(new PlanPoint(800,400));
                control.addPoint(new PlanPoint(400,400));
                control.addPoint(new PlanPoint(400,0));
                assert room1.getAreas().size() == ++areaCount;
                
                control.createNewPolygon(DelayArea.class, room1);
                control.addPoint(new PlanPoint(400,400));
                control.addPoint(new PlanPoint(800,400));
                control.addPoint(new PlanPoint(800,800));
                control.addPoint(new PlanPoint(400,800));
                control.addPoint(new PlanPoint(400,400));
                assert room1.getAreas().size() == ++areaCount;
                
                control.createNewPolygon(InaccessibleArea.class, room1);
                control.addPoint(new PlanPoint(0,400));
                control.addPoint(new PlanPoint(400,400));
                control.addPoint(new PlanPoint(400,800));
                control.addPoint(new PlanPoint(0,800));
                control.addPoint(new PlanPoint(0,400));
                assert room1.getAreas().size() == ++areaCount;

                control.createNewPolygon(Room.class, floor1);
                control.addPoint(new PlanPoint(4000,3000));
                control.addPoint(new PlanPoint(5000,3000));
                control.addPoint(new PlanPoint(5000,4000));
                control.addPoint(new PlanPoint(4000,4000));
                control.addPoint(new PlanPoint(4000,3000));
                Room room2 = (Room)control.latestPolygon();
                
                assert p.getCurrentAssignment() != null;
                assert p.getCurrentAssignment().getAssignmentTypes().size() == 1;
                AssignmentType standardAssignment = p.getCurrentAssignment().getAssignmentTypes().get(0);
                AssignmentType newAssignment = new AssignmentType("Second Assignment", standardAssignment.getDiameter(),
                        standardAssignment.getAge(), standardAssignment.getFamiliarity(), standardAssignment.getPanic(),
                        standardAssignment.getDecisiveness(), standardAssignment.getReaction());
                p.getCurrentAssignment().addAssignmentType(newAssignment);
                
                areaCount = 0;
                
                int xOffset = room2.getPolygon().bounds().x;
                int yOffset = room2.getPolygon().bounds().y;
                System.out.println(xOffset);
                System.out.println(yOffset);
                
                control.createNewPolygon(AssignmentArea.class, room2);
                control.addPoint(new PlanPoint(xOffset + 0, yOffset + 0));
                control.addPoint(new PlanPoint(xOffset + 800, yOffset + 0));
                control.addPoint(new PlanPoint(xOffset + 800, yOffset + 400));
                control.addPoint(new PlanPoint(xOffset + 0, yOffset + 400));
                control.addPoint(new PlanPoint(xOffset + 0, yOffset + 0));
                assert room2.getAreas().size() == ++areaCount;

                control.createNewPolygon(TeleportArea.class, room2);
                control.addPoint(new PlanPoint(xOffset + 0, yOffset + 400));
                control.addPoint(new PlanPoint(xOffset + 400, yOffset + 400));
                control.addPoint(new PlanPoint(xOffset + 400, yOffset + 800));
                control.addPoint(new PlanPoint(xOffset + 0, yOffset + 800));
                control.addPoint(new PlanPoint(xOffset + 0, yOffset + 400));
                assert room2.getAreas().size() == ++areaCount;
                assert room2.getTeleportAreas().size() == 1;
                TeleportArea area1 = room2.getTeleportAreas().get(0);
                

                
                control.createNewPolygon(Room.class, floor1);
                control.addPoint(new PlanPoint(0000,3000));
                control.addPoint(new PlanPoint(1000,3000));
                control.addPoint(new PlanPoint(1000,4000));
                control.addPoint(new PlanPoint(0000,4000));
                control.addPoint(new PlanPoint(0000,3000));
                assert floor1.getRooms().size() == 3;
                Room room3 = (Room)control.latestPolygon();
                
                areaCount = 0;

                xOffset = room3.getPolygon().bounds().x;
                yOffset = room3.getPolygon().bounds().y;
                
                control.createNewPolygon(TeleportArea.class, room3);
                control.addPoint(new PlanPoint(xOffset + 0, yOffset + 0));
                control.addPoint(new PlanPoint(xOffset + 400, yOffset + 0));
                control.addPoint(new PlanPoint(xOffset + 400, yOffset + 400));
                control.addPoint(new PlanPoint(xOffset + 0, yOffset + 400));
                control.addPoint(new PlanPoint(xOffset + 0, yOffset + 0));
                assert room3.getAreas().size() == ++areaCount;
                assert room3.getTeleportAreas().size() == 1;
                TeleportArea area2 = room3.getTeleportAreas().get(0);
                
                // Only one target set for now
                area1.setTargetArea(area2);
                
                // set exit for the other
                area2.setExitArea(exit);
                
                control.createNewPolygon(StairArea.class, room3);
                //TODO: change implementation such that new, equal points can be used!
                PlanPoint lowerLevelStart = new PlanPoint(xOffset + 400, yOffset + 400);
                PlanPoint lowerLevelEnd = new PlanPoint(xOffset + 800, yOffset + 400);
                PlanPoint upperLevelStart = new PlanPoint(xOffset + 800, yOffset + 800);
                PlanPoint upperLevelEnd = new PlanPoint(xOffset + 400, yOffset + 800);
                control.addPoint(lowerLevelStart);
                control.addPoint(lowerLevelEnd);
                control.addPoint(upperLevelStart);
                control.addPoint(upperLevelEnd);
                control.addPoint(new PlanPoint(xOffset + 400, yOffset + 400));
                assert room3.getAreas().size() == ++areaCount;
                assert room3.getStairAreas().size() == 1;
                StairArea s = room3.getStairAreas().get(0);
                
                PlanEdge pe = s.getEdge(lowerLevelStart, lowerLevelEnd);
                s.setLowerLevel(pe.getSource(), pe.getTarget());
                pe = s.getEdge(upperLevelStart, upperLevelEnd);
                s.setUpperLevel(pe.getSource(), pe.getTarget());
                
                // create another room that is next to the last one to create teleport areas
                control.createNewPolygon(Room.class, floor1);
                control.addPoint(new PlanPoint(1000 + 0000,3000));
                control.addPoint(new PlanPoint(1200 + 0000,2800));
                control.addPoint(new PlanPoint(1800 + 1000,2800));
                control.addPoint(new PlanPoint(1800 + 1000,4000));
                control.addPoint(new PlanPoint(1000 + 0000,4000));
                control.addPoint(new PlanPoint(1000 + 0000,3000));
                assert floor1.getRooms().size() == 4;
                Room room4 = (Room)control.latestPolygon();
                
                RoomEdge e = room4.getPolygon().getEdge(new PlanPoint(1000, 4000), new PlanPoint(1000, 3000));
                control.makePassable(e);
                assert e.isPassable();
                assert e.getLinkTarget().getRoom() == room3;
                
                
//                FloorControl floorControl = new FloorControl(control, (Floor)floor1);
//                JFloor jfloor = floorControl.getView();
//                floorControl.initView();

                // Selection listener
                //FloorClickHandler editListener = new FloorClickSelectionHandler(floorControl);

                // Creation pointwise (normal)
                //FloorClickHandler editListener = new FloorClickCreatePointwiseHandler(floorControl);

                //PostActionHandler stairHandler = new PostActionStairHandler();
//                PostActionHandler stairHandler = new DefaultPostActionHandler();
//                stairHandler.setFloor(jfloor);
//                FloorClickCreationHandler editListener = new FloorClickCreatePointwiseHandler(floorControl);
//                FloorClickHandler handler = new FloorClickCreationPostActionAdapter(editListener, stairHandler);
//                jfloor.setPostActionListener(stairHandler);
//                editListener.setZetObjectType(ZetObjectTypes.Evacuation);
//                jfloor.setJFloorEditListener(handler);
                
                
                
                // Adding the floor directly                
                //testWindow.add(jfloor);
                
                // Adding the floor in a scroll pane
                //JFloorScrollPane<JFloor> floorScrollPane = new JFloorScrollPane<>(jfloor);
                //testWindow.add(floorScrollPane);
                
                // Adding a complete edit view
                //JEditView editView = new JEditView();
                EditViewControl evc = new EditViewControl(control, p.getBuildingPlan().getFloors());
                EventServer.getInstance().registerListener(evc.getView(), ZModelRoomEvent.class);
                evc.setEditMode(EditMode.Selection);
                JEditView editView = evc.getView();
                
                testWindow.add(editView);
                
                displayWindow(testWindow);
            }
        });
    }

    private static void displayWindow(ComponentTestWindow testWindow) {
        testWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        displayCentered(testWindow, 800, 600);
        testWindow.setVisible(true);
    }
    
    public static final int MAIN_SCREEN = 0;
    
    private static void displayCentered(JFrame window, int width, int height) {
        window.setSize(width, height);
        setWindowPosition(window, MAIN_SCREEN);
    }

    private static void setWindowPosition(JFrame window, int screen) {
        GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice[] allDevices = env.getScreenDevices();
        int topLeftX, topLeftY, screenX, screenY, windowPosX, windowPosY;

        if (screen < allDevices.length && screen > -1) {
            topLeftX = allDevices[screen].getDefaultConfiguration().getBounds().x;
            topLeftY = allDevices[screen].getDefaultConfiguration().getBounds().y;

            screenX = allDevices[screen].getDefaultConfiguration().getBounds().width;
            screenY = allDevices[screen].getDefaultConfiguration().getBounds().height;
        } else {
            topLeftX = allDevices[0].getDefaultConfiguration().getBounds().x;
            topLeftY = allDevices[0].getDefaultConfiguration().getBounds().y;

            screenX = allDevices[0].getDefaultConfiguration().getBounds().width;
            screenY = allDevices[0].getDefaultConfiguration().getBounds().height;
        }

        windowPosX = ((screenX - window.getWidth()) / 2) + topLeftX;
        windowPosY = ((screenY - window.getHeight()) / 2) + topLeftY;

        window.setLocation(windowPosX, windowPosY);
    }
}
