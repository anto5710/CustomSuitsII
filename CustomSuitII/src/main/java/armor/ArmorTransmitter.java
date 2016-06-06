package armor;

import java.util.List;

import main.CustomSuitII;

import org.bukkit.scheduler.BukkitRunnable;

public class ArmorTransmitter extends BukkitRunnable {

	public enum State {
		STARTED, PREPARED, ENDED;
	}

	private static final int delay = 5;
	private CustomSuitII main;
	private ArmorController ctrl;

	private State state = State.ENDED;

	public ArmorTransmitter(CustomSuitII main, ArmorController ctrl) {
		this.main = main;
		this.ctrl = ctrl;
	}

	@Override
	public void run() {
		
		ctrl.update();
		List<Armor> armors = ctrl.getArmors();
		
		if (armors.isEmpty()) {
			stop();
			
			return;
		} 
		
		control(armors);
	}

	private void control(List<Armor> armors) {
		for (Armor armor : armors) {
			armor.move();
			
			if(armor.canWear()){
				armor.wear();
			}
		}
	}

	public void start() {
		state = State.STARTED;
		  
		new ArmorTransmitter(main, ctrl).runTaskTimer(main, 0, 1);
	}
	
	private void stop() {
		state = State.ENDED;
		cancel();
		
		new BukkitRunnable() {
			
			@Override
			public void run() {
				ctrl.revitalize();																																
			}
		}.runTaskLater(main, delay*2);
	}
	
	public void setState(State state) {
		this.state = state;
	}

	public State getState() {
		return state;
	}

	public boolean isPrepared() {
		return state == State.PREPARED;
	}

	public boolean isRunning() {
		return state == State.STARTED;
	}

	public boolean isEnded() {
		return state == State.ENDED;
	}
}
