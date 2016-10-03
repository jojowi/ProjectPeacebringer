/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.assault2142.hololol.projectpeacebringer.states;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.collision.CollisionResults;
import com.jme3.input.InputManager;
import com.jme3.input.KeyInput;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.AnalogListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.math.Ray;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;

/**
 *
 * @author jojow
 */
public class ControlState extends AbstractAppState implements ActionListener, AnalogListener {

    private SimpleApplication app;
    private InputManager im;

    @Override
    public void onAction(String name, boolean isPressed, float tpf) {
        if (name.equals("LeftMouse") && isPressed) {
            pick();
        }
        if (name.equals("ButtonB") && isPressed) {
            build();
        }
    }

    @Override
    public void onAnalog(String name, float value, float tpf) {

    }

    @Override
    public void initialize(AppStateManager stateManager, Application application) {
        super.initialize(stateManager, application);
        app = (SimpleApplication) application;
        im = app.getInputManager();
        im.addMapping("LeftMouse", new MouseButtonTrigger(MouseInput.BUTTON_RIGHT));
        im.addListener(this, "LeftMouse");
        im.addMapping("ButtonB", new KeyTrigger(KeyInput.KEY_B));
        im.addListener(this, "ButtonB");
    }

    @Override
    public void update(float tpf) {
        Vector2f pos = im.getCursorPosition();

    }

    private void pick() {
        CollisionResults results = new CollisionResults();

        Vector2f click2d = im.getCursorPosition();

        Vector3f click3d = app.getCamera().getWorldCoordinates(new Vector2f(click2d.x, click2d.y), 0f).clone();

        Vector3f dir = app.getCamera().getWorldCoordinates(new Vector2f(click2d.x, click2d.y), 1f).subtractLocal(click3d);
        dir.normalizeLocal();
        Ray ray = new Ray(click3d, dir);

        app.getStateManager().getState(SinglePlayerState.class).getTerrain().collideWith(ray, results);
        if (results.size() != 0) {
            app.getStateManager().getState(SinglePlayerState.class).pathFind(results.getClosestCollision().getContactPoint());

        }
    }

    private void build() {
        CollisionResults results = new CollisionResults();

        Vector2f click2d = im.getCursorPosition();

        Vector3f click3d = app.getCamera().getWorldCoordinates(new Vector2f(click2d.x, click2d.y), 0f).clone();

        Vector3f dir = app.getCamera().getWorldCoordinates(new Vector2f(click2d.x, click2d.y), 1f).subtractLocal(click3d);
        dir.normalizeLocal();
        Ray ray = new Ray(click3d, dir);

        app.getStateManager().getState(SinglePlayerState.class).getTerrain().collideWith(ray, results);
        app.getStateManager().getState(SinglePlayerState.class).createBuilding(results.getClosestCollision().getContactPoint());
    }
}
