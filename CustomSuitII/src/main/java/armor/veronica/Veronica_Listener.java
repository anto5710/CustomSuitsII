package armor.veronica;


import main.CustomSuitII;

import org.bukkit.entity.EnderCrystal;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FireworkExplodeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;

import armor.ArmorItems;
import util.ArmorUtil;

public class Veronica_Listener implements Listener {
	private CustomSuitII main;
	private Veronica_Archiver archiver;
	
	public Veronica_Listener(CustomSuitII main) {
		this.main = main;
		archiver = new Veronica_Archiver(main);
	}

	public void onEnable(){
		archiver.loadAll();
	}
	
	@EventHandler
	public void onLoad(PlayerJoinEvent e){
		archiver.load(e.getPlayer());
	}

	@EventHandler
	public void onSpawn(PlayerInteractEvent e) {
		if (!ArmorUtil.isRight(e.getAction())) {
			return;
		}
		if (ArmorItems.isRocket(e.getItem())) {
			Player p = e.getPlayer();
			
			if(!archiver.hasVeronica(p)){
				launch(p);
			}
		}
	}
	
	@EventHandler
	public void onPlace(BlockPlaceEvent e){
		ItemStack item = e.getItemInHand();
		
		if(item == null ){ return;}
		
		if( ArmorItems.isController(item)){
			e.setCancelled(true);
		}
	}

	private void launch(Player p) {
		Veronica veronica = new Veronica(main, p);
		veronica.spawn();
		
		archiver.save(veronica);
	}

	@EventHandler
	public void onStart(FireworkExplodeEvent e) {
		Entity passenger = e.getEntity().getPassenger();

		if(passenger instanceof EnderCrystal==false){
			return;
		}
		
		EnderCrystal crystal = (EnderCrystal) passenger;
		Veronica vr = archiver.get(crystal);
		
		if(vr!=null){
			vr.enable();
		}
	}

	@EventHandler
	public void onDespawn(EntityDamageEvent e) {
		Entity c = e.getEntity();
		
		if(c instanceof EnderCrystal){
			remove((EnderCrystal)c);
		}
	}

	private void remove(EnderCrystal core) {
		Veronica vr = archiver.get(core);
		
		if(vr!=null){
			archiver.remove(vr);
			vr.disable();
		}		
	}

	@EventHandler
	public void onTransmit(PlayerInteractEvent e) {
		Player p = e.getPlayer();

		if (!ArmorUtil.isRight(e.getAction())) {
			return;
		} 
		if (ArmorItems.isController(e.getItem())) {
			if (archiver.hasVeronica(p)) {
				Veronica vr = archiver.get(p);
				
				vr.enable();
				vr.transmit();
			}
		}
	}
}
