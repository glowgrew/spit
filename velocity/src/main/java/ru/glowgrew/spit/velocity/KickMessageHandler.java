package ru.glowgrew.spit.velocity;

import com.velocitypowered.api.proxy.ProxyServer;
import net.kyori.adventure.text.Component;
import ru.glowgrew.spit.handler.MessageHandler;
import ru.glowgrew.spit.message.KickMessage;

import java.lang.reflect.Type;

import static net.kyori.adventure.text.format.NamedTextColor.RED;

/**
 * @author glowgrew
 */
public class KickMessageHandler implements MessageHandler<KickMessage> {

    private final ProxyServer proxyServer;

    public KickMessageHandler(ProxyServer proxyServer) {
        this.proxyServer = proxyServer;
    }

    @Override
    public void accept(KickMessage alertMessage) {
        proxyServer.getPlayer(alertMessage.getUsername()).ifPresent(player -> {
            player.disconnect(Component.text(alertMessage.getReason()).colorIfAbsent(RED));
        });
    }

    @Override
    public Type getMessageType() {
        return KickMessage.class;
    }
}
