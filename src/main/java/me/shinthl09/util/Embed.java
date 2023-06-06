package me.shinthl09.util;

import me.shinthl09.config.ConfigManager;

import java.awt.Color;

public class Embed {
    public static void send(String title, String description, Color color) {
        DiscordWebhook webhook = new DiscordWebhook(ConfigManager.getConfigValue("Webhook-URL", ""));
        webhook.addEmbed(new DiscordWebhook.EmbedObject().setTitle(title).setDescription(description).setColor(color));
        webhook.setAvatarUrl(ConfigManager.getConfigValue("Webhook-Avt", ""));
        webhook.setUsername(ConfigManager.getConfigValue("Webhook-Name", ""));
        try {
            webhook.execute();
        }catch (java.io.IOException e) {

        }
    }
}
