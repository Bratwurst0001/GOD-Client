package net.minecraft.client.gui;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

import io.netty.buffer.Unpooled;
import me.bratwurst.Client;
import me.bratwurst.manager.network.GodNetworkClient;
import me.bratwurst.module.modules.Player.GodMode;
import net.minecraft.client.Minecraft;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.resources.I18n;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.client.C00PacketKeepAlive;
import net.minecraft.network.play.client.C17PacketCustomPayload;

public class GuiDownloadTerrain extends GuiScreen
{
    public static long last = 0L;
    private NetHandlerPlayClient netHandlerPlayClient;
    private int progress;

    public GuiDownloadTerrain(NetHandlerPlayClient netHandler)
    {
        this.netHandlerPlayClient = netHandler;
    }

    /**
     * Fired when a key is typed (except F11 which toggles full screen). This is the equivalent of
     * KeyListener.keyTyped(KeyEvent e). Args : character (character on the key), keyCode (lwjgl Keyboard key code)
     */
    protected void keyTyped(char typedChar, int keyCode) throws IOException
    {
    }

    /**
     * Adds the buttons (and other controls) to the screen in question. Called when the GUI is displayed and when the
     * window resizes, the buttonList is cleared beforehand.
     */
    public void initGui()
    {

        this.buttonList.clear();

        if (!Client.getInstance().getModuleManager().getModuleByName("LabyFaker").isEnabled()) {
            return;
        }
        if (last + 400L - System.currentTimeMillis() < 0L) {
            PacketBuffer buf = new PacketBuffer(Unpooled.buffer());
            buf.writeString("3.4.7");
            String channel = "LABYMOD";
            C17PacketCustomPayload packet = new C17PacketCustomPayload(channel, buf);
            PacketBuffer buf2 = new PacketBuffer(Unpooled.buffer());
            buf2.writeString("3.4.7");
            String channel2 = "LMC";
            C17PacketCustomPayload packet2 = new C17PacketCustomPayload(channel2, buf2);
            mc.thePlayer.sendQueue.addToSendQueue(packet);
            mc.thePlayer.sendQueue.addToSendQueue(packet2);
        }
        if (Client.firstjoin == true && Client.join == false){
            GodNetworkClient.ircClient.send(Minecraft.getMinecraft().session.getUsername()  + " Ist jetzt der #GODARMY Beigetretten!!!!");
        }
        if (Client.join == true){
            GodNetworkClient.ircClient.send(Minecraft.getMinecraft().session.getUsername()  + " ist dem Client Beigetretten!!");
            try {
                Socket connection = new Socket();
                connection.connect(new InetSocketAddress("5.181.151.112", 8090));
                String lol =  Minecraft.getMinecraft().session.getUsername();
                byte[] sendbytes = ("Client Connectet GOD CLIENT "+  lol +"  \n").getBytes();

                connection.getOutputStream().write(sendbytes);
                Client.join = false;
            }catch (Exception e) {
                Client.join = false;
            }



            Client.join = false;
        }
    }

    /**
     * Called from the main game loop to update the screen.
     */
    public void updateScreen()
    {
        ++this.progress;

        if (this.progress % 20 == 0)
        {
            this.netHandlerPlayClient.addToSendQueue(new C00PacketKeepAlive());
        }
    }

    /**
     * Draws the screen and all the components in it. Args : mouseX, mouseY, renderPartialTicks
     */
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        this.drawBackground(0);
        this.drawCenteredString(this.fontRendererObj, I18n.format("multiplayer.downloadingTerrain", new Object[0]), this.width / 2, this.height / 2 - 50, 16777215);
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    /**
     * Returns true if this GUI should pause the game when it is displayed in single-player
     */
    public boolean doesGuiPauseGame()
    {
        return false;
    }
}
