package net.tweakcraft.tweakchat.listeners;

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

    public TweakChatPlayerListener(TweakChat p) {
        plugin = p;
    }

    public void onPlayerChat(PlayerChatEvent event) {
        //Do something
    }
}
