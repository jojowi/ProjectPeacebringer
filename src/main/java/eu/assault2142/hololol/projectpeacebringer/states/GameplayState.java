/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.assault2142.hololol.projectpeacebringer.states;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.terrain.geomipmap.TerrainQuad;
import eu.assault2142.hololol.monkeynav.GridMapGenerator;
import eu.assault2142.hololol.monkeynav.data.GridMap;

/**
 *
 * @author jojow
 */
public class GameplayState extends AbstractAppState {

    TerrainQuad tq;
    SimpleApplication app;
    BulletAppState bulletAppState;
    GridMap map;

    @Override
    public void initialize(AppStateManager stateManager, Application application) {
        super.initialize(stateManager, application);
        app = (SimpleApplication) application;
        //inputManager.deleteMapping(SimpleApplication.INPUT_MAPPING_EXIT);
        app.getInputManager().deleteMapping(SimpleApplication.INPUT_MAPPING_CAMERA_POS);
        app.getInputManager().deleteMapping(SimpleApplication.INPUT_MAPPING_HIDE_STATS);
        app.getInputManager().deleteMapping(SimpleApplication.INPUT_MAPPING_MEMORY);

        //init Physics
        bulletAppState = new BulletAppState();
        stateManager.attach(bulletAppState);

        //load Terrain
        Node loadModel = (Node) app.getAssetManager().loadModel("Scenes/testScene.j3o");
        tq = (TerrainQuad) loadModel.getChild(0);
        app.getRootNode().attachChild(loadModel);
        RigidBodyControl terrainphy = new RigidBodyControl(0);
        tq.addControl(terrainphy);
        bulletAppState.getPhysicsSpace().add(terrainphy);

        //generate Navmesh
        GridMapGenerator gen = GridMapGenerator.createGenerator(3f, 2f, 2f, 0.2f, 30f, false);
        map = gen.build(gen.terrain2mesh(tq));

        ControlState cs = new ControlState();
        stateManager.attach(cs);

        SelectAppState sas = new SelectAppState();
        app.getStateManager().attach(sas);

        RtsCam rtsCam = new RtsCam(RtsCam.UpVector.Y_UP);
        rtsCam.setCenter(new Vector3f(0, 0, 0));
        rtsCam.setDistance(200);
        rtsCam.setMaxSpeed(RtsCam.DoF.FWD, 150, 0.1f);
        rtsCam.setMaxSpeed(RtsCam.DoF.SIDE, 150, 0.1f);
        rtsCam.setMaxSpeed(RtsCam.DoF.DISTANCE, 100, 0.1f);
        rtsCam.setHeightProvider((Vector2f coord) -> tq.getHeight(coord) + 30);
        app.getStateManager().attach(rtsCam);
        rtsCam.setCenter(Vector3f.ZERO);

    }

    @Override
    public void update(float tpf) {
    }

    public TerrainQuad getTerrain() {
        return tq;
    }
}
