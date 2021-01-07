package ru.glowgrew.spit.velocity;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;
import com.rabbitmq.client.ConnectionFactory;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.event.proxy.ProxyShutdownEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.proxy.ProxyServer;
import ru.glowgrew.spit.MessageListener;
import ru.glowgrew.spit.SharedChannelConstants;
import ru.glowgrew.spit.ShutdownHooks;
import ru.glowgrew.spit.amqp.ChannelFactory;
import ru.glowgrew.spit.handler.MessageDispatcher;
import ru.glowgrew.spit.jackson.AdaptedObjectMapperService;
import ru.glowgrew.spit.jackson.AdaptedObjectMapperServiceImpl;
import ru.glowgrew.spit.serialize.MessageSerializer;

import java.util.concurrent.TimeUnit;

/**
 * @author glowgrew
 */
@Plugin(id = "spit-velocity", name = "SpitVelocity", version = "1.0.0", authors = { "glowgrew" })
public class SpitVelocityPlugin {

    @Inject
    private ProxyServer proxy;

    private ShutdownHooks shutdownHooks;

    @Subscribe
    public void onProxyInitialization(ProxyInitializeEvent event) {
        AdaptedObjectMapperService objectMapperService = new AdaptedObjectMapperServiceImpl();
        ObjectMapper mapper = objectMapperService.createAdaptedObjectMapper();

        MessageSerializer messageDeserializer = new MessageSerializer(mapper);

        MessageDispatcher messageDispatcher = new MessageDispatcher(messageDeserializer);
        messageDispatcher.addHandler(new AlertMessageHandler(proxy));
        messageDispatcher.addHandler(new KickMessageHandler(proxy));

        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");
        connectionFactory.setAutomaticRecoveryEnabled(true);
        connectionFactory.setNetworkRecoveryInterval(TimeUnit.SECONDS.toMillis(1));

        shutdownHooks = new ShutdownHooks();

        ChannelFactory channelFactory = new ChannelFactory(connectionFactory, shutdownHooks);

        MessageListener messageListener = new MessageListener(channelFactory.newChannel(), messageDispatcher);

        ProxyChannelDeclarationTask declaration = new ProxyChannelDeclarationTask(messageListener,
                                                                                  SharedChannelConstants.PROXY,
                                                                                  channelFactory.newChannel());

        proxy.getScheduler().buildTask(this, declaration).schedule();
    }

    @Subscribe
    public void onProxyShutdown(ProxyShutdownEvent event) {
        shutdownHooks.runAll();
    }
}
