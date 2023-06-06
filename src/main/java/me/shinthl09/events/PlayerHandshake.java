package me.shinthl09.events;

import me.shinthl09.ToolboxBlocker;
import me.shinthl09.config.ConfigManager;
import me.shinthl09.util.ColorTrans;
import me.shinthl09.util.ToolboxChecker;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.chat.ComponentSerializer;
import net.md_5.bungee.event.EventHandler;
import org.geysermc.floodgate.util.DeviceOs;
import org.geysermc.geyser.GeyserImpl;
import org.geysermc.geyser.session.auth.BedrockClientData;

import java.awt.Color;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.UUID;
import me.shinthl09.util.Embed;

public class PlayerHandshake implements Listener {
    @EventHandler
    public void onPlayerHandshake(PostLoginEvent ple) {
        ProxiedPlayer player = ple.getPlayer();
        UUID playerUUID = player.getPendingConnection().getUniqueId();
        if (GeyserImpl.getInstance().connectionByUuid(playerUUID) != null) {
            String playerName = player.getName();
            BedrockClientData clientData = GeyserImpl.getInstance().connectionByUuid(playerUUID).getClientData();
            DeviceOs deviceOs = clientData.getDeviceOs();
            String modelName = clientData.getDeviceModel();
            boolean hasPlayerToolbox = ToolboxChecker.hasToolbox(deviceOs, clientData);
            MiniMessage miniMessage = MiniMessage.miniMessage();
            Component component = miniMessage.deserialize(ConfigManager.getConfigValue("Messages.toolbox-kick-message", ""));
            String json = GsonComponentSerializer.gson().serializer().toJson(component);
            BaseComponent[] bc = ComponentSerializer.parse(json);
            if (hasPlayerToolbox) {
                player.disconnect(bc);
                sendEmbed(playerName, playerUUID, modelName, deviceOs);
            }
        }
    }

    public static void sendEmbed(String playerName, UUID playerUUID, String modelName, DeviceOs deviceOs) {
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
        String time = LocalDateTime.now(ZoneId.ofOffset("UTC", ZoneOffset.ofHours(0))).format(dateFormat);

        if (ConfigManager.getConfigValue("Log.log-to-txt", false)) {
            String logDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            String resourceFolder = ToolboxBlocker.plugin.getDataFolder().getAbsolutePath();
            File logsPath = new File(resourceFolder + "/logs/");
            try {
                if (!logsPath.exists()) { logsPath.mkdir(); }

                BufferedWriter bw = new BufferedWriter(new FileWriter(logsPath + "/log-" + logDate + ".txt", true));
                bw.write("Player: " + playerName + "; UUID: " + playerUUID + "; DeviceOS: " + deviceOs + "; ModelName: " + modelName + "; Time: " + time);

                bw.close();
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }
        if (ConfigManager.getConfigValue("Log.send-embed", false)) {
            Embed.send(ConfigManager.getConfigValue("Embed.toolbox-kick.title", ""), ConfigManager.getConfigValue("Embed.toolbox-kick.description", "").replace("{player}", playerName).replace("{uuid}", playerUUID.toString()).replace("{modelname}", modelName).replace("{deviceos}", deviceOs.toString()).replace("{time}", time), ColorTrans.HexColorDecode("#00FF00"));
        }
    }
}
