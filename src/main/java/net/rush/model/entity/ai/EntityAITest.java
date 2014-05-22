package net.rush.model.entity.ai;

import net.rush.model.LivingEntity;
import net.rush.model.Player;
import net.rush.packets.packet.AnimationPacket;

import org.bukkit.Sound;

public class EntityAITest extends EntityAI {

	private boolean finished = false;
	private long airTicks = 0;

	
	private boolean was = false;
	private boolean hasName = false;
	private long nameVisibleTicks = 0;

	public EntityAITest(LivingEntity entity) {
		super(entity);
	}

	@Override
	public void pulse() {

		if(hasName) {
			if(nameVisibleTicks > 35) {
				entity.setNameVisible(false);
				entity.setName("");
				nameVisibleTicks = 0;
				hasName = false;
			} else
				nameVisibleTicks++;
		}

		if(entity.getWorld().getTypeId((int)entity.getPosition().getX(), (int)entity.getPosition().getY(), (int)entity.getPosition().getZ()) != 0) {
			if(!hasName && !was) {
				hasName = true;
				entity.setNameVisible(true);
				//entity.setName("I am stuck!");
				was = true;
			}
			return;
		}
		
		if(was && !hasName) {
			hasName = true;
			entity.setNameVisible(true);
			//entity.setName(rand.nextBoolean() ? "&aI am free!" : "&9Rush rocks!");
			was = false;
		}

		if(entity.getWorld().getTypeId((int)entity.getPosition().getX(), (int)entity.getPosition().getY() - 1, (int)entity.getPosition().getZ()) == 0) {
			System.out.println("entity falling from " + entity.getPosition().getY());
			entity.setPosition(entity.getPosition().getX(), entity.getPosition().getY() - .5, entity.getPosition().getZ());
			airTicks++;
		} else {
			if(!finished) {
				finished = true;
				System.out.println("entity stabilized at " + entity.getPosition().getY());
				System.out.println("ticks in air: " + airTicks);

				if(airTicks > 5) {
					entity.getWorld().playSound(entity.getPosition().getX(), entity.getPosition().getY(), entity.getPosition().getZ(), Sound.PIG_DEATH, 1F, 1F);
					//entity.setName(rand.nextBoolean() ? "&cThat hurted!" : "&cDon't u kill me there!");
					entity.setNameVisible(true);

					for(Player pl : entity.getWorld().getPlayers())
						if(pl.isWithinDistance(entity))
							pl.playAnimationOf(entity.getId(), AnimationPacket.DAMAGE_ANIMATION);

					hasName = true;
				}

				airTicks = 0;
			}

		}		

		if(rand.nextBoolean())
			entity.setPosition(entity.getPosition().getX(), entity.getPosition().getY(), entity.getPosition().getZ() + .15);
	}
}
