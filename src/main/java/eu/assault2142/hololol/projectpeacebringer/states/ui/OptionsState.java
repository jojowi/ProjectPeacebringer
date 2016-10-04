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
import com.simsilica.lemur.Axis;
import com.simsilica.lemur.Container;
import com.simsilica.lemur.FillMode;
import com.simsilica.lemur.TabbedPanel;
import com.simsilica.lemur.component.SpringGridLayout;

/**
 *
 * @author hololol2
 */
public class OptionsState extends AbstractAppState {

    private SimpleApplication app;
    private Container myWindow;

    @Override
    public void initialize(AppStateManager stateManager, Application application) {
        super.initialize(stateManager, application);
        app = (SimpleApplication) application;
        myWindow = new Container();
        myWindow = LemurHelper.createContainer(5, 95, 90, 90);
        TabbedPanel tabs = new TabbedPanel();
        Container graphics = tabs.addTab("Graphics", LemurHelper.createContainer(5, 5, 90, 90));
        graphics.setLayout(new SpringGridLayout(Axis.Y, Axis.X, FillMode.None, FillMode.None));
        graphics.addChild(LemurHelper.createLabel("Resolution", 0, 0, 20, 10, 20));
        graphics.addChild(LemurHelper.createListBox(0, 0, 20, 3, 1), 1);
        graphics.addChild(LemurHelper.createLabel("VSync", 0, 0, 10, 10, 20));
        graphics.addChild(LemurHelper.createLabel("Graphics Quality", 0, 0, 10, 10, 20));
        graphics.addChild(LemurHelper.createListBox(0, 0, 10, 3, 1), 1);
        graphics.addChild(LemurHelper.createLabel("Multisampling", 0, 0, 10, 10, 20));
        graphics.addChild(LemurHelper.createListBox(0, 0, 10, 3, 1), 1);
        graphics.addChild(LemurHelper.createLabel("Shadows", 0, 0, 10, 10, 20));
        graphics.addChild(LemurHelper.createListBox(0, 0, 10, 3, 1), 1);
        Container gameplay = tabs.addTab("Gameplay", LemurHelper.createContainer(5, 5, 90, 90));
        myWindow.addChild(tabs);
        app.getGuiNode().attachChild(myWindow);
    }

    @Override
    public void cleanup() {
        app.getGuiNode().detachChild(myWindow);
    }
}
