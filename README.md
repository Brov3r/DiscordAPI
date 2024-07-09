<div align="center">
    <h1>Discord API</h1>
</div>

<p align="center">
    <img alt="PZ Version" src="https://img.shields.io/badge/Project_Zomboid-41.78.16-blue">
    <img alt="Java version" src="https://img.shields.io/badge/Java-17-orange">
    <img alt="Avrix" src="https://img.shields.io/badge/AvrixLoader->=1.2.0-red">
</p>

**Discord API** - a unified set of tools for working with the Discord API from other plugins

# How to use

1) Install [AvrixLoader](https://github.com/Brov3r/Avrix)
2) Download the plugin from the releases page
2) Move to the `plugins` folder
3) Start the server and shut down
4) Configure the plugin in the plugin settings folder `plugins/discord-api`

# For developers

## How to use

1) Download and include the library as `compileOnly`
2) In the right place in the plugin (preferably in the main class) get the API object:

```java
DiscordAPI discordAPI = ServiceManager.getService(DiscordAPI.class);
```

## Events

- OnDiscordMessage -> Triggered when a chat message arrives

## API

```java
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
```

# Disclaimer

This software is provided "as is", without warranty of any kind, express or implied, including but not limited to the
warranties of merchantability, fitness for a particular purpose, and noninfringement of third party rights. Neither the
author of the software nor its contributors shall be liable for any direct, indirect, incidental, special, exemplary, or
consequential damages (including, but not limited to, procurement of substitute goods or services; loss of use, data, or
profits; or business interruption) however caused and on any theory of liability, whether in contract, strict liability,
or tort (including negligence or otherwise) arising in any way out of the use of this software, even if advised of the
possibility of such damage.

By using this software, you agree to these terms and release the author of the software and its contributors from any
liability associated with the use of this software.

# License

This project is licensed under [MIT License](./LICENSE).