package net.rush.model;

public class StepSound {

	public final String name;
	public final float volume;
	public final float pitch;

	public StepSound(String name, float volume, float pitch) {
		this.name = name;
		this.volume = volume;
		this.pitch = pitch;
	}

	public String getBreakSound() {
		return "dig." + name;
	}

	public String getStepSound() {
		return "step." + name;
	}

	public float getVolume() {
		return volume;
	}

	public float getPitch() {
		return pitch;
	}
	
	/**
	 * Used when a player places a block.
	 */
	public String getPlaceSound() {
		return getBreakSound();
	}

	public static class StepSoundStone extends StepSound {
		
		public StepSoundStone(String name, float volume, float pitch) {
			super(name, volume, pitch);
		}

		@Override
		public String getBreakSound() {
			return "random.glass";
		}

		@Override
		public String getPlaceSound() {
			return "step.stone";
		}
	}

	public static class StepSoundLadder extends StepSound {
		
		public StepSoundLadder(String name, float volume, float pitch) {
			super(name, volume, pitch);
		}

		@Override
		public String getBreakSound() {
			return "dig.wood";
		}
	}

	public static class StepSoundAnvil extends StepSound {
		
		public StepSoundAnvil(String name, float volume, float pitch) {
			super(name, volume, pitch);
		}

		@Override
		public String getBreakSound() {
			return "dig.stone";
		}
		
		@Override
		public String getPlaceSound() {
			return "random.anvil_land";
		}
	}

}