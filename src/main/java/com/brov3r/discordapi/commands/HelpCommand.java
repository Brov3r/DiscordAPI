package com.brov3r.discordapi.commands;

import com.brov3r.discordapi.Main;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.channel.MessageChannel;
import discord4j.core.spec.EmbedCreateSpec;
import discord4j.rest.util.Color;

import java.util.Map;

/**
 * Implementation of the help command. Displays a message about available commands.
 */
public class HelpCommand extends Command {
    /**
     * Constructs a new Command with a specified name and description.
     */
    public HelpCommand() {
        super("help", Main.getTranslation("translation.help.description"));
    }

    /**
     * Executes the command with the given arguments.
     *
     * @param event The MessageCreateEvent triggering the command execution.
     * @param args  The arguments passed to the command.
     * @return {@code true} if the command execution was successful, {@code false} otherwise.
     */
    @Override
    public boolean execute(MessageCreateEvent event, String[] args) {
        Map<String, Command> commandMap = CommandsManager.getCommandMap();
        String commandPrefix = Main.getInstance().getDefaultConfig().getString("commandPrefix");

        StringBuilder commandListBuilder = new StringBuilder();
        commandListBuilder.append(Main.getTranslation("translation.help.contentTitle")).append("\n");

        for (Command command : commandMap.values()) {
            String commandName = String.format("%s%s", commandPrefix, command.getName());
            commandListBuilder.append(String.format("**%s** - %s\n", commandName, command.getDescription()));
        }

        EmbedCreateSpec embedSpec = EmbedCreateSpec.builder()
                .title(Main.getTranslation("translation.help.embedTitle"))
                .description(commandListBuilder.toString())
                .color(Color.LIGHT_SEA_GREEN)
                .build();

        Main.getGateway().getChannelById(event.getMessage().getChannelId())
                .ofType(MessageChannel.class)
                .flatMap(channel -> channel.createMessage(embedSpec))
                .subscribe();

        return true;
    }
}