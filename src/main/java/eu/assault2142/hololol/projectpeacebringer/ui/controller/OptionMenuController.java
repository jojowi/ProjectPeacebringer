/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.assault2142.hololol.projectpeacebringer.ui.controller;

import com.jme3.renderer.Limits;
import com.jme3.renderer.opengl.GLRenderer;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.controls.DropDown;
import de.lessvoid.nifty.controls.RadioButton;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;
import eu.assault2142.hololol.projectpeacebringer.Peacebringer;
import eu.assault2142.hololol.projectpeacebringer.Settings;
import java.awt.DisplayMode;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.util.Arrays;
import java.util.LinkedList;

/**
 *
 * @author jojow
 */
public class OptionMenuController implements ScreenController {

    private Nifty nifty;
    private Screen screen;
    private DropDown graphic;
    private DropDown shadow;
    private DropDown resolution;
    private DropDown framerate;
    private DropDown multisampling;
    private Settings settings;
    private RadioButton vsync;
    private RadioButton novsync;

    @Override
    public void bind(Nifty nifty, Screen screen) {
        this.nifty = nifty;
        this.screen = screen;
        initDropDowns();
    }

    public void initDropDowns() {
        settings = Peacebringer.APP.getSettings();
        graphic = screen.findNiftyControl("dropdownquality", DropDown.class);
        graphic.addAllItems(Arrays.asList("Low", "Medium", "High", "Ultra"));
        graphic.selectItemByIndex(settings.getQuality());
        shadow = screen.findNiftyControl("dropdownshadows", DropDown.class);
        shadow.addAllItems(Arrays.asList("Off", "Low", "Medium", "High", "Ultra"));
        int shadowlevel;
        switch (settings.getShadowmap()) {
            case -1:
                shadowlevel = 0;
                break;
            case 1024:
                shadowlevel = 1;
                break;
            case 2048:
                shadowlevel = 2;
                break;
            case 4096:
                shadowlevel = 3;
                break;
            case 8192:
                shadowlevel = 4;
                break;
            default:
                shadowlevel = 0;
                break;
        }
        shadow.selectItemByIndex(shadowlevel);
        GraphicsDevice device = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        DisplayMode[] modes = device.getDisplayModes();
        Arrays.sort(modes, OptionMenuController::compareDisplayModes);
        LinkedList<String> res = new LinkedList();
        for (DisplayMode mode : modes) {
            if (!res.contains(mode.getWidth() + "x" + mode.getHeight() + "@" + mode.getRefreshRate() + "fps")) {
                res.add(mode.getWidth() + "x" + mode.getHeight() + "@" + mode.getRefreshRate() + "fps");
            }
        }
        resolution = screen.findNiftyControl("dropdownresolution", DropDown.class);
        resolution.addAllItems(res);
        resolution.selectItem(settings.getWidth() + "x" + settings.getHeight() + "@" + settings.getFramerate() + "fps");
        Integer maxsampling = Math.min(((GLRenderer) Peacebringer.APP.getRenderer()).getLimits().get(Limits.FrameBufferSamples), 16);
        LinkedList<String> samples = new LinkedList(Arrays.asList("0", "2", "4", "8", "16"));
        samples.removeIf((String s) -> {
            return Integer.parseInt(s) > maxsampling;
        });
        multisampling = screen.findNiftyControl("dropdownsampling", DropDown.class);
        multisampling.addAllItems(samples);
        multisampling.selectItem(settings.getSamples() + "");
        vsync = screen.findNiftyControl("option-1", RadioButton.class);
        novsync = screen.findNiftyControl("option-2", RadioButton.class);
        if (settings.isVsync()) {
            vsync.select();
        } else {
            novsync.select();
        }
    }

    @Override
    public void onStartScreen() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void onEndScreen() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void toMain() {
        nifty.gotoScreen("mainMenu");
    }

    public void save() {
        settings.setSamples(Integer.parseInt((String) multisampling.getSelection()));
        settings.setQuality(graphic.getSelectedIndex());
        String res = (String) resolution.getSelection();
        String[] resf = res.split("@");
        String[] wh = resf[0].split("x");
        settings.setFramerate(Integer.parseInt(resf[1].replace("fps", "")));
        settings.setWidth(Integer.parseInt(wh[0]));
        settings.setHeight(Integer.parseInt(wh[1]));
        settings.setVsync(vsync.isActivated());
        int shadows = shadow.getSelectedIndex();
        int shadowmap;
        int numshadowmap;
        switch (shadows) {
            case 0:
                shadowmap = -1;
                numshadowmap = 0;
                break;
            case 1:
                shadowmap = 1024;
                numshadowmap = 2;
                break;
            case 2:
                shadowmap = 2048;
                numshadowmap = 3;
                break;
            case 3:
                shadowmap = 4096;
                numshadowmap = 4;
                break;
            case 4:
                shadowmap = 8192;
                numshadowmap = 4;
                break;
            default:
                shadowmap = -1;
                numshadowmap = 0;
                break;
        }
        settings.setShadowmap(shadowmap);
        settings.setNumshadowmap(numshadowmap);
        settings.save();
        Peacebringer.APP.updateGraphicSettings();
        toMain();
    }

    public static int compareDisplayModes(DisplayMode m1, DisplayMode m2) {
        if (m1.getWidth() != m2.getWidth()) {
            return m1.getWidth() - m2.getWidth();
        }
        if (m1.getHeight() != m2.getHeight()) {
            return m1.getHeight() - m2.getHeight();
        }
        return m1.getRefreshRate() - m2.getRefreshRate();
    }
}
