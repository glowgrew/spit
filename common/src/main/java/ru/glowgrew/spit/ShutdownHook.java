package ru.glowgrew.spit;

/**
 * Represents an action that happens on server shutdown. It's typically
 * resource closing or any related stuff.
 *
 * @author glowgrew
 */
public interface ShutdownHook extends Runnable {

    void run();
}
