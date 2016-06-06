package armor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.inventory.ItemStack;

import main.CustomSuitII;
import armor.ArmorTransmitter.State;
import armor.veronica.Veronica;

public class ArmorController {
	private static List<ItemStack> items 
	= Arrays.asList(ArmorItems.helemt, ArmorItems.chest, ArmorItems.leggings, ArmorItems.boots, ArmorItems.sword, ArmorItems.bow);
	
	private List<Armor>armors = new ArrayList<Armor>();
	private ArmorTransmitter transmitter;
	
	private final Veronica vr;

	
	public ArmorController(CustomSuitII main, Veronica vr){
		this.vr = vr;
		this.transmitter = new ArmorTransmitter(main, this);
	}
	
	private void spawnArmors(){
		
		for(ItemStack item : items){
			Armor armor = new Armor(vr, item);
			armor.spawn();
			
			armors.add(armor);
		}
	}
	
	public void update(){
		List<Armor> newList = new ArrayList<>();
		
		for(Armor armor : armors ){
			if(armor.canMove()){
				newList.add(armor);
			}
		}
		
		armors = newList;
	}
	
	public List<Armor> getArmors(){
		return armors;
	}
	
	public void revitalize() {
		transmitter.setState(State.PREPARED);
	}
	
	public void remove() {
		transmitter.setState(State.ENDED);
		
		for(Armor armor : armors){
			armor.remove();
		}
	}
	
	public void transmit(){
		if(!transmitter.isPrepared()){
			return;
		}
		
		spawnArmors();
		transmitter.start();
	}
}
