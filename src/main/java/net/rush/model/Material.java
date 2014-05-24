package net.rush.model;

public class Material {

	public static final Material AIR = new Material(MapColor.airColor).notSolid();
	public static final Material GRASS = new Material(MapColor.grassColor);
	public static final Material DIRT = new Material(MapColor.dirtColor);
	public static final Material WOOD = new Material(MapColor.woodColor).setBurning();
	public static final Material STONE = new Material(MapColor.stoneColor).setRequiresTool();
	public static final Material ORE = new Material(MapColor.ironColor).setRequiresTool();
	public static final Material ANVIL = new Material(MapColor.ironColor).setRequiresTool().setImmovableMobility();
	public static final Material WATER = new Material(MapColor.waterColor).setNoPushMobility().notSolid();
	public static final Material LAVA = new Material(MapColor.tntColor).setNoPushMobility().notSolid();
	public static final Material LEAVES = new Material(MapColor.foliageColor).setBurning().setTranslucent().setNoPushMobility();
	public static final Material PLANT = new Material(MapColor.foliageColor).setNoPushMobility().notSolid();
	public static final Material REPLACEABLE_PLANT = new Material(MapColor.foliageColor).setBurning().setNoPushMobility().setReplaceable().notSolid();
	public static final Material SPONGE = new Material(MapColor.clothColor);
	public static final Material CLOTH = new Material(MapColor.clothColor).setBurning();
	public static final Material FIRE = new Material(MapColor.airColor).setNoPushMobility().notSolid();
	public static final Material SAND = new Material(MapColor.sandColor);
	public static final Material CIRCUITS = new Material(MapColor.airColor).setNoPushMobility().notSolid();
	public static final Material CARPET = new Material(MapColor.clothColor).setBurning().notSolid();
	public static final Material GLASS = new Material(MapColor.airColor).setTranslucent().setAdventureModeExempt();
	public static final Material BUILDABLE_GLASS = new Material(MapColor.airColor).setAdventureModeExempt();
	public static final Material TNT = new Material(MapColor.tntColor).setBurning().setTranslucent();
    public static final Material CORAL = (new Material(MapColor.foliageColor)).setNoPushMobility();
    public static final Material ICE = (new Material(MapColor.iceColor)).setTranslucent().setAdventureModeExempt();
    public static final Material SNOW_LAYER = (new Material(MapColor.snowColor)).setReplaceable().setTranslucent().setRequiresTool().setNoPushMobility().notSolid();
    public static final Material SNOW_BLOCK = (new Material(MapColor.snowColor)).setRequiresTool();
    public static final Material CACTUS = (new Material(MapColor.foliageColor)).setTranslucent().setNoPushMobility();
    public static final Material CLAY = new Material(MapColor.clayColor);
    public static final Material PUMPKIN = (new Material(MapColor.foliageColor)).setNoPushMobility();
    public static final Material DRAGON_EGG = (new Material(MapColor.foliageColor)).setNoPushMobility();
    public static final Material PORTAL = (new Material(MapColor.airColor)).setImmovableMobility().notSolid();
    public static final Material CAKE = (new Material(MapColor.airColor)).setNoPushMobility();
    public static final Material WEB = (new Material(MapColor.clothColor)).setRequiresTool().setNoPushMobility();
    public static final Material PISTON = (new Material(MapColor.stoneColor)).setImmovableMobility();

	/** Bool defining if the block can burn or not. */
	private boolean canBurn;

	/**
	 * Determines whether blocks with this material can be "overwritten" by other blocks when placed - eg snow, vines
	 * and tall grass.
	 */
	private boolean replaceable;

	/** Indicates if the material is translucent */
	private boolean isTranslucent;

	/** The color index used to draw the blocks of this material on maps. */
	public final MapColor mapColor;

	/**
	 * Determines if the material can be harvested without a tool (or with the wrong tool)
	 */
	private boolean requiresNoTool = true;
	
	private boolean solid = true;

