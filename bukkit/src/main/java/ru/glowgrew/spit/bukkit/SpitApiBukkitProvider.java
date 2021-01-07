package ru.glowgrew.spit.bukkit;

import org.jetbrains.annotations.NotNull;

/**
 * @author glowgrew
 */
public class SpitApiBukkitProvider {

    private static SpitApi instance = null;

    private SpitApiBukkitProvider() {
        throw new UnsupportedOperationException("This class cannot be instantiated.");
    }

    @NotNull
    public static SpitApi get() {
        if (instance == null) {
            throw new IllegalStateException("The ru.glowgrew.spit.bukkit.SpitApi is not initialized.");
        }
        return instance;
    }

    static void register(SpitApi instance) {
        SpitApiBukkitProvider.instance = instance;
    }

    static void unregister() {
        SpitApiBukkitProvider.instance = null;
    }
}
