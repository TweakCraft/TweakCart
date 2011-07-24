package net.tweakcraft.tweakchat;

import net.tweakcraft.tweakchat.listeners.TweakChatPlayerListener;
import org.bukkit.event.Event;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by IntelliJ IDEA.
 *
 * @author Edoxile
 */
public class TweakChat extends JavaPlugin {
    private static final Logger logger = Logger.getLogger("Minecraft");
    private TweakChatPlayerListener tcPlayerListener = null;

    public void onEnable() {
        tcPlayerListener = new TweakChatPlayerListener(this);
        getServer().getPluginManager().registerEvent(Event.Type.PLAYER_CHAT, tcPlayerListener, Event.Priority.Normal, this);
        log(Level.INFO, "Enabled! Version: " + getDescription().getVersion());
    }

    public void onDisable() {
        log(Level.INFO, "Disabled!");
    }

    public void log(Level level, String message) {
        logger.log(level, "[TweakChat] " + message);
    }
}
