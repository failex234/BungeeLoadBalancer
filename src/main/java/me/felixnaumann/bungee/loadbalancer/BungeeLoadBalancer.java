package me.felixnaumann.bungee.loadbalancer;

import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.event.EventHandler;

import java.util.Map;

public class BungeeLoadBalancer extends Plugin implements Listener {

    @Override
    public void onEnable() {
        getProxy().getPluginManager().registerListener(this, this);
    }

    @EventHandler
    public void onPlayerJoin(PostLoginEvent e) {
        //Loop through all servers and connect the player to the server with the least amount of players.
        Map<String, ServerInfo> servers = getProxy().getServers();
        ServerInfo smallest = null;

        for (ServerInfo server : servers.values()) {
            if (smallest == null) {
                smallest = server;
            } else {
                if (server.getPlayers().size() < smallest.getPlayers().size()) smallest = server;
            }
        }

        if (smallest != null) {
            System.out.printf("[LoadBalancer] Sent player %s to server %s\n", e.getPlayer().getName(), smallest.getName());
            e.getPlayer().connect(smallest);
        }
    }
}
