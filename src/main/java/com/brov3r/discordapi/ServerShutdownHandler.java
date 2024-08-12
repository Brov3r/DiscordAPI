package com.brov3r.discordapi;

import com.avrix.events.OnServerShutdownEvent;

import java.util.concurrent.TimeUnit;

/**
 * Handles server shutdown events to ensure proper cleanup of resources.
 */
public class ServerShutdownHandler extends OnServerShutdownEvent {

    /**
     * Handles the server shutdown event, ensuring that the Discord bot logs out and that the executor service is properly shut down.
     */
    @Override
    public void handleEvent() {
        // Log out the Discord bot if it's still active
        if (Main.getGateway() != null) {
            Main.getGateway().logout().block();
        }

        // Shutdown the executor service
        Main.executorService.shutdown();
        try {
            if (!Main.executorService.awaitTermination(60, TimeUnit.SECONDS)) {
                Main.executorService.shutdownNow();
                if (!Main.executorService.awaitTermination(60, TimeUnit.SECONDS)) {
                    System.err.println("[!] Executor service did not terminate");
                }
            }
        } catch (InterruptedException ie) {
            Main.executorService.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }
}