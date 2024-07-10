package com.brov3r.discordapi.services;

import discord4j.core.DiscordClient;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.object.entity.User;
import discord4j.core.object.entity.channel.Channel;
import discord4j.core.spec.EmbedCreateSpec;
import discord4j.core.spec.MessageEditSpec;
import discord4j.core.spec.WebhookExecuteSpec;
import reactor.core.publisher.Mono;

/**
 * Interface for Discord API services.
 * Provides methods for sending messages and embeds to channels and webhooks,
 * as well as other Discord operations.
 */
public interface DiscordAPI {
    /**
     * Retrieves the {@link GatewayDiscordClient} instance.
     *
     * @return the {@link GatewayDiscordClient} instance used for managing the gateway connection.
     */
    GatewayDiscordClient getGateway();

    /**
     * Retrieves the {@link DiscordClient} instance.
     *
     * @return the {@link DiscordClient} instance used for REST API interactions.
     */
    DiscordClient getClient();

    /**
     * Send a message to a specified channel.
     *
     * @param channelId ID of the channel where to send the message
     * @param message   Message to send
     */
    void sendMessage(String channelId, String message);

    /**
     * Send an embed message to a specified channel.
     *
     * @param channelId       ID of the channel where to send the embed
     * @param embedCreateSpec The embed to send
     */
    void sendEmbedMessage(String channelId, EmbedCreateSpec embedCreateSpec);

    /**
     * Send a message to a specified webhook.
     *
     * @param webhookId          The ID of the webhook
     * @param webhookToken       The token of the webhook
     * @param webhookExecuteSpec The WebhookExecuteSpec containing the message or embed to send
     */
    void sendWebhook(long webhookId, String webhookToken, WebhookExecuteSpec webhookExecuteSpec);

    /**
     * Deletes a message in a specified channel.
     *
     * @param channelId the ID of the channel where the message is located
     * @param messageId the ID of the message to be deleted
     */
    void deleteMessage(String channelId, String messageId);

    /**
     * Edits a message in a specified channel.
     *
     * @param channelId       the ID of the channel where the message is located
     * @param messageId       the ID of the message to be edited
     * @param messageEditSpec the MessageEditSpec containing the new content or embed for the message
     */
    void editMessage(String channelId, String messageId, MessageEditSpec messageEditSpec);

    /**
     * Gets information about a user by ID.
     *
     * @param userId the ID of the user to retrieve
     * @return a Mono emitting the User object
     */
    Mono<User> getUserById(String userId);

    /**
     * Gets information about a channel by ID.
     *
     * @param channelId the ID of the channel to retrieve
     * @return a Mono emitting the Channel object
     */
    Mono<Channel> getChannelById(String channelId);

    /**
     * Adds a reaction to a specified message.
     *
     * @param channelId the ID of the channel where the message is located
     * @param messageId the ID of the message to add a reaction to
     * @param emoji     the emoji to react with
     */
    void addReaction(String channelId, String messageId, String emoji);

    /**
     * Removes a reaction from a specified message.
     *
     * @param channelId the ID of the channel where the message is located
     * @param messageId the ID of the message to remove a reaction from
     * @param userId    the ID user
     * @param emoji     the emoji to remove
     */
    void removeReaction(String channelId, String messageId, String userId, String emoji);

    /**
     * Removes a reaction from a specified message.
     *
     * @param channelId the ID of the channel where the message is located
     * @param messageId the ID of the message to remove a reaction from
     * @param emoji     the emoji to remove
     */
    void removeReaction(String channelId, String messageId, String emoji);
}