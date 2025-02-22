package me.bratwurst.cosmetics;

import me.bratwurst.cosmetics.profile.CapeProfile;
import me.bratwurst.utils.AnimatedResourceLocation;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.entity.player.EnumPlayerModelParts;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

public class CosmeticCape extends Cosmetic {
    private final RenderPlayer playerRenderer;

    private AnimatedResourceLocation animated;

    private static final String __OBFID = "CL_00002425";

    public CosmeticCape(RenderPlayer player) {
        super(player);
        this.playerRenderer = player;
    }

    public boolean shouldCombineTextures() {
        return false;
    }

    public void render(AbstractClientPlayer player, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float headYaw, float headPitch, float scale) {


        if (player.hasPlayerInfo() && !player.isInvisible()) {
            Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation("client/cosmetics/0.png"));
        }

            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            GlStateManager.pushMatrix();
            GlStateManager.translate(0.0F, 0.0F, 0.125F);
            double d0 = player.prevChasingPosX + (player.chasingPosX - player.prevChasingPosX) * partialTicks - player.prevPosX + (player.posX - player.prevPosX) * partialTicks;
            double d1 = player.prevChasingPosY + (player.chasingPosY - player.prevChasingPosY) * partialTicks - player.prevPosY + (player.posY - player.prevPosY) * partialTicks;
            double d2 = player.prevChasingPosZ + (player.chasingPosZ - player.prevChasingPosZ) * partialTicks - player.prevPosZ + (player.posZ - player.prevPosZ) * partialTicks;
            float f = player.prevRenderYawOffset + (player.renderYawOffset - player.prevRenderYawOffset) * partialTicks;
            double d3 = MathHelper.sin(f * 3.1415927F / 180.0F);
            double d4 = -MathHelper.cos(f * 3.1415927F / 180.0F);
            float f1 = (float) d1 * 10.0F;
            f1 = MathHelper.clamp_float(f1, -6.0F, 32.0F);
            float f2 = (float) (d0 * d3 + d2 * d4) * 100.0F;
            float f3 = (float) (d0 * d4 - d2 * d3) * 100.0F;
            if (f2 < 0.0F)
                f2 = 0.0F;
            if (f2 > 165.0F)
                f2 = 165.0F;
            float f4 = player.prevCameraYaw + (player.cameraYaw - player.prevCameraYaw) * partialTicks;
            f1 += MathHelper.sin((player.prevDistanceWalkedModified + (player.distanceWalkedModified - player.prevDistanceWalkedModified) * partialTicks) * 6.0F) * 32.0F * f4;
            if (player.isSneaking()) {
                f1 += 25.0F;
                GlStateManager.translate(0.0F, 0.142F, -0.0178F);
            }
            GlStateManager.rotate(6.0F + f2 / 2.0F + f1, 1.0F, 0.0F, 0.0F);
            GlStateManager.rotate(f3 / 2.0F, 0.0F, 0.0F, 1.0F);
            GlStateManager.rotate(-f3 / 2.0F, 0.0F, 1.0F, 0.0F);
            GlStateManager.rotate(180.0F, 0.0F, 1.0F, 0.0F);
            this.playerRenderer.getMainModel().renderCape(0.0625F);
            GlStateManager.popMatrix();

    }
}