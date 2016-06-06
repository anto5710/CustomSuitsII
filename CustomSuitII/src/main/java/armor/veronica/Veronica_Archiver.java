package armor.veronica;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.EnderCrystal;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;

import util.ArmorUtil;
import main.CustomSuitII;

public class Veronica_Archiver {
	private CustomSuitII main;
	private String KEY_PLAYER = "PLAYER_mk43";
	private String KEY_VERONICA = "VERONICA";
	private String VALUE_VERONICA = "HULKBUSTER_mk44";
	private Map<String, Veronica> playerMap;
	
	public Veronica_Archiver(CustomSuitII main){
		this.main = main;
		playerMap = new HashMap<>();
	}
	
	public void loadAll(){
		for(Player p : Bukkit.getOnlinePlayers()){
			map(load(p));
		}
	}
	
	private void map(Veronica vr){
		if(vr!=null){
			playerMap.put(ArmorUtil.getUUID(vr.getPlayer()), vr);
		}
	}
	
	public void save(Veronica vr){
		EnderCrystal core = vr.getCore();
		String uuid = ArmorUtil.getUUID(vr.getPlayer());
		
		core.setMetadata(KEY_VERONICA, new FixedMetadataValue(main, VALUE_VERONICA));
		core.setMetadata(KEY_PLAYER, new FixedMetadataValue(main, uuid));

		map(vr);
	}
	
	public boolean hasVeronica(Player p){
		return playerMap.containsKey(String.valueOf(p.getUniqueId()));
	}
	
	public boolean isVeronica(EnderCrystal core){
		return ArmorUtil.hasMetadata(core, KEY_VERONICA, VALUE_VERONICA);
	}
	
	public boolean isOwner(EnderCrystal core, Player p){
		String uuid = ArmorUtil.getUUID(p);
		
		return ArmorUtil.hasMetadata(core, KEY_PLAYER, uuid);
	}

	public Veronica load(Player p){
		
		for(World w : Bukkit.getWorlds()){
			for(EnderCrystal core : w.getEntitiesByClass(EnderCrystal.class)){

				if(isVeronica(core) && isOwner(core, p)){
					return new Veronica(main, p, core);
				}
			}
		}
		return null;
	}

	public void remove(Veronica vr) {
		playerMap.remove(ArmorUtil.getUUID(vr.getPlayer()));
	}

	public Veronica get(Player p) {
		return playerMap.get(ArmorUtil.getUUID(p));
	}

	public List<Veronica> getVeronicas() {
		return new ArrayList<>(playerMap.values());
	}

	public Veronica get(EnderCrystal core){
		String uuid = ArmorUtil.getUUID(core);
		
		
		for(Veronica vr : getVeronicas()){
			EnderCrystal c = vr.getCore();
			
			if(ArmorUtil.getUUID(c).equals(uuid)){
				return vr;
			}
		}
		return null;
	}
}
