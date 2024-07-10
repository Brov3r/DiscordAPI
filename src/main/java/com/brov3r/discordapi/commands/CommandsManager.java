package com.brov3r.discordapi.commands;

import com.avrix.events.EventManager;
import com.brov3r.discordapi.Main;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.reaction.ReactionEmoji;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Manager class to handle Discord commands and their execution.
 */
public class CommandsManager {
    private static final Map<String, Command> commandMap = new HashMap<>();

    /**
     * Getting a registry of registered commands
     *
     * @return command registry
     */
    public static Map<String, Command> getCommandMap() {
        return commandMap;
    }

    /**
     * Adds a new command to the command map.
     *
     * @param command The Command object representing the command.
     */
    public static void addCommand(Command command) {
        if (commandMap.containsKey(command.getName())) {
            System.out.printf("[!] Failed adding Discord command! Command '%s' already exists!%n", command.getName());
            return;
        }

        commandMap.put(command.getName(), command);
        System.out.printf("[#] Added new Discord command: '%s'%n", command.getName());
    }

    /**
     * Executes a command based on its name and arguments.
     * Adds a reaction to the message indicating whether the command was executed successfully.
     *
     * @param event The MessageCreateEvent triggering the command execution.
     */
    public static void executeCommand(MessageCreateEvent event) {
        String[] args = com.avrix.commands.CommandsManager.getCommandArgs(
                Main.getInstance().getDefaultConfig().getString("commandPrefix"),
                event.getMessage().getContent()
        );

        if (args.length < 1) return;

        String commandName = args[0];
        String[] commandArgs = Arrays.copyOfRange(args, 1, args.length);

        if (!commandMap.containsKey(commandName)) return;

        boolean isExecuted = commandMap.get(commandName).execute(event, commandArgs);
        String reactionUnicode = isExecuted ? "\u2705" : "\u274C";

        event.getMessage().addReaction(ReactionEmoji.unicode(reactionUnicode)).subscribe();

        String userName = event.getMessage().getAuthor()
                .map(author -> author.getGlobalName().orElse("Unknown User"))
                .orElse("Unknown User");

        String argsString = Arrays.toString(commandArgs);

        System.out.printf("[#] User '%s' executed Discord command '%s' with arguments: %s%n",
                userName, commandName, argsString);

        EventManager.invokeEvent("onDiscordCommand", commandName, commandArgs, event);
    }
}