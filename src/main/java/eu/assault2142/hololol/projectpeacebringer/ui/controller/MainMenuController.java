/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.assault2142.hololol.projectpeacebringer.ui.controller;

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;
import eu.assault2142.hololol.projectpeacebringer.Peacebringer;
import eu.assault2142.hololol.projectpeacebringer.states.SinglePlayerState;

/**
 *
 * @author jojow
 */
public class MainMenuController implements ScreenController {

    private Nifty nifty;

    public void exit() {
        Peacebringer.APP.stop();
    }

    public void startSingle() {
        nifty.gotoScreen("empty");
        SinglePlayerState sps = new SinglePlayerState();
        Peacebringer.APP.getStateManager().attach(sps);
    }

    public void toOptions() {
        nifty.gotoScreen("optionsScreen");
    }

    @Override
    public void bind(Nifty nifty, Screen screen) {
        this.nifty = nifty;
    }

    @Override
    public void onStartScreen() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void onEndScreen() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
