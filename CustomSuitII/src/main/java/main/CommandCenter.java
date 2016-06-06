package main;


import org.apache.commons.lang.NullArgumentException;

import org.bukkit.command.Command;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import armor.ArmorItems;
import util.Items;

public class CommandCenter {
	
	public static boolean onCommand(Player p, Command command, String[] args){
		String name = command.getName();
		
		if(name.equals("get")){
			get(p, args);
		}else{
			return false;
		}
		
		return true;
	}

	private static void get(Player p, String[]args) {
		if(args.length == 0){
			new NullArgumentException("You must type more than one argument").printStackTrace();;
		}
		
		String key = args[0].toLowerCase().trim();
		ItemStack item;
		
		if(key.equals("rocket")){
			item = ArmorItems.rocket;
		}else if(key.equals("controller")){
			item = ArmorItems.controller;
		}else{
			return;
		}
		Items.giveItems(p, item);
	}
}
