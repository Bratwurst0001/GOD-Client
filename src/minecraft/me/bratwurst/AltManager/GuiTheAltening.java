package me.bratwurst.AltManager;

import me.bratwurst.Client;
import me.bratwurst.manager.PartikelSystem.ParticleSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Keyboard;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class GuiTheAltening extends GuiScreen {
    private PasswordField api;
    private GuiScreen previousScreen;
    private AltLoginThread thread;
    private GuiTextField token;
    ParticleSystem partikelsystem = new ParticleSystem(1000,230);
    protected Minecraft mc = Minecraft.getMinecraft();
    public GuiTheAltening(final GuiScreen previousScreen) {
        this.previousScreen = previousScreen;
    }

    @Override
    protected void actionPerformed(final GuiButton button) throws IOException {
        switch (button.id) {
            case 1: {
                mc.displayGuiScreen(previousScreen);
                break;
            }
            case 0: {
                if (this.token.getText().isEmpty()) {
                    this.thread = new AltLoginThread(this.token.getText(), "Mantics", false);
                }
                else if (!this.token.getText().isEmpty() && this.token.getText().contains(":")) {
                    final String u = this.token.getText().split(":")[0];
                    final String p = this.token.getText().split(":")[1];
                    this.thread = new AltLoginThread(u.replaceAll(" ", ""), p.replaceAll(" ", ""), false);
                }
                else {
                    this.thread = new AltLoginThread(this.token.getText(), "Explicit", false);
                }
                this.thread.start();
                break;
            }
            case 2: {
                Client.instance.APIKey = api.getText();
                Thread t = new Thread(new Runnable() {
                    @Override
                    public void run() {
                    }
                });
                t.start();
                theAltening(false, this);
                break;
            }
        }
    }
    
    public static void rectangle(double left, double top, double right, double bottom, int color) {
        Gui.drawRect(left, top, right, bottom, color);
    }

    public void theAltening(boolean usingReconnect, GuiScreen oldOne) throws IOException
    {
        String token = getToken();
        this.thread = new AltLoginThread(token, "Emilio", false);
        this.thread.start();
    }
    public String getToken() throws IOException
    {
        String text;
        if (Client.instance.APIKey != null && Client.instance.APIKey != "" && (api == null || api.getText() == null || api.getText() == "")) {
            text = Client.instance.APIKey;
        } else {
            text = api.getText();
        }
        int startChar = 10;
        int endChar = 29;
        BufferedReader br = null;
        String token = "";
        String _line = "";
        URL url = new URL("http://api.thealtening.com/v1/generate?token=");
        try {
            System.out.println(text);
            if(text != null)
            url = new URL("http://api.thealtening.com/v1/generate?token=" + text);
            if(url != null) {
            br = new BufferedReader(new InputStreamReader(url.openStream()));
            }
           
            String line = br.readLine();
            _line = line;

            for (int k = 0; k < (endChar - startChar); k++)
            {
                token = token + line.charAt(startChar + k);
            }




        } finally {

            if (br != null) {
                br.close();
            }
        }
        System.out.println(_line);
        System.out.println(token);
        return token;
    }
    @Override
    public void drawScreen(final int x, final int y, final float z) {
        this.mc.getTextureManager().bindTexture(new ResourceLocation("client/Backhub.png"));
        Gui.drawModalRectWithCustomSizedTexture(0, 0, 0, 0, this.width, this.height, this.width, this.height);
        render();

        this.api.drawTextBox();
        this.token.drawTextBox();
        mc.fontRendererObj.drawString(EnumChatFormatting.AQUA +"Alt Login", this.width / 2, 20, -1);
        mc.fontRendererObj.drawString((this.thread == null) ? EnumChatFormatting.BLUE +"Waiting..." : this.thread.getStatus(), this.width / 2, 29, -1);
        if (this.api.getText().isEmpty()) {
            mc.fontRendererObj.drawStringWithShadow(EnumChatFormatting.AQUA + "API", (float)(this.width / 2 - 94), 106.0f, -7829368);
        }
        if (this.token.getText().isEmpty()) {
            mc.fontRendererObj.drawStringWithShadow(EnumChatFormatting.AQUA + "Token", (float)(this.width / 2 - 94), 156.0f, -7829368);
        }
        super.drawScreen(x, y, z);
    }

    @Override
    public void initGui() {
        final int var3 = this.height / 4 + 24;
        this.buttonList.add(new GuiButton(2, this.width / 2 - 100, 125, EnumChatFormatting.GREEN +"Generate"));
        this.buttonList.add(new GuiButton(0, this.width / 2 - 100, 175, EnumChatFormatting.AQUA +"Login"));
        this.buttonList.add(new GuiButton(1, this.width / 2 - 100, 198, EnumChatFormatting.RED +"Back"));
        this.api = new PasswordField(mc.fontRendererObj, this.width / 2 - 98, 100, 195, 20);
        this.token = new GuiTextField(var3, mc.fontRendererObj, this.width / 2 - 98, 150, 195, 20);
        this.api.setFocused(true);
        Keyboard.enableRepeatEvents(true);
        if (!Client.APIKey.equals("")) {
            api.setText(Client.APIKey);
        }
    }

    @Override
    protected void keyTyped(final char character, final int key) throws IOException {
        try {
            super.keyTyped(character, key);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        if (character == '\t' && (this.api.isFocused() || this.token.isFocused())) {
            this.api.setFocused(!this.api.isFocused());
            this.token.setFocused(!this.token.isFocused());
        }
        if (character == '\r') {
            this.actionPerformed(this.buttonList.get(0));
        }
        this.api.textboxKeyTyped(character, key);
        this.token.textboxKeyTyped(character, key);
    }

    @Override
    protected void mouseClicked(final int x, final int y, final int button) {
        try {
            super.mouseClicked(x, y, button);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        this.api.mouseClicked(x, y, button);
        this.api.mouseClicked(x, y, button);
        this.token.mouseClicked(x, y, button);
    }

    @Override
    public void onGuiClosed() {
        Keyboard.enableRepeatEvents(false);
    }

    @Override
    public void updateScreen() {
        this.api.updateCursorCounter();
        this.api.updateCursorCounter();
        this.token.updateCursorCounter();
    }
    public void render() {
        partikelsystem.render();
        partikelsystem.tick(15);
    }
}
