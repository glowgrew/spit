package ru.glowgrew.spit.bukkit;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.ConnectionFactory;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.java.annotation.plugin.Description;
import org.bukkit.plugin.java.annotation.plugin.Plugin;
import org.bukkit.plugin.java.annotation.plugin.author.Author;
import ru.glowgrew.spit.MessagingService;
import ru.glowgrew.spit.ShutdownHooks;
import ru.glowgrew.spit.amqp.AmqpMessagingService;
import ru.glowgrew.spit.amqp.ChannelFactory;
import ru.glowgrew.spit.serialize.MessageSerializer;

import java.util.concurrent.TimeUnit;

/**
 * @author glowgrew
 */
@Plugin(name = "SpitBukkit", version = "1.0.0")
@Description("Spit source for bukkit")
@Author("glowgrew")
public class SpitBukkitPlugin extends JavaPlugin {

    private ShutdownHooks shutdownHooks;

    @Override
    public void onDisable() {
        shutdownHooks.runAll();
    }

    @Override
    public void onEnable() {
        ObjectMapper mapper = new ObjectMapper();

        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");
        connectionFactory.setAutomaticRecoveryEnabled(true);
        connectionFactory.setNetworkRecoveryInterval(TimeUnit.SECONDS.toMillis(1));

        shutdownHooks = new ShutdownHooks();

        ChannelFactory channelFactory = new ChannelFactory(connectionFactory, shutdownHooks);

        MessageSerializer messageSerializer = new MessageSerializer(mapper);

        MessagingService messagingService = new AmqpMessagingService(channelFactory.newChannel(), messageSerializer);

        SpitApi spitApi = new SpitApiImpl(messagingService);

        SpitApiBukkitProvider.register(spitApi);
    }
}
