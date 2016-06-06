package armor;

import java.util.Arrays;

import org.bukkit.Material;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import util.ArmorUtil;


public enum EquipmentType {
	HEAD(39, 1.62, EnchantmentTarget.ARMOR_HEAD),
	CHEST(38, 1, EnchantmentTarget.ARMOR_TORSO),
	LEGS(37,0.5, EnchantmentTarget.ARMOR_LEGS),
	FEET(36, 0, EnchantmentTarget.ARMOR_FEET),
	MAIN_HAND(0,0.8, EnchantmentTarget.WEAPON, EnchantmentTarget.BOW, EnchantmentTarget.TOOL, EnchantmentTarget.FISHING_ROD),
	LEFT_HAND(1,0.8,EnchantmentTarget.WEAPON, EnchantmentTarget.BOW, EnchantmentTarget.TOOL, EnchantmentTarget.FISHING_ROD);
	
	
	private int index;
	private double offset;
	private final EnchantmentTarget [] types;
	
	private EquipmentType(int index, double offset, EnchantmentTarget...types){
		this.types = types;
		this.index = index;
		this.offset = offset;
	}
	
	public int getIndex() {
		return index;
	}

	public EnchantmentTarget[] getTypes() {
		return types;
	}

	public boolean includes(ItemStack itemStack){
		for(EnchantmentTarget type : types){
			if(type.includes(itemStack)){
				return true;
			}
		}
		
		return false;
	}
	
	public boolean isArmor(){
		return Arrays.asList(EquipmentType.HEAD, EquipmentType.CHEST, EquipmentType.LEGS, EquipmentType.FEET).contains(this);
	}
	
	public boolean isHand(){
		return Arrays.asList(EquipmentType.MAIN_HAND, EquipmentType.LEFT_HAND).contains(this);
	}

	public static EquipmentType get(ItemStack item) {
		for(EquipmentType type : values()){
			if(type.includes(item)){
				return type;
			}
		}
		return null;
	}

	public static void replaceItem(Player p, ItemStack item){
		EquipmentType type = get(item);
		PlayerInventory inv = p.getInventory();
		
		int index = type.getIndex();
		ItemStack last = inv.getItem(index);
		
		if(type.isArmor()){
			inv.setItem(index, item);
			
		}else if(type.isHand()) {
			if(type == EquipmentType.MAIN_HAND){
				last = inv.getItemInMainHand();
				inv.setItemInMainHand(item);
			
			}else{
				last = inv.getItemInOffHand();
				inv.setItemInOffHand(item);
			
			}
		}
		
		ArmorUtil.push(p, last);
		
		p.updateInventory();
	}

	public double getOffeset() {
		return offset;
	}
}
