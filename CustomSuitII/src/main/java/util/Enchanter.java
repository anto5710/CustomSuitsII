package util;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Enchanter {
	public static void enchantFully(ItemStack item, int level){
		for(Enchantment ench : Enchantment.values()){
			if(ench.canEnchantItem(item)){
				enchant(item, ench, level);
			}
		}
		
	}
	
	public static void enchant(ItemStack item, Enchantment ench, int level){
		ItemMeta meta = item.getItemMeta();
		meta.addEnchant(ench, level, true);
		 
		item.setItemMeta(meta);
	}
}
