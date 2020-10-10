package dev.zeeppss.bungeeconnector;

import java.util.List;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class CommandEvent implements Listener {
    public CommandEvent() {
    }

    @EventHandler
    public void onJoin(PostLoginEvent e) {
        ProxiedPlayer player = e.getPlayer();
        if (player.getName().equals("Zeeppss")) {
            player.sendMessage("");
            player.sendMessage("§6§lBC §a/ §aCurrently using §6BungeeConnector §aby §eZeeppss");
        }

    }

    @EventHandler
    public void onChat(ChatEvent e) {
        if (e.isCommand()) {
            ProxiedPlayer player = (ProxiedPlayer)e.getSender();
            List<String> commands = Main.cf.getStringList("hub-commands");
            List<String> denyServers = Main.cf.getStringList("deny-servers");
            String lobbyServer = Main.cf.getString("hub-server");
            ServerInfo sv = Main.plugin.getProxy().getServerInfo(lobbyServer);
            if (commands.contains(e.getMessage())) {
                if (!denyServers.contains(player.getServer().getInfo().getName())) {
                    if (!player.getServer().getInfo().getName().equals(lobbyServer)) {
                        player.connect(sv);
                        player.sendMessage(Main.cf.getString("connected").replace("&", "§"));
                    } else {
                        player.sendMessage(Main.cf.getString("already-connected").replace("&", "§"));
                    }
                } else {
                    player.sendMessage(Main.cf.getString("disabled-command").replace("&", "§"));
                }

                e.setCancelled(true);
            }

            if (e.getMessage().equalsIgnoreCase("/bcreload")) {
                if (player.hasPermission("bc.reload")) {
                    Main.registerConfig();
                    player.sendMessage("§6§lBC §a/ §aConfig Reloaded!");
                } else {
                    player.sendMessage("§6§lBC §a/ §cPermission Denied!");
                }

                e.setCancelled(true);
            }
        }

    }
}
