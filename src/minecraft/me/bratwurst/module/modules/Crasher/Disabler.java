package me.bratwurst.module.modules.Crasher;

import de.Hero.settings.Setting;
import de.gerrygames.viarewind.utils.PacketUtil;
import me.bratwurst.Client;
import me.bratwurst.event.EventTarget;
import me.bratwurst.event.events.EventUpdate;
import me.bratwurst.event.events.ProcessPacketEvent;
import me.bratwurst.manager.ModuleManager;
import me.bratwurst.module.Category;
import me.bratwurst.module.Module;
import me.bratwurst.utils.MainUtil;
import me.bratwurst.utils.PacketTimer;
import me.bratwurst.utils.TimeHelper;
import me.bratwurst.utils.player.PlayerUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerCapabilities;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.*;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import net.minecraft.util.EnumChatFormatting;
import org.apache.commons.lang3.RandomUtils;

import java.util.ArrayList;

public class Disabler extends Module {
    public static Setting mode1;
    protected double x;
    protected double y;
    protected double z;
    protected float yaw;
    protected float pitch;
    protected boolean onGround;
    protected boolean moving;
    protected boolean rotating;
    ArrayList<Packet> Packets = new ArrayList<>();
    ArrayList<Packet> PacketsZone = new ArrayList<>();
    ArrayList<Packet> transactions = new ArrayList<Packet>();
    int currentTransaction = 0;
    public Disabler() {
        super("Disabler", Category.EXPLOIT);
        ArrayList<String> options = new ArrayList<>();
        options.add("Introuble");
        options.add("Ghostlie.live");
        options.add("Hypixel");
        options.add("KAURI");
        options.add("Replaysucht");
        options.add("Redesky");
        options.add("OmegaCraft");
        options.add("zonecraft");
        options.add("zonecraftnew");
        options.add("MEME");
        options.add("Verus");

        Client.instance.setmgr.rSetting(new Setting(EnumChatFormatting.RED +"Disable Anticheat", this, "Slide", options));
    }

    @EventTarget
    public void onUpdate(ProcessPacketEvent e) {
        if (mode1.getValString().equalsIgnoreCase("Ghostlie.live")) {
            ghostlie(e);
            this.setDisplayname(EnumChatFormatting.RED + " - Ghostlie");

        } else if (mode1.getValString().equalsIgnoreCase("Hypixel")) {
            Watchdog();
            this.setDisplayname(EnumChatFormatting.RED + " - Hypixel");
        } else if (mode1.getValString().equalsIgnoreCase("Replaysucht")) {
         Replaysucht();
            this.setDisplayname(EnumChatFormatting.RED + " - Replaysucht");
        }else if (mode1.getValString().equalsIgnoreCase("OmegaCraft")) {
            OmegaCraft(e);
            this.setDisplayname(EnumChatFormatting.RED + " - OmegaCraft");
        }else if (mode1.getValString().equalsIgnoreCase("zonecraft")) {

            this.setDisplayname(EnumChatFormatting.RED + " - zonecraft");
        }else if (mode1.getValString().equalsIgnoreCase("MEME")) {
          Meme(e);
            this.setDisplayname(EnumChatFormatting.RED + " - Meme");
        }else if (mode1.getValString().equalsIgnoreCase("Verus")) {
            Verus(e);
            this.setDisplayname(EnumChatFormatting.RED + " - Verus");
        }else if (mode1.getValString().equalsIgnoreCase("zonecraftnew")) {
            zone(e);
            this.setDisplayname(EnumChatFormatting.RED + " - zonecraftnew");
        }
    }

public void Verus(ProcessPacketEvent e) {
       if (mode1.getValString().equalsIgnoreCase("Verus")) {
        if (e.getPacket () instanceof C0FPacketConfirmTransaction) {
            Packets.add ( e.getPacket () );
            e.setCancelled ( true );
        } else if (e.getPacket () instanceof C00PacketKeepAlive) {
            Packets.add ( e.getPacket () );
            e.setCancelled ( true );
        }
    }
}

public void zone(ProcessPacketEvent e) {
    System.out.println("gggggg");
    if(e.getPacket() instanceof S08PacketPlayerPosLook) {
        S08PacketPlayerPosLook pppl = (S08PacketPlayerPosLook) e.getPacket();
        e.setCancelled(true);
        mc.getNetHandler().addToSendQueueSilent(new C03PacketPlayer.C06PacketPlayerPosLook(pppl.getX(), pppl.getY() + 0.05, pppl.getZ(), mc.thePlayer.rotationYaw, mc.thePlayer.rotationPitch, true));
    }
}
public void Meme(ProcessPacketEvent e) {
    if(e.getPacket() instanceof C03PacketPlayer) {
        e.setCancelled(true);
    }
}
public static boolean flymove = false;
 @EventTarget
public void zonecraft(ProcessPacketEvent event) {
     if(event.getPacket() instanceof C0FPacketConfirmTransaction) {
         event.setCancelled(true);
     }
     if(event.getPacket() instanceof C00PacketKeepAlive) {
         event.setCancelled(true);
     }


}
public void Replaysucht() {
       if (!mc.thePlayer.onGround) {
           if (TimeHelper.hasReached(1)) {
               mc.clickMouse();
               mc.thePlayer.swingItem();
               TimeHelper.reset();
           }
       }
}
public void OmegaCraft(ProcessPacketEvent event) {
    if (event.getPacket() instanceof C0FPacketConfirmTransaction)
        event.setCancelled(true);
}
    public void ghostlie(ProcessPacketEvent event) {
        if (event.getPacket() instanceof C03PacketPlayer)
            Minecraft.getMinecraft().thePlayer.sendQueue.addToSendQueue(new C0CPacketInput(999, 999, true, true));
            MainUtil.sendPacketSilent(new C0CPacketInput());
        if (event.getPacket() instanceof C0FPacketConfirmTransaction)
            event.setCancelled(true);
    }





    public void Watchdog() {
        if (Client.moduleManager.getModuleByName("Glide").isToggle()) {
            PlayerCapabilities playerCapabilities = new PlayerCapabilities();
            playerCapabilities.isFlying = true;
            playerCapabilities.allowFlying = true;
            playerCapabilities.setFlySpeed((float) 2.0D);
            mc.getNetHandler().addToSendQueueSilent(new C13PacketPlayerAbilities(playerCapabilities));
        }

    }


    public void ProcessPacketEvent(ProcessPacketEvent e) {

        for (Packet p : Packets) {
            if (p instanceof C0FPacketConfirmTransaction) {
                e.setCancelled(true);
            }

        }
        Packets.clear();
    }
    public void Redesky(ProcessPacketEvent e) {

        for (Packet p : Packets) {
            if (p instanceof S08PacketPlayerPosLook) {
                e.setCancelled(true);
            }

        }
        Packets.clear();
    }

    @Override
    public void onDisable() {
        super.onDisable();
        flymove = false;
    }

    @Override
    public void onEnable() {
        super.onEnable();

    }
}
