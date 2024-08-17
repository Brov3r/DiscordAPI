package com.brov3r.discordapi.commands;

import com.brov3r.discordapi.Main;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.spec.EmbedCreateSpec;
import discord4j.rest.util.Color;

import java.util.Map;

/**
 * Implementation of the help command. Displays a message about available commands.
 */
public class HelpCommand extends Command {
    private final Color successColor = Color.LIGHT_SEA_GREEN;
    private final Color failColor = Color.RED;

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

        EmbedCreateSpec embedSpec = args.length == 0 ? getAllCommands(commandMap, commandPrefix) : getHelpCommand(commandMap, commandPrefix, args[0].toLowerCase());

        event.getMessage().getChannel()
                .flatMap(channel -> channel.createMessage(embedSpec).withMessageReference(event.getMessage().getId()))
                .subscribe();

        return embedSpec.color().get() == successColor;
    }

    /**
     * Generates an EmbedCreateSpec containing a list of all available commands.
     *
     * @param commandMap    The map containing all commands.
     * @param commandPrefix The prefix used before each command name.
     * @return An EmbedCreateSpec that lists all commands.
     */
    private EmbedCreateSpec getAllCommands(Map<String, Command> commandMap, String commandPrefix) {
        StringBuilder commandListBuilder = new StringBuilder();
        commandListBuilder.append(Main.getTranslation("translation.help.contentTitle")).append("\n");

        commandMap.values().forEach(command -> {
            String commandName = String.format("**%s%s**", commandPrefix, command.getName());
            commandListBuilder.append(commandName).append(", ");
        });

        if (!commandListBuilder.isEmpty()) {
            commandListBuilder.setLength(commandListBuilder.length() - 2);
        }

        return EmbedCreateSpec.builder()
                .title(Main.getTranslation("translation.help.embedTitle"))
                .description(commandListBuilder.toString())
                .color(successColor)
                .build();
    }

    /**
     * Generates an EmbedCreateSpec containing detailed information about a specific command.
     *
     * @param commandMap    The map containing all commands.
     * @param commandPrefix The prefix used before the command name.
     * @param command       The name of the command to display details for.
     * @return An EmbedCreateSpec that shows the details of the specified command or a message indicating that the command was not found.
     */
    private EmbedCreateSpec getHelpCommand(Map<String, Command> commandMap, String commandPrefix, String command) {
        StringBuilder commandListBuilder = new StringBuilder();
        boolean commandExists = commandMap.containsKey(command);

        if (commandExists) {
            Command cmd = commandMap.get(command);
            commandListBuilder.append(String.format("**%s%s** - %s", commandPrefix, command, cmd.getDescription()));
        } else {
            commandListBuilder.append(Main.getTranslation("translation.help.commandNotFoundText")
                    .replace("<COMMAND>", String.format("%s%s", commandPrefix, command)));
        }

        return EmbedCreateSpec.builder()
                .title(commandExists ? Main.getTranslation("translation.help.embedTitle") : Main.getTranslation("translation.help.commandNotFoundTitle"))
                .description(commandListBuilder.toString())
                .color(commandExists ? successColor : failColor)
                .build();
    }
}