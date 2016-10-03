/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.assault2142.hololol.projectpeacebringer;

import com.jme3.app.FlyCamAppState;
import com.jme3.app.SimpleApplication;
import com.jme3.renderer.RenderManager;
import com.jme3.system.AppSettings;
import eu.assault2142.hololol.projectpeacebringer.states.NiftyAppState;
import java.util.prefs.BackingStoreException;

/**
 *
 * @author jojow
 */
public class Peacebringer extends SimpleApplication {

    public static Peacebringer APP;
    private Settings mysettings;

    public static void main(String[] args) throws BackingStoreException {
        APP = new Peacebringer();
        APP.mysettings = Settings.load();
        APP.settings = new AppSettings(true);
        APP.settings.setResolution(APP.mysettings.getWidth(), APP.mysettings.getHeight());
        APP.settings.setFullscreen(true);
        APP.settings.setFrequency(APP.mysettings.getFramerate());
        APP.settings.setVSync(APP.mysettings.isVsync());
        APP.settings.setSamples(APP.mysettings.getSamples());
        APP.settings.setFrameRate(APP.mysettings.getFramerate());
        APP.setShowSettings(false);
        APP.start();
    }

    @Override
    public void simpleInitApp() {
        getStateManager().detach(getStateManager().getState(FlyCamAppState.class));
        inputManager.setCursorVisible(true);
        NiftyAppState niftystate = new NiftyAppState();
        stateManager.attach(niftystate);
    }

    @Override
    public void simpleUpdate(float tpf) {
    }

    @Override
    public void simpleRender(RenderManager rm) {
        /* (optional) Make advanced modifications to frameBuffer and scene graph. */
    }

    public Settings getSettings() {
        return mysettings;
    }

    public void updateGraphicSettings() {
        settings.setResolution(mysettings.getWidth(), mysettings.getHeight());
        settings.setFullscreen(true);
        settings.setFrequency(mysettings.getFramerate());
        settings.setVSync(mysettings.isVsync());
        settings.setSamples(mysettings.getSamples());
        settings.setFrameRate(mysettings.getFramerate());
        restart();
    }
}
