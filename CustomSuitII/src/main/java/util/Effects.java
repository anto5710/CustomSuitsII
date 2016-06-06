package util;


import org.bukkit.Color;
import org.bukkit.Effect;
import org.bukkit.FireworkEffect;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Firework;

import armor.EquipmentType;

public class Effects {
	public static void onArmorFly(ArmorStand stand, EquipmentType type){
		Location loc = stand.getLocation().add(0, type.getOffeset(), 0);
		
		spawnParticle(loc, Particle.FIREWORKS_SPARK, 1, null);
	}
	

	public static void onArmorSpawn(ArmorStand stand) {
		playFireworkEffect(stand.getLocation(), FireworkMaker.createEffect(Color.AQUA, Type.BALL));
	}
	
	
	public static void onArmorDespawn(ArmorStand stand) {
		playEffect(stand.getLocation(), Effect.EXPLOSION_HUGE, null);
	}
	
	public static void onArmorEquip(ArmorStand stand){
		Location loc = stand.getLocation();
		
		playEffect(loc, Effect.CLOUD, null);
		playSound(loc, Sound.ENTITY_HORSE_ARMOR);
	}
	
	public static <T> void playEffect(Location loc, Effect effect, T data){
		World w = loc.getWorld();
		
		w.playEffect(loc, effect, data);
	}
	
	public static void playSound(Location loc, Sound sound){
		loc.getWorld().playSound(loc, sound, 1.0F, 0F);
	}
	
	public static <T> void spawnParticle(Location loc, Particle particle, int count, T data){
		World w = loc.getWorld();
		
		w.spawnParticle(particle, loc, 1, 0D ,0D ,0D, 0D, data);
	}
	
	public static void playFireworkEffect(Location loc, FireworkEffect...effects){
		World w = loc.getWorld();
		
		Firework f = w.spawn(loc, Firework.class);
		f.setFireworkMeta(FireworkMaker.createFireworkMeta(f, effects));
		f.detonate();
	}
}
