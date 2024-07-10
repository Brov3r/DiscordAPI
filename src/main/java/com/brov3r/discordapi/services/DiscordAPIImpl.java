package com.brov3r.discordapi.services;

import com.brov3r.discordapi.Main;
import discord4j.common.util.Snowflake;
import discord4j.core.DiscordClient;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.User;
import discord4j.core.object.entity.channel.Channel;
import discord4j.core.object.entity.channel.MessageChannel;
import discord4j.core.object.reaction.ReactionEmoji;
import discord4j.core.spec.EmbedCreateSpec;
import discord4j.core.spec.MessageEditSpec;
import discord4j.core.spec.WebhookExecuteSpec;
import reactor.core.publisher.Mono;

/**
 * Implementing {@link DiscordAPI} interface methods
 */
public class DiscordAPIImpl implements DiscordAPI {
    /**
     * Retrieves the {@link GatewayDiscordClient} instance.
     *
     * @return the {@link GatewayDiscordClient} instance used for managing the gateway connection.
     */
    @Override
    public GatewayDiscordClient getGateway() {
        return Main.getGateway();
    }

    /**
     * Retrieves the {@link DiscordClient} instance.
     *
     * @return the {@link DiscordClient} instance used for REST API interactions.
     */
    @Override
    public DiscordClient getClient() {
        return Main.getClient();
    }

    /**
     * Send a message to a specified channel.
     *
     * @param channelId ID of the channel where to send the message
     * @param message   Message to send
     */
    @Override
    public void sendMessage(String channelId, String message) {
        if (getGateway() == null) {
            System.out.println("[!] Discord API gateway is not initialized!");
            return;
        }

        getGateway().getChannelById(Snowflake.of(channelId))
                .ofType(MessageChannel.class)
                .flatMap(channel -> channel.createMessage(message))
                .subscribe();
    }

    /**
     * Send an embed message to a specified channel.
     *
     * @param channelId       ID of the channel where to send the embed
     * @param embedCreateSpec The embed to send
     */
    @Override
    public void sendEmbedMessage(String channelId, EmbedCreateSpec embedCreateSpec) {
        if (getGateway() == null) {
            System.out.println("[!] Discord API gateway is not initialized!");
            return;
        }

        getGateway().getChannelById(Snowflake.of(channelId))
                .ofType(MessageChannel.class)
                .flatMap(channel -> channel.createMessage(embedCreateSpec))
                .subscribe();
    }

    /**
     * Send a message to a specified webhook.
     *
     * @param webhookId          The ID of the webhook
     * @param webhookToken       The token of the webhook
     * @param webhookExecuteSpec The WebhookExecuteSpec containing the message or embed to send
     */
    @Override
    public void sendWebhook(long webhookId, String webhookToken, WebhookExecuteSpec webhookExecuteSpec) {
        if (getGateway() == null) {
            System.out.println("[!] Discord API gateway is not initialized!");
            return;
        }

        getGateway().getWebhookByIdWithToken(Snowflake.of(webhookId), webhookToken)
                .flatMap(hook -> hook.execute(webhookExecuteSpec)).subscribe();
    }

    /**
     * Deletes a message in a specified channel.
     *
     * @param channelId the ID of the channel where the message is located
     * @param messageId the ID of the message to be deleted
     */
    @Override
    public void deleteMessage(String channelId, String messageId) {
        if (getGateway() == null) {
            System.out.println("[!] Discord API gateway is not initialized!");
            return;
        }

        getGateway().getMessageById(Snowflake.of(channelId), Snowflake.of(messageId))
                .flatMap(Message::delete)
                .subscribe();
    }

    /**
     * Edits a message in a specified channel.
     *
     * @param channelId       the ID of the channel where the message is located
     * @param messageId       the ID of the message to be edited
     * @param messageEditSpec the MessageEditSpec containing the new content or embed for the message
     */
    @Override
    public void editMessage(String channelId, String messageId, MessageEditSpec messageEditSpec) {
        if (getGateway() == null) {
            System.out.println("[!] Discord API gateway is not initialized!");
            return;
        }

        getGateway().getMessageById(Snowflake.of(channelId), Snowflake.of(messageId))
                .flatMap(message -> message.edit(messageEditSpec))
                .subscribe();
    }

    /**
     * Gets information about a user by ID.
     *
     * @param userId the ID of the user to retrieve
     * @return a Mono emitting the User object
     */
    @Override
    public Mono<User> getUserById(String userId) {
        if (getGateway() == null) {
            System.out.println("[!] Discord API gateway is not initialized!");
            return Mono.empty();
        }

        return getGateway().getUserById(Snowflake.of(userId));
    }

    /**
     * Gets information about a channel by ID.
     *
     * @param channelId the ID of the channel to retrieve
     * @return a Mono emitting the Channel object
     */
    @Override
    public Mono<Channel> getChannelById(String channelId) {
        if (getGateway() == null) {
            System.out.println("[!] Discord API gateway is not initialized!");
            return Mono.empty();
        }

        return getGateway().getChannelById(Snowflake.of(channelId));
    }

    /**
     * Adds a reaction to a specified message.
     *
     * @param channelId the ID of the channel where the message is located
     * @param messageId the ID of the message to add a reaction to
     * @param emoji     the emoji to react with
     */
    @Override
    public void addReaction(String channelId, String messageId, String emoji) {
        if (getGateway() == null) {
            System.out.println("[!] Discord API gateway is not initialized!");
            return;
        }

        getGateway().getMessageById(Snowflake.of(channelId), Snowflake.of(messageId))
                .flatMap(message -> message.addReaction(ReactionEmoji.unicode(emoji)))
                .subscribe();
    }

    /**
     * Removes a reaction from a specified message.
     *
     * @param channelId the ID of the channel where the message is located
     * @param messageId the ID of the message to remove a reaction from
     * @param userId    the ID user
     * @param emoji     the emoji to remove (unicode)
     */
    @Override
    public void removeReaction(String channelId, String messageId, String userId, String emoji) {
        if (getGateway() == null) {
            System.out.println("[!] Discord API gateway is not initialized!");
            return;
        }

        getGateway().getMessageById(Snowflake.of(channelId), Snowflake.of(messageId))
                .flatMap(message -> message.removeReaction(ReactionEmoji.unicode(emoji), Snowflake.of(userId)))
                .subscribe();
    }

    /**
     * Removes a reaction from a specified message.
     *
     * @param channelId the ID of the channel where the message is located
     * @param messageId the ID of the message to remove a reaction from
     * @param emoji     the emoji to remove (unicode)
     */
    @Override
    public void removeReaction(String channelId, String messageId, String emoji) {
        if (getGateway() == null) {
            System.out.println("[!] Discord API gateway is not initialized!");
            return;
        }

        getGateway().getMessageById(Snowflake.of(channelId), Snowflake.of(messageId))
                .flatMap(message -> message.removeReaction(ReactionEmoji.unicode(emoji), getGateway().getSelfId()))
                .subscribe();
    }
}