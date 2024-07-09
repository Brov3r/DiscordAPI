package com.brov3r.discordapi.commands;

import discord4j.core.event.domain.message.MessageCreateEvent;

/**
 * Represents an abstract command class for Discord4J commands.
 * Subclasses should implement the {@link #execute(MessageCreateEvent, String[])} method to define command behavior.
 */
public abstract class Command {

    private final String name;
    private final String description;

    /**
     * Constructs a new Command with a specified name and description.
     *
     * @param name        The name of the command.
     * @param description The description of the command.
     */
    public Command(String name, String description) {
        this.name = name;
        this.description = description;
    }

    /**
     * Executes the command with the given arguments.
     *
     * @param event The MessageCreateEvent triggering the command execution.
     * @param args  The arguments passed to the command.
     * @return {@code true} if the command execution was successful, {@code false} otherwise.
     */
    public abstract boolean execute(MessageCreateEvent event, String[] args);

    /**
     * Returns the name of the command.
     *
     * @return The name of the command.
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the description of the command.
     *
     * @return The description of the command.
     */
    public String getDescription() {
        return description;
    }
}