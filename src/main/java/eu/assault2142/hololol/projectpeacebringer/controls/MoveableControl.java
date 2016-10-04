/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.assault2142.hololol.projectpeacebringer.controls;

import com.jme3.bullet.control.BetterCharacterControl;
import com.jme3.math.Vector3f;

/**
 *
 * @author jojow
 */
public class MoveableControl extends BetterCharacterControl {

    private boolean move = false;
    private float moveSpeed = 10;

    public MoveableControl(float radius, float height, float mass) {
        super(radius, height, mass);
    }

    public void setDirection(Vector3f dir) {
        setViewDirection(dir);
        setWalkDirection(dir.mult(moveSpeed));
    }

    void stop() {
        setWalkDirection(Vector3f.ZERO);
    }
}
