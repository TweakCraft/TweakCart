package net.tweakcraft.tweakchat.modes;

import org.bukkit.entity.Player;

/**
 * Created by IntelliJ IDEA.
 *
 * @author Edoxile
 */
public class LocalChat implements IChatMode {
    public boolean sendMessage(Player player, String message) {
        return false;
    }
}
