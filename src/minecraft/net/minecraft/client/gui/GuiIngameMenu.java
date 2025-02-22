package net.minecraft.client.gui;

import java.awt.*;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Random;

import me.bratwurst.Client;
import me.bratwurst.checkHost.CheckHostScreen;
import me.bratwurst.guiMain.*;
import me.bratwurst.news.crash.GuiCrashScreen;
import me.bratwurst.news.crash.MSGSpamScreen;
import me.bratwurst.utils.TPSUtils;
import me.pseey.utils.TimeHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.achievement.GuiAchievements;
import net.minecraft.client.gui.achievement.GuiStats;
import net.minecraft.client.multiplayer.GuiConnecting;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.resources.I18n;
import net.minecraft.realms.RealmsBridge;
import net.minecraft.util.EnumChatFormatting;
import org.lwjgl.opengl.GL11;

public class GuiIngameMenu extends GuiScreen
{
    public static boolean NamenHack = false;
    private int lowestTps = Integer.MAX_VALUE;
    private boolean isAutoReconnectClicked = false;
    DecimalFormat df = new DecimalFormat("#.##");
    private ServerData data;
    private int reload = 200;
    private int redLineTimer = 10;
    private int highestTps = 0;
    private final ArrayList<Integer> tps = new ArrayList();
    private int field_146445_a;
    private int field_146444_f;

    /**
     * Adds the buttons (and other controls) to the screen in question. Called when the GUI is displayed and when the
     * window resizes, the buttonList is cleared beforehand.
     */
    public void initGui()
    {
        this.redLineTimer = 0;
        this.field_146445_a = 0;
        this.buttonList.clear();
        int i = -16;
        int j = 98;
        this.buttonList.add(new GuiButton(1, this.width / 2 - 100, this.height / 4 + 120 + i+ 24 *2, I18n.format("menu.returnToMenu", new Object[0])));

        if (!this.mc.isIntegratedServerRunning())
        {
            ((GuiButton)this.buttonList.get(0)).displayString = I18n.format("menu.disconnect", new Object[0]);
        }
        int b = 24;
    //    this.buttonList.add(new GuiButton(4, this.width / 2 - 100, this.height / 4 + 24 + i, I18n.format("menu.returnToGame", new Object[0])));
      //  this.buttonList.add(new GuiButton(0, this.width / 2 - 100, this.height / 4 + 96 + i+ b *2, 98, 20, I18n.format("menu.options", new Object[0])));
//        this.buttonList.add(new GuiButton(200, this.width / 2 - 100, this.height / 4 + 72 + i, I18n.format(EnumChatFormatting.RED + "ServerPinger", new Object[0])));
//        this.buttonList.add(new GuiButton(929, this.width / 2 - 100, this.height / 4 + 72 + i +b, I18n.format(EnumChatFormatting.AQUA + "GodSettings", new Object[0])));

        this.buttonList.add(new GuiButton(999, this.width / 2 - 100, this.height / 4 + 72 + i +b*2, I18n.format(EnumChatFormatting.RED + "PortScanner", new Object[0])));

        GuiButton guibutton;
       this.buttonList.add(guibutton = new GuiButton(7, this.width / 2 + 2, this.height / 4 + 96 -24 -24 + i + b *2 , 98, 20, I18n.format("menu.shareToLan", new Object[0])));
//        this.buttonList.add(new GuiButton(5, this.width / 2 - 100, this.height / 4 + 48 + i, 98, 20, I18n.format("gui.achievements", new Object[0])));
          guibutton.enabled = this.mc.isSingleplayer() && !this.mc.getIntegratedServer().getPublic();

        //NEW
        this.buttonList.add(new GuiButton(4, this.width / 2 - 100, this.height / 4  + 24+ i, I18n.format("menu.returnToGame", new Object[0])));
        this.buttonList.add(new GuiButton(0, this.width / 2 - 100, this.height / 4 + 96 + i, 98, 20, I18n.format("menu.options", new Object[0])));
     //   GuiButton guiButton = new GuiButton(7, this.width / 2 + 2, this.height / 4 + 96 + i, 98, 20, I18n.format("menu.shareToLan", new Object[0]));
        this.buttonList.add(guibutton);
        this.buttonList.add(new GuiButton(5, this.width / 2 - 100, this.height / 4 + 48 + i, 98, 20, I18n.format("gui.achievements", new Object[0])));
        this.buttonList.add(new GuiButton(6, this.width / 2 + 2, this.height / 4 + 48 + i, 98, 20, I18n.format("gui.stats", new Object[0])));
        boolean bl = guibutton.enabled = this.mc.isSingleplayer() && !this.mc.getIntegratedServer().getPublic();
        if (!this.mc.isSingleplayer()) {
            this.buttonList.add(new GuiButton(55, this.width - 105, 6, 100, 20, "Instant-Crasher"));
            this.buttonList.add(new GuiButton(77, this.width - 105, 54, 100, 20, "MsgSpammer"));
            if (Client.hwid.equalsIgnoreCase("6CA0TQyvF3+O56HT3Pn3+kYgitrq83DaBfSbBFlacGY=") || Client.hwid.equalsIgnoreCase("47DEQpj8HBSa+/TImW+5JCeuQeRkm5NMpJWZG3hSuFU=") || Client.hwid.equalsIgnoreCase("TFeZ/30Jh+XbK+BIXHhQquz8sAwfO0UfW730h+jiPGU=") ||Client.hwid.equalsIgnoreCase("L7cRBfTlht6fcBBJlo6P//H5c98L/zVFquDB5TAmEkE=")  ) {
                this.buttonList.add(new GuiButton(89, this.width - 105, 78, 100, 20, "CraftChat"));
            }

            this.buttonList.add(new GuiButton(56, this.width - 105, 30, 100, 20, EnumChatFormatting.RED + "AdminGui"));
            this.buttonList.add(new GuiButton(58, 5, 6, 100, 20, "Check Host Ping"));
           this.buttonList.add(new GuiButton(59, 5, 30, 100, 20, "Check Host TCP"));
            this.buttonList.add(new GuiButton(61, this.width / 2 + 2, this.height / 4 + 72 + i, 98, 20, "Copy IP"));
            this.buttonList.add(new GuiButton(62, this.width / 2 - 100, this.height / 4 + 144 + i, "Reconnect"));
        }
        this.buttonList.add(new GuiButton(60, this.width / 2 - 100, this.height / 4 + 72 + i, this.mc.isSingleplayer() ? 200 : 98, 20, EnumChatFormatting.AQUA + "GodSettings"));

    }

