package be.maximvdw.placeholderapiexample;


import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.server.ServerListPingEvent;
import org.bukkit.plugin.java.JavaPlugin;

import be.maximvdw.placeholderapi.PlaceholderAPI; // The main API
import be.maximvdw.placeholderapi.PlaceholderReplacer; // The replacer interface
import be.maximvdw.placeholderapi.PlaceholderReplaceEvent; // The replacer event

public class PlaceholderAPIExample extends JavaPlugin implements Listener {

	@Override
	public void onEnable() {
		super.onEnable();

		// Register listener
		Bukkit.getPluginManager().registerEvents(this, this);

		// Check if the MVdWPlaceholderAPI plugin is present
		if (Bukkit.getPluginManager().isPluginEnabled("MVdWPlaceholderAPI")) {
			// The plugin is enabled
			PlaceholderAPI.registerPlaceholder(this, "myfirstplaceholder",
					new PlaceholderReplacer() {

						@Override
						public String onPlaceholderReplace(
								PlaceholderReplaceEvent event) {
							// Check if the placeholder is requested while
							// the player is online.
							boolean online = event.isOnline();
							// The player if he is online
							// NULL: If the player is not online
							Player player = event.getPlayer();
							// The offline player if he is not online
							// NULL: If the placeholder is requested without
							// a player (like the console) DO CHECKS YOURSELF
							OfflinePlayer offlinePlayer = event.getPlayer();
							// The placeholder that is requested to be replaced
							// (more about this in a later API example)
							String placeholder = event.getPlaceholder();

							if (offlinePlayer == null) {
								return "Player needed!";
							}
							// Return a colored player name
							return ChatColor.YELLOW + offlinePlayer.getName();
						}

					});
		}
	}

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		// Check if the MVdWPlaceholderAPI plugin is present
		if (Bukkit.getPluginManager().isPluginEnabled("MVdWPlaceholderAPI")) {
			// The plugin is enabled

			Player player = event.getPlayer();
			String welcomeMessage = ChatColor.GREEN
					+ "Welcome {myfirstplaceholder}" + ChatColor.GREEN
					+ "! Your locale is {locale}";
			// Replace the placeholders inside the welcome message
			welcomeMessage = PlaceholderAPI.replacePlaceholders(player,
					welcomeMessage);

			event.setJoinMessage(welcomeMessage);
		}
	}

	@EventHandler
	public void onServerPing(ServerListPingEvent event) {
		String serverMOTD = ChatColor.WHITE
				+ "Test Plugins online: {isonline@testplugins.com:25565}";
		// Replace the placeholders inside the server MOTD
		serverMOTD = PlaceholderAPI.replacePlaceholders(null,
				serverMOTD);
		
		event.setMotd(serverMOTD);
	}
}
