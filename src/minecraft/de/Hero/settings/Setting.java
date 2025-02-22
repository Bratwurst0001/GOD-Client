package de.Hero.settings;

import java.util.ArrayList;

import me.bratwurst.module.Module;
import net.minecraft.util.EnumChatFormatting;


/**
 * Made by HeroCode
 * it's free to use
 * but you have to credit me
 *
 * @author HeroCode
 */
public class Setting {


    private String name;
    private Module parent;
    public String mode;
    private String sval;
    private ArrayList<String> options;


    private boolean bval;

    private double dval;
    private double min;
    private double max;
    private boolean onlyint = false;


    public Setting(String name, Module parent, String sval, ArrayList<String> options) {
        this.name =  name;
        this.parent = parent;
        this.sval = sval;
        this.options = options;
        this.mode = "Combo";
    }

    public Setting(String name, Module parent, boolean bval) {
        this.name = name;
        this.parent = parent;
        this.bval = bval;
        this.mode = "Check";
    }

    public Setting(String name, Module parent, double dval, double min, double max, boolean onlyint) {
        this.name = name;
        this.parent = parent;
        this.dval = dval;
        this.min = min;
        this.max = max;
        this.onlyint = onlyint;
        this.mode = "Slider";
    }

    public String getName() {
        return name;
    }

    public Module getParentMod() {
        return parent;
    }

    public String getValString() {
        return this.sval;
    }

    public String getSettingname() {
        return this.name;
    }

    public Module getModule() {
        return this.parent;
    }

    public ArrayList<String> getOptions() {
        return this.options;
    }

    public boolean getValBoolean() {
        return this.bval;
    }

    public void setValBoolean(boolean in) {
        this.bval = in;
        /*
        if (Client.getInstance().getSaveLoad() != null) {
            Client.getInstance().getSaveLoad().save();
        }
        */

    }

    public double getValDouble() {
        if (this.onlyint) {
            this.dval = (int) dval;
        }
        return this.dval;
    }

    public void setValString(String in) {
        this.sval = in;
        /*
        if (Client.getInstance().getSaveLoad() != null) {
            Client.getInstance().getSaveLoad().save();
        }
        */
    }

    public int getValInt() {
        if (this.onlyint) {
            this.dval = (int) dval;
        }
        return (int) this.dval;
    }

    public void setValDouble(double in) {
        this.dval = in;
        /*
        if (Client.getInstance().getSaveLoad() != null) {
            Client.getInstance().getSaveLoad().save();
        }
        */
    }

    public double getMin() {
        return this.min;
    }

    public double getMax() {
        return this.max;
    }

    public boolean isCombo() {
        return this.mode.equalsIgnoreCase("Combo");
    }

    public boolean isCheck() {
        return this.mode.equalsIgnoreCase("Check");
    }

    public boolean isSlider() {
        return this.mode.equalsIgnoreCase("Slider");
    }

    public boolean onlyInt() {
        return this.onlyint;
    }
}

interface Dependency {
    boolean check();
}
