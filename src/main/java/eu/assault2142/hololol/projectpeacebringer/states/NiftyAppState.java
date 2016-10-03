/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.assault2142.hololol.projectpeacebringer.states;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.niftygui.NiftyJmeDisplay;
import de.lessvoid.nifty.Nifty;

/**
 *
 * @author jojow
 */
public class NiftyAppState extends AbstractAppState {

    private SimpleApplication app;
    private Nifty nifty;
    private float timeleft = 0;

    @Override
    public void initialize(AppStateManager stateManager, Application application) {
        super.initialize(stateManager, application);
        app = (SimpleApplication) application;
        NiftyJmeDisplay niftyDisplay = new NiftyJmeDisplay(application.getAssetManager(), application.getInputManager(), application.getAudioRenderer(), application.getRenderManager().getPostView("Gui Default"));
        nifty = niftyDisplay.getNifty();
        app.getRenderManager().getPostView("Gui Default").addProcessor(niftyDisplay);
        nifty.fromXml("Interface/introScreen.xml", "introScreen");
        nifty.addXml("Interface/mainMenu.xml");
        nifty.addXml("Interface/optionsScreen.xml");
        timeleft = 9;
    }

    @Override
    public void update(float tpf) {
        if (timeleft - tpf <= 0) {
            timeleft = Float.MAX_VALUE;
            nifty.gotoScreen("mainMenu");
        } else {
            timeleft -= tpf;
        }
    }
    /*public void toOptions() {
     loadOptions();
     main.hide();
     options.show();
     }
     public void toMain() {
     try {
     saveOptions();
     } catch (BackingStoreException ex) {
     Logger.getLogger(MenuState.class.getName()).log(Level.SEVERE, null, ex);
     }
     options.hide();
     main.show();
     }
     public void saveOptions() throws BackingStoreException{
     AppSettings s = app.getContext().getSettings();
     s.setSamples(Integer.parseInt((String)samples.getSelectedListItem().getValue()));
     s.setVSync(vsync.getIsChecked());
     String res = (String)resolution.getSelectedListItem().getValue();
     String[] wh = res.split("x");
     app.setSettings(s);
     app.getContext().getSettings().save("ProjectPeacebringer");
     app.restart();
     }
     public void loadOptions(){
     AppSettings s = app.getContext().getSettings();
     vsync.setIsChecked(s.isVSync());
     samples.setSelectedByValue(s.getSamples(), false);
     }*/

}