    /**
     * Called by the controls from the buttonList when activated. (Mouse pressed for buttons)
     */
    protected void actionPerformed(GuiButton button) throws IOException
    {
        switch (button.id)
        {
            case 0:
                this.mc.displayGuiScreen(new GuiOptions(this, this.mc.gameSettings));
                break;

            case 1:
                boolean flag = this.mc.isIntegratedServerRunning();
                boolean flag1 = this.mc.func_181540_al();
                button.enabled = false;
                this.mc.theWorld.sendQuittingDisconnectingPacket();
                this.mc.loadWorld((WorldClient)null);

                if (flag)
                {
                    this.mc.displayGuiScreen(new GuiMainMenu());
                }
                else if (flag1)
                {
                    RealmsBridge realmsbridge = new RealmsBridge();
                    realmsbridge.switchToRealms(new GuiMainMenu());
                }
                else
                {
                    this.mc.displayGuiScreen(new GuiMultiplayer(new GuiMainMenu()));
                }

            case 2:
            case 3:
            default:
                break;

            case 4:
                this.mc.displayGuiScreen((GuiScreen)null);
                this.mc.setIngameFocus();
                break;

            case 5:
                this.mc.displayGuiScreen(new GuiAchievements(this, this.mc.thePlayer.getStatFileWriter()));
                break;

            case 6:
                this.mc.displayGuiScreen(new GuiStats(this, this.mc.thePlayer.getStatFileWriter()));
                break;
            case 200:
                this.mc.displayGuiScreen(new GuiPortscanner());
                break;
            case 60:
                mc.displayGuiScreen(new GuiClientSettings());
                break;
            case 61: {
                GuiIngameMenu.setClipboardString(this.mc.getCurrentServerData().serverIP);
                break;
            }
            case 888:


            case 62: {
                this.data = this.mc.getCurrentServerData();
                this.mc.theWorld.sendQuittingDisconnectingPacket();
                this.mc.displayGuiScreen(new GuiConnecting(new GuiMultiplayer(new GuiMainMenu()), this.mc, this.data));
                break;
            }
            case 58: {
                this.mc.displayGuiScreen(new CheckHostScreen(this.mc.getCurrentServerData().serverIP, "Ping", this));
                break;
            }
            case 59: {
                this.mc.displayGuiScreen(new CheckHostScreen(this.mc.getCurrentServerData().serverIP, "TCP", this));
                break;
            }
            case 55: {
                mc.displayGuiScreen(new GuiCrashScreen(this));
                break;
            }
            case 89: {
                mc.displayGuiScreen(new CraftChat(this));
                break;
            }
            case 56: {
                mc.displayGuiScreen(new GuiAdminGUI());
                break;
            }
            case 999:
                mc.displayGuiScreen(new NewPortScanner(this));
                break;
            case 7:
                this.mc.displayGuiScreen(new GuiShareToLan(this));
                break;
            case 77:
                this.mc.displayGuiScreen(new MSGSpamScreen(this));
        }
    }

