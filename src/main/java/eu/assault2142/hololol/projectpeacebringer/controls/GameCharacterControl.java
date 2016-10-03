/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.assault2142.hololol.projectpeacebringer.controls;

import com.jme3.bullet.control.BetterCharacterControl;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.AnalogListener;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;

/**
 *
 * @author jojow
 */
public class GameCharacterControl extends BetterCharacterControl implements ActionListener, AnalogListener {

    private boolean forward = false, backward = false, leftRotate = false, rightRotate = false, leftStrafe = false, rightStrafe = false;
    private float yaw = 0;
    private float moveSpeed = 10;

    public GameCharacterControl(float radius, float height, float mass) {
        super(radius, height, mass);
    }

    @Override
    public void update(float tpf) {
        super.update(tpf);
        Vector3f modelForwardDir = spatial.getWorldRotation().mult(Vector3f.UNIT_Z);
        Vector3f modelLeftDir = spatial.getWorldRotation().mult(Vector3f.UNIT_X);

        walkDirection.set(0, 0, 0);
        if (forward) {
            walkDirection.addLocal(modelForwardDir.mult(moveSpeed));
        } else if (backward) {
            walkDirection.addLocal(modelForwardDir.negate().multLocal(moveSpeed));
        }
        if (leftStrafe) {
            walkDirection.addLocal(modelLeftDir.mult(moveSpeed));
        } else if (rightStrafe) {
            walkDirection.addLocal(modelLeftDir.negate().multLocal(moveSpeed));
        }
        setWalkDirection(walkDirection);
    }

    @Override
    public void onAction(String binding, boolean value, float tpf) {
        switch (binding) {
            case "StrafeLeft":
                leftStrafe = value;
                break;
            case "StrafeRight":
                rightStrafe = value;
                break;
            case "MoveForward":
                forward = value;
                break;
            case "MoveBackward":
                backward = value;
                break;
            case "Jump":
                jump();
                break;
            case "Duck":
                setDucked(value);
                break;
        }
    }

    @Override
    public void onAnalog(String name, float value, float tpf) {
        switch (name) {
            case "RotateLeft":
                rotate(tpf * value);
                break;
            case "RotateRight":
                rotate(-tpf * value);
                break;
            case "MoveForward":
                break;
            case "MoveBackward":
                break;
            case "StrafeLeft":
                break;
            case "StrafeRight":
                moveSpeed = value * 3;
                break;
        }

    }

    protected void rotate(float value) {
        Quaternion rotate = new Quaternion().fromAngleAxis(FastMath.PI * value, Vector3f.UNIT_Y);
        rotate.multLocal(viewDirection);
        setViewDirection(viewDirection);
    }
}
