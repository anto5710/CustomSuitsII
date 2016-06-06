package armor;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Bat;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.EulerAngle;
import org.bukkit.util.Vector;

import armor.veronica.Veronica;
import util.ArmorUtil;
import util.Effects;
import util.Items;

public class Armor {
	private ArmorStand stand;
	private LivingEntity mover;

	private ItemStack item;
	private EquipmentType type;

	private final double Vbmsec = 0.8;

	private Veronica vr;
	private Player p;
	
	private World w;

	public Armor(Veronica vr, ItemStack armor) {
		this.vr = vr;
		this.p = vr.getPlayer();
		w = p.getWorld();

		if (p == null) {
			new NullPointerException("Player cannot be null").printStackTrace();
		}

		this.item = armor;
		this.type = EquipmentType.get(item);
	}

	private void setItem() {

		if (type == EquipmentType.HEAD) {
			stand.setHelmet(item);

		} else if (type == EquipmentType.CHEST) {
			stand.setChestplate(item);

		} else if (type == EquipmentType.LEGS) {
			stand.setLeggings(item);

		} else if (type == EquipmentType.FEET) {
			stand.setBoots(item);

		} else if (type == EquipmentType.MAIN_HAND) {
			stand.setItemInHand(item);
			
		} else if (type == EquipmentType.LEFT_HAND){
			
			stand.setItemInHand(item);
		}
	}

	public void spawn() {

		Location spread = ArmorUtil.randomLoc(vr.getCore().getLocation(), 10);

		stand = w.spawn(spread, ArmorStand.class);
		stand.setGravity(false);
		stand.setBasePlate(false);
		stand.setVisible(false);
		
		mover = w.spawn(spread, Bat.class);
		mover.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 10, true));
		mover.setInvulnerable(true);
		mover.setCollidable(false);
		mover.setAI(false);
		mover.setPassenger(stand);
		setItem();

		Effects.onArmorSpawn(stand);
	}

	public void remove() {
		Effects.onArmorDespawn(stand);
			
		stand.remove();
		mover.remove();
	}

	public boolean canWear() {
		return ArmorUtil.distance(p.getLocation(), stand.getLocation(), 2);
	}

	public void wear() {
		
		EquipmentType.replaceItem(p, item);
		
		remove();
	}

	
	public boolean canMove() {
		return !stand.isDead() && p.isOnline() && !p.isDead();
	}

	public void move() {
		Location ploc = ArmorUtil.randomLoc(p.getLocation(), 0.2);

		moveTo(ploc);
		rotate();
		Effects.onArmorFly(stand, type);
	}

	private void rotate() {
		EulerAngle angle = getAngle();

		stand.setLeftArmPose(angle);
		stand.setRightArmPose(angle);
		stand.setLeftLegPose(angle);
		stand.setRightLegPose(angle);
		stand.setHeadPose(angle);
		stand.setBodyPose(angle);
	}

	private EulerAngle getAngle() {
		Vector v = mover.getVelocity();

		double x = v.getX();
		double y = v.getY();
		double z = v.getZ();

		double radx = Math.atan2(z, y);
		double rady = Math.atan2(z, x);
		double radz = Math.atan2(y, x); //

		return new EulerAngle(radx, rady, radz);
	}

	private void moveTo(Location to) {
		Vector origin = mover.getVelocity();
		Vector v = caculateVector(to);

		mover.setVelocity(origin.add(v));
	}

	private Vector caculateVector(Location to) {
		Vector dif = ArmorUtil.getDifference(stand.getLocation(), to);

		return ArmorUtil.getVectorUnit(dif, Vbmsec);
	}

	public ArmorStand getEntity() {
		return stand;
	}

	public Player getPlayer() {
		return p;
	}
}
