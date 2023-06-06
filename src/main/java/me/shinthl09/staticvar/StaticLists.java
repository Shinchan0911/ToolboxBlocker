package me.shinthl09.staticvar;

import java.util.ArrayList;

public final class StaticLists {
    public static final ArrayList<String> FORBIDDEN_MODELNAMES = new ArrayList<>();
    static {
        FORBIDDEN_MODELNAMES.add("Xiaomi");
    }
}