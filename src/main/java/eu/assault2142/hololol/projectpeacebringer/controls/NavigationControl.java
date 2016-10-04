/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.assault2142.hololol.projectpeacebringer.controls;

import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.control.AbstractControl;
import eu.assault2142.hololol.monkeynav.data.GridMap;
import eu.assault2142.hololol.monkeynav.data.Path;
import eu.assault2142.hololol.monkeynav.pathfinders.ThetaPathfinder;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jojow
 */
public class NavigationControl extends AbstractControl {

    private NavigationControl.PathfinderThread pathfinderThread;
    private Vector2f waypointPosition = null;
    private Path path;

    public NavigationControl(GridMap grid) {
        pathfinderThread = new NavigationControl.PathfinderThread(grid);
        pathfinderThread.start();
    }

    @Override
    protected void controlUpdate(float tpf) {
        Vector3f spatialPosition = spatial.getWorldTranslation();
        if (waypointPosition != null) {
            Vector2f aiPosition = new Vector2f(spatialPosition.x, spatialPosition.z);
            Vector2f waypoint2D = waypointPosition;
            float distance = aiPosition.distance(waypoint2D);
            if (distance > 0.1f) {
                Vector2f direction = waypoint2D.subtract(aiPosition);
                direction.mult(tpf);
                spatial.getControl(MoveableControl.class).setDirection(new Vector3f(direction.x, 0, direction.y).normalize());
            } else {
                waypointPosition = null;
            }
        } else if (path != null && !path.isAtGoal()) {
            path.nextWaypoint();
            waypointPosition = path.getCurrentWaypoint();
        } else {
            spatial.getControl(MoveableControl.class).stop();
        }
    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {
    }

    public void moveTo(Vector3f target) {
        pathfinderThread.setTarget(target);
    }

    private class PathfinderThread extends Thread {

        private Vector3f target;
        private ThetaPathfinder pathfinder;
        private boolean pathfinding;
        private boolean running = true;

        public PathfinderThread(GridMap grid) {
            pathfinder = new ThetaPathfinder(grid);
            this.setDaemon(true);
        }

        @Override
        public void run() {
            while (running) {
                if (target != null) {
                    pathfinding = true;
                    path = pathfinder.computePath(getSpatial().getWorldTranslation(), target);

                    if (path != null) {
                        waypointPosition = path.getCurrentWaypoint();
                        target = null;
                    }
                    pathfinding = false;
                }
                try {
                    Thread.sleep(100);
                } catch (InterruptedException ex) {
                    Logger.getLogger(NavigationControl.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

        public void setTarget(Vector3f target) {
            this.target = target;
        }

        public boolean isPathfinding() {
            return pathfinding;
        }
    };

}
