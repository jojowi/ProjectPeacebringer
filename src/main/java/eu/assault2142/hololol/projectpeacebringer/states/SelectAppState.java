/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.assault2142.hololol.projectpeacebringer.states;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.collision.CollisionResult;
import com.jme3.collision.CollisionResults;
import com.jme3.input.InputManager;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.math.Ray;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import eu.assault2142.hololol.projectpeacebringer.controls.SelectableControl;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author jojow
 */
public class SelectAppState extends AbstractAppState implements ActionListener {

    private static String LEFT_CLICK = "Left Click";
    private SimpleApplication app;
    private InputManager inputManager;
    private List<Spatial> selectables = new ArrayList<Spatial>();
    private List<Spatial> selected = new ArrayList<Spatial>();
    private Spatial currentSelection;

    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);
        this.app = (SimpleApplication) app;
        this.inputManager = app.getInputManager();

        inputManager.addMapping(LEFT_CLICK,
                new MouseButtonTrigger(MouseInput.BUTTON_LEFT));
        inputManager.addListener(this, LEFT_CLICK);
    }

    @Override
    public void cleanup() {
        super.cleanup();
        inputManager.deleteMapping(LEFT_CLICK);
    }

    public void onAction(String name, boolean isPressed, float tpf) {
        if (name.equals(LEFT_CLICK) && isPressed) {
            onClick();
        }
    }

    private void onClick() {
        CollisionResults results = new CollisionResults();

        Vector2f click2d = inputManager.getCursorPosition();

        Vector3f click3d = app.getCamera().getWorldCoordinates(new Vector2f(click2d.x, click2d.y), 0f).clone();

        Vector3f dir = app.getCamera().getWorldCoordinates(new Vector2f(click2d.x, click2d.y), 1f).subtractLocal(click3d);
        dir.normalizeLocal();
        Ray ray = new Ray(click3d, dir);
        for (Spatial spatial : selectables) {
            spatial.collideWith(ray, results);
            spatial.getControl(SelectableControl.class).setSelected(false);
            selected.clear();
            currentSelection = null;
        }
        if (results.size() > 0) {
            CollisionResult closest = results.getClosestCollision();

            currentSelection = closest.getGeometry().getParent();
            for (Spatial spatial : selectables) {
                if (spatial.getControl(SelectableControl.class) != null) {
                    if (spatial == currentSelection || (spatial instanceof Node && ((Node) spatial).hasChild(currentSelection))) {
                        spatial.getControl(SelectableControl.class).setSelected(true);
                        selected.add(spatial);
                    }
                }
            }
        }

    }

    public void addSelectable(Spatial s) {
        selectables.add(s);
    }

    public void removeSelectable(Spatial s) {
        selectables.remove(s);
    }

    public Spatial getCurrentSelection() {
        return currentSelection;
    }
}
