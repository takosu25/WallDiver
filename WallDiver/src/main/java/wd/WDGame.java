package wd;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFromToEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class WDGame implements Listener{

	Plugin plugin;
	World world;
	List<Player> players;
	Random rnd;
	
	public WDGame(Plugin plugin) {
		this.plugin = plugin;
		this.rnd = new Random();
		this.world = Bukkit.getWorld("WallDiver");
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	public void start() {
		callWall(0);
	}
	
	public void join(Player player) {
		if(!players.contains(player)){
			players.add(player);
		}
	}
	
	void callWall(int counter) {
		counter++;
		int delay = 10 - (counter / 10);
		if(delay <= 0) {
			delay = 1;
		}
		int tick = delay*10;
		wallMagma(delay,delay,counter / 13);
		int fcounter = counter;
		new BukkitRunnable() {
			public void run() {
				callWall(fcounter);
			}
		}.runTaskLater(plugin, delay * 20);
	}
	
	public void wallMagma(int speedForTicks,int delayForTicks,int level) {
		List<int[]> lis = new ArrayList<int[]>(); 
		Random rnd = new Random();
		for(int i = 0 ; i < (15-level)*2 ; i++) {
			int[] numbers = {rnd.nextInt(8),rnd.nextInt(8)};
			lis.add(numbers);
		}
		for(int j = 0 ; j < 40 ; j++) {
			int jj = j;
			new BukkitRunnable() {
				public void run() {
					putMagma(jj,delayForTicks,level,lis);
				}
			}.runTaskLater(plugin, speedForTicks * j);
		}
	}
	
	void putMagma(int phase,int delayForTicks,int level,List<int[]> lis) {
		Location l = new Vector(4,-59,0).toLocation(world);
		for(int i = 0 ; i < 8 ; i++) {
			for(int k = 0 ; k < 8 ; k++) {
				boolean cancel = false;
				for(int[] is:lis) {
					if(is[0] == i && is[1] == k) {
						Bukkit.getLogger().info("元の値：" + i + "    実際：" + is[0]);
						cancel = true;
						break;
					}
				}
				if(cancel) {
					continue;
				}
				Location cl = l.clone();
				cl.add(i,k,phase);
				if(cl.getBlock().getType() == Material.AIR) {
					cl.getBlock().setType(Material.LAVA);
				}
			}
		}
		new BukkitRunnable() {
			public void run() {
				for(int i = 0 ; i < 8 ; i++) {
					for(int k = 0 ; k < 8 ; k++) {
						Location cl = l.clone();
						cl.add(i,k,phase);
						cl.getBlock().setType(Material.AIR);
					}
				}
			}
		}.runTaskLater(plugin, delayForTicks);
	}
	
	@EventHandler
	public void onMagma(BlockFromToEvent e) {
		e.setCancelled(true);
	}
}
