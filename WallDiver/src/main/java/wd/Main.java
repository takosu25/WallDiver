package wd;

import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin{

	@Override
	public void onEnable() {
		super.onEnable();
		getCommand("wd").setExecutor(new WDCommand(this));
	}
}
