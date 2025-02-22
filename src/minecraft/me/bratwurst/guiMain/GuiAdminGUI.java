package me.bratwurst.guiMain;

import me.bratwurst.manager.PartikelSystem.ParticleSystem;
import me.bratwurst.news.proxy.ProxyMenuScreen;
import me.bratwurst.utils.DrawMenuLogoUtil;
import net.minecraft.client.gui.*;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;

public class GuiAdminGUI extends GuiScreen {
    ParticleSystem partikelsystem = new ParticleSystem(1000,230);
    public static boolean particle = false;
    public static boolean shader = true;

    public void initGui() {


        this.buttonList.add(new GuiButton(110, this.width / 2 - 155, this.height / 6 + 48 - 6, 150, 20, I18n.format("ForceOp-PL")));
        this.buttonList.add(new GuiButton(113, this.width / 2 - 155 + 155, this.height / 6 + 48 - 6, 150, 20, I18n.format("Plugin New")));
        this.buttonList.add(new GuiButton(200, this.width / 2 - 100, this.height / 6 + 168, I18n.format("gui.done", new Object[0])));


    }
    public static int clicked = 1;
    protected void actionPerformed(GuiButton button) throws IOException
    {
        if(button.id == 200){
            mc.displayGuiScreen(new GuiIngameMenu());
        }
        if(button.id == 110){
            mc.displayGuiScreen(new GuiForceOp(this));

        }
        if(button.id == 113){
            mc.displayGuiScreen(new GuiPlugin());

        }


    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        this.drawDefaultBackground();

        //Hintergrund
        this.mc.getTextureManager().bindTexture(new ResourceLocation("client/336293.png"));
        Gui.drawModalRectWithCustomSizedTexture(0, 0, 0, 0, this.width, this.height, this.width, this.height);
        render();
        //backScreen
        drawRect(this.width / 9 - 15, this.height / 6 - 25, this.width - 50, this.height / 2 + this.height / 3, new Color(40, 40, 40, 138).getRGB());

        final String Logo = "AdminMenu";
        DrawMenuLogoUtil.drawString(3, Logo, this.width / 7, this.height / 20, Color.RED.getRGB());

        super.drawScreen(mouseX, mouseY, partialTicks);
    }
    public void render() {
        partikelsystem.render();
        partikelsystem.tick(15);
    }
}





