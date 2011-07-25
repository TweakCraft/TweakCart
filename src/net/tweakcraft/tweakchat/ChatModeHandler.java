package net.tweakcraft.tweakchat;

import net.tweakcraft.tweakchat.modes.*;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerChatEvent;

import java.util.HashMap;

/**
 * Created by IntelliJ IDEA.
 *
 * @author Edoxile
 */
public class ChatModeHandler {
    private HashMap<Player, ChatMode> playerChatModeMap = new HashMap<Player, ChatMode>();
    private HashMap<ChatMode, IChatMode> chatModeMap = new HashMap<ChatMode, IChatMode>();
    private HashMap<Player, GroupChat> groupChatMap = new HashMap<Player, GroupChat>();
    private AdminChat adminChat = new AdminChat();

    private TweakChat plugin;

    public ChatModeHandler(TweakChat p) {
        plugin = p;

        chatModeMap.put(ChatMode.WORLD, new WorldChat());
        chatModeMap.put(ChatMode.REGION, new RegionChat());
        chatModeMap.put(ChatMode.ZONE, new ZoneChat());
        chatModeMap.put(ChatMode.LOCAL, new LocalChat());

        for (Player player : plugin.getServer().getOnlinePlayers()) {
            //Get permissions. If player is admin add him to list.
            if (true)
                adminChat.addReceiver(player);
        }
    }

    public IChatMode getChatMode(Player p) {
        if (playerChatModeMap.containsKey(p)) {
            ChatMode cm = playerChatModeMap.get(p);
            switch (cm) {
                case ZONE:
                case WORLD:
                case REGION:
                case LOCAL:
                    return chatModeMap.get(cm);
                case GROUP:
                    if (groupChatMap.containsKey(p))
                        return groupChatMap.get(p);
                    else
                        return null;
                default:
                    return null;
            }
        } else {
            return null;
        }
    }

    public boolean sendMessage(PlayerChatEvent event) {
        //Check for escape character first (!), then check on chatmode etc.

        IChatMode chatMode = getChatMode(event.getPlayer());
        if (chatMode != null) {
            chatMode.sendMessage(event.getPlayer(), event.getMessage());
            return false;
        } else {
            return true;
        }
    }
}
