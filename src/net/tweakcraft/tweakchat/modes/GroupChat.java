package net.tweakcraft.tweakchat.modes;

import org.bukkit.entity.Player;

/**
 * Created by IntelliJ IDEA.
 *
 * @author Edoxile
 */
public class GroupChat implements IManageableChatMode {
    public void addReceiver(Player player) {
    }

    public void removeReceiver(Player player) {
    }

    public boolean sendMessage(Player player, String message) {
        return false;
    }
}
