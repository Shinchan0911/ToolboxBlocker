package me.shinthl09.config;

import com.google.gson.JsonObject;
import me.shinthl09.ToolboxBlocker;
import me.shinthl09.util.SendRequest;
import net.md_5.bungee.config.Configuration;

import java.util.logging.Logger;

public class ConfigVersionWarning {
    public final ToolboxBlocker toolboxBlocker;
    public ConfigVersionWarning(ToolboxBlocker pluginInstance) {
        toolboxBlocker = pluginInstance;
    }
    public String getLastedVersion() {
        JsonObject response = SendRequest.send("https://raw.githubusercontent.com/Shinchan0911/ToolboxBlocker/main/version.json");
        return response.get("version").getAsString();
    }
    public void checkVersion(Configuration cfg, Logger logger) {
        String lastedversion = getLastedVersion();
        if (!(cfg.getString("Version") == lastedversion)) {
            logger.warning("Warning: Version Plugin" + lastedversion + "Is Outdate, Update On: https://github.com/Shinchan0911/ToolboxBlocker");
        }
    }
}