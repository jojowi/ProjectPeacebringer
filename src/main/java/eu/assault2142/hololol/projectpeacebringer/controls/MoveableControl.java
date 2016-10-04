/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.assault2142.hololol.projectpeacebringer.controls;

import com.jme3.bullet.control.BetterCharacterControl;
import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;
import eu.assault2142.hololol.monkeynav.data.GridMap;

/**
 *
 * @author jojow
 */
public class MoveableControl extends BetterCharacterControl {

    private boolean move = false;
    private float moveSpeed = 10;
    private GridMap grid;

    public MoveableControl(float radius, float height, float mass, GridMap grid) {
        super(radius, height, mass);
        this.grid = grid;
    }

    @Override
    public void setSpatial(Spatial spatial) {
        super.setSpatial(spatial);
        spatial.addControl(new NavigationControl(grid));
    }

    public void setDirection(Vector3f dir) {
        setViewDirection(dir);
        setWalkDirection(dir.mult(moveSpeed));
    }

    void stop() {
        setWalkDirection(Vector3f.ZERO);
    }
}
