package com.brov3r.discordapi.events;

import com.avrix.events.Event;
import discord4j.core.event.domain.message.MessageCreateEvent;

/**
 * Triggered when a chat command arrives
 */
public abstract class OnDiscordCommand extends Event {
    /**
     * Getting the event name
     *
     * @return name of the event being implemented
     */
    @Override
    public String getEventName() {
        return "onDiscordCommand";
    }

    /**
     * Called when a command arrives in a discord chat (any one).
     *
     * @param commandName  Name of the command that was called.
     * @param commandArgs  Command arguments passed.
     * @param messageEvent The {@link MessageCreateEvent} message event.
     */
    public abstract void handleEvent(String commandName, String[] commandArgs, MessageCreateEvent messageEvent);
}
