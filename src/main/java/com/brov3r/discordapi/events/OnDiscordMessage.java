package com.brov3r.discordapi.events;

import com.avrix.events.Event;
import discord4j.core.event.domain.message.MessageCreateEvent;

/**
 * Triggered when a chat message arrives
 */
public abstract class OnDiscordMessage extends Event {
    /**
     * Getting the event name
     *
     * @return name of the event being implemented
     */
    @Override
    public String getEventName() {
        return "onDiscordMessage";
    }

    /**
     * Called when a message arrives in a discord chat (any one).
     *
     * @param messageEvent The {@link MessageCreateEvent} message event.
     */
    public abstract void handleEvent(MessageCreateEvent messageEvent);
}