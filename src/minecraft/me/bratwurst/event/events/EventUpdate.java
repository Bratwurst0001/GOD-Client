package me.bratwurst.event.events;

import me.bratwurst.event.Event;

public class EventUpdate extends Event {
    public double x;
    public double y;
    public double z;
    public float yaw;
    public float pitch;
    public boolean onGround;
    public EventState state;
    public boolean alwaysSend;
    private double stepHeight;
    private boolean active;
    private boolean isPre;

    private boolean sneaking;
    public static float YAW, PITCH, PREVYAW, PREVPITCH;
    public static boolean SNEAKING;
    public int key;


    public EventUpdate(double x, double y, double z, float yaw, float pitch, boolean onGround) {
        this.y = y;
        this.z = z;
        this.x = x;
        this.yaw = yaw;
        this.pitch = pitch;
        this.onGround = onGround;
        this.state = EventState.PRE;
    }

    public EventUpdate() {
        this.state = EventState.POST;
    }

    public double getStepHeight() {
        return this.stepHeight;
    }

    public boolean shouldAlwaysSend() {
        return this.alwaysSend;
    }

    public boolean isActive() {
        return this.active;
    }

    public void setStepHeight(double stepHeight) {
        this.stepHeight = stepHeight;
    }

    public void setActive(boolean bypass) {
        this.active = bypass;
    }
    public void setYaw(float yaw) {
        this.yaw = yaw;
    }

    public void setPitch(float pitch) {
        this.pitch = pitch;
    }
    public void setAlwaysSend(boolean alwaysSend) {
        this.alwaysSend = alwaysSend;
    }
    public void setOnGround(boolean onGround) {
        this.onGround = onGround;
    }
    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getZ() {
        return this.z;
    }

    public void setZ(double z) {
        this.z = z;
    }

    @Override
    public boolean isPre() {
        return false;
    }
}


