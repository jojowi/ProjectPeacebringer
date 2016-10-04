/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.assault2142.hololol.projectpeacebringer.states.ui;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.simsilica.lemur.Button;
import com.simsilica.lemur.Command;
import com.simsilica.lemur.Container;
import eu.assault2142.hololol.projectpeacebringer.states.SinglePlayerState;

/**
 *
 * @author hololol2
 */
public class MainMenuState extends AbstractAppState {

    private SimpleApplication app;
    private Container myWindow;

    @Override
    public void initialize(AppStateManager stateManager, Application application) {
        super.initialize(stateManager, application);
        app = (SimpleApplication) application;
        myWindow = new Container();

        // Put it somewhere that we will see it
        // Note: Lemur GUI elements grow down from the upper left corner.
        myWindow.setLocalTranslation(0, 0, 0);

        myWindow.attachChild(LemurHelper.createButton("Singleplayer", 30, 82.5f, 40, 5, 20, true, (Command<Button>) (Button source) -> {
            app.getStateManager().detach(this);
            app.getStateManager().attach(new SinglePlayerState());
        }));
        myWindow.attachChild(LemurHelper.createButton("Multiplayer", 30, 62.5f, 40, 5, 20, false, (Command<Button>) (Button source) -> {
        }));
        myWindow.attachChild(LemurHelper.createButton("Options", 30, 42.5f, 40, 5, 20, true, (Command<Button>) (Button source) -> {
            app.getStateManager().detach(this);
            app.getStateManager().attach(new OptionsState());
        }));
        myWindow.attachChild(LemurHelper.createButton("Exit", 30, 22.5f, 40, 5, 20, true, (Command<Button>) (Button source) -> {
            app.stop();
        }));
        app.getGuiNode().attachChild(myWindow);
    }

    @Override
    public void cleanup() {
        app.getGuiNode().detachChild(myWindow);
    }
}