	/**
	 * Mobility information flag. 0 indicates that this block is normal, 1 indicates that it can't push other blocks, 2
	 * indicates that it can't be pushed.
	 */
	private int mobilityFlag;
	private boolean isAdventureModeExempt;

	public Material(MapColor mapColor) {
		this.mapColor = mapColor;
	}

	/**
	 * Returns if blocks of these materials are liquids.
	 */
	public boolean isLiquid() {
		return false;
	}

	public Material notSolid() {
		solid = false;
		return this;
	}
	
	public boolean isSolid() {
		return solid;
	}

	/**
	 * Will prevent grass from growing on dirt underneath and kill any grass below it if it returns true
	 */
	public boolean getCanBlockGrass() {
		return true;
	}

	/**
	 * Returns if this material is considered solid or not
	 */
	public boolean blocksMovement() {
		return true;
	}

	private Material setTranslucent() {
		this.isTranslucent = true;
		return this;
	}

	/**
	 * Makes blocks with this material require the correct tool to be harvested.
	 */
	protected Material setRequiresTool() {
		this.requiresNoTool = false;
		return this;
	}

	protected Material setBurning() {
		this.canBurn = true;
		return this;
	}

	public boolean getCanBurn() {
		return this.canBurn;
	}

	public Material setReplaceable() {
		this.replaceable = true;
		return this;
	}

	public boolean isReplaceable() {
		return this.replaceable;
	}

	public boolean isOpaque() {
		return this.isTranslucent ? false : this.blocksMovement();
	}

	public boolean isToolNotRequired() {
		return this.requiresNoTool;
	}

	/**
	 * Returns the mobility information of the material, 0 = free, 1 = can't push but can move over, 2 = total
	 * immobility and stop pistons.
	 */
	public int getMaterialMobility() {
		return this.mobilityFlag;
	}

	/**
	 * This type of material can't be pushed, but pistons can move over it.
	 */
	protected Material setNoPushMobility() {
		this.mobilityFlag = 1;
		return this;
	}

	/**
	 * This type of material can't be pushed, and pistons are blocked to move.
	 */
	protected Material setImmovableMobility() {
		this.mobilityFlag = 2;
		return this;
	}

	protected Material setAdventureModeExempt() {
		this.isAdventureModeExempt = true;
		return this;
	}

	public boolean isAdventureModeExempt() {
		return this.isAdventureModeExempt;
	}

	public static final class MapColor {
		/** Holds all the 16 colors used on maps, very similar of a pallete system.  */
		public static final MapColor[] mapColorArray = new MapColor[16];

		public static final MapColor airColor = new MapColor(0, 0);
		public static final MapColor grassColor = new MapColor(1, 8368696);
		public static final MapColor sandColor = new MapColor(2, 16247203);
		public static final MapColor clothColor = new MapColor(3, 10987431);
		public static final MapColor tntColor = new MapColor(4, 16711680);
		public static final MapColor iceColor = new MapColor(5, 10526975);
		public static final MapColor ironColor = new MapColor(6, 10987431);

		/** The map color for Leaf, Plant, Cactus, and Pumpkin blocks. */
		public static final MapColor foliageColor = new MapColor(7, 31744);
		public static final MapColor snowColor = new MapColor(8, 16777215);
		public static final MapColor clayColor = new MapColor(9, 10791096);
		public static final MapColor dirtColor = new MapColor(10, 12020271);
		public static final MapColor stoneColor = new MapColor(11, 7368816);
		public static final MapColor waterColor = new MapColor(12, 4210943);
		public static final MapColor woodColor = new MapColor(13, 6837042);

		/** Holds the color in RGB value that will be rendered on maps. */
		public final int colorValue;

		/** Holds the index of the color used on map. */
		public final int colorIndex;

		private MapColor(int par1, int par2) {
			this.colorIndex = par1;
			this.colorValue = par2;
			mapColorArray[par1] = this;
		}
	}
}
