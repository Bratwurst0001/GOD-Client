/*
 * Decompiled with CFR 0.150.
 */
package me.bratwurst.penner.easing;

public class Circ {
    private static     float f;
    public static float easeIn(float t, float b, float c, float d) {
        return -c * ((float)Math.sqrt(1.0f - (t /= d) * t) - 1.0f) + b;
    }

    public static float easeOut(float t, float b, float c, float d) {
        t = t / d - 1.0f;
        return c * (float)Math.sqrt(1.0f - t * t) + b;
    }

    public static float easeInOut(float t, float b, float c, float d) {

        t /= d / 2.0f;
        if (f < 1.0f) {
            return -c / 2.0f * ((float)Math.sqrt(1.0f - t * t) - 1.0f) + b;
        }
        return c / 2.0f * ((float)Math.sqrt(1.0f - (t -= 2.0f) * t) + 1.0f) + b;
    }
}

