package armor.veronica;

import main.CustomSuitII;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.EnderCrystal;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import armor.ArmorController;
import util.Colors;
import util.FireworkMaker;

public class Veronica{
	
	private boolean lauched= false;
	private boolean spawned = false;
	
	private EnderCrystal crystal;
	private Player p;
	private ArmorController ctrl;
	
	
	public Veronica(CustomSuitII main, Player p){
		this.p = p;
		this.ctrl = new ArmorController(main, this);
	}
	
	public Veronica(CustomSuitII main, Player p, EnderCrystal core){
		this(main, p);
		crystal = core;
		spawned = true;
	}
	
	public void spawn(){
		if(hasSpawned()){
			return;
		}
		
		World w = p.getWorld();
		Location ploc = p.getEyeLocation().add(0,5,0);
		
		crystal = w.spawn(ploc, EnderCrystal.class);
		crystal.setShowingBottom(false);
		
		launch();
	}
	
	public void transmit(){
		ctrl.transmit();
	}
	
	public void enable() {
		ctrl.revitalize();
	}
	
	public void disable(){
		ctrl.remove();
	}
	
	public Player getPlayer() {
		return p;
	}

	public EnderCrystal getCore() {
		return crystal;
	}
	

	private void launch() {
		Location loc = crystal.getLocation();
		lauched = true;
		
		
		Firework rocket = FireworkMaker.spawn(loc, Colors.getRandomColor());
		rocket.setPassenger(crystal);
	}
	
	public boolean hasSpawned(){
		return spawned;
	}
	
	public boolean hasLaunched(){
		return lauched;
	}
}
