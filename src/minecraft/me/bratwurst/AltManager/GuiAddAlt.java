package me.bratwurst.AltManager;

import com.mojang.authlib.Agent;
import com.mojang.authlib.exceptions.AuthenticationException;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import com.mojang.authlib.yggdrasil.YggdrasilUserAuthentication;
import me.bratwurst.manager.PartikelSystem.ParticleSystem;
import net.minecraft.client.gui.*;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Keyboard;

import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.io.IOException;
import java.net.Proxy;

public class GuiAddAlt extends GuiScreen {
    private final GuiAltManager manager;
    private PasswordField password;
    private String status;
    private GuiTextField username;
    ParticleSystem partikelsystem = new ParticleSystem(1000,230);
    public GuiAddAlt(final GuiAltManager manager) {
        this.status = EnumChatFormatting.GRAY + "Idle...";
        this.manager = manager;
    }

    @Override
    protected void actionPerformed(final GuiButton button) {
        switch (button.id) {
            case 0: {
                final AddAltThread login = new AddAltThread(this.username.getText(), this.password.getText());
                login.start();
                break;
            }
            case 1: {
                this.mc.displayGuiScreen(this.manager);
                break;
            }
            case 2: {
                String data = null;
                try {
                    data = (String) Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor);
                } catch (Exception ignored) {
                    break;
                }
                if (data.contains(":")) {
                    String[] credentials = data.split(":");
                    username.setText(credentials[0]);
                    password.setText(credentials[1]);
                    final AddAltThread login = new AddAltThread(this.username.getText(), this.password.getText());
                    login.start();
                }
            }
        }
    }

    @Override
    public void drawScreen(final int i, final int j, final float f) {
        this.mc.getTextureManager().bindTexture(new ResourceLocation("client/Backhub.png"));
        Gui.drawModalRectWithCustomSizedTexture(0, 0, 0, 0, this.width, this.height, this.width, this.height);
        render();
        this.username.drawTextBox();
        this.password.drawTextBox();
        this.drawCenteredString(this.fontRendererObj, "Add Alt", this.width / 2, 20, -1);
        if (this.username.getText().isEmpty()) {
            this.drawString(this.mc.fontRendererObj, EnumChatFormatting.GREEN +"Username / E-Mail", this.width / 2 - 96, 66, -7829368);
        }
        if (this.password.getText().isEmpty()) {
            this.drawString(this.mc.fontRendererObj, EnumChatFormatting.RED +"Password", this.width / 2 - 96, 106, -7829368);
        }
        this.drawCenteredString(this.fontRendererObj, this.status, this.width / 2, 30, -1);

        super.drawScreen(i, j, f);

    }

    public static void rectangle(double left, double top, double right, double bottom, int color) {
        Gui.drawRect(left, top, right, bottom, color);
    }

    @Override
    public void initGui() {
        Keyboard.enableRepeatEvents(true);
        this.buttonList.clear();
        this.buttonList.add(new GuiButton(0, this.width / 2 - 100, this.height / 4 + 92 + 12,  EnumChatFormatting.GREEN +"Login"));
        this.buttonList.add(new GuiButton(1, this.width / 2 - 100, this.height / 4 + 116 + 12, EnumChatFormatting.RED +"Back"));
        buttonList.add(new GuiButton(2, this.width / 2 - 100, this.height / 4 + 116 + 36, EnumChatFormatting.GREEN +"Import user:pass"));
        this.username = new GuiTextField(this.eventButton, this.mc.fontRendererObj, this.width / 2 - 100, 60, 200, 20);
        this.password = new PasswordField(this.mc.fontRendererObj, this.width / 2 - 100, 100, 200, 20);
    }

    @Override
    protected void keyTyped(final char par1, final int par2) {
        this.username.textboxKeyTyped(par1, par2);
        this.password.textboxKeyTyped(par1, par2);
        if (par1 == '\t' && (this.username.isFocused() || this.password.isFocused())) {
            this.username.setFocused(!this.username.isFocused());
            this.password.setFocused(!this.password.isFocused());
        }
        if (par1 == '\r') {
            this.actionPerformed((GuiButton) this.buttonList.get(0));
        }
    }

    @Override
    protected void mouseClicked(final int par1, final int par2, final int par3) {
        try {
            super.mouseClicked(par1, par2, par3);
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.username.mouseClicked(par1, par2, par3);
        this.password.mouseClicked(par1, par2, par3);
    }

    static /* synthetic */ void access$0(final GuiAddAlt guiAddAlt, final String status) {
        guiAddAlt.status = status;
    }

    private class AddAltThread extends Thread {
        private final String password;
        private final String username;

        public AddAltThread(final String username, final String password) {
            this.username = username;
            this.password = password;
            GuiAddAlt.access$0(GuiAddAlt.this, EnumChatFormatting.GRAY + "Idle...");
        }

        private final void checkAndAddAlt(final String username, final String password) {
            if (!username.contains("@")) {
                GuiAddAlt.access$0(GuiAddAlt.this, EnumChatFormatting.RED + "Alt failed!");
            }
            final YggdrasilAuthenticationService service = new YggdrasilAuthenticationService(Proxy.NO_PROXY, "");
            final YggdrasilUserAuthentication auth = (YggdrasilUserAuthentication) service.createUserAuthentication(Agent.MINECRAFT);
            auth.setUsername(username);
            auth.setPassword(password);
            try {
                auth.logIn();
                AltManager.registry.add(new Alt(username, password, auth.getSelectedProfile().getName()));
                GuiAddAlt.access$0(GuiAddAlt.this, "Alt added. (" + username + ")");
                GuiAddAlt.access$0(GuiAddAlt.this, EnumChatFormatting.RED + "Alt failed!");
            } catch (AuthenticationException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void run() {
            if (this.password.equals("")) {
                AltManager.registry.add(new Alt(this.username, ""));
                GuiAddAlt.access$0(GuiAddAlt.this, EnumChatFormatting.GREEN + "Alt added. (" + this.username + " - offline name)");
                return;
            }
            GuiAddAlt.access$0(GuiAddAlt.this, EnumChatFormatting.AQUA + "Trying alt...");
            if (username != null && password != null) {
                this.checkAndAddAlt(this.username, this.password);
            }
        }
    }
    public void render() {
        partikelsystem.render();
        partikelsystem.tick(15);
    }
}