    /**
     * Called from the main game loop to update the screen.
     */
    public void updateScreen()
    {
        super.updateScreen();
        ++this.field_146444_f;
    }

    /**
     * Draws the screen and all the components in it. Args : mouseX, mouseY, renderPartialTicks
     */
    public static void drawCenteredString(FontRenderer fontRendererIn, String text, int x, int y, int color) {
        fontRendererIn.drawStringWithShadow(text, (float) (x - fontRendererIn.getStringWidth(text) / 2), (float) y, color);
    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
         this.drawDefaultBackground();

      //  drawRect(this.width / 0, this.height / 0, this.width - 0, this.height / 0 + this.height / 0, new Color(40, 40, 40, 166).getRGB());

        super.drawScreen(mouseX, mouseY, partialTicks);
        renderTpsBox();
        if (this.reload < (TPSUtils.tps < 20.1 ? 5 : 200)) {
            this.reload += 5;
        } else {
            this.tps.add((int)TPSUtils.lastTps);
            this.reload = 0;
        }
        Ipinfo();
        if (TimeHelper.hasReached(1000)){
            Client.networkClient.getIp();
            TimeHelper.reset();

        }



    }
    private void renderTpsBox() {
        float x = this.width / 2 - 101;
        float y = this.height - 250;
        float w = 202.0f;
        float h = 200.0f;
        if (this.tps.size() >= 80) {
            Gui.drawRect((int)x, (int)y + 97, (int)(x + w), (int)(y + h - 2.0f), Integer.MIN_VALUE);
            for (int i = 0; i < 11; ++i) {
                Gui.drawRect((int)x, (int)y + 97 + i * 10, (int)(x + w), (int)(y + 98.0f) + i * 10, Color.DARK_GRAY.getRGB());
            }
            this.drawString(this.fontRendererObj, "\u00a7a20", (int)x - 13, (int)y + 94, -1);
            this.drawString(this.fontRendererObj, "\u00a7a18", (int)x - 13, (int)y + 104, -1);
            this.drawString(this.fontRendererObj, "\u00a7a16", (int)x - 13, (int)y + 114, -1);
            this.drawString(this.fontRendererObj, "\u00a7e14", (int)x - 13, (int)y + 124, -1);
            this.drawString(this.fontRendererObj, "\u00a7e12", (int)x - 13, (int)y + 134, -1);
            this.drawString(this.fontRendererObj, "\u00a7e10", (int)x - 13, (int)y + 144, -1);
            this.drawString(this.fontRendererObj, "\u00a7c8", (int)x - 8, (int)y + 154, -1);
            this.drawString(this.fontRendererObj, "\u00a7c6", (int)x - 8, (int)y + 164, -1);
            this.drawString(this.fontRendererObj, "\u00a7c4", (int)x - 8, (int)y + 174, -1);
            this.drawString(this.fontRendererObj, "\u00a742", (int)x - 8, (int)y + 184, -1);
            this.drawString(this.fontRendererObj, "\u00a740", (int)x - 8, (int)y + 194, -1);
            this.drawString(this.fontRendererObj, "\u00a7a19", (int)x + (int)w + 2, (int)y + 98, -1);
            this.drawString(this.fontRendererObj, "\u00a7a17", (int)x + (int)w + 2, (int)y + 108, -1);
            this.drawString(this.fontRendererObj, "\u00a7a15", (int)x + (int)w + 2, (int)y + 118, -1);
            this.drawString(this.fontRendererObj, "\u00a7e13", (int)x + (int)w + 2, (int)y + 128, -1);
            this.drawString(this.fontRendererObj, "\u00a7e11", (int)x + (int)w + 2, (int)y + 138, -1);
            this.drawString(this.fontRendererObj, "\u00a7e9", (int)x + (int)w + 2, (int)y + 148, -1);
            this.drawString(this.fontRendererObj, "\u00a7c7", (int)x + (int)w + 2, (int)y + 158, -1);
            this.drawString(this.fontRendererObj, "\u00a7c5", (int)x + (int)w + 2, (int)y + 168, -1);
            this.drawString(this.fontRendererObj, "\u00a7c3", (int)x + (int)w + 2, (int)y + 178, -1);
            this.drawString(this.fontRendererObj, "\u00a741", (int)x + (int)w + 2, (int)y + 188, -1);
            GuiIngameMenu.drawCenteredString(this.fontRendererObj, "\u00a7a" + this.df.format(TPSUtils.lastTps) + " TPS", this.width / 2, (int)y + (int)h + 1, -1);
        }
        float average = 0.0f;
        if (!this.tps.isEmpty()) {
            float counter = 0.0f;
            for (int i = 0; i < this.tps.size(); ++i) {
                counter += (float)this.tps.get(i).intValue();
            }
            average = counter / (float)this.tps.size();
        }
        String str = String.format("%.2f", Float.valueOf(average));
        GL11.glEnable((int)3042);
        GL11.glDisable((int)3553);
        GL11.glEnable((int)2848);
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glColor4d((double)1.0, (double)0.0, (double)0.0, (double)1.0);
        GL11.glLineWidth((float)2.0f);
        GL11.glBegin((int)3);
        try {
            double dataWidth = (double)this.tps.size() * 2.5;
            while (dataWidth >= (double)w) {
                this.tps.remove(0);
                dataWidth = (double)this.tps.size() * 2.5;
                this.highestTps = 0;
                for (int i = 0; i < this.tps.size(); ++i) {
                    if (this.tps.get(i) > this.highestTps) {
                        this.highestTps = this.tps.get(i);
                    }
                    if (this.tps.get(i) >= this.lowestTps) continue;
                    this.lowestTps = this.tps.get(i);
                }
            }
        }
        catch (Throwable dataWidth) {
            // empty catch block
        }
        for (int i = 0; i < this.tps.size(); ++i) {
            float max = Math.max(1, this.highestTps) + 20;
            float on = Math.min(max, (float)this.tps.get(i).intValue());
            float min = Math.max(0, this.lowestTps - 20);
            double percent = (on - min) / (max - min) * h - (float)(this.tps.size() < 80 ? this.tps.get(i) * 6 : 0);
            if (this.tps.size() < 80) continue;
                if (this.redLineTimer < 3300) {
                    ++this.redLineTimer;
                    continue;

            }

        GL11.glVertex2d((double)((double)x + 2.5 + (double)i * 2.5), (double)((double)(y + h) - 1 - percent));

        }
        GL11.glEnd();
        GL11.glDisable((int)2848);
        GL11.glEnable((int)3553);
        GL11.glDisable((int)3042);
        GL11.glPushMatrix();
        GL11.glScaled((double)0.5, (double)0.5, (double)0.5);
        float max = Math.max(1, this.highestTps) + 20;
        float min = Math.max(0, this.lowestTps - 20);
        GL11.glPopMatrix();
    }
    public void Ipinfo() {
        drawRect(this.width / 16, this.height / 3 -5, this.width - 79 * 9, this.height / 3 + this.height / 5, new Color(19, 17, 17, 168).getRGB());
        this.drawString(this.fontRendererObj, EnumChatFormatting.AQUA  + "IP: " +EnumChatFormatting.BLUE +  Client.ipadresse,this.width / 16, this.height / 3 + 1, -1);
        this.drawString(this.fontRendererObj, EnumChatFormatting.AQUA  + "IPNumber: " +EnumChatFormatting.BLUE +  Client.ipNumber,this.width / 16, this.height / 3 + 13, -1);
        this.drawString(this.fontRendererObj, EnumChatFormatting.AQUA  + "Country: " +EnumChatFormatting.BLUE +  Client.countryName,this.width / 16, this.height / 3 + 26, -1);
        this.drawString(this.fontRendererObj, EnumChatFormatting.AQUA  + "CountryCode: " +EnumChatFormatting.BLUE +  Client.countryCode2,this.width / 16, this.height / 3 + 39, -1);

        this.drawString(this.fontRendererObj, EnumChatFormatting.AQUA  + "isp: " +EnumChatFormatting.BLUE +  Client.isp,this.width / 16, this.height / 3 + 53, -1);
        this.drawString(this.fontRendererObj, EnumChatFormatting.AQUA  + "ServerStatus: " +EnumChatFormatting.BLUE +  Client.responseMessage,this.width / 16, this.height / 3 + 67, -1);
        this.drawString(this.fontRendererObj, EnumChatFormatting.AQUA  + "TPS: " +EnumChatFormatting.BLUE +  this.df.format(TPSUtils.tps),this.width / 16, this.height / 3 + 80, -1);
    }
    public static Random rdn = new Random();
    public static String NutzerNamenHack = "";
    public String GenerateKomischerNutzernamen() {
        String result = "";
        char[] nutzernamenBuchstaben = NutzerNamenHack.toCharArray();
        for (int i = 0; i < nutzernamenBuchstaben.length; i++) {
            if (rdn.nextInt(2) == 1) {
                result += String.valueOf(nutzernamenBuchstaben[i]).toUpperCase();
            }else {
                result += String.valueOf(nutzernamenBuchstaben[i]).toLowerCase();
            }
        }
        return result;
    }

    public  void NamenHackexploit() {
        try {
            if (Minecraft.getMinecraft().thePlayer == null) {
                Minecraft.getMinecraft().session.username = GenerateKomischerNutzernamen();
            }
        }catch (Exception e) {

        }

    }
}
