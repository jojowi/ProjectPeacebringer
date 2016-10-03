/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.assault2142.hololol.projectpeacebringer;

import java.awt.DisplayMode;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jojow
 */
public class Settings implements Serializable {

    private boolean vsync;
    private int height;
    private int width;
    private int quality;
    private int samples;
    private int framerate;
    private int shadowmap;
    private int numshadowmap;

    public Settings() {
        GraphicsDevice device = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        DisplayMode mode = device.getDisplayMode();
        vsync = false;
        height = mode.getHeight();
        width = mode.getWidth();
        quality = 0;
        samples = 0;
        framerate = mode.getRefreshRate();
        shadowmap = -1;
    }

    public void save() {
        try {
            File f = new File("app.settings");
            //Runtime.getRuntime().exec("attrib +H "+f.getAbsolutePath());
            FileOutputStream fos = new FileOutputStream(f);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(this);
            oos.close();
            fos.close();
        } catch (IOException ex) {
            Logger.getLogger(Settings.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static Settings load() {
        File f = new File("app.settings");
        Settings s = null;
        if (f.exists() && f.length() != 0) {
            FileInputStream fis = null;
            try {
                fis = new FileInputStream(f);
                ObjectInputStream ois = new ObjectInputStream(fis);
                s = (Settings) ois.readObject();
                fis.close();
                ois.close();
            } catch (IOException | ClassNotFoundException ex) {
                Logger.getLogger(Settings.class.getName()).log(Level.SEVERE, null, ex);
                s = new Settings();
            } finally {
                try {
                    fis.close();
                } catch (IOException ex) {
                    Logger.getLogger(Settings.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } else {
            s = new Settings();
        }
        return s;
    }

    public boolean isVsync() {
        return vsync;
    }

    public void setVsync(boolean vsync) {
        this.vsync = vsync;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getQuality() {
        return quality;
    }

    public void setQuality(int quality) {
        this.quality = quality;
    }

    public int getSamples() {
        return samples;
    }

    public void setSamples(int samples) {
        this.samples = samples;
    }

    public int getFramerate() {
        return framerate;
    }

    public void setFramerate(int framerate) {
        this.framerate = framerate;
    }

    public int getShadowmap() {
        return shadowmap;
    }

    public void setShadowmap(int shadowmap) {
        this.shadowmap = shadowmap;
    }

    public int getNumshadowmap() {
        return numshadowmap;
    }

    public void setNumshadowmap(int numshadowmap) {
        this.numshadowmap = numshadowmap;
    }
}
