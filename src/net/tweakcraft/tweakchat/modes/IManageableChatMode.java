package net.tweakcraft.tweakchat.modes;

import org.bukkit.entity.Player;

/**
 * Created by IntelliJ IDEA.
 *
 * @author Edoxile
 */
public interface IManageableChatMode extends IChatMode {
    public void addReceiver(Player player);

    public void removeReceiver(Player player);
}
