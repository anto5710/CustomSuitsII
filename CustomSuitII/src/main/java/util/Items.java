package util;

import java.util.Arrays;
import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Items {
	public static void setDisplayName(ItemStack item, String name){
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(name);
		
		item.setItemMeta(meta);
	}
	
	public static void setLore(ItemStack item, String lore){
		ItemMeta meta = item.getItemMeta();
		
		List<String>lores = Arrays.asList(lore.split("\n"));
		meta.setLore(lores);
		
		item.setItemMeta(meta);
	}
	
	public static short getDurability(ItemStack item){
		return item.getType().getMaxDurability();
	}
	
	public static void giveItems(Player p, ItemStack...item){
		p.getInventory().addItem(item);
		p.updateInventory();
	}
}	
