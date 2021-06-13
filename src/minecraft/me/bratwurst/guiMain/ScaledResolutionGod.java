package me.bratwurst.guiMain;

import net.minecraft.client.Minecraft;
import net.minecraft.util.MathHelper;

public class ScaledResolutionGod {
    private final double scaledWidthD;

    private final double scaledHeightD;

    private int scaledWidth;

    private int scaledHeight;

    private int heigt;

    private int scaleFactor;

    Minecraft minecraft;

    public ScaledResolutionGod(Minecraft minecraft) {
        this.minecraft = minecraft;
        this.scaledWidth = minecraft.displayWidth;
        this.scaledHeight = minecraft.displayHeight;
        this.scaleFactor = 1;
        int i = 3;
        if (i == 0)
            i = 1000;
        while (this.scaleFactor < i && this.scaledWidth / (this.scaleFactor + 1) >= 320 &&
                this.scaledHeight / (this.scaleFactor + 1) >= 240)
            this.scaleFactor++;
        this.scaledWidthD = this.scaledWidth / this.scaleFactor;
        this.scaledHeightD = this.scaledHeight / this.scaleFactor;
        this.scaledWidth = MathHelper.ceiling_double_int(this.scaledWidthD);
        this.scaledHeight = MathHelper.ceiling_double_int(this.scaledHeightD);
    }

    public ScaledResolutionGod(Minecraft minecraft, int displayWidth, int displayHeight) {
        this.scaledWidth = displayWidth;
        this.scaledHeight = displayHeight;
        this.scaleFactor = 1;
        boolean flag = minecraft.isUnicode();
        int i = 3;
        if (i == 0)
            i = 1000;
        while (this.scaleFactor < i && this.scaledWidth / (this.scaleFactor + 1) >= 320 &&
                this.scaledHeight / (this.scaleFactor + 1) >= 240)
            this.scaleFactor++;
        if (flag && this.scaleFactor % 2 != 0 && this.scaleFactor != 1)
            this.scaleFactor--;
        this.scaledWidthD = this.scaledWidth / this.scaleFactor;
        this.scaledHeightD = this.scaledHeight / this.scaleFactor;
        this.scaledWidth = MathHelper.ceiling_double_int(this.scaledWidthD);
        this.scaledHeight = MathHelper.ceiling_double_int(this.scaledHeightD);
    }

    public int getCenterX() {
        return this.minecraft.displayWidth / 2 * this.scaleFactor;
    }

    public int getCenterY() {
        return this.minecraft.displayHeight / 2 * this.scaleFactor;
    }

    public double getScale() {
        double scale = 640.0D / this.scaledHeightD;
        return scale;
    }

    public int getScaledWidth() {
        return this.scaledWidth;
    }

    public int getScaledHeight() {
        return this.scaledHeight;
    }

    public double getScaledWidth_double() {
        return this.scaledWidthD;
    }

    public double getScaledHeight_double() {
        return this.scaledHeightD;
    }

    public int getScaleFactor() {
        return this.scaleFactor;
    }
}

