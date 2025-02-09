package me.bratwurst.event.events;

import me.bratwurst.event.Event;
import net.minecraft.client.Minecraft;

public class EventMove extends Event {
    final float strafe;
    public double x, y, z;
    final float forward;

    final float friction;

    boolean cancelled;

    public EventMove(float strafe, float forward, float friction) {
        this.strafe = strafe;
        this.forward = forward;
        this.friction = friction;
    }

    public float getStrafe() {
        return this.strafe;
    }

    public float getForward() {
        return this.forward;
    }

    public float getFriction() {
        return this.friction;
    }

    @Override
    public boolean isPre() {
        return false;
    }

    public boolean isCancelled() {
        return this.cancelled;
    }

    public void setCancelled(boolean state) {
        this.cancelled = state;
    }

    public void setSpeed(double moveSpeed, float pseudoYaw, double pseudoStrafe, double pseudoForward) {
        double forward = pseudoForward;
        double strafe = pseudoStrafe;
        float yaw = pseudoYaw;

        if (forward != 0.0) {
            if (strafe > 0.0) {
                yaw += ((forward > 0.0) ? -45 : 45);
            } else if (strafe < 0.0) {
                yaw += ((forward > 0.0) ? 45 : -45);
            }
            strafe = 0.0F;
            if (forward > 0.0) {
                forward = 1F;
            } else if (forward < 0.0) {
                forward = -1F;
            }
        }

        if (strafe > 0.0) {
            strafe = 1F;
        } else if (strafe < 0.0) {
            strafe = -1F;
        }
        double mx = Math.cos(Math.toRadians((yaw + 90.0F)));
        double mz = Math.sin(Math.toRadians((yaw + 90.0F)));
        x = (forward * moveSpeed * mx + strafe * moveSpeed * mz);
        z = (forward * moveSpeed * mz - strafe * moveSpeed * mx);
    }






    public void setSpeed(double moveSpeed) {
        float forward = Minecraft.getMinecraft().thePlayer.movementInput.moveForward;
        float strafe = Minecraft.getMinecraft().thePlayer.movementInput.moveStrafe;
        float yaw = Minecraft.getMinecraft().thePlayer.rotationYaw;
        if (forward == 0.0 && strafe == 0.0) {
            Minecraft.getMinecraft().thePlayer.motionX = 0.0;
            Minecraft.getMinecraft().thePlayer.motionZ = 0.0;
        } else {
            if (forward != 0.0) {
                if (strafe > 0.0f) {
                    yaw += ((forward > 0.0) ? -45 : 45);
                } else if (strafe < 0.0f) {
                    yaw += ((forward > 0.0) ? 45 : -45);
                }
                strafe = 0.0f;
                if (forward > 0.0f) {
                    forward = 1.0f;
                } else if (forward < 0.0f) {
                    forward = -1.0f;
                }
            }
            double xDist = forward * moveSpeed * Math.cos(Math.toRadians(yaw + 90.0f)) + strafe * moveSpeed * Math.sin(Math.toRadians(yaw + 90.0f));
            double zDist = forward * moveSpeed * Math.sin(Math.toRadians(yaw + 90.0f)) - strafe * moveSpeed * Math.cos(Math.toRadians(yaw + 90.0f));

        }
    }


}
