package util;

import java.util.List;

import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.Location;
import org.bukkit.entity.Firework;
import org.bukkit.inventory.meta.FireworkMeta;

public class FireworkMaker {
	public static Firework spawn(Location loc, Color color){
		Firework f = loc.getWorld().spawn(loc, Firework.class);
		FireworkMeta meta = createFireworkMeta(f, createEffect(color));
		
		f.setFireworkMeta(meta);
		
		return f;
	}
	
	public static FireworkMeta createFireworkMeta(Firework firework,  FireworkEffect...effects){
		return createFireworkMeta(firework, 1, effects);
	}
	
	public static FireworkMeta createFireworkMeta(Firework firework, int power,  FireworkEffect...effects){
		FireworkMeta meta = firework.getFireworkMeta();
		meta.addEffects(effects);;
		meta.setPower(power);
		
		return meta;
	}

	public static FireworkEffect createEffect(Color color) {
		return createEffect(color, randomType());
	}
	
	public static FireworkEffect createEffect(Color color, Type type) {
		List<Color>colorList = Colors.getSpectrum(color, 20, 0.125F);
		
		return FireworkEffect.builder().with(type).withColor(colorList).withFade(colorList).withTrail().withFlicker().build();
	}

	private static Type randomType() {
		int index = (int) ArmorUtil.random(Type.values().length);
		
		return Type.values()[index];
	}
}
