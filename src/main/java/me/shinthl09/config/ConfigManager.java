package me.shinthl09.config;

import me.shinthl09.ToolboxBlocker;

public class ConfigManager {
    public static <T>T getConfigValue(String path, T type) {
        return ToolboxBlocker.config.get(path, type);
    }
}