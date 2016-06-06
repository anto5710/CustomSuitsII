package main;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import armor.ItemListener;
import armor.veronica.Veronica_Listener;

public class CustomSuitII extends JavaPlugin {
	private PluginManager manager = getServer().getPluginManager();
	
	@Override
	public void onEnable() {
		initListeners();
	}
	
	private void initListeners(){
		Veronica_Listener listener = new Veronica_Listener(this);
		listener.onEnable();

		manager.registerEvents(listener, this);
		manager.registerEvents(new ItemListener(this), this);
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		Player p = Bukkit.getPlayer(sender.getName());
		
		return CommandCenter.onCommand(p, command, args);
	}
}
