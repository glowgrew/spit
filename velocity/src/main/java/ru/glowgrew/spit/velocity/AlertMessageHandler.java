package ru.glowgrew.spit.velocity;

import com.velocitypowered.api.proxy.ProxyServer;
import ru.glowgrew.spit.handler.MessageHandler;
import ru.glowgrew.spit.message.AlertMessage;

import java.lang.reflect.Type;

import static net.kyori.adventure.text.Component.text;
import static net.kyori.adventure.text.format.NamedTextColor.DARK_RED;
import static net.kyori.adventure.text.format.NamedTextColor.RED;

/**
 * @author glowgrew
 */
public class AlertMessageHandler implements MessageHandler<AlertMessage> {

    private final ProxyServer proxyServer;

    public AlertMessageHandler(ProxyServer proxyServer) {
        this.proxyServer = proxyServer;
    }

    @Override
    public void accept(AlertMessage alertMessage) {
        proxyServer.getAllPlayers().forEach(player -> {
            player.sendMessage(text("---------------------------------------").color(DARK_RED));
            player.sendMessage(text(alertMessage.getMessage()).color(RED));
            player.sendMessage(text("---------------------------------------").color(DARK_RED));
        });
    }

    @Override
    public Type getMessageType() {
        return AlertMessage.class;
    }
}
