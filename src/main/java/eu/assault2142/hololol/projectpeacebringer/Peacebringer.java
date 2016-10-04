/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.assault2142.hololol.projectpeacebringer;

import com.jme3.app.DebugKeysAppState;
import com.jme3.app.SimpleApplication;
import com.jme3.app.StatsAppState;
import com.jme3.renderer.RenderManager;
import com.jme3.system.AppSettings;
import com.simsilica.lemur.GuiGlobals;
import com.simsilica.lemur.style.BaseStyles;
import eu.assault2142.hololol.projectpeacebringer.states.ui.LemurHelper;
import eu.assault2142.hololol.projectpeacebringer.states.ui.MainMenuState;
import java.util.prefs.BackingStoreException;

/**
 * The Main Application
 *
 * @author hololol2
 */
public class Peacebringer extends SimpleApplication {

    private Settings mysettings;

    public static void main(String[] args) throws BackingStoreException {
        Peacebringer app = new Peacebringer();
        app.mysettings = Settings.load();
        app.settings = new AppSettings(true);
        app.settings.setResolution(app.mysettings.getWidth(), app.mysettings.getHeight());
        app.settings.setFullscreen(true);
        app.settings.setFrequency(app.mysettings.getFramerate());
        app.settings.setVSync(app.mysettings.isVsync());
        app.settings.setSamples(app.mysettings.getSamples());
        app.settings.setFrameRate(app.mysettings.getFramerate());
        app.setShowSettings(false);
        app.start();
    }

    public Peacebringer() {
        super(new StatsAppState(), new DebugKeysAppState());
    }

    @Override
    public void simpleInitApp() {
        // init Lemur
        GuiGlobals.initialize(this);
        BaseStyles.loadGlassStyle();
        GuiGlobals.getInstance().getStyles().setDefaultStyle("glass");
        LemurHelper.init(mysettings.getWidth(), mysettings.getHeight());
        // show Cursor
        inputManager.setCursorVisible(true);
        //start Menu
        MainMenuState mainMenuState = new MainMenuState();
        stateManager.attach(mainMenuState);
    }

    @Override
    public void simpleUpdate(float tpf) {
    }

    @Override
    public void simpleRender(RenderManager rm) {
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
