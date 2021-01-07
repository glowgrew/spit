package ru.glowgrew.spit;

import java.util.HashSet;
import java.util.Set;

/**
 * Class responsible for holding shutdown hooks.
 *
 * @author glowgrew
 */
public class ShutdownHooks {

    private final Set<ShutdownHook> hooks;

    public ShutdownHooks() {
        this.hooks = new HashSet<>();
    }

    public ShutdownHooks(Set<ShutdownHook> hooks) {
        this.hooks = new HashSet<>(hooks);
    }

    /**
     * Registers shutdown hook.
     *
     * @param runnable hook
     */
    public void addHook(Runnable runnable) {
        hooks.add(runnable::run);
    }

    /**
     * Executes all shutdown hooks.
     */
    public void runAll() {
        hooks.forEach(Runnable::run);
    }
}
