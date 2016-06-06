package armor;

import java.security.acl.Owner;
import java.util.HashMap;
import java.util.List;

import main.CustomSuitII;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ItemDespawnEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import util.ArmorUtil;

public class ItemListener implements Listener{
	private CustomSuitII main;
	private HashMap<Item, Player>thrown_items= new HashMap<>();
	
	public ItemListener(CustomSuitII main) {
		this.main = main;
	}
	
	@EventHandler
	public void onThrow(PlayerInteractEvent e){
		if(!ArmorUtil.isRight(e.getAction())){
			return;
		}
		Player p = e.getPlayer();
		ItemStack item = e.getItem();
		
		if(!ArmorItems.sword.isSimilar(item)){
			return;
		}
		
		if(hasThrownItem(p)){
			backTo(getThrownItem(p));
		}else{
			Item thrown = throwItem(p, item);
			add(p, thrown);
		}
	}

	private Item throwItem(Player p,ItemStack item) {
		World w = p.getWorld();
		Vector dir = p.getLocation().getDirection();
		Vector v = ArmorUtil.getVectorUnit(dir, 5);
		
		Item thrown = w.dropItem(p.getLocation(), item);
		thrown.setItemStack(item);
		thrown.setVelocity(v);
		
		return thrown;
	}
	
	@EventHandler
	public void onDespanw(ItemDespawnEvent e){
		Item item = e.getEntity();
		
		if(!isThrownItem(item)){
			return;
		}
		backTo(item);
		remove(item);
	}
	
	@EventHandler
	public void onPickUp(PlayerPickupItemEvent e){
		Item item = e.getItem();
		
		if(!isThrownItem(item)){
			return;
		}
		Player p = e.getPlayer();
		Player rp = thrown_items.get(p);
		
		if(p != rp){
			strike(p.getLocation());
			
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onHit(ProjectileHitEvent e){
		Projectile prj = e.getEntity();
		
		if(prj.isEmpty()){
			return;
		}
		
		Entity passenger = prj.getPassenger();
		
		if(passenger instanceof Item == false){
			return;
		}
		Item item = (Item)passenger;
		if(isThrownItem(item)){
			backTo(item);
			strike(passenger.getLocation());
		}
	}
	
	@EventHandler
	public void onHitEntity(EntityDamageByEntityEvent e){
		Entity ball = e.getDamager();
		Entity en = e.getEntity();
		
		if(ball instanceof Snowball == false){
			return;
		}
		Entity item = ball.getPassenger();
		
		if(item instanceof Item == false || !isThrownItem((Item)item)){
			return;
		}
		
		if(en != thrown_items.get((Item)en)){
			strike(item.getLocation());
		}
	}
	
	private void backTo(Item item){
		if(isThrownItem(item)){
			Player p = thrown_items.get(item);
			ArmorUtil.push(p, item.getItemStack());
		}
	}
	
	private void strike(Location loc){
		ArmorUtil.strikeLightning(loc, false);
	}
	
	private boolean hasThrownItem(Player p){
		return thrown_items.containsValue(p);
	}
	
	private Item getThrownItem(Player p){
		for(Item item : thrown_items.keySet()){
			if(thrown_items.get(item) == p){
				return item;
			}
		}
		return null;
	}
	
	private boolean isThrownItem(Item item){
		return thrown_items.containsKey(item);
	}
	
	private void add(Player p, Item item){
		thrown_items.put(item, p);
	}
	
	private void remove(Item item){
		thrown_items.remove(item);
	}
	
	
}
