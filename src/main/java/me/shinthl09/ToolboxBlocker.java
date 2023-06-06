package me.shinthl09;

import me.shinthl09.config.ConfigVersionWarning;
import me.shinthl09.events.PlayerHandshake;
import me.shinthl09.util.ColorTrans;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.logging.Logger;
public final class ToolboxBlocker extends Plugin {
    public static Configuration config;
    public static Plugin plugin;
    public Logger log = this.getLogger();
    @Override
    public void onEnable() {
        plugin = this;
        this.getLogger().info(ColorTrans.transalate("&e---------------------"));
        this.getLogger().info(ColorTrans.transalate(ColorTrans.transalate("&aAuthor:&e ShinTHL09, flashbtw")));
        this.getLogger().info(ColorTrans.transalate("&aPlugin:&e Has Enable"));
        this.getLogger().info(ColorTrans.transalate("&aName:&e " + getDescription().getName()));
        this.getLogger().info(ColorTrans.transalate("&e---------------------"));
        ProxyServer.getInstance().getPluginManager().registerListener(this, new PlayerHandshake());
        initiateConfig();
        ConfigVersionWarning configVersionManager = new ConfigVersionWarning(this);
        configVersionManager.checkVersion(config, log);
    }

    @Override
    public void onDisable() {
        this.getLogger().info(ColorTrans.transalate("&e---------------------"));
        this.getLogger().info("");
        this.getLogger().info(ColorTrans.transalate(ColorTrans.transalate("&aAuthor:&e ShinTHL09, flashbtw")));
        this.getLogger().info(ColorTrans.transalate("&aPlugin:&e Has Disable"));
        this.getLogger().info(ColorTrans.transalate("&aName:&e " + getDescription().getName()));
        this.getLogger().info("");
        this.getLogger().info(ColorTrans.transalate("&e---------------------"));
    }

    private void initiateConfig() {
        try {
            File dataFolder = this.getDataFolder();
            File configFile = new File(dataFolder, "config.yml");
            Path configFilePath = configFile.toPath();
            if (!dataFolder.exists()) {
                dataFolder.mkdir();
            }
            if (!configFile.exists()) {
                InputStream inputStream = this.getResourceAsStream("config.yml");
                Files.copy(inputStream, configFilePath);
            }
            ConfigurationProvider configProvider = ConfigurationProvider.getProvider(YamlConfiguration.class);
            config = configProvider.load(configFile);

        } catch (IOException | NullPointerException ioe) {
            ioe.printStackTrace();
        }
    }
}
