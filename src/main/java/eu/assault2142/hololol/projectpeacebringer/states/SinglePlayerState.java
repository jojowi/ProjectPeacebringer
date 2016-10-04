/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.assault2142.hololol.projectpeacebringer.states;

import com.jme3.app.Application;
import com.jme3.app.state.AppStateManager;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;
import com.jme3.scene.shape.Quad;
import eu.assault2142.hololol.projectpeacebringer.controls.GameCharacterControl;
import eu.assault2142.hololol.projectpeacebringer.controls.NavigationControl;
import eu.assault2142.hololol.projectpeacebringer.controls.SelectableControl;
import java.util.ArrayList;

/**
 *
 * @author jojow
 */
public class SinglePlayerState extends GameplayState {

    private ArrayList<Geometry> points = new ArrayList();
    private Geometry geom;
    private Node character;
    private Geometry marker2;
    private Geometry marker;

    @Override
    public void initialize(AppStateManager stateManager, Application application) {
        super.initialize(stateManager, application);

        marker = new Geometry("Marker", new Quad(1f, 1f));
        marker.rotate(-FastMath.HALF_PI, 0, 0);
        marker.setLocalTranslation(-0.5f, 6.05f, 0.5f);

        Material selectionMaterial = new Material(app.getAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md");
        marker.setMaterial(selectionMaterial);

        Box b = new Box(3, 3, 3);
        geom = new Geometry("Box", b);
        Material mat = new Material(app.getAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", ColorRGBA.Green);
        geom.setMaterial(mat);
        geom.setLocalTranslation(0, 1.5f, 0);
        character = new Node();
        character.attachChild(geom);
        character.addControl(new NavigationControl(map));
        SelectableControl sc = new SelectableControl();
        sc.setMarker(marker);
        character.addControl(sc);
        character.setLocalTranslation(new Vector3f(-20, tq.getHeight(new Vector2f(-20, 0)) + 10, 0));

        GameCharacterControl physicsCharacter = new GameCharacterControl(0.3f, 2.5f, 8f);
        character.addControl(physicsCharacter);
        bulletAppState.getPhysicsSpace().add(physicsCharacter);
        physicsCharacter.warp(new Vector3f(-20, tq.getHeight(new Vector2f(-20, 0)) + 10, 0));

        app.getRootNode().attachChild(character);
        app.getStateManager().getState(SelectAppState.class).addSelectable(character);
    }

    public void pathFind(Vector3f pos) {
        Spatial s = app.getStateManager().getState(SelectAppState.class).getCurrentSelection();
        if (s != null) {
            s.getControl(NavigationControl.class).moveTo(pos);
        }
    }

    public void addPoint(Vector3f pos) {
        Box b = new Box(1, 1, 1);
        Geometry geom = new Geometry("Box", b);

        Material mat = new Material(app.getAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", ColorRGBA.Blue);
        geom.setMaterial(mat);
        geom.setLocalTranslation(pos);
        app.getRootNode().attachChild(geom);
        points.add(geom);
    }

    public void addPoint(Vector2f pos, ColorRGBA color) {
        Box b = new Box(1, 1, 1);
        Geometry geom = new Geometry("Box", b);
        Vector3f p = new Vector3f(pos.x, tq.getHeight(pos), pos.y);
        Material mat = new Material(app.getAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", color);
        geom.setMaterial(mat);
        geom.setLocalTranslation(p);
        app.getRootNode().attachChild(geom);
        points.add(geom);
    }

    public void markPoint(Vector3f pos) {
        Box b = new Box(3, 3, 3);
        Geometry geom = new Geometry("Box", b);

        Material mat = new Material(app.getAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", ColorRGBA.Red);
        geom.setMaterial(mat);
        geom.setLocalTranslation(pos);
        app.getRootNode().attachChild(geom);
        points.add(geom);
    }

    public void createBuilding(Vector3f position) {
        map.blockArea(position, 15f);
        Box b = new Box(15f, 5f, 15f);
        Geometry geom = new Geometry("Box", b);

        Material mat = new Material(app.getAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", ColorRGBA.Red);
        geom.setMaterial(mat);
        geom.setLocalTranslation(new Vector3f(position.x, tq.getHeight(new Vector2f(position.x, position.z)) + 2.5f, position.z));
        app.getRootNode().attachChild(geom);
    }
}
