package wd;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class WDCommand implements CommandExecutor {

	Plugin plugin;
	WDGame game;
	
	public WDCommand(Plugin plugin) {
		this.plugin = plugin;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command arg1, String arg2, String[] args) {
		if(sender instanceof Player) {
			if(args[0].equals("newgame")) {
				game = new WDGame(plugin);
			}else if(args[0].equals("join")) {
				for(Player p:Bukkit.getOnlinePlayers()) {
					game.join(p);
				}
			}else if(args[0].equals("start")) {
				game.start();
			}else if(args[0].equals("wall")) {
				int speed = Integer.parseInt(args[1]);
				int delay = Integer.parseInt(args[2]);
			}
		}
		return true;
	}

}
