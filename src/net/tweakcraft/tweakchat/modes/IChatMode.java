package net.tweakcraft.tweakchat.modes;

import org.bukkit.entity.Player;

/**
 * Created by IntelliJ IDEA.
 *
 * @author Edoxile
 */
public interface IChatMode {
    public boolean sendMessage(Player player, String message);
}
