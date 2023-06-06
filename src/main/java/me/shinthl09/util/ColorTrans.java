package me.shinthl09.util;

import net.md_5.bungee.api.ChatColor;
import java.awt.Color;

public class ColorTrans {
    public static String transalate(String message) {
        return ChatColor.translateAlternateColorCodes('&', (message));
    }

    public static Color HexColorDecode(String hexColor) {
        Color color = Color.decode(hexColor);
        int red = color.getRed();
        int green = color.getGreen();
        int blue = color.getBlue();
        return new Color(red, green, blue);
    }
}
