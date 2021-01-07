package ru.glowgrew.spit.bukkit;

import ru.glowgrew.spit.MessagingService;

/**
 * @author glowgrew
 */
public class SpitApiImpl implements SpitApi {

    private final MessagingService messagingService;

    public SpitApiImpl(MessagingService messagingService) {
        this.messagingService = messagingService;
    }

    @Override
    public MessagingService messagingService() {
        return messagingService;
    }
}
