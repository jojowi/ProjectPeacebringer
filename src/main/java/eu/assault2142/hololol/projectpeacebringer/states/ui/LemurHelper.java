/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.assault2142.hololol.projectpeacebringer.states.ui;

import com.jme3.math.Vector3f;
import com.simsilica.lemur.Button;
import com.simsilica.lemur.Command;
import com.simsilica.lemur.Container;
import com.simsilica.lemur.HAlignment;
import com.simsilica.lemur.Label;
import com.simsilica.lemur.ListBox;
import com.simsilica.lemur.VAlignment;

/**
 *
 * @author hololol2
 */
public class LemurHelper {

    private static int screenwidth = -1, screenheight = -1;

    public static void init(int width, int height) {
        screenheight = height;
        screenwidth = width;
    }

    public static Button createButton(String text, float posX, float posY, float width, float height, float fontsize, boolean enabled, Command<? super Button>... commands) {
        if (width < 0 || width > 100 || height < 0 || height > 100 || posX < 0 || posX > 100 || posY < 0 || posY > 100) {
            throw new IllegalArgumentException("Width,height,posX and posY have to be from 0 to 100");
        }
        if (screenwidth == -1) {
            throw new RuntimeException("Screenwidth and -height not initialized");
        }
        Button button = new Button(text);
        button.setLocalTranslation(posX * screenwidth * 0.01f, posY * screenheight * 0.01f, 0);
        button.addClickCommands(commands);
        button.setSize(new Vector3f(width * screenwidth * 0.01f, height * screenheight * 0.01f, 0));
        button.setTextHAlignment(HAlignment.Center);
        button.setTextVAlignment(VAlignment.Center);
        button.setFontSize(fontsize);
        button.setEnabled(enabled);
        return button;
    }

    public static Label createLabel(String text, float posX, float posY, float width, float height, float fontsize) {
        if (width < 0 || width > 100 || height < 0 || height > 100 || posX < 0 || posX > 100 || posY < 0 || posY > 100) {
            throw new IllegalArgumentException("Width,height,posX and posY have to be from 0 to 100");
        }
        if (screenwidth == -1) {
            throw new RuntimeException("Screenwidth and -height not initialized");
        }
        Label label = new Label(text);
        label.setLocalTranslation(posX * screenwidth * 0.01f, posY * screenheight * 0.01f, 0);
        label.setPreferredSize(new Vector3f(width * screenwidth * 0.01f, height * screenheight * 0.01f, 0));
        label.setTextHAlignment(HAlignment.Center);
        label.setTextVAlignment(VAlignment.Center);
        label.setFontSize(fontsize);
        return label;
    }

    public static Container createContainer(float posX, float posY, float width, float height) {
        if (width < 0 || width > 100 || height < 0 || height > 100 || posX < 0 || posX > 100 || posY < 0 || posY > 100) {
            throw new IllegalArgumentException("Width,height,posX and posY have to be from 0 to 100");
        }
        if (screenwidth == -1) {
            throw new RuntimeException("Screenwidth and -height not initialized");
        }
        Container container = new Container();
        container.setLocalTranslation(posX * screenwidth * 0.01f, posY * screenheight * 0.01f, 0);
        container.setPreferredSize(new Vector3f(width * screenwidth * 0.01f, height * screenheight * 0.01f, 0));
        return container;
    }

    public static ListBox<String> createListBox(float posX, float posY, float width, float height, int items) {
        if (width < 0 || width > 100 || height < 0 || height > 100 || posX < 0 || posX > 100 || posY < 0 || posY > 100) {
            throw new IllegalArgumentException("Width,height,posX and posY have to be from 0 to 100");
        }
        if (screenwidth == -1) {
            throw new RuntimeException("Screenwidth and -height not initialized");
        }
        ListBox listbox = new ListBox();
        listbox.setLocalTranslation(posX * screenwidth * 0.01f, posY * screenheight * 0.01f, 0);
        listbox.setVisibleItems(items);
        listbox.setPreferredSize(new Vector3f(width * screenwidth * 0.01f, height * screenheight * 0.01f, 0));
        return listbox;
    }
}
