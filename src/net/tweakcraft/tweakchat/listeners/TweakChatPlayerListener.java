package net.tweakcraft.tweakchat.listeners;

import net.tweakcraft.tweakchat.ChatModeHandler;
import net.tweakcraft.tweakchat.TweakChat;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerListener;

/**
 * Created by IntelliJ IDEA.
 *
 * @author Edoxile
 */
public class TweakChatPlayerListener extends PlayerListener {
    private TweakChat plugin = null;
    private ChatModeHandler chatModeHandler;

    public TweakChatPlayerListener(TweakChat p) {
        plugin = p;
        chatModeHandler = new ChatModeHandler(plugin);
    }

    public void onPlayerChat(PlayerChatEvent event) {
        if (!event.isCancelled())
            event.setCancelled(chatModeHandler.sendMessage(event));
    }
}
