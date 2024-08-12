package com.brov3r.discordapi;

import com.avrix.events.EventManager;
import com.avrix.plugin.Metadata;
import com.avrix.plugin.Plugin;
import com.avrix.plugin.ServiceManager;
import com.brov3r.discordapi.commands.CommandsManager;
import com.brov3r.discordapi.commands.HelpCommand;
import com.brov3r.discordapi.services.DiscordAPI;
import com.brov3r.discordapi.services.DiscordAPIImpl;
import discord4j.core.DiscordClient;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.lifecycle.ReadyEvent;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.User;

import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Main entry point
 */
public class Main extends Plugin {
    private static Main instance;
    private static GatewayDiscordClient gateway;
    private static DiscordClient client;
    public static final ExecutorService executorService = Executors.newSingleThreadExecutor();

    /**
     * Constructs a new {@link Plugin} with the specified metadata.
     * Metadata is transferred when the plugin is loaded into the game context.
     *
     * @param metadata The {@link Metadata} associated with this plugin.
     */
    public Main(Metadata metadata) {
        super(metadata);
    }

    /**
     * Called when the plugin is initialized.
     * <p>
     * Implementing classes should override this method to provide the initialization logic.
     */
    @Override
    public void onInitialize() {
        instance = this;
        loadDefaultConfig();

        EventManager.addListener(new ServerShutdownHandler());
        
        CommandsManager.addCommand(new HelpCommand());

        ServiceManager.register(DiscordAPI.class, new DiscordAPIImpl());

        executorService.submit(() -> loadBot(getDefaultConfig().getString("botToken")));
    }

    /**
     * Launching a discord bot
     *
     * @param token bot token
     */
    @SuppressWarnings("deprecation")
    private static void loadBot(String token) {
        if (token == null || token.isEmpty() || token.equalsIgnoreCase("...")) {
            System.out.println("[!] Failed to load Discord API! Discord token is invalid or missing!");
            return;
        }

        try {
            client = DiscordClient.create(token);
            gateway = client.login().block();

            if (gateway == null) {
                System.out.println("[!] Failed to authorize the bot! Check the token!");
                return;
            }

            gateway.getEventDispatcher().on(ReadyEvent.class).subscribe(event -> {
                User self = event.getSelf();
                System.out.printf("[#] Discord Bot Logged in as %s#%s%n", self.getUsername(), self.getDiscriminator());
            });

            gateway.on(MessageCreateEvent.class).subscribe(event -> {
                Optional<User> author = event.getMessage().getAuthor();

                if (author.isEmpty() || author.get().getId().asString().equalsIgnoreCase(gateway.getSelfId().asString()))
                    return;

                if (event.getMessage().getContent().isEmpty()) return;

                EventManager.invokeEvent("onDiscordMessage", event);

                String messageContent = event.getMessage().getContent();
                if (!messageContent.isEmpty()
                        && messageContent.startsWith(Main.getInstance().getDefaultConfig().getString("commandPrefix"))) {
                    CommandsManager.executeCommand(event);
                }
            });

            gateway.onDisconnect().block();
        } catch (Exception e) {
            System.out.println("[!] An error occurred in the Discord API: " + e.getMessage());
        }
    }

    /**
     * Getting a bot client gateway
     *
     * @return bot client gateway
     */
    public static GatewayDiscordClient getGateway() {
        return gateway;
    }

    /**
     * Getting a bot client
     *
     * @return bot client
     */
    public static DiscordClient getClient() {
        return client;
    }

    /**
     * Getting a plugin instance
     *
     * @return plugin instance
     */
    public static Main getInstance() {
        return instance;
    }

    /**
     * Getting a translation of text by key
     *
     * @param key translation key
     * @return text translation
     */
    public static String getTranslation(String key) {
        return instance.getDefaultConfig().getString(key);
    }
}