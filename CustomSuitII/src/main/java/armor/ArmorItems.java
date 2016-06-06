package armor;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.metadata.Metadatable;

import static util.Items.*;
import static util.ArmorUtil.*;
import static util.Enchanter.*;

public class ArmorItems {
	public static ItemStack helemt, chest, leggings, boots, sword, bow;
	
	public static ItemStack rocket, controller;

	public static final int enchant_level = 20;
	
	static{
		initItems();
	}

	private static void initItems() {
		
		helemt = createWeapon(Material.DIAMOND_HELMET, "통구리의 투구");
		chest = createWeapon(Material.DIAMOND_CHESTPLATE, "통구리의 갑옷");
		leggings = createWeapon(Material.DIAMOND_LEGGINGS, "통구리의 레깅스");
		boots = createWeapon(Material.DIAMOND_BOOTS, "통구리의 군화");
		sword = createWeapon(Material.DIAMOND_SWORD, "통구리의 검");
		bow = createWeapon(Material.BOW, "통구리의 활");
		
		controller = create(Material.REDSTONE_COMPARATOR, "조종기");
		rocket = create(Material.FIREWORK, "발사기");
	}
	
	private static ItemStack createWeapon(Material type, String name){
		ItemStack item = create(type, name);
		
		enchantFully(item, enchant_level);
		
		return item;
	}
	
	private static ItemStack create(Material type, String name){
		ItemStack item = new ItemStack(type);
		setDisplayName(item,spectralChatColor(name));
		
		return item;
	}
	
	public static boolean isRocket(ItemStack item) {
		return rocket.isSimilar(item);
	}

	public static boolean isController(ItemStack item) {
		return controller.isSimilar(item);
	}
}
