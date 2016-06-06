package util;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.metadata.Metadatable;
import org.bukkit.util.Vector;

import armor.EquipmentType;

public class ArmorUtil {
	
	private static final String ChatColorSpectrum = "4c6ea2b319780";
	private static final Set<Material> transperents = new HashSet<>();
	
	static{
		for(Material type : Material.values()){
			if(type.isTransparent()){
				transperents.add(type);
			}
		}
	}
			
	public static double random(double range){
		return (Math.random()) * range;
	}
	
	public static double random_pm(double range){
		return (Math.random()- 0.5) * range;
	}
	
	public static Vector randomVector(Vector origin, double radius){
		double dx = random_pm(radius);
		double dy = random_pm(radius);
		double dz = random_pm(radius);
		
		return origin.add(new Vector(dx, dy, dz));
	}
	
	public static Location randomLoc(Location origin, double radius){
		double dx = random_pm(radius);
		double dy = random_pm(radius);
		double dz = random_pm(radius);
		
		return origin.clone().add(dx, dy, dz);
	}
	
	public static Vector getDifference(Location loc1, Location loc2){
		Location dif = loc2.clone().subtract(loc1);
		
		return dif.toVector();
	}
	
	public static Vector getVectorUnit(Vector v, double multiply){
		return v.clone().normalize().multiply(multiply);
	}

	public static Location getTarget(Player p, int max_distance){
		return p.getTargetBlock(transperents, max_distance).getLocation();
	}
	
	public static boolean distance(Location loc1, Location loc2, double radius){
		return loc1.distance(loc2) <= radius;
	}

	public static void dropItem(Player p, ItemStack item) {
		if(item==null || item.getType() == Material.AIR){
			return;
		}
		
		Vector dir = p.getLocation().getDirection();
		dir = randomVector(dir, 2);
		
		
		Item dropped = p.getWorld().dropItem(p.getLocation(), item);
		dropped.setPickupDelay(30);
		
		dropped.setVelocity(dir.normalize());
	}
	
	public static boolean isLeft(Action action){
		return action==Action.LEFT_CLICK_AIR || action == Action.LEFT_CLICK_BLOCK;
	}
	
	public static boolean isRight(Action action){
		return action==Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK;
	}
	
	public static String spectralChatColor(String text){
		String result = "";
		char code, pointText;
		
		int length = ChatColorSpectrum.length();
		int index = 0; 
		int add = 1;

		for(int i = 0; i < text.length(); i++){
			pointText = text.charAt(i);
			code = ChatColorSpectrum.charAt(index+=add);
			
			result+=  ""+ ChatColor.getByChar(code) + pointText;
			
			if(index==0 || index == length){
				add*=-1;
			}
		}
		return result;
	}

	public static boolean hasMetadata(Metadatable metadatable, String key, String val ){
		
		if(!metadatable.hasMetadata(key)){
			return false;
		}
		
		for(MetadataValue meta : metadatable.getMetadata(key)){
			if(meta.asString().equals(val)){
				return true;
			}
		}
		return false;
	}

	public static String getUUID(Entity e){
		return String.valueOf(e.getUniqueId());
	}
	
	public static String charAt(String text, int index){
		return String.valueOf(text.charAt(index));
	}
	
	public static void strikeLightning(Location loc, boolean isEffect){
		World w = loc.getWorld();
		
		if(!isEffect){
			createExplosion(loc, 8F, true, false);
			w.strikeLightningEffect(loc);
		}else{
			w.strikeLightning(loc);
		}
	}
	
	public static void createExplosion(Location loc, float power, boolean setFire, boolean breakBlocks){
		World w = loc.getWorld();
		w.createExplosion(loc.getX(), loc.getY(), loc.getZ(), power, setFire, breakBlocks);
	}
	
	public static void push(Player p, ItemStack item) {
		if(item==null || item.getType()==Material.AIR){
			return;
		}
		
		PlayerInventory inv = p.getInventory();
		
		if(inv.firstEmpty()==-1){
			ArmorUtil.dropItem(p, item);
		}else{
			inv.addItem(item);
		}
	}
}
